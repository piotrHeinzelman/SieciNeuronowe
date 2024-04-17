package heinzelman.pl.FilesAndData;

public class Tools {
    private int[] myData;
    private final char[] table={'.',',','-','~',':',';','=','!','*','#','$','@'};

    public Tools() {
        FileRdr FR = new FileRdr();
        this.myData = FR.readInts_addClassin00();
    }


    public int[][] getXatI( int k ){
        k=k*784;
        int[][] out=new int[28][28];
        for (int i=0;i<28;i++){
            for (int j=0;j<28;j++) {
                out[i][j] = myData[ k+ (i*28) +j ];
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

    public void printTab( int[][] X ){
        for (int i=0;i<28;i++){
            printRow( X[i] );
        }
    }
    public void printRow( int[] numbers ){
        StringBuffer out = new StringBuffer();
        for ( int i=0;i<numbers.length;i++ ){
            out.append( table [ numbers[i]/22 ] );
        }
        System.out.println( out.toString() );
        //return out.toString();
    }

}
