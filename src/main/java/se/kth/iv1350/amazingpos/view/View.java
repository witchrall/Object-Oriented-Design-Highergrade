
package se.kth.iv1350.amazingpos.view;

import se.kth.iv1350.amazingpos.controller.Controller;
import se.kth.iv1350.amazingpos.integration.DataBaseException;
import se.kth.iv1350.amazingpos.model.CashPayment;
import se.kth.iv1350.amazingpos.model.CreditCardPayment;
import se.kth.iv1350.amazingpos.model.InvalidItemException;
import se.kth.iv1350.amazingpos.model.ItemDTO;
import se.kth.iv1350.amazingpos.model.PaymentStrategy;
import se.kth.iv1350.amazingpos.model.SaleDTO;


/**
 * This is a placeholder for the real view 
 * All inputs to the system are handled via view.It contains hard-coded execution
 * with calls to all system operations in controller.
 * 
 */
public class View {
    private PaymentStrategy paymentType;
    private Controller contr;


    private int  quantity = 1;

    private double paidAmount = 1000;


    /**
     * Creates a new instance which uses the specified controller for all calls to other layers
     * @param contr The controller used for all calls to other layers
     */
    public View(Controller contr){
        this.contr = contr;
        contr.addObserver(new TotalRevenueFileOutput());
        contr.addObserver(new TotalRevenueView());
    }
    /**
     * Performs a fake sale, by calling all system operations in controller, as we do not have any real view implementation.
     */
    public void runFakeExecution(){


        contr.startSale();
        System.out.println("   **A new sale has been started** ");
        System.out.println("");

        SaleDTO currentSale;
        
        for(int i = 0; i <= 4; i++){
            try{
                currentSale =  contr.addItem(i, quantity);
                ItemDTO currentItem =  currentSale.getLastRegisteredItem();
                System.out.println("Item ID: " + i);
                System.out.println("Item name: " + currentItem.getName()); 
                System.out.println("Item cost: " + currentItem.getPrice() + " SEK"); 
                System.out.println("VAT : " + currentItem.getVatRate()*100 + "%"); 
                System.out.println("Item description: " + currentItem.getItemDescription());
                System.out.println(""); 
                System.out.println("Running total: " + currentSale.getRunningTotal() + " SEK");
                System.out.println("Total VAT: " + currentSale.getTotalVat() + " SEK"); 

                System.out.println(""); 
                System.out.println(""); 
            } catch (InvalidItemException error) {
                System.out.println(error.getMessage()); 
            }
            catch (DataBaseException error){
                System.out.println(error.getMessage());
            }
            catch (Exception error){
                System.out.println("Couldn't register item, please try again");
            }
            
        }
        
                

        System.out.println(""); System.out.println("");System.out.println("");
         
        double amountToPay = contr.concludeSale();
        System.out.println("Amount to pay: " +  amountToPay);
        
        int paymentChoice = 1;
        if(paymentChoice == 1){
            paymentType = new CashPayment();
        } else {
            paymentType = new CreditCardPayment();
            paidAmount = amountToPay;
        }
       

        double change = contr.pay(paidAmount, paymentType);
        System.out.println("--------------End of receipt-------------------"); 

        System.out.println(""); System.out.println(""); System.out.println(""); 

        System.out.println("Change to give customer: " +  change);
        
    }
}
