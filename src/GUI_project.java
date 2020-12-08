package src;

/**
*Text genereted by Simple GUI Extension for BlueJ
*/
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.border.Border;
import javax.swing.*;


public class GUI_project extends JFrame {

	private JMenuBar menuBar;
	private JButton Button2;
	private JButton button1;
	private JButton button3;
	private JButton button4;
	private JTextArea textarea1;
	private JTextArea textarea2;
	private JTextArea textarea3;
	private JTextArea textarea4;

	//Constructor 
	public GUI_project(){

		this.setTitle("GUI_project");
		this.setSize(500,400);
		//menu generate method
		generateMenu();
		this.setJMenuBar(menuBar);

		//pane with null layout
		JPanel contentPane = new JPanel(null);
		contentPane.setPreferredSize(new Dimension(500,400));
		contentPane.setBackground(new Color(192,192,192));


		Button2 = new JButton();
		Button2.setBounds(195,152,90,35);
		Button2.setBackground(new Color(214,217,223));
		Button2.setForeground(new Color(0,0,0));
		Button2.setEnabled(true);
		Button2.setFont(new Font("sansserif",0,12));
		Button2.setText("Play");
		Button2.setVisible(true);

		button1 = new JButton();
		button1.setBounds(450,5,45,45);
		button1.setBackground(new Color(214,217,223));
		button1.setForeground(new Color(0,0,0));
		button1.setEnabled(true);
		button1.setFont(new Font("sansserif",0,12));
		button1.setText("Button1");
		button1.setVisible(true);

		button3 = new JButton();
		button3.setBounds(195,192,90,35);
		button3.setBackground(new Color(214,217,223));
		button3.setForeground(new Color(0,0,0));
		button3.setEnabled(true);
		button3.setFont(new Font("sansserif",0,12));
		button3.setText("Log In");
		button3.setVisible(true);

		button4 = new JButton();
		button4.setBounds(195,232,90,35);
		button4.setBackground(new Color(214,217,223));
		button4.setForeground(new Color(0,0,0));
		button4.setEnabled(true);
		button4.setFont(new Font("sansserif",0,12));
		button4.setText("Menu");
		button4.setVisible(true);

		textarea1 = new JTextArea();
		textarea1.setBounds(5,5,120,22);
		textarea1.setBackground(new Color(255,255,255));
		textarea1.setForeground(new Color(0,0,0));
		textarea1.setEnabled(true);
		textarea1.setFont(new Font("sansserif",0,12));
		textarea1.setText("Punkteanzeige");
		textarea1.setBorder(BorderFactory.createBevelBorder(1));
		textarea1.setVisible(true);

		textarea2 = new JTextArea();
		textarea2.setBounds(190,6,120,22);
		textarea2.setBackground(new Color(255,255,255));
		textarea2.setForeground(new Color(0,0,0));
		textarea2.setEnabled(true);
		textarea2.setFont(new Font("sansserif",0,12));
		textarea2.setText("Lebensanzeige");
		textarea2.setBorder(BorderFactory.createBevelBorder(1));
		textarea2.setVisible(true);

		textarea3 = new JTextArea();
		textarea3.setBounds(135,70,211,54);
		textarea3.setBackground(new Color(255,255,255));
		textarea3.setForeground(new Color(0,0,0));
		textarea3.setEnabled(true);
		textarea3.setFont(new Font("SansSerif",0,36));
		textarea3.setText("Super Mario");
		textarea3.setBorder(BorderFactory.createBevelBorder(1));
		textarea3.setVisible(true);

		textarea4 = new JTextArea();
		textarea4.setBounds(5,31,120,22);
		textarea4.setBackground(new Color(255,255,255));
		textarea4.setForeground(new Color(0,0,0));
		textarea4.setEnabled(true);
		textarea4.setFont(new Font("sansserif",0,12));
		textarea4.setText("Highscore");
		textarea4.setBorder(BorderFactory.createBevelBorder(1));
		textarea4.setVisible(true);

		//adding components to contentPane panel
		contentPane.add(Button2);
		contentPane.add(button1);
		contentPane.add(button3);
		contentPane.add(button4);
		contentPane.add(textarea1);
		contentPane.add(textarea2);
		contentPane.add(textarea3);
		contentPane.add(textarea4);

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
		System.setProperty("swing.defaultlaf", "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GUI_project();
			}
		});
	}

}