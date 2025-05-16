package se.kth.iv1350.amazingpos.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import se.kth.iv1350.amazingpos.integration.DataBaseException;
import se.kth.iv1350.amazingpos.integration.Printer;
import se.kth.iv1350.amazingpos.integration.RegistryCreator;


/**
 * One single sale made by one single customer and paid with one payment. 
 */
public class Sale {
    private List<SaleObserver> observers = new ArrayList<>();
    private ShoppingList shoppingCart;
    private double runningTotal;
    private LocalTime saleTime;
    private double vat;
    private double finalAmount;
    private Receipt receipt;
    private RegistryCreator externalSystems;
    private Printer printer;
    private Payment payment;
    private DiscountsComposite discountComposite = new DiscountsComposite();
    
            
    /**
     * Creates a new instance of sale with {@link exSystems} and {@link printer}.
     * @param exSystems The external systems.
     * @param printer   The printer.
     */
    public Sale(RegistryCreator exSystems, Printer printer){
        this.shoppingCart = new ShoppingList();
        this.externalSystems = exSystems;
        this.printer = printer;
        this.saleTime = LocalTime.now();
        this.vat = 0;
        this.finalAmount = 0;
        this.runningTotal = 0;

    }

    /**
     * Registers an item to be purchased.
     * @param itemIdentifier The identifier for each item represented as an integer.
     * @param quantity Number of Items to be registered.
     * @return An object  containing all indormation about an item.
     * @throw  DataBaseException if can not connect to the Database.
     */
    public SaleDTO registerItem(int itemIdentifier, int quantity){

        ItemDTO item;
        try {
            item = externalSystems.getExternalInventorySystem().lookupItem(itemIdentifier);

        } catch (DataBaseException dataBaseError){
            throw dataBaseError;
        }
    
        

        if(item != null){
            updateShoppingCart(item, quantity);
            updateRunningTotal(item, quantity);
        }

        SaleDTO currentSale = new SaleDTO(this, item);


        return currentSale; 
    }
    /**
     * Checks for discount and if customer is eligible it applies the rate of discount on the runningTotal and updates the price.
     * @param customerID unique int assigned to customer for identification
     * @return Returns a SaleDTO with runningTotal updated with the discount if the customer is eligible
     *         if not eligible, the runningTotal remains unchanged.
     */
    public SaleDTO checkForDiscount(int customerID){ 
        SaleDTO currentSale = new SaleDTO(this);
        // Replace with calculatediscount composite stratergy
        //DiscountDTO totalDiscount = externalSystems.getDiscountDataBase().searchForDiscount(currentSale, customerID);
        DiscountStrategy customerIDDiscount = new CustomerIDDiscount();
        DiscountStrategy itemDiscount = new ItemDiscount();
        DiscountStrategy totalCostDiscount = new TotalCostDiscount();

        discountComposite.addDiscountStrategies(customerIDDiscount);
        discountComposite.addDiscountStrategies(itemDiscount);
        discountComposite.addDiscountStrategies(totalCostDiscount);

        double totalDiscount = discountComposite.calculateDiscount(customerID, currentSale, externalSystems);
        applyDiscount(totalDiscount);
        SaleDTO updatedSale = new SaleDTO(this); 

        return updatedSale;

    }

    /**
     * Ends the sales process.
     * @return The final amount to be paid
     */
    public double endSale(){
        this.finalAmount = runningTotal;
        
        return finalAmount;
    }
    /**
     * The payment process. It calculates how much has been paid, how much to get back in change, 
     * and also updates the external systemst that need said information.
     * @param payment   The payment information.
     * @return  The change to get back.
     */
    public double pay(Payment payment){
        this.payment = payment;
        this.payment.calculateChange(this); 
        //based on the requirement specification the change will either be zero or a positive amount, 
        //therefore the customer won't be asked to pay more as there are no negative changes

        SaleDTO paidSale = new SaleDTO(this);
        externalSystems.getExternalAccountingSystem().updateExternalAccountingSystem(paidSale); 
        externalSystems.getExternalInventorySystem().updateExternalInventorySystem(paidSale); 
        
        notifyObservers(this);
        return payment.getChange();
    }

    /**
     * Prints the receipt to prove the sale.
     */
    public void printReceipt(){
        receipt = new Receipt(this);
        printer.printReceipt(receipt);
    }

    /**
     * Applies the discount to the sales runningTotal.
     * @param totalDiscount The total percentage of discount the customer is eligible for.
     */
    private void applyDiscount(double totalDiscount){
        
        double finalPriceAfterDiscount = this.runningTotal - this.runningTotal*totalDiscount;
        this.runningTotal = finalPriceAfterDiscount;

        double finalVATAfterDiscount = this.vat - this.vat*totalDiscount;
        this.vat = finalVATAfterDiscount;

    }

    /**
     * Adds a specified quantity of an item to the ShoppingCart.
     * @param item Item to be added to the ShoppingCart.
     * @param quantity The quantity of said item to be added.
     */
    private void updateShoppingCart(ItemDTO item, int quantity){
        shoppingCart.addToShoppingList(item, quantity);
    }
    /**
     * Calculates and updates the RunningTotal of the Sale.
     * @param item Item to be bought.
     * @param quantity Quantity of the item to be bought.
     */
    private void updateRunningTotal(ItemDTO item, int quantity){

        double vatToPayItem = updateVat(item, quantity);
        double totalItemPrice = item.getPrice()*quantity + vatToPayItem;
        
        runningTotal += totalItemPrice;
    }
    

    /**
     * Calculates and updates the total vat to be paid for the sale and for each item.
     * @param item Item for which the vat is to be calculated
     * @param quantity Quantity of the item
     * @return Returns the amount of vat to be paid for an item multiplied with the quantity. 
     */
    private double updateVat(ItemDTO item, int quantity){
        double vatToPayForItem = item.getPrice()*quantity*item.getVatRate();
        vat += vatToPayForItem;
        return vatToPayForItem;
    }

    /**
     * Adds observers to Sale
     * @param observer the added observer
     */
    public void addObserver(List<SaleObserver> observers){
        this.observers = observers;
    }

    private void notifyObservers(Sale sale){
        for(SaleObserver observer: observers){ 
            observer.notifyObserver(sale);
        }
    }
    

    public ShoppingList getShoppingCart(){
        return shoppingCart;
    }

    public double getRunningTotal(){
        return runningTotal;
    }

    public LocalTime getSaleTime(){
        return saleTime;
    }
    
    public double getVat(){
        return vat;
    }

    public double getFinalAmount(){
        return finalAmount;
    }

    public Receipt getReceipt(){
        return receipt;
    }

    public Payment getPayment() {
        return payment;
    }

}


