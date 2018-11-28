package TestFeat;

import DiceForge.AI.*;
import DiceForge.Feat.MiroirAbyssal;
import DiceForge.*;
import org.junit.Before;
import org.junit.Test;
import DiceForge.Face.*;


import static org.junit.Assert.assertEquals;

public class TestMiroirAbyssal {
    private MiroirAbyssal mirror;
    private Referee ref;
    private Player p1 = new Player();
    private Player p2 = new Player();


    @Before
    public void setUp() {
        mirror = new MiroirAbyssal();
        ref=new Referee(p1,p2);
        mirror.setPlayer(p1);

        for(int i = 0; i<6; i++){
            p1.getDice(0).setFace(new FaceCombinationAND(0,0,0,0),i);
            p1.getDice(1).setFace(new FaceCombinationAND(0,0,0,0),i);

            p2.getDice(0).setFace(new FaceCombinationAND(1,0,0,0),i);
            p2.getDice(1).setFace(new FaceCombinationAND(1,0,0,0),i);

            mirror.getOwner().getDice(0).getFace(i).setKind("mirror");
        }
        ref.getEnnemyRoll();
        p1.removeGold(1000);
    }

    @Test
    public void tookOtherFace() {
        assertEquals("mirror",p1.getDice(0).getFace(1).getKind());
        p1.faveur();
        assertEquals(p1.getSolarShard(),0);
        assertEquals(p1.getLunarShard(),0);
        for(int i=0;i<6;i++){
            mirror.getOwner().getDice(1).getFace(i).setKind("mirror");
        }
        p1.faveur();
        assertEquals(p1.getGold(),3);

    }


}