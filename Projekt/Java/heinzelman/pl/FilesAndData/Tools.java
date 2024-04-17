package heinzelman.pl.FilesAndData;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tools {
    private Random rnd = new Random();
    private int[] myData;
    private final char[] table={'.',',','-','~',':',';','=','!','*','#','$','@'};
    private List<Integer> notUsedX = null;

    public Tools() {
        FileRdr FR = new FileRdr();
        this.myData = FR.readInts_addClassin00();
        this.notUsedX = new ArrayList<>(this.myData.length/784);
        for (int i=0;i<this.myData.length/784;i++){
            notUsedX.add(i);
        }
    }

    public int getClassOfX( Double [][]data){
        return (int) Math.round( data[0][0]);
    }


    public Double[][] getNextX(){
        if ( notUsedX.size()==0 ) return null;
        int i = rnd.nextInt( notUsedX.size() );
        //System.out.println( " I: " + i + " of " + notUsedX.size() );
        i = notUsedX.remove( i );

        return getXatI( i );
    }


    public Double[][] getXatI( int k ){
        k=k*784;
        Double[][] out=new Double[28][28];
        for (int i=0;i<28;i++){
            for (int j=0;j<28;j++) {
                out[i][j] = Double.valueOf(myData[ k+ (i*28) +j ]);
            }
        }
        // out[0]=0;
        return out;
    }

    public int getClassAtI( int i ){
        return myData[i*784];
    }

    public void printX ( int offset ){
        System.out.println( "...   Class: "+myData[ offset*784 ]+ "   ..." );
        for (int i=0;i<28;i++){
            StringBuffer out=new StringBuffer(28);
            for (int j=0;j<28;j++){
                int val = myData[(i*28)+j +(offset*784)];
                out.append( table [  (  val )/22 ] );
            }
            System.out.println( out.toString() );
        }
    }

    public void printTab( Double[][] X ){
        for (int i=0;i< X.length;i++){
            printRow( X[i] );
        }
    }
    public void printRow( Double[] numbers ){
        StringBuffer out = new StringBuffer();
        for ( int i=0;i<numbers.length;i++ ){
            out.append( table [ (int) Math.round( numbers[i]/22 )%12 ] );
        }
        System.out.println( out.toString() );
        //return out.toString();
    }

}
