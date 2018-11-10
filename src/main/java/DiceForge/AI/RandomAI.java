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
                return Referee.EXPLOIT;
        }
        return null;
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
        if(face.getKind().contains(PDL))possibilities++;
        if(face.getKind().contains(PDS))possibilities++;
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
        this.currentIsland=r.nextInt(6);


    }

    public int chooseFeat(){
        if(this.currentIsland==0 || this.currentIsland==1) return r.nextInt(2);//pour l'instant il n'y a pas d'ile avec plus de deux feat, pour la dernière ile, il faudra juste vérif si on est dessus et dans ce cas on fera random bound: 3
        else return 0;
    }

}
