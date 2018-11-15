import DiceForge.AI.RandomAI;
import DiceForge.Feat.Meduse;
import DiceForge.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestMeduse {
    private Meduse meduse=new Meduse();
    @Before
    public void setUp() {
        meduse = new Meduse();
        meduse.setPlayer(new Player());
    }

    @Test
    public void honour() {
        assertEquals(14, meduse.getOwner().getHonour());
    }

}
