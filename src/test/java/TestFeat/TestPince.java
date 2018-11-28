package TestFeat;

import DiceForge.Face.FaceCombinationAND;
import DiceForge.Feat.Pince;
import DiceForge.Player;
import DiceForge.Referee;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestPince{
    private Pince pince;
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
        pince = new Pince();
        player.removeGold(100);
        pince.setPlayer(player);
        assertEquals(player.getHonour(),8);
        assertEquals(player.getGold(),4);


    }

}
