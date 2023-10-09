package spiel2Klassen;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AuswahlbereichSpiel2 extends JPanel{
	
	private JButton anleitung = new JButton("Anleitung");
	private JButton start = new JButton("Start");
	private JButton beenden = new JButton("Beenden");
	
	private JButton zurueck;
	private JButton pause = new JButton("pause");
	private JComboBox schwierigkeitsgrade;
	
	private JButton highscoresZuruecksetzen;
	private JButton loesungAbgeben = new JButton("loesungAbgeben");
	
	private JPanel highscoreboard;
	
	private int punktzahl = 0;
	private JLabel punkteanzeige = new JLabel("Punktzahl: " + punktzahl);
	
	private int level = 1;
	private JLabel levelanzeige = new JLabel("Level " + level);
	
	private int uebrigeSekunden = 90;
	private JLabel zeitanzeige = new JLabel("Zeit: " + zeitInMinuten(uebrigeSekunden)[1] + ":" + zeitInMinuten(uebrigeSekunden)[0]);
	
	private int maximaleAnzahlAquarien = 10;
	private JLabel schranke = new JLabel("Schranke: " + maximaleAnzahlAquarien);
	
	public AuswahlbereichSpiel2() {
		setLayout(new GridLayout(12,1));
		schwierigkeitsgradButtonErstellen();
		
		this.add(zeitanzeige);
		this.add(start);
		this.add(beenden);
		this.add(pause);
		this.add(anleitung);
		this.add(levelanzeige);
		this.add(loesungAbgeben);
		
		pause.setEnabled(false);
		loesungAbgeben.setEnabled(false);
		
		
		this.add(punkteanzeige);
		this.add(schranke);
	}
	
	public void anleitungOeffnen() {
		AnleitungSpiel2 anleitungsfenster = new AnleitungSpiel2();
	}
	
	public void schwierigkeitsgradButtonErstellen() {
		String[] anzuzeigen = {"training", "mittel", "schwer"};
		schwierigkeitsgrade = new JComboBox(anzuzeigen);
		this.add(schwierigkeitsgrade);
	}
	
	public int[] zeitInMinuten (int sekunden) {
		
		int[] a = new int[2];
		
		int min = Math.round(sekunden/60);
		int sek = sekunden%60;
		
		a[0] = sek;
		a[1] = min;
		
		return a;
	}

	public JButton getAnleitung() {
		return anleitung;
	}

	public void setAnleitung(JButton anleitung) {
		this.anleitung = anleitung;
	}

	public JButton getStart() {
		return start;
	}

	public void setStart(JButton start) {
		this.start = start;
	}

	public JButton getBeenden() {
		return beenden;
	}

	public void setBeenden(JButton beenden) {
		this.beenden = beenden;
	}

	public JButton getZurueck() {
		return zurueck;
	}

	public void setZurueck(JButton zurueck) {
		this.zurueck = zurueck;
	}

	public JButton getPause() {
		return pause;
	}

	public void setPause(JButton pause) {
		this.pause = pause;
	}

	public JComboBox getSchwierigkeitsgrade() {
		return schwierigkeitsgrade;
	}

	public void setSchwierigkeitsgrade(JComboBox schwierigkeitsgrade) {
		this.schwierigkeitsgrade = schwierigkeitsgrade;
	}

	public JButton getHighscoresZuruecksetzen() {
		return highscoresZuruecksetzen;
	}

	public void setHighscoresZuruecksetzen(JButton highscoresZuruecksetzen) {
		this.highscoresZuruecksetzen = highscoresZuruecksetzen;
	}

	public JButton getLoesungAbgeben() {
		return loesungAbgeben;
	}

	public void setLoesungAbgeben(JButton loesungAbgeben) {
		this.loesungAbgeben = loesungAbgeben;
	}

	public JPanel getHighscoreboard() {
		return highscoreboard;
	}

	public void setHighscoreboard(JPanel highscoreboard) {
		this.highscoreboard = highscoreboard;
	}

	public JLabel getPunkteanzeige() {
		return punkteanzeige;
	}

	public void setPunkteanzeige(JLabel punkteanzeige) {
		this.punkteanzeige = punkteanzeige;
	}

	public JLabel getLevelanzeige() {
		return levelanzeige;
	}

	public void setLevelanzeige(JLabel levelanzeige) {
		this.levelanzeige = levelanzeige;
	}

	public JLabel getZeitanzeige() {
		return zeitanzeige;
	}

	public void setZeitanzeige(JLabel zeitanzeige) {
		this.zeitanzeige = zeitanzeige;
	}

	public int getPunktzahl() {
		return punktzahl;
	}

	public void setPunktzahl(int punktzahl) {
		this.punktzahl = punktzahl;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getUebrigeSekunden() {
		return uebrigeSekunden;
	}

	public void setUebrigeSekunden(int uebrigeSekunden) {
		this.uebrigeSekunden = uebrigeSekunden;
	}

	public int getMaximaleAnzahlAquarien() {
		return maximaleAnzahlAquarien;
	}

	public void setMaximaleAnzahlAquarien(int maximaleAnzahlAquarien) {
		this.maximaleAnzahlAquarien = maximaleAnzahlAquarien;
	}

	public JLabel getSchranke() {
		return schranke;
	}

	public void setSchranke(JLabel schranke) {
		this.schranke = schranke;
	}

}
