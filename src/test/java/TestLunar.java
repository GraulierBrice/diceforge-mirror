
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
            referee=new Referee(lunar);
            forge=new Forge(referee);
            world=new World(referee);
            referee.addForge(forge);
            referee.addWorld(world);
        }

        public void remove(){
            lunar.removeGold(10000);
            lunar.removeLunarShard(10000);
            lunar.removeSolarShard(10000);
        }

        @Test public void choiceLunar(){
            referee.choixAction(lunar.getStrategy().chooseAction());
            assertEquals(lunar.getDice(1).getFace(0).getReward(),"1"+Player.LunarShard);

            lunar.addLunarShard(1);
            referee.choixAction(lunar.getStrategy().chooseAction());
            assertTrue(lunar.getFeat(0) instanceof Hammer);

            lunar.addGold(10);
            assertEquals(lunar.getGold(),5);
            referee.choixAction(lunar.getStrategy().chooseAction());
            assertEquals(lunar.getDice(0).getFace(0).getReward(),"6"+Player.GOLD);
            Hammer hammer=(Hammer) lunar.getFeat(0);
            assertEquals(hammer.getGold(),6);//commence avec 3G en utilise 2 puis en gagne 10 donc total de 11 dont 6 dans le hammer

            lunar.addLunarShard(6);
            referee.choixAction(lunar.getStrategy().chooseAction());
            assertTrue(lunar.getFeat(1) instanceof Pince);

            remove();
            lunar.addLunarShard(5); lunar.addSolarShard(5);
            referee.choixAction(lunar.getStrategy().chooseAction());
            assertTrue(lunar.getFeat(2) instanceof Hydre);

            remove();
            lunar.addLunarShard(4);
            referee.choixAction(lunar.getStrategy().chooseAction());
            assertTrue(lunar.getFeat(3) instanceof Passeur);

            remove();
            lunar.addLunarShard(5);
            referee.choixAction(lunar.getStrategy().chooseAction());
            assertTrue(lunar.getFeat(4) instanceof CasqueInvisibilite);


            remove();
            lunar.addLunarShard(2);
            referee.choixAction(lunar.getStrategy().chooseAction());
            assertTrue(lunar.getFeat(5) instanceof SabotArgent);

            remove();
            lunar.addLunarShard(3);
            referee.choixAction(lunar.getStrategy().chooseAction());
            assertTrue(lunar.getFeat(6) instanceof Satyres);

            remove();
            lunar.addLunarShard(1);
            referee.choixAction(lunar.getStrategy().chooseAction());
            assertTrue(lunar.getFeat(7) instanceof Chest);

            world.getIsland(0).setFeats(new Hammer());
            world.getIsland(0).setFeats((new Chest()));
            remove();
            lunar.addLunarShard(1);
            referee.choixAction(lunar.getStrategy().chooseAction());
            assertTrue(lunar.getFeat(8) instanceof Chest);

            world.getIsland(0).setFeats(new Chest());
            lunar.addGold(31);
            assertEquals(hammer.getLevel(),2);
            lunar.addLunarShard(1);
            referee.choixAction((lunar.getStrategy().chooseAction()));
            assertTrue(lunar.getFeat(9) instanceof Hammer);

            lunar.addGold(5);
            referee.choixAction(lunar.getStrategy().chooseAction());
            assertEquals(lunar.getDice(0).getFace(1).getReward(),"4"+Player.GOLD);


            lunar.addGold(35);

            remove();
            lunar.addGold(12);
            referee.choixAction(lunar.getStrategy().chooseAction());
            assertEquals(lunar.getDice(1).getFace(1).getReward(),"2"+Player.LunarShard+"2"+Player.HONOUR);
            lunar.addGold(2);
        }
}
