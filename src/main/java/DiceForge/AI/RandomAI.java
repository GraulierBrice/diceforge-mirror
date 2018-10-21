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

    public int chooseDiceFace() {
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
}
