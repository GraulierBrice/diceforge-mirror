package DiceForge.Feat;
import DiceForge.*;

public abstract class Feat{

	Player owner;
	int honour,nbExploit;
	String nameExploit;

	public Feat(int amount){
		this.honour = amount;
		this.owner = null;
		this.nameExploit=null;
	}

	public abstract int getPrice();


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