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
        System.out.println( "Epoch error: " + Math.round(ErrorOfEpoch) );
        System.out.println(  );
        ErrorOfEpoch=0.0;
        epocNum++;
    }


    public Double[] updateSfromTeacher( Double[] Z ){
        int C = (int) Math.round(ZZ);

        // calcutate error :
        Double [] Znorm = Softmax_normalizeMyZ ( Z );
        Double e = 1.0-Znorm[C];
        ErrorOfEpoch+=(e*e);

        Double[] S = new Double[10];
        for ( int i=0;i<10;i++ ){
            if ( i!=C ) { S[i]=-0.0001;/*(-1.0+Znorm[i]); */ continue; }
             S[C]=1.0-Znorm[C];
        }
        //Tools.printRowStatic( S );
        return S;
    }



    public Double[] Softmax_normalizeMyZ( Double[] Z ){
        Double[] out=new Double[Z.length];
        Double sum=0.0;
        for (int j=0;j<out.length;j++){
            out[j]=Math.exp(Z[j]);
            sum+=out[j];
        }
        for (int j=0;j<out.length;j++){
            out[j]=out[j]/sum;
        }
        return out;
    }


}
