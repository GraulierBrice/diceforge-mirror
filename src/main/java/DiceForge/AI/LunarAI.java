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
        Pool pool=Referee.getForge().lowestPoolNotEmptyContaining("PdL");
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
        if(Referee.getForge().lowestPoolNotEmptyContaining("PdL").getPrice()<=this.getGold() && this.doIHaveAnHammer()==false) {
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
        if(Referee.getForge().lowestPoolNotEmptyContaining("PdL").getPrice()<=this.getGold()){
            return 0;
        }
        return 0;
    }

    @Override
    public int choosePool() {
        if(Referee.getForge().lowestPoolNotEmptyContaining("PdL").getPrice()<=this.getGold() && this.doIHaveAnHammer()==false) {
            return Referee.getForge().isNumber(Referee.getForge().lowestPoolNotEmptyContaining("PdL"));
//        }else if(Referee.getForge().lowestPoolNotEmptyContaining("G").getPrice()<=this.getGold()){
            //return Referee.getForge().isNumber((Referee.getForge().lowestPoolNotEmptyContaining(("G"))));
        }
        return 0;//devrait être -1 en plein test
    }

    @Override
    public int goldChoice(int g, Hammer h) {
        if(this.getGold()>=2) {
            h.effect(g);
            return 0;
        }
        h.effect(g-this.getGold());
        return this.getGold();
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
