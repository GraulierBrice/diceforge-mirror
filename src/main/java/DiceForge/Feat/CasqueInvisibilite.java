package DiceForge.Feat;
import DiceForge.Face.*;
import DiceForge.Player;


public class CasqueInvisibilite extends Feat{

	Face timeThree;


    public CasqueInvisibilite(){
        super(4,5,0,false);
        timeThree = new FaceCombinationAND(0,0,0,0);
        timeThree.setKind("three");
        this.name=nameFeat.CasqueInvisibilite;
    }

    @Override
    public void effect(Object... o) {
        int dice = owner.getStrategy().chooseDice()%2;
        int diceFace = owner.getStrategy().chooseDiceFace(dice);
    	owner.getDice(dice).setFace(timeThree,diceFace);
        //owner.getDice( (owner.getStrategy().chooseDice())%2 ).setFace(timeThree,owner.getStrategy().chooseDiceFace((owner.getStrategy().chooseDice())%2));
    }

    public void setPlayer(Player player) {
        super.setPlayer(player);
        this.effect();
    }
}
