package myUber;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class GUI extends JPanel implements ActionListener {
    protected JTextField textField;
    protected static JTextArea textArea;
    private final static String newline = "\n";
 
    public GUI() {
        super(new GridBagLayout());
 
        textField = new JTextField(100);
        textField.addActionListener(this);
 
        textArea = new JTextArea(30, 100);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
 
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
 
        c.fill = GridBagConstraints.HORIZONTAL;
        add(textField, c);
 
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
    }
 
    public void actionPerformed(ActionEvent evt) {
        executeCommand();
        textField.selectAll();
 
        textArea.setCaretPosition(textArea.getDocument().getLength());
        textField.setText("");
    }
 
    /**
     * Create the GUI and show it.
     */
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("myUber GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        frame.add(new GUI());
        
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setUndecorated(true);
        frame.pack();
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    public void executeCommand() {
    	CLUI.execute(CLUI.stringToCommand(textField.getText()));
		textField.getText();
    }
    
    public static void print(String line) {
    	GUI.textArea.append(line + newline);
    }
}