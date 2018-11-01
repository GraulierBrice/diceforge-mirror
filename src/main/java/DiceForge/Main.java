package DiceForge;
import DiceForge.AI.*;

public class Main {
    public static void main(String[] args) {

        Referee R = new Referee(new LunarAI(),new RandomAI());
        Forge forge = new Forge(R);
        World world = new World(R);
        R.addForge(forge);
        R.addWorld(world);
        R.game(1);
    }
}

