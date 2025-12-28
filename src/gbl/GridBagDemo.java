package gbl;


import static gbl.Anchor.*;
import static gbl.Fill.*;

import java.awt.*;
import javax.swing.*;

public class GridBagDemo implements Runnable
{
  public static void main(String[] args)
  {
    SwingUtilities.invokeLater(new GridBagDemo());
  }

  public void run()
  {
    JLabel lblFirst  = new JLabel("First Name");
    JLabel lblLast   = new JLabel("Last Name");
    JLabel lblStreet = new JLabel("Street");
    JLabel lblCity   = new JLabel("City");
    JLabel lblState  = new JLabel("State");
    JLabel lblZip    = new JLabel("ZIP");
    JLabel lblNotes  = new JLabel("Notes");

    JTextField txfFirst  = new JTextField(15);
    JTextField txfLast   = new JTextField(20);
    JTextField txfStreet = new JTextField(40);
    JTextField txfCity   = new JTextField(15);
    JTextField txfState  = new JTextField(5);
    JTextField txfZip    = new JTextField(10);
    
    JTextArea txaNotes = new JTextArea(5, 50);
    JScrollPane scrNotes = new JScrollPane(txaNotes);
    scrNotes.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    Component spacer1 = Box.createHorizontalStrut(5);
    Component spacer2 = Box.createHorizontalStrut(5);

    JPanel panel = new JPanel(new GridBagLayout());
    panel.add(spacer1,   new GBConstraints(0,0));
    panel.add(lblFirst,  new GBConstraints(0,1));
    panel.add(txfFirst,  new GBConstraints(1,1));
    panel.add(lblLast,   new GBConstraints(2,1));
    panel.add(txfLast,   new GBConstraints(3,1).spanX(3).fill(HORIZONTAL));
    panel.add(lblStreet, new GBConstraints(0,2));
    panel.add(txfStreet, new GBConstraints(1,2).spanX(5).fill(HORIZONTAL));
    panel.add(lblCity,   new GBConstraints(0,3));
    panel.add(txfCity,   new GBConstraints(1,3));
    panel.add(lblState,  new GBConstraints(2,3).anchor(EAST));
    panel.add(txfState,  new GBConstraints(3,3));
    panel.add(lblZip,    new GBConstraints(4,3));
    panel.add(txfZip,    new GBConstraints(5,3).fill(HORIZONTAL));
    panel.add(lblNotes,  new GBConstraints(0,4));
    panel.add(scrNotes,  new GBConstraints(1,4).spanX(5).fill(BOTH));
    panel.add(spacer2,   new GBConstraints(0,5));

    JFrame frame = new JFrame("Grid Bag Demo");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(new JScrollPane(panel), BorderLayout.CENTER);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}//end GridBagDemo