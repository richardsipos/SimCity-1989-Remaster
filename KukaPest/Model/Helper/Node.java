package Model.Helper;

public class Node {
    private int id;
    boolean isroad;
    private Coordinates coordinates;

    public Node(int id,boolean isroad, Coordinates coordinates){
        this.id = id;
        this.isroad = isroad;
        this.coordinates = coordinates;
    }

    public int getID(){
        return id;
    }

    public boolean Isroad() {
        return isroad;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public String toString() {
        return Integer.toString(this.id);
    }
}
