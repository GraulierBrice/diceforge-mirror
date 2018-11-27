package DiceForge.AI;

import DiceForge.*;
import DiceForge.Face.Face;
import DiceForge.Feat.Hammer;
import DiceForge.Feat.HerbesFolles;

public abstract class Strategy {
    Player player;
    String name;

    public Strategy() {
    }
    public String getName() { return this.name; }
    public void setPlayer(Player player) { this.player = player; }
    public abstract void chooseReinforcement();
    public abstract void chooseFeatReinforcement();
    public abstract Pool setPool();

    public void chooseAction() {
        Pool pool=setPool();
        chooseIsland();
        if(this.player.getCurrentIsland()!=-1){
            Island island=Referee.getWorld().getIsland(this.player.getCurrentIsland());
            if(island!=null && this.chooseFeat()!=-1 ) {//pour farmer les marteaux pour l'instant
                this.player.setAction(Referee.EXPLOIT);
            }
        }else if(pool!=null && this.player.getGold() >= pool.getPrice()) {
            this.player.setAction(Referee.FORGE);
        }else this.player.setAction(Referee.PASSE);
    }

    public abstract Face[] chooseBestEnnemyFace();
    public abstract int chooseDice();
    public abstract int chooseDiceFace(int dice);
    public abstract int chooseFaceOr(Face face);
    public abstract int choosePoolFace(Pool pool);
    public abstract int choosePool();

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

    public abstract void chooseIsland();
    public abstract int chooseFeat();
    public abstract Dice chooseBestDice();
    public abstract void replay();


}
