package DiceForge;

import java.util.ArrayList;
import java.util.Collections;

public class Referee {
    private ArrayList<Player> players=new ArrayList<>();
    private int turnPlayer,round,maxRound;

    public Referee(int nbJoueur){
        for (int i=1; i<=nbJoueur;i++) {
            this.players.add(new Player());
        }
        turnPlayer = 0;
        round=1;
        if(nbJoueur==3){
            maxRound=10;
        }else {
            maxRound=9;
        }
    }
    public int getTurnPlayer(){
        return this.turnPlayer;
    }

    public int getNumberPlayer() {
        return this.players.size();
    }

    public Player getPlayer(int n) {
        return players.get(n);
    }

    public int getRound(){
        return this.round;
    }

    public int getMaxRound(){
        return this.maxRound;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void choixAction(String action){
        if(action == "passe"){
            System.out.println("Joueur "+ (this.turnPlayer+1) + " passe son tour");
        }
        if(action == "forge"){
            System.out.println("Joueur "+ (this.turnPlayer+1) + " peut acheter une face");
        }
        if(action == "exploit"){
            System.out.println("Joueur "+ (this.turnPlayer+1) + " peut choisir un exploit à réaliser");
        }
    }

    public void faveur() {
        for (Player player : this.players) {
            player.faveur();
        }
    }

    public void nextPlayer(){
        this.turnPlayer++;
        if(this.turnPlayer == this.getNumberPlayer()) turnPlayer = 0; round++;
    }

    public void printLog(){
        for (Player p : players) {
            System.out.println("information joueur: " + (this.players.indexOf(p) + 1));
            System.out.println("Honour: " + p.getHonour());
            System.out.println("Gold: " + p.getGold());
            System.out.println("PdS: " + p.getPdS());
            System.out.println("PdL: " + p.getPdL());
            p.getDice(1).toString(1);
            p.getDice(2).toString(2);
        }
    }

    public void honour(){
        int max=this.players.get(0).getHonour();
        int playerid = 0;
        for (Player i : this.players) {
            if (max < i.getHonour()) {
                max = i.getHonour();
                playerid = this.players.indexOf(i);
            }
        }
        System.out.println("Joueur "+ (playerid+1) + " gagne avec " + max + " honneurs ");

    }

}
