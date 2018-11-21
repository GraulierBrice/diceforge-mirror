package DiceForge;

import DiceForge.AI.LunarAI;
import DiceForge.AI.RandomAI;
import DiceForge.AI.SolarAI;
import DiceForge.AI.Strategy;
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
    public String action;

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

    /* Mutator */
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
            if(f instanceof Hammer && ((Hammer)f).getLevel() < 2){gold = this.strategy.goldChoice(gold, (Hammer)f); break;}
        }
        this.gold = (this.gold+gold<=maxGold) ? this.gold+gold : maxGold;
    }
    public void removeLunarShard(int LunarShard){this.lunarShard = (this.lunarShard -LunarShard>=0) ? this.lunarShard -LunarShard : 0;}
    public void removeSolarShard(int SolarShard){this.solarShard = (this.solarShard -SolarShard>=0) ? this.solarShard -SolarShard : 0;}
    public void removeGold(int gold){this.gold = (this.gold-gold>=0) ? this.gold-gold : 0;}
    public void nextStrategy() {
        switch (getStrategy().getName()) {
            case "Lunar":
                this.strategy = new SolarAI();
                break;
            case "Solar":
                this.strategy = new LunarAI();
                break;
           /* case "Random" :
                this.strategy = new LunarAI();
                break; */
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
        this.currentIsland=0;// à modif pour mettre une valeur "ile de départ"
        this.ennemyFaces=new ArrayList<>();
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
            case -1:
                return null;
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
                        return Satyres.class;
                }
            case 3:
                switch(featNumber){
                    case 0:
                        return AilesGardienne.class;
                    case 1:
                        return Minotaure.class;
                }
            case 4:
                switch(featNumber){
                    case 0:
                        return Passeur.class;
                    case 1:
                        return CasqueInvisibilite.class;
                }
            case 5:
                switch(featNumber){
                    case 0:
                        return Meduse.class;
                    case 1:
                        return MiroirAbyssal.class;
                }
            case 6:
                switch(featNumber){
                    case 0:
                        return Pince.class;
                    case 1:
                        return Hydre.class;
                    case 2:
                        return Enigme.class;
                }
            default:
                return null;
        }
    }

    public void shouldIChangeStrategy(Referee referee) {
        if (this.getStrategy().getName().equals("Random")) {
            return;
        }

        switch ( referee.getRound()) {
            case 1 :

                if (referee.getPlayers().stream().filter(p -> this.getStrategy().getName().equals(p.getStrategy().getName()) && p != this).count() > 1) {
                    this.nextStrategy();
                    shouldIChangeStrategy(referee);
                }
        }
    }

    public void lastAction(){
        int diceNumber=0;
        if(this.de1.diceNotFullWith(HONOUR)) diceNumber=0;//devrait traiter plus tard pour chercher la face ayant le moins d'honneur si jamais il est full honour sur ses deux dés
        else if(this.de2.diceNotFullWith(HONOUR)) diceNumber=1;
        action=Referee.EXPLOIT;
        if(Referee.getWorld().getIsland(6).isIn(Hydre.class) && this.lunarShard>=5 && this.solarShard>=5){
            this.currentIsland=6;
            Referee.getWorld().giveFeat(this,Hydre.class);

        }else if(Referee.getWorld().getIsland(5).isIn(Meduse.class) && this.solarShard >=5){
            this.currentIsland=5;
            Referee.getWorld().giveFeat(this,Meduse.class);

        }else if(Referee.getWorld().getIsland(4).isIn(Passeur.class) && this.lunarShard >=5){
            this.currentIsland=4;
            Referee.getWorld().giveFeat(this,Passeur.class);

        }else if(Referee.getWorld().getIsland(5).isIn(MiroirAbyssal.class) && this.solarShard >=5){
            this.currentIsland=5;
            Referee.getWorld().giveFeat(this,MiroirAbyssal.class);
        }else if(Referee.getWorld().getIsland(6).isIn(Enigme.class) && this.solarShard >=6){
            this.currentIsland=6;
            Referee.getWorld().giveFeat(this,Enigme.class);
        }else if(Referee.getWorld().getIsland(6).isIn(Pince.class) && this.lunarShard >=6){
            this.currentIsland=6;
            Referee.getWorld().giveFeat(this,Pince.class);
        }else if(Referee.getWorld().getIsland(3).isIn(Minotaure.class) && this.solarShard >=3){
            this.currentIsland=3;
            Referee.getWorld().giveFeat(this,Minotaure.class);
        }else if(Referee.getWorld().getIsland(2).isIn(Satyres.class) && this.lunarShard >=3){
            this.currentIsland=2;
           Referee.getWorld().giveFeat(this,Satyres.class);
        }else if(Referee.getWorld().getIsland(4).isIn(CasqueInvisibilite.class) && this.lunarShard >=5){
            this.currentIsland=4;
            Referee.getWorld().giveFeat(this,CasqueInvisibilite.class);
        }else if(Referee.getWorld().getIsland(3).isIn(AilesGardienne.class) && this.solarShard >=2){
            this.currentIsland=3;
            Referee.getWorld().giveFeat(this,AilesGardienne.class);
        }else if(Referee.getWorld().getIsland(1).isIn(HerbesFolles.class) && this.solarShard >=1){
            this.currentIsland=1;
            Referee.getWorld().giveFeat(this,HerbesFolles.class);
        }else if(Referee.getWorld().getIsland(0).isIn(Chest.class) && this.lunarShard >=1){
            this.currentIsland=0;
            Referee.getWorld().giveFeat(this,Chest.class);
        }else if(Referee.getWorld().getIsland(2).isIn(SabotArgent.class) && this.lunarShard >=2){
            this.currentIsland=2;
            Referee.getWorld().giveFeat(this,SabotArgent.class);
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
