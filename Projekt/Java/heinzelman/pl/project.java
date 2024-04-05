package heinzelman.pl;

import heinzelman.pl.neurons.Layer;
import heinzelman.pl.neurons.Neuron;

public class project {
    public static void main(String[] args) {
        Pro pro = new Pro();
        pro.run();
    }
}


class Pro{
    public Pro() {
    }

    public void run(){
        System.out.println( "start" );

        Layer layer0 = new Layer( 2 ,  2  );
        System.out.println( layer0 );
    }
}




/*

updateFilter
dL/dF = Conv (Input X, Loss gradient dL/dO - from next layer)
Fupdated = F-a*dL/dF


dL/dX = Full Conv ( 180deg rotated Filter F, loss gradient ) )
dL/dX = dL/dO dla następnej warstwy !


 */