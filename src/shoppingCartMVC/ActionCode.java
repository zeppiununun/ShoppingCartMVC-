package shoppingCartMVC;

//enumeration of the controller commands executed by View

public enum ActionCode {
getProductInfobyId(0),RemoveItemFromShoppingCart(1), UpdateItemQuantity(2), 
getCalculation(3), AddItemToShoppingCart(4),setDiscounts(5), getDiscounts(6);
	
	private final int code;

	private ActionCode(int code) {
    this.code = code;
	}
	
	public int getCode() {
    return code;
	}

}

