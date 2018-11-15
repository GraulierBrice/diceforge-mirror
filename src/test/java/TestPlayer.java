
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

        player.addSolarShard(5);
        assertTrue(player.getSolarShard()==5);
        player.addSolarShard(2);
        assertTrue(player.getSolarShard()==6);
        player.removeSolarShard(3);
        assertTrue(player.getSolarShard()==3);
        player.removeSolarShard(5);
        assertTrue(player.getSolarShard()==0);

        player.addLunarShard(4);
        assertTrue(player.getLunarShard()==4);
        player.addLunarShard(4);
        assertTrue(player.getLunarShard()==6);
        player.removeLunarShard(2);
        assertTrue(player.getLunarShard()==4);
        player.removeLunarShard(6);
        assertTrue(player.getLunarShard()==0);
        assertTrue(player.getMaxLunarShard()==6);
        assertTrue(player.getMaxSolarShard()==6);
        assertTrue(player.getMaxGold()==12);
        assertTrue(player.getCurrentIsland()==0);




    }
}
