import DiceForge.Face.*;
import DiceForge.AI.*;
import DiceForge.Pool;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class TestPool {

    @Test public void poolMethods(){
        FaceCombinationAND g1=new FaceCombinationAND(1,0,0,0);
        FaceCombinationAND PdS1=new FaceCombinationAND(0,0,1,0);
        ArrayList<Face> faces=new ArrayList<Face>();
        faces.add(g1);
        faces.add(g1);
        faces.add(PdS1);
        Pool pool=new Pool(4,faces);

        assertEquals(pool.getPrice(),4);
        assertEquals(pool.getFace(0),g1);
        assertEquals(pool.getFace(2),PdS1);
        pool.setPrice(12);
        assertEquals(pool.getPrice(),12);

        RandomAI randomAI=new RandomAI();
        randomAI.addGold(12);

        assertTrue(pool.kindOfPool(""));
        assertTrue(pool.kindOfPool("G"));

        assertEquals(pool.buy(randomAI,PdS1),PdS1);
        assertEquals(randomAI.getGold(),0);
        pool.setPrice(0);
        assertEquals(pool.buy(randomAI,g1),g1);
        assertEquals(pool.isEmpty(),false);
        assertEquals(pool.buy(randomAI,g1),g1);
        assertEquals(pool.isEmpty(),true);

    }

}
