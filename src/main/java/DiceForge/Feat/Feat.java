package DiceForge.Feat;
import DiceForge.*;

//Abstract class used as Parent for in game feats
public abstract class Feat{

	Player owner;
	int honour, priceLunarShard, priceSolarShard;
	boolean reinfor;

	public Feat(int amount, int priceLunarShard,int priceSolarShard, boolean reinfor){
		this.honour = amount;
		this.owner = null; //set in Island with no owner
		this.priceLunarShard = priceLunarShard;
		this.priceSolarShard = priceSolarShard;
		this.reinfor = reinfor;

	}

	/* Accessor */
	public int getPriceLunarShard(){return this.priceLunarShard; }
	public int getPriceSolarShard(){return this.priceSolarShard; }
	public boolean getReinfor(){return this.reinfor;}

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