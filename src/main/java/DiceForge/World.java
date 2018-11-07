package DiceForge;

import DiceForge.Feat.*;
import java.util.ArrayList;

public class World {
    private ArrayList<Island> islands=new ArrayList<>();

    public World(Referee R){
        ArrayList<Feat> feats1 = new ArrayList<>();
        ArrayList<Feat> feats2 = new ArrayList<>();
        //ArrayList<Feat> feats3 = new ArrayList<>();
        //ArrayList<Feat> feats4 = new ArrayList<>();
        ArrayList<Feat> feats5 = new ArrayList<>();
        ArrayList<Feat> feats6 = new ArrayList<>();
        //ArrayList<Feat> feats7 = new ArrayList<>();

        for(int i=0;i<R.getNumberPlayer();i++){
            feats1.add(new Hammer());feats1.add(new Chest());
            feats2.add(new Ancien());feats2.add(new HerbesFolles());
            //feats3.add();feats3.add();
            //feats4.add(new );feats4.add();
            feats5.add(new Passeur());//feats5.add();
            feats6.add(new Meduse());//feats6.add();
            //feats7.add();feats7.add();feats7.add();

        }
        islands.add(new Island(feats1));
        islands.add(new Island(feats2));
        //islands.add(new Island(feats3));
        //islands.add(new Island(feats4));
        islands.add(new Island(feats5));
        islands.add(new Island(feats6));
        //islands.add(new Island(feats7));
    }
    /* Accessor */
    public Island getIsland(int n){return this.islands.get(n); }

    public Island lowestIslandNotEmpty(String kind){
        for(Island i:islands){
            if(i.isEmpty()==false && i.doesItCost(kind)){
                return i;
            }
        }
        return null;
    }
}
