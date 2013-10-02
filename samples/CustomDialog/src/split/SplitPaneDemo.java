/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package split;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

//SplitPaneDemo itself is not a visible component.
public class SplitPaneDemo implements ListSelectionListener {
    private JList list = new JList();
    private JSplitPane splitPane;

    public SplitPaneDemo() {
        //Read image names from a properties file
        ResourceBundle imageResource;
        try {
        } catch (MissingResourceException e) {
            System.out.println("Can't find the properties file " +
                               "that contains the image names.");
            return;
        }

        //Create the list of images and put it in a scroll pane
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        JScrollPane listScrollPane = new JScrollPane(list);

        //Set up the picture label and put it in a scroll pane
        JPanel p = new JPanel();
        p.add(new JLabel("sdfdsfdsf"));

        //Create a split pane with the two scroll panes in it
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                   listScrollPane, p);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(150);

        //Provide minimum sizes for the two components in the split pane
        Dimension minimumSize = new Dimension(100, 50);
        listScrollPane.setMinimumSize(minimumSize);
//        pictureScrollPane.setMinimumSize(minimumSize);

        //Provide a preferred size for the split pane
        splitPane.setPreferredSize(new Dimension(400, 200));
    }

    //Used by SplitPaneDemo2
    public JList getImageList() {
        return list;
    }

    public JSplitPane getSplitPane() {
        return splitPane;
    }

    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting())
            return;

        JList theList = (JList)e.getSource();
        if (theList.isSelectionEmpty()) {
        } else {
        }
    }

    protected static Vector parseList(String theStringList) {
        Vector v = new Vector(10);
        StringTokenizer tokenizer = new StringTokenizer(theStringList, " ");
        while (tokenizer.hasMoreTokens()) {
            String image = tokenizer.nextToken();
            v.addElement(image);
        }
        return v;
    }

    public static void main(String s[]) {
        JFrame frame = new JFrame("SplitPaneDemo");

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });

        SplitPaneDemo splitPaneDemo = new SplitPaneDemo();
        frame.getContentPane().add(splitPaneDemo.getSplitPane());
        frame.pack();
        frame.setVisible(true);
    }
}
