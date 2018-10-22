package DiceForge;
import DiceForge.Feat.*;

import java.util.ArrayList;

public class Referee {
    private ArrayList<Player> players=new ArrayList<>();
    private Forge forge;
    private Island island;
    private int turnPlayer,round,maxRound;

    public Referee(Player... joueurs){
        for(int i=0;i<joueurs.length;i++) {
            this.players.add(joueurs[i]);
        }
        ArrayList<Feat> island1=new ArrayList<>();
        island1.add(new Hammer());
        this.island=new Island(island1,this);
        turnPlayer=0;
        round=1;

        if(this.getNumberPlayer()==3)
            {maxRound=10;}
        else
            {maxRound=9;}

    }

    public void addForge(Forge forge){this.forge=forge;}

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
            if( !pool.isEmpty() && this.getPlayer(this.turnPlayer).getGold()>=pool.getPrice()) {
                int poolFace = this.getPlayer(this.turnPlayer).choosePoolFace(pool);
                int dice = this.getPlayer(this.turnPlayer).chooseDice();
                int diceFace = this.getPlayer(this.turnPlayer).chooseDiceFace();
                this.buy(pool, poolFace, dice, diceFace);
            }
        }
        if(action.equals("exploit")){
            System.out.println("Joueur "+ (this.turnPlayer+1) + " peut choisir un exploit à réaliser");
            //int island=0;//l'ile qu'il va choisir
            int exploit=0;//l'exploit sur l'ile qu'il va choisir
            if(this.island.getFeat(exploit).getNbExploit()>0 && this.getPlayer(this.turnPlayer).getGold()>this.island.getFeat(exploit).getPrice()){
                this.getPlayer(this.turnPlayer).removeGold(1);
                this.island.getFeat(exploit).setPlayer(this.getPlayer(this.turnPlayer));
                System.out.println("Joueur "+(this.turnPlayer+1) + " réalise l'exploit "+this.island.getFeat(exploit).getNameExploit());

            }
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
            for(int i=0;i<p.getNbFeat();i++){
                if(p.getFeat(i).getNameExploit().equals("Hammer")){
                    Hammer hammer=(Hammer)p.getFeat(i);
                    if(hammer.getLevel()<2) {
                        System.out.println("Hammer level " + (hammer.getLevel() + 1) + ": " + hammer.getGold());
                        break;
                    }
                }
            }

            p.getDice(0).toString(1);
            p.getDice(1).toString(2);
        }
    }

    public int[] honour() {
        int []winner={this.players.get(0).getHonour(),0};//winner[0]=max;winner[1]=playerid;
        for (Player i : this.players) {
            if (winner[0] < i.getHonour()) {
                winner[0] = i.getHonour();
                winner[1] = this.players.indexOf(i);
            }
        }
        return winner;
    }
    public void printWinner() {
        int []winner=this.honour();
        System.out.println("Joueur " + (winner[1] + 1) + " gagne avec " + winner[0] + " honneurs ");
    }

    public void buy(Pool pool,int poolFace,int diceNumber,int diceFace){
        this.getPlayer(this.turnPlayer).buy(this,pool,poolFace,diceNumber,diceFace);
    }

}
