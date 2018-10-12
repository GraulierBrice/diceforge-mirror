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
        FaceGold g3=new FaceGold(18);
        g3.giveReward(player);
        assertTrue(player.getGold()==12);
    }
}