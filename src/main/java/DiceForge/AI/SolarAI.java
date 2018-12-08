package DiceForge.AI;

import DiceForge.Face.Face;
import DiceForge.Feat.*;
import DiceForge.*;
import java.util.ArrayList;


public class SolarAI extends Strategy {

    public SolarAI(){
        super();
        super.name = "Solar";
    }

    public Pool setPool() {
        return Referee.getForge().affordablePoolWith(Player.SolarShard,this.player.getGold());
    }


    public int chooseDice() {//on remplit le 2ème dé qui a déjà des solarshard comme ça on est sûr d'en drop à chaque tour
        Pool bestPool = Referee.getForge().bestPoolWith(Player.SolarShard,this.player.getGold());
        if(bestPool != null && bestPool.getPrice()<=this.player.getGold() && !this.player.doIHaveAnHammer()) {
            return 0;
        }
        return 1;
    }


    public Dice chooseBestDice() {
        if(this.player.getMaxSolarShard() -this.player.getSolarShard() < 2) return this.player.getDice(0);
        return this.player.getDice(1);
    }

    @Override
    public void replay() {
        if(player.getHasReplayed()==false && player.getSolarShard()>=4){
            player.setHasReplayed(true);
        }
    }


    public int chooseDiceFace(int dice) {
        if(this.chooseDice()==0) return (this.player.getDice(dice).faceNotOfThisKind(Player.SolarShard));
        else if(this.chooseDice()==1) return (this.player.getDice(dice).faceNotOfThisKind(Player.GOLD));
        return 0;//test devrait être -1 pour cas erreur
    }

    @Override
    public int chooseFaceOr(Face face){
        switch (chooseDiceFaceOr(face)){
            case 1:
                if(this.player.getLunarShard()>5 && this.player.getSolarShard()==4) {
                    return 2;
                }else if(this.player.getSolarShard()>5 && this.player.getLunarShard()==4) {
                    return 1;
                }else if(this.player.getLunarShard()==5 && this.player.getSolarShard()<4){
                    return 1;
                }else if(this.player.getSolarShard()==5 && this.player.getLunarShard()<4){
                    return 2;
                }else if(this.player.getLunarShard()==5 && this.player.getSolarShard()==4){
                    return 2;
                }else if(this.player.getSolarShard()==5 && this.player.getLunarShard()==4){
                    return 1;
                }else if(this.player.getSolarShard()<5) {
                    return 2;
                }else{
                    return 0;
                }
            case 2:
                if(this.player.getMaxGold() > this.player.getGold()+face.getRewardKind(Player.GOLD)){
                    return 0;
                }else{
                    return 1;
                }
            case 3:
                if(this.player.getLunarShard()>5 && (this.player.getSolarShard()==3 || this.player.getSolarShard()==4)){
                    return 2;
                }else if(this.player.getSolarShard()>5 && (this.player.getLunarShard()==3 || this.player.getLunarShard()==4)){
                    return 1;
                }else if(this.player.getLunarShard()==5 && this.player.getSolarShard()<3){
                    return 1;
                }else if(this.player.getSolarShard()==5 && this.player.getLunarShard()<3){
                    return 2;
                }else if(this.player.getLunarShard()==5 && (this.player.getSolarShard()==3 || this.player.getSolarShard()==4)){
                    return 2;
                }else if(this.player.getSolarShard()==5 && (this.player.getLunarShard()==3 || this.player.getLunarShard()==4)){
                    return 1;
                }else if(this.player.getSolarShard()<5) {
                    return 2;
                }else{
                    return 0;
                }
        }
        return 0;
    }
    @Override
    public Face[] chooseBestEnnemyFace() {//strat à faire
        ArrayList<Face> faces = this.player.getEnnemyFaces();
        Face[] bestFace=new Face[2];
        bestFace[0]=faces.get(0);
        bestFace[1]=faces.get(1);

        for(int i=1;i<faces.size();i++){
            if(faces.get(i).getRewardKind(Player.SolarShard) > bestFace[0].getRewardKind(Player.SolarShard)){
                bestFace[0]=faces.get(i);
            }else if(faces.get(i).getRewardKind(Player.SolarShard) > bestFace[1].getRewardKind(Player.SolarShard) && faces.get(i)!=bestFace[0]){
                bestFace[1]=faces.get(i);
            }
        }
        return bestFace;
    }

    @Override
    public Face[] chooseWorstEnnemyFace() {
        ArrayList<Face> faces = this.player.getEnnemyFaces();
        Face[] worstFaces=new Face[2];
        int numberChosen=0;
        worstFaces[0]=faces.get(0);
        worstFaces[1]=faces.get(1);
        for(int i=0;i<faces.size();i++) {
            if ((this.player.getGold() == 0 && faces.get(i).getRewardKind(Player.GOLD)>0) || (this.player.getLunarShard() == 0 && faces.get(i).getRewardKind(Player.LunarShard)>0) ||
                    (this.player.getSolarShard() == 0 && faces.get(i).getRewardKind(Player.SolarShard) >0) || this.player.getHonour() == 0 && faces.get(i).getRewardKind(Player.HONOUR) >0){
                worstFaces[numberChosen]=faces.get(i);
                numberChosen++;
            }
            if(numberChosen==2) break;
        }
        if(numberChosen!=2){
            for(int i=0;i<faces.size();i++){
                if((faces.get(i).getRewardKind(Player.GOLD)>0 && faces.get(i).getRewardKind(Player.GOLD)<=3) || (faces.get(i).getRewardKind(Player.HONOUR)>0 && faces.get(i).getRewardKind(Player.HONOUR)<=3) ||
                        (faces.get(i).getRewardKind(Player.LunarShard)>0 && faces.get(i).getRewardKind(Player.LunarShard)<=2)){
                    worstFaces[numberChosen]=faces.get(i);
                    numberChosen++;
                }
                if(numberChosen==2) break;
            }
        }
        return worstFaces;
    }


    public int choosePoolFace(Pool pool) {//test pour l'instant pour prendre les pools avec uniquement des solarshard pour le moment
        if(interestingKind()!="nothing"){
            return pool.isNumber(pool.bestFaceOf(interestingKind()));
        }
        return -1;
    }

    public String interestingKind(){
        Pool poolSolarShard =Referee.getForge().bestPoolWith(Player.SolarShard,this.player.getGold());
        Pool poolG=Referee.getForge().bestPoolWith(Player.GOLD,this.player.getGold());
        if(poolSolarShard!=null && poolSolarShard.getPrice()<=this.player.getGold() && !this.player.doIHaveAnHammer()) {
            return Player.SolarShard;
        }else if(poolG!=null && poolG.getPrice()<=this.player.getGold()) {
            return Player.GOLD;
        }
        return "nothing";
    }


    public int choosePool() {
        switch (interestingKind()) {
            case Player.SolarShard:
                return Referee.getForge().isNumber(Referee.getForge().bestPoolWith(Player.SolarShard, this.player.getGold()));
            case Player.GOLD:
                return Referee.getForge().isNumber((Referee.getForge().bestPoolWith(Player.GOLD, this.player.getGold())));
            default:
                return -1;//devrait être -1 en plein test      
        }
    }

    public boolean shouldKeepForging(){
        return Referee.getForge().bestPoolWith(interestingKind(), this.player.getGold()) != null;
    }

    @Override
    public void chooseIsland() {
        if((this.player.getSolarShard()>=6 && Referee.getWorld().getIsland(6).isIn(nameFeat.Enigme))||(this.player.getLunarShard()>=5 && this.player.getSolarShard()>=5 && Referee.getWorld().getIsland(6).isIn(nameFeat.Hydre))){
            this.player.setCurrentIsland(6);
        }else if(this.player.getSolarShard()>=4 && !Referee.getWorld().isEmpty(5)){
            this.player.setCurrentIsland(5);
        }else if(this.player.getSolarShard()>=2 && !Referee.getWorld().isEmpty(3)){
            this.player.setCurrentIsland(3);
        }else if(this.player.getSolarShard()>=1 && !Referee.getWorld().isEmpty(1)){
            this.player.setCurrentIsland(1);
        }else if(this.player.getLunarShard()>=2 && !Referee.getWorld().isEmpty(2)){
            this.player.setCurrentIsland(2);
        }else if(this.player.getLunarShard()>=1 && !Referee.getWorld().isEmpty(0)) {
            this.player.setCurrentIsland(0);
        }else{
            this.player.setCurrentIsland(-1);
        }
    }

    @Override
    public int chooseFeat(){
        switch(this.player.getCurrentIsland()){
            case 0:
                if(Referee.getWorld().affordableFeat(0,player,nameFeat.Hammer) && !this.player.doIHaveAnHammer())return 0;
                else if(Referee.getWorld().affordableFeat(0,player,nameFeat.Chest))return 1;
                else return -1;
            case 1:
                if(Referee.getWorld().affordableFeat(1,player,nameFeat.Ancien))return 0;
                else if(Referee.getWorld().affordableFeat(1,player,nameFeat.HerbesFolles)) return 1;
                else return -1;
            case 2:
                if(Referee.getWorld().affordableFeat(2,player,nameFeat.SabotArgent))return 0;
                else if(Referee.getWorld().affordableFeat(2,player,nameFeat.Satyres))return 1;
                else return -1;
            case 3:
                if(Referee.getWorld().affordableFeat(3,player,nameFeat.AilesGardienne))return 0;
                else if(Referee.getWorld().affordableFeat(3,player,nameFeat.Minotaure))return 1;
                else return -1;
            case 5:
                if(Referee.getWorld().affordableFeat(5,player,nameFeat.Meduse))return 0;
                else return -1;
            case 6:
                if(Referee.getWorld().affordableFeat(6,player,nameFeat.Hydre))return 1;
                else if(Referee.getWorld().affordableFeat(6,player,nameFeat.Enigme)) return 2;
                else return -1;
        }
        return -1;
    }
}
