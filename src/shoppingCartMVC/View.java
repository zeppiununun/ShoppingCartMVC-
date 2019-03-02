package shoppingCartMVC;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Hashtable;
import java.awt.event.ActionEvent;

public class View {
	private  CommandListener controller;
	final JFrame guiFrame = new JFrame();
	public JTable cartTable, totalTable;
	final JPanel DiscountPanel=new JPanel();
	final JPanel SCPanel=new JPanel();
	final JPanel TotalPanel=new JPanel();
	final JPanel ProduktPanel = new JPanel();
	final JScrollPane ScrolledProduktPanel = new JScrollPane(ProduktPanel);
	final JButton DiscounBtn=new JButton("Rabatt einstellen");
	final int windowWidth=700;
	final int windowHeight=600;
		

	public View(Controller controller, Object[] productIds) throws Exception
	{
		setController(controller);
					
		//make sure the program exits when the frame closes
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//guiFrame.setJMenuBar(menuBar);
		//DiscountPanel.setLayout(new BoxLayout(DiscountPanel,BoxLayout.X_AXIS));
		DiscountPanel.setAlignmentX(0);
		DiscountPanel.setLayout(new GridLayout(0,4));
		//DiscounBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
	   //
	    // DiscountPanel.setAlignmentY(1);
		DiscountPanel.add(Box.createHorizontalGlue());
		DiscountPanel.add(Box.createHorizontalGlue());
		DiscountPanel.add(Box.createHorizontalGlue());
	    DiscountPanel.add(DiscounBtn);
	    DiscountPanel.setVisible(true);	
	    DiscounBtn.addActionListener(new ActionListener()
		{
		//Adding an item
		public void actionPerformed(ActionEvent event)
		{
			showDiscountDialog();
				
		}});
	    
		SCPanel.setLayout(new BoxLayout(SCPanel, BoxLayout.Y_AXIS));
		
		guiFrame.setTitle("Einkaufskorb");
		guiFrame.setSize(windowWidth,windowHeight);
		//This will center the JFrame in the middle of the screen
		guiFrame.setLocationRelativeTo(null);
		guiFrame.add(DiscountPanel, BorderLayout.NORTH);
		guiFrame.add(SCPanel, BorderLayout.CENTER);
		guiFrame.add(ProduktPanel, BorderLayout.SOUTH);
		ScrolledProduktPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		ScrolledProduktPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		ScrolledProduktPanel.getViewport().setView(ProduktPanel);
		//ScrolledCartPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//ScrolledCartPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		//ScrolledProduktPanel.getViewport().setView(SCPanel);
		guiFrame.add(ScrolledProduktPanel,BorderLayout.SOUTH);
		//guiFrame.add(ScrolledCartPanel,BorderLayout.CENTER);
		
		Object[] productInfo;
		for(int i=0; i<productIds.length; i++)
		{
			//productInfo= controller.getProductInfobyId((int)productIds[i]);
			productInfo=controller.doAction(0, new Object[]{productIds[i]});
			addProduct((int)productIds[i],(String)productInfo[0],(String)productInfo[2],(double)productInfo[1]);
		}
		
		guiFrame.setVisible(true);
	}

	public void UpdateView(Object[][] shoppingCartItems) throws Exception
	{
		
		SCPanel.removeAll();
		SCPanel.updateUI();
		TotalPanel.removeAll();
		TotalPanel.updateUI();
		
		Object[][] shoppingCartData=new Object[shoppingCartItems.length][7];
		Object[] productInfo;
		DecimalFormat decFormat = new DecimalFormat("#0.00");
		for(int i=0; i<shoppingCartItems.length; i++){
			
			//productInfo= controller.getProductInfobyId((int)shoppingCartItems[i][0]);
			productInfo=controller.doAction(0, new Object[]{shoppingCartItems[i][0]});
			
			shoppingCartData[i][0]=i+1;//Position
			shoppingCartData[i][1]=productInfo[0];//Produktname
			shoppingCartData[i][2]=(int)shoppingCartItems[i][0]; //ProduktId
			shoppingCartData[i][3]=decFormat.format((double)productInfo[1])+"€";//Preis
			shoppingCartData[i][4]=(int)shoppingCartItems[i][1];//Menge
			shoppingCartData[i][5] = decFormat.format((double)shoppingCartItems[i][2])+"€";//Preis pro Position
			shoppingCartData[i][6]="Komplett entfernen";
			
		}
					
		addShoppingCart(shoppingCartData);	
				
	}
	
	/*
	 * 
	 */
	public void addShoppingCart(Object[][] data) throws Exception{
		
		String[] columnNames = {"Pos", "Artikel","id", "Preis", "Menge", "Gesamt", ""};
		int[] colWidths= {30, 200, 1, 90, 60, 120, 190};
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		cartTable = new JTable(model);
		
		for (int i = 0; i < columnNames.length; i++) {
		    cartTable.getColumnModel().getColumn(i).setPreferredWidth(colWidths[i]);
		    }
		Class<?> col_class = cartTable.getColumnClass(0);
		cartTable.setDefaultEditor(col_class, null);
		cartTable.getColumnModel().getColumn(4).setCellEditor(new SpinnerEditor());
	     
		
		Action delete = new AbstractAction()
		{
		    private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e)
		    {
		        JTable table = (JTable)e.getSource();
		        int row = Integer.valueOf(e.getActionCommand());
		        Object prodId=(int)((DefaultTableModel)table.getModel()).getValueAt(row, 2);
		        //int quantity=(int)((DefaultTableModel)table.getModel()).getValueAt(row, 4);
		        try {
					controller.doAction(1, new Object[] {prodId});
		        	
				} catch (Exception e1) {
						e1.printStackTrace();
				}
		        		
		    }
		};
		ButtonColumn buttonColumn = new ButtonColumn(cartTable,delete, columnNames.length-1);
		cartTable.removeColumn(cartTable.getColumnModel().getColumn(2));
		
		cartTable.getModel().addTableModelListener(new TableModelListener() {

		      public void tableChanged(TableModelEvent e) {
		    	 
		    	  
		         Object newval= ((DefaultTableModel)cartTable.getModel()).getValueAt(e.getFirstRow(), 4);
		         Object prodId= ((DefaultTableModel)cartTable.getModel()).getValueAt(e.getFirstRow(), 2);
		         
		        try {
		        	//update the quantity of an item
		             controller.doAction(2, new Object[] {prodId,newval});
		         if ((int)newval<0) 
		        	 controller.UpdateView();
		         
		        }
		        catch(Exception ex)
		        {
		        	ex.printStackTrace();
		         try {
					controller.UpdateView();
				} catch (Exception ex1) {
					
					ex1.printStackTrace();
				}
		      }
		    }});
		
		SCPanel.add(cartTable.getTableHeader(), BorderLayout.PAGE_START);
		SCPanel.add(cartTable);
		SCPanel.setVisible(true);
		
		cartTable.setFillsViewportHeight(true);
		//computing the total amount
		controller.doAction(3, null);
		
	}
	
	public void addTotal(Hashtable<String, Double> total){
		
		if (total.get("cart_size")==0)
			{ JLabel emptyCartLbl=new JLabel("Ihr Korb ist leer");
			Font font = emptyCartLbl.getFont();
			emptyCartLbl.setFont(font.deriveFont(font.BOLD,16));
			
			SCPanel.add(emptyCartLbl);
			return;
			}
		String[] columnNames = {"",""};
		int[] colWidths= {100,120};
		DecimalFormat decFormat1 = new DecimalFormat("#0.0");
		DecimalFormat decFormat2 = new DecimalFormat("#0.00");
		
		Object[][] data = new Object[][]{{"Summe",decFormat2.format(total.get("brutto"))+"€"},{"-Rabatt " + decFormat1.format(total.get("discount_rel")) + "%",decFormat2.format(total.get("discount_rel_abs"))+"€"},{"-Rabatt absolut",decFormat2.format(total.get("discount_abs"))+"€"},{"Endbetrag",decFormat2.format(total.get("netto"))+"€"}};
		TableCellRenderer bigRenderer = new CustomTableRendererTotal();
		TableCellRenderer boldRenderer = new CustomTableRendererBold();
		DefaultTableModel model = new DefaultTableModel(data,columnNames);
		
		// the subclass of JTable for rendering "Endbetrag"
		totalTable = new JTable(model){
		 	private static final long serialVersionUID = 1L;

			public TableCellRenderer getCellRenderer(int row, int column) {
		        if ((row == 3)&&(column==1)) 
		            return bigRenderer;
		        	
		        if ((row == 3)&&(column==0)) 
		            return boldRenderer;
		        
		        return super.getCellRenderer(row, column);
		    }
		};

		//Font font = totalTable.getFont();
		//totalTable.setFont(font.deriveFont(Font.BOLD, 12));
	
		Class<?> col_class = totalTable.getColumnClass(0);
		totalTable.setDefaultEditor(col_class, null);
		for (int i = 0; i < columnNames.length; i++) {
			totalTable.getColumnModel().getColumn(i).setPreferredWidth(colWidths[i]);
		    }
	    totalTable.setSize(300,100);
	    TotalPanel.setLayout(new GridLayout(6,2));
	    TotalPanel.setAlignmentX(0);
	    TotalPanel.setAlignmentY(1);
		TotalPanel.add(totalTable);
		SCPanel.add(TotalPanel);
		SCPanel.setVisible(true);
		TotalPanel.setVisible(true);
		totalTable.setFillsViewportHeight(true);
		
		
	}
	
	
	public void addProduct(int Id, String name, String desc, double price)
	{   
		DecimalFormat decFormat = new DecimalFormat("#0.00");
		JPanel itemPanel= new JPanel();
		itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
		JButton addProd = new JButton( "Zum Einkaufskorb!");
		addProd.setBackground(Color.green);
		addProd.setName(Integer.toString(Id));
		JLabel nameLbl = new JLabel(name);
		JLabel descLbl = new JLabel(desc);
		JLabel priceLbl = new JLabel("Preis:" + decFormat.format(price)+"€");
		addProd.addActionListener(new ActionListener()
		{
		//Adding an item
		public void actionPerformed(ActionEvent event)
		{
			try {
			    int prodId = Integer.parseInt(addProd.getName());
				controller.doAction(4,  new Object[]{(Object)prodId});
			} 
			catch (NumberFormatException e) {
						e.printStackTrace();
			} catch (Exception e) {
						e.printStackTrace();
			}
		}});
		itemPanel.add(nameLbl);
		itemPanel.add(descLbl);
		itemPanel.add(priceLbl);
		itemPanel.add(addProd);
		itemPanel.add(new JLabel("  "));
		ProduktPanel.add(itemPanel);
		
	}

	public void showDiscountDialog()
	{ //guiFrame,"Rabatt einstellen", true)
		DiscountDialog configFrame;
		try {
			configFrame = new DiscountDialog(guiFrame,controller);
			configFrame.setTitle("Rabatt einstellen");
			configFrame.setModal(true);
			//configFrame.setSize(230,75);
			configFrame.setLocationRelativeTo(guiFrame);
			configFrame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
	
	public CommandListener getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}
}

