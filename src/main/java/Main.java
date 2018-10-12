public class Main {
    public static void main(String[] args) {
        Player J1 = new Player();
        Player J2 = new Player();
        J1.addHonor(36);
        if (J1.getHonor() > J2.getHonor()) {
            System.out.println("Le joueur 1 a gagné avec " + J1.getHonor() + " points d'honneur");
        } else {
            System.out.println("Le joueur 2 a gagné avec " + J2.getHonor() + " points d'honneur");
        }
    }
}
