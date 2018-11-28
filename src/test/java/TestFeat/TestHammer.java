package TestFeat;

import DiceForge.AI.*;
import DiceForge.Feat.*;
import DiceForge.Player;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class TestHammer {
    private Hammer hammer ;

    @Before
    public void setUp() {
        hammer = new Hammer();
        hammer.setPlayer(new Player());
    }

    @Test
    public void Level()
    {
        assertEquals(0, hammer.getLevel());
        hammer.effect(15);
        assertEquals(1, hammer.getLevel());
    }

    @Test
    public void Gold() {
        hammer.effect(12);
        assertEquals(12, hammer.getGold());
    }

    @Test
    public void Honour(){
        assertEquals(0, hammer.getOwner().getHonour());
        hammer.effect(15);
        assertEquals(10, hammer.getOwner().getHonour());
    }

    @Test
    public void hammerLvl(){
        Player player=new Player(new LunarAI(),"lunar");
        Hammer hammer=new Hammer();
        hammer.setPlayer(player);
        player.addGold(10);
        assertEquals(hammer.getGold(),5);
        player.addGold(1);
        assertEquals(hammer.getGold(),6);
        player.removeGold(1000);
        player.addGold(1);
        assertEquals(hammer.getGold(),6);
        player.addGold(17);
        assertEquals(hammer.getLevel(),1);
        assertEquals(hammer.getGold(),4);
        player.addGold(13);
        assertEquals(hammer.getLevel(),2);
        assertEquals(hammer.getGold(),0);
        assertEquals(player.getGold(),7);

    }
}
