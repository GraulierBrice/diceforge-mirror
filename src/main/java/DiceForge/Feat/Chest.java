package DiceForge.Feat;
import DiceForge.*;

public class Chest extends Feat {

    private int maxPdL;
    private int maxPdS;
    private int maxGold;

    public Chest() {
        super(2,1);
        this.nameExploit="Chest";
    }

    public void setPlayer(Player player){
        super.setPlayer(player);
        this.effect(null);
    }

    public void effect(Object... o){
        owner.setMaxPdL(this.owner.getMaxPdL()+3);
        owner.setMaxPdS(this.owner.getMaxPdS()+3);
        owner.setMaxGold(this.owner.getMaxGold()+4);

    }
}
