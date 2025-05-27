package se.kth.iv1350.amazingpos.view;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.amazingpos.controller.Controller;
import se.kth.iv1350.amazingpos.integration.Printer;
import se.kth.iv1350.amazingpos.integration.RegistryCreator;
import se.kth.iv1350.amazingpos.model.PaymentStrategy;

public class ViewTest {
    private PaymentStrategy paymentType;
    private Controller contr;
    static  RegistryCreator externalSystems;
    static  Printer printer;
    private View view;

    PrintStream originalOut;
    ByteArrayOutputStream outContent;

    @BeforeEach
    void setUpClass(){
        printer = new Printer();
        externalSystems = new RegistryCreator();

        this.contr = new Controller(externalSystems, printer);
        this.view = new View(contr);

        outContent = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outContent));
    }
    @AfterEach
    void tearDownClass(){
        printer = null;
        externalSystems = null;
        contr = null;
        view = null;
        System.setOut(originalOut);
    }

    @Test
    void testRunFakeExecutionContainsError() {
        view.runFakeExecution();
        String output = outContent.toString();
        assertTrue(output.contains("Invalid Item Id"), "Error message missing");
    }

    @Test
    void testRunFakeExecutionContainsItem1ItemDescriptions() {
        view.runFakeExecution();
        String output = outContent.toString();
        assertTrue(output.contains("Item ID: 1"), "First line incorrect");
        assertTrue(output.contains("Item name: Mjölk"), "Second line incorrect");
        assertTrue(output.contains("Item cost: 150.0 SEK"), "Third line incorrect");
        assertTrue(output.contains("VAT : 15.0%"), "Fourth incorrect");
        assertTrue(output.contains("Item description: Arla laktosfri mjölk"), "Fifth line incorrect");
        assertTrue(output.contains("Running total: 172.5 SEK"), "Sixth line incorrect");
        assertTrue(output.contains("Total VAT: 22.5 SEK"), "Seventh line incorrect");
    }

    @Test
    void testRunFakeExecutionContainsAmountToPay() {
        view.runFakeExecution();
        String output = outContent.toString();
        assertTrue(output.contains("Amount to pay: 450.0"), "Amount to pay is incorrect or missing");
    }

    @Test
    void testRunFakeExecutionContainsChange() {
        view.runFakeExecution();
        String output = outContent.toString();
        assertTrue(output.contains("Change to give customer: 550.0"), "Change incorrect or missing");
    }

}
