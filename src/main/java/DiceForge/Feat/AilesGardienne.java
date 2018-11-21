package DiceForge.Feat;
import DiceForge.Face.*;

public class AilesGardienne extends Feat{

	FaceCombinationOR face = new FaceCombinationOR(1,1,1,0);

    public AilesGardienne(){
        super(4,0,2,true);
    }

    @Override
    public void effect(Object... o) {
    	face.giveRewardOR(this.owner,this.owner.getStrategy().chooseFaceBonus(face));
    }
}
