package DiceForge.Face;
import DiceForge.*;

public class FaceCombinationAND extends Face{

    public FaceCombinationAND(int rewardGold, int rewardLunarShard, int rewardSolarShard, int rewardHonour) {
        super(rewardGold,rewardLunarShard,rewardSolarShard,rewardHonour);
    }
    @Override
    public void giveReward(Player player) {
        player.addGold(rewardGold);
        player.addLunarShard(rewardLunarShard);
        player.addSolarShard(rewardSolarShard);
        player.addHonour(rewardHonour);
    }

    public void removeReward(Player player){
        player.removeGold(rewardGold);
        player.removeLunarShard(rewardLunarShard);
        player.removeSolarShard(rewardSolarShard);
        player.removeHonour(rewardHonour);
    }

    @Override
    public String getReward() {
        String reward="";
        if (this.rewardGold > 0) reward += (this.rewardGold + Player.GOLD);
        if (this.rewardLunarShard > 0) reward += (this.rewardLunarShard + Player.LunarShard);
        if (this.rewardSolarShard > 0) reward += (this.rewardSolarShard + Player.SolarShard);
        if (this.rewardHonour > 0) reward += (this.rewardHonour +Player.HONOUR);
        return reward;

    }
}
