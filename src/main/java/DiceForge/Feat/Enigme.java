package DiceForge.Feat;

import DiceForge.Dice;
import DiceForge.Main;
import DiceForge.Player;

public class Enigme extends Feat{

    public Enigme(){
        super(10,0,6,true);
        this.name=nameFeat.Enigme;
    }

    public void effect(Object... o) {
        Dice reward = this.owner.getStrategy().chooseBestDice();
        for(int i=0; i<4;i++) {
            reward.rollDice();
            reward.giveReward(this.owner);
            if (Main.numberOfGames == 1) reward.toString(1);
        }
    }
}
