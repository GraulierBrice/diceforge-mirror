package DiceForge.Face;

import DiceForge.Player;

public class FaceCombinationOR extends Face {

    public FaceCombinationOR(int rewardGold, int rewardLunarShard, int rewardSolarShard, int rewardHonour) {
        super(rewardGold, rewardLunarShard, rewardSolarShard, rewardHonour);

    }
    public void giveReward(Player player){}

    public void giveRewardOR(Player player,int choose) {
        switch(choose){
            case 0:
                if (kind.contains(Player.GOLD)) {
                    player.addGold(rewardGold);
                }else if(kind.contains(Player.LunarShard)){
                    player.addLunarShard(rewardLunarShard);
                }else if(kind.contains(Player.SolarShard)){
                    player.addSolarShard(rewardSolarShard);
                }else{
                    player.addHonour(rewardHonour);
                }
                break;
            case 1:
                if(kind.contains(Player.LunarShard)){
                    player.addLunarShard(rewardLunarShard);
                }else if(kind.contains(Player.SolarShard)){
                    player.addSolarShard(rewardSolarShard);
                }else{
                    player.addHonour(rewardHonour);
                }
                break;
            case 2:
                if(kind.contains(Player.SolarShard)){
                    player.addSolarShard(rewardSolarShard);
                }else{
                    player.addHonour(rewardHonour);
                }
                break;
            case 3:
                player.addHonour(rewardHonour);
                break;
        }
    }
}
