package com.example.minor_project1.service;

import com.example.minor_project1.dto.SearchBookRequest;
import com.example.minor_project1.model.Book;
import com.example.minor_project1.model.Student;
import com.example.minor_project1.model.Transaction;
import com.example.minor_project1.model.enums.TransactionStatus;
import com.example.minor_project1.model.enums.TransactionType;
import com.example.minor_project1.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    @Autowired
    StudentService studentService;

    @Autowired
    BookService bookService;

    @Autowired
    TransactionRepository transactionRepository;

    @Value("${student.issue.max_books}")
    private int maxBooksForIssuance;

    @Value("${student.issue.number_of_days}")
    private int numberOfDaysForIssuance;

//    public String initiateTxn(String bookName, int studentId, TransactionType transactionType) throws Exception {
//        switch (transactionType){
//            case ISSUE:
//                return issueTxn(bookName,studentId);
//            case RETURN:
//                return returnTxn(bookName,studentId);
//            default:
//                throw new Exception("Invalid Transaction Type");
//        }
//    }

    public String issueTxn(String bookName, int studentId) throws Exception {
        List<Book>bookList;
        try {
            bookList = bookService.search(
                    SearchBookRequest.builder()
                            .searchKey("name")
                            .searchValue(bookName)
                            .operator("=")
                            .available(true)
                            .build()
            );
        } catch (Exception e) {
            throw new Exception("Book not found");
        }
        Student student=studentService.get(studentId);
        //Validations
        if(student.getBookList()!=null && student.getBookList().size()>=maxBooksForIssuance){
            throw new Exception("Book limit reached");
        }
        if(bookList.isEmpty()){
            throw new Exception("Not able to find any book in the library");
        }
        Book book=bookList.get(0);
        Transaction transaction=Transaction.builder()
                .externalTxnId(UUID.randomUUID().toString())
                .transactionType(TransactionType.ISSUE)
                .student(student)
                .book(book)
                .transactionStatus(TransactionStatus.PENDING)
                .build();
        transaction = transactionRepository.save(transaction);
        try{
            book.setMy_student(student);
            bookService.assignBookToStudent(book,student);
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            transaction.setTransactionStatus(TransactionStatus.FAILED);
        }finally {
            return transactionRepository.save(transaction).getExternalTxnId();
        }
    }

    public String returnTxn(int bookId, int studentId) throws Exception {
        Book book;
        try{
            book=this.bookService.search(
                    SearchBookRequest.builder()
                            .searchKey("id")
                            .searchValue(String.valueOf(bookId))
                            .build()
            ).get(0);
        }catch (Exception e){
            throw new Exception("Not able to fetch book details.");
        }
        //Validation
        if(book.getMy_student()==null||book.getMy_student().getId()!=studentId){
            throw new Exception("Book is not assigned to this student.");
        }
        Student student=this.studentService.get(studentId);
        Transaction transaction=Transaction.builder()
                .externalTxnId(UUID.randomUUID().toString())
                .transactionType(TransactionType.RETURN)
                .student(student)
                .book(book)
                .transactionStatus(TransactionStatus.PENDING)
                .build();
        transaction = transactionRepository.save(transaction);
        //Get the corresponding issue Txn
        Transaction issueTransaction = this.transactionRepository
                .findTopByStudentAndBookAndTransactionTypeAndTransactionStatusOrderByTransactionTimeDesc(
                        student, book, TransactionType.ISSUE, TransactionStatus.SUCCESS
                );
        //Fine
        long issueTxnInMillis = issueTransaction.getTransactionTime().getTime();
        long currentTimeMillis = System.currentTimeMillis();
        long timeDifferenceInMillis = currentTimeMillis-issueTxnInMillis;
        long timeDifferenceInDays = TimeUnit.DAYS.convert(timeDifferenceInMillis, TimeUnit.MILLISECONDS);
        Double fine=0.0;
        if(timeDifferenceInDays > numberOfDaysForIssuance){
            fine = (timeDifferenceInDays - numberOfDaysForIssuance)*1.0;
        }
        try{
            book.setMy_student(null);
            this.bookService.unassignBookFromStudent(book);
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
            return transaction.getExternalTxnId();
        }catch (Exception e){
            e.printStackTrace();
            transaction.setTransactionStatus(TransactionStatus.FAILED);
        }finally {
            transaction.setFine(fine);
            return transactionRepository.save(transaction).getExternalTxnId();
        }
    }

}