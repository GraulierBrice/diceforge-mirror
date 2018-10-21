import DiceForge.AI.RandomAI;
import DiceForge.Feat.*;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class TestHammer {
    private Hammer hammer ;

    @Before
    public void setUp() {
        hammer = new Hammer();
        hammer.setPlayer(new RandomAI());
    }

    @Test
    public void Level()
    {
        assertEquals(0, hammer.getLevel());
        hammer.effect(15);
        assertEquals(1, hammer.getLevel());
    }

    @Test
    public void Gold() {
        hammer.effect(12);
        assertEquals(12, hammer.getGold());
    }

    @Test
    public void Honour(){
        assertEquals(0, hammer.getOwner().getHonour());
        hammer.effect(15);
        assertEquals(10, hammer.getOwner().getHonour());
    }
}
