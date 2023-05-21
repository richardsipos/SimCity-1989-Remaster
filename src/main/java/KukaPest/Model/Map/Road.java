package KukaPest.Model.Map;

public class Road extends Constructable implements java.io.Serializable {

    Environment formerEnvironment;
    public Road(Environment environment){

        super(10, 1);
        this.formerEnvironment = environment;
    }

    public Environment getFormerEnvironment() {
        return this.formerEnvironment;
    }
}
