package com.example.minor_project1.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    //issuance
    //return

    //initiateTxn()->bookId,studentId,type of txn

    /**
     * Issuance
     * 1.Create a txn with pending status
     * 2.Get the book details and student details
     * 3.Validations
     * 4.Assign the book to that particular student//update book set student_id=?
     * 5.Update the txn accordingly with status as SUCCESS or FAILED
     */

    /**
     * Return
     * 1.Create a txn with pending status
     * 2.Check the due date and correspondingly evaluate the fine
     * 3.Unassign the book from student//update book set student_id=null
     * 4.Update the txn accordingly with status as SUCCESS or FAILED
     */

}
