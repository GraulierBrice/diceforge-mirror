package DiceForge;

import DiceForge.Feat.Chest;
import DiceForge.Feat.*;


import java.util.ArrayList;
import java.util.Arrays;

public class World {
    private ArrayList<Island> islands=new ArrayList<>();
    Island island1 = new Island(new ArrayList<>(Arrays.asList(new Hammer(),new Chest())));
    Island island2 = new Island(new ArrayList<>(Arrays.asList(new Ancien())));
    /*Island island3 = new Island(new ArrayList<>());
    Island island4 = new Island(new ArrayList<>());
    Island island5 = new Island(new ArrayList<>());
    Island island6 = new Island(new ArrayList<>());
    Island island7 = new Island(new ArrayList<>());*/


    public World(Referee R){
        islands.add(island1);
        islands.add(island2);
        /*islands.add(new Island(island3));
        islands.add(new Island(island4));
        islands.add(new Island(island5));
        islands.add(new Island(island6));
        islands.add(new Island(island7));*/

    }
}
