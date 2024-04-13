package heinzelman.pl.F;

public class FunSigMod_dF_one_FOR_crossEntropy implements Fun {
    @Override
    public Double Fy(Double y) {
        return 1/(1+Math.exp( -1* y)) ;
    }

    @Override
    public Double dFdz(Double Z) {
        return 1.0;
    }
}
