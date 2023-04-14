package View;


import Model.Game;
import Model.Helper.Building;
import Model.Helper.Coordinates;
import Model.Map.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BoardGUI extends JPanel implements MouseListener {

    private int fromCol = -1;
    private int fromRow = -1;
    private int toCol = -1;
    private int toRow = -1;
    static final int DELAY = 1000;
    Image background, grass, dirt, water, road, uni, res_zone, pp, school, police, stadium;
    Board board;
    boolean build = false;
    Building selectedBuilding;
    Tile[][] map;
    private Game game;

    public BoardGUI(int fieldX, int fieldY){
        game = new Game("Nuke city");
        //inicializálás
        this.board = new Board(fieldX,fieldY);
        //egér mozgatását figyelő eseménykezelő
        addMouseListener(this);

        background = new ImageIcon("KukaPest/Assets/gatyakukarestart.jpg").getImage();
        grass = new ImageIcon("KukaPest/Assets/grass.png").getImage();
        dirt = new ImageIcon("KukaPest/Assets/dirt3.png").getImage();
        water = new ImageIcon("KukaPest/Assets/water3.png").getImage();
        road = new ImageIcon("KukaPest/Assets/road.png").getImage();
        uni = new ImageIcon("KukaPest/Assets/univ.png").getImage();
        res_zone = new ImageIcon("KukaPest/Assets/house.png").getImage();
        pp = new ImageIcon("KukaPest/Assets/pp.png").getImage();
        school = new ImageIcon("KukaPest/Assets/school.png").getImage();
        police = new ImageIcon("KukaPest/Assets/police.png").getImage();
        stadium = new ImageIcon("KukaPest/Assets/stadium.png").getImage();

        map = game.getMap();

        Timer timer = new Timer(DELAY, stepGame);
        timer.start();
    }


    @Override
    protected void paintChildren(Graphics g){
        super.paintChildren(g);
        Graphics2D g2 = (Graphics2D)g;

        //a tábla felrajzolása
        for(int i = 0; i < board.getBoardY(); i++){
            g2.setColor(Color.black);
            for(int j = 0; j < board.getBoardX(); j++){
                g2.drawRect(board.getOriginalX() + (j * board.getCellSide()), board.getOriginalY() + i * board.getCellSide(),board.getCellSide(),board.getCellSide());
            }
        }

        for(int i = 0; i < board.getBoardY(); i++){
            g2.setColor(Color.black);
            for(int j = 0; j < board.getBoardX(); j++){
                if(map[i][j] instanceof Grass)
                    g2.drawImage(grass, board.getOriginalX() + (j * board.getCellSide()), board.getOriginalY() + i * board.getCellSide(),board.getCellSide(),board.getCellSide(), null);
                else if (map[i][j] instanceof Dirt)
                    g2.drawImage(dirt, board.getOriginalX() + (j * board.getCellSide()), board.getOriginalY() + i * board.getCellSide(),board.getCellSide(),board.getCellSide(), null);
                else if (map[i][j] instanceof Water)
                    g2.drawImage(water, board.getOriginalX() + (j * board.getCellSide()), board.getOriginalY() + i * board.getCellSide(),board.getCellSide(),board.getCellSide(), null);
                else if (map[i][j] instanceof Road)
                    g2.drawImage(road, board.getOriginalX() + (j * board.getCellSide()), board.getOriginalY() + i * board.getCellSide(),40,40, null);
                else if (map[i][j] instanceof Police)
                    g2.drawImage(police, board.getOriginalX() + (j * board.getCellSide()), board.getOriginalY() + i * board.getCellSide(),80,160, null);
                else if (map[i][j] instanceof Stadium)
                    g2.drawImage(stadium, board.getOriginalX() + (j * board.getCellSide()), board.getOriginalY() + i * board.getCellSide(),160,160, null);
                else if (map[i][j] instanceof University)
                    g2.drawImage(uni, board.getOriginalX() + (j * board.getCellSide()), board.getOriginalY() + i * board.getCellSide(),160,160, null);
                else if (map[i][j] instanceof ResidentialZone)
                    g2.drawImage(res_zone, board.getOriginalX() + (j * board.getCellSide()), board.getOriginalY() + i * board.getCellSide(),80,80, null);
                else if (map[i][j] instanceof PowerPlant)
                    g2.drawImage(pp, board.getOriginalX() + (j * board.getCellSide()), board.getOriginalY() + i * board.getCellSide(),160,160, null);
                else if (map[i][j] instanceof School)
                    g2.drawImage(school, board.getOriginalX() + (j * board.getCellSide()), board.getOriginalY() + i * board.getCellSide(),80,160, null);
            }
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getPoint().x;
        int col = (x - board.getOriginalX()) / board.getCellSide();
        fromCol = col;
        int y = e.getPoint().y;
        int row = (y - board.getOriginalY()) / board.getCellSide();
        fromRow = row;
        System.out.println("from (col,row): " + fromCol + ", " + fromRow);

        game.build(selectedBuilding, new Coordinates(row, col));

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

    ActionListener stepGame = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            game.stepGame();
        }
    };
}