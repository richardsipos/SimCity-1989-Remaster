import java.awt.*;

public class Constructable extends MapItem{

    private int cost;
    private int timeToBuild;
    private int upKeep = 0;

    public Constructable(Coordinates coordinates, boolean hasElectricity, Image currentImg, int cost, int timeToBuild) {
        super(coordinates, hasElectricity, currentImg);
        this.cost = cost;
        this.timeToBuild = timeToBuild;
    }


}
