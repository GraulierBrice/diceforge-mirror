import DiceForge.AI.RandomAI;
import DiceForge.Feat.Ancien;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestAncien {
  private Ancien ancien;

    @Before
    public void setUp() {
        ancien = new Ancien();
        ancien.setPlayer(new RandomAI());
    }

    @Test
    public void notEnoughGold() {
        assertEquals(0,ancien.getOwner().getGold());
        assertEquals(0,ancien.getOwner().getHonour());
        ancien.effect();
        assertEquals(0,ancien.getOwner().getGold());
        assertEquals(0,ancien.getOwner().getHonour());
    }

    @Test
    public void enoughGold(){
        ancien.getOwner().addGold(5);
        assertEquals(5,ancien.getOwner().getGold());
        assertEquals(0,ancien.getOwner().getHonour());
        ancien.effect();
        assertEquals(2,ancien.getOwner().getGold());
        assertEquals(4,ancien.getOwner().getHonour());
    }

}

