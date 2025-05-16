package se.kth.iv1350.amazingpos.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.amazingpos.integration.Printer;
import se.kth.iv1350.amazingpos.integration.RegistryCreator;

public class CashRegisterTest {

    private RegistryCreator exSystems;
    private Printer printer;
    private Sale testSale;
    private int validItemIdentifier = 1;
    private int quantity = 2;
    private double paidAmountMore = 425;
    private double paidAmountExact = 345;
    private Payment payment;
    private PaymentStrategy paymentType = new CashPayment();

    
    @BeforeEach
    void setUp(){
        exSystems = new RegistryCreator();
        printer = new Printer();
        testSale = new Sale(exSystems, printer);
        testSale.registerItem(validItemIdentifier, quantity);
        testSale.endSale();
        payment = new Payment(paidAmountMore, paymentType);
        payment.calculateChange(testSale);
    }

    @AfterEach
    public void tearDown() {
        printer = null;
        testSale = null;
        payment = null;
    }




    @Test
    void testUpdateCashRegister() {
        double preChangeBalance =  exSystems.getCashRegister().getBalance();
        exSystems.getCashRegister().updateCashRegister(payment);
        double result =  exSystems.getCashRegister().getBalance() - preChangeBalance;
        
        double expectedResult = 345;

        assertEquals(expectedResult, result, "Cash register is not correct");
    }
}
