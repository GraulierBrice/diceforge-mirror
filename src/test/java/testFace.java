import DiceForge.Player;
import DiceForge.Face.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class testFace {
    @Test
    public void faceGold() {
        Player player=new Player();
        FaceGold g1=new FaceGold(1);
        g1.giveReward(player);
        assertTrue(player.getGold()==1);
        FaceGold g18=new FaceGold(18);
        g18.giveReward(player);
        assertTrue(player.getGold()==12);
    }

    @Test public void facePdS(){
        Player player=new Player();
        FacePdS PdS1=new FacePdS(1);
        PdS1.giveReward(player);
        assertTrue(player.getPdS()==1);
        FacePdS PdS8=new FacePdS(8);
        PdS8.giveReward(player);
        assertTrue(player.getPdS()==6);
    }
}