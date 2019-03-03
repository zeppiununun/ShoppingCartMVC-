package shoppingCartMVC;
import java.util.ArrayList;

/* the main class respresenting the data structure of the project
 * model of the shopping cart + test collection of products
 */

public class DataModel{
public ShoppingCart shoppingCart;
private ArrayList<Product> productList= new ArrayList<Product>();

public DataModel(){
	getDataFromSomeSource();
	
}

//get a product id by an index in productList
public int getProductId(int index) throws Exception {
try {
	return(productList.get(index).getId());
}
catch(IndexOutOfBoundsException e){
    throw new Exception("index out of bounds");
 }
}

/*searches for a product object by its id
 *  @return Product object, @return=null if not found
 */ 
public Product searchProductbyId(int id){
	for(Product q:productList)	{
		if (q.getId()==id)
			return(q);			
	}  
	return(null);
}

//adds a product to a product list
public void addProduct(Product p) throws Exception
{
	if (searchProductbyId(p.getId())==null)
		productList.add(p);
	else 
		throw new Exception("A duplicate product with id=" + Integer.toString((p.getId())));
}

//adds a  range(array) of  products to the product list
public void addProductRange(Product[] plist) throws Exception{
	for(Product q:plist)	
		addProduct(q);		
	  }

/* gets the info about a product by its id
 * @return: product's name, price, description
 */
public Object[] getProductInfobyId(int id) throws Exception{
	Product p=searchProductbyId(id);
	if (p!=null)
		return new Object[]{p.getName(),p.getPrice(), p.getDescription()};
	else
		throw new Exception("The item is not found");
}

//returns the number of products in the product list
public int getProductCollectionSize() {
	return(productList.size());
}

//returns the number of products in the shopping card
public int getShoppingCartSize() {
	return(productList.size());
}

/*initializes the data model with some test data  
 * adds some products to the product list
 * creates an empty shopping cart
 */
public void getDataFromSomeSource(){
	Product p1=new Product(1,"Ware1","Top Qualität!", 28.99);
	Product p2=new Product(2,"Ware2", "Funktional.", 19.95);
	Product p3=new Product(3,"Ware3", "Schnäpchen!", 5.90);
	Product p4=new Product(4,"Ware4", "Jetzt zugreifen!", 2.98);
	Product p5=new Product(5,"Ware5", "Nur heute!", 87.98);
	Product p6=new Product(6,"Ware6", "Einfach unschlagbar!", 57.95);
	try {
		addProductRange(new Product[]{p1,p2,p3,p4,p5,p6});
	} 
	catch (Exception e) {
		e.printStackTrace();
	}
	shoppingCart= new ShoppingCart(4032019);
  }
};

