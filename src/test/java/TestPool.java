import DiceForge.Face.*;
import DiceForge.AI.*;
import DiceForge.Player;
import DiceForge.Pool;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class TestPool {

    @Test public void poolMethods(){
        FaceCombinationAND g1=new FaceCombinationAND(1,0,0,0);
        FaceCombinationAND SolarShard1=new FaceCombinationAND(0,0,1,0);
        ArrayList<Face> faces=new ArrayList<Face>();
        faces.add(g1);
        faces.add(g1);
        faces.add(SolarShard1);
        Pool pool=new Pool(4,faces);

        assertEquals(pool.getPrice(),4);
        assertEquals(pool.getFace(0),g1);
        assertEquals(pool.getFace(2),SolarShard1);
        pool.setPrice(12);
        assertEquals(pool.getPrice(),12);

        Player player=new Player();
        player.addGold(12);

        assertTrue(pool.kindOfPool(""));
        assertTrue(pool.kindOfPool(Player.GOLD));

        assertEquals(pool.buy(player,SolarShard1),SolarShard1);
        assertEquals(player.getGold(),0);
        pool.setPrice(0);
        assertEquals(pool.buy(player,g1),g1);
        assertEquals(pool.isEmpty(),false);
        assertEquals(pool.buy(player,g1),g1);
        assertEquals(pool.isEmpty(),true);

    }

}
