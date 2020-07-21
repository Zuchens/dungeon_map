import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;

public class Draw extends JComponent {
    private Point pressed;

    @Override
    public void paint(Graphics g) {
        Segment seg = new Segment(400, 100, 1, 3);
        Corridor cor = new Corridor(seg,  20);
        Segment overSeg = cor.getOverSegment();
        Segment underSeg = cor.getUnderSegment();
        // Draw a simple line using the Graphics2D draw() method.
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2f));
        g2.setColor(Color.RED);
        g2.draw(new Line2D.Double(seg.x0, seg.y0, seg.x1, seg.y1));
        g2.setColor(Color.GREEN);
        g2.draw(new Line2D.Double(underSeg.x0, underSeg.y0, underSeg.x1, underSeg.y1));
        g2.setColor(Color.BLUE);
        g2.draw(new Line2D.Double(overSeg.x0, overSeg.y0, overSeg.x1, overSeg.y1));
    }

}