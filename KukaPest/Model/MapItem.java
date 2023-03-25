package Model;

import java.awt.*;

public class MapItem extends Tile{

    private Image currentImg;

    public MapItem(Coordinates coordinates, boolean hasElectricity, Image currentImg) {
        super(coordinates, hasElectricity);
        this.currentImg = currentImg;
    }

    public Image getCurrentImg() {
        return currentImg;
    }

    public void setCurrentImg(Image currentImg) {
        this.currentImg = currentImg;
    }
}
