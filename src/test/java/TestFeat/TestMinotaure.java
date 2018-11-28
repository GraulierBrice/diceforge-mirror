package TestFeat;

import DiceForge.Face.FaceCombinationAND;
import DiceForge.Feat.Minotaure;
import DiceForge.Player;
import DiceForge.AI.*;
import DiceForge.Referee;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestMinotaure {
    private Minotaure minotaure;
    private Referee referee;
    private Player player=new Player(new SolarAI(),"1");
    private Player player2=new Player("2");

    int goldEnemy;

    @Before
    public void setUp() {
        referee = new Referee(player,player2);

    }


    public void addRessources(Player player){
        player.addSolarShard(1000);
        player.addLunarShard(1000);
        player.addHonour(1000);
        player.addGold(1000);
    }

    @Test
    public void effectMinotaure(){
        minotaure=new Minotaure();
        addRessources(player2);
        player2.getDice(0).setFace(new FaceCombinationAND(1,0,0,0),5);
        player2.getDice(1).setFace(new FaceCombinationAND(1,0,0,0),4);
        player2.getDice(1).setFace(new FaceCombinationAND(1,0,0,0),5);


        minotaure.setPlayer(player);
        assertTrue(player.getHonour()==8);
        goldEnemy=player2.getGold();
        assertTrue((12-goldEnemy)>0);

    }

}
