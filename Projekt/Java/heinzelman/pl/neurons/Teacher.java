package heinzelman.pl.neurons;

import heinzelman.pl.FilesAndData.Tools;

public class Teacher {

    private Net net;
    private Tools tools;
    private Double ZZ = 0.0;

    public Teacher() {}

    public Teacher( Net net ) {
        this.net = net;
        net.addTeacherForLast( this );
        this.tools = new Tools();
    }

    public void teachOneEpoch(){
        Double[][] X=null;
        Double ErrorOfEpoch=0.0;
        Double[] XasRow = new Double[784];

        while ( true ) {
            X = tools.getNextX(); //System.out.println(X[0][0]);
            if (X==null) break;
            ZZ = X[0][0];
            for ( int i=0;i<28;i++ ) {
                for (int j=0;j<28;j++) {
                XasRow[28*i+j] = X[i][j];
                }
            };
           net.calcucateOneCycle( XasRow );
        }
        //{
       //     System.out.println( X[0][0] );
        //}
        //Double[] X_ = new Double[]{  1.0, 2.0 };
        //net.calcucateOneCycle( X_ );

    }


    public Double[] updateSfromTeacher( Double[] Z ){
        return new Double[]{ 1.0-Z[0], 0.0-Z[1] };
    }

}
