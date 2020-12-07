package src; /**
*Text genereted by Simple GUI Extension for BlueJ
*/
import java.awt.*;
import javax.swing.*;


public class SpielGui extends JFrame {

	private JMenuBar menuBar;
	private JTextArea Leben;
	private JTextArea Punktzahl;
	private JButton einstellungen;
	private JPanel panel1;

	//Constructor 
	public SpielGui(){

		this.setTitle("Spiel");
		this.setSize(500,400);
		//menu generate method
		generateMenu();
		this.setJMenuBar(menuBar);

		//pane with null layout
		JPanel contentPane = new JPanel(null);
		contentPane.setPreferredSize(new Dimension(500,400));
		contentPane.setBackground(new Color(192,192,193));


		Leben = new JTextArea();
		Leben.setBounds(205,16,100,20);
		Leben.setBackground(new Color(255,255,255));
		Leben.setForeground(new Color(0,0,0));
		Leben.setEnabled(true);
		Leben.setFont(new Font("sansserif",0,12));
		Leben.setText("");
		Leben.setBorder(BorderFactory.createBevelBorder(1));
		Leben.setVisible(true);

		Punktzahl = new JTextArea();
		Punktzahl.setBounds(48,15,100,20);
		Punktzahl.setBackground(new Color(255,255,255));
		Punktzahl.setForeground(new Color(0,0,0));
		Punktzahl.setEnabled(true);
		Punktzahl.setFont(new Font("sansserif",0,12));
		Punktzahl.setText("");
		Punktzahl.setBorder(BorderFactory.createBevelBorder(1));
		Punktzahl.setVisible(true);

		einstellungen = new JButton();
		einstellungen.setBounds(460,5,35,35);
		einstellungen.setBackground(new Color(192,192,193));
		einstellungen.setForeground(new Color(0,0,0));
		einstellungen.setEnabled(false);
		einstellungen.setFont(new Font("sansserif",0,12));
		einstellungen.setText("Button1");
		einstellungen.setVisible(true);

		panel1 = new JPanel(null);
		panel1.setBorder(BorderFactory.createEtchedBorder(1));
		panel1.setBounds(34,102,150,100);
		panel1.setBackground(new Color(214,217,223));
		panel1.setForeground(new Color(0,0,0));
		panel1.setEnabled(true);
		panel1.setFont(new Font("sansserif",0,12));
		panel1.setVisible(true);

		//adding components to contentPane panel
		contentPane.add(Leben);
		contentPane.add(Punktzahl);
		contentPane.add(einstellungen);
		contentPane.add(panel1);

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



	 public static void main(String[] args){
		//System.setProperty("swing.defaultlaf", "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new SpielGui();
			}
		});
	}

}