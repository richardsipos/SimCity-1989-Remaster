package Model.Helper;

public class Coordinates {
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