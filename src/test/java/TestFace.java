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
        FaceGold g1=new FaceGold(1);
        g1.giveReward(randomAI);
        assertTrue(randomAI.getGold()==1);
        FaceGold g18=new FaceGold(18);
        g18.giveReward(randomAI);
        assertTrue(randomAI.getGold()==12);
    }

    @Test
    public void facePdS(){
        FacePdS PdS1=new FacePdS(1);
        PdS1.giveReward(randomAI);
        assertTrue(randomAI.getPdS()==1);
        FacePdS PdS8=new FacePdS(8);
        PdS8.giveReward(randomAI);
        assertTrue(randomAI.getPdS()==6);
    }

    @Test
    public void facePdL(){
        FacePdL PdL1=new FacePdL(1);
        PdL1.giveReward(randomAI);
        assertTrue(randomAI.getPdL()==1);
        FacePdL PdL8=new FacePdL(8);
        PdL8.giveReward(randomAI);
        assertTrue(randomAI.getPdL()==6);
    }

    @Test
    public void FaceHonour(){
        FaceHonour honour1=new FaceHonour(1);
        honour1.giveReward(randomAI);
        assertTrue(randomAI.getHonour()==1);
    }
}