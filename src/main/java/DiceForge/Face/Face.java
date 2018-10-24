package DiceForge.Face;
import DiceForge.*;

//Abstract Methode use as Parent of other Face Objects
public abstract class Face{

	int reward;

	public Face(int amount){
		this.reward = amount;
	}

	public abstract void giveReward(Player player); //Overridden to adjust for each Face ressource type

	public abstract String getReward(); //Display overridden depending on ressource
}