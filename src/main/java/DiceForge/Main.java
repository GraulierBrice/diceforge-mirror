package DiceForge;
import DiceForge.Face.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Referee R = new Referee(4,"random");
        Forge forge=new Forge(R);
        R.addForge(forge);

        while(R.getRound()<=R.getMaxRound()) {
            System.out.println("Nous sommes au tour : " + R.getRound() + "\n");
            for (int i = 0; i < R.getNumberPlayer(); i++) {
                R.faveur();
                R.choixAction(R.getPlayer(R.getTurnPlayer()).chooseAction());
                R.printLog();
                R.nextPlayer();
            }
        }
        System.out.println(R.getNumberPlayer());
    	R.honour();
    }
}

