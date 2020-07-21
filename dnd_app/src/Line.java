public class Line {
    public float a;
    public float b;

    public Line(float a, float b) {
        this.a = a;
        this.b = b;
    }

    public float getPerpendicular(){
        return -1/this.a;
    }
}
