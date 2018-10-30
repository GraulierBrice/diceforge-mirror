import DiceForge.Player;
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

        AI.addPdS(5);
        assertTrue(AI.getPdS()==5);
        AI.addPdS(2);
        assertTrue(AI.getPdS()==6);
        AI.removePdS(3);
        assertTrue(AI.getPdS()==3);
        AI.removePdS(5);
        assertTrue(AI.getPdS()==0);

        AI.addPdL(4);
        assertTrue(AI.getPdL()==4);
        AI.addPdL(4);
        assertTrue(AI.getPdL()==6);
        AI.removePdL(2);
        assertTrue(AI.getPdL()==4);
        AI.removePdL(6);
        assertTrue(AI.getPdL()==0);
        assertTrue(AI.getMaxPdL()==6);
        assertTrue(AI.getMaxPdS()==6);
        assertTrue(AI.getMaxGold()==12);
        assertTrue(AI.getCurrentIsland()==-1);




    }
}
