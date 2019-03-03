package shoppingCartMVC;
import java.util.Hashtable;

/*
 * the class that settles the application logic    
*/

	public class Controller implements CommandListener{
	private DataModel dataModel=new DataModel();
	private View view;
	
	/*renders the unchangeable part of the View (GUI), 
	 * which  is independent of user interaction */
  	public void InitializeView() throws Exception {
	  view=new View(this, retrieveProductData());
	}
  
  	
  	/* Updates the View, part of the interface CommandListener
  	 * @see shoppingCartMVC.CommandListener#UpdateView()
  	 */
	public void UpdateView() throws Exception {
		view.UpdateView(retrieveShoppingCartData());

	}
	
	/* Provides the  main interactions between the View and the Data Model 
	 * part of the interface CommandListener
	 * @see shoppingCartMVC.CommandListener#doAction(shoppingCartMVC.ActionCode, java.lang.Object[])
	 */
	
	public Object[] doAction(ActionCode actionCode, Object[] args) throws Exception {
		switch (actionCode) {
        case getProductInfobyId:  return(getProductInfobyId((int)args[0]));
		case RemoveItemFromShoppingCart:  RemoveItemFromShoppingCart((int)args[0]);
				 return null;
        case UpdateItemQuantity:  UpdateItemQty((int)args[0],(int)args[1]);
                 return null;
        case getCalculation:  getCalculation();
                 return null;
        case AddItemToShoppingCart:  AddItemToShoppingCart((int)args[0]);
                 return null;
        case setDiscounts:  setDiscounts((double)args[0],(double)args[1]);
        		 return null;
        case getDiscounts:  return(getDiscounts());
          
        default: return null;
		}	
	}
	/* Retrieves the information about a product by its id
	 *  id is a unique identifier of a product. It is stored in
	 * the hidden fields of the GUI components
	 */
	public Object[] getProductInfobyId(int id) throws Exception {
		Object[] productInfo= dataModel.getProductInfobyId(id);

		return productInfo;
	}
	
	/* (completely) removes a line item from the shopping cart
	 */
	public void RemoveItemFromShoppingCart(int id) throws Exception {
		
		dataModel.shoppingCart.removeItem(dataModel.searchProductbyId(id));
		UpdateView();
				
	}
	
		
	/* Updates the product quantity in the shopping cart
	 * 
	 */
	public void UpdateItemQty(int id, int newquantity) throws Exception {
		if (newquantity==0)
			dataModel.shoppingCart.removeItem(dataModel.searchProductbyId(id));
		else
			dataModel.shoppingCart.updateQuantity(dataModel.searchProductbyId(id), newquantity);
		
		UpdateView();		
	}
	
	/* Performs the calculation in the Data Model 
	 * and renders its results in the View
	 */
	  public void getCalculation() throws Exception	{
	    	Hashtable<String, Double> total= dataModel.shoppingCart.getTotal();
	    	view.addTotal(total);
	    	
		}
	  
	  /* Adds an item to the shopping cart
	  	*/
	  public void AddItemToShoppingCart(int id) throws Exception {
			
			dataModel.shoppingCart.AddItem(dataModel.searchProductbyId(id));
			UpdateView();
			
		}
	  
	  /* Updates the relative &  the absolute discount in the Data Model
	   * 
	   */
	  public void setDiscounts(double discount_abs, double discount_rel) throws Exception {
	    	
	    	dataModel.shoppingCart.setDiscount_abs(discount_abs);
	    	dataModel.shoppingCart.setDiscount_rel(discount_rel);
	    	UpdateView();    	
	    }
	  
	  /* Retrieves the relative & the absolute discount from the Data Model
	   * 
	   */
	    public Object[] getDiscounts() {
	    	return(new Object[]{dataModel.shoppingCart.getDiscount_abs(), dataModel.shoppingCart.getDiscount_rel()});
	    	   }
	  
	 /* Retrieves the ids of all Products in the Data Model
	  * @returns a 1-dim array containing the ids
	  */   
	    
	public Object[] retrieveProductData() throws Exception	{
		Object[] productIds=new Object[dataModel.getProductCollectionSize()];
		
		for(int i=0; i<dataModel.getProductCollectionSize(); i++)
			productIds[i]=dataModel.getProductId(i);
		return(productIds);
	}
	
  
	/* Retrieves the info  about the line items in the shopping cart
	 * @returns a 2-dim array whose rows contains product id, 
	 * product quantity and  their product(for the MVC pattern purity reasons 
	 * this multiplication is performed in the Data Model:)
	 */
	
	public Object[][] retrieveShoppingCartData() throws Exception{
		int sz=dataModel.shoppingCart.getShoppingCartSize();
		Object[][] shoppingCartItems= new Object[sz][3];
		
		for(int  i=0; i<sz; ++i){
			//
			Object[] pos= dataModel.shoppingCart.getLineItemInfo(i);
			shoppingCartItems[i][0]=pos[0];
			shoppingCartItems[i][1]=pos[1];
			shoppingCartItems[i][2]=pos[2];			
		}
		
		return(shoppingCartItems);
		
	}
    
    /*
     * Getter/Setter
     */
    
   	public DataModel getDataModel() {
		return dataModel;
	}


	public void setDataModel(DataModel dataModel) {
		this.dataModel = dataModel;
	}


	public View getView() {
		return view;
	}


	public void setView(View view) {
		this.view = view;
	}

}
