package DiceForge;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Referee R = new Referee(4);
        for(int i =0;i<4;i++){
        	R.faveur();
        	R.choixAction("forge"); //a prendre en compte choix de l'IA
        	R.printLog();
        	R.nextPlayer();
    	}
    	R.honour();
    }
}

