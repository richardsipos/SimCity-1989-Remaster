import java.awt.*;

public class Constructable extends MapItem{

    private int cost;
    private int timeToBuild;
    private int upKeep = 0;

    public Constructable(Coordinates coordinates, boolean hasElectricity, Image currentImg, int cost, int timeToBuild) {
        super(coordinates, hasElectricity, currentImg);
        // Ricsi was here
        // Bence was here too, but I havent found free cookies
        //Niki
        this.cost = cost;
        this.timeToBuild = timeToBuild;
    }


}
