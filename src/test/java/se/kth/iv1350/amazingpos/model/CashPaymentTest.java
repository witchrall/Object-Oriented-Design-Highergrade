package se.kth.iv1350.amazingpos.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CashPaymentTest {
    private double paidAmount = 1961;
    private double amountToPay = 345;
    private double paidAmountExact = 345;
    private PaymentStrategy pamynetType = new CashPayment();

    @Test
    void testCalculateChangePaidMore() {
        double result = pamynetType.calculateChange(amountToPay, paidAmount);
        double expectedResult = paidAmount - amountToPay;

        assertEquals(expectedResult, result, "Change doesn't match");
    }

    @Test
    void testCalculateChangePaidExact() {
        double result = pamynetType.calculateChange(amountToPay, paidAmountExact);
        double expectedResult = 0;

        assertEquals(expectedResult, result, "Change doesn't match");
    }
}
