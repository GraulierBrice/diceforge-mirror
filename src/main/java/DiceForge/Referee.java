package DiceForge;

import DiceForge.Face.*;
import DiceForge.Feat.Satyres;

import java.util.ArrayList;

public class Referee {
    private ArrayList<Player> players = new ArrayList<>();
    private static Forge forge;
    private static World world;
    private int turnPlayer, round, maxRound;
    private Player turnP;

    public static final String PASSE="passe";
    public static final String FORGE="forge";
    public static final String EXPLOIT="exploit";
    public static final String REINFORCEMENT="reinforcement";
    public static final String FEAT_REINFORCEMENT="featreinforcement";

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
    public Player getCurrentPlayer() { return this.turnP; }
    public ArrayList<Player> getPlayers(){ return this.players;}

    public static Forge getForge() {
        return forge;
    }

    public static World getWorld() {
        return world;
    }

    public void getEnnemyRoll(){
        ArrayList<Face> ennemyFaces=new ArrayList<>();
        for(int i=0;i<this.getNumberPlayer();i++){
            if(players.get(i)!=players.get(turnPlayer)){
                ennemyFaces.add(players.get(i).getDice(0).getReward());
                ennemyFaces.add(players.get(i).getDice(1).getReward());
            }
        }
        this.players.get(this.getTurnPlayer()).setEnnemyFaces(ennemyFaces);
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

    public void choixReinforcement(String action) {
        turnP = this.getPlayer(this.turnPlayer);
        if (action == REINFORCEMENT) {
            for (int i = 0; i < turnP.getNbFeat(); i++) {
                if (turnP.getFeat(i).getReinfor() && turnP.strategy.chooseFeatReinforcement() == FEAT_REINFORCEMENT) {
                    turnP.getFeat(i).effect();
                }
            }
        }
    }

    public void choixAction(String action) {//number = nombre de games

        turnP = this.getPlayer(this.turnPlayer);
        turnP.shouldIChangeStrategy(this);
        switch (action) {//"passe" virer vu que c'était juste un print, il est passé chez announcer
            case FORGE:
                Pool pool = forge.getPool(turnP.strategy.choosePool());
                if (!pool.isEmpty() && turnP.getGold() >= pool.getPrice()) {
                    int poolFace = turnP.strategy.choosePoolFace(pool);
                    int dice = turnP.strategy.chooseDice();
                    int diceFace = turnP.strategy.chooseDiceFace(turnP.strategy.chooseDice());
                    this.getPlayer(this.turnPlayer).buy(pool, poolFace, dice, diceFace);
                }
                break;
            case EXPLOIT:
                Island island = this.world.getIsland(turnP.getCurrentIsland());
                Class exploit = turnP.listFeat(turnP.strategy.chooseFeat());//l'exploit sur l'ile qu'il va choisir
                if (island.isIn(exploit) && (turnP.getLunarShard() >= island.getFeat(exploit).getPriceLunarShard() && turnP.getSolarShard() >= island.getFeat(exploit).getPriceSolarShard())) {
                    this.getEnnemyRoll();
                    this.world.giveFeat(turnP, exploit);
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


    public Player winner(){
        Player player=this.players.get(0);
        for(Player p : this.players){
            if(p.getHonour()>player.getHonour()){
                player=p;
            }
        }
        player.addVictory();
        return player;
    }

    public void reset() {
        for (Player player : players) {
            player.addSumHonour();
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
