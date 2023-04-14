package View;

import Model.Helper.Building;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class MainWindow extends JFrame{
    private static final int INITIAL_BOARD_X = 59;
    private static final int INITIAL_BOARD_Y = 31;
    private BoardGUI BoardPanel;


    public MainWindow(String cityName){

        BoardPanel = new BoardGUI(INITIAL_BOARD_X, INITIAL_BOARD_Y);

        setTitle("KukaPest");
        setSize(1500, 1500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);



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
        add(tbClip, BorderLayout.SOUTH);


        // container felvétele, hogy a felugró Build menü vertikális legyen

        Container box = new Container();
        box.setLayout(new GridLayout(0, 2));

        //vertikális toolbar felvétele

        JToolBar buildBar = new JToolBar();

        // a gomb iconok felvétele

        Icon iconSchool = new ImageIcon("KukaPest/Assets/school_button.png");
        Icon iconPolice = new ImageIcon("KukaPest/Assets/police_button.png");
        Icon iconStadium = new ImageIcon("KukaPest/Assets/stadium_button.png");
        Icon iconRoad = new ImageIcon("KukaPest/Assets/road_button.png");
        Icon iconUniversity = new ImageIcon("KukaPest/Assets/university_button.png");
        Icon iconpp = new ImageIcon("KukaPest/Assets/pp_button.png");
        Icon iconresidental = new ImageIcon("KukaPest/Assets/residental_button2.png");
        Icon iconindustrial = new ImageIcon("KukaPest/Assets/industrial_button.png");
        Icon iconpowerPole = new ImageIcon("KukaPest/Assets/power_pole_button.png");


        //a gombok létrehozása és vertikálisan középre igazítása

        JButton buildExit = new JButton("Exit from Build Menu");
        buildExit.setBackground(Color.WHITE);
        JButton buildSchool = new JButton(iconSchool);
        buildSchool.setBackground(Color.WHITE);
        JButton buildPolice = new JButton(iconPolice);
        buildPolice.setBackground(Color.WHITE);
        JButton buildStadium = new JButton(iconStadium);
        buildStadium.setBackground(Color.WHITE);
        JButton buildRoad = new JButton(iconRoad);
        buildRoad.setBackground(Color.WHITE);
        JButton buildUniversity = new JButton(iconUniversity);
        buildUniversity.setBackground(Color.WHITE);
        JButton buildpp = new JButton(iconpp);
        buildpp.setBackground(Color.WHITE);
        JButton buildResidentalZone = new JButton(iconresidental);
        buildResidentalZone.setBackground(Color.WHITE);
        JButton buildIndustrialZone = new JButton(iconindustrial);
        buildIndustrialZone.setBackground(Color.WHITE);
        JButton powerpolebutton = new JButton(iconpowerPole);
        powerpolebutton.setBackground(Color.WHITE);



        // hozzáadjuk a gombokat a box-hoz
        box.add(buildExit);
        box.add(buildSchool);
        box.add(buildPolice);
        box.add(buildStadium);
        box.add(buildRoad);
        box.add(buildUniversity);
        box.add(buildpp);
        box.add(buildResidentalZone);
        box.add(buildIndustrialZone);
        box.add(powerpolebutton);


        //scroll felvétele, amit a box-hoz veszünk fel és azt adjuk át a toolbarnak

        JScrollPane scrollBar=new JScrollPane(box);
        scrollBar.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        buildBar.add(scrollBar);
        buildBar.setFloatable(false);
        buildBar.setOrientation(SwingConstants.VERTICAL);
        buildBar.setVisible(true);


        //StartBar létrehozása

        JToolBar startBar = new JToolBar();

        JLabel label = new JLabel("<html>  Welcome to  <br/>"+ cityName +"!  </html>",SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Cooper Black", Font.BOLD, 30));




        JLabel picLabel = new JLabel(new ImageIcon("KukaPest/Assets/city_gif_2.gif"));


        startBar.add(label);
        startBar.add(picLabel);
        startBar.setFloatable(false);


        startBar.setOrientation(SwingConstants.VERTICAL);
        startBar.setVisible(true);

        JToolBar statBar = new JToolBar();

        JLabel statlabel = new JLabel("<html>Stats: <br/></html>",SwingConstants.CENTER);
        statlabel.setVerticalAlignment(SwingConstants.CENTER);
        statlabel.setFont(new Font("Cooper Black", Font.BOLD, 30));

        JLabel populationlabel = new JLabel("<html>population: " + BoardPanel.getGame().getPopulation() + " <br/></html>",SwingConstants.CENTER);
        populationlabel.setVerticalAlignment(SwingConstants.CENTER);
        populationlabel.setFont(new Font("Cooper Black", Font.BOLD, 15));

        JLabel fundslabel = new JLabel("<html>fundslabel: " + BoardPanel.getGame().getFunds()+ " <br/></html>",SwingConstants.CENTER);
        fundslabel.setVerticalAlignment(SwingConstants.CENTER);
        fundslabel.setFont(new Font("Cooper Black", Font.BOLD, 15));

        statBar.add(statlabel);
        statBar.add(populationlabel);
        statBar.add(fundslabel);

        statBar.setFloatable(false);


        statBar.setOrientation(SwingConstants.VERTICAL);
        statBar.setVisible(true);

        add(startBar,BorderLayout.EAST);
        //add(buildBar,BorderLayout.EAST);

        add(BoardPanel,BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
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




        pack();
        setResizable(true);
        //setLocationRelativeTo(null);
        setVisible(true);




        // figyeli, hogy a Build menübe kattintottunk-e
        build.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {



                remove(startBar);
                remove(statBar);
                repaint();
                add(buildBar,BorderLayout.EAST);
                pack();

                BoardPanel.build = true;
            }
        });
        // figyeli, hogy a Stats menübe kattintottunk-e
        /*stats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                remove(startBar);
                remove(buildBar);
                repaint();
                add(statBar,BorderLayout.EAST);
                pack();
            }
        });*/

        //a build menüben lévő gombok figyelése, a megfelelő épületre való kattintáskor áttadja a BoardGUI-nak
        // az épület nevét, ami ennek segítségével kirajzolja a megfelelő képet, az exit-re való kattintáskor beállítjuk
        //, hogy a BoardGUI.build false-al térjen vissza, így nem fog semmilyen képet kirajzolni és beállítja, hogy a
        // BoardGUI.buildingname-je is alapállapotba ("") kerüljön
        buildExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                remove(buildBar);
                repaint();
                add(startBar,BorderLayout.EAST);
                pack();

                BoardPanel.build = false;
                BoardPanel.selectedBuilding = null;
                Toolkit toolkit= Toolkit.getDefaultToolkit();


            }
        });

        buildSchool.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                BoardPanel.selectedBuilding = Building.SCHOOL;
                //CustomCursor("KukaPest/Assets/School.png");
            }
        });
        buildPolice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                BoardPanel.selectedBuilding = Building.POLICE;
                //CustomCursor("KukaPest/Assets/Police.png");
            }
        });
        buildStadium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                BoardPanel.selectedBuilding = Building.STADIUM;
                //CustomCursor("KukaPest/Assets/Stadium.png");
            }
        });
        buildRoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                BoardPanel.selectedBuilding = Building.ROAD;
                //CustomCursor("KukaPest/Assets/Road");
            }
        });
        buildUniversity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                BoardPanel.selectedBuilding = Building.UNIVERSITY;
                //CustomCursor("KukaPest/Assets/University.png");
            }
        });
        buildpp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                BoardPanel.selectedBuilding = Building.POWER_PLANT;
                //CustomCursor("KukaPest/Assets/PowerPlant.png");
            }
        });
        buildResidentalZone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                BoardPanel.selectedBuilding = Building.RESIDENTIAL;
                //CustomCursor("KukaPest/Assets/ResidentialZone.png");
            }
        });
        buildIndustrialZone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                BoardPanel.selectedBuilding = Building.INDUSTRY;
                //CustomCursor("KukaPest/Assets/IndustrialZone.png");
            }
        });
        powerpolebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                BoardPanel.selectedBuilding = Building.POLE;
                //CustomCursor("KukaPest/Assets/Pole.png");
            }
        });
    }

    public void CustomCursor(String name){
        Toolkit toolkit= Toolkit.getDefaultToolkit();
        Dimension aBestSize = Toolkit.getDefaultToolkit().getBestCursorSize(0, 0);
        Image imageHouse = toolkit.getImage(name);
        Point point = new Point(0,0);
        Cursor c = toolkit.createCustomCursor(imageHouse,point,"c");
        setCursor(c);
    }
}