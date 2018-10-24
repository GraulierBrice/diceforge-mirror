package DiceForge.Feat;
import DiceForge.*;

//Abstract class used as Parent for in game feats
public abstract class Feat{

	Player owner;
	int honour, pricePdL, pricePdS;

	public Feat(int amount, int pricePdL,int pricePdS){
		this.honour = amount;
		this.owner = null; //set in Island with no owner
		this.pricePdL = pricePdL;
		this.pricePdS = pricePdS;
	}

	/* Accessor */
	public int getPricePdL(){return this.pricePdL; }
	public int getPricePdS(){return this.pricePdS; }

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