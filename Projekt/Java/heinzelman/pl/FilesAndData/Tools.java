package heinzelman.pl.FilesAndData;

public class Tools {
    private final char[] table={'"','@','$','#','*','!','=',';',':','~','-',',','.',',','-','~',':',';','=','!','*','#','$','@','"'};

    public void printX ( byte[] X ){
        for (int i=0;i<28;i++){
            StringBuffer out=new StringBuffer(28);
            for (int j=0;j<28;j++){
                out.append( table [  (  X[(i*28)+j]+128  )/11 ] );
                //out.append( X[i*28+j] );
            }
            System.out.println( out.toString() );
        }
        System.out.println( "Class: "+X[0] );

    }

}
