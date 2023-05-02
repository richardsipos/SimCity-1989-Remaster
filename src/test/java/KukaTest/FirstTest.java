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
}