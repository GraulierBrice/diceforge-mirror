package DiceForge.Face;
import DiceForge.Player;

public class FaceHonour extends Face{
    public FaceHonour(int amount){
        super(amount);
    }
    public void giveReward(Player player){
        player.addHonour(this.reward);
    }
    public void getReward(){
        System.out.print(""+this.reward+"H ");

      //  return Integer.toString(this.reward)+"H";
    }
}
