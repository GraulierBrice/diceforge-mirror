import DiceForge.AI.RandomAI;
import DiceForge.Feat.*;
import DiceForge.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestChest {
    private Chest chest;

    @Before
    public void setUp() {
        chest = new Chest();
        chest.setPlayer(new Player());
    }

    @Test
    public void MaxSolarShard() {
        assertEquals(9,chest.getOwner().getMaxSolarShard());
    }

    @Test
    public void MaxLunarShard() {
        assertEquals(9,chest.getOwner().getMaxLunarShard());
    }

    @Test
    public void MaxGold() {
        assertEquals(16,chest.getOwner().getMaxGold());
    }
}
