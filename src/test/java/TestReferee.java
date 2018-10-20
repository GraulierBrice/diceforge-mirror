
import org.junit.Test;
import static org.junit.Assert.*;
import DiceForge.*;

public class TestReferee {

    @Test public void refereeMethods() {
        Referee referee=new Referee(4,"random");
        Forge forge=new Forge(referee);
        referee.addForge(forge);

        assertEquals(referee.getNumberPlayer(),4);
        assertEquals(referee.getMaxRound(),9);
        assertEquals(referee.getRound(),1);
        for(int i=0;i<referee.getNumberPlayer();i++) {
            assertEquals(referee.getTurnPlayer(),i);
            referee.nextPlayer();
            assertEquals(referee.getPlayer(i).getHonour(),0);
            referee.getPlayer(i).addHonour(12*(i+1));
            assertEquals(referee.getPlayer(i).getHonour(),12*(i+1));
       }
        assertEquals(referee.honour()[0],referee.getPlayer(3).getHonour());
        assertEquals(referee.honour()[1],3);
        assertEquals(referee.getRound(),2);

        assertEquals(referee.getPlayer(referee.getTurnPlayer()).getDice(0).getFace(3).getReward(),"1G");// on regarde la face avant modification par achat

        referee.buy(forge.getPool(3),0,0,3);
        assertEquals(referee.getPlayer(referee.getTurnPlayer()).getGold(),0);//pas assez de gold
        assertEquals(forge.getPool(3).howManyFaces(),referee.getNumberPlayer());//pour buy la face
        referee.getPlayer(referee.getTurnPlayer()).addGold(3);//on ajoute des golds pour buy la face plus tard
        assertEquals(referee.getPlayer(referee.getTurnPlayer()).getGold(),3);
        referee.buy(forge.getPool(3),0,0,3);
        assertEquals(referee.getPlayer(referee.getTurnPlayer()).getGold(),0);
        assertEquals(forge.getPool(3).howManyFaces(),referee.getNumberPlayer()-1);//pour buy la face

        assertEquals(referee.getPlayer(referee.getTurnPlayer()).getDice(0).getFace(3).getReward(),"1PdS");//on regarde si après achat la face achetée est bien la bonne

    }
}
