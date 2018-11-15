package DiceForge;
import DiceForge.AI.*;



public class Main {
	public static int numberOfGames=100000;
    public static void main(String[] args) {

        Referee R = new Referee(new RandomAI("1 random"),new RandomAI("2 random"),new RandomAI("3 random"), new LunarAI("4 lunar"));
        Forge forge = new Forge(R);
        World world = new World(R);
        Announcer announcer = new Announcer(R);
        R.addForge(forge);
        R.addWorld(world);
        announcer.DiceForgeResult(numberOfGames);
    }
}

