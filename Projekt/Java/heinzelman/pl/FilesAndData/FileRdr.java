package heinzelman.pl.FilesAndData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;

// x = [ x0 = class num, x1 - (char)[0,1]
// char = ushort



public class FileRdr {
    byte[] trainData;
    byte[] emptyX=new byte[784];
    int offset=0;


    public FileRdr() {
        byte[] labelaData;
        String  trainDataFileName="D:\\SieciNeuronowe\\Projekt\\Data\\train-images.idx3-ubyte";
        String labelsDataFileName="D:\\SieciNeuronowe\\Projekt\\Data\\train-labels.idx1-ubyte";
        try {
            // input:
            //FileReader fr = new FileReader( trainDataFileName );
            //fr.read( trainData );
            //fr.close(); // char[]

            File file = new File(trainDataFileName );
            //Instantiate the input stread
            InputStream insputStream = new FileInputStream(file);
            long length = file.length();
            byte[] trainData = new byte[(int) length];

            insputStream.read( trainData );
            insputStream.close();

            File fileLabels = new File( labelsDataFileName );
            //Instantiate the input stread
                 insputStream = new FileInputStream(fileLabels);
                   labelaData = new byte[(int) fileLabels.length()];

            insputStream.read( labelaData );
            insputStream.close();

            for ( int i=0;i<labelaData.length;i++ ){
                trainData[i*784]=labelaData[i];
            }
        } catch ( Throwable th ){ System.out.println( th ); }
    }


    public byte[] readX( int i ) {
        offset=784*i;
        for (int j=0;j<784;j++) {
            emptyX[j]  = trainData[ offset+j ];
        }
        return emptyX;
    }



}

