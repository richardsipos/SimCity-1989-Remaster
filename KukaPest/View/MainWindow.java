package View;

import Model.Game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;

public class MainWindow {
    public JFrame f;
    GameGraphics g;
    f = new JFrame();
    g = new GameGraphics();

    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu("Top 10");
    JMenu ng = new JMenu("New Game");

    menuBar.add(menu);
    menuBar.add(ng);
    f.setJMenuBar(menuBar);
    f.add(g);
    f.pack();
    f.setVisible(true);
    f.setTitle("Snake");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setResizable(false);
    f.setLocationRelativeTo(null);

}
