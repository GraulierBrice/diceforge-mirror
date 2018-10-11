import org.junit.Test;
import static org.junit.Assert.*;

public class testPlayer {
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

        player.addHonor(36);
        assertTrue(player.getHonor()==36);

        player.addPdF(5);
        assertTrue(player.getPdF()==5);
        player.addPdF(2);
        assertTrue(player.getPdF()==6);
        player.removePdF(3);
        assertTrue(player.getPdF()==3);
        player.removePdF(5);
        assertTrue(player.getPdF()==0);

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
