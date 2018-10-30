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
    public void methodsFeats() {
        assertEquals(2, island.numOfFeats());
        assertTrue(island.doesItCost("PdL"));
        assertFalse(island.doesItCost("PdS"));
        assertTrue(island.isIn(Hammer.class));

        Feat feat=feats1.get(1);
        assertEquals(island.lowestPriceOfFeat("PdL"),feat);

        island.removeFeat(Hammer.class);
        assertEquals(1, island.numOfFeats());
        assertTrue( !island.isIn(Hammer.class));

        Feat feat1=feats1.get(0);
        assertEquals(island.lowestPriceOfFeat("PdL"),feat1);

        island.removeFeat(Chest.class);
        assertTrue(island.isEmpty());
    }


}
