package TestAI;

import DiceForge.Face.Face;
import DiceForge.Face.FaceCombinationAND;
import DiceForge.Face.FaceCombinationOR;
import org.junit.After;
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
        referee=new Referee(solar,new Player(new SolarAI(),"2"),new Player(new SolarAI(),"3"));
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

    public void valueSolarChooseFaceOr(Face face,int lunarShard,int solarShard,int gold,int value){
        remove();
        solar.addLunarShard(lunarShard);solar.addSolarShard(solarShard);solar.addGold(gold);
        assertTrue(solar.getStrategy().chooseFaceOr(face)==value);
    }

    public void turnExploitSolar(int lunarShard,int solarShard,int gold,nameFeat nameFeat,int featNumber){
        remove();
        solar.addGold(gold);solar.addSolarShard(solarShard);solar.addLunarShard(lunarShard);
        if(nameFeat==nameFeat.Satyres) referee.getEnnemyRoll();
        solar.getStrategy().chooseAction();
        referee.choixAction(solar.getAction());
        assertTrue(solar.getFeat(featNumber).getName()==nameFeat);
    }

    @Test public void choiceSolar(){
        solar.getStrategy().chooseAction();
        referee.choixAction(solar.getAction());
        assertEquals(solar.getDice(0).getFace(0).getReward(),"1"+Player.SolarShard);

        turnExploitSolar(6,6,0,nameFeat.Hydre,0);
        turnExploitSolar(0,6,0,nameFeat.Enigme,1);
        turnExploitSolar(0,4,0,nameFeat.Meduse,2);
        turnExploitSolar(0,2,0,nameFeat.AilesGardienne,3);
        turnExploitSolar(0,3,0,nameFeat.Minotaure,4);
        turnExploitSolar(0,1,0,nameFeat.Ancien,5);
        turnExploitSolar(0,1,0,nameFeat.HerbesFolles,6);
        turnExploitSolar(2,0,0,nameFeat.SabotArgent,7);
        turnExploitSolar(3,0,0,nameFeat.Satyres,8);
        turnExploitSolar(1,0,0,nameFeat.Hammer,9);
        turnExploitSolar(1,0,0,nameFeat.Chest,10);

        this.setEnemiesFaces(new FaceCombinationAND(1,0,0,0));
        referee.getWorld().getIsland(2).setFeats(new Satyres());
        turnExploitSolar(3,0,0,nameFeat.Satyres,11);//test chooseBestEnnemyFace() à travers satyres
        assertTrue(solar.getGold()==2);

        remove();
        solar.addGold(10000);
        referee.getWorld().getIsland(0).setFeats(new Hammer());
        assertTrue(solar.getStrategy().chooseDice()==0);
        solar.addLunarShard(1);
        solar.getStrategy().chooseAction();
        referee.choixAction(solar.getAction());//ajout hammer
        assertTrue(solar.getStrategy().chooseDice()==1);

        remove();
        assertEquals(solar.getStrategy().chooseBestDice(),solar.getDice(1));
        solar.addSolarShard(solar.getMaxSolarShard());
        assertEquals(solar.getStrategy().chooseBestDice(),solar.getDice(0));

        replay();
        chooseFaceOr();
        chooseWorstEnnemyFace();
        choosePool();
    }

    public void replay(){
        remove();
        solar.getStrategy().replay();
        assertTrue(!solar.getHasReplayed());
        solar.addSolarShard(2);
        solar.getStrategy().replay();
        assertTrue(!solar.getHasReplayed());
        solar.addSolarShard(2);
        solar.getStrategy().replay();
        assertTrue(solar.getHasReplayed());
    }

    public void chooseFaceOr(){//1G/1PdL/1PdS    3G/2H      2G/2PdL/2PdS
        remove();
        Face face1=new FaceCombinationOR(1,1,1,0);
        valueSolarChooseFaceOr(face1,6,4,0,2);
        valueSolarChooseFaceOr(face1,4,6,0,1);
        valueSolarChooseFaceOr(face1,5,3,0,1);
        valueSolarChooseFaceOr(face1,3,5,0,2);
        valueSolarChooseFaceOr(face1,5,4,0,2);
        valueSolarChooseFaceOr(face1,4,5,0,1);
        valueSolarChooseFaceOr(face1,0,2,0,2);
        valueSolarChooseFaceOr(face1,6,6,0,0);

        Face face2=new FaceCombinationOR(3,0,0,2);
        valueSolarChooseFaceOr(face2,0,0,solar.getMaxGold(),1);
        valueSolarChooseFaceOr(face2,0,0,2,0);

        Face face3=new FaceCombinationOR(2,2,2,0);
        valueSolarChooseFaceOr(face3,6,3,0,2);
        valueSolarChooseFaceOr(face3,6,4,0,2);
        valueSolarChooseFaceOr(face3,3,6,0,1);
        valueSolarChooseFaceOr(face3,4,6,0,1);
        valueSolarChooseFaceOr(face3,5,2,0,1);
        valueSolarChooseFaceOr(face3,2,5,0,2);
        valueSolarChooseFaceOr(face3,5,3,0,2);
        valueSolarChooseFaceOr(face3,5,4,0,2);
        valueSolarChooseFaceOr(face3,3,5,0,1);
        valueSolarChooseFaceOr(face3,4,5,0,1);
        valueSolarChooseFaceOr(face3,0,2,0,2);
        valueSolarChooseFaceOr(face3,6,6,0,0);

        valueSolarChooseFaceOr(new FaceCombinationAND(0,0,0,0),0,0,0,0);
    }


    public void chooseWorstEnnemyFace(){
       // referee=new Referee(solar,new Player(),new Player());
        remove();
        Face gold_1=new FaceCombinationAND(1,0,0,0);
        setEnemiesFaces(gold_1);
        referee.faveur();
        //referee.getPlayer(1).getDice(0).setFace(new FaceCombinationAND(0,4,0,0),5);
        //referee.getPlayer(1).getDice(1).setFace(new FaceCombinationAND(0,4,0,0),5);

        remove();
        assertEquals(solar.getStrategy().chooseWorstEnnemyFace()[0].getReward(),"1"+Player.GOLD);
        assertEquals(solar.getStrategy().chooseWorstEnnemyFace()[1].getReward(),"1"+Player.GOLD);

        setEnemyFaces(referee.getPlayer(1), new FaceCombinationAND(0,0,4,0));

        referee.faveur();
        remove();
        solar.addSolarShard(6);
        assertEquals(solar.getStrategy().chooseWorstEnnemyFace()[0].getReward(),"1"+Player.GOLD);
        assertEquals(solar.getStrategy().chooseWorstEnnemyFace()[1].getReward(),"1"+Player.GOLD);

        referee.faveur();
        remove();
        solar.addGold(12);
        assertEquals(solar.getStrategy().chooseWorstEnnemyFace()[0].getReward(),"4"+Player.SolarShard);
        assertEquals(solar.getStrategy().chooseWorstEnnemyFace()[1].getReward(),"4"+Player.SolarShard);
    }

    public void choosePool(){
        referee=new Referee(solar);
        Forge forge2=new Forge(referee);
        referee.addForge(forge2);
        remove();
        assertEquals(solar.getStrategy().choosePool(),-1);
        solar.addGold(2);
        assertEquals(solar.getStrategy().choosePool(),9);//recup des golds parce qu'il ne peut rien acheter de mieux
        solar.addGold(1);
        assertEquals(solar.getStrategy().choosePool(),6);
        solar.addGold(5);
        assertEquals(solar.getStrategy().choosePool(),2);
        solar.addGold(4);
        assertEquals(solar.getStrategy().choosePool(),0);

        Hammer hammer=new Hammer();
        hammer.setPlayer(solar);
        assertEquals(solar.getStrategy().choosePool(),5);//face 6g
    }
}
