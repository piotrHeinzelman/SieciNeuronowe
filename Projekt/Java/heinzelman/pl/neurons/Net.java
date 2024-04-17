package heinzelman.pl.neurons;


import java.util.ArrayList;

public class Net {

    private ArrayList<Layer> myLayers;

    public Net() {
        myLayers = new ArrayList<>();
    }

    public void addNextLayer ( Layer nextLater ) {
        Layer last = null;
        if ( myLayers.size()>0 ) {
            last = myLayers.get( myLayers.size()-1 );
            last.setNextLayer( nextLater );
            nextLater.setPrevLayer( last );
        }
        myLayers.add( nextLater );
    }
    public void addTeacherForLast( Teacher teacher ) {
        myLayers.get( myLayers.size()-1).setTeacher( teacher );
    }

    public Double calcucateOneCycle( Double[] X ){

        Double[] nextX=X;
        for (int i=0;i< myLayers.size();i++){
            Layer layer=myLayers.get(i);
            if ( nextX==null ) { layer.setX( X ); }
            else { layer.setX( nextX ); }
            nextX = layer.calcZ();
        }
        // if softmax
        if ( true ) { myLayers.get(myLayers.size()-1).Softmax_normalizeMyZ(); }


        Double[] S = null;
        // backprop
        for (int i=(myLayers.size()-1);i>=0 ;i--) {
            Layer layer = myLayers.get(i);
            layer.updateSfromNextLayer();
            layer.updateS_ZxFPrim();
            layer.updateWmyNeu();
            layer.calcOutSj();
        }
        return 0.0;
    }




    public void print(Double[] ary) {
        String out=new String();
        for( Double d : ary ){
            out += ", "+d;
        }
        System.out.println( out );
    }


}
