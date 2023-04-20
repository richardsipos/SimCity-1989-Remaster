package View;

import Model.Helper.Building;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame{
    private static final int INITIAL_BOARD_X = 59;
    private static final int INITIAL_BOARD_Y = 31;
    private BoardGUI BoardPanel;
    JLabel populationlabel = new JLabel();
    JLabel fundslabel = new JLabel();
    JLabel balancelabel = new JLabel();
    Timer gameTime;
    String cityname;


    public MainWindow(String cityName){

        cityname = cityName;
        BoardPanel = new BoardGUI(INITIAL_BOARD_X, INITIAL_BOARD_Y);

        gameTime = new Timer(1000,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                BoardPanel.getGame().stepGame();
                System.out.println("Lakók: " + BoardPanel.getGame().getPopulation() + "\nPézz: " + BoardPanel.getGame().getFunds()+ "\n\n");
                refreshGameStatLabel();

            }
        });
        gameTime.start();

        setTitle("KukaPest");
        setSize(1500, 1500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //alsó menü gombok létrehozása

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


        //alsó menü létrehozás

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

        JButton buildExit = new JButton("<html>Exit from <br/>Build Menu</html>");
        buildExit.setBackground(Color.WHITE);
        buildExit.setPreferredSize(new Dimension(125,122));
        JButton buildSchool = new JButton(iconSchool);
        buildSchool.setBackground(Color.WHITE);
        buildSchool.setPreferredSize(new Dimension(125,122));
        JButton buildPolice = new JButton(iconPolice);
        buildPolice.setBackground(Color.WHITE);
        buildPolice.setPreferredSize(new Dimension(125,122));
        JButton buildStadium = new JButton(iconStadium);
        buildStadium.setBackground(Color.WHITE);
        buildStadium.setPreferredSize(new Dimension(125,122));
        JButton buildRoad = new JButton(iconRoad);
        buildRoad.setBackground(Color.WHITE);
        buildRoad.setPreferredSize(new Dimension(125,122));
        JButton buildUniversity = new JButton(iconUniversity);
        buildUniversity.setBackground(Color.WHITE);
        buildUniversity.setPreferredSize(new Dimension(125,122));
        JButton buildpp = new JButton(iconpp);
        buildpp.setBackground(Color.WHITE);
        buildpp.setPreferredSize(new Dimension(125,122));
        JButton buildResidentalZone = new JButton(iconresidental);
        buildResidentalZone.setBackground(Color.WHITE);
        buildResidentalZone.setPreferredSize(new Dimension(125,122));
        JButton buildIndustrialZone = new JButton(iconindustrial);
        buildIndustrialZone.setBackground(Color.WHITE);
        buildIndustrialZone.setPreferredSize(new Dimension(125,122));
        JButton powerpolebutton = new JButton(iconpowerPole);
        powerpolebutton.setBackground(Color.WHITE);
        powerpolebutton.setPreferredSize(new Dimension(125,122));


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
        scrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

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


        //StatusBar létrehozása

        JToolBar statBar = new JToolBar();

        JLabel statlabel = new JLabel("<html>Stats:<br/><br/></html>");
        statlabel.setFont(new Font("Cooper Black", Font.BOLD, 30));



        populationlabel.setBackground(Color.WHITE);
        populationlabel.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(
                                EtchedBorder.RAISED, Color.GRAY
                                , Color.DARK_GRAY), "Population"));

        populationlabel.setText("" + BoardPanel.getGame().getPopulation() + " people");

        populationlabel.setFont(new Font("Cooper Black", Font.BOLD, 15));
        populationlabel.setPreferredSize(new Dimension(250,50));
        populationlabel.setMinimumSize(new Dimension(250,50));
        populationlabel.setMaximumSize(new Dimension(250,50));

        fundslabel.setBackground(Color.WHITE);
        fundslabel.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(
                                EtchedBorder.RAISED, Color.GRAY
                                , Color.DARK_GRAY), "Funds"));
        fundslabel.setText(BoardPanel.getGame().getFunds()+ " $");

        fundslabel.setFont(new Font("Cooper Black", Font.BOLD, 15));

        fundslabel.setPreferredSize(new Dimension(250,50));
        fundslabel.setMinimumSize(new Dimension(250,50));
        fundslabel.setMaximumSize(new Dimension(250,50));

        balancelabel.setBackground(Color.WHITE);
        balancelabel.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(
                                EtchedBorder.RAISED, Color.GRAY
                                , Color.DARK_GRAY), "Last balance"));
        balancelabel.setText((BoardPanel.getGame().getLastBalance()[0] + BoardPanel.getGame().getLastBalance()[1]) + " $");

        balancelabel.setFont(new Font("Cooper Black", Font.BOLD, 15));

        balancelabel.setPreferredSize(new Dimension(250,50));
        balancelabel.setMinimumSize(new Dimension(250,50));
        balancelabel.setMaximumSize(new Dimension(250,50));


        statBar.add(statlabel);
        statBar.add(populationlabel);
        statBar.add(fundslabel);
        statBar.add(balancelabel);

        statBar.setFloatable(false);


        statBar.setOrientation(SwingConstants.VERTICAL);
        statBar.setVisible(true);

        add(startBar,BorderLayout.EAST);

        add(BoardPanel,BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu gameMenu = new JMenu("Game");
        menuBar.add(gameMenu,BorderLayout.SOUTH);


        JMenuItem ngMenuItem = new JMenuItem("New Game");
        gameMenu.add(ngMenuItem);
        JMenuItem lgMenuItem = new JMenuItem("Load Game");
        gameMenu.add(lgMenuItem);
        JMenuItem sgMenuItem = new JMenuItem("Save Game");
        gameMenu.add(sgMenuItem);
        JMenuItem crMenuItem = new JMenuItem("Credits");
        gameMenu.add(crMenuItem);
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        gameMenu.add(exitMenuItem);


        pack();
        setResizable(false);
        setVisible(true);



        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });


        // figyeli, hogy a Build menübe kattintottunk-e
        build.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {



                remove(startBar);
                remove(statBar);
                add(buildBar,BorderLayout.EAST);
                pack();

                BoardPanel.build = true;
            }
        });
        // figyeli, hogy a Stats menübe kattintottunk-e
        stats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                remove(startBar);
                remove(buildBar);
                add(statBar,BorderLayout.EAST);

                pack();
                BoardPanel.build = false;
            }
        });

        //a build menüben lévő gombok figyelése, a megfelelő épületre való kattintáskor áttadja a BoardGUI-nak
        // az épület nevét, ami ennek segítségével kirajzolja a megfelelő képet, az exit-re való kattintáskor beállítjuk
        //, hogy a BoardGUI.build false-al térjen vissza, így nem fog semmilyen képet kirajzolni és beállítja, hogy a
        // BoardGUI.buildingname-je is alapállapotba ("") kerüljön
        buildExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                remove(buildBar);
                add(startBar,BorderLayout.EAST);
                pack();

                BoardPanel.build = false;
                BoardPanel.selectedBuilding = null;


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
        buildResidentalZone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                BoardPanel.selectedBuilding = Building.RESIDENTIAL;
            }
        });
        buildIndustrialZone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                BoardPanel.selectedBuilding = Building.INDUSTRY;
            }
        });
        powerpolebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                BoardPanel.selectedBuilding = Building.POLE;
            }
        });
    }

    /**
     *Refresh Stats Label
     */
    private void refreshGameStatLabel(){

        populationlabel.setText(BoardPanel.getGame().getPopulation() + " people");
        fundslabel.setText(BoardPanel.getGame().getFunds()+ " $");
        balancelabel.setText((BoardPanel.getGame().getLastBalance()[0] + BoardPanel.getGame().getLastBalance()[1]) + " $");
    }

}