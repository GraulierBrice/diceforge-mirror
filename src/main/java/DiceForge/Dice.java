package DiceForge;
import DiceForge.Face.*;

public class Dice{

	Face[] faces = new Face[6];
	private int roll;

	Dice(Face face1,Face face2,Face face3,Face face4,Face face5,Face face6){
		this.faces[0] = face1;
		this.faces[1] = face2;
		this.faces[2] = face3;
		this.faces[3] = face4;
		this.faces[4] = face5;
		this.faces[5] = face6;
		this.roll = 0;
	}

	void rollDice(){
		this.roll = (int)(Math.random()*5)];
	}

	Face getFace(int i){
		return this.faces[i];
	}

}