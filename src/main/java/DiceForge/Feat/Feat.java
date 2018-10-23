package DiceForge.Feat;
import DiceForge.*;

public abstract class Feat{

	Player owner;
	int honour,nbExploit, price;
	String nameExploit;

	public Feat(int amount, int price){
		this.honour = amount;
		this.owner = null;
		this.price = price;
		this.nameExploit=null;
	}

	public int getPrice(){
		return this.price;
	}


	public void setNbExploit(int nbExploit){
	    this.nbExploit=nbExploit;
    }
    public int getNbExploit(){
	    return this.nbExploit;
    }

    public String getNameExploit(){
	    return this.nameExploit;
    }

	public int getHonour(){
		return this.honour;
	}

	public Player getOwner() { return owner; }

	public void setPlayer(Player player){
		this.owner = player;
		this.nbExploit--;
	}

	public abstract void effect(Object... o);

}