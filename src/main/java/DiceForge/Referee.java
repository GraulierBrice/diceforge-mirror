package DiceForge;

import DiceForge.Feat.Hammer;

import java.util.ArrayList;

public class Referee {
    private ArrayList<Player> players = new ArrayList<>();
    private static Forge forge;
    private static World world;
    private int turnPlayer, round, maxRound;

    public Referee(Player... players) {
        int gold = 3;
        for (int i = 0; i < players.length; i++) {
            this.players.add(players[i]);
            this.players.get(i).addGold(gold);

            gold--;
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

    public ArrayList<Player> getPlayers(){ return this.players;}

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

    public void choixReinforcement(String action, int number) {
        Player turnP = this.getPlayer(this.turnPlayer);
        if (action == "reinforcement") {
            for (int i = 0; i < turnP.getNbFeat(); i++) {
                if (turnP.getFeat(i).getReinfor()) {
                    if (number == 1)
                        System.out.println(Announcer.ANSI_UNDERLINE+Announcer.ANSI_BOLD+Announcer.ANSI_SGREEN+"Joueur " + (this.turnPlayer + 1) + " peut renforcer " + turnP.getFeat(i).getClass().getName().split("\\.")[2] + Announcer.ANSI_RESET);
                    if (turnP.chooseFeatReinforcement() == "featreinforcement") {
                        if (number == 1)
                            System.out.println(Announcer.ANSI_GREEN+"Joueur " + (this.turnPlayer + 1) + " renforce l'exploit " + turnP.getFeat(i).getClass().getName().split("\\.")[2] + Announcer.ANSI_RESET);
                        turnP.getFeat(i).effect();
                    } else {
                        if (number == 1)
                            System.out.println(Announcer.ANSI_GREEN+"Il n'a pas fait de renforcement"+Announcer.ANSI_RESET);
                    }
                }
            }
        }
    }

    public void choixAction(String action, int number) {//number = nombre de games

        Player turnP = this.getPlayer(this.turnPlayer);

        switch (action) {
            case "passe":
                if (number == 1)
                    System.out.println(Announcer.ANSI_SBLUE+"Joueur " + (this.turnPlayer + 1) + " passe son tour"+Announcer.ANSI_RESET);
                break;
            case "forge":
                if (number == 1)
                    System.out.println(Announcer.ANSI_SBLUE+"Joueur " + (this.turnPlayer + 1) + " peut acheter une face"+Announcer.ANSI_RESET);
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
                    System.out.println(Announcer.ANSI_UNDERLINE+Announcer.ANSI_BOLD+Announcer.ANSI_SBLUE+"Joueur " + (this.turnPlayer + 1) + " peut choisir un exploit à réaliser"+Announcer.ANSI_RESET);
                turnP.chooseIsland();
                Island island = this.world.getIsland(turnP.getCurrentIsland());
                Class exploit = turnP.listFeat(turnP.chooseFeat());//l'exploit sur l'ile qu'il va choisir
                if (island.isIn(exploit) && (turnP.getPdL() >= island.getFeat(exploit).getPricePdL() || turnP.getPdS() >= island.getFeat(exploit).getPricePdS())) {
                    turnP.removePdL(island.getFeat(exploit).getPricePdL());
                    turnP.removePdS(island.getFeat(exploit).getPricePdS());
                    island.getFeat(exploit).setPlayer(turnP);
                    if (number == 1)
                        System.out.println(Announcer.ANSI_SBLUE+" Joueur " + (this.turnPlayer + 1) + " réalise l'exploit " + exploit.getName().split("\\.")[2] + Announcer.ANSI_RESET);
                    island.removeFeat(exploit);
                } else {
                    if (number == 1)
                        System.out.println(Announcer.ANSI_SBLUE+"Il ne réalise pas d'exploit"+Announcer.ANSI_RESET);
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

}
