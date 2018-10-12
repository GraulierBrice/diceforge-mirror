public class Player {
    private int honor;
    private int PdL;
    private int PdF;
    private int gold;

    public Player(){
        this.honor=0;
        this.PdL=0;
        this.PdF=0;
        this.gold=0;
    }
    public int getHonor(){
        return this.honor;
    }
    public int getPdL(){
        return this.PdL;
    }
    public int getPdF(){
        return this.PdF;
    }
    public int getGold(){
        return this.gold;
    }
    public void addHonor(int honor){
        this.honor+=honor;
    }
    public void addPdL(int PdL){
        if(this.PdL+PdL<=6){
            this.PdL+=PdL;
        }else {
            this.PdL=6;
        }
    }
    public void addPdF(int PdF){
        if(this.PdF+PdF<=6){
            this.PdF+=PdF;
        }else {
            this.PdF=6;
        }
    }
    public void addGold(int gold){
        if(this.gold+gold<=12){
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
    public void removePdF(int PdF){
        if(this.PdF-PdF>=0){
            this.PdF-=PdF;
        }else {
            this.PdF=0;
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
