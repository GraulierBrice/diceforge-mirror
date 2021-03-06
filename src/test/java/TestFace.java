import DiceForge.AI.*;
import DiceForge.Face.*;
import DiceForge.Player;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestFace {
    Player player;

    @Before
    public void setUp() {
        player=new Player();
    }

    @Test
    public void faceGold() {
        FaceCombinationAND g1=new FaceCombinationAND(1,0,0,0);
        g1.giveReward(player);
        assertTrue(player.getGold()==1);
        FaceCombinationAND g18=new FaceCombinationAND(18,0,0,0);
        g18.giveReward(player);
        assertTrue(player.getGold()==12);
        assertEquals(g1.getKind(), Player.GOLD);
        assertEquals(g1.getReward(),"1G");
    }

    @Test
    public void faceSolarShard(){
        FaceCombinationAND SolarShard1=new FaceCombinationAND(0,0,1,0);
        SolarShard1.giveReward(player);
        assertTrue(player.getSolarShard()==1);
        FaceCombinationAND SolarShard8=new FaceCombinationAND(0,0,8,0);
        SolarShard8.giveReward(player);
        assertTrue(player.getSolarShard()==6);
        assertEquals(SolarShard1.getKind(),Player.SolarShard);
        assertEquals(SolarShard1.getReward(),"1SolarShard");
    }

    @Test
    public void faceLunarShard(){
        FaceCombinationAND LunarShard1=new FaceCombinationAND(0,1,0,0);
        LunarShard1.giveReward(player);
        assertTrue(player.getLunarShard()==1);
        FaceCombinationAND LunarShard8=new FaceCombinationAND(0,8,0,0);
        LunarShard8.giveReward(player);
        assertTrue(player.getLunarShard()==6);
        assertEquals(LunarShard1.getKind(),Player.LunarShard);
        assertEquals(LunarShard1.getReward(),"1LunarShard");

    }

    @Test
    public void FaceHonour(){
        FaceCombinationAND honour1=new FaceCombinationAND(0,0,0,1);
        honour1.giveReward(player);
        assertTrue(player.getHonour()==1);
        assertEquals(honour1.getKind(),Player.HONOUR);
        assertEquals(honour1.getReward(),"1H");

    }
}