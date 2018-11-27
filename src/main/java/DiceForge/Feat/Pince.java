package DiceForge.Feat;

public class Pince extends Feat{

    public Pince(){
        super(8,6,0,false);
        this.name=nameFeat.Pince;
    }

    @Override
    public void effect(Object... o) {
    	owner.faveur();
    	owner.faveur();
    }
}
