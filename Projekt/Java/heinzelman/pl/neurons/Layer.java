package heinzelman.pl.neurons;

import java.util.ArrayList;

public class Layer {

    private ArrayList<Neuron> myNeurons;

    public Layer( int neuronLength , int xLength ) {
        this.myNeurons = new ArrayList<>( neuronLength );
        for ( int i=0;i<neuronLength;i++ ){
            Neuron n = new Neuron(this);
                   n.initWags( xLength );
            myNeurons.add( n );
        }
    }

    @Override
    public String toString() {
        String out=new String();
        for ( Neuron n  : myNeurons ){
            out += n.toString();
        }
        return out;
    }
}
