package DiceForge.AI;

import DiceForge.Feat.*;
import DiceForge.*;

import java.util.Random;

public class LunarAI extends Player {

    public LunarAI(){
        super();
    }


    @Override
    public String chooseAction() {
        Pool pool=Referee.getForge().affordablePoolWith("PdL",this.getGold());
        Island island=Referee.getWorld().lowestIslandNotEmpty("PdL");
        if(island!=null && this.getPdL()>=island.lowestPriceOfFeat("PdL").getPricePdL() && this.doIHaveAnHammer()==false) {//pour farmer les marteaux pour l'instant
            return "exploit";
        }else if(pool!=null) {
            if (this.getGold() >= pool.getPrice()) {
                return "forge";
            }
        }
        return "passe";
    }

    @Override
    public int chooseDice() {//on remplit le 2ème dé qui a déjà des PdL comme ça on est sûr d'en drop à chaque tour
        if(Referee.getForge().affordablePoolWith("PdL",this.getGold()).getPrice()<=this.getGold() && this.doIHaveAnHammer()==false) {
            return 1;
        }
        return 0;// devrait retourner -1 test
    }

    @Override
    public int chooseDiceFace(int dice) {
        if(this.chooseDice()==1) return (this.getDice(dice).faceNotOfThisKind("PdL"));
        else if(this.chooseDice()==0) return (this.getDice(dice).faceNotOfThisKind("G"));
        return 0;//test devrait être -1 pour cas erreur
    }

    @Override
    public int choosePoolFace(Pool pool) {//test pour l'instant pour prendre les pools avec uniquement des PdL pour le moment
        if(Referee.getForge().affordablePoolWith("PdL",this.getGold()).getPrice()<=this.getGold()){
            return 0;
        }
        return 0;
    }

    @Override
    public int choosePool() {
        Pool poolPdL =Referee.getForge().affordablePoolWith("PdL",this.getGold());
        Pool poolG=Referee.getForge().affordablePoolWith("G",this.getGold());
        if(poolPdL!=null && poolPdL.getPrice()<=this.getGold() && this.doIHaveAnHammer()==false) {
            return Referee.getForge().isNumber(Referee.getForge().affordablePoolWith("PdL",this.getGold()));
        }else if(poolG!=null && poolG.getPrice()<=this.getGold()){
            return Referee.getForge().isNumber((Referee.getForge().affordablePoolWith("G",this.getGold())));
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
        if(this.getPdL()>=4 && false){//virer les && false quand les iles existeronts
            this.currentIsland=2;
        }else if(this.getPdL()>2 && false){
            this.currentIsland=1;
        }else{
            this.currentIsland=0;
        }
    }

    @Override
    public Class chooseFeat() {

        switch(this.currentIsland){
            case 0:
                int featNumber;
                if(this.doIHaveAnHammer()==false){
                    featNumber=0;
                }else featNumber=1;
                switch(featNumber){
                    case 0:
                        return Hammer.class;
                    case 1:
                        return Chest.class;
                }
            default:
                return null;
        }
    }
}
