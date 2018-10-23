package DiceForge.Feat;
import DiceForge.*;

public class Hammer extends Feat{

	private int gold;
	private int level;


	public Hammer(){
		super(0,1);
		this.gold = 0;
		this.level = 0;
		this.nameExploit="Hammer";
	}

	public int getLevel(){
		return this.level;
	}

	public int getGold(){
		return this.gold;
	}

	public void giveGold(int g){
		this.gold+=g;
	}

	public void setPlayer(Player player){
	    super.setPlayer(player);
        player.addFeat(this);

    }

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
					owner.addGold(this.gold%=15);
				break;	
			}
		}
		this.gold%=15;
	}

}