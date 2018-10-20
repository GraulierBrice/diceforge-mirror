package DiceForge.Face;
import DiceForge.*;

public abstract class Face{

	int reward;

	public Face(int amount){
		this.reward = amount;
	}

	public abstract void giveReward(Player player);

	public abstract String getReward();

}