package DiceForge;
import DiceForge.Face.*;
import DiceForge.Feat.*;

import java.util.ArrayList;

public abstract class Player {
    private ArrayList<Feat> feats=new ArrayList<>();
    private int honour;
    private int PdL;
    private int PdS;
    private int gold;
    private int maxPdL=6;
    private int maxPdS=6;
    private int maxGold=12;

    private Dice de1 = new Dice(new FaceGold(1),new FaceGold(1),new FaceGold(1),new FaceGold(1),new FaceGold(1),new FacePdS(1));
    private Dice de2 = new Dice(new FaceGold(1),new FaceGold(1),new FaceGold(1),new FaceGold(1),new FaceHonour(1),new FacePdL(1));

    public Player(){
        this.honour=0;
        this.PdL=0;
        this.PdS=0;
        this.gold=0;
    }

    public int getHonour(){
        return this.honour;
    }
    public int getPdL(){
        return this.PdL;
    }
    public int getPdS(){
        return this.PdS;
    }
    public int getGold(){
        return this.gold;
    }
    public Dice getDice(int n){
        if(n==0){
            return this.de1;
        }else if(n==1){
            return this.de2;
        }
        return null;
    }
    public Feat getFeat(int n){
        return this.feats.get(n);
    }

    public int getNbFeat(){ return this.feats.size();}

    public void addFeat(Feat feat){
        this.feats.add(feat);
    }

    public void addHonour(int honour){
        this.honour+=honour;
    }
    public void addPdL(int PdL){
        if(this.PdL+PdL<=maxPdL){
            this.PdL+=PdL;
        }else {
            this.PdL=6;
        }
    }
    public void addPdS(int PdS){
        if(this.PdS+PdS<=maxPdS){
            this.PdS+=PdS;
        }else {
            this.PdS=6;
        }
    }
    public void addGold(int gold){
        for(Feat f : this.feats){
            if(f instanceof Hammer && ((Hammer)f).getLevel() < 2){gold = this.goldChoice(gold, (Hammer)f); break;}
        }
        if(this.gold+gold<=maxGold){
            this.gold+=gold;
        }else {
            this.gold=12;
        }
    }
    public void removePdL(int PdL){
        if(this.PdL-PdL>=0){
            this.PdL-=PdL;
        }else{
            this.PdL=0;
        }
    }
    public void removePdS(int PdS){
        if(this.PdS-PdS>=0){
            this.PdS-=PdS;
        }else {
            this.PdS=0;
        }
    }
    public void removeGold(int gold){
        if(this.gold-gold>=0){
            this.gold-=gold;
        }else{
            this.gold=0;
        }
    }
    public void faveur(){
        this.de1.getFace(this.de1.rollDice()).giveReward(this);
        this.de2.getFace(this.de2.rollDice()).giveReward(this);
    }
    public void buy(Referee R,Pool pool,int poolFace,int diceNumber,int diceFace){
        this.getDice(diceNumber).setFace(pool.buy(R.getPlayer(R.getTurnPlayer()), pool.getFace(poolFace)), diceFace);
    }

    public abstract String chooseAction();
    public abstract int chooseDice();
    public abstract int chooseDiceFace();
    public abstract int choosePoolFace(Pool pool);
    public abstract int choosePool();
    public abstract int goldChoice(int g, Hammer h);

}
