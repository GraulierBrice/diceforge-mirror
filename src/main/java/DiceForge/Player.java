package DiceForge;

import DiceForge.AI.*;
import DiceForge.Face.*;
import DiceForge.Feat.*;

import java.util.ArrayList;

public class Player {
    protected ArrayList<Feat> feats=new ArrayList<>();
    protected ArrayList<Face> ennemyFaces=new ArrayList<>();
    protected int honour, gold, lunarShard, solarShard, maxLunarShard = 6, maxSolarShard =6, maxGold=12;
    protected int currentIsland=-1;//ile début à modif pour mettre une valeur "ile de départ"
    protected Dice de1 = new Dice(new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(0,0,1,0));
    protected Dice de2 = new Dice(new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(0,0,0,1),new FaceCombinationAND(0,1,0,0));
    protected Strategy strategy;
    protected int nbVictory=0, sumHonour=0;
    protected String name;
    public static final String GOLD="G";
    public static final String HONOUR="H";
    public static final String LunarShard="LunarShard";
    public static final String SolarShard="SolarShard";
    private String action;
    private boolean hasReplayed=false;

    public Player(Object strategy, String name){
        this.strategy = (Strategy) strategy;
        this.strategy.setPlayer(this);
        this.honour=0;
        this.lunarShard =0;
        this.solarShard =0;
        this.gold=0;
        this.name=name;
    }

    public Player(String name) {
        this.strategy = new RandomAI();
        this.strategy.setPlayer(this);
        this.honour=0;
        this.lunarShard =0;
        this.solarShard =0;
        this.gold=0;
        this.name=name;
    }

    public Player() {
        this.strategy = new RandomAI();
        this.strategy.setPlayer(this);
        this.honour=0;
        this.lunarShard =0;
        this.solarShard =0;
        this.gold=0;
        this.name="1";
    }

    /* Accessor */
    public int getHonour(){return this.honour;}
    public int getLunarShard(){return this.lunarShard;}
    public int getSolarShard(){return this.solarShard;}
    public int getGold(){return this.gold;}
    public int getMaxLunarShard(){return this.maxLunarShard;}
    public int getMaxSolarShard(){return this.maxSolarShard;}
    public int getMaxGold(){return this.maxGold;}
    public int getCurrentIsland(){return this.currentIsland;}
    public Feat getFeat(int n){return this.feats.get(n);}
    public int getNbFeat(){return this.feats.size();}
    public Dice getDice(int n){return (n==0) ? this.de1 : (n==1) ? this.de2 : null;}
    public Strategy getStrategy() { return strategy; }
    public int getNbVictory(){return this.nbVictory;}
    public int getSumHonour(){return this.sumHonour;}
    public String getName(){return this.name;}
    public ArrayList<Face> getEnnemyFaces(){return this.ennemyFaces;}
    public String getAction(){return this.action;}
    public boolean getHasReplayed(){return this.hasReplayed;}

    /* Mutator */
    public void setHasReplayed(boolean bool){this.hasReplayed=bool;}
    public void setAction(String action){this.action=action;}
    public void setMaxLunarShard(int n){this.maxLunarShard = n;}
    public void setMaxSolarShard(int n){this.maxSolarShard = n;}
    public void setCurrentIsland(int currentIsland) { this.currentIsland = currentIsland; }
    public void setEnnemyFaces(ArrayList<Face> faces){ this.ennemyFaces=faces;}
    public void setMaxGold(int n){this.maxGold = n;}
    public void addFeat(Feat feat){this.feats.add(feat);}
    public void addHonour(int honour){this.honour+=honour;}
    public void addLunarShard(int LunarShard){this.lunarShard = (this.lunarShard +LunarShard<= maxLunarShard) ? this.lunarShard +LunarShard : maxLunarShard;}
    public void addSolarShard(int SolarShard){this.solarShard = (this.solarShard +SolarShard<= maxSolarShard) ? this.solarShard +SolarShard : maxSolarShard;}
    public void addGold(int gold){
        for(Feat f : this.feats){
            if(f.getName()==nameFeat.Hammer && ((Hammer)f).getLevel() < 2){gold = this.strategy.goldChoice(gold, (Hammer)f, this.getStrategy().getName()); break;}
        }
        this.gold = (this.gold+gold<=maxGold) ? this.gold+gold : maxGold;
    }
    public void removeLunarShard(int LunarShard){this.lunarShard = (this.lunarShard -LunarShard>=0) ? this.lunarShard -LunarShard : 0;}
    public void removeSolarShard(int SolarShard){this.solarShard = (this.solarShard -SolarShard>=0) ? this.solarShard -SolarShard : 0;}
    public void removeGold(int gold){this.gold = (this.gold-gold>=0) ? this.gold-gold : 0;}
    public void removeHonour(int honour){this.honour =(this.honour-honour>=0) ? this.honour-honour : 0;}
    public void nextStrategy() {
        switch (getStrategy().getName()) {
            case "Lunar":
                this.strategy = new SolarAI();
                break;
            case "Solar":
                this.strategy = new HammerAI();
                break;
            case "Hammer" :
                this.strategy = new LunarAI();
                break;
        }
        this.strategy.setPlayer(this);
    }
    public void addVictory(){this.nbVictory++;}
    public void addSumHonour(){this.sumHonour+=this.honour;}

    //Rolls dice and adds rewards to player's ressources
    public void faveur(){
        this.de1.giveReward(this);
        this.de2.giveReward(this);
        Face face1 = this.de1.getReward();
        Face face2 = this.de2.getReward();

        if(face1.getKind().equals("three") || face2.getKind().equals("three")){
            face1.giveReward(this);
            face2.giveReward(this);
            face1.giveReward(this);
            face2.giveReward(this);
        }
    }

    public void defaveur(){
        this.de1.rollDice();
        this.de2.rollDice();
        Face face1 = this.de1.getReward();
        Face face2 = this.de2.getReward();
        face1.removeReward(this);
        face2.removeReward(this);

        if(face1.getKind().equals("three") || face2.getKind().equals("three")){
            face1.removeReward(this);
            face2.removeReward(this);
            face1.removeReward(this);
            face2.removeReward(this);
        }
    }

    //Purchase a dice face and set it on a dice of chosing
    public void buy(Pool pool,int poolFace,int diceNumber,int diceFace){
        this.getDice(diceNumber).setFace(pool.buy(this, pool.getFace(poolFace)), diceFace);
    }

    public void reset(){
        this.de1 = new Dice(new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(0,0,1,0));
        this.de2 = new Dice(new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(1,0,0,0),new FaceCombinationAND(0,0,0,1),new FaceCombinationAND(0,1,0,0));
        this.gold=0;
        this.honour=0;
        this.lunarShard =0;
        this.solarShard =0;
        this.maxLunarShard =6;
        this.maxSolarShard =6;
        this.maxGold=12;
        this.currentIsland=-1;// à modif pour mettre une valeur "ile de départ"
        hasReplayed=false;
        this.ennemyFaces=new ArrayList<>();
        this.feats=new ArrayList<>();
    }

    public boolean doIHaveAnHammer(){
        for(Feat f:feats){
            if(f.getName()==nameFeat.Hammer) {
                Hammer hammer = (Hammer) f;
                if (hammer.getLevel() != 2) {
                    return true;
                }
            }
        }
        return false;
    }

    public nameFeat listFeat(int featNumber){
        switch(this.currentIsland){
            case -1:
                return null;
            case 0:
                switch(featNumber){
                    case 0:
                        return nameFeat.Hammer;
                    case 1:
                        return nameFeat.Chest;
                }
            case 1:
                switch(featNumber){
                    case 0:
                        return nameFeat.Ancien;
                    case 1:
                        return nameFeat.HerbesFolles;
                }
            case 2:
                switch(featNumber){
                    case 0:
                        return nameFeat.SabotArgent;
                    case 1:
                        return nameFeat.Satyres;
                }
            case 3:
                switch(featNumber){
                    case 0:
                        return nameFeat.AilesGardienne;
                    case 1:
                        return nameFeat.Minotaure;
                }
            case 4:
                switch(featNumber){
                    case 0:
                        return nameFeat.Passeur;
                    case 1:
                        return nameFeat.CasqueInvisibilite;
                }
            case 5:
                switch(featNumber){
                    case 0:
                        return nameFeat.Meduse;
                    case 1:
                        return nameFeat.MiroirAbyssal;
                }
            case 6:
                switch(featNumber){
                    case 0:
                        return nameFeat.Pince;
                    case 1:
                        return nameFeat.Hydre;
                    case 2:
                        return nameFeat.Enigme;
                }
            default:
                return null;
        }
    }

    public void shouldIChangeStrategy(Referee referee) {
        if (this.getStrategy().getName().equals("Random")) {
            return;
        }

        //switch ( referee.getRound()) {
        //    case 1 :

        //        if (referee.getPlayers().stream().filter(p -> this.getStrategy().getName().equals(p.getStrategy().getName()) && p != this).count() > 1) {
        //            this.nextStrategy();
          //          shouldIChangeStrategy(referee);
        //        }
        //}
    }



    public void lastAction(){
        int diceNumber=0;
        if(this.de1.diceNotFullWith(HONOUR)) diceNumber=0;//devrait traiter plus tard pour chercher la face ayant le moins d'honneur si jamais il est full honour sur ses deux dés
        else if(this.de2.diceNotFullWith(HONOUR)) diceNumber=1;
        action=Referee.EXPLOIT;
        if(Referee.getWorld().getIsland(6).isIn(nameFeat.Hydre) && this.lunarShard>=5 && this.solarShard>=5){
            this.currentIsland=6;
            Referee.getWorld().giveFeat(this,nameFeat.Hydre);

        }else if(Referee.getWorld().getIsland(5).isIn(nameFeat.Meduse) && this.solarShard >=5){
            this.currentIsland=5;
            Referee.getWorld().giveFeat(this,nameFeat.Meduse);

        }else if(Referee.getWorld().getIsland(4).isIn(nameFeat.Passeur) && this.lunarShard >=5){
            this.currentIsland=4;
            Referee.getWorld().giveFeat(this,nameFeat.Passeur);
        }else if(Referee.getWorld().getIsland(5).isIn(nameFeat.MiroirAbyssal) && this.solarShard >=5){
            this.currentIsland=5;
            Referee.getWorld().giveFeat(this,nameFeat.MiroirAbyssal);
        }else if(Referee.getWorld().getIsland(6).isIn(nameFeat.Enigme) && this.solarShard >=6){
            this.currentIsland=6;
            Referee.getWorld().giveFeat(this,nameFeat.Enigme);
        }else if(Referee.getWorld().getIsland(6).isIn(nameFeat.Pince) && this.lunarShard >=6){
            this.currentIsland=6;
            Referee.getWorld().giveFeat(this,nameFeat.Pince);
        }else if(Referee.getWorld().getIsland(3).isIn(nameFeat.Minotaure) && this.solarShard >=3){
            this.currentIsland=3;
            Referee.getWorld().giveFeat(this,nameFeat.Minotaure);
        }else if(Referee.getWorld().getIsland(2).isIn(nameFeat.Satyres) && this.lunarShard >=3){
            this.currentIsland=2;
           Referee.getWorld().giveFeat(this,nameFeat.Satyres);
        }else if(Referee.getWorld().getIsland(4).isIn(nameFeat.CasqueInvisibilite) && this.lunarShard >=5){
            this.currentIsland=4;
            Referee.getWorld().giveFeat(this,nameFeat.CasqueInvisibilite);
        }else if(Referee.getWorld().getIsland(3).isIn(nameFeat.AilesGardienne) && this.solarShard >=2){
            this.currentIsland=3;
            Referee.getWorld().giveFeat(this,nameFeat.AilesGardienne);
        }else if(Referee.getWorld().getIsland(1).isIn(nameFeat.HerbesFolles) && this.solarShard >=1){
            this.currentIsland=1;
            Referee.getWorld().giveFeat(this,nameFeat.HerbesFolles);
        }else if(Referee.getWorld().getIsland(0).isIn(nameFeat.Chest) && this.lunarShard >=1){
            this.currentIsland=0;
            Referee.getWorld().giveFeat(this,nameFeat.Chest);
        }else if(Referee.getWorld().getIsland(2).isIn(nameFeat.SabotArgent) && this.lunarShard >=2){
            this.currentIsland=2;
            Referee.getWorld().giveFeat(this,nameFeat.SabotArgent);
        }else if(Referee.getForge().getPool(0).kindOfPool(HONOUR) && this.gold>=12){
            action=Referee.FORGE;
            this.buy(Referee.getForge().getPool(0),Referee.getForge().getPool(0).faceKind(HONOUR),diceNumber,this.de1.faceNotOfThisKind(HONOUR));
        }else if(Referee.getForge().getPool(1).kindOfPool(HONOUR) && this.gold>=8){
            action=Referee.FORGE;
            this.buy(Referee.getForge().getPool(1),Referee.getForge().getPool(1).faceKind(HONOUR),diceNumber,this.de1.faceNotOfThisKind(HONOUR));
        }else if(Referee.getForge().getPool(4).kindOfPool(HONOUR) && this.gold>=5){
            action=Referee.FORGE;
            this.buy(Referee.getForge().getPool(4),Referee.getForge().getPool(4).faceKind(HONOUR),diceNumber,this.de1.faceNotOfThisKind(HONOUR));
        }else if(Referee.getForge().getPool(5).kindOfPool(HONOUR) && this.gold>=4){
            action=Referee.FORGE;
            this.buy(Referee.getForge().getPool(5),Referee.getForge().getPool(5).faceKind(HONOUR),diceNumber,this.de1.faceNotOfThisKind(HONOUR));
        }
    }
}
