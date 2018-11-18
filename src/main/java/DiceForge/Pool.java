package DiceForge;
import DiceForge.Face.Face;

import java.util.ArrayList;

public class Pool {
    private int price;
    private ArrayList<Face> faces;

    public Pool(int price, ArrayList<Face> faces) {
        this.faces = faces;
        this.price = price;
    }
    
    /* Accessor */
    public int getPrice(){return this.price;}
    public Face getFace(int n){return this.faces.get(n);}  
    public int howManyFaces(){return faces.size();} 

    /* Mutator */
    public void setPrice(int price) {this.price = price;}

    //Remove the gold and return face
    public Face buy(Player player,Face face) {
        if (player.getGold() >= this.price) {
            player.removeGold(this.price);
            faces.remove(face);
            return face;
        }
        return null;
    }

    //Checks if there are any faces left in pool
    public boolean isEmpty() {
        return this.faces.isEmpty();
    }

    public boolean kindOfPool(String kind){
        for(Face f:faces){
            if(f.getKind().contains(kind) || kind.equals("")){
                return true;
            }
        }
        return false;
    }

    public int faceKind(String kind){
        for(int i=0;i<this.howManyFaces();i++){
            if(this.faces.get(i).getKind().contains(kind)){
                return i;
            }
        }
        return -1;
    }

    public Face bestFaceOf(String kind){
        if(this.faceKind(kind)==-1) return null;
        Face face=faces.get(this.faceKind(kind));
        for(int i=0;i<this.howManyFaces();i++){
            if(this.faces.get(i).getRewardKind(kind)>face.getRewardKind(kind)) face=this.faces.get(i);
        }
        return face;
    }

    public int isNumber(Face face){
        for(int i=0;i<this.howManyFaces();i++){
            if(faces.get(i)==face) return i;
        }
        return -1;
    }
}


