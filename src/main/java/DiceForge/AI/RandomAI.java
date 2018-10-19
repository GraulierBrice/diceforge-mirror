package DiceForge.AI;
import DiceForge.*;
import DiceForge.Face.*;
import java.util.ArrayList;

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

    public int choosePoolFace(Referee R) {
        Random r = new Random();
        return r.nextInt(R.getNumberPlayer());
    }

    public Pool choosePool() {
        int randomValue=0;
        if(randomValue==0){
            ArrayList<Face> pool2G=new ArrayList<>();
            for(int i=0;i<4;i++) {//pour 4J pour l'instant pour test
                pool2G.add(new FaceGold(2));
            }
            Pool pool= new Pool(2,pool2G);
            return pool;
        }
        return null;
    }
}
