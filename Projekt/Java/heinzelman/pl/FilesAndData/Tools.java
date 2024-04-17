package heinzelman.pl.FilesAndData;

public class Tools {
    private int[] myData;
    private final char[] table={'.',',','-','~',':',';','=','!','*','#','$','@'};

    public Tools() {
        FileRdr FR = new FileRdr();
        this.myData = FR.readInts_addClassin00();
    }

    public void printX ( int offset ){
        for (int i=0;i<28;i++){
            StringBuffer out=new StringBuffer(28);
            for (int j=0;j<28;j++){
                out.append( table [  (  myData[(i*28)+j +(offset*784)] )/22 ] );
            }
            System.out.println( out.toString() );
        }
        System.out.println( "Class: "+myData[ offset*784 ] );
    }

}
