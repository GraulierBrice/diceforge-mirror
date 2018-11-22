package DiceForge;
import DiceForge.AI.*;



public class Main {
	public static final int numberOfGames=10000;
    public static final int LEVEL=2;//LEVEL==1 printLog, LEVEL==2 print result tons of games, LEVEL==3 print dans tout les cas
    public static void main(String[] args) {

        Referee R = new Referee(new Player("2"),new Player("3"));
        Forge forge = new Forge(R);
        World world = new World(R);
        Announcer announcer = new Announcer(R);
        R.addForge(forge);
        R.addWorld(world);
        announcer.DiceForgeResult();
    }
}

