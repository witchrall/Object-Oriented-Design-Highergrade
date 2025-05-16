package se.kth.iv1350.amazingpos.model;



/**
 * A payment strategy where the customer pays by cash.
 */
public class CashPayment implements PaymentStrategy{
    /**
     * Calculates the change amount to be paid to the customer
     * @param amountToPay The amount to be paid.
     * @param paidAmount The amount paid by customer
     * @return the change to give back to the customer.
     */
    @Override
    public double calculateChange(double amountToPay, double paidAmount){
        return paidAmount-amountToPay;
    }


}
