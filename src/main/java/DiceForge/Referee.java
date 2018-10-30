package DiceForge;
import DiceForge.Feat.*;

import java.util.ArrayList;

public class Referee {
    private ArrayList<Player> players = new ArrayList<>();
    private static Forge forge;
    private static World world;
    private int turnPlayer, round, maxRound;
    public ArrayList<Integer> nbVictoire = new ArrayList<>();

    public Referee(Player... players) {
        for (int i = 0; i < players.length; i++) {
            this.players.add(players[i]);
            nbVictoire.add(0);
        }
        this.turnPlayer = 0;
        this.round = 1;
        this.maxRound = (this.getNumberPlayer() == 3) ? 10 : 9;


    }

    public void addForge(Forge forge) {
        this.forge = forge;
    }

    public void addWorld(World world) {
        this.world = world;
    }

    /* Accessor */
    public int getTurnPlayer() {
        return this.turnPlayer;
    }

    public int getNumberPlayer() {
        return this.players.size();
    }

    public Player getPlayer(int n) {
        return players.get(n);
    }

    public int getRound() {
        return this.round;
    }

    public int getMaxRound() {
        return this.maxRound;
    }

    public static Forge getForge() {
        return forge;
    }

    public static World getWorld() {
        return world;
    }

    /* Mutator */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void nextPlayer() {
        this.turnPlayer++;
        if (this.turnPlayer == this.getNumberPlayer()) {
            turnPlayer = 0;
            round++;
        }
    }

    public void choixAction(String action, int number) {//number = nombre de games

        Player turnP = this.getPlayer(this.turnPlayer);

        switch (action) {
            case "passe":
                if (number == 1)
                    System.out.println("\033[34mJoueur " + (this.turnPlayer + 1) + " passe son tour\u001B[0m");
                break;
            case "forge":
                if (number == 1)
                    System.out.println("\033[34mJoueur " + (this.turnPlayer + 1) + " peut acheter une face\u001B[0m");
                Pool pool = forge.getPool(turnP.choosePool());
                if (!pool.isEmpty() && turnP.getGold() >= pool.getPrice()) {
                    int poolFace = turnP.choosePoolFace(pool);
                    int dice = turnP.chooseDice();
                    int diceFace = turnP.chooseDiceFace(turnP.chooseDice());
                    this.buy(pool, poolFace, dice, diceFace);
                }
                break;
            case "exploit":
                if (number == 1)
                    System.out.println("\033[34mJoueur " + (this.turnPlayer + 1) + " peut choisir un exploit à réaliser\u001B[0m");
                turnP.chooseIsland();
                Island island = this.world.getIsland(turnP.getCurrentIsland());
                Class exploit = turnP.chooseFeat();//l'exploit sur l'ile qu'il va choisir
                if (island.isIn(exploit) && (turnP.getPdL() >= island.getFeat(exploit).getPricePdL() || turnP.getPdS() >= island.getFeat(exploit).getPricePdS())) {
                    turnP.removePdL(island.getFeat(exploit).getPricePdL());
                    turnP.removePdS(island.getFeat(exploit).getPricePdS());
                    island.getFeat(exploit).setPlayer(turnP);
                    if (number == 1)
                        System.out.println("\033[34mJoueur " + (this.turnPlayer + 1) + " réalise l'exploit " + exploit.getName().split("\\.")[2] + "\u001B[0m");
                    island.removeFeat(exploit);
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
    public void printLog(int number) {
        if (number == 1) {
            for (Player p : players) {
                System.out.println(this.players.indexOf(p) == this.getTurnPlayer() ? "\u001B[31m" + "information joueur: " + (this.players.indexOf(p) + 1) + "\u001B[0m" : "information joueur: " + (this.players.indexOf(p) + 1));
                System.out.println("Honour: " + p.getHonour() + "\nGold: " + p.getGold() + "/" + p.getMaxGold() + "\nPdS: " + p.getPdS() + "/" + p.getMaxPdS() + "\nPdL: " + p.getPdL() + "/" + p.getMaxPdL());
                for (int i = 0; i < p.getNbFeat(); i++) {
                    if (p.getFeat(i) instanceof Hammer) {
                        Hammer hammer = (Hammer) p.getFeat(i);
                        if (hammer.getLevel() < 2) {
                            System.out.println("Hammer level " + (hammer.getLevel() + 1) + ": " + hammer.getGold());
                            break;
                        }
                    }
                }
                p.getDice(0).toString(1);
                p.getDice(1).toString(2);
            }
        }
    }

    public int[] honour() {
        int[] winner = {this.players.get(0).getHonour(), 0};//winner[0]=max;winner[1]=playerid;
        for (Player i : this.players) {
            if (winner[0] < i.getHonour()) {
                winner[0] = i.getHonour();
                winner[1] = this.players.indexOf(i);
            }
        }
        return winner;
    }

    public void printWinner(int number) {
        int[] winner = this.honour();
        if (number == 1) {
            System.out.println("Joueur " + (winner[1] + 1) + " gagne avec " + winner[0] + " honneurs ");
        } else {
            for (int i = 0; i < this.getNumberPlayer(); i++) {
                if (winner[1] == i) {
                    this.nbVictoire.set(i, this.nbVictoire.get(i) + 1);
                }
            }
        }
    }


    public void buy(Pool pool, int poolFace, int diceNumber, int diceFace) {
        this.getPlayer(this.turnPlayer).buy(this, pool, poolFace, diceNumber, diceFace);
    }

    public void reset() {
        for (Player player : players) {
            player.reset();
        }
        this.round = 1;
        Forge forge = new Forge(this);
        this.addForge(forge);
        World world = new World(this);
        this.addWorld(world);
        this.turnPlayer = 0;
    }

    public void game(int number) {
        for (int j = 0; j < number; j++) {
            while (this.getRound() <= this.getMaxRound()) {
                if (number == 1) System.out.println("Nous sommes au tour : " + this.getRound() + "\n");
                for (int i = 0; i < this.getNumberPlayer(); i++) {
                    this.faveur();
                    this.choixAction(this.getPlayer(this.getTurnPlayer()).chooseAction(), number);
                    this.printLog(number);
                    this.nextPlayer();
                }
            }
            this.printWinner(number);
            this.reset();
        }
        if (number > 1) {
            for (int i = 0; i < this.getNumberPlayer(); i++) {
                System.out.println("Le joueur " + (i+1) + ": " + this.nbVictoire.get(i));
            }
        }
    }
}
