package DiceForge.Face;

import DiceForge.Player;

public class FaceCombinationOR extends Face {

    public FaceCombinationOR(int rewardGold, int rewardPdL, int rewardPdS, int rewardHonour) {
        super(rewardGold, rewardPdL, rewardPdS, rewardHonour);

    }
    public void giveReward(Player player){}

    public void giveRewardOR(Player player,int choose) {
        switch(choose){
            case 0:
                if (kind.contains("G")) {
                    player.addGold(rewardGold);
                }else if(kind.contains("PdL")){
                    player.addPdL(rewardPdL);
                }else if(kind.contains("PdS")){
                    player.addPdS(rewardPdS);
                }else{
                    player.addHonour(rewardHonour);
                }
                break;
            case 1:
                if(kind.contains("PdL")){
                    player.addPdL(rewardPdL);
                }else if(kind.contains("PdS")){
                    player.addPdS(rewardPdS);
                }else{
                    player.addHonour(rewardHonour);
                }
                break;
            case 2:
                if(kind.contains("PdS")){
                    player.addPdS(rewardPdS);
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
