package DiceForge;
import DiceForge.AI.*;



public class Main {
	public static int numberOfGames=100000;
    public static void main(String[] args) {

        Referee R = new Referee(new Player("RandomAI_1"),new Player(new LunarAI(),"LunarAI"),new Player(new SolarAI(),"SolarAI"),new Player("RandomAI_2"));
        Forge forge = new Forge(R);
        World world = new World(R);
        Announcer announcer = new Announcer(R);
        R.addForge(forge);
        R.addWorld(world);
        announcer.DiceForgeResult(numberOfGames);
    }
}

