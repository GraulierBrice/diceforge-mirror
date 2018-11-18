package DiceForge.AI;

import DiceForge.Face.Face;
import DiceForge.Feat.*;
import DiceForge.*;


public class SolarAI extends Strategy {

    public SolarAI(){
        super();
    }


    public String chooseReinforcement() {
        return Referee.REINFORCEMENT;
    }
    public String chooseFeatReinforcement() {
        return Referee.FEAT_REINFORCEMENT;
    }



    public String chooseAction() {
        Pool pool=Referee.getForge().affordablePoolWith(Player.SolarShard,this.player.getGold());
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


    public int chooseDice() {//on remplit le 2ème dé qui a déjà des solarshard comme ça on est sûr d'en drop à chaque tour
        if(Referee.getForge().bestPoolWith(Player.SolarShard,this.player.getGold()).getPrice()<=this.player.getGold() && !this.player.doIHaveAnHammer()) {
            return 1;
        }
        return 0;
    }


    public Dice chooseBestDice() {
        if(this.player.getMaxSolarShard() -this.player.getSolarShard() < 2) return this.player.getDice(0);
        return this.player.getDice(1);
    }


    public int chooseDiceFace(int dice) {
        if(this.chooseDice()==0) return (this.player.getDice(dice).faceNotOfThisKind(Player.SolarShard));
        else if(this.chooseDice()==1) return (this.player.getDice(dice).faceNotOfThisKind(Player.GOLD));
        return 0;//test devrait être -1 pour cas erreur
    }


    public int chooseFaceBonus(Face face) {
        return 0;
    }


    public int choosePoolFace(Pool pool) {//test pour l'instant pour prendre les pools avec uniquement des solarshard pour le moment
        if(interestingKind()!="nothing"){
            return pool.isNumber(pool.bestFaceOf(interestingKind()));
        }
        return -1;
    }

    public String interestingKind(){
        Pool poolSolarShard =Referee.getForge().bestPoolWith(Player.SolarShard,this.player.getGold());
        Pool poolG=Referee.getForge().bestPoolWith(Player.GOLD,this.player.getGold());
        if(poolSolarShard!=null && poolSolarShard.getPrice()<=this.player.getGold() && !this.player.doIHaveAnHammer()) {
            return Player.SolarShard;
        }else if(poolG!=null && poolG.getPrice()<=this.player.getGold()) {
            return Player.GOLD;
        }
        return "nothing";
    }


    public int choosePool() {
        if(interestingKind()==Player.SolarShard) return Referee.getForge().isNumber(Referee.getForge().bestPoolWith(Player.SolarShard,this.player.getGold()));
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
        if((this.player.getSolarShard()>=6 && Referee.getWorld().getIsland(6).isIn(Enigme.class))||(this.player.getLunarShard()>=5 && this.player.getSolarShard()>=5 && Referee.getWorld().getIsland(6).isIn(Hydre.class))){
            this.player.setCurrentIsland(6);
        }else if(this.player.getSolarShard()>=4 && Referee.getWorld().getIsland(5).isIn(Meduse.class)){
            this.player.setCurrentIsland(5);
        }else if(this.player.getSolarShard()>=2 && Referee.getWorld().getIsland(3).isIn(AilesGardienne.class)){
            this.player.setCurrentIsland(3);
        }else if(this.player.getSolarShard()>=1 && Referee.getWorld().getIsland(1).isIn(Ancien.class) || (this.player.getSolarShard()>=1 && Referee.getWorld().getIsland(1).isIn(HerbesFolles.class))){
            this.player.setCurrentIsland(1);
        }else if(this.player.getLunarShard()>=4 && !Referee.getWorld().getIsland(4).isIn(Passeur.class)) {
            this.player.setCurrentIsland(4);
        }else if(this.player.getLunarShard()>=2 && Referee.getWorld().getIsland(2).isIn(SabotArgent.class)) {
            this.player.setCurrentIsland(2);
        }else if(this.player.getLunarShard()>=1 && Referee.getWorld().getIsland(0).isIn(Hammer.class) || (this.player.getLunarShard()>=1 && Referee.getWorld().getIsland(0).isIn(Chest.class))) {
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
                if(this.player.getLunarShard()>=5 &&island.isIn(Hydre.class))return 1;
                else if(island.isIn(Enigme.class))return 0;
                else return -1;
        }
        return -1;
    }
}
