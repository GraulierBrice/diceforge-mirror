package TestFeat;

import DiceForge.Player;
import org.junit.Before;
import org.junit.Test;
import DiceForge.Feat.Hydre;
import DiceForge.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertEquals;

public class TestHydre {
    private Hydre hydre=new Hydre();

        @Before
        public void setUp() {
            hydre = new Hydre();
            hydre.setPlayer(new Player());
        }

        @Test
        public void honour() {
            assertEquals(26, hydre.getOwner().getHonour());
        }

    }
