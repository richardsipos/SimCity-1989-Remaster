package View;

import Model.Game;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow {
    private static final int INITIAL_BOARD_SIZE = 10;
    JFrame frame;
    JLabel gameStatLabel;
    JLabel winnerLabel;


    private BoardGUI BoardPanel;


    MainWindow(){
        BoardPanel = new BoardGUI(INITIAL_BOARD_SIZE);

        frame = new JFrame("KukaPest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        Container buttons = frame.getContentPane();
        buttons.setLayout(new BorderLayout());
        JButton build = new JButton("Build");
        JButton destroy = new JButton("Destroy");
        JButton upgrade = new JButton("Upgrade");
        JButton stats = new JButton("Stats");

        JToolBar tbClip = new JToolBar();
        tbClip.add(build);
        tbClip.add(destroy);
        tbClip.add(upgrade);
        tbClip.add(stats);
        frame.add(tbClip, BorderLayout.SOUTH);





        frame.add(BoardPanel,BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu gameMenu = new JMenu("Game");
        menuBar.add(gameMenu,BorderLayout.SOUTH);
        JMenu newMenu = new JMenu("New");
        gameMenu.add(newMenu);
        int[] boardSizes = new int[]{3, 5, 7};
        frame.setSize(INITIAL_BOARD_SIZE * 60 + 125,INITIAL_BOARD_SIZE * 60 + 150);
        frame.setVisible(true);

        for (int boardSize : boardSizes) {
            JMenuItem sizeMenuItem = new JMenuItem(boardSize + "x" + boardSize);
            newMenu.add(sizeMenuItem);

        }

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        gameMenu.add(exitMenuItem);
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });




    }










}