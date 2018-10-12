package DiceForge;

public class Player {
    private int honour;
    private int PdL;
    private int PdS;
    private int gold;
    private int maxPdL=6;
    private int maxPdS=6;
    private int maxGold=12;

    public Player(){
        this.honour=0;
        this.PdL=0;
        this.PdS=0;
        this.gold=0;
    }
    public int getHonour(){
        return this.honour;
    }
    public int getPdL(){
        return this.PdL;
    }
    public int getPdS(){
        return this.PdS;
    }
    public int getGold(){
        return this.gold;
    }
    public void addHonour(int honour){
        this.honour+=honour;
    }
    public void addPdL(int PdL){
        if(this.PdL+PdL<=maxPdL){
            this.PdL+=PdL;
        }else {
            this.PdL=6;
        }
    }
    public void addPdS(int PdS){
        if(this.PdS+PdS<=maxPdS){
            this.PdS+=PdS;
        }else {
            this.PdS=6;
        }
    }
    public void addGold(int gold){
        if(this.gold+gold<=maxGold){
            this.gold+=gold;
        }else {
            this.gold=12;
        }
    }
    public void removePdL(int PdL){
        if(this.PdL-PdL>=0){
            this.PdL-=PdL;
        }else{
            this.PdL=0;
        }
    }
    public void removePdS(int PdS){
        if(this.PdS-PdS>=0){
            this.PdS-=PdS;
        }else {
            this.PdS=0;
        }
    }
    public void removeGold(int gold){
        if(this.gold-gold>=0){
            this.gold-=gold;
        }else{
            this.gold=0;
        }
    }
}
