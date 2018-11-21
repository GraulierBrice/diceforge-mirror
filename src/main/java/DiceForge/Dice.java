package DiceForge;
import DiceForge.Face.*;

import java.util.Random;

public class Dice{

	Face[] faces = new Face[6];
	private int roll; //numéro de face roulé

	public Dice(Face... faces) {
        System.arraycopy(faces, 0, this.faces, 0, faces.length);
        this.roll=-1;
	}


	/* Accessor */
	public Face getFace(int i){return this.faces[i];}
	public Face getReward(){
		return this.faces[roll];
	}
	/* Mutator */
	public void setFace(Face face,int i){this.faces[i] = face;}

	public void giveReward(Player player){
		this.rollDice();
		if(this.faces[this.roll] instanceof FaceCombinationOR){
			this.faces[this.roll].giveRewardOR(player,player.strategy.chooseFaceBonus(this.faces[this.roll]));
		}else {
			this.faces[roll].giveReward(player);
		}
	}

	//Change le numéro de face roulé aléatoirement
	public void rollDice(){ 
		Random r = new Random();
		this.roll = r.nextInt(6);
	}



	//Affichage du dé et ses faces
	public void toString(int dice){
		System.out.print("    Dé "+dice+" :");
		for(int i=0;i<6;i++){
			//affiche en couleur la face roulé
            System.out.print(i == this.roll ? Announcer.ANSI_BOLD+Announcer.ANSI_YELLOW + this.getFace(i).getReward() +" "+ Announcer.ANSI_RESET : this.getFace(i).getReward() + " ");
		}
		System.out.print("\n");
	}
	
	public boolean diceNotFullWith(String kind){
		for(Face f:faces){
			if(!f.getKind().equals(kind)){
				return false;
			}
		}
		return true;
	}

	public int faceNotOfThisKind(String kind){
		for(int i=0;i<faces.length;i++){
			if(!faces[i].getKind().equals(kind) || (kind.equals("G") && faces[i].getReward().equals("1G"))){
				return i;
			}
		}
		return 0;
	}


}