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
        System.out.println("Le joueur "+owner.getName()+" choisit les faces suivantes :");
        for(int i=0;i<owner.getEnnemyFaces().size();i++){
            if(owner.getEnnemyFaces().get(i)==((Face)o[0]) || owner.getEnnemyFaces().get(i)==((Face)o[1])){
                System.out.print(Announcer.ANSI_YELLOW+owner.getEnnemyFaces().get(i).getReward()+Announcer.ANSI_RESET+" ");
            }else System.out.print(owner.getEnnemyFaces().get(i).getReward()+" ");
        }
        System.out.println("\n");
        owner.setEnnemyFaces(new ArrayList<>());

    }
}
