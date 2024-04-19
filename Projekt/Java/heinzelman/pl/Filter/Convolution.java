package heinzelman.pl.Filter;

public class Convolution {

    public static Double[][] conv ( Double[][] data , Double[][] filter , int padding , int stride ) { // stride = step
        if ( padding>1 ){ data = makePadding( data, padding ); }

        int D = data.length;
        int H = filter.length-1;
        int h = (H)/2;
        int d = D-H;

        Double [][] out= new Double[ d ][ d ];

        //     . i=0
        // |  : :  |         |
        //  0     H
        //  ..+i     ( i-h , i<D-h )    i=<0,d>  d=D-H
        //
        // H = FilterSize-1 !!!!
        // h = H/2

        for (int i=0;i<d;i++){
            for (int j=0;j<d;j++) {
                double sum=0.0;
                    for (int m=0;m<=H;m++){
                        for (int n=0;n<=H;n++) {
                            //System.out.println( "F["+m+"]["+n+"]"+ filter[m][n] + ", Data["+(m+i)+"]["+(n+j)+"]="+data[m+i][n+j]  );
                            sum+=filter[m][n]*data[m+i][n+j];
                        }
                    }
                out[j][i]= /*ReLU*/ (sum>0) ? sum : 0 ; // ReLU !!
            }
        }
        return out;
    }



    //data[0] = row[0] (0,1,2...)
    public static Double[][] makePadding ( Double [][] data, int padding  ) {

        int p=(padding-1)/2;
        int w=data.length;
        int h=data[0].length;
        Double[][] out = new Double[ w+padding-1 ][ h+padding-1];

        for ( int i=0;(i<w+padding-1);i++ ){
            for ( int j=0;j<(h+padding-1);j++ ){
                int si=i-p;
                int sj=j-p;
                if ( si<0 || sj<0 || si >= w || sj >=h ) { out[i][j]=0.0; }
                else { out[i][j]=data[si][sj]; }
            }
        }
        return out;
    }


}
