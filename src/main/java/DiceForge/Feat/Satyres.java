package DiceForge.Feat;
import DiceForge.Announcer;
import DiceForge.Face.*;

import DiceForge.Player;

import java.util.ArrayList;

public class Satyres extends Feat{

    public Satyres(){
        super(6,3,0,false);
    }

    @Override
    public void setPlayer(Player player) {
        super.setPlayer(player);
        Face[] face=player.getStrategy().chooseBestEnnemyFace();
        this.effect(face[0],face[1]);
    }

    @Override
    public void effect(Object... o) {
        ((Face)o[0]).giveReward(owner);
        ((Face)o[1]).giveReward(owner);
        Announcer.printSatyres(owner,(Face)o[0],(Face)o[1]);
        owner.setEnnemyFaces(new ArrayList<>());

    }
}
