package DiceForge;
import DiceForge.Face.*;

import java.util.ArrayList;
import java.util.Random;

public class Forge{
    private ArrayList<Pool> forge=new ArrayList<>();

    public Forge(Referee R){//il faut ajouter un random selon le nombre de joueurs ou on enlève 4-nbJoueurs de faces dans chaque bassin aléatoirement
        ArrayList<Face> _3G=new ArrayList<>();
        ArrayList<Face> _1PdL=new ArrayList<>();
        ArrayList<Face> _4G=new ArrayList<>();
        ArrayList<Face> _1PdS=new ArrayList<>();
        ArrayList<Face> FacePool4G=new ArrayList<>();//toutes les faces pas encore codée
        ArrayList<Face> FacePool5G=new ArrayList<>();//toutes les faces pas encore codée
        ArrayList<Face> _2PdL=new ArrayList<>();
        ArrayList<Face> _2PdS=new ArrayList<>();
        ArrayList<Face> _3Honour=new ArrayList<>();
        ArrayList<Face> FacePool12G=new ArrayList<>();//toutes les faces pas encore codée


        for(int i=0;i<4;i++){
            _3G.add(new FaceCombinationAND(3,0,0,0));
            _1PdL.add(new FaceCombinationAND(0,1,0,0));
            _4G.add(new FaceCombinationAND(4,0,0,0));
            _1PdS.add(new FaceCombinationAND(0,0,1,0));
            _2PdL.add(new FaceCombinationAND(0,2,0,0));
            _2PdS.add(new FaceCombinationAND(0,0,2,0));
            _3Honour.add(new FaceCombinationAND(0,0,0,3));
            FacePool5G.add(new FaceCombinationAND(3,0,0,2));//ici un OR à mettre

        }
        FacePool4G.add(new FaceCombinationAND(6,0,0,0));
        FacePool4G.add(new FaceCombinationAND(2,1,0,0));
        FacePool4G.add(new FaceCombinationAND(0,0,1,1));
        FacePool4G.add(new FaceCombinationAND(3,0,0,0));//ici un OR mise de 4 faces pour éviter toute possibilités de problèmes

        FacePool12G.add(new FaceCombinationAND(0,0,0,4));
        FacePool12G.add(new FaceCombinationAND(1,1,1,1));
        FacePool12G.add(new FaceCombinationAND(0,2,0,2));
        FacePool12G.add(new FaceCombinationAND(2,2,2,0));//ici un OR à mettre

        if(R.getNumberPlayer()==2){
            Random r = new Random();
            for(int i=0;i<2;i++){
                _3G.remove(r.nextInt(4-i));
                _1PdL.remove(r.nextInt(4-i));
                _4G.remove(r.nextInt(4-i));
                _1PdS.remove(r.nextInt(4-i));
                _2PdL.remove(r.nextInt(4-i));
                _2PdS.remove(r.nextInt(4-i));
                _3Honour.remove(r.nextInt(4-i));
                FacePool4G.remove(r.nextInt(4-i));
                FacePool5G.remove(r.nextInt(4-i));
                FacePool12G.remove(r.nextInt(4-i));
            }
        }

        Pool pool2G_3G=new Pool(2,_3G);
        Pool pool2G_1PdL=new Pool(2,_1PdL);
        Pool pool3G_4G=new Pool(3,_4G);
        Pool pool3G_1PdS=new Pool(3,_1PdS);
        Pool pool4G=new Pool(4,FacePool4G);//pas codée encore
        Pool pool5G=new Pool(5,FacePool5G);//pas codée encore
        Pool pool6G_2PdL=new Pool(6,_2PdL);
        Pool pool8G_2PdS=new Pool(8,_2PdS);
        Pool pool8G_3Honour=new Pool(8,_3Honour);
        Pool pool12G=new Pool(12,FacePool12G);//pas codée encore

        this.forge.add(pool12G);
        this.forge.add(pool8G_3Honour);
        this.forge.add(pool8G_2PdS);
        this.forge.add(pool6G_2PdL);
        this.forge.add(pool5G);
        this.forge.add(pool4G);
        this.forge.add(pool3G_1PdS);
        this.forge.add(pool3G_4G);
        this.forge.add(pool2G_1PdL);
        this.forge.add(pool2G_3G);









    }

    /* Accessor */
    public Pool getPool(int n){
        return this.forge.get(n);
    }

    public int size(){return this.forge.size();}

    public Pool lowestPoolNotEmptyContaining(String kind){//if kind=="" then return all kind of pool
        for(Pool p:forge){
            if(p.isEmpty()==false && p.kindOfPool(kind)){
                return p;
            }
        }
        return null;
    }

    public Pool affordablePoolWith(String kind,int gold){
        for(Pool p:forge){
            if(p.isEmpty()==false && p.kindOfPool(kind) && p.getPrice()<=gold){
                return p;
            }
        }
        return null;
    }


    public int isNumber(Pool pool){
        for(int i=0;i<forge.size();i++){
            if(forge.get(i)==pool){
                return i;
            }
        }
        return 0;//test devrait retourner -1
    }
}
