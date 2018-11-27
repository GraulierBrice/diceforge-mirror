package DiceForge.Feat;

import DiceForge.Player;
import DiceForge.Referee;
import java.util.ArrayList;

public class Minotaure extends Feat{

    public Minotaure(){
        super(8,0,3,false);
    }

    @Override
    public void setPlayer(Player player){
        super.setPlayer(player);
        this.effect();
    }

    @Override
    public void effect(Object... o) {
        for(Player p:Referee.getPlayers()){
                if (p.getName() != this.owner.getName()) {
                    p.defaveur();
                }
            }
    }
}
