import java.util.LinkedList;

public class Plane {
    private int width;
    private int length;
    private LinkedList<Element> elements;
    private LinkedList<Wall> walls;
    private static Plane single_instance = null;
    private LinkedList<Creature> creatures;

    private Plane() {
        this.width = 200;
        this.length = 200;
        this.elements = new LinkedList<>();
        this.walls = new LinkedList<>();
        this.creatures = new LinkedList<>();

    }

    private Plane(int width, int length) {
        this.width = width;
        this.length = length;
        this.elements = new LinkedList<>();
        this.walls = new LinkedList<>();
    }


    // static method to create instance of Singleton class
    public static Plane getInstance()
    {
        if (single_instance == null)
            single_instance = new Plane();

        return single_instance;
    }


    public LinkedList<Wall> getWalls() {
        return walls;
    }

    public void addCorridors(Corridor newCorridor) {
        for(Wall wall : this.walls){

            compareToSegment(wall.getSegment(), newCorridor.getOverSegment());
            compareToSegment(wall.getSegment(), newCorridor.getUnderSegment());

        }
        this.walls.add(new Wall(newCorridor.getOverSegment()));
        this.walls.add(new Wall(newCorridor.getUnderSegment()));
    }

    private void compareToSegment(Segment segment, Segment newSegment) {
        if(compareToPoint(segment.x0, segment.y0, newSegment.x0,  newSegment.y0)){
            newSegment.x0 = segment.x0;
            newSegment.y0 = segment.y0;
        }
        if(compareToPoint(segment.x1, segment.y1, newSegment.x1,  newSegment.y1)){
            newSegment.x1 = segment.x1;
            newSegment.y1 = segment.y1;
        }
        if(compareToPoint(segment.x0, segment.y0, newSegment.x0,  newSegment.y0)){
            newSegment.x0 = segment.x0;
            newSegment.y0 = segment.y0;
        }
        if(compareToPoint(segment.x0, segment.y0, newSegment.x1,  newSegment.y1)){
            newSegment.x1 = segment.x0;
            newSegment.y1 = segment.y0;
        }
        if(compareToPoint(segment.x1, segment.y1, newSegment.x0,  newSegment.y0)){
            newSegment.x0 = segment.x1;
            newSegment.y0 = segment.y1;
        }
    }

    private boolean compareToPoint(float x, float y, float newX, float newY) {
        int epsilon = 40;
        return Math.abs(x - newX) < epsilon && Math.abs(y - newY) < epsilon;
    }

    public void addWall(Wall newWall) {
        for(Wall wall : this.walls){

            compareToSegment(wall.getSegment(), newWall.getSegment());

        }
        this.walls.add(newWall);
    }
    public void addElement(Element element) {
        this.elements.add(element);
    }
    public LinkedList<Element> getElements() {
        return elements;
    }

    public void removeElements(Element element) {
        this.elements.remove(element);
    }
    public void removeWall(double x, double y, float epsilon) {
        for(Wall wall: walls){
            float x0 = wall.getSegment().x0;
            float y0 = wall.getSegment().y0;
            float x1 = wall.getSegment().x1;
            float y1 = wall.getSegment().y1;
//            float u = (float) (((x1 - x0)*(x - x0) +(y1 -y0)*(y-y0))/(Math.pow(x1-x0,2) +Math.pow(y1-y0,2)));
            float u = (float) (Math.abs( (y1 -y0)/(x1 - x0)*x -y + (x1*y0 - x0*y1)/(x1-x0)) /Math.sqrt(Math.pow((y1 -y0)/(x1 - x0),2)+1));
            if(u<epsilon){
                this.walls.remove(wall);
                break;
            }

        }

    }

    public void addCreature(Creature creature) {
        this.creatures.add(creature);
    }
    public LinkedList<Creature> getCreatures() {
        return creatures;
    }

    public void removeCreature(Creature creature) {
        this.creatures.remove(creature);
    }
}
