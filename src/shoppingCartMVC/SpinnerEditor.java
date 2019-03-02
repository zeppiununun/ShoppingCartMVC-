package shoppingCartMVC;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/* provided by Jason, see https://stackoverflow.com/a/9042680
 * SpinnerEditor for updating cells of the JTable 
 */
public class SpinnerEditor extends DefaultCellEditor
    {
        private static final long serialVersionUID = 1L;
		JSpinner spinner;
        JSpinner.DefaultEditor editor;
        JTextField textField;
        boolean valueSet;

        // Initializes the spinner.
        public SpinnerEditor() {
            super(new JTextField());
            spinner = new JSpinner();
            editor = ((JSpinner.DefaultEditor)spinner.getEditor());
            textField = editor.getTextField();
            textField.addFocusListener( new FocusListener() {
                public void focusGained( FocusEvent fe ) {
                	
                    SwingUtilities.invokeLater( new Runnable() {
                        public void run() {
                            if ( valueSet ) {
                                textField.setCaretPosition(1);
                            }
                        }
                    });
                }
                public void focusLost( FocusEvent fe ) {
                }
            });
            textField.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent ae ) {
                    stopCellEditing();
                }
            });
        }

        // Prepares the spinner component and returns it.
        public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column
        ) {
            if ( !valueSet ) {
                spinner.setValue(value);
            }
            SwingUtilities.invokeLater( new Runnable() {
                public void run() {
                    textField.requestFocus();
                }
            });
            return spinner;
        }

        public boolean isCellEditable( EventObject eo ) {
             if ( eo instanceof KeyEvent ) {
                KeyEvent ke = (KeyEvent)eo;
                System.err.println("key event: "+ke.getKeyChar());
                textField.setText(String.valueOf(ke.getKeyChar()));
                //textField.select(1,1);
                //textField.setCaretPosition(1);
                //textField.moveCaretPosition(1);
                valueSet = true;
            } else {
                valueSet = false;
            }
            return true;
        }

        // Returns the spinners current value.
        public Object getCellEditorValue() {
            return spinner.getValue();
        }

        public boolean stopCellEditing() {
            //System.err.println("Stopping edit");
            try {
                editor.commitEdit();
                spinner.commitEdit();
            } catch ( java.text.ParseException e ) {
                JOptionPane.showMessageDialog(null,
                    "Falsche Eingabe, wird ignoriert.");
            }
            return super.stopCellEditing();
        }
    }



