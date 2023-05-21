package KukaPest.Model.Map;

abstract public class Constructable extends Tile implements java.io.Serializable {
    private int price;
    private int upKeep;

    public Constructable(int price, int upKeep) {
        super();
        this.price=price;
        this.upKeep = upKeep;
    }

    public int getPrice() {
        return price;
    }
    public int getUpKeep() {
        return upKeep;
    }
}
