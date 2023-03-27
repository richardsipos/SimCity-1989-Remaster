package View;

import Model.Game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.TimerTask;

public class GameGraphics extends JPanel {
    Game game;
    private Image background;
    private int WIDTH = 1280;
    private int HEIGHT = 720;
    private Font font = new Font("TimesRoman", Font.BOLD, 30);
    private Timer timer = new Timer(1000, new gameListener());


    public GameGraphics() {
        super();
        this.requestFocusInWindow();
        game = new Game();
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setFocusable(true);
        background = new ImageIcon("assets/gatyakukarestart.jpg").getImage();
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        grphcs.drawImage(background, 0, 0, WIDTH, HEIGHT, null);
    }

    protected void start() {
        //game.start();


        //timer.start();
    }

}


class gameListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        //repaint();
    }
}
