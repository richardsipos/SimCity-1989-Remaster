package View;

import Model.Game;

import javax.swing.*;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow {
    private static final int INITIAL_BOARD_X = 32;
    private static final int INITIAL_BOARD_Y = 18;
    JFrame frame;


    private BoardGUI BoardPanel;


    public MainWindow(){
        BoardPanel = new BoardGUI(INITIAL_BOARD_X, INITIAL_BOARD_Y);

        frame = new JFrame("KukaPest");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JPopupMenu popup = new JPopupMenu();
        popup.add(new JMenuItem(new AbstractAction("Road") {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Road selected");
            }
        }));
        popup.add(new JMenuItem(new AbstractAction("Residential zone") {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Residential zone selected");
            }
        }));
        popup.add(new JMenuItem(new AbstractAction("School") {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "School selected");
            }
        }));
        popup.add(new JMenuItem(new AbstractAction("University") {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "University selected");
            }
        }));
        popup.add(new JMenuItem(new AbstractAction("Police") {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Police selected");
            }
        }));
        popup.add(new JMenuItem(new AbstractAction("Stadium") {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Stadium selected");
            }
        }));
        popup.add(new JMenuItem(new AbstractAction("Power plant") {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Power plant selected");
            }
        }));


        Container buttons = frame.getContentPane();
        buttons.setLayout(new BorderLayout());
        JButton build = new JButton("Build");
        build.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        });
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


        JMenuItem ngMenuItem = new JMenuItem("New Game");
        ngMenuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {

            }
        });
        gameMenu.add(ngMenuItem);
        JMenuItem lgMenuItem = new JMenuItem("Load Game");
        //lgMenuItem.addActionListener(new ActionListener());
        gameMenu.add(lgMenuItem);
        JMenuItem sgMenuItem = new JMenuItem("Save Game");
        //sgMenuItem.addActionListener(new ActionListener());
        gameMenu.add(sgMenuItem);
        JMenuItem crMenuItem = new JMenuItem("Credits");
        //sgMenuItem.addActionListener(new ActionListener());
        gameMenu.add(crMenuItem);
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        gameMenu.add(exitMenuItem);
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });


        frame.setSize(INITIAL_BOARD_X* 40+ 125,INITIAL_BOARD_Y * 40 + 150);
        frame.setVisible(true);
    }
}