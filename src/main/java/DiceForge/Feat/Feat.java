package DiceForge.Feat;
import DiceForge.*;

public abstract class Feat{

	int honour;
	Player owner;

	public Feat(int amount){
		this.honour = amount;
		this.owner = null;
	}

	public int getHonour(){
		return this.honour;
	}

	public void setPlayer(Player player){
		this.owner = player;
	}

	public abstract void effect(Object... o);

}