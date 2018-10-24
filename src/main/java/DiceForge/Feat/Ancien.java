package DiceForge.Feat;
import DiceForge.Player;

public class Ancien extends Feat{

    public Ancien(){super(0,0,1);}

    //trade gold vs honour
    public void effect(Object... o) {
        owner.removeGold(3);
        owner.addHonour(4);
    }
}
