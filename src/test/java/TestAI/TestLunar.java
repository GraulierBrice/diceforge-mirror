package TestAI;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import DiceForge.*;
import DiceForge.Feat.*;
import DiceForge.Face.*;
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

        public void setEnemiesFaces(Face face){
            for(int i=0;i<6;i++){
                referee.getPlayer(0).getDice(0).setFace(face,i);
                referee.getPlayer(0).getDice(1).setFace(face,i);
                referee.getPlayer(1).getDice(0).setFace(face,i);
                referee.getPlayer(1).getDice(1).setFace(face,i);
                referee.getPlayer(2).getDice(0).setFace(face,i);
                referee.getPlayer(2).getDice(1).setFace(face,i);
            }
        }

        public void setEnemyFaces(Player player,Face face){
            for(int i=0;i<6;i++){
                player.getDice(0).setFace(face,i);
                player.getDice(1).setFace(face,i);
            }
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

        public void valueLunarChooseFaceOr(Face face,int lunarShard,int solarShard,int gold,int value){
            remove();
            lunar.addLunarShard(lunarShard);lunar.addSolarShard(solarShard);lunar.addGold(gold);
            assertTrue(lunar.getStrategy().chooseFaceOr(face)==value);
        }

        public void turnExploitLunar(int lunarShard,int solarShard,int gold,nameFeat nameFeat,int featNumber){
            remove();
            lunar.addGold(gold);lunar.addSolarShard(solarShard);lunar.addLunarShard(lunarShard);
            if(nameFeat==nameFeat.Satyres) referee.getEnnemyRoll();
            lunar.getStrategy().chooseAction();
            referee.choixAction(lunar.getAction());
            assertTrue(lunar.getFeat(featNumber).getName()==nameFeat);
        }

        @Test public void choiceLunar(){

            lunar.getStrategy().chooseAction();
            referee.choixAction(lunar.getAction());
            assertEquals(lunar.getDice(1).getFace(0).getReward(),"1"+Player.LunarShard);

            turnExploitLunar(1,0,0,nameFeat.Hammer,0);

            lunar.addGold(10);
            assertEquals(lunar.getGold(),5);

            lunar.getStrategy().chooseAction();
            referee.choixAction(lunar.getAction());
            assertEquals(lunar.getDice(0).getFace(0).getReward(),"6"+Player.GOLD);
            Hammer hammer=(Hammer) lunar.getFeat(0);
            assertEquals(hammer.getGold(),5);//commence avec 3G en utilise 2 puis en gagne 10 donc total de 11 dont 6 dans le hammer

            turnExploitLunar(6,0,0,nameFeat.Pince,1);
            turnExploitLunar(5,5,0,nameFeat.Hydre,2);
            turnExploitLunar(4,0,0,nameFeat.Passeur,3);
            turnExploitLunar(5,0,0,nameFeat.CasqueInvisibilite,4);
            turnExploitLunar(2,0,0,nameFeat.SabotArgent,5);
            turnExploitLunar(3,0,0,nameFeat.Satyres,6);
            turnExploitLunar(1,0,0,nameFeat.Chest,7);

            world.getIsland(0).setFeats(new Hammer());
            world.getIsland(0).setFeats((new Chest()));
            turnExploitLunar(1,0,0,nameFeat.Chest,8);

            remove();
            lunar.addGold(31);
            assertEquals(hammer.getLevel(),2);

            world.getIsland(0).setFeats(new Chest());
            turnExploitLunar(1,0,0,nameFeat.Hammer,9);

            lunar.addGold(6);
            lunar.getStrategy().chooseAction();
            referee.choixAction(lunar.getAction());
            assertEquals(lunar.getDice(0).getFace(1).getReward(),"4"+Player.GOLD);


            lunar.addGold(35);

            remove();
            lunar.addGold(12);
            lunar.getStrategy().chooseAction();
            referee.choixAction(lunar.getAction());
            assertEquals(lunar.getDice(1).getFace(1).getReward(),"2"+Player.LunarShard+"2"+Player.HONOUR);

            lunar.addSolarShard(2);
            lunar.getStrategy().replay();//ne replay pas car ne pourrait rien faire d'interessant après avec la strat actuelle
            assertEquals(lunar.getHasReplayed(),false);

            this.setEnemiesFaces(new FaceCombinationAND(1,0,0,0));
            referee.getWorld().getIsland(2).setFeats(new Satyres());
            turnExploitLunar(3,0,0,nameFeat.Satyres,10);//test chooseBestEnnemyFace() à travers satyres
            assertTrue(lunar.getGold()==2);

            remove();
            lunar.addGold(10000);
            referee.getWorld().getIsland(0).setFeats(new Hammer());
            assertTrue(lunar.getStrategy().chooseDice()==1);
            lunar.addLunarShard(1);
            lunar.getStrategy().chooseAction();
            referee.choixAction(lunar.getAction());
            assertTrue(lunar.getStrategy().chooseDice()==0);

            remove();
            assertEquals(lunar.getStrategy().chooseBestDice(),lunar.getDice(1));
            lunar.addLunarShard(lunar.getMaxLunarShard());
            assertEquals(lunar.getStrategy().chooseBestDice(),lunar.getDice(0));
        }

        @Test
        public void replay(){
            lunar.getStrategy().replay();
            assertTrue(!lunar.getHasReplayed());
            lunar.addSolarShard(2);
            lunar.getStrategy().replay();
            assertTrue(!lunar.getHasReplayed());
            lunar.addLunarShard(2);
            lunar.getStrategy().replay();
            assertTrue(lunar.getHasReplayed());
        }

        @Test
        public void chooseFaceOr(){//1G/1PdL/1PdS    3G/2H      2G/2PdL/2PdS
            Face face1=new FaceCombinationOR(1,1,1,0);
            valueLunarChooseFaceOr(face1,6,4,0,2);
            valueLunarChooseFaceOr(face1,4,6,0,1);
            valueLunarChooseFaceOr(face1,5,3,0,1);
            valueLunarChooseFaceOr(face1,3,5,0,2);
            valueLunarChooseFaceOr(face1,5,4,0,2);
            valueLunarChooseFaceOr(face1,4,5,0,1);
            valueLunarChooseFaceOr(face1,2,0,0,1);
            valueLunarChooseFaceOr(face1,6,6,0,0);

            Face face2=new FaceCombinationOR(3,0,0,2);
            valueLunarChooseFaceOr(face2,0,0,lunar.getMaxGold(),1);
            valueLunarChooseFaceOr(face2,0,0,2,0);

            Face face3=new FaceCombinationOR(2,2,2,0);
            valueLunarChooseFaceOr(face3,6,3,0,2);
            valueLunarChooseFaceOr(face3,6,4,0,2);
            valueLunarChooseFaceOr(face3,3,6,0,1);
            valueLunarChooseFaceOr(face3,4,6,0,1);
            valueLunarChooseFaceOr(face3,5,2,0,1);
            valueLunarChooseFaceOr(face3,2,5,0,2);
            valueLunarChooseFaceOr(face3,5,3,0,2);
            valueLunarChooseFaceOr(face3,5,4,0,2);
            valueLunarChooseFaceOr(face3,3,5,0,1);
            valueLunarChooseFaceOr(face3,4,5,0,1);
            valueLunarChooseFaceOr(face3,0,0,0,1);
            valueLunarChooseFaceOr(face3,6,6,0,0);

            valueLunarChooseFaceOr(new FaceCombinationAND(0,0,0,0),0,0,0,0);
        }

        @Test
        public void chooseWorstEnnemyFace(){
            Face gold_1=new FaceCombinationAND(1,0,0,0);
            setEnemiesFaces(gold_1);
            referee.faveur();
            //referee.getPlayer(1).getDice(0).setFace(new FaceCombinationAND(0,4,0,0),5);
            //referee.getPlayer(1).getDice(1).setFace(new FaceCombinationAND(0,4,0,0),5);

            remove();
            assertEquals(lunar.getStrategy().chooseWorstEnnemyFace()[0].getReward(),"1"+Player.GOLD);
            assertEquals(lunar.getStrategy().chooseWorstEnnemyFace()[1].getReward(),"1"+Player.GOLD);

            setEnemyFaces(referee.getPlayer(1), new FaceCombinationAND(0,4,0,0));

            referee.faveur();
            remove();
            lunar.addLunarShard(6);
            assertEquals(lunar.getStrategy().chooseWorstEnnemyFace()[0].getReward(),"1"+Player.GOLD);
            assertEquals(lunar.getStrategy().chooseWorstEnnemyFace()[1].getReward(),"1"+Player.GOLD);

            referee.faveur();
            remove();
            lunar.addGold(12);
            assertEquals(lunar.getStrategy().chooseWorstEnnemyFace()[0].getReward(),"4"+Player.LunarShard);
            assertEquals(lunar.getStrategy().chooseWorstEnnemyFace()[1].getReward(),"4"+Player.LunarShard);
        }

        @Test
        public void choosePool(){
            Forge forge2=new Forge(referee);
            referee.addForge(forge2);
            remove();
            assertEquals(lunar.getStrategy().choosePool(),-1);
            lunar.addGold(2);
            assertEquals(lunar.getStrategy().choosePool(),8);
            lunar.addGold(4);
            assertEquals(lunar.getStrategy().choosePool(),3);
            lunar.addGold(12);
            assertEquals(lunar.getStrategy().choosePool(),0);

            Hammer hammer=new Hammer();
            hammer.setPlayer(lunar);
            assertEquals(lunar.getStrategy().choosePool(),5);//face 6g
        }

}
