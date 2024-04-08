package heinzelman.pl.neurons;

public class FunReLU implements Fun {
    @Override
    public Double Fx(Double x) {
        return (x>0) ? x : 0 ;
    }

    @Override
    public Double dFdu(Double y) {
        return (y>0) ? 1.0 : 0.0;
    }

}
