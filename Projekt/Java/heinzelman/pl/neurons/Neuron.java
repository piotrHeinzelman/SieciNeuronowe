package heinzelman.pl.neurons;

import java.util.Random;


public class Neuron {

    private int Xsize;
    private Double[] W;
    private Layer parent;

    @Deprecated // for Tet ONLY !
    public Neuron( int Xsize , Layer parent, Double[] startW ) {
        this.Xsize=Xsize;
        this.W = new Double[Xsize];
        for ( int i=0;i<startW.length;i++ ){
            W[i]=startW[i];
        }
    }

    public Neuron( int Xsize , Layer parent ) {
        this.Xsize=Xsize;
        this.W = new Double[Xsize];
        initRandomWags();
    }

    public void initRandomWags(){
        Random r = new Random();
        for (int i=0;i<Xsize;i++) {
            W[i] = r.nextDouble();
        }
    }




    public Double calcXW(){
        Double[] X = parent.getX();
        Double XW=0.0;
        for (int i=0;i<Xsize;i++) {
            XW = XW + ( W[i]*X[i]);
        }
    return XW;
    }

    public void calcOutSj( Double S_ZxFprim ){
        Double EW=0.0;
        for (int i=0;i<Xsize;i++) {
            parent.getX()[i] += W[i]*S_ZxFprim;
        }
    }




//    public Double[] getW() { return W; }
//    public Double getWi(int i){ return W[i]; }
    public void updateW ( Double S_ZxFprim ){
        for (int i=0;i<Xsize;i++) {
            W[i] += parent.getWspUcz() * parent.getX()[i] * S_ZxFprim;
        }
    }





    @Override
    public String toString() {
        StringBuffer out = new StringBuffer();
        for ( int i=0;i<Xsize;i++){
            out.append ( " "+W[i]+", ");
        }
        return out.toString();
    }

}
