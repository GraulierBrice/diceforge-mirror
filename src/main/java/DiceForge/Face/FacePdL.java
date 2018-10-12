package DiceForge.Face;
import DiceForge.Player;

public class FacePdL extends Face{

    public FacePdL(int amount){
        super(amount);
    }

    public void giveReward(Player player) {
        player.addPdL(this.reward);
    }
}
