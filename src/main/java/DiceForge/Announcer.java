package DiceForge;

import DiceForge.Face.Face;
import DiceForge.Feat.Hammer;
import DiceForge.Feat.nameFeat;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Announcer {


    Referee referee;
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BOLD = "\u001B[1m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_UNDERLINE = "\u001B[4m";

    public static final String ANSI_SYELLOW = "\u001B[93m";
    public static final String ANSI_SRED = "\u001B[91m";
    public static final String ANSI_SGREEN = "\u001B[92m";
    public static final String ANSI_SBLUE = "\u001B[94m";


    public Announcer(Referee referee) {
        this.referee = referee;
    }

    public void printWinner() {
        ArrayList<Player> winners=referee.winner();
        if (Main.LEVEL == 1) {
            if(winners.size()==1)System.out.println(ANSI_SYELLOW + ANSI_BOLD + "Joueur " + (winners.get(0).getName()) + " gagne avec " + winners.get(0).getHonour() + " honneurs" + ANSI_RESET);
            else{
                System.out.print(ANSI_SYELLOW + ANSI_BOLD + "Le Joueur " + winners.get(0).getName() + " a autant de points d'honneur que ");
                for(int i=1;i<winners.size()-1;i++){
                    System.out.print("le joueur "+winners.get(i).getName() + " et ");
                }
                System.out.println("le joueur "+winners.get(winners.size()-1).getName() + ANSI_RESET);
            }
        }
    }

    public static void printTurnNumber(int number){
        if (Main.LEVEL == 1) System.out.println("Nous sommes au tour : " + number + "\n\n");
    }

    public static void printTwoPlayersSecondRoll(Player player){
        if(Main.LEVEL==1) {
            System.out.println("Seconde Faveur:");
            printDice(player);
        }
    }


    public void DiceForgeResult() {
        for (int j = 0; j < Main.numberOfGames; j++) {
            referee.game();
            this.printWinner();
            referee.reset();
        }
        if (Main.LEVEL == 2) {
            for (int i = 0; i < referee.getNumberPlayer(); i++) {
                System.out.println(ANSI_UNDERLINE + ANSI_SRED + "Le joueur " + referee.getPlayer(i).getName() + ":" + ANSI_RESET + " " + referee.getPlayer(i).getNbVictory() + " victoire(s) (" + ((float) referee.getPlayer(i).getNbVictory() / (float) Main.numberOfGames) * 100 + "%)");
                System.out.println("Le joueur " + referee.getPlayer(i).getName() + " a réalisé: " + referee.getPlayer(i).getNbEgality() + " égalité(s)");
                System.out.println("Honneur moyen du joueur " + referee.getPlayer(i).getName() + ": " + referee.getPlayer(i).getSumHonour() / Main.numberOfGames);
                System.out.println("Honneur max du joueur " + referee.getPlayer(i).getName() + ": " + referee.getPlayer(i).getMaxHonour());
            }
        }
    }


    public static void printLog(Referee referee) {
        if (Main.LEVEL == 1) {
            for (Player p : referee.getPlayers()) {
                System.out.println(referee.getPlayers().indexOf(p) == referee.getTurnPlayer() ? ANSI_UNDERLINE + ANSI_SRED + "Information joueur: " + (referee.getPlayers().indexOf(p) + 1) + ANSI_RESET : ANSI_UNDERLINE + "Information joueur: " + (referee.getPlayers().indexOf(p) + 1) + ANSI_RESET);
                System.out.println("  Honour: " + p.getHonour() + "\n  Gold: " + p.getGold() + "/" + p.getMaxGold() + "\n  SolarShard: " + p.getSolarShard() + "/" + p.getMaxSolarShard() + "\n  LunarShard: " + p.getLunarShard() + "/" + p.getMaxLunarShard());
                for (int i = 0; i < p.getNbFeat(); i++) {
                    if (p.getFeat(i).getName() == nameFeat.Hammer) {
                        Hammer hammer = (Hammer) p.getFeat(i);
                        if (hammer.getLevel() < 2) {
                            System.out.println("  Hammer level " + (hammer.getLevel() + 1) + ": " + hammer.getGold());
                            break;
                        }
                    }
                }
                System.out.println("  Faveur :");
                printDice(p);
                if (referee.getNumberPlayer() == 2) {
                    p.faveur();
                    printTwoPlayersSecondRoll(p);
                }
            }
        }
    }

    public static void printPasse(Player player){if(Main.LEVEL==1) System.out.println(ANSI_SBLUE + "Joueur " + player.getName() + " passe son tour" + ANSI_RESET);}

    public static void printForge(Player player){if(Main.LEVEL==1) System.out.println(ANSI_SBLUE + "Joueur " + player.getName()+ " peut acheter une face" + ANSI_RESET);}

    public static void printExploit(Player player, nameFeat featName){
        if(Main.LEVEL==1) {
            System.out.println(ANSI_UNDERLINE + Announcer.ANSI_BOLD + ANSI_SBLUE + "Joueur " + player.getName() + " peut choisir un exploit à réaliser" + ANSI_RESET);
            System.out.println(ANSI_SBLUE + " Joueur " + player.getName() + " réalise l'éxploit " + featName+ ANSI_RESET);
        }
    }

    public static void printReinforcement(Player player, nameFeat featName) {
        System.out.println(ANSI_GREEN + "Joueur " + (player.getName()) + " renforce l'exploit " + featName + ANSI_RESET);
    }

    public static void printDice(Player player){
        if(Main.LEVEL==1) {
            player.getDice(0).toString(1);
            player.getDice(1).toString(2);
        }
    }

    public static void printSatyres(Player player, Face[] faces) {
        if (Main.LEVEL == 1) {
            System.out.println("Le joueur " + player.getName() + " choisit les faces suivantes :");
            for (int i = 0; i < player.getEnnemyFaces().size(); i++) {
                if (player.getEnnemyFaces().get(i) == faces[0] || player.getEnnemyFaces().get(i) == faces[1]) {
                    System.out.print(Announcer.ANSI_YELLOW + player.getEnnemyFaces().get(i).getReward() + Announcer.ANSI_RESET + " ");
                } else System.out.print(player.getEnnemyFaces().get(i).getReward() + " ");
            }
            System.out.println("\n");
        }
        player.setEnnemyFaces(new ArrayList<>());
    }

    public static void printMinotaure(Referee referee){
        if(Main.LEVEL==1){
            System.out.print('\n');
            for(Player p:referee.getPlayers()){
                if(p.getName()!=referee.getPlayer(referee.getTurnPlayer()).getName())
                System.out.println("Le joueur " + p.getName() + " choisit de perdre "+ANSI_YELLOW+p.getDice(0).getReward().getReward()+ANSI_RESET+" et "+ANSI_YELLOW+p.getDice(1).getReward().getReward()+ANSI_RESET+"");
            }
        }
    }

    public static void printSameIsland(Player currentPlayer,Player otherPlayer) {
        if (Main.LEVEL == 1) {
            System.out.println(ANSI_RED + "\nLe joueur " + otherPlayer.getName() + " est sur la même île que le joueur " + currentPlayer.getName() + " il est donc déplacé au point de départ et une faveur lui est accordée." + ANSI_RESET);
            printDice(otherPlayer);
            System.out.print("\n");
        }
    }

    public static void printReplay(Player player){
        if(Main.LEVEL==1) System.out.println(Announcer.ANSI_SYELLOW+"\n\nle joueur "+player.getName()+" rejoue."+Announcer.ANSI_RESET);
    }


}
