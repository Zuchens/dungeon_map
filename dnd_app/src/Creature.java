public class Creature {
    private int y;
    private int x;
    private String name;
    private int size;

    public Creature(int x, int y, String name, int size) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.size = size;

    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
