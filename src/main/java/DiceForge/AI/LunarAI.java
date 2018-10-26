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
        if(this.getPdL()>=island.lowestPriceOfFeat("PdL").getPricePdL()) {
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
        return 1;
    }

    @Override
    public int chooseDiceFace(int dice) {
        System.out.println(dice);
        if(this.chooseDice()>=0) return (this.getDice(dice).faceNotOfThisKind("PdL"));
        return -1;
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
        if(Referee.getForge().lowestPoolNotEmptyContaining("PdL").getPrice()<=this.getGold()) {
            return Referee.getForge().isNumber(Referee.getForge().lowestPoolNotEmptyContaining("PdL"));
        }
        return -1;
    }

    @Override
    public int goldChoice(int g, Hammer h) {
        h.effect(g);
        return 0;
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
                Random r = new Random();
                int featNumber=r.nextInt(2);
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
