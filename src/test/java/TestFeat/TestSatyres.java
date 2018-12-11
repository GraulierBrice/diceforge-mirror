package TestFeat;

import DiceForge.AI.*;
import DiceForge.Feat.Satyres;
import DiceForge.Player;
import DiceForge.Referee;
import org.junit.Before;
import org.junit.Test;
import DiceForge.Face.*;
import org.junit.jupiter.api.BeforeAll;


import static org.junit.Assert.assertEquals;

public class TestSatyres {
    Referee referee;
    Satyres satyres;

    @Before
    public void setup(){
        referee.getPlayers().removeAll(referee.getPlayers());
        referee=new Referee(new Player("1"), new Player("2"));
        satyres = new Satyres();
    }
    @Test
    public void gotReward () {

        referee.getEnnemyRoll();
        satyres.setPlayer(referee.getPlayer(0));
        assertEquals(6,satyres.getOwner().getHonour());
    }


}