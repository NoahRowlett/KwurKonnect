package kwurKonnect;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingWorker;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JList;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import com.jcraft.jsch.JSchException;
import javax.swing.JScrollPane;

public class klientGUI{

	private static JFrame frmKwurkonnectV;
	private static JTextField usernameField;
	private static JTextField passwordVisibleBox;
	private static JPasswordField passwordHiddenField;
	static String[] statuses;
	static String consoleOutPt;
	static JProgressBar progressBar;
	private static JTextArea console;
	private klient klientConnect;
	private static JCheckBox chckbxLaunchKreditTracker;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new klientGUI();
					klientGUI.frmKwurkonnectV.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public klientGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		statuses = new String[] {"Status", "Creating SSH Session...","Determining host...", "Setting ports...",
				"Strict host key checking: disabled", "Connecting...", "Successfully konnected!", "Konnection failed!"};

		frmKwurkonnectV = new JFrame();
		frmKwurkonnectV.setTitle("kwurKonnect v0.1");
		frmKwurkonnectV.setBounds(100, 100, 497, 329);
		frmKwurkonnectV.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmKwurkonnectV.getContentPane().setLayout(null);


		usernameField = new JTextField();
		usernameField.setBounds(113, 46, 175, 28);
		frmKwurkonnectV.getContentPane().add(usernameField);
		usernameField.setColumns(10);
		usernameField.setToolTipText("No need to type the \"@kwurmail.wustl.edu\" bit...");

		passwordVisibleBox = new JTextField();
		passwordVisibleBox.setBounds(113, 86, 175, 28);
		frmKwurkonnectV.getContentPane().add(passwordVisibleBox);
		passwordVisibleBox.setColumns(10);
		passwordVisibleBox.setVisible(false);

		passwordHiddenField = new JPasswordField();
		passwordHiddenField.setBounds(113, 86, 175, 28);
		frmKwurkonnectV.getContentPane().add(passwordHiddenField);


		chckbxLaunchKreditTracker = new JCheckBox("Launch kredit tracker");
		chckbxLaunchKreditTracker.setBounds(113, 135, 175, 23);
		chckbxLaunchKreditTracker.setSelected(true);
		frmKwurkonnectV.getContentPane().add(chckbxLaunchKreditTracker);


		final JTextArea console = new JTextArea();
		console.setBounds(35, 230, 380, 107);
		frmKwurkonnectV.getContentPane().add(console);
		console.setVisible(false);
		console.setEditable(false);
		consoleOutPt = new String("Welcome to KWUR Konnect!\n\n");
		console.setText(consoleOutPt);


		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(38, 52, 80, 16);
		frmKwurkonnectV.getContentPane().add(lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(38, 92, 61, 16);
		frmKwurkonnectV.getContentPane().add(lblPassword);

		JProgressBar progressBar = new JProgressBar(0,100);
		progressBar.setBounds(35, 183, 415, 16);
		frmKwurkonnectV.getContentPane().add(progressBar);
		progressBar.setStringPainted(true); 

		JLabel lblStatus = new JLabel("Status: ");
		lblStatus.setBounds(35, 202, 61, 16);
		frmKwurkonnectV.getContentPane().add(lblStatus);

		JButton btnKonnect = new JButton("Konnect");
		btnKonnect.setBounds(322, 46, 128, 29);
		frmKwurkonnectV.getContentPane().add(btnKonnect);
		btnKonnect.addActionListener(new btnKonnectAction());

		JMenuBar menuBar = new JMenuBar();
		frmKwurkonnectV.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);



		final JCheckBox chckbxHideConsole = new JCheckBox("Hide console");
		chckbxHideConsole.setSelected(true);
		chckbxHideConsole.setBounds(322, 200, 128, 23);
		frmKwurkonnectV.getContentPane().add(chckbxHideConsole);
		chckbxHideConsole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (chckbxHideConsole.isSelected()){
					console.setVisible(false);
					frmKwurkonnectV.setBounds(frmKwurkonnectV.getX(), frmKwurkonnectV.getY(), frmKwurkonnectV.getWidth(), 300);
				}

				else{
					console.setVisible(true);
					frmKwurkonnectV.setBounds(frmKwurkonnectV.getX(), frmKwurkonnectV.getY(), frmKwurkonnectV.getWidth(), 400);
				}

			}
		});

		final JCheckBox chckbxHidePassword = new JCheckBox("Hide password");
		chckbxHidePassword.setBounds(294, 89, 128, 23);
		chckbxHidePassword.setSelected(true);
		frmKwurkonnectV.getContentPane().add(chckbxHidePassword);
		chckbxHidePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (chckbxHidePassword.isSelected()){
					String password = new String(passwordVisibleBox.getText());
					passwordHiddenField.setVisible(true);
					passwordVisibleBox.setVisible(false);
					passwordHiddenField.setText(password);

				}

				else{
					String password = new String(passwordHiddenField.getPassword());
					passwordHiddenField.setVisible(false);
					passwordVisibleBox.setVisible(true);
					passwordVisibleBox.setText(password);
				}

			}
		});

		/*
		 * A menu item to close the application.
		 * Opens a yes/no dialog.
		 */
		JMenuItem mntmQuit = new JMenuItem("Quit");
		mnFile.add(mntmQuit);
		mntmQuit.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mntmQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int n = JOptionPane.showConfirmDialog(
						frmKwurkonnectV, "Are you sure you want to quit?",
						"Quit?",
						JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					klientConnect.disconnect();
					System.exit(-1);
				} else if (n == JOptionPane.NO_OPTION) {
					return;
				}
			}
		});
	}
	
	public static class btnKonnectAction implements ActionListener{
		public void actionPerformed (ActionEvent e){
			String pass = new String("");
			String username = new String(usernameField.getText());
			if(usernameField.getText().contains("@kwurmail.wustl.edu"))
				username=username.substring(0, username.indexOf('@'));

			if(passwordVisibleBox.isVisible())
				pass = passwordVisibleBox.getText();
			else
				pass = new String(passwordHiddenField.getPassword());

			klient klientConnect = new klient(username, pass);
			if(username.equals("")){
				JOptionPane.showMessageDialog(frmKwurkonnectV.getContentPane(),
						"No username entered!", "You shall not pass!",
						JOptionPane.WARNING_MESSAGE);
			}
			if(pass.equals("")){
				JOptionPane.showMessageDialog(frmKwurkonnectV.getContentPane(),
						"No password entered!", "You shall not pass!",
						JOptionPane.WARNING_MESSAGE);
			}
			else{
				try {
					consoleOutPt = "";
					klientConnect.runKonnect();
					if(chckbxLaunchKreditTracker.isSelected()){
						String url = "http://localhost:4600/ctracker/";
						try {
							java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				} catch (JSchException e2) {
					consoleOutPt += statuses[7] + "\n";
					console.setText(consoleOutPt);
					e2.printStackTrace();
				}
			}
		}
	}
	

	
	
}


