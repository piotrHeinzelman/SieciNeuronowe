package heinzelman.pl.FilesAndData;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class FileRdr {
    int fileRowNum=3000; //59900;
    int Toffset=16;
    int Loffset=8;

    public int[] readInts_addClassin00( Boolean isTrain )  {

        int[] ints=null;
        String trainFS=null;
        String labelFS=null;


        String path="F:\\MSI\\Projekt\\Data\\";
        //String path="D:\\SieciNeuronowe\\Projekt\\Data\\";
        if ( isTrain ) {
            trainFS = path + "train-images-idx3-ubyte";
            labelFS = path + "train-labels-idx1-ubyte";
        } else {
            trainFS = path + "t10k-images-idx3-ubyte";
            labelFS = path + "t10k-labels-idx1-ubyte";
        }

        File trainF = new File( trainFS );
        try{
            // data
            InputStream TF = new FileInputStream( trainFS );
            TF.skip(Toffset);
            byte[] bytes = TF.readNBytes( fileRowNum*784 );//.readAllBytes();


            ints = new int[bytes.length];
            for ( int i=0;i<bytes.length;i++) {
                ints[i]=Byte.toUnsignedInt( bytes[i] );
            }

            // label
            InputStream LF = new FileInputStream( labelFS );
            LF.skip( Loffset );
            byte[] labels = LF.readNBytes( fileRowNum);
            for ( int i=0;i<fileRowNum;i++) {
                ints[i*784]=Byte.toUnsignedInt( labels[i] );
            }
        } catch ( Throwable t ) { System.out.println( t ); }
        return ints;
    }

}

