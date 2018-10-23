import DiceForge.Feat.Chest;
import DiceForge.Feat.Feat;
import DiceForge.Feat.Hammer;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import DiceForge.*;

import java.util.ArrayList;

public class TestIsland {

    private Island island;
    ArrayList<Feat> feats1;

    @Before
    public void Setup(){
        feats1 =new ArrayList<>();
        feats1.add(new Hammer());
        feats1.add(new Chest());
        island = new Island(feats1);
    }

    @Test
    public void removeFeats() {
        assertEquals(2, island.numOfFeats());
        assertTrue(island.isIn(Hammer.class));
        island.removeFeat(Hammer.class);
        assertEquals(1, island.numOfFeats());
        assertTrue( !island.isIn(Hammer.class));
    }


}
