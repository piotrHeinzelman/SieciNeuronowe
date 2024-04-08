package heinzelman.pl.neurons;

import java.util.ArrayList;


public class Layer {

    private Neuron[] myNeurons;
    private Fun F;

    private Double[] X;
    private Double[] sigma;

    private Double [] U;
    private Double [] d;

    private Double[] Y;

    private Double[] E; // input


    public Layer( int Xsize, int Ysize, Fun F ) {
        this.F = F;
        this.X = new Double[ Xsize ];
        this.U = new Double[ Ysize ];
        this.Y = new Double[ Ysize ];
        this.E = new Double[ Ysize ];


        this.myNeurons = new Neuron[ Ysize ];
        for ( int i=0; i<Ysize; i++ ){
            Neuron n = new Neuron( this , Xsize );
            //if ( i==0 ) { n.setWags( new Double[]{  1.0, -1.0 } ); }
            //if ( i==1 ) { n.setWags( new Double[]{  1.0,  1.0 } ); }
            //if ( i==2 ) { n.setWags( new Double[]{ -1.0,  1.0 } ); }
            if ( i==0 ) { n.setWags( new Double[]{  1.0, -1.0,  1.0 } ); }
            if ( i==1 ) { n.setWags( new Double[]{ -1.0,  1.0, -1.0 } ); }
            myNeurons[i]=n;
        }
    }

    public void setX( Double[] X ) {
        this.X = X;
    }

    public void calcU(){
        for ( int i=0; i<U.length; i++ ) {
            this.U[i] = myNeurons[i].calcXW( this.X );
        }
    }

    public void calcY(){
        for ( int i=0; i<Y.length; i++ ) {
            this.Y[i] = F.Fx( U[i]);
        }
    }

    public void calcError( Double[] expactedS ){
        for ( int i=0; i<E.length; i++ ) {
            this.E[i] = expactedS[i]-Y[i];
        }
    }


    @Override
    public String toString() {
        String out=new String();
            out += "Layer Type: "+F.getClass().getSimpleName() +"\nx: (";
            for ( int i=0;i<X.length;i++){
                out += ""+X[i]+", ";
            }
            out +=")\n";

            out+="u: (";
            for ( int i=0;i<U.length;i++){
                out += ""+U[i]+", ";
            }
            out +=")\n";

            out+="y: (";
            for ( int i=0;i<Y.length;i++){
                out += ""+Y[i]+", ";
            }
            out +=")\n";

            out+="E: (";
            for ( int i=0;i<E.length;i++){
                out += ""+E[i]+", ";
            }
            out +=")\n";


        for ( Neuron n  : myNeurons ){
            out += n.toString()+"\n";
        }
        return out;
    }
}
