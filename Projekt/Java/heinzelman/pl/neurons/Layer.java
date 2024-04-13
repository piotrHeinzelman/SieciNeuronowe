package heinzelman.pl.neurons;

import heinzelman.pl.F.Fun;

import java.util.List;

public class Layer {

    private int Xsize;
    private int Zsize;

    private Neuron[] myNeurons;
    private Fun F;

    private Double[] X;
    //private Double[] Y;
    private Double[] Z;
    private Double[] S;
    private Double[] S_ZxFprim;
    private Double[] SprevLayer;

    private Double wspUcz = 0.1;
    private Layer nextLayer = null;
    private Layer prevLayer = null;
    private Teacher teacher = null;


    public Layer( int Xsize, int Zsize, Fun F , Layer prevLayer, Layer nextLayer ) {
        this.Xsize=Xsize;
        this.Zsize=Zsize;
        this.F = F;
        if ( prevLayer!=null ) this.prevLayer=prevLayer;
        if ( nextLayer!=null ) this.nextLayer=nextLayer;


        this.X = new Double[ Xsize ];
        //this.Y = new Double[ Zsize ];
        this.Z = new Double[ Zsize ];
        this.S = new Double[ Zsize ];
        this.S_ZxFprim = new Double[ Zsize ];
        this.SprevLayer = new Double[ Zsize ];


        this.myNeurons = new Neuron[ Zsize ];
        for ( int i=0; i<Zsize; i++ ){
            myNeurons[ i ] = new Neuron( Xsize, this );
        }
    }

    @Deprecated // for test only !
    public Layer( int Xsize, int Zsize, Fun F , Layer prevLayer, Layer nextLayer, List<Double[]> WList ) {
        this.Xsize=Xsize;
        this.Zsize=Zsize;
        this.F = F;
        this.prevLayer=prevLayer;
        this.nextLayer=nextLayer;

        this.X = new Double[ Xsize ];
        this.Z = new Double[ Zsize ];
        this.S = new Double[ Zsize ];
        this.S_ZxFprim = new Double[ Zsize ];

        this.myNeurons = new Neuron[ Zsize ];
        for ( int i=0; i<Zsize; i++ ){
            myNeurons[ i ] = new Neuron( Xsize, this , WList.get(i) );
        }
    }


    public void calcZ(){
        for ( int j=0; j<Zsize; j++ ) {  // j dla Zsize i dla Xsize
            Double Yj = myNeurons[j].calcXW(); // przed fun.
            Z[j] = F.Fy( Yj );            // Zj wyjscie
        }
    }


    public void updateSfromNextLayer(){
        if ( nextLayer!=null ){
            for (int j=0;j<Zsize;j++){
                S[j]=nextLayer.getSprevLayer()[j];
            }
        } teacher.updateSfromTeacher( Z );
    }

    public void updateS_ZxFPrim(){
        for (int j=0;j<Zsize;j++){
            S_ZxFprim[j]=S[j] * F.dFdz( Z[j] );
        }
    }


    public void calcOutSj(){
        for (int j=0;j<Zsize;j++){
            SprevLayer[j]=0.0;
        }
        for (int j=0;j<Zsize;j++){
            myNeurons[j].calcOutSj( S_ZxFprim[j] );
        }
    }

    public void updateWmyNeu(){
        for ( int j=0;j<Zsize;j++ ){
            myNeurons[j].updateW( S_ZxFprim[j] );
        }
    }


















    public Double[] getX() { return X; }
    public Double[] getSprevLayer() { return SprevLayer; }

    public Double getWspUcz() { return wspUcz; }

    public void setX(Double[] X ) { this.X = X; }
    public void setTeacher( Teacher teacher ) { this.teacher = teacher; }






    @Override
    public String toString() {
        String out=new String();
            out += "Layer Type: "+F.getClass().getSimpleName()+", X[" ; for ( int i=0;i<X.length;i++){ out += ""+X[i]+", "; } out +=")\n";
            out += "Z: ("; for ( int i=0;i<Z.length;i++){ out += ""+Z[i]+", "; } out +=")\n";
            out += "s: ("; for (int i = 0; i< Z.length; i++){ out += ""+ Z[i]+", "; } out +=")\n";

            for ( Neuron n  : myNeurons ){ out += n.toString()+"\n"; }
        return out;
    }
}








/*



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


 */