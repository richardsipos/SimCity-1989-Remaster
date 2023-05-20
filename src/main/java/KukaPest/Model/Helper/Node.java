package KukaPest.Model.Helper;

public class Node implements java.io.Serializable{
    private int id;
    boolean isroad;
    boolean isworkplace;
    private Coordinates coordinates;

    public Node(int id,boolean isroad, Coordinates coordinates,boolean isworkplace){
        this.id = id;
        this.isroad = isroad;
        this.coordinates = coordinates;
        this.isworkplace = isworkplace;
    }

    public int getID(){
        return id;
    }

    public boolean Isroad() {
        return isroad;
    }
    public boolean IsWorkplace(){return isworkplace;}

    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public String toString() {
        return Integer.toString(this.id);
    }
}
