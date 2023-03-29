package View;

import Model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FieldVisualizer extends JPanel implements MouseListener {
    /*
     * Variable that models how thick the lines should be between each element in
     * the board
     */
    public static final int BOARD_THICKNESS = 1;
    /*
     * Instance of model that the Widget encapsulates to know how to draw each piece
     */
    private Game game;
    /*
     * Graphics2d Instance needed to draw the images and grid itself
     */
    Graphics2D g2d;

    public FieldVisualizer(Game game) {
        /*
         * Add ourselves as a mouse listener, so that we can detect when the user clicks
         * on a specific part of the grid
         */
        this.addMouseListener(this);

        /* Set our preferred size */
        this.setPreferredSize(new Dimension(900, 900));

        /* Encapsulate the model and repaint the display */
        this.game = game;
        repaint();
    }

    /* Method that repaints the entire widget */
    public void paintComponent(Graphics g) {


    }

    @Override
    /*
     * When the user clicks on the grid, we need to get it to a position on the
     * board Then we need to prompt the user to construct on that piece, if possible
     */
    public void mouseClicked(MouseEvent e) {
        
    }

    /*
     * Methods that we are required to override as part of the MouseListener
     * Interface, but that we don't actually need
     */
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
