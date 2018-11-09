package DiceForge.Feat;

import DiceForge.Player;

public class HerbesFolles extends Feat {

    public HerbesFolles() {
        super(2,0,1,false);
    }

    public void setPlayer(Player player) {
        super.setPlayer(player);
        this.effect();
    }

    public void effect(Object... o) {
        owner.addGold(3);
        owner.addPdL(3);
    }
}
