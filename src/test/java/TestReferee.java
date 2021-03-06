
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import DiceForge.*;
import DiceForge.Feat.*;
import DiceForge.AI.*;

public class TestReferee {
    Referee referee;


    @Test public void refereeMethods() {
        referee.getPlayers().removeAll(referee.getPlayers());
        referee=new Referee(new Player("1"),new Player("2"),new Player("3"),new Player("4"));
        Forge forge=new Forge(referee);
        World world=new World(referee);
        referee.addForge(forge);
        referee.addWorld(world);
        assertEquals(referee.getNumberPlayer(),4);
        assertEquals(referee.getMaxRound(),9);
        assertEquals(referee.getRound(),1);
        for(int i=0;i<referee.getNumberPlayer();i++) {
            assertEquals(referee.getTurnPlayer(),i);
            assertEquals(referee.getPlayer(i).getGold(),3-i);
            referee.nextPlayer();
            assertEquals(referee.getPlayer(i).getHonour(),0);
            referee.getPlayer(i).addHonour(12*(i+1));
            assertEquals(referee.getPlayer(i).getHonour(),12*(i+1));
       }
        assertEquals(referee.winner().get(0).getHonour(),referee.getPlayer(3).getHonour());
        assertEquals(referee.winner().get(0).getName(), "4");
        assertEquals(referee.getRound(),2);

        assertEquals(referee.getPlayer(referee.getTurnPlayer()).getDice(0).getFace(3).getReward(),"1G");// on regarde la face avant modification par achat

        referee.getPlayer(referee.getTurnPlayer()).buy(forge.getPool(6),0,0,3);
        assertEquals(referee.getPlayer(referee.getTurnPlayer()).getGold(),0);//pas assez de gold
        assertEquals(forge.getPool(3).howManyFaces(),referee.getNumberPlayer());//pour buy la face
        referee.getPlayer(referee.getTurnPlayer()).addGold(3);//on ajoute des golds pour buy la face plus tard
        assertEquals(referee.getPlayer(referee.getTurnPlayer()).getGold(),3);
        referee.getPlayer(referee.getTurnPlayer()).buy(forge.getPool(6),0,0,3);
        assertEquals(referee.getPlayer(referee.getTurnPlayer()).getGold(),0);
        assertEquals(forge.getPool(6).howManyFaces(),referee.getNumberPlayer()-2);//pour buy la face

        assertEquals(referee.getPlayer(referee.getTurnPlayer()).getDice(0).getFace(3).getReward(),"1SolarShard");//on regarde si après achat la face achetée est bien la bonne

        referee.reset();

        assertEquals(referee.getTurnPlayer(),0);
        assertEquals(referee.getRound(),1);
        assertEquals(referee.getMaxRound(),9);
        assertNotSame(referee.getForge(),forge);
        assertNotSame(referee.getWorld(),world);

        referee.getPlayer(0).setCurrentIsland(0);
        referee.getPlayer(1).setCurrentIsland(0);
        assertEquals(referee.getPlayer(1).getCurrentIsland(),0);
        referee.sameIsland();
        assertEquals(referee.getPlayer(1).getCurrentIsland(),-1);
    }
}
