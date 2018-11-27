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
    public Feat getFeat(nameFeat featName){
        for(Feat f:feats){
            if(f.getName()==featName)return f;
        }
        return null;
    }
    public void setFeats(Feat feat){ this.feats.add(feat);}

    //checks if Feat type is present in island
    public Boolean isIn(nameFeat featName){
        if(featName==null) return false;
        else {
            for(Feat f:feats){
                if(f.getName()==featName) return true;
            }
        }
        return false;
    }

    public int numOfFeats(){
        return this.feats.size();
    }

    public void removeFeat(nameFeat featName){
        feats.remove(getFeat(featName)); //Lambda streams are fun
    }

    public boolean isEmpty(){
        return feats.isEmpty();
    }

    public boolean doesItCost(String kind) {
        for (Feat f : feats) {
            if ((kind.equals(Player.LunarShard) && f.getPriceLunarShard()>0) || (kind.equals(Player.SolarShard) && f.getPriceSolarShard()>0)) {
                return true;
            }
        }
        return false;
    }

    public Feat lowestPriceOfFeat(String kind){
        int number=0;
        for(int i=0;i<feats.size()-1;i++) {
            if(kind.equals(Player.LunarShard)) {
                if (feats.get(i).getPriceLunarShard()<feats.get(i+1).getPriceLunarShard()){
                    number=i;
                }else {
                    number=i+1;
                }
            }else if(kind.equals(Player.SolarShard)){
                if(feats.get(i).getPriceSolarShard()<feats.get(i+1).getPriceSolarShard()){
                    number=i;
                }else {
                    number=i+1;
                }
            }
        }
        return feats.get(number);
    }

}