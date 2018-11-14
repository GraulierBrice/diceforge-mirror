package DiceForge;

import DiceForge.Feat.Hammer;

import java.util.ArrayList;

public class Announcer {
    Referee referee;
    public ArrayList<Integer> nbVictoire = new ArrayList<>();
    public ArrayList<Integer> sumHonour = new ArrayList<>();
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
        for (Player p : referee.getPlayers()) {
            this.nbVictoire.add(0);
            this.sumHonour.add(p.getHonour());
        }
    }

    public void printWinner(int number) {
        int[] winner = referee.honour();
        if (number == 1) {
            System.out.println(ANSI_SYELLOW + ANSI_BOLD + "Joueur " + (winner[1] + 1) + " gagne avec " + winner[0] + " honneurs" + ANSI_RESET);
        } else {
            for (int i = 0; i < referee.getNumberPlayer(); i++) {
                if (winner[1] == i) {
                    this.nbVictoire.set(i, this.nbVictoire.get(i) + 1);
                }
                this.sumHonour.set(i, referee.getPlayer(i).getHonour() + this.sumHonour.get(i));
            }
        }
    }

    public void DiceForgeResult(int number) {
        for (int j = 0; j < number; j++) {
            while (referee.getRound() <= referee.getMaxRound()) {
                if (number == 1) System.out.println("Nous sommes au tour : " + referee.getRound() + "\n\n");
                for (int i = 0; i < referee.getNumberPlayer(); i++) {
                    if(number==1)
                        this.printReinforcement();
                    referee.choixReinforcement(referee.getPlayer(referee.getTurnPlayer()).chooseReinforcement());
                    if (number == 1) this.printAction();
                    if (referee.getRound() != referee.getMaxRound()) {
                        referee.choixAction(referee.getPlayer(referee.getTurnPlayer()).chooseAction());
                    }else referee.getPlayer(referee.getTurnPlayer()).lastAction();

                        this.printLog(number);

                    if (referee.getNumberPlayer() == 2) {
                        referee.faveur();
                        System.out.println("Seconde Faveur:");
                        this.printDice(referee.getPlayer(referee.getTurnPlayer()));
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
                System.out.println(ANSI_UNDERLINE + ANSI_SRED + "Le joueur " + (i + 1) + ":" + ANSI_RESET + " " + this.nbVictoire.get(i) + "(" + ((float) this.nbVictoire.get(i) / (float) number) * 100 + "%)");
                System.out.println("Honneur moyen du joueur " + (i + 1) + ": " + this.sumHonour.get(i) / number);
            }
        }
    }

    public void printLog(int number) {
        if (number == 1) {
            for (Player p : referee.getPlayers()) {
                System.out.println(referee.getPlayers().indexOf(p) == referee.getTurnPlayer() ? ANSI_UNDERLINE + ANSI_SRED + "Information joueur: " + (referee.getPlayers().indexOf(p) + 1) + ANSI_RESET : ANSI_UNDERLINE + "Information joueur: " + (referee.getPlayers().indexOf(p) + 1) + ANSI_RESET);
                System.out.println("  Honour: " + p.getHonour() + "\n  Gold: " + p.getGold() + "/" + p.getMaxGold() + "\n  SolarShard: " + p.getSolarShard() + "/" + p.getMaxSolarShard() + "\n  LunarShard: " + p.getLunarShard() + "/" + p.getMaxLunarShard());
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
                this.printDice(p);
            }
        }
    }

    public void printAction() {
        String action = referee.getPlayer(referee.getTurnPlayer()).chooseAction();
        switch (action) {
            case Referee.PASSE:
                System.out.println(ANSI_SBLUE + "Joueur " + (referee.getTurnPlayer() + 1) + " passe son tour" + ANSI_RESET);
                break;
            case Referee.FORGE:
                System.out.println(ANSI_SBLUE + "Joueur " + (referee.getTurnPlayer() + 1) + " peut acheter une face" + ANSI_RESET);
                break;
            case Referee.EXPLOIT:
                System.out.println(ANSI_UNDERLINE + Announcer.ANSI_BOLD + ANSI_SBLUE + "Joueur " + (referee.getTurnPlayer() + 1) + " peut choisir un exploit à réaliser" + ANSI_RESET);
                Player player = referee.getPlayer(referee.getTurnPlayer());
                Island island = referee.getWorld().getIsland(player.getCurrentIsland());
                Class exploit = player.listFeat(player.chooseFeat());//l'exploit sur l'ile qu'il va choisir

                if (island.isIn(exploit) && (player.getLunarShard() >= island.getFeat(exploit).getPriceLunarShard() || player.getSolarShard() >= island.getFeat(exploit).getPriceSolarShard())) {
                    System.out.println(ANSI_SBLUE + " Joueur " + (referee.getTurnPlayer() + 1) + " réalise l'exploit " + exploit.getName().split("\\.")[2] + ANSI_RESET);
                } else System.out.println(ANSI_SBLUE + "Il ne réalise pas d'exploit" + ANSI_RESET);
                break;
        }

    }

    public void printReinforcement() {
        Player player = referee.getPlayer(referee.getTurnPlayer());
        String action = referee.getPlayer(referee.getTurnPlayer()).chooseReinforcement();
        if (action == Referee.REINFORCEMENT) {
            for (int i = 0; i < player.getNbFeat(); i++) {
                if (player.getFeat(i).getReinfor()) {
                        System.out.println(ANSI_UNDERLINE+ANSI_BOLD+ANSI_SGREEN+"Joueur " + (referee.getTurnPlayer() + 1) + " peut renforcer " + player.getFeat(i).getClass().getName().split("\\.")[2] + ANSI_RESET);
                    if (player.chooseFeatReinforcement() == Referee.FEAT_REINFORCEMENT) {
                            System.out.println(ANSI_GREEN+"Joueur " + (referee.getTurnPlayer() + 1) + " renforce l'exploit " + player.getFeat(i).getClass().getName().split("\\.")[2] + ANSI_RESET);
                    } else {
                            System.out.println(ANSI_GREEN+"Il n'a pas fait de renforcement"+ANSI_RESET);
                    }
                }
            }
        }
    }

    public void printDice(Player player){
        player.getDice(0).toString(1);
        player.getDice(1).toString(2);
    }
}
