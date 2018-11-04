package DiceForge.Face;
import DiceForge.*;

public class FaceCombinationAND extends Face{

    public FaceCombinationAND(int rewardGold, int rewardPdL, int rewardPdS, int rewardHonour) {
        super(rewardGold,rewardPdL,rewardPdS,rewardHonour);
    }

    public void giveReward(Player player) {
        player.addGold(rewardGold);
        player.addPdL(rewardPdL);
        player.addPdS(rewardPdS);
        player.addHonour(rewardHonour);
    }


}
