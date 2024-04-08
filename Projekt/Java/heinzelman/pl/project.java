package heinzelman.pl;

import heinzelman.pl.neurons.FunSigMod;
import heinzelman.pl.neurons.FunSigMod_dF_one_FOR_crossEntropy;
import heinzelman.pl.neurons.Layer;

import java.io.File;
import java.io.FileReader;
import java.net.FileNameMap;
import java.util.ArrayList;
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
        ArrayList<String> allFiles = new ArrayList<>(60000);
        String root="C:/TEMP/DATA/10/";

        String root0=root+"0/";
        String root1=root+"1/";
        String root2=root+"2/";

        Random rand = new Random();

        ArrayList<File> files;


        String[] listOfFile = new File( root0 ).list();
        for (int i=0;i<listOfFile.length;i++){
            allFiles.add( "0_"+listOfFile[i] );
        }
        listOfFile = new File( root1 ).list();
        for (int i=0;i<listOfFile.length;i++){
            allFiles.add( "1_"+listOfFile[i] );
        }
        listOfFile = new File( root2 ).list();
        for (int i=0;i<listOfFile.length;i++){
            allFiles.add( "2_"+listOfFile[i] );
        }

        while( allFiles.size()>0 ){
            int i = rand.nextInt(allFiles.size());
            String s = allFiles.remove(i);
            System.out.println( s );

        }


        System.out.println( listOfFile[0]);
    }


    public void runFirstCycle(){

        try {
            // input:
            FileReader fr = new FileReader("");
        } catch ( Throwable th ){ System.out.println( th ); }
        Double[] inputX = { 1.0, 2.0 };


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

    public void runEntr(){
        // FORWARD layer 0:
        Layer layer0 = new Layer( 2, 3, new FunSigMod() );
        layer0.setIneuronWags(0, new Double[]{  1.0, -1.0 } );
        layer0.setIneuronWags(1, new Double[]{  1.0,  1.0 } );
        layer0.setIneuronWags(2, new Double[]{ -1.0,  1.0 } );

        Double[] inputX = { 1.0, 2.0 };
        layer0.setX( inputX );
        layer0.calcU();
        layer0.calcY();

        Double[] layer0Y = layer0.getY(); // layer0Y = layet1X


        // FORWARD layer 1:
        Double[] layer1X = layer0Y;

        Layer layer1 = new Layer( 3, 1, new FunSigMod_dF_one_FOR_crossEntropy());
        layer1.setIneuronWags(0, new Double[]{  1.0, -1.0,  1.0 } );

        layer1.setX( layer1X );
        layer1.calcU();
        layer1.calcY();


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
        layer0.calcY();

        Double[] layer0Y = layer0.getY(); // layer0Y = layet1X


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

        System.out.println( "Error on exit:" + D_for_EXIT[0] );
        System.out.println( layer0 );
        System.out.println( layer1 );
    }
}




/*

updateFilter
dL/dF = Conv (Input X, Loss gradient dL/dO - from next layer)
Fupdated = F-a*dL/dF


dL/dX = Full Conv ( 180deg rotated Filter F, loss gradient ) )
dL/dX = dL/dO dla następnej warstwy !


 */