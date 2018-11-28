package DiceForge.Feat;
import DiceForge.Face.*;

public class MiroirAbyssal extends Feat{

    Face mirror;

    public MiroirAbyssal(){
        super(10,5,0,false);
        this.mirror = new FaceCombinationAND(0,0,0,0);
        this.mirror.setKind("mirror");
        this.name = nameFeat.MiroirAbyssal;
        this.effect();
    }

    @Override
    public void effect(Object... o) {
    	owner.getDice( (owner.getStrategy().chooseDice())%2 ).setFace(mirror,owner.getStrategy().chooseDiceFace((owner.getStrategy().chooseDice())%2));
    }
}
