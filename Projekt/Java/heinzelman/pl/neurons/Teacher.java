package heinzelman.pl.neurons;

import heinzelman.pl.FilesAndData.Tools;

public class Teacher {

    private Net net;
    private Tools tools;
    private Double ZZ = 0.0;
    private Double ErrorOfEpoch=0.0;
    private int epocNum = 0;
    private Double startE=null;

    public Teacher() {}

    public Teacher( Net net ) {
        this.net = net;
        net.addTeacherForLast( this );
        this.tools = new Tools( true ); // train data
    }

    public void teachOneEpoch(){
        //System.out.println( " Epoka:"+epocNum );

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
        if (startE==null ) { startE=ErrorOfEpoch; }
        //System.out.println( startE + "Epoch error: " + 100*100*((startE-ErrorOfEpoch)/startE) );

        ErrorOfEpoch=0.0;
        epocNum++;
    }


    public Double[] updateSfromTeacher( Double[] Z ){

        Double [] Zn = Softmax_normalizeMyZ(Z);
        int C = (int) Math.round(ZZ);



        Double[] S = new Double[10];
        for ( int i=0;i<10;i++ ){
            if ( i!=C ) { S[i]= ( 0.0-Zn[i] )  ;  continue; }
             S[i]=1.0-Zn[C];
        }

        // calcutate error :

        for (int i=0;i<10;i++) {
            Double e = S[i]-Z[i];
            ErrorOfEpoch += (e * e);
        }
        //System.out.println(C);
        //Tools.printRowStatic( Z );
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

    public void test(){
        Tools test = new Tools( false ); // test
        int n=0;
        Double[] XasRow = new Double[784];
        Double accuracy=0.0;
        while (true){
            n++;
            Double[][] nextX = test.getNextX();
            if (nextX==null) break;
            int classOfX = test.getClassOfX( nextX );
                for ( int i=0;i<28;i++ ) {
                    for (int j=0;j<28;j++) {
                        XasRow[28*i+j] = nextX[i][j];
                    }
                };
            Double[] Result = Softmax_normalizeMyZ ( net.calcucateOneCycle(XasRow));
            Double val=Result[0]; int ind=0;
            for (int k=0;k<10;k++){
                if (Result[k]>val){ ind=k; val=Result[k]; }
            }
            if ( classOfX == ind ) { accuracy++; };
        }
        System.out.println( "Trafość: " + (accuracy*100)/n + "%" );
    }


}
