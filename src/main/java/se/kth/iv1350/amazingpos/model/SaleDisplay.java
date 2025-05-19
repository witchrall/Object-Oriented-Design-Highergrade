package se.kth.iv1350.amazingpos.model;

public abstract class SaleDisplay implements SaleObserver{
    private double totalRevenue;
    
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
