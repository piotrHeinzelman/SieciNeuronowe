package heinzelman.pl.FilesAndData;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.stream.IntStream;

class fileRdrTest {

    @Test
    void readData() {

    }




    @Test
    public void testFileRdr()  {
        byte[] labelaData=new byte[1000];
        int[] ints;
        String trainFS = "D:\\SieciNeuronowe\\Projekt\\Data\\train-images-idx3-ubyte";
        File trainF = new File( trainFS );
        try{
            InputStream FR = new FileInputStream( trainFS );
            int skip=0;
            FR.read( labelaData , skip , 1000 );
            for ( int i=0;i< labelaData.length;i++) {
                System.out.println( Byte.toUnsignedInt( labelaData[i] ) );
            }
/*
            InputStream insputStream = new FileInputStream( trainF );
            for (int i=0; i<10; i++ ) {
                System.out.println(insputStream.read());
            }

 */
            //labelaData = insputStream.readAllBytes();
            //ints=new int[ labelaData.length ];
            //for (int i=0;i<900;i++) {
            //    System.out.println( labelaData[i] );
            //}
            //insputStream.close();
        } catch (Throwable t) { System.out.println( t ); }
    }










    @Test
    public void testFileRdr2()  {
        byte[] labelaData;
        String trainFS = "D:\\SieciNeuronowe\\Projekt\\Data\\train-images-idx3-ubyte";
        File trainF = new File( trainFS );
        try{
            InputStream insputStream = new FileInputStream( trainF );
            for (int i=0;i<900;i++) {
                System.out.println( insputStream.read() );
            }
            insputStream.close();
        } catch (Throwable t) { System.out.println( t ); }
    }

    public void pringByte( byte b ){
        System.out.println( b );
    }


}