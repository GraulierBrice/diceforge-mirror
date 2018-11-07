package DiceForge;
import DiceForge.AI.*;

public class Main {
	public static int number=1000000;
    public static void main(String[] args) {

        Referee R = new Referee(new RandomAI(),new RandomAI(),new LunarAI(), new RandomAI());
        Forge forge = new Forge(R);
        World world = new World(R);
        R.addForge(forge);
        R.addWorld(world);
        R.game(number);
    }
}

