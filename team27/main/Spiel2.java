package team27.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import spiel2Klassen.AnleitungSpiel2;
import spiel2Klassen.AuswahlbereichSpiel2;
import spiel2Klassen.Fisch;
import spiel2Klassen.GraphSpiel2;
import spiel2Klassen.HighscoreBoard;
import spiel2Klassen.SpielbereichSpiel2;
import spiel2Klassen.RichtungenSpiel2;
import spiel2Klassen.Tabelle;
import spiel2Klassen.Transporter;

public class Spiel2 extends JPanel implements ActionListener, WindowListener{
	
	private GraphSpiel2 graph;
	
	private JPanel linkeHaelfte = new JPanel();
	private SpielbereichSpiel2 spielbereich;
	private JPanel ButtonPanel = new JPanel();
	private AuswahlbereichSpiel2 auswahlbereich = new AuswahlbereichSpiel2();
	
	private JButton zurueckbutton;
	
	//  1, falls Spiel pausiert
	// -1, falls Spiel nicht pausiert
	private int pausiert = -1;
	private int anleitungGeoeffnet = -1;
	
	private AnleitungSpiel2 anleitungsfenster;
	
	private String schwierigkeit = "training";
	private String alteSchwierigkeit = "training";
	
	private boolean verloren = false;
	
	private int level = 1;
	
	Timer uhr = new Timer(1000, this);
	
	Spiel2(){
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		linkeHaelfte.setPreferredSize(new Dimension(500, 600));
		auswahlbereich.setPreferredSize(new Dimension(100, 600));
		
		JPanel leererAnfangsbildschirm = new JPanel();
		leererAnfangsbildschirm.setPreferredSize(new Dimension(500, 600));
		
		this.add(leererAnfangsbildschirm);
		this.add(auswahlbereich);
		
		actionListenerAdden();
		
		//graph.fischeAusgeben();
		//graph.konflikteAusgeben();
		
		
		HighscoreBoard highscoreboard = new HighscoreBoard();
		
		repaint();
	}
	
	public void steuerungEinrichten() {
		RichtungenSpiel2 links = new RichtungenSpiel2(spielbereich.getTransporter(), "links", this);
		RichtungenSpiel2 rechts = new RichtungenSpiel2(spielbereich.getTransporter(), "rechts", this);
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "TransporterNachLinks");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "TransporterNachLinks");
		this.getActionMap().put("TransporterNachLinks", links);
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "TransporterNachRechts");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "TransporterNachRechts");
		this.getActionMap().put("TransporterNachRechts", rechts);
		
		// Erstellt Action, die das SPiel pausiert
		// actionPerformed von AbstractAction wird hier in einer
		// anonymen Klasse ueberschrieben
		AbstractAction fuerPause = new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(anleitungGeoeffnet == -1 && !verloren) {
					pausierungFlippen();
					spielPausieren();
				}
			}
			
		};
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("P"), "Pausieren");
		this.getActionMap().put("Pausieren", fuerPause);
		
		
	}
	
	// Wird in Start ausgefuehrt, um den (erst spaeter erstellten) zurueckbutton abzuholen	
	public void zurueckbuttonAbholen(JButton zurueckbutton) {
		this.zurueckbutton = zurueckbutton;
		auswahlbereich.add(zurueckbutton);
		zurueckbutton.addActionListener(this);
	}
	
	// Fuegt nur alle actionListener zu
	public void actionListenerAdden() {
		auswahlbereich.getSchwierigkeitsgrade().addActionListener(this);
		auswahlbereich.getStart().addActionListener(this);
		auswahlbereich.getBeenden().addActionListener(this);
		auswahlbereich.getPause().addActionListener(this);
		auswahlbereich.getAnleitung().addActionListener(this);
		auswahlbereich.getLoesungAbgeben().addActionListener(this);
		
	}
	
	// Entfernt alle Panel und erstellt neue.
	public void neuePanel() {
		this.removeAll();
		
		spielbereich = new SpielbereichSpiel2(graph);
		spielbereich.setPreferredSize(new Dimension(500, 600 - 30));
		ButtonPanel = new JPanel();
		
		//auswahlbereich = new AuswahlbereichSpiel2();
		//auswahlbereich.setPreferredSize(new Dimension(100, 600));
		
		ButtonPanel.setPreferredSize(new Dimension(500, 40));
		ButtonPanel.setBackground(Color.BLUE);
		
		linkeHaelfte = new JPanel();
		linkeHaelfte.setLayout(new BoxLayout(linkeHaelfte, BoxLayout.Y_AXIS));
		
		ButtonPanel.add(spielbereich.getTransporter().getPlus());
		ButtonPanel.add(spielbereich.getTransporter().getMinus());
		
		linkeHaelfte.add(spielbereich);
		linkeHaelfte.add(ButtonPanel);
		
		this.add(linkeHaelfte);
		this.add(auswahlbereich);
		
		steuerungEinrichten();
		
		this.repaint();
	}
	
	public void spielStarten() {
		uhr.start();
		
	}
	
	public void pausierungFlippen() {
		pausiert = pausiert * (-1);
		spielbereich.setPausiert(pausiert);
	}
	
	public void spielPausieren() {
		if(pausiert == 1) {
			uhr.stop();
			auswahlbereich.getStart().setEnabled(true);
			auswahlbereich.getSchwierigkeitsgrade().setEnabled(true);
			if(spielbereich != null) {
				spielbereich.getTransporter().getPlus().setEnabled(false);
				spielbereich.getTransporter().getMinus().setEnabled(false);
			}
		}
		
		if(pausiert == -1) {
			uhr.start();
			auswahlbereich.getStart().setEnabled(false);
			auswahlbereich.getSchwierigkeitsgrade().setEnabled(false);
			
			schwierigkeit = alteSchwierigkeit;
			auswahlbereich.getSchwierigkeitsgrade().setSelectedItem(schwierigkeit);
			if(spielbereich != null && !verloren) {
				spielbereich.getTransporter().getPlus().setEnabled(true);
				spielbereich.getTransporter().getMinus().setEnabled(true);
			}
		}
		
	}
	
	public void loesungAbgeben() {
		
		spielbereich.setSollOptimalwertAnzeigen(true);
		spielbereich.repaint();
		
		if(spielbereich.loesungPruefen() == true) {
			neuerGraphNachSchwierigkeit();
			spielbereich.neuerSpielbereich(graph);
			
			if(schwierigkeit == "training" || schwierigkeit == "mittel") {
				auswahlbereich.setMaximaleAnzahlAquarien(graph.getChromatischeZahl() + 1);
				auswahlbereich.getSchranke().setText("Schranke: " + auswahlbereich.getMaximaleAnzahlAquarien());
			}
			else {
				auswahlbereich.setMaximaleAnzahlAquarien(graph.getChromatischeZahl());
				auswahlbereich.getSchranke().setText("Schranke: " + auswahlbereich.getMaximaleAnzahlAquarien());
			}
				
			level = level + 1;
			auswahlbereich.setLevel(level);
			auswahlbereich.getLevelanzeige().setText("Level " + level);
			
			auswahlbereich.getLoesungAbgeben().setEnabled(true);
			
			spielbereich.setSollOptimalwertAnzeigen(false);
		}
		else {
			spielVerloren();
		}
		
		spielbereich.repaint();
		
		
	}
	
	public void spielVerloren() {
		uhr.stop();
		
		auswahlbereich.getStart().setEnabled(true);
		zurueckbutton.setEnabled(true);
		auswahlbereich.getPause().setEnabled(false);
		verloren = true;
		
		if(spielbereich != null) {
			spielbereich.setVerloren(verloren);
			spielbereich.getTransporter().getPlus().setEnabled(false);
			spielbereich.getTransporter().getMinus().setEnabled(false);
		}
		
		System.out.println("Verloren!");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == uhr) {
			// Aktualisiere Zeit, falls noch nicht abgelaufen
			
			if(schwierigkeit == "mittel" || schwierigkeit == "schwer") {
				auswahlbereich.setUebrigeSekunden(auswahlbereich.getUebrigeSekunden() - 1);
			}
			
			if(schwierigkeit == "training") {
				auswahlbereich.setUebrigeSekunden(auswahlbereich.getUebrigeSekunden() + 1);
			}
			
			int min = auswahlbereich.zeitInMinuten(auswahlbereich.getUebrigeSekunden())[1];
			int sek = auswahlbereich.zeitInMinuten(auswahlbereich.getUebrigeSekunden())[0];
			auswahlbereich.getZeitanzeige().setText("Zeit: " + min + ":" + sek);
			
			if(auswahlbereich.getUebrigeSekunden() == 0 && schwierigkeit != "training") {
				uhr.stop();
			}
		}
		
		if(e.getSource() == auswahlbereich.getAnleitung()) {
			anleitungGeoeffnet = anleitungGeoeffnet * (-1);
			anleitungsfenster = new AnleitungSpiel2();
			anleitungsfenster.addWindowListener(this);
			
			pausiert = 1;
			spielbereich.setPausiert(1);
			spielPausieren();
			
		}
		
		if(e.getSource() == zurueckbutton) {
			Maze maze = null;
			Start s = new Start(maze);
			s.mazeErzeugen(s);
		}
		
		if(e.getSource() == auswahlbereich.getSchwierigkeitsgrade()) {
			// Auswahl uebernehmen
			alteSchwierigkeit = schwierigkeit;
			schwierigkeit = (String) auswahlbereich.getSchwierigkeitsgrade().getSelectedItem();
		}
		
		if(e.getSource() == auswahlbereich.getStart()) {
			alteSchwierigkeit = schwierigkeit;
			neuerGraphNachSchwierigkeit();
			neuePanel();
			spielStarten();
			
			pausiert = -1;
			spielbereich.setPausiert(-1);
			verloren = false;
			spielbereich.setVerloren(false);
			
			if(schwierigkeit == "training") {
				auswahlbereich.setUebrigeSekunden(0);
			}
			
			if(schwierigkeit == "mittel") {
				auswahlbereich.setUebrigeSekunden(90);
			}
			
			if(schwierigkeit == "schwer") {
				auswahlbereich.setUebrigeSekunden(120);
			}
			
			spielbereich.setSollOptimalwertAnzeigen(false);
			
			auswahlbereich.getStart().setEnabled(false);
			zurueckbutton.setEnabled(false);
			auswahlbereich.getPause().setEnabled(true);
			auswahlbereich.getLoesungAbgeben().setEnabled(true);
			auswahlbereich.getSchwierigkeitsgrade().setEnabled(false);
			
			auswahlbereich.setMaximaleAnzahlAquarien(graph.getChromatischeZahl() + spielbereich.getFehlertoleranz());
			auswahlbereich.getSchranke().setText("Schranke: " + auswahlbereich.getMaximaleAnzahlAquarien());
		}
		
		if(e.getSource() == auswahlbereich.getBeenden()) {
			spielVerloren();
			zurueckbutton.setEnabled(true);
		}
		
		if(e.getSource() == auswahlbereich.getPause()) {
			pausierungFlippen();
			spielPausieren();
		}
		
		if(e.getSource() == auswahlbereich.getLoesungAbgeben()) {
			auswahlbereich.getLoesungAbgeben().setEnabled(false);
			loesungAbgeben();
		}
	}
	// Falls das Anleitungsfenster geoeffnet wird
	@Override
	public void windowOpened(WindowEvent e) {
		if(e.getSource() == anleitungsfenster) {
			anleitungGeoeffnet = 1;
			auswahlbereich.getPause().setEnabled(false);
		}
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	// Falls das Anleitungsfenster geschlossen wird
	@Override
	public void windowClosed(WindowEvent e) {
		if(e.getSource() == anleitungsfenster) {
			anleitungGeoeffnet = -1;
			auswahlbereich.getPause().setEnabled(true);
			auswahlbereich.getStart().setEnabled(true);
		}
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void neuerGraphNachSchwierigkeit() {
		if(schwierigkeit == "training") {
			Random zufallsgenerator = new Random();
			
			// Zahl in [0,3]
			int anzahlKonflikte = zufallsgenerator.nextInt(4);
			int anzahlFische = 4;
			int fehlertoleranz = 1;
			
			graph = new GraphSpiel2(anzahlKonflikte, anzahlFische, fehlertoleranz);
		}
		
		if(schwierigkeit == "mittel") {
			Random zufallsgenerator = new Random();
			
			// Zahl in [0,10] \ [4,7]
			int anzahlKonflikte = zufallsgenerator.nextInt(11);
			while(anzahlKonflikte == 4 || anzahlKonflikte == 5 || anzahlKonflikte == 6 || anzahlKonflikte == 7) {
				anzahlKonflikte = zufallsgenerator.nextInt(11);
			}
			
			int anzahlFische = 6;
			int fehlertoleranz = 1;
			
			graph = new GraphSpiel2(anzahlKonflikte, anzahlFische, fehlertoleranz);
		}
		
		if(schwierigkeit == "schwer") {
			Random zufallsgenerator = new Random();
			
			// Zahl in [10, 15]
			
			int anzahlKonflikte = zufallsgenerator.nextInt(5) + 10;
			
			int anzahlFische = 8;
			int fehlertoleranz = 0;
			
			
			graph = new GraphSpiel2(anzahlKonflikte, anzahlFische, fehlertoleranz);
		}
	}

	public GraphSpiel2 getGraph() {
		return graph;
	}

	public void setGraph(GraphSpiel2 graph) {
		this.graph = graph;
	}

	public JPanel getLinkeHaelfte() {
		return linkeHaelfte;
	}

	public void setLinkeHaelfte(JPanel linkeHaelfte) {
		this.linkeHaelfte = linkeHaelfte;
	}

	public SpielbereichSpiel2 getSpielbereich() {
		return spielbereich;
	}

	public void setSpielbereich(SpielbereichSpiel2 spielbereich) {
		this.spielbereich = spielbereich;
	}

	public JPanel getButtonPanel() {
		return ButtonPanel;
	}

	public void setButtonPanel(JPanel buttonPanel) {
		ButtonPanel = buttonPanel;
	}

	public AuswahlbereichSpiel2 getAuswahlbereich() {
		return auswahlbereich;
	}

	public void setAuswahlbereich(AuswahlbereichSpiel2 auswahlbereich) {
		this.auswahlbereich = auswahlbereich;
	}

	public JButton getZurueckbutton() {
		return zurueckbutton;
	}

	public void setZurueckbutton(JButton zurueckbutton) {
		this.zurueckbutton = zurueckbutton;
	}

	public int getPausiert() {
		return pausiert;
	}

	public void setPausiert(int pausiert) {
		this.pausiert = pausiert;
	}

	public String getSchwierigkeit() {
		return schwierigkeit;
	}

	public void setSchwierigkeit(String schwierigkeit) {
		this.schwierigkeit = schwierigkeit;
	}

	public Timer getUhr() {
		return uhr;
	}

	public void setUhr(Timer uhr) {
		this.uhr = uhr;
	}

	public int getAnleitungGeoeffnet() {
		return anleitungGeoeffnet;
	}

	public void setAnleitungGeoeffnet(int anleitungGeoeffnet) {
		this.anleitungGeoeffnet = anleitungGeoeffnet;
	}

	public AnleitungSpiel2 getAnleitungsfenster() {
		return anleitungsfenster;
	}

	public void setAnleitungsfenster(AnleitungSpiel2 anleitungsfenster) {
		this.anleitungsfenster = anleitungsfenster;
	}

	public boolean isVerloren() {
		return verloren;
	}

	public void setVerloren(boolean verloren) {
		this.verloren = verloren;
	}

	
}
