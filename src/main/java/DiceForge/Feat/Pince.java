package DiceForge.Feat;
import DiceForge.Player;
import DiceForge.Main;

public class Pince extends Feat{

    public Pince(){
        super(8,6,0,false);
        this.name=nameFeat.Pince;
    }

    @Override
    public void setPlayer(Player player){
        super.setPlayer(player);
        this.effect();
    }

    @Override
    public void effect(Object... o) {
    	owner.faveur();
        if (Main.numberOfGames == 1) {owner.getDice(0).toString(1);owner.getDice(1).toString(2);}
    	owner.faveur();
        if (Main.numberOfGames == 1) {owner.getDice(0).toString(1);owner.getDice(1).toString(2);}
    }
}
