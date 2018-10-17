package DiceForge;
import DiceForge.Face.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Referee R = new Referee(4);
        ArrayList pool2Gelements=new ArrayList<>();
        while(pool2Gelements.size()<R.getNumberPlayer()){
            pool2Gelements.add(new FaceGold(2));
        }
        Pool pool2G = new Pool(2,pool2Gelements);
        while(R.getRound()<=R.getMaxRound()) {
            System.out.println("Nous sommes au tour : " + R.getRound() + "\n");
            for (int i = 0; i < R.getNumberPlayer(); i++) {
                R.faveur();
                if (pool2G.isEmpty() == false && R.getPlayer(R.getTurnPlayer()).getGold() >= pool2G.getPrice()) {
                    R.choixAction("forge"); //a prendre en compte choix de l'IA
                    R.getPlayer(R.getTurnPlayer()).getDice(1).setFace(pool2G.buy(R.getPlayer(R.getTurnPlayer()), pool2G.getFace(0)), 3);
                }
                R.printLog();
                R.nextPlayer();
            }
        }
    	R.honour();
    }
}

