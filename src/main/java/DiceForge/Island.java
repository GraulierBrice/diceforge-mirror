package DiceForge;
import DiceForge.Feat.*;
import java.util.ArrayList;

public class Island{
	private int price;
    private ArrayList<Feat> feats;

    public Island(ArrayList<Feat> feats,Referee R){
        this.feats=feats;
        for(int i=0;i<feats.size();i++) {
            this.feats.get(i).setNbExploit(R.getNumberPlayer());
        }
    }

    public Feat getFeat(int i){
        return this.feats.get(i);
    }


}