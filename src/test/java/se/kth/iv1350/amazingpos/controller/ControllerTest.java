package se.kth.iv1350.amazingpos.controller;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.amazingpos.integration.Printer;
import se.kth.iv1350.amazingpos.integration.RegistryCreator;

import se.kth.iv1350.amazingpos.model.*;


public class ControllerTest {
    static  Sale currentSale;
    static  RegistryCreator externalSystems;
    static  Printer printer;
    static  Controller controller;

    private int validItemIdentifier = 1;
    private int inValidItemIdentifier = 7;
    private int  quantity = 2;
    private int  invalidMaxQuantity = 1001;
    private int  invalidNegativeQuantity = -1001;
    private int customerID = 123;

    private double paidAmountMore = 1150;
    private double paidAmountExact = 765;

    private int validItemIdentifier1 = 1;
    private int validItemIdentifier2 = 2;
    private int validItemIdentifier3 = 3;

    private int  quantityX = 1;

    private PaymentStrategy paymentType = new CashPayment();
    private PaymentStrategy paymentTypeCreditCard = new CreditCardPayment();
    
   

    @BeforeEach
    void setUpClass(){    
        printer = new Printer();
        externalSystems = new RegistryCreator();
        controller = new Controller(externalSystems, printer);
        controller.startSale();
    }

    @AfterEach
    void tearDown(){
        printer = null;
        externalSystems = null;
        controller = null;    
    }

    @Test
    void testStartSale(){
        controller.startSale();
    }
 
    @Test
    void testAddItemInvalidMaxQuantity() {
        SaleDTO current = null;
        try{
            current = controller.addItem(validItemIdentifier,quantity);
            current = controller.addItem(validItemIdentifier, invalidMaxQuantity);
        }catch(InvalidItemException error){

        } 
        int expected = 2;
        int result = current.getListOfRegisteredItem().getShoppingList().get(0).getQuantity();
        assertEquals(result, expected, "Invalid item has been added.");
        
    
    }

    @Test
    void testAddItemInvalidNegativeQuantity() {
        SaleDTO current = null;
        try{
            current = controller.addItem(validItemIdentifier,quantity);
            current = controller.addItem(validItemIdentifier, invalidNegativeQuantity);
        }catch(InvalidItemException error){

        } 
        int expected = 2;
        int result = current.getListOfRegisteredItem().getShoppingList().get(0).getQuantity();
        assertEquals(result, expected, "Invalid item has been added.");   
    }

 
    @Test
    void testIfAddItemReturnsSaleDTO(){
        SaleDTO current = null;
        try{
            current = controller.addItem(validItemIdentifier, quantity);
        }catch (InvalidItemException error){
            
        }
        assertTrue(current instanceof SaleDTO);
    }

    @Test
    void testAddInvalidItemIsNotAdded(){
        SaleDTO current = null;
        try{
            current = controller.addItem(validItemIdentifier,quantity);
            current = controller.addItem(inValidItemIdentifier, quantity);
        }catch(InvalidItemException error){

        } 
        assertTrue(current.getLastRegisteredItem() != null, "Invalid item has been added.");
    }

    @Test
    void testSignalDiscount(){
        SaleDTO discountedSale = controller.signalDiscount(customerID);

        assertTrue(discountedSale instanceof SaleDTO, "signalDiscount doesn't return a SaleDTO");

    }

    @Test
    void testConcludeSaleWithBoughtItems(){
        SaleDTO current = null;
        try{
            current = controller.addItem(validItemIdentifier,quantity);
            current = controller.addItem(inValidItemIdentifier, quantity);
        }catch(InvalidItemException error){

        } 

        double result = controller.concludeSale();
        double expected = 345;

        assertEquals(expected, result, "Failed to conclude sale");

    }

    @Test
    void testConcludeSaleWithNoBoughtItems(){
        

        double result = controller.concludeSale();
        double expected = 0;

        assertEquals(expected, result, "Failed to conclude sale");

    }

    @Test
    void testPayReturnsZeroChange(){
        try{
            controller.addItem(validItemIdentifier1, quantityX);
            controller.addItem(validItemIdentifier2, quantityX*2); 
            controller.addItem(validItemIdentifier3, quantityX*3); 
            controller.concludeSale();
        }catch(InvalidItemException error){

        }

        double result = controller.pay(paidAmountExact, paymentType);
        double expected = 0;

        assertEquals(expected, result, "Change amount doesnt match!");

    }

    @Test
    void testPayReturnsRightChangeAmount(){
        try{
            controller.addItem(validItemIdentifier1, quantityX);
            controller.addItem(validItemIdentifier2, quantityX*2); 
            controller.addItem(validItemIdentifier3, quantityX*3); 
            controller.concludeSale();
        }catch(InvalidItemException error){
            
        }

        double result = controller.pay(paidAmountMore, paymentType);
        double expected = 385;

        assertEquals(expected, result, "Change amount doesnt match!");

    }
    @Test
    void testInvalidItemExceptionForInvalidMaxQuantity() {
        String message = ""; 
        try {
            SaleDTO current = controller.addItem(validItemIdentifier, invalidMaxQuantity);
        } catch (InvalidItemException quantityError){
            message = quantityError.getMessage();
        }

        String expectedResult = "The quantity is unreasonable!";
        
        assertEquals(message, expectedResult, "Exception Message Is incorrect");    
    }

    @Test
    void testInvalidItemExceptionForInvalidNegativeQuantity() {
        String message = "";
        try {
           SaleDTO current = controller.addItem(validItemIdentifier, invalidNegativeQuantity);
        } catch (InvalidItemException quantityError){
            message = quantityError.getMessage();
        }
        String expectedResult = "The quantity is unreasonable!";
        assertEquals(message, expectedResult,"Exception Message Is incorrect");    
    }
    @Test
    void testInvalidItemExceptionForInvalidItemIdentifier() {
        String message = "";
        try {
           SaleDTO current = controller.addItem(inValidItemIdentifier, quantity);
        } catch (InvalidItemException quantityError){
            message = quantityError.getMessage();
        }
        String expectedResult = "itemNotValid";
        assertEquals(message, expectedResult,"Exception Message Is incorrect");    
    }

    @Test
    void testCreditCardPaymentReturnsZero(){
        try{
            controller.addItem(validItemIdentifier1, quantityX);
            controller.addItem(validItemIdentifier2, quantityX*2); 
            controller.addItem(validItemIdentifier3, quantityX*3); 
            controller.concludeSale();
        }catch(InvalidItemException error){

        }

        double result = controller.pay(paidAmountExact, paymentTypeCreditCard);
        double expected = 0;

        assertEquals(expected, result, "Change amount doesnt match!");

    }
}

