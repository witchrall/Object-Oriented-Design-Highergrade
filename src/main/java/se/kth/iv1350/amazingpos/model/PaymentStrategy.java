package se.kth.iv1350.amazingpos.model;


/**
 * Defines the ability to pay by different payment methods
 * This interface shall be implemented by classes that define the payment strategy
 */
public interface PaymentStrategy {
    public double calculateChange(double amountToPay, double paidAmount);

    
}
