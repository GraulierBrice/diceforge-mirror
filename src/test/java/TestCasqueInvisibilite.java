import DiceForge.AI.RandomAI;
import DiceForge.Feat.CasqueInvisibilite;
import DiceForge.Player;
import org.junit.Before;
import org.junit.Test;
import DiceForge.Face.*;


import static org.junit.Assert.assertEquals;

public class TestCasqueInvisibilite {
  private CasqueInvisibilite casque;

    @Before
    public void setUp() {
        casque = new CasqueInvisibilite();
        casque.setPlayer(new Player());
        for(int i = 0; i<6; i++){
            casque.getOwner().getDice(0).setFace(new FaceCombinationAND(0,0,0,0),i);
            casque.getOwner().getDice(1).setFace(new FaceCombinationAND(1,1,1,0),i);
            casque.getOwner().getDice(0).getFace(i).setKind("three");
        }
        casque.getOwner().removeGold(1000);
    }

    @Test
    public void gotThreeTimes() {
        assertEquals("three",casque.getOwner().getDice(0).getFace(1).getKind());
        casque.getOwner().faveur();
        assertEquals(casque.getOwner().getGold(),3);
        assertEquals(casque.getOwner().getSolarShard(),3);
        assertEquals(casque.getOwner().getLunarShard(),3);
    }


}

