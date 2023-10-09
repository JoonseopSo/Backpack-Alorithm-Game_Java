package team27.main;

import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import team27.main.RichtungenVonSteuerung.Richtung;


/**
 * 
 * @author Akin Tas 7365710
 * 
 *         Implementiert die Steuerung der Figur. Aktuell ist nur die Steuerung
 *         für den Startbildschirm implementiert, d.h. die Figur kann mithilfe
 *         von W,A,S,D bzw. den Pfeiltasten in die entsprechenden Richtungen
 *         bewegt werden. Die Logik der Steuerung befindet sich in der Klasse
 *         "Richtung".
 *
 */
public class Steuerung{
	
	private Maze m;
	private Start panelVonM;
	private JButton einsZurueck;
	private Buttonaction buttonaction;
	
	
	/**
	 * Erstellt eine Steuerung, die eine Figur in einem Labyrinth steuert. Aktuell ist die 
	 * Steuerung standardmässig die des Startbildschirms
	 * 
	 * @param m   Das maze, in dem die Figur ist.
	 * @param panelVonM   Das panel, in dem das maze der Figur ist.
	 */
	public Steuerung(Maze m, Start panelVonM, JButton einsZurueck, Buttonaction buttonaction) {
		this.m = m;
		this.panelVonM = panelVonM;
		this.einsZurueck = einsZurueck;
		this.buttonaction = buttonaction;
		
		steuerungVonStartbildschirm();
	}
	
	
	/**
	 * Belegt die Tasten W,A,S,D und die Pfeiltasten mit der Steuerung des Startbildschirms.
	 */
	public void steuerungVonStartbildschirm() {
		
		// In jeder Richtung steht der entsprechende Code, um die Figur zu bewegen.
		Richtung oben = new Richtung(m, panelVonM, "oben", einsZurueck);
		Richtung rechts = new Richtung(m, panelVonM, "rechts", einsZurueck);
		Richtung unten = new Richtung(m, panelVonM, "unten", einsZurueck);
		Richtung links = new Richtung(m, panelVonM, "links", einsZurueck);
		
		// Wenn "W" oder "Up" gedrueckt wird, soll "nachOben" ausgefuehrt werden
		panelVonM.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "nachOben");
		panelVonM.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "nachOben");
		// Was "nachOben" macht steht in "oben"
		panelVonM.getActionMap().put("nachOben", oben);
		
		// Analog
		panelVonM.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "nachRechts");
		panelVonM.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "nachRechts");
		panelVonM.getActionMap().put("nachRechts", rechts);
		
		panelVonM.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "nachUnten");
		panelVonM.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "nachUnten");
		panelVonM.getActionMap().put("nachUnten", unten);
		
		panelVonM.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "nachLinks");
		panelVonM.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "nachLinks");
		panelVonM.getActionMap().put("nachLinks", links);
		
		
		panelVonM.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), "enter");
		panelVonM.getActionMap().put("enter", buttonaction);
	}


	public Maze getM() {
		return m;
	}


	public void setM(Maze m) {
		this.m = m;
	}


	public Start getPanelVonM() {
		return panelVonM;
	}


	public void setPanelVonM(Start panelVonM) {
		this.panelVonM = panelVonM;
	}
	
}
