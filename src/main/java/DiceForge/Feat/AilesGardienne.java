package DiceForge.Feat;
import DiceForge.Face.*;
import DiceForge.Main;

public class AilesGardienne extends Feat{

	FaceCombinationOR face = new FaceCombinationOR(1,1,1,0);

    public AilesGardienne(){
        super(4,0,2,true);
        this.name=nameFeat.AilesGardienne;
    }

    @Override
    public void effect(Object... o) {
    	face.giveReward(this.owner);
    	if(Main.LEVEL==1)System.out.println(owner.getName()+" choisis une ressource:"+"\n"+face.getReward());
    }
}
