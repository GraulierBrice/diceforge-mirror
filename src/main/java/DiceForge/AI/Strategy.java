package DiceForge.AI;

import DiceForge.*;
import DiceForge.Face.Face;
import DiceForge.Feat.Hammer;
import DiceForge.Feat.HerbesFolles;

public abstract class Strategy {
    Player player;

    public Strategy() {
    }

    public void setPlayer(Player player) { this.player = player; }
    public abstract String chooseReinforcement();
    public abstract String chooseFeatReinforcement();
    public abstract String chooseAction();
    public abstract int chooseDice();
    public abstract int chooseDiceFace(int dice);
    public abstract int chooseFaceBonus(Face face);
    public abstract int choosePoolFace(Pool pool);
    public abstract int choosePool();
    public abstract int goldChoice(int g, Hammer h);
    public abstract void chooseIsland();
    public abstract int chooseFeat();
    public abstract Dice chooseBestDice();


}
