package DiceForge.Face;

import DiceForge.Player;

public class FaceCombinationOR extends Face {

    public FaceCombinationOR(int rewardGold, int rewardLunarShard, int rewardSolarShard, int rewardHonour) {
        super(rewardGold, rewardLunarShard, rewardSolarShard, rewardHonour);

    }
    @Override
    public void giveReward(Player player){
        switch(player.getStrategy().chooseFaceOr(this)){
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

    @Override
    public void removeReward(Player player) {
        switch(player.getStrategy().chooseFaceOr(this)){
            case 0:
                if (kind.contains(Player.GOLD)) {
                    player.removeGold(rewardGold);
                }else if(kind.contains(Player.LunarShard)){
                    player.removeLunarShard(rewardLunarShard);
                }else if(kind.contains(Player.SolarShard)){
                    player.removeSolarShard(rewardSolarShard);
                }else{
                    player.removeHonour(rewardHonour);
                }
                break;
            case 1:
                if(kind.contains(Player.LunarShard)){
                    player.removeLunarShard(rewardLunarShard);
                }else if(kind.contains(Player.SolarShard)){
                    player.removeSolarShard(rewardSolarShard);
                }else{
                    player.removeHonour(rewardHonour);
                }
                break;
            case 2:
                if(kind.contains(Player.SolarShard)){
                    player.removeSolarShard(rewardSolarShard);
                }else{
                    player.removeHonour(rewardHonour);
                }
                break;
            case 3:
                player.removeHonour(rewardHonour);
                break;
        }
    }
    @Override
    public String getReward(){
        String reward="";
        if (this.rewardGold > 0) reward += (this.rewardGold + Player.GOLD);
        if (this.rewardLunarShard > 0) reward += ("/" +this.rewardLunarShard + Player.LunarShard);
        if (this.rewardSolarShard > 0) reward += ("/" +this.rewardSolarShard + Player.SolarShard);
        if (this.rewardHonour > 0) reward += ("/" + this.rewardHonour + Player.HONOUR);
        return reward;
    }
}
