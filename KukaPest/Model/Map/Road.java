package Model.Map;

public class Road extends Constructable{

    Environment formerEnvironment;
    public Road(Environment environment){

        super(10, 1);
        formerEnvironment = environment;
    }

    public Environment getFormerEnvironment() {
        return formerEnvironment;
    }
}
