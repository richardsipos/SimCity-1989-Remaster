package KukaPest.Model.Helper;

public class Coordinates implements java.io.Serializable{
    private int x;
    private int y;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Coordinates){
            if(this.x == ((Coordinates) obj).getHeight() && this.y == ((Coordinates) obj).getWidth()){
                return true;
            }else{
                return false;
            }

        }
        return false;
    }

    public Coordinates(int x, int y) {
        this.x =x;
        this.y = y;
    }

    public int getHeight() {
        return x;
    }

    public int getWidth() {
        return y;
    }


}