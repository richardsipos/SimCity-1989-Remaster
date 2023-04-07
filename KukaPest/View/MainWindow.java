package View;

import Model.Game;
import Model.Helper.Building;

import javax.swing.*;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow {
    private static final int INITIAL_BOARD_X = 32;
    private static final int INITIAL_BOARD_Y = 18;
    JFrame frame;


    private BoardGUI BoardPanel;


    public MainWindow(String cityName){

        BoardPanel = new BoardGUI(INITIAL_BOARD_X, INITIAL_BOARD_Y);

        frame = new JFrame("KukaPest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JButton build = new JButton("Build");
        JButton destroy = new JButton("Destroy");
        JButton upgrade = new JButton("Upgrade");
        JButton stats = new JButton("Stats");

        //alsó menü gombok beállításai

        build.setFont(new Font("Arial", Font.BOLD, 15));
        build.setBackground(Color.WHITE);
        destroy.setFont(new Font("Arial", Font.BOLD, 15));
        destroy.setBackground(Color.WHITE);
        upgrade.setFont(new Font("Arial", Font.BOLD, 15));
        upgrade.setBackground(Color.WHITE);
        stats.setFont(new Font("Arial", Font.BOLD, 15));
        stats.setBackground(Color.WHITE);

        JToolBar tbClip = new JToolBar();
        JPanel panel = new JPanel();
        panel.add(build);
        panel.add(destroy);
        panel.add(upgrade);
        panel.add(stats);
        tbClip.add(panel);
        frame.add(tbClip, BorderLayout.SOUTH);


        // container felvétele, hogy a felugró Build menü vertikális legyen

        Container box = new Container();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));

        //vertikális toolbar felvétele

        JToolBar buildBar = new JToolBar();
        JPanel buildPanel = new JPanel();

        // a gomb iconok felvétele

        Icon iconHouse = new ImageIcon("KukaPest/Assets/house.png");
        Icon iconSchool = new ImageIcon("KukaPest/Assets/school.png");
        Icon iconPolice = new ImageIcon("KukaPest/Assets/police.png");
        Icon iconStadium = new ImageIcon("KukaPest/Assets/stadium.png");
        Icon iconRoad = new ImageIcon("KukaPest/Assets/road.png");
        Icon iconUniversity = new ImageIcon("KukaPest/Assets/univ.png");
        Icon iconpp = new ImageIcon("KukaPest/Assets/pp.png");


        //a gombok létrehozása és vertikálisan középre igazítása

        JButton buildExit = new JButton("Exit from Build Menu");
        buildExit.setAlignmentX(Component.CENTER_ALIGNMENT);
        //bulidExit.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton buildHouse = new JButton(iconHouse);
        buildHouse.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton buildSchool = new JButton(iconSchool);
        buildSchool.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton buildPolice = new JButton(iconPolice);
        buildPolice.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton buildStadium = new JButton(iconStadium);
        buildStadium.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton buildRoad = new JButton(iconRoad);
        buildRoad.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton buildUniversity = new JButton(iconUniversity);
        buildUniversity.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton buildpp = new JButton(iconpp);
        buildpp.setAlignmentX(Component.CENTER_ALIGNMENT);

        // hozzáadjuk a gombokat a box-hoz
        box.add(buildExit);
        box.add(buildHouse);
        box.add(buildSchool);
        box.add(buildPolice);
        box.add(buildStadium);
        box.add(buildRoad);
        box.add(buildUniversity);
        box.add(buildpp);


        //scroll felvétele, amit a box-hoz veszünk fel és azt adjuk át a toolbarnak

        JScrollPane scrollBar=new JScrollPane(box);
        scrollBar.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        buildBar.add(scrollBar);

        buildBar.setOrientation(SwingConstants.VERTICAL);
        buildBar.setVisible(false);
        frame.add(buildBar,BorderLayout.EAST);
        frame.setTitle("Build Items");

        // figyeli, hogy a Build menübe kattintottunk-e
        build.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                buildBar.setVisible(true);
                BoardPanel.build = true;
            }
        });

        //a build menüben lévő gombok figyelése, a megfelelő épületre való kattintáskor áttadja a BoardGUI-nak
        // az épület nevét, ami ennek segítségével kirajzolja a megfelelő képet, az exit-re való kattintáskor beállítjuk
        //, hogy a BoardGUI.build false-al térjen vissza, így nem fog semmilyen képet kirajzolni és beállítja, hogy a
        // BoardGUI.buildingname-je is alapállapotba ("") kerüljön
        buildExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                buildBar.setVisible(false);
                BoardPanel.build = false;
                BoardPanel.selectedBuilding = null;
            }
        });
        buildHouse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                BoardPanel.selectedBuilding = Building.RESIDENTIAL;
            }
        });
        buildSchool.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                BoardPanel.selectedBuilding = Building.SCHOOL;
            }
        });
        buildPolice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                BoardPanel.selectedBuilding = Building.POLICE;
            }
        });
        buildStadium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                BoardPanel.selectedBuilding = Building.STADIUM;
            }
        });
        buildRoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                BoardPanel.selectedBuilding = Building.ROAD;
            }
        });
        buildUniversity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                BoardPanel.selectedBuilding = Building.UNIVERSITY;
            }
        });
        buildpp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                BoardPanel.selectedBuilding = Building.POWER_PLANT;
            }
        });





        frame.add(BoardPanel,BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu gameMenu = new JMenu("Game");
        menuBar.add(gameMenu,BorderLayout.SOUTH);


        JMenuItem ngMenuItem = new JMenuItem("New Game");
        //ngMenuItem.addActionListener(new OpenActionListener());
        gameMenu.add(ngMenuItem);
        JMenuItem lgMenuItem = new JMenuItem("Load Game");
        //lgMenuItem.addActionListener(new OpenActionListener());
        gameMenu.add(lgMenuItem);
        JMenuItem sgMenuItem = new JMenuItem("Save Game");
        //sgMenuItem.addActionListener(new SaveActionListener());
        gameMenu.add(sgMenuItem);
        JMenuItem crMenuItem = new JMenuItem("Credits");
        //sgMenuItem.addActionListener(new OpenActionListener());
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