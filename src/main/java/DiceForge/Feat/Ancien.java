package DiceForge.Feat;
import DiceForge.Player;

public class Ancien extends Feat{

    public Ancien(){
        super(0,0,1,true);
        this.name=nameFeat.Ancien;
    }

    //trade gold vs honour
    public void effect(Object... o) {
        if (owner.getGold() >= 3) {
            owner.removeGold(3);
            owner.addHonour(4);
        }
    }
}
