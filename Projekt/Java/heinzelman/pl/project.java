package heinzelman.pl;


public class project {

    public static void main(String[] args) {
        System.out.println( "?" );
    }
}


/*

updateFilter
dL/dF = Conv (Input X, Loss gradient dL/dO - from next layer)
Fupdated = F-a*dL/dF


dL/dX = Full Conv ( 180deg rotated Filter F, loss gradient ) )
dL/dX = dL/dO dla następnej warstwy !


 */