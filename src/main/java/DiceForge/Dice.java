package DiceForge;
import DiceForge.Face.*;

import java.util.Random;


public class Dice{

	Face[] faces = new Face[6];
	private int roll;

	public Dice(Face face1,Face face2,Face face3,Face face4,Face face5,Face face6){
		this.faces[0] = face1;
		this.faces[1] = face2;
		this.faces[2] = face3;
		this.faces[3] = face4;
		this.faces[4] = face5;
		this.faces[5] = face6;
		this.roll = 0;
	}

	int rollDice(){
		Random r = new Random();
		this.roll = r.nextInt(6);
		return this.roll;
	}

	public Face getFace(int i){
		return this.faces[i];
	}

	public void toString(int dice){
		System.out.print("Dé "+dice+" :");
		for(int i=0;i<6;i++){
			System.out.print(this.getFace(i).getReward()+" ");
		}
		System.out.print("\n");
	}

	public void setFace(Face face,int i){
		this.faces[i] = face;
	}

}