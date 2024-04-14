package heinzelman.pl;

import heinzelman.pl.FilesAndData.FileRdr;
import heinzelman.pl.FilesAndData.Tools;
import heinzelman.pl.F.*;
import heinzelman.pl.neurons.Layer;
import heinzelman.pl.neurons.Net;
import heinzelman.pl.neurons.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class project {
    public static void main(String[] args) {
        Pro pro = new Pro();
        pro.run();
    }
}


class Pro{
    public Pro() {
    }

    public void run(){
        Random rand = new Random();
        FileRdr rdr = new FileRdr();
        Fun sigmod = new FunSigMod();

        if (false) { // read data
            byte[] X = rdr.readX(2);
            new Tools().printX(X);
        }

        if ( true ) { // TURN OFF BUILD NET !
            List<Double[]>data0= new ArrayList<>();
                          data0.add( new Double[]{  1.0 ,-1.0 }  );
                          data0.add( new Double[]{  1.0 , 1.0 }  );
                          data0.add( new Double[]{ -1.0 , 1.0 }  );
            Layer layer0=new Layer( 2, 3,   sigmod , data0 );

            List<Double[]>data1= new ArrayList<>();
            data1.add( new Double[]{  1.0 , -1.0,  1.0 } );
            data1.add( new Double[]{ -1.0 ,  1.0, -1.0 } );

            Layer layer1=new Layer(  3, 2,  sigmod , data1);

            //Layer layer0 = new Layer(28 * 28, 28 * 28, new FunSigMod());
            //Layer layer1 = new Layer(28 * 28, 3, new FunSigMod());

            Net net = new Net();
            net.addNextLayer( layer0 );
            net.addNextLayer( layer1 );
            net.addTeacherForLast( new Teacher());

            Double[] X_ = new Double[]{  1.0, 2.0 };
            net.calcucateOneCycle( X_ );

        }
    }


    /*
    public void runEntr(){
        // FORWARD layer 0:
        Layer layer0 = new Layer( 2, 3, new FunSigMod() );
        layer0.setIneuronWags(0, new Double[]{  1.0, -1.0 } );
        layer0.setIneuronWags(1, new Double[]{  1.0,  1.0 } );
        layer0.setIneuronWags(2, new Double[]{ -1.0,  1.0 } );

        Double[] inputX = { 1.0, 2.0 };
        layer0.setX( inputX );
        layer0.calcU();
        layer0.calcZ();

        Double[] layer0Y = layer0.getY(); // layer0Y = layet1X


        // FORWARD layer 1:
        Double[] layer1X = layer0Y;

        Layer layer1 = new Layer( 3, 1, new FunSigMod_dF_one_FOR_crossEntropy());
        layer1.setIneuronWags(0, new Double[]{  1.0, -1.0,  1.0 } );

        layer1.setX( layer1X );
        layer1.calcU();
        layer1.calcZ();


        // BACKPROPAGATION layer 1:
        Double[] S_Y = layer1.calcDeltaError_S_Y( new Double[]{ 1.0 } ); // OCZEKIWANA
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

        System.out.println( "Error on exit:" + D_for_EXIT[0] );
        System.out.println( layer0 );
        System.out.println( layer1 );
    }
    */

    /*
    public void runNormalLayer(){
        System.out.println( "start" );

        // FORWARD layer 0:
        Layer layer0 = new Layer( 2, 3, new FunSigMod() );
        layer0.setIneuronWags(0, new Double[]{  1.0, -1.0 } );
        layer0.setIneuronWags(1, new Double[]{  1.0,  1.0 } );
        layer0.setIneuronWags(2, new Double[]{ -1.0,  1.0 } );

        Double[] inputX = { 1.0, 2.0 };
        layer0.setX( inputX );
        layer0.calcU();
        layer0.calcZ();

        Double[] layer0Y = layer0.getY(); // layer0Y = layet1X


            // FORWARD layer 1:
            Double[] layer1X = layer0Y;

            Layer layer1 = new Layer( 3, 2, new FunSigMod() );
            layer1.setIneuronWags(0, new Double[]{  1.0, -1.0,  1.0 } );
            layer1.setIneuronWags(1, new Double[]{ -1.0,  1.0, -1.0 } );

            layer1.setX( layer1X );
            layer1.calcU();
            layer1.calcZ();


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
}




/*

updateFilter
dL/dF = Conv (Input X, Loss gradient dL/dO - from next layer)
Fupdated = F-a*dL/dF


dL/dX = Full Conv ( 180deg rotated Filter F, loss gradient ) )
dL/dX = dL/dO dla następnej warstwy !


 */