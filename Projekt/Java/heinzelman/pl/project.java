package heinzelman.pl;

import heinzelman.pl.F.Fun;
import heinzelman.pl.F.FunSigMod;
import heinzelman.pl.F.FunSoftmax;
import heinzelman.pl.FilesAndData.Tools;
import heinzelman.pl.Filter.Convolution;
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
        Tools tools = new Tools();
         Fun sigmod = new FunSigMod();
        Fun softmax = new FunSoftmax();

        if ( true ) {
            // test without Convolution, only FullConnected
            Layer layer0 = new Layer( 784, 40 , sigmod , null ); // layer 0
            Layer layer1 = new Layer( 40,  10 , softmax, null );


            Net net = new Net();
            net.addNextLayer( layer0 );
            net.addNextLayer( layer1 );
            Teacher teacher = new Teacher( net );

            for (int i=0;i<30;i++) {
                teacher.teachOneEpoch();
                teacher.teachOneEpoch();
                teacher.teachOneEpoch();
                teacher.teachOneEpoch();
                teacher.teachOneEpoch();
                teacher.teachOneEpoch();
                teacher.teachOneEpoch();
                teacher.teachOneEpoch();
                teacher.teachOneEpoch();
                teacher.teachOneEpoch();
                teacher.teachOneEpoch();
                teacher.teachOneEpoch();
                teacher.teachOneEpoch();
            }

        }



        if ( false ) { // read data // show data :- )
            for (int i=0;i<1;i++) {

                Double [][] X = tools.getNextX();
                System.out.println( "Class: " + tools.getClassOfX( X ) );
            //    tools.printTab( X );

                Double[][] filter = new Double[][]{
                        { 1.0 , 0.0 , 1.0 },
                        { 1.0 , 0.0 , 1.0 },
                        { 1.0 , 0.0 , 1.0 },
                };

            //    tools.printTab( filter );
                Double[][] A = Convolution.conv( X, filter, 0, 1);
            //    tools.printTab( A );
                  A = Convolution.conv( A, filter, 0, 1);
                  A = Convolution.conv( A, filter, 0, 1);
            //    tools.printTab( A );

                Double[][] B = A = Convolution.conv( filter, A, 22, 1);
                tools.printTab( B );
            }
        }

        if ( false ) { // TURN OFF BUILD NET !
            List<Double[]>data0= new ArrayList<>();
                          data0.add( new Double[]{  1.0 ,-1.0 }  );
                          data0.add( new Double[]{  1.0 , 1.0 }  );
                          data0.add( new Double[]{ -1.0 , 1.0 }  );
            Layer layer0=new Layer( 2, 3,   sigmod , data0 );

            List<Double[]>data1= new ArrayList<>();
            data1.add( new Double[]{  1.0 , -1.0,  1.0 } );
            data1.add( new Double[]{ -1.0 ,  1.0, -1.0 } );

            Layer layer1=new Layer(  3, 2,  softmax , data1 );

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
}
