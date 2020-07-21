import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends javax.swing.JFrame{

    private final JSplitPane splitPane;  // split the window in top and bottom
    private final DrawLine topPanel;       // container panel for the top
    private final JPanel bottomPanel;    // container panel for the bottom

    public Main(){

        splitPane = new JSplitPane();

        topPanel = new DrawLine();         // our top component
        bottomPanel = new JPanel();      // our bottom component

        // now lets define the default size of our window and its layout:
        setPreferredSize(new Dimension(500, 500));     // let's open the window with a default size of 400x400 pixels
        // the contentPane is the container that holds all our components
        getContentPane().setLayout(new GridLayout());  // the default GridLayout is like a grid with 1 column and 1 row,
        // we only add one element to the window itself
        getContentPane().add(splitPane);               // due to the GridLayout, our splitPane will now fill the whole window

        // let's configure our splitPane:
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);  // we want it to split the window verticaly
        splitPane.setDividerLocation(50);                    // the initial position of the divider is 200 (our window is 400 pixels high)
        splitPane.setTopComponent(bottomPanel);                  // at the top we want our "topPanel"
        splitPane.setBottomComponent(topPanel);            // and at the bottom we want our "bottomPanel"
//
//        // our topPanel doesn't need anymore for this example. Whatever you want it to contain, you can add it here
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS)); // BoxLayout.Y_AXIS will arrange the content vertically
        Choice c = new Choice();

        // add element to the list
        c.add("ADD_CORRIDOR");
        c.add("ADD_WALL");
        c.add("ADD_ELEMENT");
        c.add("REMOVE_WALL");
        c.add("REMOVE_ELEMENT");
        bottomPanel.add(c);
        c.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                String data = "Type selected: "+ c.getItem(c.getSelectedIndex());
                System.out.println(data);
                topPanel.elementType = DrawType.valueOf(c.getItem(c.getSelectedIndex()));
            }
        });
        pack();   // calling pack() at the end, will ensure that every layout and size we just defined gets applied before the stuff becomes visible
    }

    public static void main(String args[]){
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                new Main().setVisible(true);
            }
        });
    }
}