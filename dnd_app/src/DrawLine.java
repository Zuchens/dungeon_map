import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.LinkedList;


public class DrawLine extends JPanel  {
    int iterator = 0;
    public State state;
    DrawType elementType = DrawType.ADD_CORRIDOR;
    Point pointStart = null;
    Point pointEnd   = null;
    Point prevPointEnd;
    private Segment prevSegment;
    private Segment prevSegment2;
    private Creature movedCreature;

    {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {

                pointStart = e.getPoint();
                if(elementType == DrawType.MOVE_CREATURE){
                    if(movedCreature == null) {
                        movedCreature = removeCreature();
                    }else{
                        movedCreature.setX(pointStart.x);
                        movedCreature.setY(pointStart.y);
                        addCreature(movedCreature);
                        movedCreature = null;
                    }
                }

            }

            public void mouseReleased(MouseEvent e) {
                pointEnd = e.getPoint();

                Segment seg = new Segment(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);
                if(elementType == DrawType.ADD_CORRIDOR) {
                    Corridor corridor = new Corridor(seg, 50);
                    Plane.getInstance().addCorridors(corridor);
                }
                if(elementType == DrawType.ADD_WALL){
                    Wall wall = new Wall(seg);
                    Plane.getInstance().addWall(wall);
                }
                if(elementType == DrawType.ADD_ELEMENT){
                    Element element = new Element(pointEnd.x, pointEnd.y, "Default element " + iterator, 20);
                    iterator+=1;
                    Plane.getInstance().addElement(element);
                }
                if(elementType == DrawType.REMOVE_ELEMENT){
                    LinkedList<Element> elements = Plane.getInstance().getElements();
                    for(Element element: elements){
                        double dist_pow = Math.pow(element.getX() - pointEnd.getX(),2)  + Math.pow(element.getY() - pointEnd.getY(),2);
                        if (dist_pow < Math.pow(element.getSize(),2)){
                            Plane.getInstance().removeElements(element);
                        }
                    }

                }
                if(elementType == DrawType.ADD_CREATURE){
                    Creature creature = new Creature(pointEnd.x, pointEnd.y, "Default creature "+ iterator, 20);
                    iterator+=1;
                    addCreature(creature);
                }
                if(elementType == DrawType.REMOVE_CREATURE){
                    removeCreature();

                }
                if(elementType == DrawType.REMOVE_WALL){
                    Plane.getInstance().removeWall(pointEnd.getX(), pointEnd.getY(), 10);

                }
                repaint();

                pointStart = null;
            }

            private void addCreature(Creature creature) {
                Plane.getInstance().addCreature(creature);
            }

            private Creature removeCreature() {
                LinkedList<Creature> creatures = Plane.getInstance().getCreatures();
                for(Creature creature: creatures){
                    double dist_pow = Math.pow(creature.getX() - pointEnd.getX(),2)  + Math.pow(creature.getY() - pointEnd.getY(),2);
                    if (dist_pow < Math.pow(creature.getSize(),2)){
                        Plane.getInstance().removeCreature(creature);
                        return creature;
                    }
                }
                return null;
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                pointEnd = e.getPoint();

                repaint();


            }

            public void mouseDragged(MouseEvent e) {
                pointEnd = e.getPoint();
                repaint();

            }
        });
    }
    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (pointEnd != null && elementType == DrawType.ADD_CORRIDOR && prevPointEnd != null) {
            int r = 50;
            Segment s = new Segment(prevPointEnd.x, prevPointEnd.y, pointEnd.x, pointEnd.y);
            Corridor c = new Corridor(s, 50);

            if (prevPointEnd.x == pointEnd.x &&  prevPointEnd.y == pointEnd.y && prevSegment!= null && prevSegment2!= null) {
                drawSegments(g2,prevSegment, Color.ORANGE);
                drawSegments(g2,prevSegment2, Color.ORANGE);

            }else{
                drawSegments(g2, new Segment(c.getOverSegment().x0, c.getOverSegment().y0, c.getUnderSegment().x0, c.getUnderSegment().y0), Color.ORANGE);
                drawSegments(g2, new Segment(c.getOverSegment().x1, c.getOverSegment().y1, c.getUnderSegment().x1, c.getUnderSegment().y1), Color.ORANGE);
                prevSegment = new Segment(c.getOverSegment().x0, c.getOverSegment().y0, c.getUnderSegment().x0, c.getUnderSegment().y0);
                prevSegment2 = new Segment(c.getOverSegment().x1, c.getOverSegment().y1, c.getUnderSegment().x1, c.getUnderSegment().y1);
            }
            g2.setColor(Color.ORANGE);
            g.drawOval(pointEnd.x -r,pointEnd.y -r,2*r,2*r);
        }
        if (pointStart != null) {
            g.setColor(Color.GREEN);
            g.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);

            Segment seg = new Segment(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);
            g2.draw(new Line2D.Double(seg.x0, seg.y0, seg.x1, seg.y1));

            if (elementType == DrawType.ADD_CORRIDOR) {
                Segment s = new Segment(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);
                Corridor c = new Corridor(s, 50);
                drawSegments(g2, c.getOverSegment());
                drawSegments(g2, c.getUnderSegment());
            }
        }
        for(Wall wall : Plane.getInstance().getWalls()) {
            drawSegments(g2, wall.getSegment());
        }
        for(Element element : Plane.getInstance().getElements()) {
            int r = element.getSize();
            g2.setColor(Color.GRAY);
            g.fillOval(element.getX() -r,element.getY()-r,2*r,2*r);
            g2.setColor(Color.BLACK);
            g.drawString(element.getName(), element.getX()-r, element.getY()+r);
        }

        for(Creature creature : Plane.getInstance().getCreatures()) {
            int r = creature.getSize();
            g2.setColor(Color.CYAN);
            g.fillOval(creature.getX() -r,creature.getY()-r,2*r,2*r);
            g2.setColor(Color.BLACK);
            g.drawString(creature.getName(), creature.getX()-r, creature.getY()+r);
        }
        prevPointEnd = pointEnd;
    }

    private void drawSegments(Graphics2D g2, Segment seg) {
        drawSegments(g2, seg, Color.DARK_GRAY);
    }
    private void drawSegments(Graphics2D g2, Segment seg, Color color) {
        g2.setStroke(new BasicStroke(2f));
        g2.setColor(color);
        g2.draw(new Line2D.Double(seg.x0, seg.y0, seg.x1, seg.y1));
    }

}

