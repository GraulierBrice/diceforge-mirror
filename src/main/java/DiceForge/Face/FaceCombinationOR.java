package DiceForge.Face;

import DiceForge.Player;
import DiceForge.Announcer;

public class FaceCombinationOR extends Face {

    private String lastReward = "";

    public FaceCombinationOR(int rewardGold, int rewardLunarShard, int rewardSolarShard, int rewardHonour) {
        super(rewardGold, rewardLunarShard, rewardSolarShard, rewardHonour);

    }
    @Override
    public void giveReward(Player player){
        switch(player.getStrategy().chooseFaceOr(this)){
            case 0:
                if (kind.contains(Player.GOLD)) {
                    player.addGold(rewardGold);
                    lastReward = Player.GOLD;
                }else if(kind.contains(Player.LunarShard)){
                    player.addLunarShard(rewardLunarShard);
                    lastReward = Player.LunarShard;
                }else if(kind.contains(Player.SolarShard)){
                    player.addSolarShard(rewardSolarShard);
                    lastReward = Player.SolarShard;
                }else{
                    player.addHonour(rewardHonour);
                    lastReward = Player.HONOUR;
                }
                break;
            case 1:
                if(kind.contains(Player.LunarShard)){
                    player.addLunarShard(rewardLunarShard);
                    lastReward = Player.LunarShard;
                }else if(kind.contains(Player.SolarShard)){
                    player.addSolarShard(rewardSolarShard);
                    lastReward = Player.SolarShard;
                }else{
                    player.addHonour(rewardHonour);
                    lastReward = Player.HONOUR;
                }
                break;
            case 2:
                if(kind.contains(Player.SolarShard)){
                    player.addSolarShard(rewardSolarShard);
                    lastReward = Player.SolarShard;
                }else{
                    player.addHonour(rewardHonour);
                    lastReward = Player.HONOUR;
                }
                break;
            case 3:
                player.addHonour(rewardHonour);
                lastReward = Player.HONOUR;
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
        if (this.rewardGold > 0) reward += (lastReward.equals(Player.GOLD) ? Announcer.ANSI_YELLOW : "") +this.rewardGold + Player.GOLD+"/" +Announcer.ANSI_RESET;
        if (this.rewardLunarShard > 0) reward += (lastReward.equals(Player.LunarShard) ? Announcer.ANSI_YELLOW : "") + (this.rewardLunarShard + Player.LunarShard+"/" +Announcer.ANSI_RESET);
        if (this.rewardSolarShard > 0) reward += (lastReward.equals(Player.SolarShard) ? Announcer.ANSI_YELLOW : "") + (this.rewardSolarShard + Player.SolarShard+"/" +Announcer.ANSI_RESET);
        if (this.rewardHonour > 0) reward += (lastReward.equals(Player.HONOUR) ? Announcer.ANSI_YELLOW : "") + ( this.rewardHonour + Player.HONOUR+Announcer.ANSI_RESET);
        return reward;
    }
}
