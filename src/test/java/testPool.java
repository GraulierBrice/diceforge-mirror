import DiceForge.Face.*;
import DiceForge.Player;
import DiceForge.Pool;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class testPool {

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

        Player player=new Player();
        player.addGold(12);

        assertEquals(pool.buy(player,PdS1),PdS1);
        assertEquals(player.getGold(),0);
        assertEquals(pool.isEmpty(),false);
        pool.removeFace(PdS1);
        pool.removeFace(g1);
        assertEquals(pool.isEmpty(),false);//ici on vérifie que l'on enlève pas plusieurs fois une face si elle existe en plusieurs fois dans la pool
        pool.removeFace(g1);
        assertEquals(pool.isEmpty(),true);
    }

}
