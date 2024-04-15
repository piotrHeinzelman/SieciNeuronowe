package heinzelman.pl.Filter;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static heinzelman.pl.Filter.convolution.conv;
import static org.junit.jupiter.api.Assertions.*;

public class convolutionTest {

    @Test
    public void makePaddingTest(){
        Double[][] data = new Double[][]{ { 1.0,0.0,2.0 },
                                          { 3.0,1.0,2.0 },
                                          { 1.0,1.0,0.0 }
                                                         };

        Double[][] filter = new Double[][]
        {
            {  1.0, 0.0 },
            { -1.0, 1.0 }
        };

        Double[][] filter2 = new Double[][]
        {
            {  2.0, 0.0 },
            {  1.0,-1.0 }
        };


        print( data );
        Double[][] pad = convolution.makePadding( data ,  3 );
        print( pad );

        print ( convolution.conv ( data , filter , 1 , 1 ));
        print ( convolution.conv ( data , filter2 , 1 , 1 ));

        Assertions.assertTrue(true);
    }








        public void print(Double[][] ary) {
            String out=new String();
            for( int y=0;y<ary.length;y++ ){
                for ( int x=0;x<ary[0].length;x++ ){
                    out +=" "+ ary[x][y];
                }
                out+="\n";
            }
            System.out.println( out );
        }



}