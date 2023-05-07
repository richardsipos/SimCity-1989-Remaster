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
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.RescaleOp;
import java.util.Map;

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
    protected void paintComponent(Graphics g){
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
                    g2.drawImage(((Police)map[i][j]).isElectricity() ? police : newBrightness(police, 0.5f), j * board.getCellSide(), i * board.getCellSide(),100,50, null);
                else if (map[i][j] instanceof Stadium)
                    g2.drawImage(((Stadium)map[i][j]).isElectricity() ? stadium : newBrightness(stadium, 0.5f), j * board.getCellSide(), i * board.getCellSide(),100,100, null);
                else if (map[i][j] instanceof University)
                    g2.drawImage(((University)map[i][j]).isElectricity() ? uni : newBrightness(uni, 0.5f), j * board.getCellSide(),i * board.getCellSide(),100,100, null);
                else if (map[i][j] instanceof ResidentialZone) {
                    // System.out.println(((ResidentialZone) map[i][j]).getCurrentCapacity());
                    if(((ResidentialZone) map[i][j]).getLevel() == 1){
                        if (((ResidentialZone) map[i][j]).getCurrentCapacity() == 0) {
                            g2.drawImage(((MainZone)map[i][j]).isElectricity() ? res_zone : newBrightness(res_zone, 0.5f), j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }
                        if (((ResidentialZone) map[i][j]).getCurrentCapacity() <= 2 && ((ResidentialZone) map[i][j]).getCurrentCapacity() >0) {
                            g2.drawImage(((MainZone)map[i][j]).isElectricity() ? res_zone_1 : newBrightness(res_zone_1, 0.5f), j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }
                        if (((ResidentialZone) map[i][j]).getCurrentCapacity() <= 4 && ((ResidentialZone) map[i][j]).getCurrentCapacity() > 2) {
                            g2.drawImage(((MainZone)map[i][j]).isElectricity() ? res_zone_2 : newBrightness(res_zone_2, 0.5f), j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }
                        if (((ResidentialZone) map[i][j]).getCurrentCapacity() <= 6 && ((ResidentialZone) map[i][j]).getCurrentCapacity() > 4) {
                            g2.drawImage(((MainZone)map[i][j]).isElectricity() ? res_zone_3 : newBrightness(res_zone_3, 0.5f), j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }
                        if (((ResidentialZone) map[i][j]).getCurrentCapacity() <= 10 && ((ResidentialZone) map[i][j]).getCurrentCapacity() > 6) {
                            g2.drawImage(((MainZone)map[i][j]).isElectricity() ? res_zone_max : newBrightness(res_zone_max, 0.5f), j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }

                    }
                    if(((ResidentialZone) map[i][j]).getLevel() == 2){
                        if (((ResidentialZone) map[i][j]).getCurrentCapacity() == 0) {
                            g2.drawImage(((MainZone)map[i][j]).isElectricity() ? res_zone : newBrightness(res_zone, 0.5f), j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }
                        if (((ResidentialZone) map[i][j]).getCurrentCapacity() <= 16 && ((ResidentialZone) map[i][j]).getCurrentCapacity() > 0) {
                            g2.drawImage(((MainZone)map[i][j]).isElectricity() ? res_zone_level2 : newBrightness(res_zone_level2, 0.5f), j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }
                        if (((ResidentialZone) map[i][j]).getCurrentCapacity() <= 25 && ((ResidentialZone) map[i][j]).getCurrentCapacity() > 16) {
                            g2.drawImage(((MainZone)map[i][j]).isElectricity() ? res_zone_level2_2 : newBrightness(res_zone_level2_2, 0.5f), j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }

                    }
                    if(((ResidentialZone) map[i][j]).getLevel() == 3){
                        if (((ResidentialZone) map[i][j]).getCurrentCapacity() == 0) {
                            g2.drawImage(((MainZone)map[i][j]).isElectricity() ? res_zone : newBrightness(res_zone, 0.5f), j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }
                        if (((ResidentialZone) map[i][j]).getCurrentCapacity() <= 32 && ((ResidentialZone) map[i][j]).getCurrentCapacity() > 0) {
                            g2.drawImage(((MainZone)map[i][j]).isElectricity() ? res_zone_level3 : newBrightness(res_zone_level3, 0.5f), j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }
                        if (((ResidentialZone) map[i][j]).getCurrentCapacity() <= 50 && ((ResidentialZone) map[i][j]).getCurrentCapacity() > 32) {
                            g2.drawImage(((MainZone)map[i][j]).isElectricity() ? res_zone_level3_2 : newBrightness(res_zone_level3_2, 0.5f), j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
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
                            g2.drawImage(((MainZone)map[i][j]).isElectricity() ? industrial : newBrightness(industrial, 0.5f), j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        } else if (((IndustrialZone) map[i][j]).getCurrentCapacity() <= 6 && ((IndustrialZone) map[i][j]).getCurrentCapacity() > 0) {
                            g2.drawImage(((MainZone)map[i][j]).isElectricity() ? industrial_1 : newBrightness(industrial_1, 0.5f), j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        } else {
                            g2.drawImage(((MainZone)map[i][j]).isElectricity() ? industrial_2 : newBrightness(industrial_2, 0.5f), j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }
                    }
                    if(((IndustrialZone) map[i][j]).getLevel() == 2){
                        if (((IndustrialZone) map[i][j]).getCurrentCapacity() == 0) {
                            g2.drawImage(((MainZone)map[i][j]).isElectricity() ? industrial : newBrightness(industrial, 0.5f), j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        } else if (((IndustrialZone) map[i][j]).getCurrentCapacity() <= 20 && ((IndustrialZone) map[i][j]).getCurrentCapacity() > 0) {
                            g2.drawImage(((MainZone)map[i][j]).isElectricity() ? industrial_level2 : newBrightness(industrial_level2, 0.5f), j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        } else {
                            g2.drawImage(((MainZone)map[i][j]).isElectricity() ? industrial_level2_2 : newBrightness(industrial_level2_2, 0.5f), j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }

                    }
                    if(((IndustrialZone) map[i][j]).getLevel() == 3){
                        if (((IndustrialZone) map[i][j]).getCurrentCapacity() == 0) {
                            g2.drawImage(((MainZone)map[i][j]).isElectricity() ? industrial : newBrightness(industrial, 0.5f), j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        } else if (((IndustrialZone) map[i][j]).getCurrentCapacity() <= 42 && ((IndustrialZone) map[i][j]).getCurrentCapacity() > 0) {
                            g2.drawImage(((MainZone)map[i][j]).isElectricity() ? industrial_level3 : newBrightness(industrial_level3, 0.5f), j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        } else {
                            g2.drawImage(((MainZone)map[i][j]).isElectricity() ? industrial_level3_2 : newBrightness(industrial_level3_2, 0.5f), j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }

                    }
                    //g2.drawImage(industrial, j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                }
                else if (map[i][j] instanceof Pole)
                    g2.drawImage(power_pole, j * board.getCellSide(), i * board.getCellSide(),25,25, null);
                else if (map[i][j] instanceof ServiceZone){

                    if(((ServiceZone) map[i][j]).getLevel() == 1) {
                        if (((ServiceZone) map[i][j]).getCurrentCapacity() == 0) {
                            g2.drawImage(((MainZone)map[i][j]).isElectricity() ? service : newBrightness(service, 0.5f), j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        } else if (((ServiceZone) map[i][j]).getCurrentCapacity() <= 6 && ((ServiceZone) map[i][j]).getCurrentCapacity() > 0) {
                            g2.drawImage(((MainZone)map[i][j]).isElectricity() ? service_1 : newBrightness(service_1, 0.5f), j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        } else {
                            g2.drawImage(((MainZone)map[i][j]).isElectricity() ? service_2 : newBrightness(service_2, 0.5f), j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }
                    }
                    if(((ServiceZone) map[i][j]).getLevel() == 2) {
                        if (((ServiceZone) map[i][j]).getCurrentCapacity() == 0) {
                            g2.drawImage(((MainZone)map[i][j]).isElectricity() ? service : newBrightness(service, 0.5f), j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        } else if (((ServiceZone) map[i][j]).getCurrentCapacity() <= 20 && ((ServiceZone) map[i][j]).getCurrentCapacity() > 0) {
                            g2.drawImage(((MainZone)map[i][j]).isElectricity() ? service_level2 : newBrightness(service_level2, 0.5f), j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        } else {
                            g2.drawImage(((MainZone)map[i][j]).isElectricity() ? service_level2_2 : newBrightness(service_level2_2, 0.5f), j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }
                    }
                    if(((ServiceZone) map[i][j]).getLevel() == 3) {
                        if (((ServiceZone) map[i][j]).getCurrentCapacity() == 0) {
                            g2.drawImage(((MainZone)map[i][j]).isElectricity() ? service : newBrightness(service, 0.5f), j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        } else if (((ServiceZone) map[i][j]).getCurrentCapacity() <= 35 && ((ServiceZone) map[i][j]).getCurrentCapacity() > 0) {
                            g2.drawImage(((MainZone)map[i][j]).isElectricity() ? service_level3 : newBrightness(service_level3, 0.5f), j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        } else {
                            g2.drawImage(((MainZone)map[i][j]).isElectricity() ? service_level3_2 : newBrightness(service_level3_2, 0.5f), j * board.getCellSide(), i * board.getCellSide(), 50, 50, null);
                        }
                    }
                }
                    //g2.drawImage(service, j * board.getCellSide(), i * board.getCellSide(),50,50, null);

            }
        }
    }


    /**
     * Click monitoring and coordinate determination
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
            if(!candestroy){
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
                /*if(mainZone instanceof ResidentialZone) {
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
                }*/
                StringBuilder stats = new StringBuilder();
                for (Map.Entry<String, String> stat :
                        mainZone.getStats().entrySet()) {
                    stats.append(stat.getKey() + ": " + stat.getValue() + "\n");
                }
                JOptionPane.showMessageDialog(null,
                        stats.toString().trim(),
                        "Stats",
                        JOptionPane.PLAIN_MESSAGE);

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

    public static Image newBrightness( Image source, float brightnessPercentage ) {

        BufferedImage bi = new BufferedImage(
                source.getWidth( null ),
                source.getHeight( null ),
                BufferedImage.TYPE_INT_ARGB );

        int[] pixel = { 0, 0, 0, 0 };
        float[] hsbvals = { 0, 0, 0 };

        bi.getGraphics().drawImage( source, 0, 0, null );

        // recalculare every pixel, changing the brightness
        for ( int i = 0; i < bi.getHeight(); i++ ) {
            for ( int j = 0; j < bi.getWidth(); j++ ) {

                // get the pixel data
                bi.getRaster().getPixel( j, i, pixel );

                // converts its data to hsb to change brightness
                Color.RGBtoHSB( pixel[0], pixel[1], pixel[2], hsbvals );

                // create a new color with the changed brightness
                Color c = new Color( Color.HSBtoRGB( hsbvals[0], hsbvals[1], hsbvals[2] * brightnessPercentage ) );

                // set the new pixel
                bi.getRaster().setPixel( j, i, new int[]{ c.getRed(), c.getGreen(), c.getBlue(), pixel[3] } );

            }

        }

        return bi;

    }

}
