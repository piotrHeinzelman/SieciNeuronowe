package heinzelman.pl.neurons;


import java.util.ArrayList;

public class Net {

    private ArrayList<Layer> myLayers;

    public Net() {
        myLayers = new ArrayList<>();
    }

    public void addNextLayer ( Layer nextLater ) {
        Layer last = null;
        if ( myLayers.size()>0 ) {
            last = myLayers.get( myLayers.size()-1 );
            last.setNextLayer( nextLater );
            nextLater.setPrevLayer( last );
        }
        myLayers.add( nextLater );

    }
    public void addTeacherForLast( Teacher teacher ) {
        myLayers.get( myLayers.size()-1).setTeacher( teacher );
    }

    public Double calcucateOneCycle( Double[] X ){

        System.out.println( "X:" );
        Double[] nextX=null;
        for (int i=0;i< myLayers.size();i++){
            Layer layer=myLayers.get(i);
            if ( nextX==null ) { layer.setX( X ); }
            else { layer.setX( nextX ); }
            nextX = layer.calcZ();

            print( nextX );
        }
        System.out.println( " *** " );

        Double[] S = null;
        // backprop
        for (int i=(myLayers.size()-1);i>=0 ;i--) {
            Layer layer = myLayers.get(i);
            S = layer.updateSfromNextLayer();
                // print( S );
            layer.updateS_ZxFPrim();
            layer.updateWmyNeu();
            layer.calcOutSj();

        }
        return 0.0;
    }




    public void print(Double[] ary) {
        String out=new String();
        for( Double d : ary ){
            out += ", "+d;
        }
        System.out.println( out );
    }


}




/*



    public void runFirstCycle( LabelAndData labelAndData ){
        int Label=labelAndData.getLabel();
        Double[] inputX = labelAndData.getData();
        // Double[] inputX = { 1.0, 2.0 };


        // FORWARD layer 0:
        Layer layer0 = new Layer( 28*28, 28*28, new FunSigMod() );
        Layer layer1 = new Layer( 28*28, 3, new FunSigMod() );

        layer0.setX( inputX );
        layer0.calcU();
        layer0.calcY();

        Double[] layer0Y = layer0.getY(); // layer0Y = layet1X


        // FORWARD layer 1:
        Double[] layer1X = layer0Y;

        layer1.setX( layer1X );
        layer1.calcU();
        layer1.calcY();


        // BACKPROPAGATION layer 1:
        Double[] S_Y = layer1.calcDeltaError_S_Y( new Double[]{ 1.0, 0.0 } ); // OCZEKIWANA
        layer1.calcD( S_Y );
        layer1.calcAndUpdateW();
        layer1.calcS();
        Double[] D_for_layer0 = layer1.getS_is_dForPrevoiusLayer();


        // BACKPROPAGATION layer 0:
        layer0.calcD( D_for_layer0 );
        layer0.calcAndUpdateW();
        //layer0.calcS();
        //Double[] D_for_EXIT = layer0.getS_is_dForPrevoiusLayer();
    }


 */