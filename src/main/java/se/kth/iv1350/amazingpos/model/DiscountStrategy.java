package se.kth.iv1350.amazingpos.model;

import se.kth.iv1350.amazingpos.integration.RegistryCreator;

/**
 * Defines the ability to apply different types of discounts
 * This interface shall be implemented by classes that define the discount type
 */
public interface DiscountStrategy {
    public double calculateDiscount(int customerID, SaleDTO saleDTO, RegistryCreator exSystem);
}
