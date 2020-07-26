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
        getContentPane().setLayout(new GridLayout());
        getContentPane().add(splitPane);

        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);  // we want it to split the window verticaly
        splitPane.setDividerLocation(50);                    // the initial position of the divider is 200 (our window is 400 pixels high)
        splitPane.setTopComponent(bottomPanel);                  // at the top we want our "topPanel"
        splitPane.setBottomComponent(topPanel);            // and at the bottom we want our "bottomPanel"
//
//        // our topPanel doesn't need anymore for this example. Whatever you want it to contain, you can add it here
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS)); // BoxLayout.Y_AXIS will arrange the content vertically
        Choice drawChoice = new Choice();
        drawChoice.add("ADD_CORRIDOR");
        drawChoice.add("ADD_WALL");
        drawChoice.add("REMOVE_WALL");
        drawChoice.add("ADD_ELEMENT");
        drawChoice.add("REMOVE_ELEMENT");
        drawChoice.addItemListener(itemEvent -> {
            String data = "Type selected: "+ drawChoice.getItem(drawChoice.getSelectedIndex());
            System.out.println(data);
            topPanel.elementType = DrawType.valueOf(drawChoice.getItem(drawChoice.getSelectedIndex()));
        });
        bottomPanel.add(drawChoice);

        Choice fightChoice = new Choice();
        fightChoice.add("ADD_CREATURE");
        fightChoice.add("REMOVE_CREATURE");
        fightChoice.add("MOVE_CREATURE");
        fightChoice.addItemListener(itemEvent -> {
            String data = "Type selected: "+ fightChoice.getItem(fightChoice.getSelectedIndex());
            System.out.println(data);
            topPanel.elementType = DrawType.valueOf(fightChoice.getItem(fightChoice.getSelectedIndex()));
        });

        Choice state = new Choice();
        state.add("PLAN");
        state.add("FIGHT");
        state.addItemListener(itemEvent -> {
            String data = "State selected: "+ state.getItem(state.getSelectedIndex());
            System.out.println(data);
            topPanel.state = State.valueOf(state.getItem(state.getSelectedIndex()));
            if(topPanel.state.equals(State.PLAN)){
                bottomPanel.remove(fightChoice);
                bottomPanel.add(drawChoice);
                topPanel.elementType = DrawType.valueOf(drawChoice.getItem(drawChoice.getSelectedIndex()));
            }
            if(topPanel.state.equals(State.FIGHT)){
                bottomPanel.remove(drawChoice);
                bottomPanel.add(fightChoice);
                topPanel.elementType = DrawType.valueOf(fightChoice.getItem(fightChoice.getSelectedIndex()));
            }
        });
        bottomPanel.add(state);
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