import DiceForge.AI.*;
import DiceForge.Face.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestFace {
    @Test
    public void faceGold() {
        RandomAI randomAI=new RandomAI();
        FaceGold g1=new FaceGold(1);
        g1.giveReward(randomAI);
        assertTrue(randomAI.getGold()==1);
        FaceGold g18=new FaceGold(18);
        g18.giveReward(randomAI);
        assertTrue(randomAI.getGold()==12);
    }

    @Test public void facePdS(){
        RandomAI randomAI=new RandomAI();
        FacePdS PdS1=new FacePdS(1);
        PdS1.giveReward(randomAI);
        assertTrue(randomAI.getPdS()==1);
        FacePdS PdS8=new FacePdS(8);
        PdS8.giveReward(randomAI);
        assertTrue(randomAI.getPdS()==6);
    }

    @Test public void facePdL(){
        RandomAI randomAI=new RandomAI();
        FacePdL PdL1=new FacePdL(1);
        PdL1.giveReward(randomAI);
        assertTrue(randomAI.getPdL()==1);
        FacePdL PdL8=new FacePdL(8);
        PdL8.giveReward(randomAI);
        assertTrue(randomAI.getPdL()==6);
    }

    @Test public void FaceHonour(){
        RandomAI RandomAI=new RandomAI();
        FaceHonour honour1=new FaceHonour(1);
        honour1.giveReward(RandomAI);
        assertTrue(RandomAI.getHonour()==1);
    }
}