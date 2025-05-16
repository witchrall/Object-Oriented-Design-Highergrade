package se.kth.iv1350.amazingpos.model;


/**
 * Represents an certain quantity of items in the sopping cart
 */
public class ItemInCart {
    private ItemDTO item;
    private int quantity;

    /**
     * Creates a new instance of {@link item} in cart with a certain {@link quantity}.
     * @param item The item DTO of the item added.
     * @param quantity The quantity of items.
     */
    public ItemInCart(ItemDTO item, int quantity){
        this.item = item;
        this.quantity = quantity;
    }
    
    /**
     * Adds {@link quantity} of the item in the cart.
     * @param quantity The quantity to add.
     */
    public void addQuantity(int quantity){
        this.quantity += quantity;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public ItemDTO getItemDTO(){
        return this.item;
    }

}
