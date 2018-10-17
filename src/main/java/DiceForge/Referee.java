package DiceForge;

import java.util.ArrayList;
import java.util.Collections;

public class Referee {
    private ArrayList<Player> players=new ArrayList<>();

    public Referee(int nbJoueur){
        for (int i=1; i<=nbJoueur;i++) {
            this.players.add(new Player());
        }
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
        System.out.println("choisissez votre action: passe, forge, exploit");
        if(action == "passe"){
            System.out.println("tu passes ton tour");
        }
        if(action == "forge"){
            System.out.println("choisis la face que tu veux acheter");
        }
        if(action == "exploit"){
            System.out.println("choisis la carte que tu veux acheter");
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
