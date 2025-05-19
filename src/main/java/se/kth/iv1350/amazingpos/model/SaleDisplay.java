package se.kth.iv1350.amazingpos.model;


/**
 * A template class for printing total revenue.
 */
public abstract class SaleDisplay implements SaleObserver{
    private double totalRevenue;

    /**
     * notifies the observers about a sale and prints the total revenue.
     * @param sale The current sale.
     */
    @Override   
    public void notifyObserver(Sale sale){
        addToTotalRevenue(sale.getFinalAmount());
    }


    private void addToTotalRevenue(double amount){  
        this.totalRevenue += amount;
        showTotalIncome();
    }

    private void showTotalIncome() {
        try { 
            doShowTotalIncome(this.totalRevenue);
        } catch (Exception e) { 
            handleErrors(e); 
        } 
    }

    protected abstract void doShowTotalIncome(double income) throws Exception; 
    protected abstract void handleErrors(Exception e);

}
