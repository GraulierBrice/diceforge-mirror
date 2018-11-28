package TestFeat;

import DiceForge.AI.*;
import DiceForge.Feat.Satyres;
import DiceForge.Player;
import DiceForge.Referee;
import org.junit.Before;
import org.junit.Test;
import DiceForge.Face.*;


import static org.junit.Assert.assertEquals;

public class TestSatyres {

    @Test
    public void gotReward () {
        Referee referee=new Referee(new Player("1"));
        Satyres satyres = new Satyres();
        referee.getEnnemyRoll();
        satyres.setPlayer(referee.getPlayer(0));
        assertEquals(6,satyres.getOwner().getHonour());
    }


}