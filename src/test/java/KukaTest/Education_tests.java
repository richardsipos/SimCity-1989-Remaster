package KukaTest;

import KukaPest.Model.Game;
import KukaPest.Model.Helper.Building;
import KukaPest.Model.Helper.Coordinates;
import KukaPest.Model.Helper.EduLevel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Education_tests {

    @Test
    public void BaseEducation() throws InterruptedException {
        Game g = new Game("test");
        for (int i = 21; i > -1; --i) {
            g.build(Building.ROAD, new Coordinates(26,i));
        }
        g.build(Building.RESIDENTIAL, new Coordinates(24,22));
        g.build(Building.SERVICE, new Coordinates(24,20));
        g.build(Building.POWER_PLANT, new Coordinates(22,16));
        assertTrue(g.getCitizens().get(0).getEducation() == EduLevel.BASIC);
    }

    /*@Test
    public void MidEducation() throws InterruptedException {
        Game g = new Game("test");
        for (int i = 21; i > -1; --i) {
            g.build(Building.ROAD, new Coordinates(26,i));
        }
        g.build(Building.RESIDENTIAL, new Coordinates(2422));
        g.build(Building.SERVICE, new Coordinates(24,20));
        g.build(Building.POWER_PLANT, new Coordinates(22,16));
        g.build(Building.SCHOOL, new Coordinates(24,12));
        Thread.sleep(5000);
        assertTrue(g.getCitizens().get(0).education == 2);
    }*/

    /*@Test
    public void HighEducation() throws InterruptedException {
        Game g = new Game("test");
        for (int i = 21; i > -1; --i) {
            g.build(Building.ROAD, new Coordinates(26,i));
        }
        g.build(Building.RESIDENTIAL, new Coordinates(2422));
        g.build(Building.SERVICE, new Coordinates(24,20));
        g.build(Building.POWER_PLANT, new Coordinates(22,16));
        g.build(Building.SCHOOL, new Coordinates(24,12));
        g.build(Building.UNIVERSITY, new Coordinates(22,8));
        Thread.sleep(20000);
        assertTrue(g.getCitizens().get(0).education == 3);
    }*/
}
