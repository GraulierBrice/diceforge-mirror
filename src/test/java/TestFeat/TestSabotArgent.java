package TestFeat;

import DiceForge.AI.LunarAI;
import DiceForge.Face.FaceCombinationAND;
import DiceForge.Player;
import org.junit.Before;
import org.junit.Test;
import DiceForge.Feat.SabotArgent;
import static org.junit.Assert.assertEquals;


public class TestSabotArgent {
    private SabotArgent sabot;

    @Before
    public void setUp() {
        sabot = new SabotArgent();
        sabot.setPlayer(new Player(new LunarAI(),"LunarAI"));
        sabot.getOwner().removeGold(1000);
        for(int i=0;i<6;i++) {
            sabot.getOwner().getDice(0).setFace(new FaceCombinationAND(1, 0, 0, 0), i);
            sabot.getOwner().getDice(1).setFace(new FaceCombinationAND(1, 0, 0, 0), i);

        }
    }

    @Test
    public void gotReward () {
        assertEquals(2,sabot.getOwner().getHonour());
        assertEquals(0,sabot.getOwner().getGold());
        sabot.effect();
        assertEquals(sabot.getOwner().getGold(),1);
        sabot.effect();
        assertEquals(sabot.getOwner().getGold(),2);
        }


}

