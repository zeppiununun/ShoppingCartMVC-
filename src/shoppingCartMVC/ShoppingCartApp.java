package shoppingCartMVC;
/*
 * the entry point
 */
public class ShoppingCartApp {

	public static void main(String[] args) {
		
		Controller controller=new Controller();
				
		try {
		controller.InitializeView();
		controller.UpdateView();
			}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
						
	}
}
