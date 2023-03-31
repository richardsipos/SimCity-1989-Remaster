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
import java.util.Random;


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
            }
        }

        for(int i = 0; i < board.getBoardY(); i++){
            g2.setColor(Color.black);
            for(int j = 0; j < board.getBoardX(); j++){
                if(map[i][j] == 1)
                    g2.drawImage(grass, board.getOriginalX() + (j * board.getCellSide()), board.getOriginalY() + i * board.getCellSide(),board.getCellSide(),board.getCellSide(), null);
                else if (map[i][j] == 2)
                    g2.drawImage(dirt, board.getOriginalX() + (j * board.getCellSide()), board.getOriginalY() + i * board.getCellSide(),board.getCellSide(),board.getCellSide(), null);
                else if (map[i][j] == 3)
                    g2.drawImage(water, board.getOriginalX() + (j * board.getCellSide()), board.getOriginalY() + i * board.getCellSide(),board.getCellSide(),board.getCellSide(), null);
                else if (map[i][j] == 4)
                    g2.drawImage(road, board.getOriginalX() + (j * board.getCellSide()), board.getOriginalY() + i * board.getCellSide(),40,40, null);
                else if (map[i][j] == 5)
                    g2.drawImage(police, board.getOriginalX() + (j * board.getCellSide()), board.getOriginalY() + i * board.getCellSide(),80,160, null);
                else if (map[i][j] == 6)
                    g2.drawImage(stadium, board.getOriginalX() + (j * board.getCellSide()), board.getOriginalY() + i * board.getCellSide(),160,160, null);
                else if (map[i][j] == 7)
                    g2.drawImage(uni, board.getOriginalX() + (j * board.getCellSide()), board.getOriginalY() + i * board.getCellSide(),160,160, null);
                else if (map[i][j] == 8)
                    g2.drawImage(res_zone, board.getOriginalX() + (j * board.getCellSide()), board.getOriginalY() + i * board.getCellSide(),80,80, null);
                else if (map[i][j] == 9)
                    g2.drawImage(pp, board.getOriginalX() + (j * board.getCellSide()), board.getOriginalY() + i * board.getCellSide(),160,160, null);
                else if (map[i][j] == 10)
                    g2.drawImage(school, board.getOriginalX() + (j * board.getCellSide()), board.getOriginalY() + i * board.getCellSide(),80,160, null);
            }
        }
    }
/*
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        for(int i = 0; i < board.getBoardY(); i++) {
            for (int j = 0; j < board.getBoardX(); j++) {

                if(map[i][j] == 1)
                    g2.drawImage(grass, board.getOriginalX() + (j * board.getCellSide()), board.getOriginalY() + i * board.getCellSide(),board.getCellSide(),board.getCellSide(), null);

                else if (map[i][j] == 2)
                    g2.drawImage(dirt, board.getOriginalX() + (j * board.getCellSide()), board.getOriginalY() + i * board.getCellSide(),board.getCellSide(),board.getCellSide(), null);

                else if (map[i][j] == 3)
                    g2.drawImage(water, board.getOriginalX() + (j * board.getCellSide()), board.getOriginalY() + i * board.getCellSide(),board.getCellSide(),board.getCellSide(), null);
                else if (map[i][j] == 4)
                    g2.drawImage(road, board.getOriginalX() + (j * board.getCellSide()), board.getOriginalY() + i * board.getCellSide(),40,40, null);
                else if (map[i][j] == 5)
                    g2.drawImage(police, board.getOriginalX() + (j * board.getCellSide()), board.getOriginalY() + i * board.getCellSide(),80,160, null);
                else if (map[i][j] == 6)
                    g2.drawImage(stadium, board.getOriginalX() + (j * board.getCellSide()), board.getOriginalY() + i * board.getCellSide(),160,160, null);
                else if (map[i][j] == 7)
                    g2.drawImage(uni, board.getOriginalX() + (j * board.getCellSide()), board.getOriginalY() + i * board.getCellSide(),160,160, null);
                else if (map[i][j] == 8)
                    g2.drawImage(res_zone, board.getOriginalX() + (j * board.getCellSide()), board.getOriginalY() + i * board.getCellSide(),80,80, null);
                else if (map[i][j] == 9)
                    g2.drawImage(pp, board.getOriginalX() + (j * board.getCellSide()), board.getOriginalY() + i * board.getCellSide(),160,160, null);
                else if (map[i][j] == 10)
                    g2.drawImage(school, board.getOriginalX() + (j * board.getCellSide()), board.getOriginalY() + i * board.getCellSide(),80,160, null);
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
        Random r = new Random();
        int max=10;
        int min=4;
        int a = r.nextInt(max-min + 1)+ min;
        System.out.println(a);
        switch(a) {
            case 4: //road
                map[row][col] =4;
                break;
            case 5: //police
                for (int i = 0; i < 4; ++i) {
                    for (int j = 0; j < 2; ++j){
                        if (row+i < 18 && col + j < 32){
                            if(i == 0 && j == 0) map[row][col] = 5;
                            else map[row+i][col+j] = 11;
                        }
                    }
                }
                break;
            case 6: //stadium
                for (int i = 0; i < 4; ++i) {
                    for (int j = 0; j < 4; ++j){
                        if (row+i < 18 && col + j < 32){
                            if(i == 0 && j == 0) map[row][col] = 6;
                            else map[row+i][col+j] = 11;
                        }
                    }
                }
                break;
            case 7: //uni
                for (int i = 0; i < 4; ++i) {
                    for (int j = 0; j < 4; ++j){
                        if (row+i < 18 && col + j < 32){
                            if(i == 0 && j == 0) map[row][col] = 7;
                            else map[row+i][col+j] = 11;
                        }
                    }
                }
                break;
            case 8: //res
                for (int i = 0; i < 2; ++i) {
                    for (int j = 0; j < 2; ++j){
                        if (row+i < 18 && col + j < 32){
                            if(i == 0 && j == 0) map[row][col] = 8;
                            else map[row+i][col+j] = 11;
                        }
                    }
                }
                break;
            case 9: //pp
                for (int i = 0; i < 4; ++i) {
                    for (int j = 0; j < 4; ++j){
                        if (row+i < 18 && col + j < 32){
                            if(i == 0 && j == 0) map[row][col] = 9;
                            else map[row+i][col+j] = 11;
                        }
                    }
                }
                break;
            case 10: //school
                for (int i = 0; i < 4; ++i) {
                    for (int j = 0; j < 2; ++j){
                        if (row+i < 18 && col + j < 32){
                            if(i == 0 && j == 0) map[row][col] = 10;
                            else map[row+i][col+j] = 11;
                        }
                    }
                }
                break;
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
}
