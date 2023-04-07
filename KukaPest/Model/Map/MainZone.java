package Model.Map;

abstract public class MainZone extends Constructable {
    private final int width;
    private final int height;
    private final int maxCapacity;
    private int currentCapacity;


    public MainZone(int width, int height,int maxCapacity,int priceOfBuilding) {
        super(priceOfBuilding);
        this.width = height;
        this.height = width;
        this.maxCapacity = maxCapacity;
        this.currentCapacity=0;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }

    public void setCurrentCapacity(int currentCapacity) {
        this.currentCapacity = currentCapacity;
    }
}
