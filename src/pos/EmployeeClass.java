/*
 * You are permitted to copy, distribute and/or modify this software or its source code.
 * There is no warranty for this program, implying that in no event required by applicable
 * law or agreed to in writing will the copyright owner be liable to you for damages.
 * That said, I do agree to any and all fun and awsomity this product might cause. Have Fun!:-)
 */
package pos;

import javax.swing.JOptionPane;

/**
 *
 * @author Witwicky
 */
public class EmployeeClass {
   //class to perform employee operations
    
    //declaration of necessary variables
    String firstName;
    String lastName;
    String userName;
    String dOB;
    String gender;
    String employeeType;
    String emailAddress;
    String homeNumber;
    String mobileNumber;
    String city;
    String postalCode;
    String imagePath;
    String query;
    
    //create object and reference of class Database
    DatabaseClass myDatabase = new DatabaseClass();
    
    //method to add employee to database
    public void addEmployee(String name1, String name2, String name3, String sex, String dob, String type,
            String homeNo, String mobileNo, String email, String town, String code, String path) {
        
        //call appropriate set methods
        firstName = name1;
        lastName = name2;
        userName = name3;
        setDOB(dob);
        setGender(sex);
        employeeType = type;
        emailAddress = email;
        setHomeNumber(homeNo);
        setMobileNumber(mobileNo);
        city = town;
        postalCode = code;
        imagePath = path;
        
        query = String.format("INSERT INTO employee_table (first_name, last_name, user_name, password, gender, dob, employee_type,"
                + " home_phone, mobile_phone, email_address, city, postal_code, image_path) VALUES (\"%s\", \"%s\", \"%s\", \"%s\","
                + " \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\")", firstName, lastName, 
                userName, userName, gender, dOB, employeeType, homeNumber, mobileNumber, emailAddress, city, postalCode, imagePath);
        
        //add entry to db
        myDatabase.executeQuery(query, "adding Employee");
        
        //System.out.println("The path is now: " + imagePath);
    } //end of method addEmployee
    
    //method to delete employee from database
    public void deleteEmployee(int empId) {
        query = String.format("DELETE FROM employee_table WHERE emp_id=%d", empId);
        
        //delete entry from db
        myDatabase.executeQuery(query, "Deleting Employee");
    } //end of method of deleteEmployee
    
    //method to set dob
    private void setDOB(String date) {
        //check if date is set up correctly
        dOB = date;
    }
    
    //method to set the password
    public void setPassword(String pwd, String uName) {
        query = String.format("UPDATE employee_table SET password=\"%s\" WHERE user_name=\"%s\"", pwd, uName);
        
        //call method to update the db
        myDatabase.executeQuery(query, "Changing password");
    }
    //method to set gender
    private void setGender(String sex) {
        if(sex.equals("Male") || sex.equals("Female")) {
            gender = sex;
        }
        else {
            JOptionPane.showMessageDialog(null, "You have input a wrong gender", "Non existent gender",
                    JOptionPane.ERROR_MESSAGE);
        }
    } //end of method setGender
    
    //method to set the phone number
    private void setHomeNumber(String number) {
        if(number.startsWith("0") && number.length() == 10) {
            homeNumber = number;
        }
        else {
            JOptionPane.showMessageDialog(null, "That home number entered does not exist", "Wrong home number format",
                    JOptionPane.ERROR_MESSAGE);
        } //end of else
    } //end of method sethomeNumber
    
    //method to set the phone number
    private void setMobileNumber(String number) {
        if(number.startsWith("0") && number.length() == 10) {
            mobileNumber = number;
        }
        else {
            JOptionPane.showMessageDialog(null, "That mobile number entered does not exist", "Wrong mobile number format",
                    JOptionPane.ERROR_MESSAGE);
        } //end of else
    } //end of method sethomeNumber
}