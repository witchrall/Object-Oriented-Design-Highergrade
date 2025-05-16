
package se.kth.iv1350.amazingpos.integration;

import se.kth.iv1350.amazingpos.model.SaleDTO;

/**
 *
 * Includes data about accounting information
 */
public class ExternalAccountingSystem {
    private static final ExternalAccountingSystem EXTERNAL_ACCOUNTING_SYSTEM = new ExternalAccountingSystem();

    public static ExternalAccountingSystem getExternalAccountingSystem(){
        return EXTERNAL_ACCOUNTING_SYSTEM; 
    }

    private ExternalAccountingSystem(){

    } 

    /**
     * Takes a {@link paidSale} and sends it to an external system to update said system.
     * @param paidSale  The final sale data.
     */
    public void updateExternalAccountingSystem(SaleDTO paidSale){

    } 

   

    

}
