
package se.kth.iv1350.amazingpos.model;

/**
 *  Represents the cash register.
 * 
 */
public class CashRegister {
    
    private static final CashRegister CASH_REGISTER = new CashRegister(); 
    private double balance; 

    /**
     * Creates a new instance of cash register.
     */
    private CashRegister(){
        this.balance = 5000;

    }

    public static CashRegister getCashRegister(){ 
        return CASH_REGISTER;  
    }


    


    



    /**
     * Updates the balance in the cashregister given a {@link payment}.
     * @param payment   The information about how much has been paid.
     */
    public void updateCashRegister(Payment payment){
        this.balance += payment.getPaidAmount() - payment.getChange();
    }

    public double getBalance(){
        return this.balance;
    }



}
