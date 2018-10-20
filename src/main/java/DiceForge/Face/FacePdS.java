package DiceForge.Face;
import DiceForge.Player;

public class FacePdS extends Face{
    public FacePdS(int amount){
        super(amount);
    }
    public void giveReward(Player player){
        player.addPdS(this.reward);
    }
    public String getReward(){
        return (Integer.toString(this.reward)+"PdS");
    }
}
