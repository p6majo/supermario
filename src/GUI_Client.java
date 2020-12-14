package src;

/**
*Text genereted by Simple GUI Extension for BlueJ
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.Font.BOLD;
import static javax.swing.SwingConstants.CENTER;


public class GUI_Client extends JFrame implements SuperMarioGui{

	private JMenuBar menuBar;
	private JButton pingButton;
	private JButton loginButton;
	private JButton connectButton;
	private JTextField punkteTextField;
	private JTextField lebensTextField;
	private JTextField titleTextField;
	private JTextField highScoreTextField;
	private JTextField ipTextField;
	private JTextField portTextField;

	private WeltView weltView;
	private Spieler guiSpieler;
	private Nutzer nutzer;

	private SuperMarioClient client;

	//Constructor
	public GUI_Client(){

		this.setTitle("SuperMarioClient");
		this.setSize(500,400);
		//menu generate method
		generateMenu();
		this.setJMenuBar(menuBar);

		//pane with null layout
		JPanel contentPane = new JPanel(null);
		contentPane.setPreferredSize(new Dimension(500,400));
		contentPane.setBackground(new Color(192,192,192));


		pingButton = new JButton();
		pingButton.setBounds(195,152,90,35);
		pingButton.setBackground(new Color(214,217,223));
		pingButton.setForeground(new Color(0,0,0));
		pingButton.setEnabled(true);
		pingButton.setFont(new Font("sansserif",0,12));
		pingButton.setText("Ping");
		pingButton.setVisible(true);
		pingButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				ping();
			}
		});
		pingButton.setEnabled(false);




		loginButton = new JButton();
		loginButton.setBounds(195,192,90,35);
		loginButton.setBackground(new Color(214,217,223));
		loginButton.setForeground(new Color(0,0,0));
		loginButton.setEnabled(true);
		loginButton.setFont(new Font("sansserif",0,12));
		loginButton.setText("Log In");
		loginButton.setVisible(true);
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				login();
			}
		});

		connectButton = new JButton();
		connectButton.setBounds(195,232,90,35);
		connectButton.setBackground(new Color(214,217,223));
		connectButton.setForeground(new Color(0,0,0));
		connectButton.setEnabled(true);
		connectButton.setFont(new Font("sansserif",0,12));
		connectButton.setText("Connect");
		connectButton.setVisible(true);
		connectButton.setEnabled(false);
		connectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				connectButton.setEnabled(false);
				connectToServer();
				pingButton.setEnabled(true);
			}
		});

		ipTextField = new JTextField();
		ipTextField.setBounds(350,232,120,22);
		ipTextField.setBackground(new Color(255,255,255));
		ipTextField.setForeground(new Color(0,0,0));
		ipTextField.setEnabled(true);
		ipTextField.setFont(new Font("sansserif",BOLD,12));
		ipTextField.setText("127.0.0.1");
		ipTextField.setVisible(true);
		ipTextField.setHorizontalAlignment(CENTER);

		portTextField = new JTextField();
		portTextField.setBounds(350,260,120,22);
		portTextField.setBackground(new Color(255,255,255));
		portTextField.setForeground(new Color(0,0,0));
		portTextField.setEnabled(true);
		portTextField.setFont(new Font("sansserif",BOLD,12));
		portTextField.setText("12345");
		portTextField.setBorder(BorderFactory.createBevelBorder(1));
		portTextField.setVisible(true);
		portTextField.setHorizontalAlignment(CENTER);

		punkteTextField = new JTextField();
		punkteTextField.setBounds(5,5,120,22);
		punkteTextField.setBackground(new Color(255,255,255));
		punkteTextField.setForeground(new Color(0,0,0));
		punkteTextField.setEnabled(true);
		punkteTextField.setFont(new Font("sansserif",BOLD,12));
		punkteTextField.setText("Punkteanzeige");
		punkteTextField.setBorder(BorderFactory.createBevelBorder(1));
		punkteTextField.setVisible(true);
		punkteTextField.setEnabled(false);
		punkteTextField.setHorizontalAlignment(CENTER);

		lebensTextField = new JTextField();
		lebensTextField.setBounds(190,6,120,22);
		lebensTextField.setBackground(new Color(255,255,255));
		lebensTextField.setForeground(new Color(0,0,0));
		lebensTextField.setEnabled(true);
		lebensTextField.setFont(new Font("sansserif",BOLD,12));
		lebensTextField.setText("Lebensanzeige");
		lebensTextField.setBorder(BorderFactory.createBevelBorder(1));
		lebensTextField.setVisible(true);
		lebensTextField.setEnabled(false);
		lebensTextField.setHorizontalAlignment(CENTER);

		titleTextField = new JTextField();
		titleTextField.setBounds(135,70,211,54);
		titleTextField.setBackground(new Color(255,255,255));
		titleTextField.setForeground(new Color(0,0,0));
		titleTextField.setEnabled(true);
		titleTextField.setFont(new Font("SansSerif",0,30));
		titleTextField.setText("Super Mario");
		titleTextField.setBorder(BorderFactory.createBevelBorder(1));
		titleTextField.setVisible(true);
		titleTextField.setEnabled(false);
		titleTextField.setHorizontalAlignment(CENTER);

		highScoreTextField = new JTextField();
		highScoreTextField.setBounds(5,31,120,22);
		highScoreTextField.setBackground(new Color(255,255,255));
		highScoreTextField.setForeground(new Color(0,0,0));
		highScoreTextField.setEnabled(true);
		highScoreTextField.setFont(new Font("sansserif",BOLD,12));
		highScoreTextField.setText("Highscore");
		highScoreTextField.setBorder(BorderFactory.createBevelBorder(1));
		highScoreTextField.setVisible(true);
		highScoreTextField.setEnabled(false);
		highScoreTextField.setHorizontalAlignment(CENTER);

		//adding components to contentPane panel
		contentPane.add(pingButton);
		contentPane.add(loginButton);
		contentPane.add(connectButton);
		contentPane.add(punkteTextField);
		contentPane.add(lebensTextField);
		contentPane.add(titleTextField);
		contentPane.add(highScoreTextField);
		contentPane.add(ipTextField);
		contentPane.add(portTextField);

		//adding panel to JFrame and seting of window position and close operation
		this.add(contentPane);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);
	}

	//method for generate menu
	public void generateMenu(){
		menuBar = new JMenuBar();

		JMenu file = new JMenu("File");
		JMenu tools = new JMenu("Tools");
		JMenu help = new JMenu("Help");

		JMenuItem open = new JMenuItem("Open   ");
		JMenuItem save = new JMenuItem("Save   ");
		JMenuItem exit = new JMenuItem("Exit   ");
		JMenuItem preferences = new JMenuItem("Preferences   ");
		JMenuItem about = new JMenuItem("About   ");


		file.add(open);
		file.add(save);
		file.addSeparator();
		file.add(exit);
		tools.add(preferences);
		help.add(about);

		menuBar.add(file);
		menuBar.add(tools);
		menuBar.add(help);
	}



	private void login(){
		connectButton.setEnabled(false);
		new DatenbankGui(this);
	}

	 public static void main(String[] args){
		//System.setProperty("swing.defaultlaf", "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GUI_Client();
			}
		});
	}

	private void connectToServer(){
		client = new SuperMarioClient(ipTextField.getText(),Integer.parseInt(portTextField.getText()),nutzer.getVorname(),nutzer.getName());
	}

	private void ping(){
		client.ping();
	}



	@Override
	public void update(){
		punkteTextField.setText(String.format("%04d",guiSpieler.getPunkte()));
		lebensTextField.setText(String.format("%02d",guiSpieler.getLebenszahl()));

	}


	@Override
	public void draw() {
		weltView.draw();
	}

	@Override
	public void loginNutzer(Nutzer pNutzer){
		nutzer = pNutzer;
		connectButton.setEnabled(true);
		loginButton.setEnabled(false);
	}
}