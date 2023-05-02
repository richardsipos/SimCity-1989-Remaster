package KukaPest.View;


import KukaPest.Model.Game;
import KukaPest.Model.Helper.Building;
import KukaPest.Model.Helper.Coordinates;
import KukaPest.Model.Map.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class BoardGUI extends JPanel implements MouseListener, MouseMotionListener {

    static final int DELAY = 1000;
    Image background, grass, dirt, water, road, uni, res_zone, res_zone_1, res_zone_2, res_zone_3, res_zone_max, res_zone_level2,
            res_zone_level2_2,res_zone_level3,res_zone_level3_2,pp, school, police, stadium, industrial, power_pole,
            service, industrial_1, industrial_2, industrial_level2, industrial_level2_2, industrial_level3,
            industrial_level3_2, service_1, service_2, service_level2, service_level2_2, service_level3, service_level3_2;
    Board board;
    boolean build = false;
    boolean destroy = false;
    boolean upgrade = false;
    boolean zonestat = false;
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

        background = new ImageIcon("src/main/java/KukaPest/Assets/gatyakukarestart.jpg").getImage();
        grass = new ImageIcon("src/main/java/KukaPest/Assets/grass2.png").getImage();
        dirt = new ImageIcon("src/main/java/KukaPest/Assets/dirt2.png").getImage();
        water = new ImageIcon("src/main/java/KukaPest/Assets/water_2.png").getImage();
        road = new ImageIcon("src/main/java/KukaPest/Assets/road.png").getImage();
        uni = new ImageIcon("src/main/java/KukaPest/Assets/university_2.png").getImage();
        res_zone = new ImageIcon("src/main/java/KukaPest/Assets/residental.png").getImage();
        res_zone_1 = new ImageIcon("src/main/java/KukaPest/Assets/residental_1.png").getImage();
        res_zone_2 = new ImageIcon("src/main/java/KukaPest/Assets/residental_2.png").getImage();
        res_zone_3 = new ImageIcon("src/main/java/KukaPest/Assets/residental_3.png").getImage();
        res_zone_max = new ImageIcon("src/main/java/KukaPest/Assets/residental_max.png").getImage();
        res_zone_level2 = new ImageIcon("src/main/java/KukaPest/Assets/residental_level2_1.png").getImage();
        res_zone_level2_2 = new ImageIcon("src/main/java/KukaPest/Assets/residental_level2_2.png").getImage();
        res_zone_level3 = new ImageIcon("src/main/java/KukaPest/Assets/residental_level2_3.png").getImage();
        res_zone_level3_2 = new ImageIcon("src/main/java/KukaPest/Assets/residental_level3_2.png").getImage();
        pp = new ImageIcon("src/main/java/KukaPest/Assets/pp.png").getImage();
        school = new ImageIcon("src/main/java/KukaPest/Assets/school.png").getImage();
        police = new ImageIcon("src/main/java/KukaPest/Assets/police.png").getImage();
        stadium = new ImageIcon("src/main/java/KukaPest/Assets/stadium.png").getImage();
        industrial = new ImageIcon("src/main/java/KukaPest/Assets/industrial.png").getImage();
        industrial_1 = new ImageIcon("src/main/java/KukaPest/Assets/industrial_1.png").getImage();
        industrial_2 = new ImageIcon("src/main/java/KukaPest/Assets/industrial_2.png").getImage();
        industrial_level2 = new ImageIcon("src/main/java/KukaPest/Assets/industrial_level2.png").getImage();
        industrial_level2_2 = new ImageIcon("src/main/java/KukaPest/Assets/industrial_level2_2.png").getImage();
        industrial_level3 = new ImageIcon("src/main/java/KukaPest/Assets/industrial_level3.png").getImage();
        industrial_level3_2 = new ImageIcon("src/main/java/KukaPest/Assets/industrial_level3_2.png").getImage();
        power_pole = new ImageIcon("src/main/java/KukaPest/Assets/power_pole.png").getImage();
        service = new ImageIcon("src/main/java/KukaPest/Assets/service.png").getImage();
        service_1 = new ImageIcon("src/main/java/KukaPest/Assets/service_1.png").getImage();
        service_2 = new ImageIcon("src/main/java/KukaPest/Assets/service_2.png").getImage();
        service_level2 = new ImageIcon("src/main/java/KukaPest/Assets/service_level2.png").getImage();
        service_level2_2 = new ImageIcon("src/main/java/KukaPest/Assets/service_level2_2.png").getImage();
        service_level3 = new ImageIcon("src/main/java/KukaPest/Assets/service_level3.png").getImage();
        service_level3_2 = new ImageIcon("src/main/java/KukaPest/Assets/service_level3_2.png").getImage();

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
                        if (((ResidentialZone) map[i][j]).getCurrentCapacity() == 0) {
                            g2.drawImage(res_zone, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }
                        if (((ResidentialZone) map[i][j]).getCurrentCapacity() <= 16 && ((ResidentialZone) map[i][j]).getCurrentCapacity() > 0) {
                            g2.drawImage(res_zone_level2, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }
                        if (((ResidentialZone) map[i][j]).getCurrentCapacity() <= 25 && ((ResidentialZone) map[i][j]).getCurrentCapacity() > 16) {
                            g2.drawImage(res_zone_level2_2, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }

                    }
                    if(((ResidentialZone) map[i][j]).getLevel() == 3){
                        if (((ResidentialZone) map[i][j]).getCurrentCapacity() == 0) {
                            g2.drawImage(res_zone, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }
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

                    if(((IndustrialZone) map[i][j]).getLevel() == 1) {
                        if (((IndustrialZone) map[i][j]).getCurrentCapacity() == 0) {
                            g2.drawImage(industrial, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        } else if (((IndustrialZone) map[i][j]).getCurrentCapacity() <= 6 && ((IndustrialZone) map[i][j]).getCurrentCapacity() > 0) {
                            g2.drawImage(industrial_1, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        } else {
                            g2.drawImage(industrial_2, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }
                    }
                    if(((IndustrialZone) map[i][j]).getLevel() == 2){
                        if (((IndustrialZone) map[i][j]).getCurrentCapacity() == 0) {
                            g2.drawImage(industrial, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        } else if (((IndustrialZone) map[i][j]).getCurrentCapacity() <= 20 && ((IndustrialZone) map[i][j]).getCurrentCapacity() > 0) {
                            g2.drawImage(industrial_level2, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        } else {
                            g2.drawImage(industrial_level2_2, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }

                    }
                    if(((IndustrialZone) map[i][j]).getLevel() == 3){
                        if (((IndustrialZone) map[i][j]).getCurrentCapacity() == 0) {
                            g2.drawImage(industrial, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        } else if (((IndustrialZone) map[i][j]).getCurrentCapacity() <= 42 && ((IndustrialZone) map[i][j]).getCurrentCapacity() > 0) {
                            g2.drawImage(industrial_level3, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        } else {
                            g2.drawImage(industrial_level3_2, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }

                    }
                    //g2.drawImage(industrial, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                }
                else if (map[i][j] instanceof Pole)
                    g2.drawImage(power_pole, j * board.getCellSide(), i * board.getCellSide(),25,25, null);
                else if (map[i][j] instanceof ServiceZone){

                    if(((ServiceZone) map[i][j]).getLevel() == 1) {
                        if (((ServiceZone) map[i][j]).getCurrentCapacity() == 0) {
                            g2.drawImage(service, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        } else if (((ServiceZone) map[i][j]).getCurrentCapacity() <= 6 && ((ServiceZone) map[i][j]).getCurrentCapacity() > 0) {
                            g2.drawImage(service_1, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        } else {
                            g2.drawImage(service_2, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }
                    }
                    if(((ServiceZone) map[i][j]).getLevel() == 2) {
                        if (((ServiceZone) map[i][j]).getCurrentCapacity() == 0) {
                            g2.drawImage(service, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        } else if (((ServiceZone) map[i][j]).getCurrentCapacity() <= 20 && ((ServiceZone) map[i][j]).getCurrentCapacity() > 0) {
                            g2.drawImage(service_level2, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        } else {
                            g2.drawImage(service_level2_2, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }
                    }
                    if(((ServiceZone) map[i][j]).getLevel() == 3) {
                        if (((ServiceZone) map[i][j]).getCurrentCapacity() == 0) {
                            g2.drawImage(service, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        } else if (((ServiceZone) map[i][j]).getCurrentCapacity() <= 35 && ((ServiceZone) map[i][j]).getCurrentCapacity() > 0) {
                            g2.drawImage(service_level3, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        } else {
                            g2.drawImage(service_level3_2, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }
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
        if(zonestat){
            System.out.println(row +" " + col);
            //JFrame popupframe = new JFrame();
            if(this.map[row][col] instanceof MainZone || this.map[row][col] instanceof ZonePart){
                MainZone mainZone;
                if(this.map[row][col] instanceof MainZone){
                    mainZone = (MainZone) this.map[row][col];
                }
                else{
                    mainZone = ((ZonePart) this.map[row][col]).mainBuilding;
                }
                if(mainZone instanceof ResidentialZone) {
                    JOptionPane.showMessageDialog(null,
                            "Level: " + ((ResidentialZone) mainZone).getLevel()+ " level\nCapacity: " + mainZone.getMaxCapacity() + " people \nPopulation: "
                                    + mainZone.getCurrentCapacity() + " people\nElectricity: " + mainZone.isElectricity(),
                            "Residental Zone",
                            JOptionPane.PLAIN_MESSAGE);
                }
                else if(mainZone instanceof ServiceZone) {
                    JOptionPane.showMessageDialog(null,
                            "Level: " + ((ServiceZone) mainZone).getLevel()+ " level\nCapacity: " + mainZone.getMaxCapacity() + " people \nWorkers: "
                                    + mainZone.getCurrentCapacity() + " people\nElectricity: " + mainZone.isElectricity(),
                            "Service Zone",
                            JOptionPane.PLAIN_MESSAGE);
                }
                else if(mainZone instanceof IndustrialZone) {
                    JOptionPane.showMessageDialog(null,
                             "Level: " + ((IndustrialZone) mainZone).getLevel()+ " level\nCapacity: " + mainZone.getMaxCapacity() + " people \nWorkers: "
                                    + mainZone.getCurrentCapacity() + " people\nElectricity: " + mainZone.isElectricity(),
                            "Industrial Zone",
                            JOptionPane.PLAIN_MESSAGE);
                }

            }
        }

        repaint();

    }

    @Override
    public void mouseDragged(MouseEvent e){
        if((selectedBuilding == Building.ROAD || selectedBuilding == Building.POLE ) && build){
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
