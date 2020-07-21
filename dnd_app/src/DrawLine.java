import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.LinkedList;


public class DrawLine extends JPanel  {
    DrawType elementType = DrawType.ADD_CORRIDOR;
    Point pointStart = null;
    Point pointEnd   = null;
    Point prevPointEnd;
    private Segment prevSegment;
    private Segment prevSegment2;

    {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                pointStart = e.getPoint();
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
                    Element element = new Element(pointEnd.x, pointEnd.y, "Default creature", 20);
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
                if(elementType == DrawType.REMOVE_WALL){
                    Plane.getInstance().removeWall(pointEnd.getX(), pointEnd.getY(), 10);

                }
                repaint();

                pointStart = null;
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
            g.setColor(Color.RED);
            int r = 50;
            Segment s = new Segment(prevPointEnd.x, prevPointEnd.y, pointEnd.x, pointEnd.y);
            Corridor c = new Corridor(s, 50);

            if (prevPointEnd.x == pointEnd.x &&  prevPointEnd.y == pointEnd.y && prevSegment!= null && prevSegment2!= null) {
                drawSegments(g2,prevSegment);
                drawSegments(g2,prevSegment2);
                g.drawString("PrevSegment", prevPointEnd.x, prevPointEnd.y);

            }else{
                drawSegments(g2, new Segment(c.getOverSegment().x0, c.getOverSegment().y0, c.getUnderSegment().x0, c.getUnderSegment().y0));
                drawSegments(g2, new Segment(c.getOverSegment().x1, c.getOverSegment().y1, c.getUnderSegment().x1, c.getUnderSegment().y1));
                prevSegment = new Segment(c.getOverSegment().x0, c.getOverSegment().y0, c.getUnderSegment().x0, c.getUnderSegment().y0);
                prevSegment2 = new Segment(c.getOverSegment().x1, c.getOverSegment().y1, c.getUnderSegment().x1, c.getUnderSegment().y1);
                Float sa = prevSegment.x0;
                if(sa.isNaN()){
                     new Corridor(s, 50);
                    int a = 1;
                }
            }
            g.drawOval(pointEnd.x -r,pointEnd.y -r,2*r,2*r);
        }
        if (pointStart != null) {
            g.setColor(Color.RED);
            g.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);

            Segment seg = new Segment(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);
            g2.setColor(Color.RED);
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
            g2.setColor(Color.CYAN);
            g.fillOval(element.getX() -r,element.getY()-r,2*r,2*r);
            g2.setColor(Color.BLACK);
            g.drawString(element.getName(), element.getX()-r, element.getY()+r);
        }
        prevPointEnd = pointEnd;
    }

    private void drawSegments(Graphics2D g2, Segment seg) {
        g2.setStroke(new BasicStroke(2f));
        g2.setColor(Color.GREEN);
        g2.draw(new Line2D.Double(seg.x0, seg.y0, seg.x1, seg.y1));
    }

}

