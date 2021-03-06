package DiceForge.Feat;
import DiceForge.Player;
import DiceForge.Dice;
import DiceForge.Main;

public class SabotArgent extends Feat{

    public SabotArgent(){
        super(2,2,0,true);
        this.name=nameFeat.SabotArgent;
    }

    //rolls dice to play
    public void effect(Object... o) {
        Dice reward = this.owner.getStrategy().chooseBestDice();
        reward.rollDice();
        reward.giveReward(this.owner);
        if(Main.LEVEL==1)reward.toString(1);
    }
}
