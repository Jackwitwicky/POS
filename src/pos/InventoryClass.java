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
public class InventoryClass {
    //class to perform inventory mgt
    
    //declaration of necessary variables
    int barCode;
    String name;
    String category;
    double price;
    int vat;
    int stock;
    int store;
    String query;
    Boolean doubleError =  false;
    Boolean intErrorOne = false;
    Boolean intErrorTwo = false;
    
    //create object and reference of class Database
    DatabaseClass myDatabase = new DatabaseClass();
    
    //method to add a new inventory
    public Boolean newInventory(String code, String nam, String cat, String cost, String stk, String vt) {
        
        //store the values provided
        price = setPrice(cost);
        stock = setStock(stk);
        barCode = setBarCode(code);
        setVat(vt);
        
        if(intErrorOne || doubleError || intErrorTwo) {
            return true;
        }
        else{
            store = stock;
            name = nam;
            category = cat;
            query = String.format("INSERT INTO inventory_table (bar_code, name, category,"
                    + " price, total_stock, store_stock, vat) VALUES (\"%s\", \"%s\", \"%s\", \"%f\","
                    + " \"%d\", \"%d\", \"%s\")", barCode, name, category, price, stock, store, vat);
           
            //add entry to db
            myDatabase.executeQuery(query, "adding Stock");
            return false;
        } //end of else
    } //end of method newInventory
    
    //method to restock the inventory
    public Boolean restock(int code, String total, String newStk) {
        Boolean error = false;
        //try to convert new stock to an integer
        try{
            int totalStock = Integer.parseInt(total);
            int newStock = Integer.parseInt(newStk);
            
            totalStock += newStock;
            
            //pass new value to database
            query = String.format("UPDATE inventory_table SET total_stock=%d WHERE bar_code=%d",
                    totalStock, code);
            
            //edit entry of db
            myDatabase.executeQuery(query, "restocking inventory");
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, "The entered value is not a number.", "Invalid Stock", JOptionPane.ERROR_MESSAGE);
            error = true;
        } //end of catch block
        
        return error;
    } //end of method restock
    
    //method to move stock to shelf
    public Boolean toShelf(int code, String storeStk, String shelfStk) {
        Boolean error = false;
        //try to convert new shelf stock to an integer
        try{
            int shelfStock = Integer.parseInt(shelfStk);
            int storeStock = Integer.parseInt(storeStk);
            
            if(shelfStock <= storeStock) {
                storeStock -= shelfStock;
            
                //pass new value to database
                query = String.format("UPDATE inventory_table SET store_stock=%d WHERE bar_code=%d",
                         storeStock, code);
            
                //edit entry of db
                myDatabase.executeQuery(query, "restocking inventory");
            } //end of if
            else {
                JOptionPane.showMessageDialog(null, "That number exceeds the available stock."
                        + "\nPlease try again.", "Over Stocking", JOptionPane.INFORMATION_MESSAGE);
                error = true;
            } //end of else
        } 
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, "The entered value is not a number.\nPlease try again.", "Invalid Stock", JOptionPane.ERROR_MESSAGE);
            error = true;
        } //end of catch block
        
        return error;
    } //end of method toShelf
    
    //method to update the inventory
    public void updateInventory(int code, int total, int qty) {
        int newTotal = total - qty;
        query = String.format("UPDATE inventory_table SET total_stock=%d WHERE bar_code=%d",
                newTotal, code );
        
        //edit entry of db
        myDatabase.executeQuery(query, "updating inventory");
    }
    //method to set the vat
    private void setVat(String vt) {
        if(vt.equals("Yes")) {
            vat = 16;
        }
        else {
            vat = 0;
        }
    } //end of method setVat
    
    //method to set the bar code
    private int setBarCode(String code) {
        int num = 0;
        try {
            num = Integer.parseInt(code);
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, "That is an incorrect barcode", "Wrong bar code", 
                    JOptionPane.ERROR_MESSAGE);
            intErrorOne = true;
        } //end of catch
        return num;
    } //end of method setBarCode
    
    //method to set the price
    private double setPrice(String cost) {
        double num = 0.0; 
        try{
            num = Double.parseDouble(cost);
            if(num < 0.0) {
                doubleError = true;
            }
            else {
                return num;
            }
        }
        catch(Exception e) {
            doubleError = true;
            JOptionPane.showMessageDialog(null, "The price you entered is not a number.\nPlease enter again",
                    "Not a number", JOptionPane.ERROR_MESSAGE, null);
        } //end of catch block
        
        return num;
    } //end of method set price
     
    
    //method to set the stock
    private int setStock(String stk) {
        //local variable to hold stock integer
        int num = 0;
        //try to convert to integer
        try {
            num = Integer.parseInt(stk);
        }
        catch(Exception e) {
            intErrorTwo = true;
            JOptionPane.showMessageDialog(null, "The stock you have input is not a "
                    + "valid number.\nPlease enter again", "Wrong stock", JOptionPane.ERROR_MESSAGE);
        } //end of catch block
        return num;
    } //end of method set Stock
    
    //method to calculate shelf stock
    public String calculateShelf(String total, String store) {
        int totalStock = Integer.parseInt(total);
        int storeStock = Integer.parseInt(store);
        
        int shelfStock = totalStock - storeStock;
        
        return Integer.toString(shelfStock);
    } //end of method calculate shelf
}