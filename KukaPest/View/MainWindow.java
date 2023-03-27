package View;

import Model.Game;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainWindow {
    public JFrame f;
    GameGraphics g;
    public MainWindow(){
        f = new JFrame();
        g = new GameGraphics();

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");


        JMenuItem ngMenuItem = new JMenuItem("New Game");
        //ngMenuItem.addActionListener(new OpenActionListener());
        menu.add(ngMenuItem);
        JMenuItem lgMenuItem = new JMenuItem("Load Game");
        //lgMenuItem.addActionListener(new OpenActionListener());
        menu.add(lgMenuItem);
        JMenuItem sgMenuItem = new JMenuItem("Save Game");
        //sgMenuItem.addActionListener(new SaveActionListener());
        menu.add(sgMenuItem);
        JMenuItem crMenuItem = new JMenuItem("Credits");
        //sgMenuItem.addActionListener(new OpenActionListener());
        menu.add(crMenuItem);
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        menu.add(exitMenuItem);
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });

        menuBar.add(menu);
        f.setJMenuBar(menuBar);
        f.add(g);
        f.pack();
        f.setVisible(true);
        f.setTitle("KukaPest");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
    }

    public static void main(String[] args){
            new MainWindow();
    }

}



class OpenActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

class SaveActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
