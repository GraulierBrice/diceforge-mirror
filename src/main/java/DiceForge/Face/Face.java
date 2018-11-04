package DiceForge.Face;
import DiceForge.*;

//Abstract Methode use as Parent of other Face Objects
public abstract class Face{

	int reward;
	int rewardGold;
	int rewardPdL;
	int rewardPdS;
	int rewardHonour;
	String kind="";

	public Face(int amount,String kind){
		this.kind=kind;
	    this.reward = amount;
	}
	public Face(int rewardGold, int rewardPdL, int rewardPdS, int rewardHonour) {
		this.rewardGold = rewardGold;
		this.rewardPdL = rewardPdL;
		this.rewardPdS = rewardPdS;
		this.rewardHonour = rewardHonour;
		if(rewardGold>0) kind+="G";
		if(rewardPdL>0) kind+="PdL";
		if(rewardPdS>0) kind+="PdS";
		if(rewardHonour>0) kind+="H";
	}

	public abstract void giveReward(Player player); //Overridden to adjust for each Face ressource type

	public String getReward() {
		String reward = "";
		if (this.rewardGold>0)reward += (this.rewardGold + "G");
		if(this.rewardPdL>0) reward+=(this.rewardPdL + "PdL");
		if(this.rewardPdS>0) reward+=(this.rewardPdS + "PdS");
		if(this.rewardHonour>0) reward+=(this.rewardHonour +"H");

		return reward;
	}

    public String getKind(){return this.kind;}
}