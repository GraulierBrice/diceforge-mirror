
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import DiceForge.*;
import DiceForge.Feat.*;
import DiceForge.AI.*;




public class TestHammerAI {
    private Referee referee;
    private Player hammerAI;


    @Before
    public void setUp() {
        hammerAI=new Player(new HammerAI(),"hammer");
        referee=new Referee(hammerAI,new Player("2"),new Player("3"));
        Forge forge = new Forge(referee);
        World world = new World(referee);
        referee.addForge(forge);
        referee.addWorld(world);
        removeFeat();//on le fait deux fois car on a besoin de 3j pour avoir tjrs autant de Face dans les pools;
        //removeFeat();//mais aussi avoir qu'un seul feat de chaque types pour alléger les tests (sinon redondance de choix)
    }

    public void remove(){
        hammerAI.removeGold(10000);
        hammerAI.removeLunarShard(10000);
        hammerAI.removeSolarShard(10000);
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

    @Test public void choiceHammer(){
        remove();
        hammerAI.addSolarShard(1);
        hammerAI.getStrategy().chooseAction();
        referee.choixAction(hammerAI.getAction());
        assertEquals(hammerAI.getFeat(0).getName(), nameFeat.HerbesFolles);


        hammerAI.getStrategy().chooseAction();
        referee.choixAction(hammerAI.getAction());
        assertEquals(hammerAI.getFeat(1).getName(), nameFeat.Hammer);


        hammerAI.addSolarShard(1);
        hammerAI.getStrategy().chooseAction();
        referee.choixAction(hammerAI.getAction());
        assertEquals(hammerAI.getFeat(2).getName(), nameFeat.Ancien);


        hammerAI.addSolarShard(2);
        hammerAI.getStrategy().chooseAction();
        referee.choixAction(hammerAI.getAction());
        assertEquals(hammerAI.getFeat(3).getName(), nameFeat.Ancien);


        remove();
        hammerAI.addGold(5);
        hammerAI.getStrategy().chooseAction();
        assertEquals(hammerAI.getAction(),"forge");
        referee.choixAction(hammerAI.getAction());


        remove();
        hammerAI.addGold(50); hammerAI.addSolarShard(5); hammerAI.addLunarShard(5);
        hammerAI.getStrategy().chooseAction();
        assertEquals(hammerAI.getAction(),"forge");
        referee.choixAction(hammerAI.getAction());
        assertEquals(hammerAI.getDice(0).getFace(0).getReward(),"2"+Player.GOLD+"\u001B[0m/2"+Player.LunarShard+"\u001B[0m/2"+Player.SolarShard+"\u001B[0m");


        remove();
        hammerAI.addLunarShard(1);
        hammerAI.getStrategy().chooseAction();
        referee.choixAction(hammerAI.getAction());
        assertEquals(hammerAI.getFeat(4).getName(), nameFeat.Hammer);


        remove();
        hammerAI.addSolarShard(5); hammerAI.addLunarShard(5);
        hammerAI.getStrategy().chooseAction();
        referee.choixAction(hammerAI.getAction());
        assertEquals(hammerAI.getAction(),"exploit");
        assertEquals(hammerAI.getFeat(5).getName(), nameFeat.Hydre);


    }
}
