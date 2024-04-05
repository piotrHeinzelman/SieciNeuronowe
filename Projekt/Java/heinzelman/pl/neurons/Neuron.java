package heinzelman.pl.neurons;

import java.util.ArrayList;
import java.util.Random;


public class Neuron {

    private ArrayList<Double> wags = new ArrayList<>();
    private Layer myLayer;
    private ArrayList<Double> X;
    private int Xlen;
    private Double y;  // xi * wi -> uj  /  F(uj) -> y
    private Double u;


    public Neuron( Layer myLayer ) {
        this.myLayer=myLayer;
    }

    public ArrayList<Double> getWags() {
        return wags;
    }

    public void initWags( int Xlen ){
        this.Xlen = Xlen;
        wags = new ArrayList<>( Xlen );
        Random r = new Random();
        for (int i=0;i<this.Xlen;i++){
            wags.add( r.nextDouble() );
        }
    }

    @Override
    public String toString() {
        return "Neuron{" +
                "wags=" + wags +
                '}';
    }
}
