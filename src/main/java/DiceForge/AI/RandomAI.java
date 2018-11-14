package DiceForge.AI;
import DiceForge.*;
import DiceForge.Face.Face;
import DiceForge.Feat.*;

import java.util.Random;

public class RandomAI extends Player{

    private Random r = new Random();

    public RandomAI(){
        super();
    }

    public String chooseReinforcement() {
        int choice = r.nextInt(2);
        switch(choice){
            case 0:
                return null;
            case 1:
                return Referee.REINFORCEMENT;
        }
        return null;
    }
    public String chooseFeatReinforcement() {
        int choice = r.nextInt(2);
        switch(choice){
            case 0:
                return null;
            case 1:
                return Referee.FEAT_REINFORCEMENT;
        }
        return null;
    }


    public String chooseAction() {
        int choice = r.nextInt(3);
        switch(choice){
            case 0:
                return Referee.PASSE;
            case 1:
                return Referee.FORGE;
            case 2:
                this.chooseIsland();
                if(this.currentIsland!=-1 && this.chooseFeat()!=-1)
                return Referee.EXPLOIT;
        }
        return Referee.PASSE;
    }


    public int chooseDice(){
        return r.nextInt(2);
    }

    public Dice chooseBestDice(){
        return this.getDice(r.nextInt(2));
    }

    public int chooseDiceFace(int dice) {
        return r.nextInt(6);
    }

    public int chooseFaceBonus(Face face){
        int possibilities=0;
        if(face.getKind().contains(GOLD))possibilities++;
        if(face.getKind().contains(LunarShard))possibilities++;
        if(face.getKind().contains(SolarShard))possibilities++;
        if(face.getKind().contains(HONOUR))possibilities++;
        return r.nextInt(possibilities);
    }

    public int choosePoolFace(Pool pool) {
        return r.nextInt(pool.howManyFaces());
    }

    public int choosePool() {
        return r.nextInt(10);//10 jeu complet mais 3 pools non codées
    }

    public int goldChoice(int gold, Hammer m) {
        int g = r.nextInt(gold+1);
        m.effect(g);
        return gold-g;
    }

    public void chooseIsland(){
        int value=r.nextInt(7);
        if(Referee.getWorld().getIsland(value).isEmpty() || this.lunarShard<Referee.getWorld().getIsland(value).lowestPriceOfFeat(Player.LunarShard).getPriceLunarShard() || this.solarShard<Referee.getWorld().getIsland(value).lowestPriceOfFeat(Player.SolarShard).getPriceSolarShard()){
            value=-1;
        }
        this.currentIsland=value;


    }

    public int chooseFeat(){
        int value;
        if(this.currentIsland<6) value= r.nextInt(2);//pour l'instant il n'y a pas d'ile avec plus de deux feat, pour la dernière ile, il faudra juste vérif si on est dessus et dans ce cas on fera random bound: 3
        else  value=r.nextInt(3);
        if(this.currentIsland==-1 || !Referee.getWorld().getIsland(this.currentIsland).isIn(this.listFeat(value))){
            return -1;
        }else{
            return value;
        }
    }

}
