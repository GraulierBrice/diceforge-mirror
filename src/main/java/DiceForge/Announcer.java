package DiceForge;

import DiceForge.Feat.Hammer;

import java.util.ArrayList;

public class Announcer {
    Referee referee;
    public ArrayList<Integer> nbVictoire = new ArrayList<>();
    public ArrayList<Integer> sumHonour = new ArrayList<>();
    public static final String ANSI_RED="\u001B[31m";
    public static final String ANSI_GREEN="\u001B[32m";
    public static final String ANSI_BLUE="\u001B[34m";
    public static final String ANSI_YELLOW="\u001B[33m";
    public static final String ANSI_BOLD="\u001B[1m";
    public static final String ANSI_RESET="\u001B[0m";
    public static final String ANSI_UNDERLINE="\u001B[4m";

    public static final String ANSI_SYELLOW="\u001B[93m";
    public static final String ANSI_SRED="\u001B[91m";
    public static final String ANSI_SGREEN="\u001B[92m";
    public static final String ANSI_SBLUE="\u001B[94m";

    public Announcer(Referee referee) {
        this.referee = referee;
        for(Player p:referee.getPlayers()) {
            this.nbVictoire.add(0);
            this.sumHonour.add(p.getHonour());
        }
    }

    public void printWinner(int number) {
        int[] winner = referee.honour();
        if (number == 1) {
            System.out.println(ANSI_SYELLOW+ANSI_BOLD+"Joueur " + (winner[1] + 1) + " gagne avec " + winner[0] + " honneurs"+ANSI_RESET);
        } else {
            for (int i = 0; i < referee.getNumberPlayer(); i++) {
                if (winner[1] == i) {
                    this.nbVictoire.set(i, this.nbVictoire.get(i) + 1);
                }
                this.sumHonour.set(i, referee.getPlayer(i).getHonour() + this.sumHonour.get(i));
            }
        }
    }

    public void game(int number) {
            for (int j = 0; j < number; j++) {
                while(referee.getRound()<referee.getMaxRound()){
                if (number == 1) System.out.println("Nous sommes au tour : " + referee.getRound() + "\n");
                for (int i = 0; i < referee.getNumberPlayer(); i++) {
                    referee.choixReinforcement(referee.getPlayer(referee.getTurnPlayer()).chooseReinforcement(), number);
                    referee.choixAction(referee.getPlayer(referee.getTurnPlayer()).chooseAction(), number);
                    this.printLog(number);
                    if (referee.getNumberPlayer() == 2) {
                        referee.faveur();
                        System.out.println("Seconde Faveur:");
                        referee.getPlayer(referee.getTurnPlayer()).getDice(0).toString(1);
                        referee.getPlayer(referee.getTurnPlayer()).getDice(1).toString(2);
                    }
                    referee.faveur();

                    referee.nextPlayer();
                }
            }
            this.printWinner(number);
            referee.reset();
        }
        if (number > 1) {
            for (int i = 0; i < referee.getNumberPlayer(); i++) {
                System.out.println(ANSI_UNDERLINE+ANSI_SRED+"Le joueur " + (i + 1) + ANSI_RESET + this.nbVictoire.get(i) + "(" + ((float) this.nbVictoire.get(i) / (float) number) * 100 + "%)");
                System.out.println("Honneur moyen du joueur " + (i + 1) + ": " + this.sumHonour.get(i) / number);
            }
        }
    }

    public void printLog(int number){
        if (number == 1) {
            for (Player p : referee.getPlayers()) {
                System.out.println(referee.getPlayers().indexOf(p) == referee.getTurnPlayer() ? ANSI_UNDERLINE+ANSI_SRED+"Information joueur: " + (referee.getPlayers().indexOf(p) + 1) + ANSI_RESET :  ANSI_UNDERLINE+"Information joueur: " + (referee.getPlayers().indexOf(p) + 1) + ANSI_RESET);
                System.out.println("  Honour: " + p.getHonour() + "\n  Gold: " + p.getGold() + "/" + p.getMaxGold() + "\n  PdS: " + p.getPdS() + "/" + p.getMaxPdS() + "\n  PdL: " + p.getPdL() + "/" + p.getMaxPdL());
                for (int i = 0; i < p.getNbFeat(); i++) {
                    if (p.getFeat(i) instanceof Hammer) {
                        Hammer hammer = (Hammer) p.getFeat(i);
                        if (hammer.getLevel() < 2) {
                            System.out.println("  Hammer level " + (hammer.getLevel() + 1) + ": " + hammer.getGold());
                            break;
                        }
                    }
                }
                System.out.println("  Faveur :");
                p.getDice(0).toString(1);
                p.getDice(1).toString(2);
            }
        }
    }
}
