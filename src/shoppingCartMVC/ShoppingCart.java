package shoppingCartMVC;
import java.util.ArrayList;
import java.util.Hashtable;

/* represents basic data and functionality of a shopping cart
 * 
 */

public class ShoppingCart {
    private int orderId;   //a unique identifier
    private int customerId=-1;//no customer account assigned by default (e.g. not signed in Internet users)
    private ArrayList<LineItem> itemList= new ArrayList<LineItem>();
	private double discount_rel=0;
	private double discount_abs=0; 

		
	public ShoppingCart(int orderId){
		this.orderId = orderId;
	}
	
	/*searches for a Product object in the shopping cart
	 * @return=-1, if not found
	 * otherwise returns its index in the itemList  
	 */
	
	int searchItem(Product p)
	{
		for(LineItem q:itemList)
		{ //the product is there
			if (q.getProduct().getId()==p.getId())
				return(itemList.indexOf(q));			
		}
		return(-1);		
	}
	
	/* adds a neu product to the shopping cart
	 * @count quantity
	 */
	public void AddItem(Product p, int count) throws Exception
	{  
	  int r=searchItem(p);
	  
	  if (r==-1) //kreiert neuer Posten für das Produkt nicht im Korb
		  itemList.add(new LineItem(p, count));
	  else   // erhöht die Menge für das Produkt im Korb
		  itemList.get(r).ChangeQuantity(count);	  
	}
	
	/* adds a neu product to the shopping cart
	 * only one item
	 */
	public void AddItem(Product p) throws Exception
	{  
		AddItem(p, 1); 
	}
	
	/* Either removes a product completely from the shopping cart(count= its quantity)
	 * or reduces its quantity by count 
	 */
	
	public void RemoveItem(Product p, int count) throws Exception
	{  
	  int index=searchItem(p);
	  
	  if (index==-1) return; //not found, nothing to remove
	  
	  int qnt= itemList.get(index).getQuantity();
	  
	 //insufficient quantity available for removal
	  if (qnt < count) 
		  throw new Exception("inkorrekte Menge zu entfernen");
	  	  
	  if (qnt > count) 
		  itemList.get(index).ChangeQuantity(-count);
	  else   
		  itemList.remove(index);
	}
	/*
	 * removes a product completely from the shopping cart
	 */
	public void removeItem(Product p)
	{
		int index=searchItem(p);
		itemList.remove(index);
	}
	
	/* 
	 * setting a new quantity for the given product 
	 */
	
	public void updateQuantity(Product p, int newquantity) throws Exception
	{
		int index=searchItem(p);
		itemList.get(index).setQuantity(newquantity);
	}
	
	/* get a sum of the shopping cart + discounts
	 * @return Hashtable
	 * key: "brutto" the sum without discounting
	 * key: "discount_rel_abs"  % discount in Euro
	 * key: "discount_abs" absolute discount 
	 * key: "discount"  % discount + absolute discount
	 * key: "netto" discounted sum
	 * key: "cart_size" a number of items in the shopping cart
	 * key: "discount_rel_abs"  % discount in %
	 */ 
		
	public Hashtable<String, Double> getTotal(){
		
		Hashtable<String, Double> total= new Hashtable<String, Double>();
		double sum=0;
		for(LineItem q:itemList)
				sum+= q.getTotalItem();
		
		total.put("brutto", sum);
		double discount_rel_abs=(sum*discount_rel)/100; 
		total.put("discount_rel_abs",discount_rel_abs);
		total.put("discount_abs", discount_abs);
		double discount= discount_abs + discount_rel_abs;
		total.put("discount",discount);
		total.put("netto", sum - discount);
		total.put("discount_rel", discount_rel);
		total.put("cart_size",(double)getShoppingCartSize());
		
		return(total);	
	}
	
	//a number of items in the shopping cart 
	public int getShoppingCartSize()
	{
		return(itemList.size());
	}
	
	/* returns the info about an item line 
	 * @return Object[0] id of a product
	 * @return Object[1] qty
	 * @return Object[2] a total price per the line item
	*/
	
		public Object[] getLineItemInfo(int index) throws Exception
	{
		try {
		LineItem lI =itemList.get(index); 
		
		return new Object[] {lI.getProduct().getId(), lI.getQuantity(),lI.getTotalItem()};
		
		}
		catch(IndexOutOfBoundsException e){	
			 throw new Exception("The index is out of bounds");
		}
	}
	
	/*
	 * Getter/Setter methods
	 */
	
	
	public double getDiscount_abs() {
		return discount_abs;
	}
	
	public void setDiscount_abs(double discount_abs) {
		this.discount_abs = discount_abs;
	}

	public double getDiscount_rel() {
		return discount_rel;
	}

	public void setDiscount_rel(double discount_rel) {
		this.discount_rel = discount_rel;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
 }
	

