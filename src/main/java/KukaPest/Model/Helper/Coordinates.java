package KukaPest.Model.Helper;

public class Coordinates implements java.io.Serializable{
    private int x;
    private int y;
    public Coordinates(int x, int y) {
        this.x =x;
        this.y = y;
    }

    public int getHeight() {
        return x;
    }

    public int getWidth() {
        return y;
    }
}