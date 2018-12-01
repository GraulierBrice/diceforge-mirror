package DiceForge.AI;
import DiceForge.*;
import DiceForge.Face.Face;
import DiceForge.Feat.*;
import java.util.ArrayList;

import java.util.Random;

public class RandomAI extends Strategy{


    private Random r = new Random();

    public RandomAI(){
        super();
        super.name = "Random";
    }

    @Override
    public Pool setPool() {
        return null;
    }

    public void chooseReinforcement() {
        int choice = r.nextInt(2);
        switch(choice) {
            case 0:
                this.player.setAction(null);
                break;
            case 1:
                this.player.setAction(Referee.REINFORCEMENT);
                break;
        }
    }

    public void chooseFeatReinforcement() {
        int choice = r.nextInt(2);
        switch(choice) {
            case 0:
                this.player.setAction(null);
                break;
            case 1:
                this.player.setAction(Referee.FEAT_REINFORCEMENT);
                break;
        }
    }

    @Override
    public void chooseAction() {
        this.chooseIsland();
        int choice;
        if(this.player.getCurrentIsland()!=-1 && this.chooseFeat()!=-1)
             choice = r.nextInt(3);
        else choice= r.nextInt(2);
        switch(choice){
            case 0:
                this.player.setAction(Referee.PASSE);
                break;
            case 1:
                this.player.setAction(Referee.FORGE);
                break;
            case 2:
                this.player.setAction(Referee.EXPLOIT);
                break;
        }
    }


    public int chooseDice(){
        return r.nextInt(2);
    }

    public Dice chooseBestDice(){
        return this.player.getDice(r.nextInt(2));
    }

    public int chooseDiceFace(int dice) {
        return r.nextInt(6);
    }

    @Override
    public int chooseFaceOr(Face face) {
        return r.nextInt(face.getNumberOfKind());
    }

    @Override
    public Face[] chooseBestEnnemyFace() {
        ArrayList<Face> faces = this.player.getEnnemyFaces();// System.out.println(faces.size());
        Face[] bestFaces=new Face[2];
        bestFaces[0]=faces.get(0);
        bestFaces[1]=faces.get(1);
        return bestFaces;
    }

    public int choosePoolFace(Pool pool) {
        return r.nextInt(pool.howManyFaces());
    }

    public int choosePool() {
        return r.nextInt(10);//10 jeu complet mais 3 pools non codées
    }

    public boolean shouldKeepForging(){
        return false;
    }

    public int goldChoice(int gold, Hammer m) {
        int g = r.nextInt(gold+1);
        m.effect(g);
        return gold-g;
    }

    public void chooseIsland(){
        int value=r.nextInt(7);
        if(!Referee.getWorld().getIsland(value).isIn(player.listFeat(this.chooseFeat()))  || this.player.getLunarShard()<Referee.getWorld().getIsland(value).lowestPriceOfFeat(Player.LunarShard).getPriceLunarShard() || this.player.getSolarShard()<Referee.getWorld().getIsland(value).lowestPriceOfFeat(Player.SolarShard).getPriceSolarShard()){
            value=-1;
        }
        this.player.setCurrentIsland(value);

    }

    public int chooseFeat(){
        int value;
        if(this.player.getCurrentIsland()<6) value= r.nextInt(2);//pour l'instant il n'y a pas d'ile avec plus de deux feat, pour la dernière ile, il faudra juste vérif si on est dessus et dans ce cas on fera random bound: 3
        else  value=r.nextInt(3);
        if(this.player.getCurrentIsland()==-1 || !Referee.getWorld().getIsland(this.player.getCurrentIsland()).isIn(this.player.listFeat(value))){
            return -1;
        }else{
            return value;
        }
    }

    public void replay(){
        if(player.getHasReplayed()==false && player.getSolarShard()>=2){
            int value=r.nextInt(2);
            if(value==1) player.setHasReplayed(true);
        }
    }
}
