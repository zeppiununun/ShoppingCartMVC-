package shoppingCartMVC;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CustomTableRendererBold extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

		// You should override getTableCellRendererComponent
	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value,
	            boolean isSelected, boolean hasFocus, int row, int column) {

	        Component c = super.getTableCellRendererComponent(table, value, isSelected,
	                hasFocus, row, column);

	        Font font = c.getFont();
	       c.setFont(font.deriveFont(Font.BOLD));
	     
	     
	        
	        return c;
	    }
	}