package DiceForge;

public class Main {
    public static void main(String[] args) {

        Player J1 = new Player();
        Player J2 = new Player();
        J1.addHonour(36);
        if (J1.getHonour() > J2.getHonour()) {
            System.out.println("Le joueur 1 a gagné avec " + J1.getHonour() + " points d'honneur");
        } else {
            System.out.println("Le joueur 2 a gagné avec " + J2.getHonour() + " points d'honneur");
        }
    }
}

