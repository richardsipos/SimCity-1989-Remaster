package KukaPest.Model.Helper;

public enum EduLevel {
    BASIC, MID, HIGH;

    public int salaryModifier(){
        return switch (this){
            case BASIC -> 1;
            case MID -> 2;
            case HIGH -> 3;
        };
    }
}
