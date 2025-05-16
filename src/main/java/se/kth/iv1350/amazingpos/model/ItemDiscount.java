package se.kth.iv1350.amazingpos.model;

import se.kth.iv1350.amazingpos.integration.*;

/**
 * A class to handle the implimentation of discounts relating only to the customers chosen items.
 */
public class ItemDiscount implements DiscountStrategy {
    private double discount;
    DiscountDTO totalDiscount;

    /**
     * Calculates the discount based on the the items in the cart
     * @param customerID The customers ID
     * @param saleDTO The saleDTO containing the information about the sale
     * @param exSystems The external systems from where to get the discount information
     * @return The discount based on the cart
     */
    @Override
    public double calculateDiscount(int customerID, SaleDTO saleDTO, RegistryCreator exSystem){
        DiscountDataBase discountDatabase = exSystem.getDiscountDataBase();

        totalDiscount = discountDatabase.searchForDiscount(saleDTO, customerID);
        discount = totalDiscount.getItemDiscount();
        return discount;
        
    }

}
