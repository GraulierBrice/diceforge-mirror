package DiceForge.Face;
import DiceForge.*;

//Abstract Methode use as Parent of other Face Objects
public abstract class Face{

	int rewardGold;
	int rewardLunarShard;
	int rewardSolarShard;
	int rewardHonour;
	String kind="";


	public Face(int rewardGold, int rewardLunarShard, int rewardSolarShard, int rewardHonour) {
		this.rewardGold = rewardGold;
		this.rewardLunarShard = rewardLunarShard;
		this.rewardSolarShard = rewardSolarShard;
		this.rewardHonour = rewardHonour;
		if(rewardGold>0) kind+=Player.GOLD;
		if(rewardLunarShard>0) kind+=Player.LunarShard;
		if(rewardSolarShard>0) kind+=Player.SolarShard;
		if(rewardHonour>0) kind+=Player.HONOUR;
	}

	public abstract void giveReward(Player player); //Overridden to adjust for each Face ressource type

	public String getReward() {
		String reward = "";
		if (this instanceof FaceCombinationAND){
			if (this.rewardGold > 0) reward += (this.rewardGold + Player.GOLD);
			if (this.rewardLunarShard > 0) reward += (this.rewardLunarShard + Player.LunarShard);
			if (this.rewardSolarShard > 0) reward += (this.rewardSolarShard + Player.SolarShard);
			if (this.rewardHonour > 0) reward += (this.rewardHonour +Player.HONOUR);
		}else if(this instanceof FaceCombinationOR) {
                if (this.rewardGold > 0) reward += (this.rewardGold + Player.GOLD);
                if (this.rewardLunarShard > 0) reward += (this.rewardLunarShard + Player.LunarShard);
                if (this.rewardSolarShard > 0) reward += (this.rewardSolarShard + Player.SolarShard);
                if (this.rewardHonour > 0) reward += ("/" + this.rewardHonour + Player.HONOUR);
		}
		return reward;
	}

    public String getKind(){return this.kind;}

	public abstract void giveRewardOR(Player player, int chooseFaceBonus);
}