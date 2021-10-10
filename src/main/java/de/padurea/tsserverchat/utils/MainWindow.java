package de.padurea.tsserverchat.utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.github.manevolent.ts3j.command.CommandException;

import de.padurea.tsserverchat.main.App;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	private JPanel contentPane;
	private static JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow.frame = new MainWindow();
					
					frame.setTitle("Padurea.de - TS5 ServerChat Tool");
					frame.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/de/padurea/tsserverchat/img/icon.png")));
					
					//add content
					final JTextField txtfield = createTextField("Server Message:", "", 25, 50, 250, 25, true);
					frame.getContentPane().add(txtfield);
					
					JButton sendBtn = createButton("SEND", 300, 50, 50, 25);
					sendBtn.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (App.getSocket().isConnected()) {
								if (!txtfield.getText().isEmpty()) {
									try {
										App.getSocket().sendServerMessage(txtfield.getText());
										SoundPlayer.playSound(SoundPlayer.getSoundPathInProject("chat_message_outbound.wav"), false);
									} catch (IOException ex) {} catch (TimeoutException ex) {} catch (InterruptedException ex) {} catch (CommandException ex) {
										System.err.println("Insufficient permissions.");
										SoundPlayer.playSound(SoundPlayer.getSoundPathInProject("insufficient_permissions.wav"), false);
									}
								}
							}
						}
					});
					
					frame.getContentPane().add(sendBtn);
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the 
	 */
	public MainWindow() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 160);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setBackground(Color.DARK_GRAY);
		setContentPane(contentPane);
		getContentPane().setLayout(null);
		setResizable(false);
	}
	
	private static JButton createButton(String text, int posX, int posY, int width, int height) {
		JButton button = new JButton(text);
		button.setToolTipText("");
		button.setForeground(Color.BLACK);
		button.setFont(new Font("Tahoma", Font.BOLD, 15));
		button.setBorder(null);
		button.setBackground(Color.WHITE);
		button.setBounds(posX, posY, width, height);
		button.setFocusPainted(false);
		frame.getContentPane().add(button);
		return button;
	}
	
	private static JTextField createTextField(String name, String preString, int posX, int posY, int width, int height, boolean showName) {
		JLabel title = new JLabel(name);
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Tahoma", Font.PLAIN, 12));
		title.setBounds(posX, posY - 20, (name.length()) * 10, 15);
		if (showName) {
			frame.getContentPane().add(title);
		}
		
		JTextField textField = new JTextField();
		textField.setBackground(Color.WHITE);
		textField.setBounds(posX, posY, width, height);
		textField.setText(preString);
		textField.setColumns(10);
		frame.getContentPane().add(textField);
		return textField;
	}
}
