package heinzelman.pl.neurons;

import java.util.ArrayList;


public class Layer {

    private Neuron[] myNeurons;
    private Fun F;

    private Double[] X;
    private Double[] S;

    private Double [] U;
    private Double [] d;

    private Double[] Y;

    private Double[] E; // input

    private Double wspUcz = 0.1;


    public Layer( int Xsize, int Ysize, Fun F ) {
        this.F = F;
        this.X = new Double[ Xsize ];
        this.S = new Double[ Xsize ];

        this.U = new Double[ Ysize ];
        this.Y = new Double[ Ysize ];
        this.E = new Double[ Ysize ];
        this.d = new Double[ Ysize ];


        this.myNeurons = new Neuron[ Ysize ];
        for ( int i=0; i<Ysize; i++ ){
            Neuron n = new Neuron( Xsize );
            myNeurons[i]=n;
        }
    }

    public Double[] getY() { return Y; }
    public void setX( Double[] X ) { this.X = X; }

    public Double[] getS_is_dForPrevoiusLayer(){ return S; }
    public void setIneuronWags( int i , Double [] wags ){ myNeurons[i].setWags( wags ); }





    public void calcU(){
        for ( int i=0; i<U.length; i++ ) {
            U[i] = myNeurons[i].calcXW( X );
        }
    }

    public void calcY(){
        for ( int i=0; i<Y.length; i++ ) {
             Y[i] = F.Fx( U[i]);
        }
    }

    public Double[] calcDeltaError_S_Y( Double[] expactedS ){
        Double[] S_Y=new Double[ expactedS.length ];
        for ( int i=0; i<expactedS.length; i++ ) {
            S_Y[i] = expactedS[i]-Y[i];
        }
        return S_Y;
    }

    public void calcD( Double[] S_Y ){
        for ( int i=0; i<Y.length; i++ ) {
             d[i] = S_Y[i] * F.dFdu( Y[i] );
        }
    }

    public void calcAndUpdateW(){
        for ( int n=0; n<Y.length; n++ ) {
            Neuron neu = myNeurons[n];
            for ( int i=0; i<X.length; i++ ) {
                Double delta = wspUcz * d[n] * X[i];
                neu.updateWiBy( delta, i );
            }
        }
    }

    public void calcS(){
        for ( int i=0; i<X.length; i++ ) { S[i]=0.0;}
        for ( int n=0; n<Y.length; n++ ) {

            Neuron neu = myNeurons[n];
            for ( int i=0; i<X.length; i++ ) {
                S[i]+=d[n] * neu.getWi(i);
            }
        }
    }





    @Override
    public String toString() {
        String out=new String();
            out += "Layer Type: "+F.getClass().getSimpleName()+", X[" ; for ( int i=0;i<X.length;i++){ out += ""+X[i]+", "; } out +=")\n";
            out += "u: ("; for ( int i=0;i<U.length;i++){ out += ""+U[i]+", "; } out +=")\n";
            out += "y: ("; for ( int i=0;i<Y.length;i++){ out += ""+Y[i]+", "; } out +=")\n";
            out += "E: ("; for ( int i=0;i<E.length;i++){ out += ""+E[i]+", "; } out +=")\n";
            out += "d: ("; for ( int i=0;i<d.length;i++){ out += ""+d[i]+", "; } out +=")\n";
            out += "s: ("; for ( int i=0;i<S.length;i++){ out += ""+S[i]+", "; } out +=")\n";

            for ( Neuron n  : myNeurons ){ out += n.toString()+"\n"; }
        return out;
    }
}
