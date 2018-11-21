package DiceForge;
import DiceForge.AI.*;



public class Main {
	public static final int numberOfGames=1;
    public static final int LEVEL=1;//LEVEL==1 printLog, LEVEL==2 print result tons of games, LEVEL==3 print dans tout les cas
    public static void main(String[] args) {

        Referee R = new Referee(new Player(new LunarAI(),"1"),new Player("2"),new Player(new LunarAI(),"3"),new Player(new SolarAI(),"4"));
        Forge forge = new Forge(R);
        World world = new World(R);
        Announcer announcer = new Announcer(R);
        R.addForge(forge);
        R.addWorld(world);
        announcer.DiceForgeResult();
    }
}

