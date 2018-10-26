package DiceForge.Face;
import DiceForge.Player;

public class FacePdS extends Face{

    public FacePdS(int amount,String kind){
        super(amount,kind);
    }

    public void giveReward(Player player){
        player.addPdS(this.reward);
    }
    

}
