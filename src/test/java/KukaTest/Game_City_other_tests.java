package KukaTest;

import KukaPest.Model.Game;
import KukaPest.Model.Helper.Building;
import KukaPest.Model.Helper.Coordinates;
import KukaPest.Model.Map.ResidentialZone;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Game_City_other_tests {

    @Test
    public void testBuildingUpgrade() {
        Game g = new Game("test");
        g.build(Building.RESIDENTIAL, new Coordinates(24,22));
        g.upgrade(new Coordinates(24,22));
        ResidentialZone r = (ResidentialZone) g.getMap()[24][22];
        assertTrue(r.getLevel() == 2 );
    }

    @Test
    public void testElectricity() {
        Game g = new Game("test");
        for (int i = 21; i > 16; --i) {
            g.build(Building.ROAD, new Coordinates(26,i));
        }
        g.build(Building.RESIDENTIAL, new Coordinates(24,22));
        g.build(Building.POWER_PLANT, new Coordinates(22,18));
        g.getCity().electricityStats();
        assertTrue( g.getElectricityNeed() == 5 && g.getElectricityProduction() == 300);
    }
    @Test
    public void testElectricityUsedAndPoles() throws InterruptedException {
        Game g = new Game("test");
        for (int i = 21; i > 16; --i) {
            g.build(Building.ROAD, new Coordinates(26,i));
        }
        g.build(Building.RESIDENTIAL, new Coordinates(24,22));
        g.build(Building.POWER_PLANT, new Coordinates(22,16));
        g.build(Building.POLE, new Coordinates(22,20));
        g.build(Building.POLE, new Coordinates(22,21));
        Thread.sleep(1000);
        ResidentialZone r = g.getPoweredResZone();
        assertTrue( r == null);
    }


}
