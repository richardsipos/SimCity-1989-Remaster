package Model.Map;

abstract public class MainZone extends Constructable {
    private final int width;
    private final int height;

    public MainZone(int width, int height) {
        super();
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
