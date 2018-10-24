package DiceForge.AI;
import DiceForge.*;
import DiceForge.Feat.*;

import java.util.Random;

public class RandomAI extends Player{

    private Random r = new Random();

    public RandomAI(){
        super();
    }

    public String chooseAction() {
        int choice = r.nextInt(3);
        switch(choice){
            case 0:
                return "passe";
            case 1:
                return "forge";
            case 2:
                return "exploit";
        }
        return null;
    }

    public int chooseDice(){
        return r.nextInt(2);
    }

    public int chooseDiceFace(int dice) {
        return r.nextInt(6);
    }

    public int choosePoolFace(Pool pool) {
        return r.nextInt(pool.howManyFaces());
    }

    public int choosePool() {
        return r.nextInt(10-3);//10 jeu complet mais 3 pools non codées
    }

    public int goldChoice(int gold, Hammer m) {
        int g = r.nextInt(gold+1);
        m.effect(g);
        return gold-g;
    }

    public void chooseIsland(){
        this.currentIsland=r.nextInt(1);
    }

    public Class chooseFeat(){
        int featNumber=r.nextInt(2);//pour l'instant il n'y a pas d'ile avec plus de deux feat, pour la dernière ile, il faudra juste vérif si on est dessus et dans ce cas on fera random bound: 3
        switch(this.currentIsland){
            case 0:
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
