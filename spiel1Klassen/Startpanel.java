package spiel1Klassen;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Startpanel extends JPanel {
	
	public Startpanel(){
		setBackground(new Color(204, 229, 255));
		setLayout(new GridLayout(2,1));
		JLabel text = new JLabel("1. Schwierigkeit auswählen", SwingConstants.CENTER);
		JLabel text2 = new JLabel("2. ...und auf Start drücken", SwingConstants.CENTER);
		add(text);
		add(text2);
		setVisible(true);
	}
	

}
