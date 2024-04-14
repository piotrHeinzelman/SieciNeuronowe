package heinzelman.pl.F;

public class FunSoftmax implements Fun {
    @Override
    public Double Fy(Double y) {
        return y;
    }

    @Override
    public Double dFdz(Double Z) {
        return 1.0;
    }

}
