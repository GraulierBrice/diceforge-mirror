package TestAI;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import DiceForge.*;
import DiceForge.Feat.*;
import DiceForge.AI.*;




public class TestSolarAI {
    private Referee referee;
    private Forge forge;
    private World world;
    private Player solar;


    @Before
    public void setUp() {
        solar=new Player(new SolarAI(),"solar");
        referee=new Referee(solar,new Player("2"),new Player("3"));
        forge=new Forge(referee);
        world=new World(referee);
        referee.addForge(forge);
        referee.addWorld(world);
        removeFeat();//on le fait deux fois car on a besoin de 3j pour avoir tjrs autant de Face dans les pools;
        removeFeat();//mais aussi avoir qu'un seul feat de chaque types pour alléger les tests (sinon redondance de choix)
    }

    public void remove(){
        solar.removeGold(10000);
        solar.removeLunarShard(10000);
        solar.removeSolarShard(10000);
    }

    public void removeFeat(){
        referee.getWorld().getIsland(0).removeFeat(nameFeat.Hammer);               //world équivalent à 1 joueur
        referee.getWorld().getIsland(0).removeFeat(nameFeat.Chest);                //pour ne pas modif test
        referee.getWorld().getIsland(1).removeFeat(nameFeat.Ancien);               //mais besoin de deux joueurs
        referee.getWorld().getIsland(1).removeFeat(nameFeat.HerbesFolles);         //pour l'effect de satyres
        referee.getWorld().getIsland(2).removeFeat(nameFeat.SabotArgent);          //car il regarde les dés des ennemis
        referee.getWorld().getIsland(2).removeFeat(nameFeat.Satyres);              //et s'il n'a pas d'ennemis ça ne
        referee.getWorld().getIsland(3).removeFeat(nameFeat.AilesGardienne);       //marchera pas
        referee.getWorld().getIsland(3).removeFeat(nameFeat.Minotaure);
        referee.getWorld().getIsland(4).removeFeat(nameFeat.Passeur);
        referee.getWorld().getIsland(4).removeFeat(nameFeat.CasqueInvisibilite);
        referee.getWorld().getIsland(5).removeFeat(nameFeat.Meduse);
        referee.getWorld().getIsland(5).removeFeat(nameFeat.MiroirAbyssal);
        referee.getWorld().getIsland(6).removeFeat(nameFeat.Pince);
        referee.getWorld().getIsland(6).removeFeat(nameFeat.Hydre);
        referee.getWorld().getIsland(6).removeFeat(nameFeat.Enigme);
    }

    @Test public void choiceSolar(){
        solar.getStrategy().chooseAction();
        referee.choixAction(solar.getAction());
        assertEquals(solar.getDice(0).getFace(0).getReward(),"1"+Player.SolarShard);
        remove();

        solar.addLunarShard(6); solar.addSolarShard(6);
        solar.getStrategy().chooseAction();
        referee.choixAction(solar.getAction());
        assertEquals(solar.getFeat(0).getName(), nameFeat.Hydre);
        remove();

        solar.addSolarShard(6);
        solar.getStrategy().chooseAction();
        referee.choixAction(solar.getAction());
        assertEquals(solar.getFeat(1).getName(), nameFeat.Enigme);
        remove();

        solar.addSolarShard(4);
        solar.getStrategy().chooseAction();
        referee.choixAction(solar.getAction());
        assertEquals(solar.getFeat(2).getName(), nameFeat.Meduse);
        remove();

        solar.addSolarShard(2);
        solar.getStrategy().chooseAction();
        referee.choixAction(solar.getAction());
        assertEquals(solar.getFeat(3).getName(),nameFeat.AilesGardienne);
        remove();

        solar.addSolarShard(3);
        solar.getStrategy().chooseAction();
        referee.choixAction(solar.getAction());
        assertEquals(solar.getFeat(4).getName(), nameFeat.Minotaure);
        remove();

        solar.addSolarShard(1);
        solar.getStrategy().chooseAction();
        referee.choixAction(solar.getAction());
        assertEquals(solar.getFeat(5).getName(), nameFeat.Ancien);
        remove();

        solar.addSolarShard(1);
        solar.getStrategy().chooseAction();
        referee.choixAction(solar.getAction());
        assertEquals(solar.getFeat(6).getName(), nameFeat.HerbesFolles);
        remove();

        solar.addLunarShard(2);
        solar.getStrategy().chooseAction();
        referee.choixAction(solar.getAction());
        assertEquals(solar.getFeat(7).getName(), nameFeat.SabotArgent);
        remove();

        solar.addLunarShard(3);
        solar.getStrategy().chooseAction();
        referee.choixAction(solar.getAction());
        assertEquals(solar.getFeat(8).getName(), nameFeat.Satyres);
        remove();

        solar.addLunarShard(1);
        solar.getStrategy().chooseAction();
        referee.choixAction(solar.getAction());
        assertEquals(solar.getFeat(9).getName(), nameFeat.Hammer);

        solar.addLunarShard(1);
        solar.getStrategy().chooseAction();
        referee.choixAction(solar.getAction());
        assertEquals(solar.getFeat(10).getName(), nameFeat.Chest);


    }
}
