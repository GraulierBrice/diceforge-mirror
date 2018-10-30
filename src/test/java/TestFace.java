import DiceForge.AI.*;
import DiceForge.Face.*;
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
        FaceGold g1=new FaceGold(1,"G");
        g1.giveReward(randomAI);
        assertTrue(randomAI.getGold()==1);
        FaceGold g18=new FaceGold(18,"G");
        g18.giveReward(randomAI);
        assertTrue(randomAI.getGold()==12);
        assertEquals(g1.getKind(),"G");
        assertEquals(g1.getReward(),"1G");
    }

    @Test
    public void facePdS(){
        FacePdS PdS1=new FacePdS(1,"PdS");
        PdS1.giveReward(randomAI);
        assertTrue(randomAI.getPdS()==1);
        FacePdS PdS8=new FacePdS(8,"PdS");
        PdS8.giveReward(randomAI);
        assertTrue(randomAI.getPdS()==6);
        assertEquals(PdS1.getKind(),"PdS");
        assertEquals(PdS1.getReward(),"1PdS");
    }

    @Test
    public void facePdL(){
        FacePdL PdL1=new FacePdL(1,"PdL");
        PdL1.giveReward(randomAI);
        assertTrue(randomAI.getPdL()==1);
        FacePdL PdL8=new FacePdL(8,"PdL");
        PdL8.giveReward(randomAI);
        assertTrue(randomAI.getPdL()==6);
        assertEquals(PdL1.getKind(),"PdL");
        assertEquals(PdL1.getReward(),"1PdL");

    }

    @Test
    public void FaceHonour(){
        FaceHonour honour1=new FaceHonour(1,"H");
        honour1.giveReward(randomAI);
        assertTrue(randomAI.getHonour()==1);
        assertEquals(honour1.getKind(),"H");
        assertEquals(honour1.getReward(),"1H");

    }
}