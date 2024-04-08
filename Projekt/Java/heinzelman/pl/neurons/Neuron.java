package heinzelman.pl.neurons;

import java.util.Random;


public class Neuron {

    private int Xsize;
    private Double[] W;

    public Neuron( int Xsize ) {
        this.Xsize=Xsize;
        this.W = new Double[Xsize];
        initRandomWags();
    }


    public Double calcXW( Double[] X ){
        Double XW=0.0;
        for (int i=0;i<Xsize;i++) {
            XW =XW + ( W[i]*X[i]);
        }
    return XW;
    }


    public Double[] getW() {
        return W;
    }
    public Double getWi(int i){ return W[i]; }

    public void updateWiBy(Double delta, int i){
        W[i]+=delta;
    }

    public void initRandomWags(){
        Random r = new Random();
        for (int i=0;i<Xsize;i++) {
            W[i] = r.nextDouble();
        }
    }

    @Deprecated // only for test
    public void setWags( Double[] newWags ){
        for ( int i=0; i<Xsize; i++ ){
            W[i] = newWags[i];
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
