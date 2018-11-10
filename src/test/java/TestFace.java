import DiceForge.AI.*;
import DiceForge.Face.*;
import DiceForge.Player;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestFace {
    RandomAI randomAI;

    @Before
    public void setUp() {
        randomAI=new RandomAI();
    }

    @Test
    public void faceGold() {
        FaceCombinationAND g1=new FaceCombinationAND(1,0,0,0);
        g1.giveReward(randomAI);
        assertTrue(randomAI.getGold()==1);
        FaceCombinationAND g18=new FaceCombinationAND(18,0,0,0);
        g18.giveReward(randomAI);
        assertTrue(randomAI.getGold()==12);
        assertEquals(g1.getKind(), Player.GOLD);
        assertEquals(g1.getReward(),"1G");
    }

    @Test
    public void facePdS(){
        FaceCombinationAND PdS1=new FaceCombinationAND(0,0,1,0);
        PdS1.giveReward(randomAI);
        assertTrue(randomAI.getPdS()==1);
        FaceCombinationAND PdS8=new FaceCombinationAND(0,0,8,0);
        PdS8.giveReward(randomAI);
        assertTrue(randomAI.getPdS()==6);
        assertEquals(PdS1.getKind(),Player.PDS);
        assertEquals(PdS1.getReward(),"1PdS");
    }

    @Test
    public void facePdL(){
        FaceCombinationAND PdL1=new FaceCombinationAND(0,1,0,0);
        PdL1.giveReward(randomAI);
        assertTrue(randomAI.getPdL()==1);
        FaceCombinationAND PdL8=new FaceCombinationAND(0,8,0,0);
        PdL8.giveReward(randomAI);
        assertTrue(randomAI.getPdL()==6);
        assertEquals(PdL1.getKind(),Player.PDS);
        assertEquals(PdL1.getReward(),"1PdL");

    }

    @Test
    public void FaceHonour(){
        FaceCombinationAND honour1=new FaceCombinationAND(0,0,0,1);
        honour1.giveReward(randomAI);
        assertTrue(randomAI.getHonour()==1);
        assertEquals(honour1.getKind(),Player.HONOUR);
        assertEquals(honour1.getReward(),"1H");

    }
}