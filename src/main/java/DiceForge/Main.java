package DiceForge;
import DiceForge.Face.*;
import DiceForge.AI.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Referee R = new Referee(new LunarAI(),new RandomAI(),new RandomAI(),new RandomAI());
        Forge forge = new Forge(R);
        World world = new World(R);
        R.addForge(forge);
        R.addWorld(world);
        R.game(10000);
    }
}

