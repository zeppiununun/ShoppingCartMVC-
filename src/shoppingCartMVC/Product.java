package shoppingCartMVC;

/*a minimal class modelling the shop items 
 * kept as small as possible
 * may be extended with some other info as pictures of products
 * VAT(MwSt) rate groups, classifiers, etc.
 */

public class Product {
	private int id; //unique identifier
	private String name;
	private String description;
	private double price;
    final static double maxprice= 10000000; //the maximal possible price for an item 
	
	
	public Product(int id, String name, String desc, double price)
	{
		this.id=id;
		this.name=name;
		this.description=desc;
		this.price=price;
	}
		
	/*checks if a floating point number represents an item price 
	 * @true if it has no more than 2 decimal digits and 0<= n<=maxprice 
	 */
	
     static boolean isPriceAdmissible(double number){
    	 
    	 if ((number < 0)||(number>maxprice)||(100*number == Math.round(100*number))) 
    		 return false;
    	 
    	return(true);    		 
	}
     
     
      /* getter/setter methods
      */
     
     public void setName(String nm){
 		name = nm;
 	}
 	
 	public String getName(){
 		return(name);
 	}
     
     public void setPrice(double price) throws NumberFormatException{
 		if (isPriceAdmissible(price))
 			this.price = price;
 		else 
 			throw new NumberFormatException("Unzulässiger Preis");
 	}
 	
 	public double getPrice(){
 		return(price);
 	}
     
 	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
		
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return(id);
	}
	
}
