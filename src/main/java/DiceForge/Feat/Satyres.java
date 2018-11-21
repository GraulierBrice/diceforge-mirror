package DiceForge.Feat;
import DiceForge.Face.*;

import DiceForge.Player;

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
        System.out.println("Le joueur "+owner.getName()+" choisit la face "+((Face)o[0]).getReward());
        System.out.println("Le joueur "+owner.getName()+" choisit la face "+((Face)o[1]).getReward());

    }
}
