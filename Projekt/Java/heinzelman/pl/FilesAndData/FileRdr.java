package heinzelman.pl.FilesAndData;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class FileRdr {
    int fileRowNum=60;//60000;

    int Toffset=16;
    int Loffset=1;

    public int[] readInts_addClassin00()  {

        int[] ints=null;
        String trainFS = "D:\\SieciNeuronowe\\Projekt\\Data\\train-images-idx3-ubyte";
        String labelFS = "D:\\SieciNeuronowe\\Projekt\\Data\\t10k-labels-idx1-ubyte";

        File trainF = new File( trainFS );
        try{
            // data
            InputStream TF = new FileInputStream( trainFS );
            byte[] bytes = TF.readNBytes( ( fileRowNum*784)+Toffset );//.readAllBytes();


            ints = new int[bytes.length-Toffset];
            for ( int i=0;i<bytes.length-Toffset;i++) {
                ints[i]=Byte.toUnsignedInt( bytes[i+Toffset] );
            }

            // label
            InputStream LF = new FileInputStream( labelFS );
            byte[] labels = TF.readNBytes( fileRowNum );
            int len=ints.length/784;
            for ( int i=0;i<len;i++) {
            //    ints[i*784]=Byte.toUnsignedInt( labels[i+Loffset] );
            }
        } catch (Throwable t) { System.out.println( t ); }
        return ints;
    }





/*
    public FileRdr() {
        byte[] labelaData;
        String  trainFS="D:\\SieciNeuronowe\\Projekt\\Data\\train-images-idx3-ubyte";
        String  labelFS="D:\\SieciNeuronowe\\Projekt\\Data\\train-labels-idx1-ubyte";
        try {
            File trainF = new File( trainFS );
            InputStream insputStream = new FileInputStream( trainF );
            insputStream.skip(12);
            insputStream.read( trainData, 0, fileRowNum*784 );
            insputStream.close();

            File labelF = new File( labelFS );
            insputStream = new FileInputStream( labelF );
              labelaData = new byte[ fileRowNum ];
            insputStream.skip(8);
            insputStream.readNBytes( labelaData, 0, fileRowNum ); // magic numbers
            insputStream.close();

            for ( int i=0;i<fileRowNum;i++ ){
                trainData[i*784]=labelaData[i];
            }
        } catch ( Throwable th ){ System.out.println( th ); }
    }


    public byte[] readX( int i ) {
        offset=784*i;
        for (int j=0;j<784;j++) {
            emptyX[j] = trainData[ offset+j ];
        }
        return emptyX;
    }
*/

}

