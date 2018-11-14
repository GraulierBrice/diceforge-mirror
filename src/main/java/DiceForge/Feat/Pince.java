package DiceForge.Feat;

public class Pince extends Feat{

    public Pince(){
        super(8,6,0,true);
    }

    @Override
    public void effect(Object... o) {
    	owner.faveur();
    	owner.faveur();
    }
}
