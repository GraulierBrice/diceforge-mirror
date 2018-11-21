package DiceForge.AI;
import DiceForge.*;
import DiceForge.Face.Face;
import DiceForge.Feat.*;

import java.util.Random;

public class RandomAI extends Strategy{


    private Random r = new Random();

    public RandomAI(){
        super();
        super.name = "Random";
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
                if(this.player.getCurrentIsland()!=-1 && this.chooseFeat()!=-1)
                return Referee.EXPLOIT;
        }
        return Referee.PASSE;
    }


    public int chooseDice(){
        return r.nextInt(2);
    }

    public Dice chooseBestDice(){
        return this.player.getDice(r.nextInt(2));
    }

    public int chooseDiceFace(int dice) {
        return r.nextInt(6);
    }

    public int chooseFaceBonus(Face face){
        int possibilities=0;
        if(face.getKind().contains(Player.GOLD))possibilities++;
        if(face.getKind().contains(Player.LunarShard))possibilities++;
        if(face.getKind().contains(Player.SolarShard))possibilities++;
        if(face.getKind().contains(Player.HONOUR))possibilities++;
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
        if(Referee.getWorld().getIsland(value).isEmpty() || this.player.getLunarShard()<Referee.getWorld().getIsland(value).lowestPriceOfFeat(Player.LunarShard).getPriceLunarShard() || this.player.getSolarShard()<Referee.getWorld().getIsland(value).lowestPriceOfFeat(Player.SolarShard).getPriceSolarShard()){
            value=-1;
        }
        this.player.setCurrentIsland(value);

    }

    public int chooseFeat(){
        int value;
        if(this.player.getCurrentIsland()<6) value= r.nextInt(2);//pour l'instant il n'y a pas d'ile avec plus de deux feat, pour la dernière ile, il faudra juste vérif si on est dessus et dans ce cas on fera random bound: 3
        else  value=r.nextInt(3);
        if(this.player.getCurrentIsland()==-1 || !Referee.getWorld().getIsland(this.player.getCurrentIsland()).isIn(this.player.listFeat(value))){
            return -1;
        }else{
            return value;
        }
    }

}
