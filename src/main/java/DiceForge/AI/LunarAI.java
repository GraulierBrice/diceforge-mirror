package DiceForge.AI;

import DiceForge.Face.Face;
import DiceForge.Feat.*;
import DiceForge.*;
import java.util.Random;

public class LunarAI extends Strategy {

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
        Pool pool=Referee.getForge().affordablePoolWith(Player.LunarShard,this.player.getGold());
        chooseIsland();
        if(this.player.getCurrentIsland()!=-1){
            Island island=Referee.getWorld().getIsland(this.player.getCurrentIsland());
            if(island!=null && this.chooseFeat()!=-1 ) {//pour farmer les marteaux pour l'instant
                return Referee.EXPLOIT;
            }
        }
        if(pool!=null && this.player.getGold() >= pool.getPrice()) {
            return Referee.FORGE;
        }
        return Referee.PASSE;
    }

    @Override
    public int chooseDice() {//on remplit le 2ème dé qui a déjà des lunarShard comme ça on est sûr d'en drop à chaque tour
        if(Referee.getForge().bestPoolWith(Player.LunarShard,this.player.getGold()).getPrice()<=this.player.getGold() && !this.player.doIHaveAnHammer()) {
            return 1;
        }
        return 0;
    }

    @Override
    public Dice chooseBestDice() {
        if(this.player.getMaxLunarShard() -this.player.getLunarShard() < 2) return this.player.getDice(0);
        return this.player.getDice(1);
    }

    @Override
    public int chooseDiceFace(int dice) {
        if(this.chooseDice()==1) return (this.player.getDice(dice).faceNotOfThisKind(Player.LunarShard));
        else if(this.chooseDice()==0) return (this.player.getDice(dice).faceNotOfThisKind(Player.GOLD));
        return 0;//test devrait être -1 pour cas erreur
    }

    @Override
    public int chooseFaceBonus(Face face) {
        return 0;
    }

    @Override
    public int choosePoolFace(Pool pool) {//test pour l'instant pour prendre les pools avec uniquement des lunarShard pour le moment
        if(interestingKind()!="nothing"){
            return pool.isNumber(pool.bestFaceOf(interestingKind()));
        }
        return -1;
    }

    public String interestingKind(){
        Pool poolPdL =Referee.getForge().bestPoolWith(Player.LunarShard,this.player.getGold());
        Pool poolG=Referee.getForge().bestPoolWith(Player.GOLD,this.player.getGold());
        if(poolPdL!=null && poolPdL.getPrice()<=this.player.getGold() && !this.player.doIHaveAnHammer()) {
            return Player.LunarShard;
        }else if(poolG!=null && poolG.getPrice()<=this.player.getGold()) {
            return Player.GOLD;
        }
        return "nothing";
        }

    @Override
    public int choosePool() {
            if(interestingKind()==Player.LunarShard) return Referee.getForge().isNumber(Referee.getForge().bestPoolWith(Player.LunarShard,this.player.getGold()));
            else if(interestingKind()==Player.GOLD) return Referee.getForge().isNumber((Referee.getForge().bestPoolWith(Player.GOLD,this.player.getGold())));
            else return -1;//devrait être -1 en plein test
    }

    @Override
    public int goldChoice(int g, Hammer h) {
        if(g+this.player.getGold()>=5) {
            if ((h.getGold() + g - (5 - this.player.getGold()) <= 15 && h.getLevel() == 1) || (h.getGold() + g - (5 - this.player.getGold()) <= 30 && h.getLevel() == 0)) {
                h.effect(g - (5 - this.player.getGold()));
                return (5 - this.player.getGold());
            } else if (h.getGold() + g - (5 - this.player.getGold()) > 15 && h.getLevel() == 1) {
                int value = h.getGold() + g - (5 - this.player.getGold()) - 15;
                h.effect(15 - h.getGold());
                return value;
            } else if (h.getGold() + g - (5 - this.player.getGold()) > 30 && h.getLevel() == 0) {
                int value = h.getGold() + g - (5 - this.player.getGold()) - 30;
                h.effect(15 - h.getGold());
                h.effect(15);
                return value;
            }
        }
        return g;
    }

    @Override
    public void chooseIsland() {
        if((this.player.getLunarShard()>=6 && Referee.getWorld().getIsland(6).isIn(Pince.class))||(this.player.getLunarShard()>=5 && this.player.getSolarShard()>=5 && Referee.getWorld().getIsland(6).isIn(Hydre.class))){
            this.player.setCurrentIsland(6);
        }else if(this.player.getLunarShard()>=4 && !Referee.getWorld().getIsland(4).isIn(Passeur.class)) {
            this.player.setCurrentIsland(4);
        }else if(this.player.getLunarShard()>=2 && Referee.getWorld().getIsland(2).isIn(SabotArgent.class)) {
            this.player.setCurrentIsland(2);
        }else if(this.player.getLunarShard()>=1 && Referee.getWorld().getIsland(0).isIn(Hammer.class) || (this.player.getLunarShard()>=1 && Referee.getWorld().getIsland(0).isIn(Chest.class))) {
            this.player.setCurrentIsland(0);
        }else if(this.player.getSolarShard()>=4 && Referee.getWorld().getIsland(5).isIn(Meduse.class)){
            this.player.setCurrentIsland(5);
        }else if(this.player.getSolarShard()>=2 && Referee.getWorld().getIsland(3).isIn(AilesGardienne.class)){
            this.player.setCurrentIsland(3);
        }else if(this.player.getSolarShard()>=1 && Referee.getWorld().getIsland(0).isIn(Ancien.class) || (this.player.getLunarShard()>=1 && Referee.getWorld().getIsland(0).isIn(HerbesFolles.class))) {
            this.player.setCurrentIsland(0);
        }else{
            this.player.setCurrentIsland(-1);
        }
    }

    @Override
    public int chooseFeat(){
        Island island=Referee.getWorld().getIsland(this.player.getCurrentIsland());
        switch(this.player.getCurrentIsland()){
            case 0:
                if(island.isIn(Hammer.class) && !this.player.doIHaveAnHammer())return 0;
                else if(island.isIn(Chest.class))return 1;
                else return -1;
            case 1:
                if(island.isIn(Ancien.class))return 0;
                else if(island.isIn(HerbesFolles.class)) return 1;
                else return -1;
            case 2:
                if(island.isIn(SabotArgent.class))return 0;
                else return -1;
            case 3:
                if(island.isIn(AilesGardienne.class))return 0;
                else return -1;
            case 4:
                if(island.isIn(Passeur.class))return 0;
                else return -1;
            case 5:
                if(island.isIn(Meduse.class))return 0;
                else return -1;
            case 6:
                if(this.player.getSolarShard()>=5 &&island.isIn(Hydre.class))return 1;
                else if(island.isIn(Enigme.class))return 0;
                else return -1;
        }
        return -1;
    }
}
