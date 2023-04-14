package View;

import Model.Helper.Building;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow {
    private static final int INITIAL_BOARD_X = 59;
    private static final int INITIAL_BOARD_Y = 31;
    JFrame frame;


    private BoardGUI BoardPanel;


    public MainWindow(){

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
        //box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setLayout(new GridLayout(0, 2));

        //vertikális toolbar felvétele

        JToolBar buildBar = new JToolBar();
        JPanel buildPanel = new JPanel();

        // a gomb iconok felvétele

        //Icon iconHouse = new ImageIcon("KukaPest/Assets/house_button.png");
        Icon iconSchool = new ImageIcon("KukaPest/Assets/school_button.png");
        Icon iconPolice = new ImageIcon("KukaPest/Assets/police_button.png");
        Icon iconStadium = new ImageIcon("KukaPest/Assets/stadium_button.png");
        Icon iconRoad = new ImageIcon("KukaPest/Assets/road_button.png");
        Icon iconUniversity = new ImageIcon("KukaPest/Assets/university_button.png");
        Icon iconpp = new ImageIcon("KukaPest/Assets/pp_button.png");
        Icon iconresidental = new ImageIcon("KukaPest/Assets/residental_button2.png");
        Icon iconindustrial = new ImageIcon("KukaPest/Assets/industrial_button.png");
        Icon iconpowerPole = new ImageIcon("KukaPest/Assets/power_pole_button.png");
        Icon iconServiceZone = new ImageIcon("KukaPest/Assets/service_button.png");


        //a gombok létrehozása és vertikálisan középre igazítása

        JButton buildExit = new JButton("Exit from Build Menu");
        //buildExit.setAlignmentX(Component.CENTER_ALIGNMENT);
        buildExit.setBackground(Color.WHITE);
        //bulidExit.setAlignmentX(Component.CENTER_ALIGNMENT);
        //JButton buildHouse = new JButton(iconHouse);
        //buildHouse.setAlignmentX(Component.CENTER_ALIGNMENT);
        //buildHouse.setBackground(Color.WHITE);
        JButton buildSchool = new JButton(iconSchool);
        //buildSchool.setAlignmentX(Component.CENTER_ALIGNMENT);
        buildSchool.setBackground(Color.WHITE);
        JButton buildPolice = new JButton(iconPolice);
        //buildPolice.setAlignmentX(Component.CENTER_ALIGNMENT);
        buildPolice.setBackground(Color.WHITE);
        JButton buildStadium = new JButton(iconStadium);
        //buildStadium.setAlignmentX(Component.CENTER_ALIGNMENT);
        buildStadium.setBackground(Color.WHITE);
        JButton buildRoad = new JButton(iconRoad);
        //buildRoad.setAlignmentX(Component.CENTER_ALIGNMENT);
        buildRoad.setBackground(Color.WHITE);
        JButton buildUniversity = new JButton(iconUniversity);
        //buildUniversity.setAlignmentX(Component.CENTER_ALIGNMENT);
        buildUniversity.setBackground(Color.WHITE);
        JButton buildpp = new JButton(iconpp);
        //buildpp.setAlignmentX(Component.CENTER_ALIGNMENT);
        buildpp.setBackground(Color.WHITE);
        JButton buildResidentalZone = new JButton(iconresidental);
        buildResidentalZone.setBackground(Color.WHITE);
        JButton buildIndustrialZone = new JButton(iconindustrial);
        buildIndustrialZone.setBackground(Color.WHITE);
        JButton powerpolebutton = new JButton(iconpowerPole);
        powerpolebutton.setBackground(Color.WHITE);
        //JButton serviceZone = new JButton(iconServiceZone);
        //serviceZone.setBackground(Color.WHITE);


        // hozzáadjuk a gombokat a box-hoz
        box.add(buildExit);
        //box.add(buildHouse);
        box.add(buildSchool);
        box.add(buildPolice);
        box.add(buildStadium);
        box.add(buildRoad);
        box.add(buildUniversity);
        box.add(buildpp);
        box.add(buildResidentalZone);
        box.add(buildIndustrialZone);
        box.add(powerpolebutton);
        //box.add(serviceZone);


        //scroll felvétele, amit a box-hoz veszünk fel és azt adjuk át a toolbarnak

        JScrollPane scrollBar=new JScrollPane(box);
        scrollBar.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        buildBar.add(scrollBar);

        buildBar.setOrientation(SwingConstants.VERTICAL);
        buildBar.setVisible(false);
        frame.add(buildBar,BorderLayout.EAST);
        frame.setTitle("Build Items");

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image imageHouse = toolkit.getImage("KukaPest/Assets/stadium.png");
        Cursor c = toolkit.createCustomCursor(imageHouse , new Point(frame.getX(),
                frame.getY()), "img");



        // figyeli, hogy a Build menübe kattintottunk-e
        build.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                buildBar.setVisible(true);
                BoardPanel.build = true;
                frame.setSize(INITIAL_BOARD_X* 20+300,INITIAL_BOARD_Y * 20+100);
            }
        });

        //a build menüben lévő gombok figyelése, a megfelelő épületre való kattintáskor áttadja a BoardGUI-nak
        // az épület nevét, ami ennek segítségével kirajzolja a megfelelő képet, az exit-re való kattintáskor beállítjuk
        //, hogy a BoardGUI.build false-al térjen vissza, így nem fog semmilyen képet kirajzolni és beállítja, hogy a
        // BoardGUI.buildingname-je is alapállapotba ("") kerüljön
        buildExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                frame.setSize(INITIAL_BOARD_X* 20,INITIAL_BOARD_Y * 20+100);
                buildBar.setVisible(false);
                BoardPanel.build = false;
                BoardPanel.selectedBuilding = null;
                Toolkit toolkit= Toolkit.getDefaultToolkit();
                //frame.setCursor();
                //Cursor.getDefaultCursor();
                System.out.println("valami");


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


        frame.setSize(INITIAL_BOARD_X* 20,INITIAL_BOARD_Y * 20+100);
        frame.setVisible(true);
    }

    public void CustomCursor(String name){
        Toolkit toolkit= Toolkit.getDefaultToolkit();
        Dimension aBestSize = Toolkit.getDefaultToolkit().getBestCursorSize(0, 0);
        Image imageHouse = toolkit.getImage(name);
        Point point = new Point(0,0);
        Cursor c = toolkit.createCustomCursor(imageHouse,point,"c");
        frame.setCursor(c);
    }
}