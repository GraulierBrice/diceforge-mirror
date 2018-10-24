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
        for(Feat f : feats){
             if(c.isInstance(f)) return f;
        }
        return null;
    }

    //checks if Feat type is present in island
    public Boolean isIn(Class c){
        for (Feat f : feats)
            if (c.isInstance(f)) return true;
        return false;
    }

    public int numOfFeats(){
        return this.feats.size();
    }

    public void removeFeat(Class c){
        feats.remove(feats.stream().filter(c::isInstance).findFirst().orElse(null)); //Lambda streams are fun
    }

}