/*
 * You are permitted to copy, distribute and/or modify this software or its source code.
 * There is no warranty for this program, implying that in no event required by applicable
 * law or agreed to in writing will the copyright owner be liable to you for damages.
 * That said, I do agree to any and all fun and awsomity this product might cause. Have Fun!:-)
 */
package pos;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Witwicky
 */
public class TransactionClass {
    //class to add the transaction to the database
    
    //create object and reference of class Database
    DatabaseClass myDatabase = new DatabaseClass();
    
    int transactionId;
    int empId;
    String date;
    double amount;
    String query;
    
    //method to insert the transaction
    public void addTransaction(int id, double amt) {
        //set the values
        empId = id;
        amount = amt;
        setDate(); 
        query = String.format("INSERT INTO transaction_table (emp_id, date, amount) VALUES "
                + "(\"%d\", \"%s\", \"%.2f\")", empId, date, amount);
        
        //add entry to db
        myDatabase.executeQuery(query, "adding transaction");
        
    } //end of method to add transaction
    
    //method to set the date
    public void setDate() {
        Date dNow = new Date( );
        SimpleDateFormat dateFormat = 
            new SimpleDateFormat ("dd.MM.yyy");
        date = String.format("%s", dateFormat.format(dNow));
    } //end of method to set the date
} //end of transaction class
