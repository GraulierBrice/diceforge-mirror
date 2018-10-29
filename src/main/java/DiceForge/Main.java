package DiceForge;
import DiceForge.Face.*;
import DiceForge.AI.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Referee R = new Referee(new RandomAI(), new RandomAI(), new LunarAI(),new LunarAI());
        Forge forge = new Forge(R);
        World world = new World(R);
        R.addForge(forge);
        R.addWorld(world);
        R.game(30000);
    }
}

