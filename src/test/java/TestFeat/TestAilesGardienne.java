package TestFeat;

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
        ailes.setPlayer(new Player(new SolarAI(),"LunarAI"));
    }

    public void remove(){
        ailes.getOwner().removeGold(1000);
        ailes.getOwner().removeSolarShard(1000);
        ailes.getOwner().removeLunarShard(1000);
        ailes.getOwner().removeHonour(1000);
    }

    @Test
    public void gotReward () {
        remove();

        ailes.getOwner().addLunarShard(6);
        ailes.getOwner().addSolarShard(4);
        ailes.effect();
        assertEquals(ailes.getOwner().getLunarShard(),6);
        assertEquals(ailes.getOwner().getSolarShard(),5);
        remove();

        ailes.getOwner().addLunarShard(4);
        ailes.getOwner().addSolarShard(6);
        ailes.effect();
        assertEquals(ailes.getOwner().getLunarShard(),4);
        assertEquals(ailes.getOwner().getSolarShard(),6);
        remove();

        ailes.getOwner().addLunarShard(5);
        ailes.getOwner().addSolarShard(2);
        ailes.effect();
        assertEquals(ailes.getOwner().getLunarShard(),5);
        assertEquals(ailes.getOwner().getSolarShard(),2);
        remove();

        ailes.getOwner().addLunarShard(1);
        ailes.getOwner().addSolarShard(5);
        ailes.effect();
        assertEquals(ailes.getOwner().getLunarShard(),1);
        assertEquals(ailes.getOwner().getSolarShard(),5);
        remove();

        ailes.getOwner().addLunarShard(5);
        ailes.getOwner().addSolarShard(4);
        ailes.effect();
        assertEquals(ailes.getOwner().getLunarShard(),5);
        assertEquals(ailes.getOwner().getSolarShard(),4);
        remove();

        ailes.getOwner().addLunarShard(4);
        ailes.getOwner().addSolarShard(5);
        ailes.effect();
        assertEquals(ailes.getOwner().getLunarShard(),4);
        assertEquals(ailes.getOwner().getSolarShard(),5);
        remove();


        ailes.getOwner().addLunarShard(2);
        ailes.getOwner().addSolarShard(2);
        ailes.effect();
        assertEquals(ailes.getOwner().getLunarShard(),2);
        assertEquals(ailes.getOwner().getSolarShard(),2);
        remove();

        ailes.getOwner().addLunarShard(0);
        ailes.getOwner().addSolarShard(6);
        ailes.effect();
        assertEquals(ailes.getOwner().getGold(),1);
        remove();
        ailes.getOwner().addLunarShard(1);
        ailes.getOwner().addSolarShard(6);
        ailes.effect();
        assertEquals(ailes.getOwner().getGold(),1);
        remove();
        ailes.getOwner().addLunarShard(2);
        ailes.getOwner().addSolarShard(6);
        ailes.effect();
        assertEquals(ailes.getOwner().getGold(),1);
        remove();
        ailes.getOwner().addLunarShard(3);
        ailes.getOwner().addSolarShard(6);
        ailes.effect();
        assertEquals(ailes.getOwner().getGold(),1);
        remove();
        ailes.getOwner().addLunarShard(5);
        ailes.getOwner().addSolarShard(6);
        ailes.effect();
        assertEquals(ailes.getOwner().getGold(),1);
        remove();
        ailes.getOwner().addLunarShard(6);
        ailes.getOwner().addSolarShard(6);
        ailes.effect();
        assertEquals(ailes.getOwner().getGold(),1);
        remove();
        ailes.getOwner().addLunarShard(5);
        ailes.getOwner().addSolarShard(5);
        ailes.effect();
        assertEquals(ailes.getOwner().getGold(),1);
        remove();
        ailes.getOwner().addLunarShard(6);
        ailes.getOwner().addSolarShard(5);
        ailes.effect();
        assertEquals(ailes.getOwner().getGold(),1);
        remove();


    }


}

