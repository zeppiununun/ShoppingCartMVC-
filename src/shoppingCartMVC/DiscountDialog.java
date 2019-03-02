package shoppingCartMVC;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

/* Dialog
 * Adjusting discount parameters of the shopping cart 
 */

public class DiscountDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	final JButton confirmBtn=new JButton("OK");
	JFormattedTextField DiscountInput_rel;
	JFormattedTextField DiscountInput_abs;
	CommandListener controller;
	final int windowWidth=230;
	final int windowHeight=75;

  public DiscountDialog (JFrame frame, CommandListener controller) throws Exception {
	    super(frame);
	    this.controller=controller;
	  	confirmBtn.addActionListener(this);
	  	setSize(windowWidth,windowHeight);
	  	JPanel inputPanel= new JPanel();
	  	
	  	//make sure the dialog gets disposed as it closes
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	
		//format for the % discount		
		NumberFormat format = DecimalFormat.getInstance(Locale.GERMAN);
		format.setGroupingUsed(false);
		format.setMaximumFractionDigits(6);
		
		NumberFormatter formatter_rel = new NumberFormatter(format);
	    formatter_rel.setMinimum(0.00);
	    formatter_rel.setMaximum(100.00);
	    formatter_rel.setAllowsInvalid(true);
	    formatter_rel.setOverwriteMode(false);
	    // If you want the value to be committed on each keystroke instead of focus lost
	    formatter_rel.setCommitsOnValidEdit(true);
		
	    //format for the abs. discount% 	
		NumberFormat currFormat = DecimalFormat.getInstance(Locale.GERMAN);
		currFormat.setGroupingUsed(false);
		currFormat.setMinimumFractionDigits(0);
		currFormat.setMaximumFractionDigits(2);

		NumberFormatter curr_Formatter = new NumberFormatter(currFormat);
		curr_Formatter.setMinimum(0.00);
		curr_Formatter.setAllowsInvalid(true);
		curr_Formatter.setOverwriteMode(false);
		curr_Formatter.setCommitsOnValidEdit(true);
	    
	    //using formatters for an absolute and relative discount	   
	    DiscountInput_rel = new JFormattedTextField(formatter_rel);
	    DiscountInput_abs = new JFormattedTextField(curr_Formatter);
	    DiscountInput_rel.setPreferredSize(new Dimension(50, 25));
	    DiscountInput_rel.setPreferredSize(new Dimension(50, 25));
	    
	    //get values for an absolute and relative discount
	    Object[] discounts = controller.doAction(6, null);
	    
	    DiscountInput_abs.setValue((double) discounts[0]);
	    DiscountInput_rel.setValue((double) discounts[1]);
	    
	    inputPanel.setLayout(new GridLayout(2,2));
	    inputPanel.add(new JLabel("Rabatt (%) "));
		inputPanel.add(DiscountInput_rel);
		inputPanel.add(new JLabel("Rabatt (€) "));
		inputPanel.add(DiscountInput_abs);
		add(inputPanel,BorderLayout.WEST);
		add(confirmBtn,BorderLayout.EAST);
		
  }
//OK button clicked
  public void actionPerformed(ActionEvent e) {
	  setVisible(false);
	  	  
	 try {
	 // set new values for an absolute and relative discount
		 controller.doAction(5, new Object[]{DiscountInput_abs.getValue(),DiscountInput_rel.getValue()});
	} catch (Exception e1) {
		
		e1.printStackTrace();
	}
      dispose();
      
  }
}