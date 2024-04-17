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
    private Double[] S_Z;
    private Double[] S_ZxFprim;
    private Double[] SforPrevLayer;

    private Double wspUcz = 0.1;
    private Layer nextLayer = null;
    private Layer prevLayer = null;
    private Teacher teacher = null;
 
    // WList may by null
    public Layer( int Xsize, int Zsize, Fun F , List<Double[]> WList ) {
        this.Xsize=Xsize;
        this.Zsize=Zsize;
        this.F = F;

        this.X = new Double[ Xsize ];
        this.Z = new Double[ Zsize ];
        this.S_Z = new Double[ Zsize ];
        this.S_ZxFprim = new Double[ Zsize ];
        this.SforPrevLayer = new Double[ Xsize ];

        this.myNeurons = new Neuron[ Zsize ];
        for ( int i=0; i<Zsize; i++ ){
            if ( WList!=null ) { myNeurons[ i ] = new Neuron( Xsize, this , WList.get(i) ); }
            else               { myNeurons[ i ] = new Neuron( Xsize, this , null ); }
        }
    }


    public Double[] calcZ(){
        for ( int j=0; j<Zsize; j++ ) {  // j dla Zsize i dla Xsize
            Double Yj = myNeurons[j].calcXW(); // przed fun.
            Z[j] = F.Fy( Yj );            // Zj wyjscie
        }
        return Z;
    }


    public Double[] updateSfromNextLayer(){
        if ( nextLayer!=null ){
            S_Z =nextLayer.getSforPrevLayer();
        } else {
            S_Z =teacher.updateSfromTeacher( Z );
        }
        return S_Z;
    }

    public void updateS_ZxFPrim(){
        for (int j=0;j<Zsize;j++){
            S_ZxFprim[j]=( S_Z[j] ) * F.dFdz( Z[j] );
        }
    }


    public Double[] calcOutSj(){
        for (int i=0;i<Xsize;i++){
            SforPrevLayer[i]=0.0;
        }
        for (int j=0;j<Zsize;j++){
            myNeurons[j].calcOutSj( S_ZxFprim[j] );
        }
        print( SforPrevLayer );
        return SforPrevLayer;
    }

    public void updateWmyNeu(){
        for ( int j=0;j<Zsize;j++ ){
            myNeurons[j].updateW( S_ZxFprim[j] );
            System.out.println( myNeurons[j] );
        }
    }

    public void Softmax_normalizeMyZ(){
        Double sum=0.0;
        for (int j=0;j<Zsize;j++){
            Z[j]=Math.exp(Z[j]);
            sum+=Z[j];
        }
        for (int j=0;j<Zsize;j++){
            Z[j]=Z[j]/sum;
        }
    }

















    public Double[] getX() { return X; }
    public Double[] getZ() { return Z; }
    public Double[] getSforPrevLayer() { return SforPrevLayer; }
    public Fun getF() { return F; }

    public Double getWspUcz() { return wspUcz; }

    public void setX(Double[] X ) { this.X = X; }
    public void setNextLayer(Layer nextLayer) { this.nextLayer = nextLayer; }
    public void setPrevLayer(Layer prevLayer) { this.prevLayer = prevLayer; }

    public void setTeacher(Teacher teacher ) { this.teacher = teacher; }






    @Override
    public String toString() {
        String out=new String();
            out += "Layer Type: "+F.getClass().getSimpleName()+", X[" ; for ( int i=0;i<X.length;i++){ out += ""+X[i]+", "; } out +=")\n";
            out += "Z: ("; for ( int i=0;i<Z.length;i++){ out += ""+Z[i]+", "; } out +=")\n";
            out += "s: ("; for (int i = 0; i< Z.length; i++){ out += ""+ Z[i]+", "; } out +=")\n";

            for ( Neuron n  : myNeurons ){ out += n.toString()+"\n"; }
        return out;
    }

    public void print(Double[] ary) {
        String out=new String();
        for( Double d : ary ){
            out += ", "+d;
        }
        System.out.println( out );
    }

}
