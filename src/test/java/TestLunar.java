
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import DiceForge.*;
import DiceForge.Feat.*;
import DiceForge.AI.*;




    public class TestLunar {
        private Referee referee;
        private Forge forge;
        private World world;
        private Player lunar;


        @Before
        public void setUp() {
            lunar=new Player(new LunarAI(),"lunar");
            referee=new Referee(lunar,new Player("2"),new Player("3"));
            forge=new Forge(referee);
            world=new World(referee);
            referee.addForge(forge);
            referee.addWorld(world);
            removeFeat();//on le fait deux fois car on a besoin de 3j pour avoir tjrs autant de Face dans les pools;
            removeFeat();//mais aussi avoir qu'un seul feat de chaque types pour alléger les tests (sinon redondance de choix)
        }

        public void remove(){
            lunar.removeGold(10000);
            lunar.removeLunarShard(10000);
            lunar.removeSolarShard(10000);
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

        @Test public void choiceLunar(){

            lunar.getStrategy().chooseAction();
            referee.choixAction(lunar.getAction());
            assertEquals(lunar.getDice(1).getFace(0).getReward(),"1"+Player.LunarShard);

            lunar.addLunarShard(1);
            lunar.getStrategy().chooseAction();
            referee.choixAction(lunar.getAction());
            assertTrue(lunar.getFeat(0).getName() == nameFeat.Hammer);

            lunar.addGold(10);
            assertEquals(lunar.getGold(),5);

            lunar.getStrategy().chooseAction();
            referee.choixAction(lunar.getAction());
            assertEquals(lunar.getDice(0).getFace(0).getReward(),"6"+Player.GOLD);
            Hammer hammer=(Hammer) lunar.getFeat(0);
            assertEquals(hammer.getGold(),6);//commence avec 3G en utilise 2 puis en gagne 10 donc total de 11 dont 6 dans le hammer

            lunar.addLunarShard(6);
            lunar.getStrategy().chooseAction();
            referee.choixAction(lunar.getAction());
            assertTrue(lunar.getFeat(1).getName()==nameFeat.Pince);

            remove();
            lunar.addLunarShard(5); lunar.addSolarShard(5);
            lunar.getStrategy().chooseAction();
            referee.choixAction(lunar.getAction());
            assertTrue(lunar.getFeat(2).getName()==nameFeat.Hydre);

            remove();
            lunar.addLunarShard(4);
            lunar.getStrategy().chooseAction();
            referee.choixAction(lunar.getAction());
            assertTrue(lunar.getFeat(3).getName()==nameFeat.Passeur);

            remove();
            lunar.addLunarShard(5);
            lunar.getStrategy().chooseAction();

            referee.choixAction(lunar.getAction());
            assertTrue(lunar.getFeat(4).getName()==nameFeat.CasqueInvisibilite);


            remove();
            lunar.addLunarShard(2);
            lunar.getStrategy().chooseAction();
            referee.choixAction(lunar.getAction());
            assertTrue(lunar.getFeat(5).getName()==nameFeat.SabotArgent);

            remove();
            lunar.addLunarShard(3);
            referee.getEnnemyRoll();
            lunar.getStrategy().chooseAction();
            referee.choixAction(lunar.getAction());
            assertTrue(lunar.getFeat(6).getName()==nameFeat.Satyres);

            /* ajouter test de choix de faces quand Satyres


                                                            */

            remove();
            lunar.addLunarShard(1);
            lunar.getStrategy().chooseAction();
            referee.choixAction(lunar.getAction());
            assertTrue(lunar.getFeat(7).getName()==nameFeat.Chest);

            world.getIsland(0).setFeats(new Hammer());
            world.getIsland(0).setFeats((new Chest()));
            remove();
            lunar.addLunarShard(1);
            lunar.getStrategy().chooseAction();
            referee.choixAction(lunar.getAction());
            assertTrue(lunar.getFeat(8).getName()==nameFeat.Chest);

            world.getIsland(0).setFeats(new Chest());
            lunar.addGold(31);
            assertEquals(hammer.getLevel(),2);
            lunar.addLunarShard(1);
            lunar.getStrategy().chooseAction();
            referee.choixAction((lunar.getAction()));
            assertTrue(lunar.getFeat(9).getName()==nameFeat.Hammer);

            lunar.addGold(5);
            lunar.getStrategy().chooseAction();
            referee.choixAction(lunar.getAction());
            assertEquals(lunar.getDice(0).getFace(1).getReward(),"4"+Player.GOLD);


            lunar.addGold(35);

            remove();
            lunar.addGold(12);
            lunar.getStrategy().chooseAction();
            referee.choixAction(lunar.getAction());
            assertEquals(lunar.getDice(1).getFace(1).getReward(),"2"+Player.LunarShard+"2"+Player.HONOUR);

            remove();

            lunar.addLunarShard(2);
            lunar.addSolarShard(2);
            lunar.getStrategy().replay();
            assertEquals(lunar.getHasReplayed(),true);

            lunar.setHasReplayed(false);

            remove();//0 ressources donc ne peut pas rejouer
            lunar.getStrategy().replay();
            assertEquals(lunar.getHasReplayed(),false);

            lunar.addSolarShard(2);
            lunar.getStrategy().replay();//ne replay pas car ne pourrait rien faire d'interessant après avec la strat actuelle
            assertEquals(lunar.getHasReplayed(),false);
        }
}
