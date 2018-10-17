package DiceForge;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Referee R = new Referee(4);
        R.faveur();
        R.choixAction("forge"); //a prendre en compte choix de l'IA
        R.honour();

    }
}

