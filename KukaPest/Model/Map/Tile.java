package Model.Map;

import Model.*;

public class Tile {
    private Coordinates coordinates;
    private boolean hasElectricity;

    public Tile(Coordinates coordinates, boolean hasElectricity) {
        this.coordinates = coordinates;
        this.hasElectricity = hasElectricity;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public boolean getHasElectricity() {
        return hasElectricity;
    }

    public void setHasElectricity(boolean hasElectricity) {
        this.hasElectricity = hasElectricity;
    }
}
