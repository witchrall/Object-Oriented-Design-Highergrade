package se.kth.iv1350.amazingpos.model;

/**
 * A payment strategy where the customer pays by credit card.
 */
public class CreditCardPayment implements PaymentStrategy {
    /**
     * Calculates the change amount to be paid to the customer
     * 
     * @param amountToPay The amount to be paid.
     * @param paidAmount The amount paid by customer
     * @return As the payment is done by card the change amount will always be 0.
     */
    @Override
    public double calculateChange(double amountToPay, double paidAmount){
        return 0; 
    }

}
