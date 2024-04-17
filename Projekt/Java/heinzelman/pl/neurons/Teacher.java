package heinzelman.pl.neurons;

import heinzelman.pl.FilesAndData.Tools;

public class Teacher {

    private Net net;
    private Tools tools;
    private Double ZZ = 0.0;
    private Double ErrorOfEpoch=0.0;
    private int epocNum = 0;

    public Teacher() {}

    public Teacher( Net net ) {
        this.net = net;
        net.addTeacherForLast( this );
        this.tools = new Tools();
    }

    public void teachOneEpoch(){
        System.out.println( " Epoka:"+epocNum );


        Double[][] X = null;
        Double[] XasRow = new Double[784];

        ErrorOfEpoch=0.0;
        tools.refreshDataSet();

        while ( true ) {
            X = tools.getNextX(); //System.out.println(X[0][0]);
            if (X==null) break;
            ZZ = X[0][0];
            for ( int i=0;i<28;i++ ) {
                for (int j=0;j<28;j++) {
                    XasRow[28*i+j] = X[i][j];
                }
            };
            net.calcucateOneCycle(XasRow);
        }
        System.out.println( "Epoch error: " + ErrorOfEpoch );
        ErrorOfEpoch=0.0;
        epocNum++;
    }


    public Double[] updateSfromTeacher( Double[] Z ){
        int j = (int) Math.round(ZZ);
        Double[] out = new Double[10];

        for ( int i=0;i<10;i++ ){
            out[i]=0.0-Z[j];
        }

        out[j]=1.0-Z[j];

                // calcutate error :
                for (int n=0;n<10;n++){
                    Double e = out[n]-Z[n];
                    ErrorOfEpoch+=(e*e);
                }

        return out;
    }

}
