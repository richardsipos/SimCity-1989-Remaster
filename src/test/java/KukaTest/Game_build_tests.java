package KukaTest;

import KukaPest.Model.Game;
import KukaPest.Model.Helper.Building;
import KukaPest.Model.Helper.Coordinates;
import KukaPest.Model.Map.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Game_build_tests {

    //building-near-road tests
    @Test
    public void testBuildRoadSuccessful() {
        Game g = new Game("test");
        g.build(Building.ROAD, new Coordinates(26,23));
        assertTrue(g.getMap()[26][23] instanceof Road);
    }

    @Test
    public void testBuildRoadUnsuccessful() {
        Game g = new Game("test");
        g.build(Building.ROAD, new Coordinates(23,26));
        assertFalse(g.getMap()[23][26] instanceof Road);
    }

    @Test
    public void testBuildResiSuccessful() {
        Game g = new Game("test");
        g.build(Building.RESIDENTIAL, new Coordinates(24,22));
        assertTrue(g.getMap()[24][22] instanceof ResidentialZone);
    }

    @Test
    public void testBuildResiUnsuccessful() {
        Game g = new Game("test");
        g.build(Building.RESIDENTIAL, new Coordinates(0,0));
        assertFalse(g.getMap()[0][0] instanceof ResidentialZone);
    }

    @Test
    public void testBuildIndSuccessful() {
        Game g = new Game("test");
        g.build(Building.INDUSTRY, new Coordinates(24,22));
        assertTrue(g.getMap()[24][22] instanceof IndustrialZone);
    }

    @Test
    public void testBuildIndUnsuccessful() {
        Game g = new Game("test");
        g.build(Building.INDUSTRY, new Coordinates(0,0));
        assertFalse(g.getMap()[0][0] instanceof IndustrialZone);
    }

    @Test
    public void testBuildServSuccessful() {
        Game g = new Game("test");
        g.build(Building.SERVICE, new Coordinates(24,22));
        assertTrue(g.getMap()[24][22] instanceof ServiceZone);
    }

    @Test
    public void testBuildServUnsuccessful() {
        Game g = new Game("test");
        g.build(Building.SERVICE, new Coordinates(0,0));
        assertFalse(g.getMap()[0][0] instanceof ServiceZone);
    }

    @Test
    public void testBuildStadSuccessful() {
        Game g = new Game("test");
        g.build(Building.STADIUM, new Coordinates(22,22));
        assertTrue(g.getMap()[22][22] instanceof Stadium);
    }

    @Test
    public void testBuildStadUnsuccessful() {
        Game g = new Game("test");
        g.build(Building.STADIUM, new Coordinates(0,0));
        assertFalse(g.getMap()[0][0] instanceof Stadium);
    }

    @Test
    public void testBuildUniSuccessful() {
        Game g = new Game("test");
        g.build(Building.UNIVERSITY, new Coordinates(22,22));
        assertTrue(g.getMap()[22][22] instanceof University);
    }

    @Test
    public void testBuildUniUnsuccessful() {
        Game g = new Game("test");
        g.build(Building.UNIVERSITY, new Coordinates(0,0));
        assertFalse(g.getMap()[0][0] instanceof University);
    }

    @Test
    public void testBuildPPSuccessful() {
        Game g = new Game("test");
        g.build(Building.POWER_PLANT, new Coordinates(22,22));
        assertTrue(g.getMap()[22][22] instanceof PowerPlant);
    }

    @Test
    public void testBuildPPUnsuccessful() {
        Game g = new Game("test");
        g.build(Building.POWER_PLANT, new Coordinates(0,0));
        assertFalse(g.getMap()[0][0] instanceof PowerPlant);
    }

    @Test
    public void testBuildPoliceSuccessful() {
        Game g = new Game("test");
        g.build(Building.POLICE, new Coordinates(24,22));
        assertTrue(g.getMap()[24][22] instanceof Police);
    }

    @Test
    public void testBuildPoliceUnsuccessful() {
        Game g = new Game("test");
        g.build(Building.POLICE, new Coordinates(0,0));
        assertFalse(g.getMap()[0][0] instanceof Police);
    }

    @Test
    public void testBuildSchoolSuccessful() {
        Game g = new Game("test");
        g.build(Building.SCHOOL, new Coordinates(24,22));
        assertTrue(g.getMap()[24][22] instanceof School);
    }

    @Test
    public void testBuildSchoolUnsuccessful() {
        Game g = new Game("test");
        g.build(Building.SCHOOL, new Coordinates(0,0));
        assertFalse(g.getMap()[0][0] instanceof School);
    }

    //collision test
    @Test
    public void testBuildingCollision() {
        Game g = new Game("test");
        g.build(Building.RESIDENTIAL, new Coordinates(24,22));
        g.build(Building.POWER_PLANT, new Coordinates(22,22));
        assertTrue(g.getMap()[24][22] instanceof ResidentialZone);
    }

    //building on water tests
    @Test
    public void testBuildOnWaterSuccessful() {
        Game g = new Game("test");
        for (int i = 21; i > -1; --i) {
            g.build(Building.ROAD, new Coordinates(26,i));
        }
        g.build(Building.POLE, new Coordinates(25,0));
        assertTrue(g.getMap()[26][0] instanceof Road && g.getMap()[25][0] instanceof Pole);
    }

    @Test
    public void testBuildOnWaterUnsuccessful() {
        Game g = new Game("test");
        for (int i = 21; i > -1; --i) {
            g.build(Building.ROAD, new Coordinates(26,i));
        }
        g.build(Building.RESIDENTIAL, new Coordinates(24,0));
        assertFalse(g.getMap()[26][0] instanceof Road && g.getMap()[24][0] instanceof ResidentialZone);
    }
}