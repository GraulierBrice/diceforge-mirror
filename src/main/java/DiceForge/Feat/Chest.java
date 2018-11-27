package DiceForge.Feat;
import DiceForge.*;

public class Chest extends Feat {

    private int maxLunarShard;
    private int maxSolarShard;
    private int maxGold;

    public Chest() {
        super(2,1,0,false);
        this.name=nameFeat.Chest;
    }

    public void setPlayer(Player player){
        super.setPlayer(player);
        this.effect(); //activates on purchase
    }

    //Increase ressource capacity
    public void effect(Object... o){
        owner.setMaxLunarShard(this.owner.getMaxLunarShard()+3);
        owner.setMaxSolarShard(this.owner.getMaxSolarShard()+3);
        owner.setMaxGold(this.owner.getMaxGold()+4);
    }
}
