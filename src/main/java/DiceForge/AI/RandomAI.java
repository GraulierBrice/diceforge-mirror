package DiceForge.AI;
import DiceForge.*;

import java.util.Random;

public class RandomAI extends Player{

    public RandomAI(){
        super();
    }

    public String chooseAction() {
        Random r=new Random();
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
        Random r = new Random();
        return r.nextInt(2);

    }

    public int chooseDiceFace() {
        Random r = new Random();
        return r.nextInt(6);
    }

    public int choosePoolFace(Pool pool) {
        Random r = new Random();
        return r.nextInt(pool.howManyFaces());
    }

    public int choosePool() {
        Random r = new Random();
        return r.nextInt(10-3);//10 jeu complet mais 3 pools non codées
    }
}
