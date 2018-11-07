package DiceForge;

import DiceForge.Face.*;
import DiceForge.Feat.*;

import java.util.ArrayList;

public abstract class Player {
    protected ArrayList<Feat> feats=new ArrayList<>();
    protected int honour, gold, PdL, PdS, maxPdL = 6, maxPdS=6, maxGold=12;
    protected int currentIsland=0;//ile début à modif pour mettre une valeur "ile de départ"
    protected Dice de1 = new Dice(new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(0,0,1,0));
    protected Dice de2 = new Dice(new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(0,0,0,1),new FaceCombinationAND(0,1,0,0));

    public Player(){
        this.honour=0;
        this.PdL=0;
        this.PdS=0;
        this.gold=0;
    }

    /* Accessor */
    public int getHonour(){return this.honour;}
    public int getPdL(){return this.PdL;}
    public int getPdS(){return this.PdS;}
    public int getGold(){return this.gold;}
    public int getMaxPdL(){return this.maxPdL;}
    public int getMaxPdS(){return this.maxPdS;}
    public int getMaxGold(){return this.maxGold;}
    public int getCurrentIsland(){return this.currentIsland;}
    public Feat getFeat(int n){return this.feats.get(n);}
    public int getNbFeat(){return this.feats.size();}
    public Dice getDice(int n){return (n==0) ? this.de1 : (n==1) ? this.de2 : null;}

    /* Mutator */
    public void setMaxPdL(int n){this.maxPdL = n;}
    public void setMaxPdS(int n){this.maxPdS = n;}
    public void setMaxGold(int n){this.maxGold = n;}
    public void addFeat(Feat feat){this.feats.add(feat);}
    public void addHonour(int honour){this.honour+=honour;}
    public void addPdL(int PdL){this.PdL = (this.PdL+PdL<=maxPdL) ? this.PdL+PdL : maxPdL;}
    public void addPdS(int PdS){this.PdS = (this.PdS+PdS<=maxPdS) ? this.PdS+PdS : maxPdS;}
    public void addGold(int gold){
        for(Feat f : this.feats){
            if(f instanceof Hammer && ((Hammer)f).getLevel() < 2){gold = this.goldChoice(gold, (Hammer)f); break;}
        }
        this.gold = (this.gold+gold<=maxGold) ? this.gold+gold : maxGold;
    }
    public void removePdL(int PdL){this.PdL = (this.PdL-PdL>=0) ? this.PdL-PdL : 0;}
    public void removePdS(int PdS){this.PdS = (this.PdS-PdS>=0) ? this.PdS-PdS : 0;}
    public void removeGold(int gold){this.gold = (this.gold-gold>=0) ? this.gold-gold : 0;}
    
    //Rolls dice and adds rewards to player's ressources
    public void faveur(){
        this.de1.giveReward(this);
        this.de2.giveReward(this);
    }

    //Purchase a dice face and set it on a dice of chosing
    public void buy(Referee R,Pool pool,int poolFace,int diceNumber,int diceFace){
        this.getDice(diceNumber).setFace(pool.buy(R.getPlayer(R.getTurnPlayer()), pool.getFace(poolFace)), diceFace);
    }

    public void reset(){
        this.de1 = new Dice(new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(0,0,1,0));
        this.de2 = new Dice(new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(0,0,0,1),new FaceCombinationAND(0,1,0,0));
        this.gold=0;
        this.honour=0;
        this.PdL=0;
        this.PdS=0;
        this.maxPdL=6;
        this.maxPdS=6;
        this.maxGold=12;
        this.currentIsland=0;// à modif pour mettre une valeur "ile de départ"
        this.feats=new ArrayList<>();
    }

    public boolean doIHaveAnHammer(){
        for(Feat f:feats){
            if(f instanceof Hammer) {
                Hammer hammer = (Hammer) f;
                if (hammer.getLevel() != 2) {
                    return true;
                }
            }
        }
        return false;
    }

    public Class listFeat(int featNumber){
        switch(this.currentIsland){
            case 0:
                switch(featNumber){
                    case 0:
                        return Hammer.class;
                    case 1:
                        return Chest.class;
                }
            case 1:
                switch(featNumber){
                    case 0:
                        return Ancien.class;
                    case 1:
                        return HerbesFolles.class;
                }
            case 2:
                switch(featNumber){
                    case 0:
                        return SabotArgent.class;
                    case 1:
                        //return Les satyres.class;
                }
            case 3:
                switch(featNumber){
                    case 0:
                        //return Les ailes de la gardienne.class;
                    case 1:
                        //return Le minotaure.class;
                }
            case 4:
                switch(featNumber){
                    case 0:
                        return Passeur.class;
                    case 1:
                        //return Le casque d'invisibilité.class;
                }
            case 5:
                switch(featNumber){
                    case 0:
                       // return Meduse.class;
                    case 1:
                        //return Le miroir abyssal.class;
                }
            case 6:
                switch(featNumber){
                    case 0:
                        //return La pince.class;
                    case 1:
                        //return L'hydre.class;
                    case 2:
                        //return L'énigme.class;
                }
            default:
                return null;
        }
    }

    public abstract String chooseReinforcement();
    public abstract String chooseFeatReinforcement();
    public abstract String chooseAction();
    public abstract int chooseDice();
    public abstract int chooseDiceFace(int dice);
    public abstract int chooseFaceBonus(Face face);
    public abstract int choosePoolFace(Pool pool);
    public abstract int choosePool();
    public abstract int goldChoice(int g, Hammer h);
    public abstract void chooseIsland();
    public abstract int chooseFeat();
    public abstract Dice chooseBestDice();

}
