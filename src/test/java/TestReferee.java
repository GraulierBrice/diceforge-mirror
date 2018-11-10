
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import DiceForge.*;
import DiceForge.Feat.*;
import DiceForge.AI.*;

public class TestReferee {
    private Referee referee;
    private Forge forge;
    private World world;


    @Before
    public void setUp() {
        referee=new Referee(new RandomAI(),new RandomAI(),new RandomAI(),new LunarAI());
        forge=new Forge(referee);
        world=new World(referee);
        referee.addForge(forge);
        referee.addWorld(world);
    }

    @Test public void refereeMethods() {

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

        referee.getPlayer(referee.getTurnPlayer()).buy(forge.getPool(6),0,0,3);
        assertEquals(referee.getPlayer(referee.getTurnPlayer()).getGold(),0);//pas assez de gold
        assertEquals(forge.getPool(3).howManyFaces(),referee.getNumberPlayer());//pour buy la face
        referee.getPlayer(referee.getTurnPlayer()).addGold(3);//on ajoute des golds pour buy la face plus tard
        assertEquals(referee.getPlayer(referee.getTurnPlayer()).getGold(),3);
        referee.getPlayer(referee.getTurnPlayer()).buy(forge.getPool(6),0,0,3);
        assertEquals(referee.getPlayer(referee.getTurnPlayer()).getGold(),0);
        assertEquals(forge.getPool(6).howManyFaces(),referee.getNumberPlayer()-2);//pour buy la face

        assertEquals(referee.getPlayer(referee.getTurnPlayer()).getDice(0).getFace(3).getReward(),"1PdS");//on regarde si après achat la face achetée est bien la bonne

        referee.reset();

        assertEquals(referee.getTurnPlayer(),0);
        assertEquals(referee.getRound(),1);
        assertEquals(referee.getMaxRound(),9);
        assertNotSame(referee.getForge(),forge);
        assertNotSame(referee.getWorld(),world);
    }

    @Test public void interactionLunar(){
         referee.nextPlayer();
         referee.nextPlayer();
         referee.nextPlayer();
         referee.getPlayer(referee.getTurnPlayer()).addPdL(2);
         referee.choixAction(Referee.EXPLOIT);
         assertTrue(referee.getPlayer(referee.getTurnPlayer()).getFeat(0) instanceof Hammer);

         referee.choixAction(Referee.EXPLOIT);
         assertTrue(referee.getPlayer(referee.getTurnPlayer()).getFeat(1) instanceof Chest);

         referee.getPlayer(referee.getTurnPlayer()).addGold(15);
         assertEquals(referee.getPlayer(referee.getTurnPlayer()).getGold(),2);
         Hammer hammer=(Hammer)referee.getPlayer(referee.getTurnPlayer()).getFeat(0);
         assertEquals(hammer.getLevel(),0);
         assertEquals(hammer.getGold(),13);

         referee.getPlayer(referee.getTurnPlayer()).addGold(12);
         assertEquals(hammer.getLevel(),1);
         assertEquals(hammer.getGold(),10);

         referee.getPlayer(referee.getTurnPlayer()).addGold(10);

         assertEquals(hammer.getLevel(),2);
         assertEquals(hammer.getGold(),0);

         referee.getPlayer(referee.getTurnPlayer()).addGold(10);

         assertEquals(referee.getPlayer(referee.getTurnPlayer()).getGold(),16);//test du up du maxGold du chest

        assertFalse(referee.getPlayer(referee.getTurnPlayer()).doIHaveAnHammer());

        referee.choixAction(Referee.EXPLOIT);

        assertTrue(referee.getPlayer(referee.getTurnPlayer()).getFeat(2) instanceof Hammer);

        Hammer hammer2=(Hammer)referee.getPlayer(referee.getTurnPlayer()).getFeat(2);
        referee.choixAction(Referee.EXPLOIT);

        referee.getPlayer(referee.getTurnPlayer()).addGold(13);
        referee.getPlayer(referee.getTurnPlayer()).addGold(13);
        referee.getPlayer(referee.getTurnPlayer()).addGold(7);

        assertEquals(hammer2.getLevel(),2);
        assertEquals(hammer2.getGold(),0);


        assertEquals(referee.getPlayer(referee.getTurnPlayer()).getGold(),19);//test add chest
        referee.getPlayer(referee.getTurnPlayer()).removeGold(13);
        referee.choixAction(Referee.FORGE);

        assertEquals(referee.getPlayer(referee.getTurnPlayer()).getGold(),0);
        assertEquals(referee.getPlayer(referee.getTurnPlayer()).getDice(1).getFace(0).getReward(),"2PdL");

        referee.choixAction(Referee.EXPLOIT);
        referee.getPlayer(referee.getTurnPlayer()).addGold(5);

        referee.choixAction(Referee.FORGE);

        assertEquals(referee.getPlayer(referee.getTurnPlayer()).getGold(),0);
        assertEquals(referee.getPlayer(referee.getTurnPlayer()).getDice(0).getFace(0).getReward(),"3G");

        referee.getPlayer(referee.getTurnPlayer()).removePdL(1000);
        referee.getPlayer(referee.getTurnPlayer()).removeGold(1000);
        referee.getPlayer(referee.getTurnPlayer()).removePdS(1000);
        referee.getPlayer(referee.getTurnPlayer()).addPdS(1);//on reset toutes ses stats pour voir si il achète bien herbes folles

        referee.choixAction(Referee.EXPLOIT);

        assertTrue(referee.getPlayer(referee.getTurnPlayer()).getFeat(5) instanceof HerbesFolles);
        assertEquals(referee.getPlayer(referee.getTurnPlayer()).getPdS(),0);

        referee.getPlayer(referee.getTurnPlayer()).addPdL(1);
        referee.getPlayer(referee.getTurnPlayer()).addPdS(1);

        referee.choixAction(Referee.EXPLOIT);

        assertFalse(referee.getPlayer(referee.getTurnPlayer()).getFeat(6) instanceof HerbesFolles);

        assertNotSame(referee.getPlayer(referee.getTurnPlayer()).chooseAction(),Referee.PASSE);


    }
}
