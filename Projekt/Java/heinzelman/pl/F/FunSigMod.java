package heinzelman.pl.F;

public class FunSigMod implements Fun {
    @Override
    public Double Fy(Double y) {
        return 1/(1+Math.exp( -1* y)) ;
    }

    @Override
    public Double dFdz(Double Z) {
        return (1- Z)* Z;
    }
}
