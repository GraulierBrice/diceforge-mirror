package DiceForge;

import DiceForge.Face.*;
import DiceForge.Feat.Satyres;
import DiceForge.Feat.nameFeat;

import java.util.ArrayList;

public class Referee {
    private static ArrayList<Player> players = new ArrayList<>();
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
    public static ArrayList<Player> getPlayers(){ return players;}

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
                players.get(i).getDice(0).rollDice();
                players.get(i).getDice(1).rollDice();
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

    public void sameIsland(){
        for(Player p:this.getPlayers()){
            if(p!=this.getPlayer(this.getTurnPlayer()) && p.getCurrentIsland()==this.getPlayer(this.getTurnPlayer()).getCurrentIsland() && p.getCurrentIsland()!=-1){
                p.setCurrentIsland(-1);
                p.faveur();
                Announcer.printSameIsland(this.getPlayer(this.getTurnPlayer()),p);
            }
        }
    }

    public void choixReinforcement() {
        turnP = this.getPlayer(this.turnPlayer);
        turnP.strategy.chooseReinforcement();
        String action=turnP.getAction();
        if (action == REINFORCEMENT) {
            turnP.strategy.chooseFeatReinforcement();
            action=turnP.getAction();
            for (int i = 0; i < turnP.getNbFeat(); i++) {
                if (turnP.getFeat(i).getReinfor() && action == FEAT_REINFORCEMENT) {
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
                nameFeat exploit = turnP.listFeat(turnP.strategy.chooseFeat());//l'exploit sur l'ile qu'il va choisir
                if (island.isIn(exploit) && (turnP.getLunarShard() >= island.getFeat(exploit).getPriceLunarShard() && turnP.getSolarShard() >= island.getFeat(exploit).getPriceSolarShard())) {
                    this.world.giveFeat(turnP, exploit);
                }
                break;
        }
    }

    public void turn(Player player){
        player.strategy.chooseReinforcement();
        Announcer.printReinforcement(player);
        this.choixReinforcement();

        player.strategy.chooseAction();
        this.getEnnemyRoll(); // à voir à mieux placer
        if (this.round != this.maxRound) {
            this.choixAction(player.getAction());
        } else {
            player.lastAction();
        }
        this.sameIsland();

       Announcer.printAction(player);
        if (this.getNumberPlayer() == 2) {
            this.faveur();
        }
        this.faveur();
       Announcer.printLog(this);
    }


    public void replay(Player player){
        if(!player.getHasReplayed()){
            player.strategy.replay();
            if(player.getHasReplayed()){
                Announcer.printReplay(player);
                this.turn(player);
                player.setHasReplayed(false);
                player.removeSolarShard(2);
            }
        }
    }

    public void game(){
        while (this.getRound() <= this.getMaxRound()) {
            Announcer.printTurnNumber(this.getRound());
            for (int i = 0; i < this.getNumberPlayer(); i++) {
                Player currentPlayer=this.getPlayer(this.getTurnPlayer());

                this.turn(currentPlayer);
                this.replay(currentPlayer);


                this.nextPlayer();
            }
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
            player.setMaxHonour();
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
