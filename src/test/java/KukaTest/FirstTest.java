package KukaTest;

import KukaPest.Model.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FirstTest {

    //first tries
    @Test
    public void testName() {
        Game g = new Game("asd");
        assertEquals("asd", g.getCityName());
    }

    @Test
    public void testMoney() {
        Game g = new Game("asd");
        assertEquals(10000, g.getFunds());
    }

    @Test
    public void testPopulation() {
        Game g = new Game("test");
        assertEquals(0, g.getPopulation());
    }
    @Test
    public void testUpkeep() {
        Game g = new Game("test");
        g.stepGame();
        assertNotEquals(10000, g.getFunds());
    }
}