public class Corridor {

    private Segment overSegment;
    private Segment underSegment;

    public Corridor(Segment s, int size){
        generate(s, size);
    }
    public Corridor(Segment s){
        generate(s, 10);
    }
    private void generate(Segment seg, int width) {

        float a = (float) ((seg.y1 - seg.y0)/ (seg.x1 - seg.x0 + 0.0001)+ 0.0001);
        float b = seg.y0 - a*seg.x0;
        Line baseLine = new Line(a,b);
        this.overSegment = generateSegment(seg, width,  baseLine);
        this.underSegment = generateSegment(seg, -width,  baseLine);


    }

    private Segment generateSegment(Segment seg, int width, Line baseLine) {

        float b1 = (float) (baseLine.b +width/Math.cos(Math.atan(baseLine.a)));
        Line line1 = new Line(baseLine.a,b1);

        float b0p = seg.y0 - baseLine.getPerpendicular()*seg.x0;
        float newX0 = (b0p - line1.b)/(baseLine.a - baseLine.getPerpendicular()) ;
        float newY0 = line1.a * newX0 + b1;

        float b1p = seg.y1 - baseLine.getPerpendicular()*seg.x1;
        float newX1 = (b1p - line1.b)/(baseLine.a - baseLine.getPerpendicular()) ;
        float newY1 = line1.a * newX1 + b1;
        return new Segment(newX0, newY0, newX1, newY1);
    }

    public Segment getOverSegment() {
        return overSegment;
    }

    public Segment getUnderSegment() {
        return underSegment;
    }
}
