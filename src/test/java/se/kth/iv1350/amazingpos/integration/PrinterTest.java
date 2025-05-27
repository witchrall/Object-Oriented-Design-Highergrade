package se.kth.iv1350.amazingpos.integration;



import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.amazingpos.model.CashPayment;
import se.kth.iv1350.amazingpos.model.Payment;
import se.kth.iv1350.amazingpos.model.PaymentStrategy;
import se.kth.iv1350.amazingpos.model.Receipt;
import se.kth.iv1350.amazingpos.model.Sale;

public class PrinterTest {
    private RegistryCreator exSystems;
    private Printer printer;
    private Sale testSale;
    private int validItemIdentifier1 = 1;
    private int validItemIdentifier2 = 2;
    private int validItemIdentifier3 = 3;
    private int quantity = 1;
    private double paidAmountMore = 1150;
    private double paidAmountExact = 765;
    private PaymentStrategy paymentType = new CashPayment();
    PrintStream originalOut;
    ByteArrayOutputStream outContent;

    private Payment payment; 

    private Receipt receipt;
  
    @BeforeEach
    void setUp(){
        exSystems = new RegistryCreator();
        printer = new Printer();
        testSale = new Sale(exSystems, printer);
        testSale.registerItem(validItemIdentifier1, quantity);
        testSale.registerItem(validItemIdentifier2, quantity*2);
        testSale.registerItem(validItemIdentifier3, quantity*3);
        testSale.endSale();
        payment = new Payment(paidAmountMore, paymentType);
        testSale.pay(payment);
        receipt = new Receipt(testSale);

        outContent = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outContent));
    
    }
    @AfterEach
    void tearDownClass(){
        System.setOut(originalOut);
    }

    @Test
    void testPrintReceiptTimeOfSale() {
        printer.printReceipt(receipt);
        String output = outContent.toString();
        assertTrue(output.contains("Time of sale: " + receipt.getSaleTime()), "Time of sale missing");
    }

    @Test
    void testPrintReceiptTotal() {
        printer.printReceipt(receipt);
        String output = outContent.toString();
        assertTrue(output.contains("Total: " + receipt.getFinalAmount()), "Total is missing");
    }

    @Test
    void testPrintReceiptVAT() {
        printer.printReceipt(receipt);
        String output = outContent.toString();
        assertTrue(output.contains("VAT: " + receipt.getVat()), "VAT is missing");
    }

    @Test
    void testPrintReceiptPaidAmmount() {
        printer.printReceipt(receipt);
        String output = outContent.toString();
        assertTrue(output.contains("Paid amount: " + receipt.getAmountPaid()), "Paid amount is missing");
    }

    @Test
    void testPrintReceiptChange() {
        printer.printReceipt(receipt);
        String output = outContent.toString();
        assertTrue(output.contains("Change: " + receipt.getChange()), "Change is missing");
    }
}
