package heinzelman.pl.neurons;

public class Teacher {

    public Double[] updateSfromTeacher( Double[] Z ){
        return new Double[]{ 1.0-Z[0], 0.0-Z[1] };
    }

}
