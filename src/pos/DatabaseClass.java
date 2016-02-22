/*
 * You are permitted to copy, distribute and/or modify this software or its source code.
 * There is no warranty for this program, implying that in no event required by applicable
 * law or agreed to in writing will the copyright owner be liable to you for damages.
 * That said, I do agree to any and all fun and awsomity this product might cause. Have Fun!:-)
 */
package pos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Witwicky
 */
public class DatabaseClass {
    //class to handle database mgt
    
    //initialize necessary variables
    static final String databaseUrl = "jdbc:mysql://localhost/pos";
    //static final String databaseUrlTwo = "jdbc:mysql://localhost/download_list";
    String query = "";
    String output = "";
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    PreparedStatement deleteElement = null;
    int counter = 0;
    //ResultSetTableModel tableModel;
    
    //String[] nameArray = new String[100];
    
    //constructor of the class
    public DatabaseClass() {
        try {
            //establish a connection to the database
            connection = DriverManager.getConnection(databaseUrl, "root", "");
            
            //create a statement to query the database
            statement = connection.createStatement(); 
        }
        catch(Exception e) {
            System.err.println(e);
        } //end of try n catch block
    } //end of constructor
    
    //method to execute query
    public ResultSet executeQuery(String exp, String type) {
        query = exp;
        //try to execute the query
        if(type.equals("Report") || type.equals("report")) {
            try{
            resultSet = statement.executeQuery(exp);
            }
            catch(Exception e) {
                System.err.println(e);
            } //end of catch block
        } //end of report
        else if(type.equals("Changing password")) {
            try {
                statement.executeUpdate(exp);
                JOptionPane.showMessageDialog(null, "Your new password has been successfully set up.", "Success", JOptionPane.INFORMATION_MESSAGE);
                resultSet = null;
            }
            catch(Exception e) {
                System.err.println(e);
            } //end of catch block
        } //end of else
        else if(type.equals("updating inventory") || type.equals("adding transaction")) {
            try {
                statement.executeUpdate(exp);
                resultSet = null;
            }
            catch(Exception e) {
                System.err.println(e);
            } //end of catch block
        } //end of if
        else {
            try {
                statement.executeUpdate(exp);
                JOptionPane.showMessageDialog(null, "The entry has been modified successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                resultSet = null;
            }
            catch(Exception e) {
                System.err.println(e);
            } //end of catch block
        } //end of else
        return resultSet;
    } //end of method execute Query
    
    //method to get the result Set
    public String[] getResultSet(int index, String type) {
        try {
            ArrayList<String> itemList = new ArrayList <String> ();
            if(type.equals("employeeNames")) {
                if(index == 0) {
                    resultSet = statement.executeQuery("SELECT * FROM employee_table");
                    while(resultSet.next()) {
                        itemList.add(resultSet.getString("user_name"));
                        //resultSet
                    } //end of while
                } //end of first if
                else {
                    //ArrayList<String> itemList = new ArrayList <String> ();
                    String exp = String.format("SELECT * FROM employee_table WHERE emp_id=%d", index);
                    resultSet = statement.executeQuery(exp);
                    while(resultSet.next()) {
                        for(int counter = 1; counter <=14; counter++) {
                            itemList.add(resultSet.getString(counter));
                        }
                        //resultSet
                    } //end of while
                } //end of else
            } //end of empployeeNames if
            else if(type.equals("employeeIds")) {
                if(index == 0) {
                    resultSet = statement.executeQuery("SELECT * FROM employee_table");
                    while(resultSet.next()) {
                        itemList.add(resultSet.getString("emp_id"));
                        //resultSet
                    } //end of while
                } //end of first if 
            }
            else if(type.equals("inventory")) {
                if(index == 0) {
                    resultSet = statement.executeQuery("SELECT * FROM inventory_table");
                    while(resultSet.next()) {
                        itemList.add(resultSet.getString("emp_id"));
                        //resultSet
                    }
                }
                else {
                    //ArrayList<String> itemList = new ArrayList <String> ();
                    String exp = String.format("SELECT * FROM employee_table WHERE emp_id=%d", index);
                    resultSet = statement.executeQuery(exp);
                    while(resultSet.next()) {
                        for(int counter = 1; counter <=14; counter++) {
                            itemList.add(resultSet.getString(counter));
                        } //end of for
                        //resultSet
                    }
                }
            }
            else if(type.equals("inventoryIds")) {
                if(index == 0) {
                    resultSet = statement.executeQuery("SELECT * FROM inventory_table");
                    while(resultSet.next()) {
                        itemList.add(resultSet.getString("bar_code"));
                        //resultSet
                    }
                }
                else {
                    //ArrayList<String> itemList = new ArrayList <String> ();
                    String exp = String.format("SELECT * FROM inventory_table WHERE bar_code=%d", index);
                    resultSet = statement.executeQuery(exp);
                    while(resultSet.next()) {
                        for(int counter = 1; counter <=7; counter++) {
                            itemList.add(resultSet.getString(counter));
                        }
                        //resultSet
                    } //end of while
                } //end of else
            } //end of inventory Id
            else {
                String exp = String.format("SELECT * FROM employee_table WHERE user_name=\"%s\"", type);
                    resultSet = statement.executeQuery(exp);
                    while(resultSet.next()) {
                        for(int counter = 1; counter <=14; counter++) {
                            itemList.add(resultSet.getString(counter));
                        }
                        //resultSet
                    }
            }
            
            
           
           String [] nameArray = (String[]) itemList.toArray(new String [itemList.size()]);
           itemList = null;
           return nameArray;
        }
        catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e, "Error connecting to db", JOptionPane.INFORMATION_MESSAGE);
        }
        return null;
        
    }
} //end of DatabaseClass
