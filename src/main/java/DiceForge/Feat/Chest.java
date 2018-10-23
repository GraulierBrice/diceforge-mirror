package DiceForge.Feat;
import DiceForge.*;

public class Chest extends Feat {

    private int maxPdL;
    private int maxPdS;
    private int maxGold;

    public Chest() {
        super(1,1);
        this.nameExploit="Chest";
    }

    public void setPlayer(Player player){
        super.setPlayer(player);
        player.addFeat(this);

    }

    public void effect(Object... o){
        owner.setMaxPdL(owner.getMaxPdL()+3);
        owner.setMaxPdS(owner.getMaxPdS()+3);
        owner.setMaxGold(owner.getMaxGold()+4);

    }
}
