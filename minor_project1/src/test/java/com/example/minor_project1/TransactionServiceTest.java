package com.example.minor_project1;

import com.example.minor_project1.dto.SearchBookRequest;
import com.example.minor_project1.exceptions.BookLimitExceededException;
import com.example.minor_project1.exceptions.BookNotFoundException;
import com.example.minor_project1.model.Book;
import com.example.minor_project1.model.Student;
import com.example.minor_project1.model.Transaction;
import com.example.minor_project1.repository.TransactionRepository;
import com.example.minor_project1.service.BookService;
import com.example.minor_project1.service.StudentService;
import com.example.minor_project1.service.TransactionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)//how you want to execute your test cases
public class TransactionServiceTest {

    /**
     * Difference between InjectMocks and Mock is that InjectMocks creates an actual object of that particular class on top of which this annotation is written
     * whereas Mock just creates a dummy object of type <class>$<MockitoMock>$------ and associates mock with the actual object created by InjectMocks.
     */

    @InjectMocks//similar to @autowired
    TransactionService transactionService;

    @Mock//mocks at both parent and child level
    StudentService studentService;

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    BookService bookService;

//    @InjectMocks//actual object for parent level and dummy for child level
//    StudentService studentService;

    @Test
    public void issue_TxnTest() throws Exception {
        String bookName="ABC";
        int studentId=1;
        String externalTxnId=UUID.randomUUID().toString();

        List<Book> bookList= Arrays.asList(
                Book.builder()
                        .id(1)
                        .name("ABC")
                        .build()
        );

        Student student = Student.builder()
                .id(1)
                .name("Peter")
                .build();

        SearchBookRequest searchBookRequest = SearchBookRequest.builder()
                .searchKey("name")
                .searchValue(bookName)
                .available(true)
                .operator("=")
                .build();

        Transaction transaction = Transaction.builder()
                .externalTxnId(externalTxnId)
                .book(bookList.get(0))
                .student(student)
                .build();

        //obj1-> k1,k2,k3 obj2->k1,k2,k3 both are different objects

        //Mockito.when(bookService.search(searchBookRequest)).thenReturn(bookList);
        when(bookService.search(any())).thenReturn(bookList);
        when(studentService.get(studentId)).thenReturn(student);

        when(transactionRepository.save(any())).thenReturn(transaction);
        //Mockito.doNothing().when(bookService.assignBookToStudent(Mockito.any(),Mockito.any()));

        String txnIdReturned = transactionService.issueTxn("ABC",1);

        /*Checks for test case correctness-> (i)Assertion (ii)Verification*/

        //Assertion-> expected result == actual result
        Assert.assertEquals(externalTxnId,txnIdReturned);

        //Verification-> How many times function is called
        verify(studentService,times(1)).get(studentId);//will fail on 2
        verify(transactionRepository,times(2)).save(any());
        verify(bookService,times(1)).assignBookToStudent(any(),any());//will fail on 0

    }

    @Test(expected = BookNotFoundException.class)
    public void issue_TxnBookNotFound() throws Exception {
        String bookName="ABC";
        int studentId=1;

        Student student = Student.builder()
                .id(1)
                .name("Peter")
                .build();

        when(bookService.search(any())).thenReturn(new ArrayList<>());
        when(studentService.get(studentId)).thenReturn(student);

        String txnIdReturned = transactionService.issueTxn("ABC",1);
    }

    @Test(expected = BookLimitExceededException.class)
    public void issue_TxnBookLimitExceeded() throws Exception {
        String bookName="ABC";
        int studentId=1;

        List<Book> bookList= Arrays.asList(
                Book.builder()
                        .id(1)
                        .name("ABC")
                        .build()
        );

        Student student = Student.builder()
                .id(1)
                .bookList(Arrays.asList(
                        Book.builder().id(4).build(),
                        Book.builder().id(5).build(),
                        Book.builder().id(6).build()
                ))
                .name("Peter")
                .build();

        when(bookService.search(any())).thenReturn(bookList);
        when(studentService.get(studentId)).thenReturn(student);

        String txnIdReturned = transactionService.issueTxn("ABC",1);

        verify(transactionRepository,times(0)).save(any());
        verify(bookService,times(0)).assignBookToStudent(any(),any());
    }

}
