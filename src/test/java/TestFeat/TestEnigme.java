package TestFeat;

import DiceForge.Face.FaceCombinationAND;
import DiceForge.Player;
import DiceForge.Feat.Enigme;
import DiceForge.Referee;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestEnigme {
    private Enigme pince;
    private Player player=new Player();;
    private Referee referee;
    @Before
    public void setUp() {
        referee=new Referee(player);
    }

    @Test
    public void effect(){
        player.getDice(0).setFace(new FaceCombinationAND(1,0,0,0),5);
        player.getDice(1).setFace(new FaceCombinationAND(1,0,0,0),4);
        player.getDice(1).setFace(new FaceCombinationAND(1,0,0,0),5);
        pince = new Enigme();
        player.removeGold(100);
        pince.setPlayer(player);
        assertEquals(player.getHonour(),10);
        assertEquals(player.getGold(),4);


    }
}
