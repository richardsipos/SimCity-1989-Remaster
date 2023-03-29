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
    private GridBagConstraints c;
    private FieldVisualizer fieldDrawer;
    private Image background;
    private int WIDTH = 1280;
    private int HEIGHT = 720;
    //private Font font = new Font("TimesRoman", Font.BOLD, 30);
    //private Timer timer = new Timer(1000, new gameListener());

    JLabel moneyLabel;


    public GameGraphics() {
        super();
        this.requestFocusInWindow();
        //game = new Game();
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setFocusable(true);
        background = new ImageIcon("KukaPest/Assets/gatyakukarestart.jpg").getImage();

        this.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        this.setPreferredSize(new Dimension(1280, 720));

        fieldDrawer = new FieldVisualizer(game);
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 10;
        this.add(fieldDrawer, c);

        moneyLabel = new JLabel("Helloo");
        c.gridx = 2;
        c.gridy = 0;
        c.gridheight = 1;
        this.add(moneyLabel, c);

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


    class gameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            repaint();
        }
    }

    private class clockListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //game.stepTime();
            //refreshLabel();
            repaint();
        }
    }

}



