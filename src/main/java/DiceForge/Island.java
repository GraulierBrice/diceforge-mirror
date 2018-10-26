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
    public void setFeats(Feat feat){ this.feats.add(feat);}

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

    public boolean isEmpty(){
        return feats.isEmpty();
    }

    public boolean doesItCost(String kind) {
        for (Feat f : feats) {
            if ((kind.equals("PdL") && f.getPricePdL()>0) || (kind.equals("PdS") && f.getPricePdS()>0)) {
                return true;
            }
        }
        return false;
    }

    public Feat lowestPriceOfFeat(String kind){
        int number=0;
        for(int i=0;i<feats.size()-1;i++) {
            if(kind.equals("PdL")) {
                if (feats.get(i).getPricePdL()<feats.get(i+1).getPricePdL()){
                    number=i;
                }else {
                    number=i+1;
                }
            }else if(kind.equals("PdS")){
                if(feats.get(i).getPricePdS()<feats.get(i+1).getPricePdS()){
                    number=i;
                }else {
                    number=i+1;
                }
            }
        }
        return feats.get(number);
    }

}