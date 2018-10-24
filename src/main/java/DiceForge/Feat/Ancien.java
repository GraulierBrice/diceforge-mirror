package DiceForge.Feat;
import DiceForge.Player;

public class Ancien extends Feat{

    public Ancien(){super(0,1);}
    public void setPlayer(Player player){
        super.setPlayer(player);
        this.effect(); //activates on purchase
    }

    //trade gold vs honour
    public void effect(Object... o) {
        owner.removeGold(3);
        owner.addHonour(4);
    }
}
