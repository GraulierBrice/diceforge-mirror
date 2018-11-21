import DiceForge.AI.*;
import DiceForge.Feat.AilesGardienne;
import DiceForge.Player;
import org.junit.Before;
import org.junit.Test;
import DiceForge.Face.*;


import static org.junit.Assert.assertEquals;

public class TestAilesGardienne {
  private AilesGardienne ailes;

    @Before
    public void setUp() {
        ailes = new AilesGardienne();
        ailes.setPlayer(new Player(new LunarAI(),"LunarAI"));
        ailes.getOwner().removeGold(1000);
    }

    @Test
    public void gotReward () {
        assertEquals(0,ailes.getOwner().getLunarShard());
        ailes.effect();
        assertEquals(ailes.getOwner().getGold(),1);
        ailes.effect();
        assertEquals(ailes.getOwner().getGold(),2);
        assertEquals(ailes.getOwner().getLunarShard(),0);
    }


}

