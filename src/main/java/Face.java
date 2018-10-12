public abstract class Face{

	int reward;

	public Face(int amount){
		this.reward = amount;
	}

	public abstract void giveReward(Player player);


}