import DiceForge.AI.RandomAI;
import DiceForge.Feat.Passeur;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestPasseur {
    private Passeur passeur=new Passeur();
    @Before
    public void setUp() {
        passeur = new Passeur();
        passeur.setPlayer(new RandomAI());
    }

    @Test
    public void honour() {
        assertEquals(12, passeur.getOwner().getHonour());
    }

}
