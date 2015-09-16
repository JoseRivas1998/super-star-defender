package com.tcg.superstardefender.desktop;

import java.awt.BorderLayout;
import java.io.InputStreamReader;

import javax.swing.*;

public class License extends JFrame {

	private static final long serialVersionUID = -3281492726926093896L;

	public License() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(400, 400);
		setTitle("Licenses");
		setLocationRelativeTo(null);
		
		JScrollPane pane = new JScrollPane();
		
		JTextArea text = new JTextArea();
		
		try {
			text.read(new InputStreamReader(this.getClass().getResourceAsStream("LICENSE.txt")), null);
		} catch (Exception e) {}
		
		pane.setViewportView(text);
		
		getContentPane().add(pane, BorderLayout.CENTER);
		
		this.setVisible(true);
	}

}
