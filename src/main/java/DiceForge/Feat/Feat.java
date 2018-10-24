package DiceForge.Feat;
import DiceForge.*;

//Abstract class used as Parent for in game feats
public abstract class Feat{

	Player owner;
	int honour, price;

	public Feat(int amount, int price){
		this.honour = amount;
		this.owner = null; //set in Island with no owner
		this.price = price;
	}

	/* Accessor */
	public int getPrice(){
		return this.price;
	}

	public int getHonour(){
		return this.honour;
	}

	public Player getOwner() {
		return owner;
	}

	/* Mutator */
	public void setPlayer(Player player){
		this.owner = player;
		owner.addHonour(this.honour);
		player.addFeat(this);
	}

	//Abstract method set as general for any possible card effect
	public abstract void effect(Object... o);

}