package DiceForge;
import DiceForge.AI.*;


public class Main {
	public static final int numberOfGames=1000;
    public static final int LEVEL=2;//LEVEL==1 printLog, LEVEL==2 print result tons of games, LEVEL==3 print dans tout les cas
    public static void main(String[] args) {//modification du main pour correspondre à la demande de rendu

        Referee R = new Referee(new Player(new LunarAI(),"lunar1"),new Player(new SolarAI(),"solar2"),new Player(new HammerAI(),"hammer3"),new Player(new HammerAI(),"hammer4"));
        Forge forge = new Forge(R);
        World world = new World(R);
        Announcer announcer = new Announcer(R);
        R.addForge(forge);
        R.addWorld(world);
        System.out.println(Announcer.ANSI_SBLUE +numberOfGames+" parties avec LunarAI, SolarAI et 2 HammerAI, donc toutes nos AI se battent :"+Announcer.ANSI_RESET);
        announcer.DiceForgeResult();
        R.getPlayers().removeAll(R.getPlayers());
        System.out.println(Announcer.ANSI_SBLUE+numberOfGames+" parties avec nos meilleures AI qui se battent contre elles:"+Announcer.ANSI_RESET);
        Referee referee = new Referee(new Player(new HammerAI(),"hammer1"),new Player(new HammerAI(),"hammer2"),new Player(new HammerAI(),"hammer3"),new Player(new HammerAI(),"hammer4"));
        Forge forge2 = new Forge(referee);
        World world2 = new World(referee);
        Announcer announcer2 = new Announcer(referee);
        R.addForge(forge2);
        R.addWorld(world2);
        announcer2.DiceForgeResult();

    }
}

