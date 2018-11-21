package DiceForge.Feat;
import DiceForge.Face.*;
import DiceForge.AI.*;


public class CasqueInvisibilite extends Feat{

	FaceCombinationAND timeThree;


    public CasqueInvisibilite(){
        super(4,5,0,false);
        timeThree = new FaceCombinationAND(0,0,0,0);
        //timeThree.setKind("three");
    }

    @Override
    public void effect(Object... o) {
    	owner.getDice((owner.getStrategy().chooseDice()+1)%2).setFace(timeThree,owner.getStrategy().chooseDiceFace((owner.getStrategy().chooseDice()+1)%2));
    }
}
