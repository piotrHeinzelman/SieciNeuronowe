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
            Tools tools = new Tools();
        Fun sigmod = new FunSigMod();
        Fun softmax = new FunSoftmax();

        if ( true ) { // read data
            for (int i=0;i<5;i++) {
                System.out.println(  );
                System.out.println( " ** próbka " + i + "  **" );
                System.out.println(  );
//      tools.printX(i);
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
