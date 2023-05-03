package KukaTest;

import KukaPest.Model.Game;
import KukaPest.Model.Helper.Building;
import KukaPest.Model.Helper.Coordinates;
import KukaPest.Model.Map.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Game_destroy_tests {

    @Test
    public void testDestroyInitRoad() {
        Game g = new Game("test");
        g.destroy(new Coordinates(26,22));
        assertTrue(g.getMap()[26][22] instanceof Road);
    }

    @Test
    public void testDestroyRoadSuccessful() {
        Game g = new Game("test");
        g.build(Building.ROAD, new Coordinates(26,21));
        g.destroy(new Coordinates(26,21));
        assertFalse(g.getMap()[26][21] instanceof Road);
    }
    @Test
    public void testDestroyGraphIntegrity() {
        Game g = new Game("test");
        g.build(Building.ROAD, new Coordinates(26,21));
        g.build(Building.ROAD, new Coordinates(26,20));
        g.build(Building.RESIDENTIAL, new Coordinates(24,20));
        g.destroy(new Coordinates(26,20));
        g.destroy(new Coordinates(26,21));
        assertTrue(g.getMap()[26][21] instanceof Road);
    }

}