package DiceForge.Feat;
import DiceForge.Face.*;
import DiceForge.Player;

public class MiroirAbyssal extends Feat{

    Face mirror;

    public MiroirAbyssal(){
        super(10,5,0,false);
        this.mirror = new FaceCombinationAND(0,0,0,0);
        this.mirror.setKind("mirror");
        this.name = nameFeat.MiroirAbyssal;
    }

    @Override
    public void effect(Object... o) {
        int dice = owner.getStrategy().chooseDice()%2;
        int diceFace = owner.getStrategy().chooseDiceFace(dice);
        owner.getDice(dice).setFace(mirror,diceFace);
    }

    public void setPlayer(Player player) {
        super.setPlayer(player);
        this.effect();
    }
}
