package DiceForge;
import DiceForge.Face.*;
import DiceForge.AI.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Referee R = new Referee(new RandomAI(),new RandomAI(),new LunarAI());
        Forge forge=new Forge(R);
        World world=new World(R);
        R.addForge(forge);
        R.addWorld(world);
        while(R.getRound()<=R.getMaxRound()) {
            System.out.println("Nous sommes au tour : " + R.getRound() + "\n");
            for (int i = 0; i < R.getNumberPlayer(); i++) {
                R.faveur();
                R.choixAction(R.getPlayer(R.getTurnPlayer()).chooseAction());
                R.printLog();
                R.nextPlayer();
            }
        }
    	R.printWinner();
    }
}

