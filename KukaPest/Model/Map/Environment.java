package Model.Map;

import Model.Coordinates;

import java.awt.*;
import Model.*;

public class Environment extends MapItem{

        public Environment(Coordinates coordinates, boolean hasElectricity, Image currentImg) {
            super(coordinates, hasElectricity, currentImg);
    }
}
