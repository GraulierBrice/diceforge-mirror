package DiceForge;

import DiceForge.Feat.*;
import java.util.ArrayList;

public class World {
    private ArrayList<Island> islands=new ArrayList<>();

    public World(Referee R){
        ArrayList<Feat> feats1 = new ArrayList<>();
        ArrayList<Feat> feats2 = new ArrayList<>();
        ArrayList<Feat> feats3 = new ArrayList<>();
        ArrayList<Feat> feats4 = new ArrayList<>();
        ArrayList<Feat> feats5 = new ArrayList<>();
        ArrayList<Feat> feats6 = new ArrayList<>();
        ArrayList<Feat> feats7 = new ArrayList<>();

        for(int i=0;i<R.getNumberPlayer();i++){
            feats1.add(new Hammer());feats1.add(new Chest());
            feats2.add(new Ancien());feats2.add(new HerbesFolles());
            feats3.add(new SabotArgent());feats3.add(new Satyres());
            feats4.add(new AilesGardienne());feats4.add(new Minotaure());
            feats5.add(new Passeur());feats5.add(new CasqueInvisibilite());
            feats6.add(new Meduse());feats6.add(new MiroirAbyssal());
            feats7.add(new Pince());feats7.add(new Hydre());feats7.add(new Enigme());

        }
        islands.add(new Island(feats1));
        islands.add(new Island(feats2));
        islands.add(new Island(feats3));
        islands.add(new Island(feats4));
        islands.add(new Island(feats5));
        islands.add(new Island(feats6));
        islands.add(new Island(feats7));
    }
    /* Accessor */
    public Island getIsland(int n){return this.islands.get(n); }

    public Island lowestIslandNotEmpty(String kind){
        for(Island i:islands){
            if(!i.isEmpty() && i.doesItCost(kind)){
                return i;
            }
        }
        return null;
    }

    public boolean isEmpty(int n){
        return this.islands.get(n).isEmpty();
    }

    public void giveFeat(Player player, Class feat){
        this.islands.get(player.getCurrentIsland()).getFeat(feat).setPlayer(player);
        player.removePdL(this.islands.get(player.getCurrentIsland()).getFeat(feat).getPricePdL());
        player.removePdS(this.islands.get(player.getCurrentIsland()).getFeat(feat).getPricePdS());
        this.islands.get(player.getCurrentIsland()).removeFeat(feat);
    }
}
