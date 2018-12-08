package DiceForge.AI;

import DiceForge.*;
import DiceForge.Face.Face;
import DiceForge.Feat.Hammer;
import DiceForge.Feat.HerbesFolles;
import DiceForge.Feat.nameFeat;

public abstract class Strategy {
    Player player;
    String name;

    public Strategy() {
    }
    public String getName() { return this.name; }
    public void setPlayer(Player player) { this.player = player; }
    public void chooseReinforcement() {
        this.player.setAction(Referee.REINFORCEMENT);
    }
    public void chooseFeatReinforcement() {
        this.player.setAction(Referee.FEAT_REINFORCEMENT);
    }
    public abstract Pool setPool();

    public void chooseAction() {
        Pool pool=setPool();
        chooseIsland();
        System.out.println(player.getCurrentIsland());
        if(this.player.getCurrentIsland()!=-1 && this.player.getGold()<9){
            Island island=Referee.getWorld().getIsland(this.player.getCurrentIsland());
            if(island!=null && this.chooseFeat()!=-1 ) {//pour farmer les marteaux pour l'instant
                this.player.setAction(Referee.EXPLOIT);
            }
        }else if(pool!=null && choosePoolFace(pool)!=-1 && this.player.getGold() >= pool.getPrice()) {
            this.player.setAction(Referee.FORGE);
        }else this.player.setAction(Referee.PASSE);
    }

    public abstract Face[] chooseBestEnnemyFace();
    public abstract Face[] chooseWorstEnnemyFace();
    public abstract int chooseDice();
    public abstract int chooseDiceFace(int dice);
    public int chooseDiceFaceOr(Face face) {
        if(face.getReward().equals("1"+Player.GOLD+Announcer.ANSI_RESET+"/1"+Player.LunarShard+Announcer.ANSI_RESET+"/1"+Player.SolarShard+Announcer.ANSI_RESET)){
            return 1;
        }else if(face.getReward().equals("3"+Player.GOLD+Announcer.ANSI_RESET+"/2"+Player.HONOUR+Announcer.ANSI_RESET)){
            return 2;
        }else if(face.getReward().equals("2"+Player.GOLD+Announcer.ANSI_RESET+"/2"+Player.LunarShard+Announcer.ANSI_RESET+"/2"+Player.SolarShard+Announcer.ANSI_RESET)) {
            return 3;
        }
        return 0;
    }
    public abstract int chooseFaceOr(Face face);
    public abstract int choosePoolFace(Pool pool);
    public abstract int choosePool();
    public abstract boolean shouldKeepForging();

    public int goldChoice(int g, Hammer h,String strategy) {
        int maxGold = strategy.equals("Hammer") ? 8 : 5;
        if(g+this.player.getGold()>=maxGold) {
            if ((h.getGold() + g - (maxGold - this.player.getGold()) <= 15 && h.getLevel() == 1) || (h.getGold() + g - (maxGold - this.player.getGold()) <= 30 && h.getLevel() == 0)) {
                h.effect(g - (maxGold - this.player.getGold()));
                return (maxGold - this.player.getGold());
            } else if (h.getGold() + g - (maxGold - this.player.getGold()) > 15 && h.getLevel() == 1) {
                int value = h.getGold() + g - (maxGold - this.player.getGold()) - 15;
                h.effect(15 - h.getGold());
                return value;
            } else if (h.getGold() + g - (maxGold - this.player.getGold()) > 30 && h.getLevel() == 0) {
                int value = h.getGold() + g - (maxGold - this.player.getGold()) - 30;
                h.effect(15 - h.getGold());
                h.effect(15);
                return value;
            }
        }
        return g;
    }

    public abstract void chooseIsland();
    public abstract int chooseFeat();
    public abstract Dice chooseBestDice();
    public abstract void replay();


}
