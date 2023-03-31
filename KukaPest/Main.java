import Model.Game;
import Model.Helper.Building;
import Model.Helper.Coordinates;
import View.MainWindow;

public class Main {
    public static void main(String[] args){
        new MainWindow();
        Game currentGame = new Game("KukaPest");
        currentGame.build(Building.STADIUM,new Coordinates(1,1));
        currentGame.build(Building.STADIUM,new Coordinates(2,2)); //ez teszt ez nem kell menjen mert foglalt
        currentGame.build(Building.SCHOOL,new Coordinates(1,10));
        currentGame.build(Building.UNIVERSITY,new Coordinates(10,1));
        currentGame.build(Building.RESIDENTIAL,new Coordinates(14,14));currentGame.build(Building.STADIUM,new Coordinates(1,1));
        currentGame.build(Building.INDUSTRY,new Coordinates(10,6));



        currentGame.build(Building.ROAD,new Coordinates(5,5));
        currentGame.build(Building.POLE,new Coordinates(8,8));
        currentGame.build(Building.SCHOOL,new Coordinates(10,10));

    }
}
