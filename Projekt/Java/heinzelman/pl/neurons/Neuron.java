package heinzelman.pl.neurons;

import java.util.ArrayList;
import java.util.Random;


public class Neuron {

    private int Xsize;
    private Double[] W;
    private Layer myLayer;


    public Neuron( Layer parentLayer, int Xsize ) {
        this.myLayer=parentLayer;
        this.Xsize=Xsize;
        this.W = new Double[Xsize];
        initWags();
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


    public void initWags(){
        Random r = new Random();
        for (int i=0;i<Xsize;i++) {
            W[i] = r.nextDouble();
        }
    }


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
