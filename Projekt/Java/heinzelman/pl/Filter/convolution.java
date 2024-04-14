package heinzelman.pl.Filter;

public class convolution {

    public static Double[][] conv ( Double[][] data , Double[][] filter , int padding , int stride ) { // stride = step
        if ( padding>1 ){ data = makePadding( data, padding ); }

        int dim = 1+data[0].length-filter.length;
        int hSize=filter.length;
        Double[][] out=new Double[ (dim/stride) ][ (dim/stride) ];
        for ( int i=0;i<dim;i+=stride ){
            for ( int j=0;j<dim;j+=stride ){
                out[i][j]=mul( cutArray( data , i, j, hSize ) , filter );
            }
        }
        return out;
    }


    public static Double mul ( Double[][] data , Double [][] Filter ){
        Double out=0.0;
        int dim = Filter.length;
        for ( int i=0;i<dim;i++){
            for ( int j=0;j<dim;j++) {
                out += data[i][j]*Filter[i][j];
            }
        }

        return out;
    }


    public static Double[][] cutArray ( Double [][] data, int iOffset, int jOffset , int dim  ) {
        Double[][] out = new Double[ dim ][ dim ];
        for ( int i=(dim-1)/2;i<(dim+1)/2;i++ ){
            for ( int j=(dim-1)/2;j<(dim);j++ ){
                out[i][j]=data[ i + iOffset ][ j + jOffset ];
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
