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

    Board board;


    BoardGUI(int boardSize){
        //inicializálás
        this.board = new Board(boardSize);
        //egér mozgatását figyelő eseménykezelő

        addMouseListener(this);


    }

    /**
     *A játéktábla megrajzolása
     * @param g
     */
    @Override
    protected void paintChildren(Graphics g){
        super.paintChildren(g);
        Graphics2D g2 = (Graphics2D)g;

        //a tábla felrajzolása
        for(int j = 0; j < board.getBoardSize(); j++){
            g2.setColor(Color.black);
            for(int i = 0; i < board.getBoardSize(); i++){
                g2.drawRect(board.getOriginalX() + (i * board.getCellSide()), board.getOriginalY() + j * board.getCellSide(),board.getCellSide(),board.getCellSide());
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
