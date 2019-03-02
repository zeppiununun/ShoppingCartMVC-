package shoppingCartMVC;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/*makes the font bold in the CellRendererComponent components of the JTable object
 * 
 */
public class CustomTableRendererBold extends DefaultTableCellRenderer {
 
	private static final long serialVersionUID = 1L;

	    public Component getTableCellRendererComponent(JTable table, Object value,
	            boolean isSelected, boolean hasFocus, int row, int column) {

	        Component c = super.getTableCellRendererComponent(table, value, isSelected,
	                hasFocus, row, column);

	        Font font = c.getFont();
	       c.setFont(font.deriveFont(Font.BOLD));	        
	        return c;
	    }
	}