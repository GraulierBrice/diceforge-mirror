import DiceForge.AI.RandomAI;
import DiceForge.Feat.HerbesFolles;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestHerbesFolles {
    private HerbesFolles herbesFolles;

    @Before
    public void setUp() {
        herbesFolles = new HerbesFolles();
        herbesFolles.setPlayer(new RandomAI("random"));
    }

    @Test
    public void Gold(){
        assertEquals(3,herbesFolles.getOwner().getGold());
        herbesFolles.effect();
        assertEquals(6,herbesFolles.getOwner().getGold());
    }

    @Test
    public void LunarShard() {
        assertEquals(3,herbesFolles.getOwner().getLunarShard());
        herbesFolles.effect();
        assertEquals(6,herbesFolles.getOwner().getLunarShard());
    }
}
