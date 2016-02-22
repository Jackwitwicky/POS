/*
 * You are permitted to copy, distribute and/or modify this software or its source code.
 * There is no warranty for this program, implying that in no event required by applicable
 * law or agreed to in writing will the copyright owner be liable to you for damages.
 * That said, I do agree to any and all fun and awsomity this product might cause. Have Fun!:-)
 */
package pos;

import java.util.Random;
import java.util.ArrayList;
import java.util.Date;
import java.io.*;
import java.util.Formatter;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.OK_CANCEL_OPTION;

/**
 *
 * @author Witwicky
 */
public class PrintClass {
    //class that deals with setting up the pos dirs
    //and printing receipt to the file
    
    //declaration of necessary variables
    String printOut = "";
    ArrayList< Integer > randomList = new ArrayList< Integer >();
    String receiptNo = "";
    String userName;
    String [] items;
    int [] quantities;
    double [] prices;
    double [] amounts;
    String date = new Date().toString();
    //int counter = prices.length - 1;
    double total;
    double cash = 300.00;
    double change = 84.54;
    double vat = 0.16;
    double vatableAmt;
    double vatAmt;
    
    //create object and reference of class Random
    Random randomNumbers = new Random();
    
    //file variables
    String path = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "POS";
    String printPath = path + File.separator + "printOut.txt";
    String name = System.getProperty("user.name");
    String welcomeMessage = "\tWelcome back, " + name + "." +
            "\nThe necessary files have been loaded successfully.";
    String firstRunMessage = "Hi " + name + ",\n" + 
            "It appears this is the first time running this application.\nSome necessary files must be created before we start.";
    File printFile;
    Formatter printFileFormat;
    
    Boolean scannerInputOpen = false;
    Boolean formatterInputOpen = false;
    
    Boolean dialog = false;
    
    
    //create file 
    //check if the folder exists and create it if not
    public void checkFolder() {
        String path = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "POS";
        File customDir = new File(path);
        
        if(!customDir.exists()) {
            int showConfirmDialog = JOptionPane.showConfirmDialog(null, firstRunMessage, "First Run", OK_CANCEL_OPTION,INFORMATION_MESSAGE);
            //perform operation that has been chosen
            if(showConfirmDialog == 0) {
                customDir.mkdirs();
            printFile = new File(printPath);
            
            //try to create the files
            try{
                printFile.createNewFile();
                JOptionPane.showMessageDialog(null, "The necessary files have successfully been created.", "All Done", INFORMATION_MESSAGE);
                
            }
            catch(Exception e) {
                JOptionPane.showMessageDialog(null, "The files could not be created, you could try doing it manually.", "Unfortunate Error", ERROR_MESSAGE);
            } //end of catch block
            

            } //end of if selection was okay
            else {
                JOptionPane.showMessageDialog(null, "The necessary files are needed to run. The application will now exit.", "Goodbye", INFORMATION_MESSAGE);
                System.exit(0);
            } //else the selection was no
            
        } //if folder does not exist
        
    } //end of method check folder
    
    //method to open the file
    private void openFile() {
        //try to open the file
        try {
            printFileFormat = new Formatter(printPath);
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, "This is embarrassing, the file could not be written to.", "Error", ERROR_MESSAGE);
        }
    } //end of method to open the file
    
    
    //method to add records to the file
    private void addRecord(String printString) {
        printFileFormat.format("%s", printString);
    } //end of method to add the record
    
    //method to close the file
    private void closeFile() {
        printFileFormat.close();
    } //end of method closeFile
    
    //method to generate the receipt number
    public void generateReceiptNo() {
        for(int counter = 5; counter >=0; counter--) {
            receiptNo = receiptNo.concat(Integer.toString(1 + randomNumbers.nextInt( 8)));
        }
        //receiptNo = Integer.parseInt(randomList.toString());
    } //end of method generateReceiptNo
    
    //method to generate the receipt
    public void generateReceipt(String name, String [] item, int [] qty, double [] cost,
            double [] amt, double ttl, double csh, double chnge, double vt) {
        userName = name;
        items = item;
        quantities = qty;
        prices = cost;
        amounts = amt;
        total = ttl;
        cash = csh;
        change = chnge;
        vat = vt;
        vatableAmt = total / (1 + vat);
        vatAmt = total - vatableAmt;
        int counter = items.length - 1;
        
        //call method to generate number
        generateReceiptNo();
        
        printOut = printOut.concat(String.format("%43s: %s\r\n\r\n", "Receipt No", receiptNo));
        printOut = printOut.concat(String.format("%s\r\n", date));
        printOut = printOut.concat("--------------------------------------------------\r\n");
        
        printOut = printOut.concat(String.format("%s%25s%10s%10s\r\n", "ITEM", "QUANTITY", "PRICE", "AMOUNT"));
        printOut = printOut.concat("--------------------------------------------------\r\n");
        
        //loop to insert items to printOut
        for(int tempCounter = 0;tempCounter <=counter; tempCounter++) {
            printOut = printOut.concat(String.format("%s%19d%13.2f%11.2f\r\n", items[tempCounter], quantities[tempCounter], prices[tempCounter], amounts[tempCounter]));
        } //end of for loop
        
        printOut = printOut.concat("--------------------------------------------------\r\n");
        
        printOut = printOut.concat(String.format("%s: %43.2f\r\n", "TOTAL", total));
        printOut = printOut.concat(String.format("%s:  %43.2f\r\n", "CASH", cash));
        printOut = printOut.concat(String.format("%s:%43.2f\r\n", "CHANGE", change));
        
        printOut = printOut.concat("--------------------------------------------------\r\n");
        
        printOut = printOut.concat(String.format("TOTAL ITEMS: %d\r\n", ++counter));
        
        printOut = printOut.concat(String.format("%s%30s%12s\r\n", "VAT RATE", "VATABLE AMT", "VAT AMT"));
        printOut = printOut.concat(String.format("-------- %28s%13s\r\n", "----------", "-------"));
        
        printOut = printOut.concat(String.format("%.2f%32.2f%13.2f\r\n\r\n\r\n", (vat * 100), vatableAmt, vatAmt));
        
        printOut = printOut.concat(String.format("PRICES INCLUSIVE OF VAT WHERE APPLICABLE\r\n"));
        printOut = printOut.concat("--------------------------------------------------\r\n");
        printOut = printOut.concat("--------------------------------------------------\r\n");
        
        printOut = printOut.concat(String.format("YOU WERE SERVED BY: %s\r\n", userName));
        
        printOut = printOut.concat(String.format("Thank You For Shopping With Us.\r\n"));
        
        printOut = printOut.concat("--------------------------------------------------\r\n");
        printOut = printOut.concat("--------------------------------------------------\r\n");
        
        //call methods to write the file
        openFile();
        addRecord(printOut);
        closeFile();
        
        System.out.println(printOut);
    }
    
}
