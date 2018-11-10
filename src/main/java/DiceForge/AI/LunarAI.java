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
        Pool pool=Referee.getForge().affordablePoolWith(PDL,this.getGold());
        Island island=Referee.getWorld().lowestIslandNotEmpty(PDL);
        Island islandPdS=Referee.getWorld().lowestIslandNotEmpty(PDS);
        if(island!=null && (this.getPdL()>=island.lowestPriceOfFeat(PDL).getPricePdL() || (islandPdS!=null && this.getPdS()>=islandPdS.lowestPriceOfFeat(PDS).getPricePdS() && island.isIn(HerbesFolles.class) ))) {//pour farmer les marteaux pour l'instant
            return Referee.EXPLOIT;
        }else if(pool!=null) {
            if (this.getGold() >= pool.getPrice()) {
                return Referee.FORGE;
            }
        }
        return Referee.PASSE;
    }

    @Override
    public int chooseDice() {//on remplit le 2ème dé qui a déjà des PdL comme ça on est sûr d'en drop à chaque tour
        if(Referee.getForge().affordablePoolWith(PDL,this.getGold()).getPrice()<=this.getGold() && !this.doIHaveAnHammer()) {
            return 1;
        }
        return 0;
    }

    @Override
    public Dice chooseBestDice() {
        if(this.maxPdL-this.PdL < 2) return de1;
        return de2;
    }

    @Override
    public int chooseDiceFace(int dice) {
        if(this.chooseDice()==1) return (this.getDice(dice).faceNotOfThisKind(PDL));
        else if(this.chooseDice()==0) return (this.getDice(dice).faceNotOfThisKind(GOLD));
        return 0;//test devrait être -1 pour cas erreur
    }

    @Override
    public int chooseFaceBonus(Face face) {
        return 0;
    }

    @Override
    public int choosePoolFace(Pool pool) {//test pour l'instant pour prendre les pools avec uniquement des PdL pour le moment
        if(Referee.getForge().affordablePoolWith(PDL,this.getGold()).getPrice()<=this.getGold()){
            return 0;
        }
        return 0;
    }

    @Override
    public int choosePool() {
        Pool poolPdL =Referee.getForge().affordablePoolWith(PDL,this.getGold());
        Pool poolG=Referee.getForge().affordablePoolWith(GOLD,this.getGold());
        if(poolPdL!=null && poolPdL.getPrice()<=this.getGold() && !this.doIHaveAnHammer()) {
            return Referee.getForge().isNumber(Referee.getForge().affordablePoolWith(PDL,this.getGold()));
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
        if(this.getPdL()>=4 && !Referee.getWorld().isEmpty(4)){//virer les && false quand les iles existeronts
            this.currentIsland=4;//3,lune
        }else if(this.getPdL()>2 && !Referee.getWorld().isEmpty(2)){
            this.currentIsland=2;//2,lune
        }else if(this.getPdL()>1 && !Referee.getWorld().isEmpty(0)){
            this.currentIsland=0;//1,lune
        }else if(this.getPdS()>=1 && !Referee.getWorld().isEmpty(1)){
            this.currentIsland=1;//1,soleil
        }
    }

    @Override
    public int chooseFeat() {
        if(this.currentIsland==0){
                if(!this.doIHaveAnHammer()){
                    return 0;
                }else return 1;
        }else if(this.currentIsland==1){
            return 1;
        }if(this.currentIsland==4 || this.currentIsland==2)return 0;
        return 1;//pour le moment dans tout les cas, 2iles donc on veut qu'il prenne les herbesfolles
    }
}
