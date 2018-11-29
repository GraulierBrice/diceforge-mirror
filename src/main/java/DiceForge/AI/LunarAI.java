package DiceForge.AI;

import DiceForge.Face.Face;
import DiceForge.Feat.*;
import DiceForge.*;
import java.util.ArrayList;

public class LunarAI extends Strategy {

    public LunarAI(){
        super();
        super.name="Lunar";
    }
    
    @Override
    public Pool setPool() {
        return Referee.getForge().affordablePoolWith(Player.LunarShard,this.player.getGold());
    }


    public int chooseDice() {//on remplit le 2ème dé qui a déjà des lunarShard comme ça on est sûr d'en drop à chaque tour
        Pool bestPool = Referee.getForge().bestPoolWith(Player.LunarShard,this.player.getGold());
        if(bestPool != null && bestPool.getPrice()<=this.player.getGold() && !this.player.doIHaveAnHammer()) {
            return 1;
        }
        return 0;
    }

    @Override
    public Dice chooseBestDice() {
        if(this.player.getMaxLunarShard() -this.player.getLunarShard() < 2) return this.player.getDice(0);
        return this.player.getDice(1);
    }

    @Override
    public void replay() {
        if(player.getHasReplayed()==false && player.getSolarShard()>=2 && player.getLunarShard()>=2){
            player.setHasReplayed(true);
        }
    }

    @Override
    public int chooseDiceFace(int dice) {
        if(this.chooseDice()==1) return (this.player.getDice(dice).faceNotOfThisKind(Player.LunarShard));
        else if(this.chooseDice()==0) return (this.player.getDice(dice).faceNotOfThisKind(Player.GOLD));
        return 0;//test devrait être -1 pour cas erreur
    }

    @Override
    public int chooseFaceOr(Face face) {
        return 0;
    }

    @Override
    public Face[] chooseBestEnnemyFace() {//strat pas faite
        ArrayList<Face> faces = this.player.getEnnemyFaces();
        Face[] bestFace=new Face[2];
        bestFace[0]=faces.get(0);
        bestFace[1]=faces.get(1);

        for(int i=0;i<faces.size();i++){
            if(faces.get(i).getRewardKind(Player.LunarShard) > bestFace[0].getRewardKind(Player.LunarShard)){
                bestFace[0]=faces.get(i);
            }else if(faces.get(i).getRewardKind(Player.LunarShard) > bestFace[1].getRewardKind(Player.LunarShard) && faces.get(i)!=bestFace[0]){
                bestFace[1]=faces.get(i);
            }
        }
        return bestFace;
    }

    @Override
    public int choosePoolFace(Pool pool) {//test pour l'instant pour prendre les pools avec uniquement des lunarShard pour le moment
        if(interestingKind()!="nothing"){
            return pool.isNumber(pool.bestFaceOf(interestingKind()));
        }
        return -1;
    }

    public String interestingKind(){
        Pool poolPdL =Referee.getForge().bestPoolWith(Player.LunarShard,this.player.getGold());
        Pool poolG=Referee.getForge().bestPoolWith(Player.GOLD,this.player.getGold());
        if(poolPdL!=null && poolPdL.getPrice()<=this.player.getGold() && !this.player.doIHaveAnHammer()) {
            return Player.LunarShard;
        }else if(poolG!=null && poolG.getPrice()<=this.player.getGold()) {
            return Player.GOLD;
        }
        return "nothing";
        }

    @Override
    public int choosePool() {
        switch (interestingKind()) {
            case Player.LunarShard:
                return Referee.getForge().isNumber(Referee.getForge().bestPoolWith(Player.LunarShard, this.player.getGold()));
            case Player.GOLD:
                return Referee.getForge().isNumber((Referee.getForge().bestPoolWith(Player.GOLD, this.player.getGold())));
            default:
                return -1;//devrait être -1 en plein test
        }
    }


    @Override
    public void chooseIsland() {
        if((this.player.getLunarShard()>=6 && Referee.getWorld().getIsland(6).isIn(nameFeat.Pince))||(this.player.getLunarShard()>=5 && this.player.getSolarShard()>=5 && Referee.getWorld().getIsland(6).isIn(nameFeat.Hydre))){
            this.player.setCurrentIsland(6);
        }else if(this.player.getLunarShard()>=4 && !Referee.getWorld().isEmpty(4)){//virer les && false quand les iles existeronts
            this.player.setCurrentIsland(4);//3,lune
        }else if(this.player.getLunarShard()>=2 && !Referee.getWorld().isEmpty(2)){
            this.player.setCurrentIsland(2);//2,lune
        }else if(this.player.getLunarShard()>=1 && !Referee.getWorld().isEmpty(0)) {
            this.player.setCurrentIsland(0);//1,lune
        }else if(this.player.getSolarShard()>=2 && Referee.getWorld().getIsland(3).isIn(nameFeat.AilesGardienne)){
            this.player.setCurrentIsland(3);
        }else if(this.player.getSolarShard()>=1 && Referee.getWorld().getIsland(1).isIn(nameFeat.HerbesFolles)){
            this.player.setCurrentIsland(1);//1,soleil
        }else{
            this.player.setCurrentIsland(-1);
        }
    }

    @Override
    public int chooseFeat(){
        Island island=Referee.getWorld().getIsland(this.player.getCurrentIsland());
        switch(this.player.getCurrentIsland()){
            case 0:
                if(island.isIn(nameFeat.Hammer) && !this.player.doIHaveAnHammer()&& player.getLunarShard()>=1)return 0;
                else if(island.isIn(nameFeat.Chest)&& player.getLunarShard()>=1)return 1;
                else return -1;
            case 1:
                if(island.isIn(nameFeat.HerbesFolles)&& player.getSolarShard()>=1) return 1;
                else return -1;
            case 2:
                if(island.isIn(nameFeat.SabotArgent)&& player.getLunarShard()>=2)return 0;
                else if(island.isIn(nameFeat.Satyres)&& player.getLunarShard()>=3)return 1;
                else return -1;
            case 3:
                if(island.isIn(nameFeat.AilesGardienne)&& player.getSolarShard()>=2)return 0;
                else return -1;
            case 4:
                if(island.isIn(nameFeat.Passeur)&& player.getLunarShard()>=4)return 0;
                else if(island.isIn(nameFeat.CasqueInvisibilite)&& player.getLunarShard()>=5)return 1;
                else return -1;
            case 6:
                if(this.player.getSolarShard()>=5 &&island.isIn(nameFeat.Hydre)&& player.getLunarShard()>=5)return 1;
                else if(island.isIn(nameFeat.Pince)&& player.getLunarShard()>=6)return 0;
                else return -1;
        }
        return -1;
    }
}
