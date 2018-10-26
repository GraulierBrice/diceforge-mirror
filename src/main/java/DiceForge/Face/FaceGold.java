package DiceForge.Face;
import DiceForge.*;

public class FaceGold extends Face {

	public FaceGold(int amount,String kind){
		super(amount,kind);
	}

	public void giveReward(Player player) {
		player.addGold(this.reward);
	}

}