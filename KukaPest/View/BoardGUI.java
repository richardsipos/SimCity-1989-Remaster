package View;


import Model.Game;
import Model.Helper.Building;
import Model.Helper.Coordinates;
import Model.Map.*;
import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BoardGUI extends JPanel implements MouseListener {

    static final int DELAY = 1000;
    Image background, grass, dirt, water, road, uni, res_zone, pp, school, police, stadium,industrial, power_pole;
    Board board;
    boolean build = false;
    boolean destroy = false;
    Building selectedBuilding;
    Tile[][] map;
    private Game game;
    //private MenuWindow menuWindow = new MenuWindow();



    /**
     *Constructor for the BoardGUI class, here we add the images for the game and here the time starts
     * @param fieldX map width
     * @param fieldY height
     */
    public BoardGUI(int fieldX, int fieldY, String cityname){
        //menuWindow.setVisible(false);
        game = new Game(cityname);

        this.board = new Board(fieldX,fieldY);

        addMouseListener(this);

        //szükséges képek felvétele a kirajzoláshoz

        background = new ImageIcon("KukaPest/Assets/gatyakukarestart.jpg").getImage();
        grass = new ImageIcon("KukaPest/Assets/grass2.png").getImage();
        dirt = new ImageIcon("KukaPest/Assets/dirt2.png").getImage();
        water = new ImageIcon("KukaPest/Assets/water_2.png").getImage();
        road = new ImageIcon("KukaPest/Assets/road.png").getImage();
        uni = new ImageIcon("KukaPest/Assets/university_2.png").getImage();
        res_zone = new ImageIcon("KukaPest/Assets/house.png").getImage();
        pp = new ImageIcon("KukaPest/Assets/pp.png").getImage();
        school = new ImageIcon("KukaPest/Assets/school.png").getImage();
        police = new ImageIcon("KukaPest/Assets/police.png").getImage();
        stadium = new ImageIcon("KukaPest/Assets/stadium.png").getImage();
        industrial = new ImageIcon("KukaPest/Assets/industrial.png").getImage();
        power_pole = new ImageIcon("KukaPest/Assets/power_pole.png").getImage();

        map = game.getMap();

        Dimension dim = new Dimension(59 * 20, 31 * 20);
        setPreferredSize(dim);
        setMaximumSize(dim);
        setSize(dim);
    }


    /**
     *Drawing the map
     * @param g
     */
    @Override
    protected void  paintComponent(Graphics g){
        super.paintChildren(g);
        Graphics2D g2 = (Graphics2D)g;


        for(int i = 0; i < board.getBoardY(); i++){
            g2.setColor(Color.black);

            for(int j = 0; j < board.getBoardX(); j++){
                if(map[i][j] instanceof Grass)
                    g2.drawImage(grass, j * 20,i * 20,20,20, null);
                else if (map[i][j] instanceof Dirt)
                    g2.drawImage(dirt, j * 20,i * 20,20,20, null);
                else if (map[i][j] instanceof Water)
                    g2.drawImage(water, j * 20,i * 20,20,20, null);
                else if (map[i][j] instanceof Road)
                    g2.drawImage(road, j * board.getCellSide(), i * board.getCellSide(),20,20, null);
                else if (map[i][j] instanceof Police)
                    g2.drawImage(police, j * board.getCellSide(), i * board.getCellSide(),40,80, null);
                else if (map[i][j] instanceof Stadium)
                    g2.drawImage(stadium, j * board.getCellSide(), i * board.getCellSide(),80,80, null);
                else if (map[i][j] instanceof University)
                    g2.drawImage(uni, j * board.getCellSide(),i * board.getCellSide(),80,80, null);
                else if (map[i][j] instanceof ResidentialZone)
                    g2.drawImage(res_zone, j * board.getCellSide(),i * board.getCellSide(),40,40, null);
                else if (map[i][j] instanceof PowerPlant)
                    g2.drawImage(pp, j * board.getCellSide(), i * board.getCellSide(),80,80, null);
                else if (map[i][j] instanceof School)
                    g2.drawImage(school, j * board.getCellSide(), i * board.getCellSide(),40,80, null);
                else if (map[i][j] instanceof IndustrialZone)
                    g2.drawImage(industrial, j * board.getCellSide(), i * board.getCellSide(),40,40, null);
                else if (map[i][j] instanceof Pole)
                    g2.drawImage(power_pole, j * board.getCellSide(), i * board.getCellSide(),20,20, null);

            }
        }
    }


    /**
     *Click monitoring and coordinate determination
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getPoint().x;
        int col = (x  / board.getCellSide());
        int y = e.getPoint().y;
        int row = (y  / board.getCellSide());

        if(build){
            game.build(selectedBuilding, new Coordinates(row, col));
        }

        if(destroy){
            boolean candestroy = game.destroy(new Coordinates(row,col));
            if(candestroy == false){
                System.out.println("Nem sikerült a rombolás");
            }
            //destroy = false;
            repaint();
        }

        repaint();

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }



    /**
     *Getter for the game
     */
    public Game getGame() {
        return game;
    }
}
