package DiceForge;
import DiceForge.Feat.*;

import java.util.ArrayList;
import java.lang.*;

public class Island{
    private ArrayList<Feat> feats;

    public Island(ArrayList<Feat> feats){
        this.feats=feats;
    }

    /* Accessor */
    public Feat getFeat(Class c){
        return feats.stream().filter(c::isInstance).findFirst().orElse(null);
    }

    //checks if Feat type is present in island
    public Boolean isIn(Class c){
        return feats.stream().anyMatch(c::isInstance);
    }

    public int numOfFeats(){
        return this.feats.size();
    }

    public void removeFeat(Class c){
        feats.remove(getFeat(c)); //Lambda streams are fun
    }

}