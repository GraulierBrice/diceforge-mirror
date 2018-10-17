package DiceForge;

import java.util.ArrayList;
import java.util.Collections;

public class Referee {
    private ArrayList<Player> players=new ArrayList<>();
    private int turnPlayer;

    public Referee(int nbJoueur){
        for (int i=1; i<=nbJoueur;i++) {
            this.players.add(new Player());
        }
        turnPlayer = 0;
    }

    public int getNumberPlayer() {
        return this.players.size();
    }

    public Player getPlayer(int n) {
        return players.get(n);
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void choixAction(String action){
        if(action == "passe"){
            System.out.println("Joueur "+ this.turnPlayer+1 + " passe son tour");
        }
        if(action == "forge"){
            System.out.println("Joueur "+ this.turnPlayer+1 + "achête une face");
        }
        if(action == "exploit"){
            System.out.println("Joueur "+ this.turnPlayer+1 + "choisit un exploit");
        }
    }

    public void faveur() {
        for (Player player : this.players) {
            player.faveur();
        }
    }

    public void sort(){
        for(int i=0;i<this.players.size();i++) {
            for (int j = i + 1; j < this.players.size(); j++) {
                if (this.players.get(i).getHonour() < this.players.get(j).getHonour()) {
                    Collections.swap(this.players, i,j);
                }
            }
        }
    }

    public void nextPlayer(){
        this.turnPlayer++;
        if(this.turnPlayer == this.getNumberPlayer()) turnPlayer = 0;
    }

    public void printLog(){
        for (Player p : players){
            System.out.println("Honour: "+p.getHonour());
            System.out.println("Gold: "+p.getGold());
            System.out.println("PdS: "+p.getPdS());
            System.out.println("PdL: "+p.getPdL());

        }
    }

    /*public void honour(){
        if (.getHonour() > J2.getHonour()) {
            System.out.println("Le joueur 1 a gagné avec " + J1.getHonour() + " points d'honneur");
        } else if(J1.getHonour()<J2.getHonour()) {
            System.out.println("Le joueur 2 a gagné avec " + J2.getHonour() + " points d'honneur");
        }else {
            System.out.println("Egalité");
        }
    }*/

}
