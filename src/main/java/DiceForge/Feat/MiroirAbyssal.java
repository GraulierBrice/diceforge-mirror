package DiceForge.Feat;

public class MiroirAbyssal extends Feat{

    public MiroirAbyssal(){
        super(10,5,0,false);
        mirror = new FaceCombinationAND(0,0,0,0);
        mirror.setKind("mirror");
        this.name = nameFeat.MiroirAbyssal;
    }

    @Override
    public void effect(Object... o) {
    	owner.getDice( (owner.getStrategy().chooseDice())%2 ).setFace(mirror,owner.getStrategy().chooseDiceFace((owner.getStrategy().chooseDice())%2));
    }
}
