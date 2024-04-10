package heinzelman.pl.FilesAndData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;

// x = [ x0 = class num, x1 - (char)[0,1]
// char = ushort



public class FileRdr {
    int fileRowNum=60;//60000;
    byte[] trainData=new byte[fileRowNum*784+14];
    byte[] emptyX=new byte[784];
    int offset=0;


    public FileRdr() {
        byte[] labelaData;
        String  trainDataFileName="D:\\SieciNeuronowe\\Projekt\\Data\\train-images.idx3-ubyte";
        String labelsDataFileName="D:\\SieciNeuronowe\\Projekt\\Data\\train-labels.idx1-ubyte";
        try {
            File file = new File(trainDataFileName );
            InputStream insputStream = new FileInputStream(file);
            insputStream.read( trainData , 14 , fileRowNum*784  );
            insputStream.close();

            File fileLabels = new File( labelsDataFileName );
            insputStream = new FileInputStream(fileLabels);
              labelaData = new byte[ fileRowNum ];
            insputStream.read( labelaData , 0 , fileRowNum  );
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


}

