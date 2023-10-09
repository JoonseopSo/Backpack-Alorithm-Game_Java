package spiel4Klassen;
//Jonte Jakob 7380571
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Startbildschirm extends JPanel{
	
	public Startbildschirm() {
		setLayout(new GridLayout(4,1));
		JLabel start = new JLabel("Schwierigkeit auswählen und Spiel starten", SwingConstants.CENTER);
		add(start);
		setVisible(true);
	}
}
