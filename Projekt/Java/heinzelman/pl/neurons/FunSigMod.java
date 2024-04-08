package heinzelman.pl.neurons;

public class FunSigMod implements Fun {
    @Override
    public Double Fx(Double x) {
        return 1/(1+Math.exp( -1*x )) ;
    }

    @Override
    public Double dFdu(Double y) {
        return (1-y)*y;
    }
}
