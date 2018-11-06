package DiceForge.Feat;
import DiceForge.*;

public class Hammer extends Feat{

	private int gold;
	private int level;

	public Hammer(){
		super(0,1,0,0);
		this.gold = 0;
		this.level = 0;
	}

	/* Accessor */
	public int getLevel(){
		return this.level;
	}

	public int getGold(){
		return this.gold;
	}

	/* Mutator */
	public void giveGold(int g){
		this.gold+=g;
	}

	//Recieves gold and grant honour at certain milestones
	public void effect(Object... o){
		this.gold+=(int)o[0];
		if(this.gold>=15) {
			level++;
			switch (level) {
				case 1 :
					owner.addHonour(10);
				break;	
				case 2 :
					owner.addHonour(15);
					owner.addGold(this.gold%=15); //in case overflow of gold
				break;	
			}
		}
		this.gold%=15; //Keeps number between 0-14
	}

}