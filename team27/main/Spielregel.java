package team27.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SpringLayout.Constraints;
import javax.swing.SwingConstants;

public class Spielregel extends JFrame {

	JTextPane jtxtpane;

	public Spielregel() {

		JFrame anleitung = new JFrame("Anleitung");
		anleitung.setLayout(new GridLayout(4, 1));
		JLabel text = new JLabel("ANLEITUNG: 1. Bewege die Figur mit WASD/Pfeiltasten", SwingConstants.CENTER);
		JLabel text2 = new JLabel("2. Suche den kuerzesten Pfad zu einem Spiel", SwingConstants.CENTER);
		JLabel text3 = new JLabel("3. Starte ein neues Spiel mit Enter", SwingConstants.CENTER);
		JLabel text4 = new JLabel("Viel Spass", SwingConstants.CENTER);
		anleitung.add(text);
		anleitung.add(text2);
		anleitung.add(text3);
		anleitung.add(text4);
		anleitung.dispose();
		anleitung.setBackground(Color.white);
		anleitung.setSize(600, 600);
		anleitung.setLocationRelativeTo(null);
		anleitung.setVisible(true);
	}
}
