package View;


import Model.Game;
import Model.Helper.Building;
import Model.Helper.Coordinates;
import Model.Map.*;
import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BoardGUI extends JPanel implements MouseListener, MouseMotionListener {

    static final int DELAY = 1000;
    Image background, grass, dirt, water, road, uni, res_zone, res_zone_1, res_zone_2, res_zone_3, res_zone_max, res_zone_level2,
            res_zone_level2_2,res_zone_level3,res_zone_level3_2,pp, school, police, stadium, industrial, power_pole,
            service, industrial_1, industrial_2, service_1, service_2;
    Board board;
    boolean build = false;
    boolean destroy = false;
    boolean upgrade = false;
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
        addMouseMotionListener(this);

        //szükséges képek felvétele a kirajzoláshoz

        background = new ImageIcon("KukaPest/Assets/gatyakukarestart.jpg").getImage();
        grass = new ImageIcon("KukaPest/Assets/grass2.png").getImage();
        dirt = new ImageIcon("KukaPest/Assets/dirt2.png").getImage();
        water = new ImageIcon("KukaPest/Assets/water_2.png").getImage();
        road = new ImageIcon("KukaPest/Assets/road.png").getImage();
        uni = new ImageIcon("KukaPest/Assets/university_2.png").getImage();
        res_zone = new ImageIcon("KukaPest/Assets/residental.png").getImage();
        res_zone_1 = new ImageIcon("KukaPest/Assets/residental_1.png").getImage();
        res_zone_2 = new ImageIcon("KukaPest/Assets/residental_2.png").getImage();
        res_zone_3 = new ImageIcon("KukaPest/Assets/residental_3.png").getImage();
        res_zone_max = new ImageIcon("KukaPest/Assets/residental_max.png").getImage();
        res_zone_level2 = new ImageIcon("KukaPest/Assets/residental_level2_1.png").getImage();
        res_zone_level2_2 = new ImageIcon("KukaPest/Assets/residental_level2_2.png").getImage();
        res_zone_level3 = new ImageIcon("KukaPest/Assets/residental_level2_3.png").getImage();
        res_zone_level3_2 = new ImageIcon("KukaPest/Assets/residental_level3_2.png").getImage();
        pp = new ImageIcon("KukaPest/Assets/pp.png").getImage();
        school = new ImageIcon("KukaPest/Assets/school.png").getImage();
        police = new ImageIcon("KukaPest/Assets/police.png").getImage();
        stadium = new ImageIcon("KukaPest/Assets/stadium.png").getImage();
        industrial = new ImageIcon("KukaPest/Assets/industrial.png").getImage();
        industrial_1 = new ImageIcon("KukaPest/Assets/industrial_1.png").getImage();
        industrial_2 = new ImageIcon("KukaPest/Assets/industrial_2.png").getImage();
        power_pole = new ImageIcon("KukaPest/Assets/power_pole.png").getImage();
        service = new ImageIcon("KukaPest/Assets/service.png").getImage();
        service_1 = new ImageIcon("KukaPest/Assets/service_1.png").getImage();
        service_2 = new ImageIcon("KukaPest/Assets/service_2.png").getImage();

        map = game.getMap();

        Dimension dim = new Dimension(48 * 25, 27 * 25);
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
                    g2.drawImage(grass, j * 25,i * 25,25,25, null);
                else if (map[i][j] instanceof Dirt)
                    g2.drawImage(dirt, j * 25,i * 25,25,25, null);
                else if (map[i][j] instanceof Water)
                    g2.drawImage(water, j *25,i * 25,25,25, null);
                else if (map[i][j] instanceof Road)
                    g2.drawImage(road, j * board.getCellSide(), i * board.getCellSide(),25,25, null);
                else if (map[i][j] instanceof Police)
                    g2.drawImage(police, j * board.getCellSide(), i * board.getCellSide(),100,50, null);
                else if (map[i][j] instanceof Stadium)
                    g2.drawImage(stadium, j * board.getCellSide(), i * board.getCellSide(),100,100, null);
                else if (map[i][j] instanceof University)
                    g2.drawImage(uni, j * board.getCellSide(),i * board.getCellSide(),100,100, null);
                else if (map[i][j] instanceof ResidentialZone) {
                    // System.out.println(((ResidentialZone) map[i][j]).getCurrentCapacity());
                    if(((ResidentialZone) map[i][j]).getLevel() == 1){
                        if (((ResidentialZone) map[i][j]).getCurrentCapacity() == 0) {
                            g2.drawImage(res_zone, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }
                        if (((ResidentialZone) map[i][j]).getCurrentCapacity() <= 2 && ((ResidentialZone) map[i][j]).getCurrentCapacity() >0) {
                            g2.drawImage(res_zone_1, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }
                        if (((ResidentialZone) map[i][j]).getCurrentCapacity() <= 4 && ((ResidentialZone) map[i][j]).getCurrentCapacity() > 2) {
                            g2.drawImage(res_zone_2, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }
                        if (((ResidentialZone) map[i][j]).getCurrentCapacity() <= 6 && ((ResidentialZone) map[i][j]).getCurrentCapacity() > 4) {
                            g2.drawImage(res_zone_3, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }
                        if (((ResidentialZone) map[i][j]).getCurrentCapacity() <= 10 && ((ResidentialZone) map[i][j]).getCurrentCapacity() > 6) {
                            g2.drawImage(res_zone_max, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }

                    }
                    if(((ResidentialZone) map[i][j]).getLevel() == 2){
                        if (((ResidentialZone) map[i][j]).getCurrentCapacity() <= 16 && ((ResidentialZone) map[i][j]).getCurrentCapacity() > 0) {
                            g2.drawImage(res_zone_level2, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }
                        if (((ResidentialZone) map[i][j]).getCurrentCapacity() <= 25 && ((ResidentialZone) map[i][j]).getCurrentCapacity() > 16) {
                            g2.drawImage(res_zone_level2_2, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }

                    }
                    if(((ResidentialZone) map[i][j]).getLevel() == 3){
                        if (((ResidentialZone) map[i][j]).getCurrentCapacity() <= 32 && ((ResidentialZone) map[i][j]).getCurrentCapacity() > 0) {
                            g2.drawImage(res_zone_level3, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }
                        if (((ResidentialZone) map[i][j]).getCurrentCapacity() <= 50 && ((ResidentialZone) map[i][j]).getCurrentCapacity() > 32) {
                            g2.drawImage(res_zone_level3_2, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }

                    }

                }
                else if (map[i][j] instanceof PowerPlant)
                    g2.drawImage(pp, j * board.getCellSide(), i * board.getCellSide(),100,100, null);
                else if (map[i][j] instanceof School)
                    g2.drawImage(school, j * board.getCellSide(), i * board.getCellSide(),100,50, null);
                else if (map[i][j] instanceof IndustrialZone) {
                    if (((IndustrialZone) map[i][j]).getCurrentCapacity() == 0) {
                        g2.drawImage(industrial, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                    }
                    else if (((IndustrialZone) map[i][j]).getCurrentCapacity() <= 4 && ((IndustrialZone) map[i][j]).getCurrentCapacity() > 0) {
                        g2.drawImage(industrial_1, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                    }
                    else{
                        g2.drawImage(industrial_2, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                    }
                    //g2.drawImage(industrial, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                }
                else if (map[i][j] instanceof Pole)
                    g2.drawImage(power_pole, j * board.getCellSide(), i * board.getCellSide(),25,25, null);
                else if (map[i][j] instanceof ServiceZone){
                    if (((ServiceZone) map[i][j]).getCurrentCapacity() == 0) {
                        g2.drawImage(service, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                    }
                    else if (((ServiceZone) map[i][j]).getCurrentCapacity() <= 4 && ((ServiceZone) map[i][j]).getCurrentCapacity() > 0) {
                        g2.drawImage(service_1, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                    }
                    else{
                        g2.drawImage(service_2, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                    }
                }
                    //g2.drawImage(service, j * board.getCellSide(), i * board.getCellSide(),50,50, null);

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
        if(upgrade){
            game.upgrade(new Coordinates(row,col));
            repaint();
        }

        repaint();

    }

    @Override
    public void mouseDragged(MouseEvent e){
        if(selectedBuilding == Building.ROAD && build){
            int x = e.getPoint().x;
            int col = (x  / board.getCellSide());
            int y = e.getPoint().y;
            int row = (y  / board.getCellSide());

            game.build(selectedBuilding, new Coordinates(row, col));
            repaint();
        }
    }
    @Override
    public void mouseMoved(MouseEvent e){

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
