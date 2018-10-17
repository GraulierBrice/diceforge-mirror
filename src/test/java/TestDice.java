import org.junit.Test;
import DiceForge.Face.*;
import DiceForge.Dice;

import static org.junit.Assert.*;

public class TestDice {

	@Test public void diceMethods(){
		Face g1 = new FaceGold(1);
		Face g2 = new FaceGold(2);
		Dice dice = new Dice(g1,g1,g1,g1,g1,g1);
		dice.setFace(g2,2);
		assertEquals(dice.getFace(0),g1);
		assertEquals(dice.getFace(1),g1);
		assertEquals(dice.getFace(2),g2);
		assertEquals(dice.getFace(3),g1);
		assertEquals(dice.getFace(4),g1);
		assertEquals(dice.getFace(5),g1);
	}
	
}