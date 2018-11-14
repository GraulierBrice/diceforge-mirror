import DiceForge.Player;
import org.junit.Test;
import DiceForge.Face.*;
import DiceForge.Dice;

import static org.junit.Assert.*;

public class TestDice {

	@Test public void diceMethods(){
		FaceCombinationAND g1 = new FaceCombinationAND(1,0,0,0);
		FaceCombinationAND g2 = new FaceCombinationAND(2,0,0,0);
		Dice dice = new Dice(g1,g1,g1,g1,g1,new FaceCombinationAND(0,0,0,5));
		dice.setFace(g2,2);
		assertEquals(dice.getFace(0),g1);
		assertEquals(dice.getFace(1),g1);
		assertEquals(dice.getFace(2),g2);
		assertEquals(dice.getFace(3),g1);
		assertEquals(dice.getFace(4),g1);
		assertEquals(dice.faceNotOfThisKind(Player.LunarShard),0);
		assertEquals(dice.faceNotOfThisKind(Player.GOLD),0);//1G ne compte pas comme une FaceGold i.e on souhaite la remplacer par n'importe quoi d'autre
        Dice dice2 = new Dice(g2,g2,g2,g2,g2,new FaceCombinationAND(0,0,0,5));
        assertEquals(dice2.faceNotOfThisKind(Player.GOLD),5);
    }
	
}