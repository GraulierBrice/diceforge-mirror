package DiceForge;
import DiceForge.Feat.*;

import java.util.ArrayList;

public class Referee {
    private ArrayList<Player> players=new ArrayList<>();
    private Forge forge;
    private Island island;
    private int turnPlayer,round,maxRound;

    public Referee(Player... players){
        ArrayList<Feat> island1 = new ArrayList<>();
        for(int i=0;i<players.length;i++) {
            this.players.add(players[i]);
            island1.add(new Hammer());
            island1.add(new Chest());
        }
        this.island=new Island(island1);
        this.turnPlayer=0;
        this.round=1;
        this.maxRound = (this.getNumberPlayer()==3) ? 10 : 9;

    }

    public void addForge(Forge forge){this.forge=forge;}

    /* Accessor */
    public int getTurnPlayer(){return this.turnPlayer;}
    public int getNumberPlayer(){return this.players.size();}
    public Player getPlayer(int n){return players.get(n);}
    public int getRound(){return this.round;}
    public int getMaxRound(){return this.maxRound;}
   
    /* Mutator */
    public void setPlayers(ArrayList<Player> players){this.players = players;}
    public void nextPlayer(){
        this.turnPlayer++;
        if(this.turnPlayer == this.getNumberPlayer()) {
            turnPlayer = 0;
            round++;
        }
    }

    public void choixAction(String action){

        Player turnP = this.getPlayer(this.turnPlayer);

        switch (action) {
            case "passe" :
                System.out.println("\033[34mJoueur "+(this.turnPlayer+1)+" passe son tour\u001B[0m");
                break;   
            case "forge" :
                System.out.println("\033[34mJoueur "+(this.turnPlayer+1)+" peut acheter une face\u001B[0m");
                Pool pool=forge.getPool(turnP.choosePool());
                if(!pool.isEmpty() && turnP.getGold()>=pool.getPrice()) {
                    int poolFace = turnP.choosePoolFace(pool);
                    int dice = turnP.chooseDice();
                    int diceFace = turnP.chooseDiceFace(turnP.chooseDice());
                    this.buy(pool, poolFace, dice, diceFace);
                }
                break;   
            case "exploit" :
                System.out.println("\033[34mJoueur "+(this.turnPlayer+1)+" peut choisir un exploit à réaliser\u001B[0m");
                turnP.chooseIsland();
                Class exploit = turnP.chooseFeat();//l'exploit sur l'ile qu'il va choisir
                if(this.island.isIn(exploit) && (turnP.getPdL()>=this.island.getFeat(exploit).getPricePdL() || turnP.getPdS()>=this.island.getFeat(exploit).getPricePdS())){
                    turnP.removePdL(1);
                    this.island.getFeat(exploit).setPlayer(turnP);
                    System.out.println("\033[34mJoueur "+(this.turnPlayer+1)+" réalise l'exploit "+exploit.getName().split("\\.")[2]+"\u001B[0m");
                    this.island.removeFeat(exploit);
                }
                break;      
        }
    }

    public void faveur() {
        players.forEach(Player::faveur);
    }


    /*Prints out information as the game flows:
        -Each round gives round number
        -Show each players' ressources
        -Indicates turn player in red
        -Indicates player actions in blue
        -Indicates dice faces rolled in yellow    
    */
    public void printLog(){
        for (Player p : players) {
            System.out.println(this.players.indexOf(p) == this.getTurnPlayer() ? "\u001B[31m" + "information joueur: " + (this.players.indexOf(p) + 1) + "\u001B[0m" : "information joueur: " + (this.players.indexOf(p) + 1));
            System.out.println("Honour: " + p.getHonour()+"\nGold: " + p.getGold() + "/" + p.getMaxGold()+"\nPdS: " + p.getPdS() + "/" + p.getMaxPdS()+"\nPdL: " + p.getPdL() + "/" + p.getMaxPdL());
            for(int i=0;i<p.getNbFeat();i++){
                if(p.getFeat(i) instanceof Hammer){
                    Hammer hammer=(Hammer)p.getFeat(i);
                    if(hammer.getLevel()<2) {
                        System.out.println("Hammer level "+(hammer.getLevel()+1)+": "+hammer.getGold());
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
