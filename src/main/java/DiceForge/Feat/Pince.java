package DiceForge.Feat;
import DiceForge.Player;

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
    	owner.faveur();
    }
}
