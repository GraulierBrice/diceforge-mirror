package DiceForge.Face;
import DiceForge.*;

//Abstract Methode use as Parent of other Face Objects
public abstract class Face{

	int rewardGold;
	int rewardPdL;
	int rewardPdS;
	int rewardHonour;
	String kind="";


	public Face(int rewardGold, int rewardPdL, int rewardPdS, int rewardHonour) {
		this.rewardGold = rewardGold;
		this.rewardPdL = rewardPdL;
		this.rewardPdS = rewardPdS;
		this.rewardHonour = rewardHonour;
		if(rewardGold>0) kind+=Player.GOLD;
		if(rewardPdL>0) kind+=Player.PDL;
		if(rewardPdS>0) kind+=Player.PDS;
		if(rewardHonour>0) kind+=Player.HONOUR;
	}

	public abstract void giveReward(Player player); //Overridden to adjust for each Face ressource type

	public String getReward() {
		String reward = "";
		if (this instanceof FaceCombinationAND){
			if (this.rewardGold > 0) reward += (this.rewardGold + Player.GOLD);
			if (this.rewardPdL > 0) reward += (this.rewardPdL + Player.PDL);
			if (this.rewardPdS > 0) reward += (this.rewardPdS + Player.PDS);
			if (this.rewardHonour > 0) reward += (this.rewardHonour +Player.HONOUR);
		}else if(this instanceof FaceCombinationOR) {
                if (this.rewardGold > 0) reward += (this.rewardGold + Player.GOLD);
                if (this.rewardPdL > 0) reward += (this.rewardPdL + Player.PDL);
                if (this.rewardPdS > 0) reward += (this.rewardPdS + Player.PDS);
                if (this.rewardHonour > 0) reward += ("/" + this.rewardHonour + Player.HONOUR);
		}
		return reward;
	}

    public String getKind(){return this.kind;}

	public abstract void giveRewardOR(Player player, int chooseFaceBonus);
}