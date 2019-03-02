package shoppingCartMVC;
import java.util.Hashtable;

public class Controller implements CommandListener{
	private DataModel dataModel=new DataModel();
	private static View view;
	
  	public void InitializeView() throws Exception {
	  view=new View(this, retrieveProductData());
	}
  
	public void UpdateView() throws Exception {
		view.UpdateView(retrieveShoppingCartData());

	}
	
	public Object[] doActionOld(int actionCode, Object[] args) throws Exception {
		switch (actionCode) {
        case 0:  return(getProductInfobyId((int)args[0]));
		case 1:  RemoveItemFromShoppingCart((int)args[0]);
				 return(null);
        case 2:  UpdateItemQty((int)args[0],(int)args[1]);
                 return(null);
        case 3:  getCalculation();
                 return(null);
        case 4:  AddItemToShoppingCart((int)args[0]);
                 return(null);
        case 5:  setDiscounts((double)args[0],(double)args[1]);
        		 return(null);
        case 6:  return(getDiscounts());
          
        default: return null;
		}		
	
	}
	
	public Object[] doAction(ActionCode actionCode, Object[] args) throws Exception {
		switch (actionCode) {
        case getProductInfobyId:  return(getProductInfobyId((int)args[0]));
		case RemoveItemFromShoppingCart:  RemoveItemFromShoppingCart((int)args[0]);
				 return(null);
        case UpdateItemQuantity:  UpdateItemQty((int)args[0],(int)args[1]);
                 return(null);
        case getCalculation:  getCalculation();
                 return(null);
        case AddItemToShoppingCart:  AddItemToShoppingCart((int)args[0]);
                 return(null);
        case setDiscounts:  setDiscounts((double)args[0],(double)args[1]);
        		 return(null);
        case getDiscounts:  return(getDiscounts());
          
        default: return null;
		}		
	
	}
		
	public Object[][] retrieveShoppingCartData() throws Exception{
		int sz=dataModel.shoppingCart.getShoppingCartSize();
		Object[][] shoppingCartItems= new Object[sz][3];
		
		for(int  i=0; i<sz; ++i){
			Object[] pos= dataModel.shoppingCart.getLineItemInfo(i);
			shoppingCartItems[i][0]=pos[0];
			shoppingCartItems[i][1]=pos[1];
			shoppingCartItems[i][2]=pos[2];			
		}
		
		return(shoppingCartItems);
		
	}
	
	public Object[] retrieveProductData() throws Exception	{
		Object[] productIds=new Object[dataModel.getProductCollectionSize()];
		
		for(int i=0; i<dataModel.getProductCollectionSize(); i++)
			productIds[i]=dataModel.getProductId(i);
		return(productIds);
	}
	
    public void getCalculation() throws Exception	{
    	Hashtable<String, Double> total= dataModel.shoppingCart.getTotal();
    	view.addTotal(total);
    	
	}
    
    
    public Object[] getDiscounts() {
    	return(new Object[]{dataModel.shoppingCart.getDiscount_abs(), dataModel.shoppingCart.getDiscount_rel()});
    	   }
    
    public void setDiscounts(double discount_abs, double discount_rel) throws Exception {
    	
    	dataModel.shoppingCart.setDiscount_abs(discount_abs);
    	dataModel.shoppingCart.setDiscount_rel(discount_rel);
    	UpdateView();
    	
    }
    
	
	public Object[] getProductInfobyId(int id) throws Exception {
			Object[] productInfo= dataModel.getProductInfobyId(id);
	
			return productInfo;
	}
	
	public void AddItemToShoppingCart(int id) throws Exception {
		
		dataModel.shoppingCart.AddItem(dataModel.searchProductbyId(id));
		UpdateView();
		
	}
	
	public void RemoveItemFromShoppingCart(int id) throws Exception {
		
		dataModel.shoppingCart.removeItem(dataModel.searchProductbyId(id));
		UpdateView();
		//dataModel.shoppingCart.RemoveItem(dataModel.searchProductbyId(id), quantity);
		
	}
	
	public void UpdateItemQty(int id, int newquantity) throws Exception {
		if (newquantity==0)
			dataModel.shoppingCart.removeItem(dataModel.searchProductbyId(id));
		else
			dataModel.shoppingCart.updateQuantity(dataModel.searchProductbyId(id), newquantity);
		
		UpdateView();		
	}
	
	
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
