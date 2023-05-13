package KukaPest.View;

import KukaPest.Model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;

public class MenuWindow extends JPanel {
    JFrame frame;
    private MainWindow main;
    Image background;
    ImageIcon bg, ng, ngactive, load, loadactive, exit, exitactive;

    String name = "";

    @Override
    public String getName() {
        return name;
    }

    public MenuWindow(){
        frame = new JFrame("KukaPest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        bg = new ImageIcon("src/main/java/KukaPest/Assets/main2.jpg");
        background = new ImageIcon("src/main/java/KukaPest/Assets/main2.jpg").getImage();
        ng = new ImageIcon("src/main/java/KukaPest/Assets/playinactive.png");
        ngactive = new ImageIcon("src/main/java/KukaPest/Assets/playactive.png");
        load = new ImageIcon("src/main/java/KukaPest/Assets/loadinactive.png");
        loadactive = new ImageIcon("src/main/java/KukaPest/Assets/loadactive.png");
        exit = new ImageIcon("src/main/java/KukaPest/Assets/exitinactive.png");
        exitactive = new ImageIcon("src/main/java/KukaPest/Assets/exitactive.png");

        JLabel contentPane = new JLabel();
        contentPane.setIcon(bg);
        contentPane.setLayout( new GridBagLayout() );
        frame.setContentPane( contentPane );

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridBagLayout());
        buttons.setOpaque(false);

        //GridBagConstraints for vertical arrangement of buttons
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 0;
        gbc2.gridy = 1;
        gbc2.weightx = 1;
        gbc2.weighty = 1;
        gbc2.fill = GridBagConstraints.HORIZONTAL;

        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.gridx = 0;
        gbc3.gridy = 2;
        gbc3.weightx = 1;
        gbc3.weighty = 1;
        gbc3.fill = GridBagConstraints.HORIZONTAL;

        JButton ngame = new JButton();
        ngame.setIcon(ng);
        ngame.setRolloverIcon(ngactive);
        ngame.setOpaque(false);
        ngame.setContentAreaFilled(false);
        ngame.setBorderPainted(false);
        ngame.setFocusPainted(false);

        ngame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                name = JOptionPane.showInputDialog(null, "Your city's name:", "");
                if (name != null) {
                    if (!name.equals("")){
                        frame.setVisible(false);
                        new MainWindow(name, false);
                    }
                }
                else name = "";
            }
        });

        JButton lgame = new JButton();
        lgame.setIcon(load);
        lgame.setRolloverIcon(loadactive);
        lgame.setOpaque(false);
        lgame.setContentAreaFilled(false);
        lgame.setBorderPainted(false);
        lgame.setFocusPainted(false);

        JButton egame = new JButton();
        egame.setIcon(exit);
        egame.setRolloverIcon(exitactive);
        egame.setOpaque(false);
        egame.setContentAreaFilled(false);
        egame.setBorderPainted(false);
        egame.setFocusPainted(false);

        egame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });

        //setting vertical or horizontal arrangement
        buttons.add(ngame, gbc);
        buttons.add(lgame, gbc2);
        buttons.add(egame, gbc3);
        /*buttons.add(ngame); //setting vertical or horizontal arrangement
        buttons.add(lgame);
        buttons.add(egame);*/
        frame.add(buttons);


        frame.setSize(1280,720);
        frame.setResizable(false);
        //frame.setUndecorated(true); //hiding the title bar
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        egame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });

        lgame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                new MainWindow(name,true);

            }
        });

        //buttons.add(ngame, gbc);
        //buttons.add(lgame, gbc2);
        //buttons.add(egame, gbc3);

    }


}
