package DiceForge.Feat;

import DiceForge.Dice;
import DiceForge.Main;
import DiceForge.Player;

public class Enigme extends Feat{

    public Enigme(){
        super(10,0,6,false);
        this.name=nameFeat.Enigme;
    }

    @Override
    public void setPlayer(Player player){
        super.setPlayer(player);
        this.effect();
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
