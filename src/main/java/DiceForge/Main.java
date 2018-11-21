package DiceForge;
import DiceForge.AI.*;



public class Main {
	public static int numberOfGames=10000;
    public static void main(String[] args) {

        Referee R = new Referee(new Player(new LunarAI(),"1"),new Player("2"),new Player(new LunarAI(),"3"),new Player(new LunarAI(),"4"));
        Forge forge = new Forge(R);
        World world = new World(R);
        Announcer announcer = new Announcer(R);
        R.addForge(forge);
        R.addWorld(world);
        announcer.DiceForgeResult(numberOfGames);
    }
}

