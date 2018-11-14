import DiceForge.AI.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestPlayer {
    @Test public void AIMethods(){
        RandomAI AI=new RandomAI();
        AI.addGold(11);
        assertTrue(AI.getGold()==11);
        AI.addGold(3);
        assertTrue(AI.getGold()==12);
        AI.removeGold(8);
        assertTrue(AI.getGold()==4);
        AI.removeGold(5);
        assertTrue(AI.getGold()==0);

        AI.addHonour(36);
        assertTrue(AI.getHonour()==36);

        AI.addSolarShard(5);
        assertTrue(AI.getSolarShard()==5);
        AI.addSolarShard(2);
        assertTrue(AI.getSolarShard()==6);
        AI.removeSolarShard(3);
        assertTrue(AI.getSolarShard()==3);
        AI.removeSolarShard(5);
        assertTrue(AI.getSolarShard()==0);

        AI.addLunarShard(4);
        assertTrue(AI.getLunarShard()==4);
        AI.addLunarShard(4);
        assertTrue(AI.getLunarShard()==6);
        AI.removeLunarShard(2);
        assertTrue(AI.getLunarShard()==4);
        AI.removeLunarShard(6);
        assertTrue(AI.getLunarShard()==0);
        assertTrue(AI.getMaxLunarShard()==6);
        assertTrue(AI.getMaxSolarShard()==6);
        assertTrue(AI.getMaxGold()==12);
        assertTrue(AI.getCurrentIsland()==0);




    }
}
