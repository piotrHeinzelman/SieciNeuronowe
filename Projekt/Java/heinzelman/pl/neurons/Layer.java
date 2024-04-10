package heinzelman.pl.neurons;

import heinzelman.pl.FilesAndData.LabelAndData;

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
    private Layer nextLayer = null;
    private Layer prevLayer = null;
    private int pattern;


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
    public void setPattern(int pattern) { this.pattern = pattern; }

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












     public Double Forward( Double[] data  ){
        Double error;
        this.setX( data );
        this.calcU();
        this.calcY();
        Double[] layer0Y = this.getY();
        if ( this.nextLayer!=null ){
            error = this.nextLayer.Forward( layer0Y );

        } else {
            Double[] patternAry=new Double[10];
            for ( int j=0;j<10;j++ ){ patternAry[j] = ( j==pattern ) ? 1.0 : 0.0 ; }

            Double[] calcDeltaError_S_Y( patternAry )

            Double[] D_for_EXIT = layer0.getS_is_dForPrevoiusLayer();
        }


        Double[] S_Y = layer1.calcDeltaError_S_Y( Pattern );

        this.calcD( S_Y );
        this.calcAndUpdateW();
        this.calcS();
        error = 0.0;
            for ( int i=0;i<S_Y.length;i++ ){
                error+=( S_Y[i]*S_Y[i] );
            }
        return error; // poprawić - zwrócić długość wketora !
    }




                 // layer0Y = layet1X


                // FORWARD layer 1:
                Double[] layer1X = layer0Y;

                Layer layer1 = new Layer( 3, 2, new FunSigMod() );
                layer1.setIneuronWags(0, new Double[]{  1.0, -1.0,  1.0 } );
                layer1.setIneuronWags(1, new Double[]{ -1.0,  1.0, -1.0 } );

                layer1.setX( layer1X );
                layer1.calcU();
                layer1.calcY();


                // BACKPROPAGATION layer 1:
                Double[] S_Y = layer1.calcDeltaError_S_Y( new Double[]{ 1.0, 0.0 } ); // OCZEKIWANA
                layer1.calcD( S_Y );
                layer1.calcAndUpdateW();
                layer1.calcS();
                Double[] D_for_layer0 = layer1.getS_is_dForPrevoiusLayer();
                //System.out.println( layer1 );

                // BACKPROPAGATION layer 0:
                layer0.calcD( D_for_layer0 );
                layer0.calcAndUpdateW();
                layer0.calcS();
                Double[] D_for_EXIT = layer0.getS_is_dForPrevoiusLayer();

                //System.out.println( "Error on exit:" + D_for_EXIT[0] );
                //System.out.println( layer0 );
                //System.out.println( layer1 );
                }