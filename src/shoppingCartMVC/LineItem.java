package shoppingCartMVC;

/**
 * represents Lineitem in the shopping cart, i.e. a product with quantity
 */
public class LineItem {
	private Product p;
	private int quantity = 1;

	public LineItem(Product q, int num) throws Exception {
		if (num <= 0)
			throw new NumberFormatException("inkorrekte Anzahl");
		setProduct(q);
		setQuantity(num);
	}

	public LineItem(Product q) {
		setProduct(q);
	}

	void changeQuantity(int count) throws Exception {
		setQuantity(getQuantity() + count);

	}

	/**
	 * sum for a line item
	 * 
	 * @return
	 */
	double getTotalItem() {
		return (p.getPrice() * getQuantity());
	}

	/**
	 * setter/getter methods
	 */
	public Product getProduct() {
		return p;
	}

	public void setProduct(Product p) {
		this.p = p;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) throws Exception {
		if (quantity > 0)
			this.quantity = quantity;
		else
			throw new Exception("das Produktmenge ist inkorrekt");
	}
}