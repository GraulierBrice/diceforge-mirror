package DiceForge;

import DiceForge.Face.Face;
import DiceForge.Player.*;
import java.util.ArrayList;

public class Pool {
    private int price;
    private ArrayList<Face> faces;

    public Pool(int price, ArrayList<Face> faces) {
        this.faces = faces;
        this.price = price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return this.price;
    }

    public Face getFace(int n){
        return this.faces.get(n);
    }

    public Face Buy(Player player,Face face) {
        if (player.getGold() > this.price) {
            player.removeGold(this.price);
            faces.remove(face);
            return face;
        } else {
            return null;
        }
    }

    public boolean isEmpty() {
        return this.faces.isEmpty();
    }
}
