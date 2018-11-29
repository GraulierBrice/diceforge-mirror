package DiceForge.AI;

import DiceForge.*;
import DiceForge.Face.Face;
import DiceForge.Feat.Hammer;
import DiceForge.Feat.nameFeat;

import java.sql.Ref;
import java.util.ArrayList;

public class HammerAI extends Strategy{


    public HammerAI() {
        super();
        super.name="Hammer";
    }

    @Override
    public Pool setPool() {
        if ( !this.player.doIHaveAnHammer() ) {
            return Referee.getForge().affordablePoolWith(Player.SolarShard,this.player.getGold());
        } else {
            return Referee.getForge().affordablePoolWith(Player.GOLD,this.player.getGold());
        }
    }

    @Override
    public Face[] chooseBestEnnemyFace() {
        ArrayList<Face> faces = this.player.getEnnemyFaces();
        Face[] bestFace=new Face[2];
        bestFace[0]=faces.get(0);
        bestFace[1]=faces.get(1);

        for (Face face : faces) {
            if (face.getRewardKind(Player.GOLD) > bestFace[0].getRewardKind(Player.GOLD)) {
                bestFace[0] = face;
            } else if (face.getRewardKind(Player.GOLD) > bestFace[1].getRewardKind(Player.GOLD) && face != bestFace[0]) {
                bestFace[1] = face;
            }
        }
        return bestFace;

    }

    @Override
    public int chooseDice() {
        Pool bestPool = Referee.getForge().bestPoolWith(Player.GOLD,this.player.getGold());
        if(bestPool != null && bestPool.getPrice()<=this.player.getGold() && !this.player.doIHaveAnHammer()) {
            return 0;
        }
        return 1;
    }

    @Override
    public int chooseDiceFace(int dice) {
        if (this.chooseDice()>=0)
            return this.player.doIHaveAnHammer() ?
                    (this.player.getDice(dice).faceNotOfThisKind(Player.GOLD)) :
                    (this.player.getDice(dice).faceNotOfThisKind(Player.SolarShard));
        return 0;
    }

    @Override
    public int chooseFaceOr(Face face) {
        return 0;
    }

    @Override
    public int choosePoolFace(Pool pool) {
        if(interestingKind().equals("nothing")){
            return pool.isNumber(pool.bestFaceOf(interestingKind()));
        }
        return -1;
    }

    @Override
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

    @Override
    public void chooseIsland() {
        if (!this.player.doIHaveAnHammer() && this.player.getLunarShard() >= 1 && (Referee.getWorld().getIsland(0).isIn(nameFeat.Hammer))) {
            this.player.setCurrentIsland(0);
        } else if (this.player.getSolarShard() >= 1 &&  ((this.player.getLunarShard() == 0 && !player.doIHaveAnHammer() && Referee.getWorld().getIsland(1).isIn(nameFeat.HerbesFolles)) || (player.doIHaveAnHammer()&& Referee.getWorld().getIsland(1).isIn(nameFeat.Ancien)))) {
            this.player.setCurrentIsland(1);
        } else if ((this.player.getLunarShard() >= 6 && Referee.getWorld().getIsland(6).isIn(nameFeat.Pince)) ||
                (this.player.getLunarShard() >= 5 && this.player.getSolarShard() >= 5 && Referee.getWorld().getIsland(6).isIn(nameFeat.Hydre)) ||
                (this.player.getSolarShard() >= 6 && Referee.getWorld().getIsland(6).isIn(nameFeat.Enigme))) {
            this.player.setCurrentIsland(6);
        }else if(this.player.getSolarShard()>=5 && !Referee.getWorld().isEmpty(5)){
            this.player.setCurrentIsland(5);
        }else if(this.player.getLunarShard()>=5 && !Referee.getWorld().isEmpty(4)){//virer les && false quand les iles existeronts
            this.player.setCurrentIsland(4);//3,lune
        }else if(this.player.getSolarShard()>=2 && Referee.getWorld().getIsland(3).isIn(nameFeat.AilesGardienne) ||
                this.player.getSolarShard()>=3 && Referee.getWorld().getIsland(3).isIn(nameFeat.Minotaure)){
            this.player.setCurrentIsland(3);
        }else if(this.player.getLunarShard()>=2 && !Referee.getWorld().isEmpty(2)){
            this.player.setCurrentIsland(2);
        } else {
            this.player.setCurrentIsland(-1);
        }
    }

    @Override
    public int chooseFeat() {
        switch(this.player.getCurrentIsland()) {
            case 0:
                if(Referee.getWorld().affordableFeat(0,player,nameFeat.Hammer)) return 0;
                else if(Referee.getWorld().affordableFeat(0,player, nameFeat.Chest))return 1;
                else return -1;
            case 1:
                if(Referee.getWorld().affordableFeat(1,player,nameFeat.HerbesFolles) && !this.player.doIHaveAnHammer() && this.player.getLunarShard() == 0) return 1;
                else if(Referee.getWorld().affordableFeat(1,player,nameFeat.Ancien)) return 0;
                else return -1;
            case 2:
                if(Referee.getWorld().affordableFeat(2,player,nameFeat.SabotArgent))return 0;
                else if(Referee.getWorld().affordableFeat(2,player,nameFeat.Satyres))return 1;
                else return -1;
            case 3:
                if(Referee.getWorld().affordableFeat(3,player,nameFeat.AilesGardienne)) return 0;
                else if (Referee.getWorld().affordableFeat(3,player,nameFeat.Minotaure)) return 1;
                else return -1;
            case 4:
                if (Referee.getWorld().affordableFeat(4,player,nameFeat.Passeur)) return 0;
                else if(Referee.getWorld().affordableFeat(4,player,nameFeat.CasqueInvisibilite)) return 1;
                else return -1;
            case 5:
                if (Referee.getWorld().affordableFeat(5,player,nameFeat.Meduse)) return 0;
                else if(Referee.getWorld().affordableFeat(5,player,nameFeat.MiroirAbyssal)) return 1;
                else return -1;
            case 6:
                if (Referee.getWorld().affordableFeat(6,player,nameFeat.Hydre)) return 1;
                else if (Referee.getWorld().affordableFeat(6,player,nameFeat.Pince)) return 0;
                else if (Referee.getWorld().affordableFeat(6,player,nameFeat.Enigme)) return 2;
                else return -1;

        }
        return -1;
    }

    public String interestingKind(){
        Pool poolPdS =Referee.getForge().bestPoolWith(Player.SolarShard,this.player.getGold());
        Pool poolG=Referee.getForge().bestPoolWith(Player.GOLD,this.player.getGold());
        if(poolPdS!=null && poolPdS.getPrice()<=this.player.getGold() && !this.player.doIHaveAnHammer()) {
            return Player.SolarShard;
        }else if(poolG!=null && poolG.getPrice()<=this.player.getGold()) {
            return Player.GOLD;
        }
        return "nothing";
    }

    @Override
    public Dice chooseBestDice() {
        if(this.player.getMaxGold() -this.player.getGold() < 2) return this.player.getDice(0);
        return this.player.getDice(1);
    }

    @Override
    public void replay() {
        if(player.getHasReplayed()==false && player.getSolarShard()>=3 ||(player.getSolarShard()>=2 && player.getLunarShard()>=1)){
            player.setHasReplayed(true);
        }
    }
}
