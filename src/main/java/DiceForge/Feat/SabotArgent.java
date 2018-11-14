package DiceForge.Feat;
import DiceForge.Player;
import DiceForge.Dice;
import DiceForge.Main;

public class SabotArgent extends Feat{

    public SabotArgent(){super(2,2,0,true);}

    //rolls dice to play
    public void effect(Object... o) {
        Dice reward = this.owner.chooseBestDice();
        reward.rollDice();
        reward.giveReward(this.owner);
        if(Main.numberOfGames==1)reward.toString(1);
    }

    @Override
    public void setPlayer(Player player){
        super.setPlayer(player);
    }
}
