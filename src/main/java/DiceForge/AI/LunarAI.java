package DiceForge.AI;

import DiceForge.Face.Face;
import DiceForge.Feat.*;
import DiceForge.*;

import java.util.Random;

public class LunarAI extends Player {

    public LunarAI(){
        super();
    }

    @Override
    public String chooseReinforcement() {
        return Referee.REINFORCEMENT;
    }

    @Override
    public String chooseFeatReinforcement() {
        return Referee.FEAT_REINFORCEMENT;
    }


    @Override
    public String chooseAction() {
        Pool pool=Referee.getForge().affordablePoolWith(LunarShard,this.getGold());
        chooseIsland();
        if(this.currentIsland!=-1){
            Island island=Referee.getWorld().getIsland(this.currentIsland);
            if(island!=null && this.chooseFeat()!=-1 ) {//pour farmer les marteaux pour l'instant
                return Referee.EXPLOIT;
            }
        }
        if(pool!=null && this.getGold() >= pool.getPrice()) {
            return Referee.FORGE;
        }
        return Referee.PASSE;
    }

    @Override
    public int chooseDice() {//on remplit le 2ème dé qui a déjà des PdL comme ça on est sûr d'en drop à chaque tour
        if(Referee.getForge().affordablePoolWith(LunarShard,this.getGold()).getPrice()<=this.getGold() && !this.doIHaveAnHammer()) {
            return 1;
        }
        return 0;
    }

    @Override
    public Dice chooseBestDice() {
        if(this.maxLunarShard-this.lunarShard < 2) return de1;
        return de2;
    }

    @Override
    public int chooseDiceFace(int dice) {
        if(this.chooseDice()==1) return (this.getDice(dice).faceNotOfThisKind(LunarShard));
        else if(this.chooseDice()==0) return (this.getDice(dice).faceNotOfThisKind(GOLD));
        return 0;//test devrait être -1 pour cas erreur
    }

    @Override
    public int chooseFaceBonus(Face face) {
        return 0;
    }

    @Override
    public int choosePoolFace(Pool pool) {//test pour l'instant pour prendre les pools avec uniquement des PdL pour le moment
        if(Referee.getForge().affordablePoolWith(LunarShard,this.getGold()).getPrice()<=this.getGold()){
            return 0;
        }
        return 0;
    }

    @Override
    public int choosePool() {
        Pool poolPdL =Referee.getForge().affordablePoolWith(LunarShard,this.getGold());
        Pool poolG=Referee.getForge().affordablePoolWith(GOLD,this.getGold());
        if(poolPdL!=null && poolPdL.getPrice()<=this.getGold() && !this.doIHaveAnHammer()) {
            return Referee.getForge().isNumber(Referee.getForge().affordablePoolWith(LunarShard,this.getGold()));
        }else if(poolG!=null && poolG.getPrice()<=this.getGold()){
            return Referee.getForge().isNumber((Referee.getForge().affordablePoolWith(GOLD,this.getGold())));
        }
        return 0;//devrait être -1 en plein test
    }

    @Override
    public int goldChoice(int g, Hammer h) {
        if((h.getGold()+g-(2-this.getGold())<=15 && h.getLevel()==1) || (h.getGold()+g-(2-this.getGold())<=30 && h.getLevel()==0)){
            h.effect(g - (2 - this.getGold()));
            return (2 - this.getGold());
        }else if(h.getGold()+g-(2-this.getGold())>15 && h.getLevel()==1){
            int value=h.getGold()+g-(2-this.getGold())-15;
            h.effect(g-value);
            return value;
        }else if(h.getGold()+g-(2-this.getGold())>30 && h.getLevel()==0){
            int value=h.getGold()+g-(2-this.getGold())-30;
            h.effect(g-value);
            return value;
        }
        return g;
    }

    @Override
    public void chooseIsland() {
        if((this.lunarShard>=6 && !Referee.getWorld().getIsland(6).isIn(Pince.class))||(this.lunarShard>=5 && this.solarShard>=5 && !Referee.getWorld().getIsland(6).isIn(Hydre.class))){
            this.currentIsland=6;
        }else if(this.lunarShard>=4 && !Referee.getWorld().isEmpty(4)){//virer les && false quand les iles existeronts
            this.currentIsland=4;//3,lune
        }else if(this.lunarShard>2 && !Referee.getWorld().isEmpty(2)){
            this.currentIsland=2;//2,lune
        }else if(this.lunarShard>1 && !Referee.getWorld().isEmpty(0)) {
            this.currentIsland = 0;//1,lune
        }else if(this.solarShard>=2 && !Referee.getWorld().getIsland(3).isIn(AilesGardienne.class)){
            this.currentIsland=3;
        }else if(this.solarShard>=1 && !Referee.getWorld().getIsland(1).isIn(HerbesFolles.class)){
            this.currentIsland=1;//1,soleil
        }else{
            this.currentIsland=-1;
        }
    }


    @Override
    public int chooseFeat(){
        Island island=Referee.getWorld().getIsland(this.currentIsland);
        switch(this.currentIsland){
            case 0:
                if(island.isIn(Hammer.class) && !this.doIHaveAnHammer())return 0;
                else if(island.isIn(Chest.class))return 1;
                else return -1;
            case 1:
                if(island.isIn(HerbesFolles.class)) return 1;
                else return -1;
            case 2:
                if(island.isIn(SabotArgent.class))return 0;
                else if(island.isIn(Satyres.class))return 1;
                else return -1;
            case 3:
                if(island.isIn(AilesGardienne.class))return 0;
                else return -1;
            case 4:
                if(island.isIn(Passeur.class))return 0;
                else if(island.isIn(CasqueInvisibilite.class))return 1;
                else return -1;
            case 6:
                if(this.solarShard>=5 &&island.isIn(Hydre.class))return 1;
                else if(island.isIn(Pince.class))return 0;
                else return -1;
        }
        return -1;
    }
}
