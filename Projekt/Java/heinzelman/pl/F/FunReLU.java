package heinzelman.pl.F;

public class FunReLU implements Fun {
    @Override
    public Double Fy(Double y) {
        return (y >0) ? y : 0 ;
    }

    @Override
    public Double dFdz(Double Z) {
        return (Z >0) ? 1.0 : 0.0;
    }

}
