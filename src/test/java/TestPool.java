import DiceForge.Face.*;
import DiceForge.AI.*;
import DiceForge.Pool;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class TestPool {

    @Test public void poolMethods(){
        FaceGold g1=new FaceGold(1);
        FacePdS PdS1=new FacePdS(1);
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

        assertEquals(pool.buy(randomAI,PdS1),PdS1);
        assertEquals(randomAI.getGold(),0);
        pool.setPrice(0);
        assertEquals(pool.buy(randomAI,g1),g1);
        assertEquals(pool.isEmpty(),false);
        assertEquals(pool.buy(randomAI,g1),g1);
        assertEquals(pool.isEmpty(),true);
    }

}
