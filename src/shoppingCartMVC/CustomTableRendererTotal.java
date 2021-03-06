package shoppingCartMVC;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * provides large, blue font in the CellRendererComponent components of the
 * JTable object
 * 
 */
public class CustomTableRendererTotal extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		Font font = c.getFont();
		c.setFont(font.deriveFont(Font.BOLD, 16));
		c.setForeground(Color.BLUE);

		return c;
	}
}
