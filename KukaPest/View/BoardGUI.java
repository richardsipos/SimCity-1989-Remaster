package View;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



public class BoardGUI extends JPanel implements MouseListener {

    private int fromCol = -1;
    private int fromRow = -1;
    private int toCol = -1;
    private int toRow = -1;
    Image background, grass, dirt, water, road, uni, res_zone, pp, school, police, stadium;
    Board board;

    int[][] map;

    public BoardGUI(int fieldX, int fieldY){
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

        /*int count = 0;
        this.map = new int[18][32];
        for (int i = 0; i < 18; ++i) {
            for (int j = 0; j < 32; ++j) {
                map[i][j] = count % 3;
                ++count;
            }
        }*/
        this.map = new int[][]{{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 3, 3, 3, 3, 2, 1, 1, 1, 1, 1, 1, 1, 1,1,1},
                                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 3, 3, 3, 3, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1,1,1},
                                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 3, 3, 3, 3, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1,1},
                                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 3, 3, 3, 3, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1,1},
                                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 3, 3, 3, 3, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1,1},
                                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 3, 3, 3, 3, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1,1},
                                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 3, 3, 3, 3, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1,1},
                                {1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 3, 3, 3, 3, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1,1},
                                {1, 1, 1, 1, 1, 1, 1, 1, 2, 3, 3, 3, 3, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1,1},
                                {1, 1, 1, 1, 1, 1, 1, 2, 3, 3, 3, 3, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1,1},
                                {1, 1, 1, 1, 1, 1, 2, 3, 3, 3, 3, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1,1},
                                {1, 1, 1, 1, 1, 2, 3, 3, 3, 3, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1,1},
                                {1, 1, 1, 1, 2, 3, 3, 3, 3, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1,1},
                                {1, 1, 1, 2, 3, 3, 3, 3, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3,3,3},
                                {1, 1, 2, 3, 3, 3, 3, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 3, 3, 3, 3,3,3},
                                {1, 1, 2, 3, 3, 3, 3, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3,3,3},
                                {1, 1, 2, 3, 3, 3, 3, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3,3,3},
                                {1, 2, 3, 3, 3, 3, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3,3,3}};
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

                    if(map[i][j] == 1)
                        g2.drawImage(grass, board.getOriginalX() + (j * board.getCellSide()), board.getOriginalY() + i * board.getCellSide(),board.getCellSide(),board.getCellSide(), null);

                    else if (map[i][j] == 2)
                        g2.drawImage(dirt, board.getOriginalX() + (j * board.getCellSide()), board.getOriginalY() + i * board.getCellSide(),board.getCellSide(),board.getCellSide(), null);

                    else
                        g2.drawImage(water, board.getOriginalX() + (j * board.getCellSide()), board.getOriginalY() + i * board.getCellSide(),board.getCellSide(),board.getCellSide(), null);
            }
        }

    }
/*
    @Override
    protected void paintComponent(Graphics g){
        for(int i = 0; i < board.getBoardY(); i++){
            for(int j = 0; j < board.getBoardX(); j++){

                if(map[i][j] == 0)
                    g.drawImage(grass, board.getOriginalY(), board.getOriginalX(), null);

                else if (map[i][j] == 1)
                    g.drawImage(water, board.getOriginalY(), board.getOriginalX(), null);

                else
                    g.drawImage(dirt, board.getOriginalY(), board.getOriginalX(), null);
            }
        }
    }*/
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getPoint().x;
        int col = (x - board.getOriginalX()) / board.getCellSide();
        fromCol = col;
        int y = e.getPoint().y;
        int row = (y - board.getOriginalY()) / board.getCellSide();
        fromRow = row;
        System.out.println("from (col,row): " + fromCol + ", " + fromRow);


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
}
