package DiceForge.Face;
import DiceForge.*;

//Abstract Methode use as Parent of other Face Objects
public abstract class Face{

	int reward;
	String kind;

	public Face(int amount,String kind){
		this.kind=kind;
	    this.reward = amount;
	}

	public abstract void giveReward(Player player); //Overridden to adjust for each Face ressource type

	public String getReward(){return (this.reward+this.kind);}; //Display overridden depending on ressource

    public String getKind(){return this.kind;}
}