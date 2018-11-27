import DiceForge.Feat.Chest;
import DiceForge.Feat.Feat;
import DiceForge.Feat.Hammer;
import DiceForge.Feat.nameFeat;
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
        assertTrue(island.doesItCost(Player.LunarShard));
        assertFalse(island.doesItCost(Player.SolarShard));
        assertTrue(island.isIn(nameFeat.Hammer));

        Feat feat=feats1.get(1);
        assertEquals(island.lowestPriceOfFeat(Player.LunarShard),feat);

        island.removeFeat(nameFeat.Hammer);
        assertEquals(1, island.numOfFeats());
        assertTrue( !island.isIn(nameFeat.Hammer));

        Feat feat1=feats1.get(0);
        assertEquals(island.lowestPriceOfFeat(Player.LunarShard),feat1);

        island.removeFeat(nameFeat.Chest);
        assertTrue(island.isEmpty());
    }


}
