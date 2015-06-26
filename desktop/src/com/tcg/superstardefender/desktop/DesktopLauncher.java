package com.tcg.superstardefender.desktop;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;

import javax.swing.*;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tcg.superstardefender.Game;
import com.tcg.superstardefender.MyConstants;

public class DesktopLauncher   extends JFrame implements ActionListener{

	private JTextField widthField, heightField;
	
	private JButton run, website, sixteenNineRes;
	
	private JCheckBox fullScreenB, vSyncB;
	
	private boolean fullScreen, vSync;
	
	private int width, height;
	
	private static final long serialVersionUID = -6747633674982423257L;

	public static void main (String[] arg) {
		new DesktopLauncher();
	}
	
	public DesktopLauncher() {
		fullScreenB = new JCheckBox("Fullscreen");
		fullScreenB.setSelected(false);
		fullScreenB.addActionListener(this);
		vSyncB = new JCheckBox("vSync");
		vSyncB.setSelected(true);
		fullScreen = fullScreenB.isSelected();
		vSync = vSyncB.isSelected();

		widthField = new JTextField("" + MyConstants.WOLRD_WIDTH, 5);
		heightField = new JTextField("" + MyConstants.WORLD_HEIGHT, 5);
		
		run = new JButton("Run");
		run.addActionListener(this);

		website = new JButton("Visit our website!");
		website.addActionListener(this);
		
		sixteenNineRes = new JButton("16:9");
		sixteenNineRes.addActionListener(this);
		
		JPanel title = new JPanel();
		title.setLayout(new FlowLayout());
		title.add(new JLabel(Game.TITLE));
	    
	    JPanel screenSettings = new JPanel();
	    screenSettings.setLayout(new FlowLayout());
	    screenSettings.add(new JLabel("Width"));
	    screenSettings.add(widthField);
	    screenSettings.add(new JLabel("Height"));
	    screenSettings.add(heightField);
	    screenSettings.add(sixteenNineRes);
	    screenSettings.add(fullScreenB);
	    screenSettings.add(vSyncB);
	    
	    JPanel settings = new JPanel();
	    settings.setLayout(new BorderLayout());
	    settings.add(screenSettings, BorderLayout.NORTH);
	    
	    JPanel buttons = new JPanel();
	    buttons.setLayout(new FlowLayout());
	    buttons.add(run);
	    buttons.add(website);
	    
	    getContentPane().add(title, BorderLayout.NORTH);
	    getContentPane().add(settings, BorderLayout.CENTER);
	    getContentPane().add(buttons, BorderLayout.SOUTH);
	    
	    setSize(450, 125);
	    setTitle("Tiny Country Games Launcher");
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setResizable(false);
	    
	    ImageIcon img = new ImageIcon(this.getClass().getResource("16.png"));

		widthField.setToolTipText("Width of the game");
		heightField.setToolTipText("Height of the game");
		
		run.setToolTipText("Runs the game");
		website.setToolTipText("Opens http://tinycountrygames.com/ in default browser");
		
		fullScreenB.setToolTipText("Check if you want to run game in fullscreen");
		vSyncB.setToolTipText("Check if you want to enable vSync");
		sixteenNineRes.setToolTipText("Make the game aspect ratio 16:9 based on width");
	    
	    setIconImage(img.getImage());
	    setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == website) {
			try {
				Desktop.getDesktop().browse(new URL("http://tinycountrygames.com/").toURI());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		if(e.getSource() == fullScreenB) {
			widthField.setEditable(!fullScreenB.isSelected());
			heightField.setEditable(!fullScreenB.isSelected());
			int tw = 800, th = 600;
			if(fullScreenB.isSelected()) {
				tw = Toolkit.getDefaultToolkit().getScreenSize().width;
				th = Toolkit.getDefaultToolkit().getScreenSize().height;
			} 
			widthField.setText("" + tw);
			heightField.setText("" + th);
		}
		if(e.getSource() == run) {
			fullScreen = fullScreenB.isSelected();
			vSync = vSyncB.isSelected();
			if(fullScreen) {
				width = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
				height = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
			} else {
				try {
					width = new Integer(widthField.getText().trim()).intValue();
				} catch(NumberFormatException e1) {
					JOptionPane.showMessageDialog(this, "Width must be a number!", "Invalid", JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					height = new Integer(heightField.getText().trim()).intValue();
				} catch(NumberFormatException e1) {
					JOptionPane.showMessageDialog(this, "Height must be a number!", "Invalid", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			setVisible(false);
			LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
			config.width = width;
			config.height = height;
			config.fullscreen = fullScreen;
			config.vSyncEnabled = vSync;
			config.addIcon("desktopicons/win.png", FileType.Internal);
			config.addIcon("desktopicons/lin.png", FileType.Internal);
			config.addIcon("desktopicons/mac.png", FileType.Internal);
			new LwjglApplication(new Game(), config);
		}
		if(e.getSource() == sixteenNineRes) {
			int tw = new Integer(widthField.getText().trim()).intValue();
			int th = MyConstants.sixteenNineResolution(tw);
			widthField.setText("" + tw);
			heightField.setText("" + th);
		}
		
	}
}