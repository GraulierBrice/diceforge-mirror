package DiceForge.Face;
import DiceForge.Player;

public class FacePdS extends Face{
    public FacePdS(int amount){
        super(amount);
    }
    public void giveReward(Player player){
        player.addPdS(this.reward);
    }
    public void getReward(){
        System.out.print(""+this.reward+"PdS ");

        //return Integer.toString(this.reward)+"PdS";
    }
}
