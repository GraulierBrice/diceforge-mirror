package DiceForge;
import DiceForge.AI.RandomAI;
import DiceForge.Face.*;

import java.util.ArrayList;

public class Referee {
    private ArrayList<Player> players=new ArrayList<>();
    private Forge forge;
    private int turnPlayer,round,maxRound;

    public Referee(int nbJoueur,String typeAI){
        for(int i=1;i<=nbJoueur;i++){
            if(typeAI.equals("random")) {
                this.players.add(new RandomAI());
            }
        }
        turnPlayer=0;
        round=1;
        if(nbJoueur==3){
            maxRound=10;
        }else {
            maxRound=9;
        }
    }

    public void addForge(Forge forge){this.forge=forge; }

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
            int poolNumber= this.getPlayer(this.turnPlayer).choosePool();
            Pool pool=forge.getPool(poolNumber);
        /*for(int i=0;i<this.getNumberPlayer();i++){
                System.out.println(this.getNumberPlayer());
                System.out.println(pool.getFace(i).getReward());
            }*/
            if(pool.isEmpty()==false && this.getPlayer(this.turnPlayer).getGold()>=pool.getPrice()) {
                int poolFace = this.getPlayer(this.turnPlayer).choosePoolFace(pool);
                int dice =0;// this.getPlayer(this.turnPlayer).chooseDice();
                int diceFace = this.getPlayer(this.turnPlayer).chooseDiceFace();
                System.out.println("\u001B[32m"+"pool number :"+poolNumber);
                System.out.println("pool face: "+poolFace);
                System.out.println("dice number: "+dice);
                System.out.println("dice face: "+diceFace);
                System.out.println("pool nb de face restante: "+pool.howManyFaces()+"\u001B[0m");
                this.buy(pool, poolFace, dice, diceFace);
            }
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
        if(this.turnPlayer == this.getNumberPlayer()) {
            turnPlayer = 0;
            round++;
        }
    }

    public void printLog(){
        for (Player p : players) {
            if(this.players.indexOf(p)==this.getTurnPlayer()){
                System.out.println("\u001B[31m" +"information joueur: " + (this.players.indexOf(p) + 1)+"\u001B[0m");
            }else {
                System.out.println("information joueur: " + (this.players.indexOf(p) + 1));
            }
            System.out.println("Honour: " + p.getHonour());
            System.out.println("Gold: " + p.getGold());
            System.out.println("PdS: " + p.getPdS());
            System.out.println("PdL: " + p.getPdL());
            p.getDice(0).toString(1);
            p.getDice(1).toString(2);
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
    public void buy(Pool pool,int poolFace,int diceNumber,int diceFace){
        this.getPlayer(this.turnPlayer).buy(this,pool,poolFace,diceNumber,diceFace);
    }

}
