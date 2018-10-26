package DiceForge.Face;
import DiceForge.Player;

public class FaceHonour extends Face{

    public FaceHonour(int amount,String kind){
        super(amount,kind);
    }

    public void giveReward(Player player){
        player.addHonour(this.reward);
    }


}
