package DiceForge.Face;
import DiceForge.*;

public class FaceGold extends Face {

	public FaceGold(int amount){
		super(amount);
	}

	public void giveReward(Player player) {
		player.addGold(this.reward);
	}

	public String getReward(){
		return (Integer.toString(this.reward)+"G");

	}

}