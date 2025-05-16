package se.kth.iv1350.amazingpos.model;

import se.kth.iv1350.amazingpos.integration.RegistryCreator;
import java.util.*;
/**
 * A Discount composite that handles the different types of discounts and gives a total discount
 */
public class DiscountsComposite implements DiscountStrategy{
    double totalDiscount;
    private List<DiscountStrategy> strategyList = new ArrayList<>();

    /**
     * Adds the defined discount types
     * @param discountType  The discount type to be added.
     */
    void addDiscountStrategies(DiscountStrategy discountType){
        this.strategyList.add(discountType);
    }

    /**
     * Calulates the total discounts from all different types of discounts added.
     * @param customerID The customer Identifier.
     * @param saleDTO The information about the current sale.
     * @param exSystems The external systems from where the information is gotten.
     * @return The total discount as a decimal number.
     */
    @Override
    public double calculateDiscount(int customerID, SaleDTO saleDTO, RegistryCreator exSystem){

        for(DiscountStrategy discount : strategyList){
            totalDiscount += discount.calculateDiscount(customerID, saleDTO, exSystem);
        }
        return totalDiscount;
    }

}
