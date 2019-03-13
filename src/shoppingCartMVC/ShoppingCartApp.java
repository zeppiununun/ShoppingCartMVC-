package shoppingCartMVC;
/*
 * the entry point
 */
public class ShoppingCartApp {

	public static void main(String[] args) {
		
		Controller controller=new Controller();
				
		try {
		controller.initializeView();
		controller.updateView();
			}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
						
	}
}
