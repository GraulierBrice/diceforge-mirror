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

	public abstract void removeReward(Player player);

	public abstract String getReward();

	public int getRewardKind(String kind){
		if(kind.contains(Player.GOLD)) return this.rewardGold;
		else if(kind.contains(Player.SolarShard)) return this.rewardSolarShard;
		else if(kind.contains(Player.LunarShard)) return this.rewardLunarShard;
		else if(kind.contains(Player.HONOUR)) return this.rewardHonour;
		return 0;
	}

	public int getNumberOfKind(){
		int number=0;
		if(rewardGold>0)number++;
		if(rewardHonour>0)number++;
		if(rewardLunarShard>0)number++;
		if(rewardSolarShard>0)number++;
		return number;
	}

    public String getKind(){return this.kind;}

    public void setKind(String a){this.kind+=a;}

    public boolean compareTo(Face f){
    	return this.kind.equals(f.getKind()) && this.getReward().equals(f.getReward());
    }

}