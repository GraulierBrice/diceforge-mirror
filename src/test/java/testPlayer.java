import DiceForge.Player;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestPlayer {
    @Test public void playerMethods(){
        Player player=new Player();
        player.addGold(11);
        assertTrue(player.getGold()==11);
        player.addGold(3);
        assertTrue(player.getGold()==12);
        player.removeGold(8);
        assertTrue(player.getGold()==4);
        player.removeGold(5);
        assertTrue(player.getGold()==0);

        player.addHonour(36);
        assertTrue(player.getHonour()==36);

        player.addPdS(5);
        assertTrue(player.getPdS()==5);
        player.addPdS(2);
        assertTrue(player.getPdS()==6);
        player.removePdS(3);
        assertTrue(player.getPdS()==3);
        player.removePdS(5);
        assertTrue(player.getPdS()==0);

        player.addPdL(4);
        assertTrue(player.getPdL()==4);
        player.addPdL(4);
        assertTrue(player.getPdL()==6);
        player.removePdL(2);
        assertTrue(player.getPdL()==4);
        player.removePdL(6);
        assertTrue(player.getPdL()==0);
    }
}
