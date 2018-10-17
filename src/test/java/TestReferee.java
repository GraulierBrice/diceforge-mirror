
import org.junit.Test;
import static org.junit.Assert.*;
import DiceForge.*;

public class TestReferee {

    @Test public void refereeMethods() {
        Referee referee=new Referee(4);
        for (int i=0;i<referee.getNumberPlayer();i++) {
            referee.getPlayer(i).addHonour(6*(i+1));//on donne 6 honour au j1 puis 12 au j2 etc..
        }
    }
}
