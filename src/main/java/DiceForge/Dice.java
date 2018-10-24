package DiceForge;
import DiceForge.Face.*;

import java.util.Random;

public class Dice{

	Face[] faces = new Face[6];
	private int roll; //numéro de face roulé

	public Dice(Face face1,Face face2,Face face3,Face face4,Face face5,Face face6){
		this.faces[0] = face1;
		this.faces[1] = face2;
		this.faces[2] = face3;
		this.faces[3] = face4;
		this.faces[4] = face5;
		this.faces[5] = face6;
		this.roll = 0;
	}

	/* Accessor */
	public Face getFace(int i){
		return this.faces[i];
	}

	/* Mutator */
	public void setFace(Face face,int i){
		this.faces[i] = face;
	}

	//Change le numéro de face roulé aléatoirement
	void rollDice(){ 
		Random r = new Random();
		this.roll = r.nextInt(6);
	}

	void giveReward(Player player){
		this.rollDice();
		this.getFace(roll).giveReward(player);
	}

	//Affichage du dé et ses faces
	public void toString(int dice){
		System.out.print("Dé "+dice+" :");
		for(int i=0;i<6;i++){
			if(i==this.roll){ System.out.print("\u001B[33m"+this.getFace(i).getReward() + "\u001B[0m "); } //affiche en couleur la face roulé
			else {System.out.print(this.getFace(i).getReward() + " "); }
		}
		System.out.print("\n");
	}

}