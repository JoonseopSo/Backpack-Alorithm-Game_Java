package spiel2Klassen;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class AnleitungSpiel2 extends JFrame{
	
	public AnleitungSpiel2() {
		this.getContentPane().setLayout(new GridLayout(7,1));
		this.setLocationRelativeTo(null);
		this.setSize(new Dimension(700, 500));
		
		JLabel zeile1 = new JLabel("Anleitung und Spielregeln");
		JLabel zeile2 = new JLabel("Die Fische aus der Tabelle müssen auf die Aquarien verteilt werden (Drag and Drop)");
		JLabel zeile3 = new JLabel("Dabei sollen Fische, die im Konflikt stehen, nicht in das gleiche Aquarium gesetzt werden.");
		JLabel zeile4 = new JLabel("Es darf höchstens ein/kein Aquarium mehr benutzt werden (training, mittel)/schwer");
		JLabel zeile5 = new JLabel("Ist die Lösung abgabebereit, so soll der Transporter zur Seite des Ausgangs verschoben werden (Pfeiltasten)");
		JLabel zeile6 = new JLabel("Das Spiel ist verloren, wenn eine Lösung abgegeben wird, in der es Konflikte gibt,");
		JLabel zeile7 = new JLabel("die zu viele Aquarien verwendet, oder nicht alle Fische verwendet wurden.");
		
		add(zeile1);
		add(zeile2);
		add(zeile3);
		add(zeile4);
		add(zeile5);
		add(zeile6);
		add(zeile7);
		
		this.setVisible(true);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}

}
