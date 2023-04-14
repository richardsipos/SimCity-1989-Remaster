package Model.Map;

abstract public class Constructable extends Tile{ //abstract
    private int price;

    public Constructable(int price) {
        super();
        this.price=price;
    }

    public int getPrice() {
        return price;
    }
}
