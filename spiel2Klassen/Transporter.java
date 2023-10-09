package spiel2Klassen;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import team27.main.Spiel2;

public class Transporter implements ActionListener{
	private int laenge = 300;
	
	private JButton plus;
	private JButton minus;
	
	// Startposition des Transporters
	private int posX = 100;
	private int posY = 100;
	
	// Verschiebung durch Pfeiltasten
	private int verschiebung = 0;
	
	private int reifenDicke = 30;
	
	private ArrayList<Aquarium> aquarien = new ArrayList<Aquarium>();
	
	// Aktuelle Anzahl an Aquarien
	private int anzahlAquarien = 1;
	// Maximale Anzahl von Aquarien
	private int maxAnzahl = 10;
	
	// Abstand der Aquarien voneinander
	private int abstand = 10;
	
	private SpielbereichSpiel2 panel;
	
	public Transporter(SpielbereichSpiel2 panel) {
		
		plus = new JButton("plus");
		minus = new JButton("minus");
		
		plus.setBounds(posX, posY, 20, 20);
		plus.setBounds(posX, posY, 20, 20);
		
		this.panel = panel;
		
		actionListenerFuerButtons();
		
		aquarien.add(new Aquarium());
		
	}
	public void transporterMalen(Graphics2D g, int posX, int posY) {
		g.setStroke(new BasicStroke(6));
		
		g.drawLine(posX + verschiebung, posY, posX + verschiebung + laenge, posY);
		g.drawOval((int) (posX + verschiebung + 0.3*laenge), posY, reifenDicke, reifenDicke);
		g.drawOval((int) (posX + verschiebung + 0.6*laenge), posY, reifenDicke, reifenDicke);
		
		g.setStroke(new BasicStroke(1));
	}
	
	// TODO Hardcoded!
	public void aquarienMalen(Graphics2D g) {
		for(int i = 0; i < anzahlAquarien; i++) {
			
			int aqHoehe = aquarien.get(i).getHoehe() ;
			int aqBreite = aquarien.get(i).getBreite();
			
			if(i == 0) {
				aquarien.get(i).aquariumMalen(g, posX, posY - aqHoehe);
				aquarien.get(i).setPosX(posX);
				aquarien.get(i).setPosY(posY - aqHoehe);
			}
			// Untere Reihe
			else if(i <= 4){
				int xKoord = posX + i * (aqBreite + abstand);
				aquarien.get(i).aquariumMalen(g, xKoord, posY - aqHoehe);
				aquarien.get(i).setPosX(xKoord);
				aquarien.get(i).setPosY(posY - aqHoehe);
			}
			// Obere Reihe (faengt wieder links an, also i - 5)
			else{
				int xKoord = posX + (i - 5) * (aqBreite + abstand);
				aquarien.get(i).aquariumMalen(g, xKoord, posY - 2*aqHoehe);
				aquarien.get(i).setPosX(xKoord);
				aquarien.get(i).setPosY(posY - 2*aqHoehe);
			}
		}
	}
	
	public void aquarienLeeren() {
		/*
		for(int i = 0; i < aquarien.size(); i++) {
			aquarien.get(i).alleFischeEntfernen();
		}
		*/
		
		aquarien.removeAll(aquarien);
		aquarien.add(new Aquarium());
		anzahlAquarien = 1;
	}
	
	public void fischeInAquarienMalen(Graphics2D g) {
		for(int i = 0; i < aquarien.size(); i++) {
			aquarien.get(i).fischeMalen(g);
		}
	}
	
	public void actionListenerFuerButtons() {
		plus.addActionListener(this);
		minus.addActionListener(this);
		
		if(anzahlAquarien == maxAnzahl) {
			plus.setEnabled(false);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(panel.getPausiert() == 1) {
			return;
		}
		
		if(e.getSource() == plus) {
			aquarien.add(new Aquarium());
			anzahlAquarien++;
			
			// Falls nach Hinzufuegen maxAnzahl erreicht ist
			if(anzahlAquarien == maxAnzahl) {
				plus.setEnabled(false);
			}
			
			// Pruefen, ob Minus nun geklickt werden kann
			int leereAquarien = 0;
			
			for(int i = 0; i < aquarien.size(); i++) {
				if(aquarien.get(i).isLeer()) {
					leereAquarien++;
				}
			}
			
			if(leereAquarien >= 1 && anzahlAquarien >= 2) {
				minus.setEnabled(true);
			}
			else {
				minus.setEnabled(false);
			}
			panel.repaint();
		}
		
		if(e.getSource() == minus) {
			// Suche zuletzt hinzugefuegtes leeres Aquarium und entfernt es
			// Steht in der Liste am weitesten hinten
			
			for(int i = (anzahlAquarien - 1); i >= 0; i--) {
				if(aquarien.get(i).isLeer()) {
					aquarien.remove(i);
					break;
				}
			}
			
			anzahlAquarien--;
			
			// Schaltet minus aus oder ein gemaess Kriterien
			minusSchalten();
			
			// Nach Entfernen ist immer Platz fuer ein weiteres Aquarium
			plus.setEnabled(true);
			
			panel.repaint();
		}
		
	}
	
	// Pruefe, ob minus disabled werden muss
	public void minusSchalten() {
		int leereAquarien = 0;
					
		for(int i = 0; i < aquarien.size(); i++) {
			if(aquarien.get(i).isLeer()) {
				leereAquarien++;
			}
		}
		if(leereAquarien >= 1 && anzahlAquarien >= 2) {
			minus.setEnabled(true);
		}
		else {
			minus.setEnabled(false);
		}
		
	}
	
	public int getLaenge() {
		return laenge;
	}
	public void setLaenge(int laenge) {
		this.laenge = laenge;
	}
	public JButton getPlus() {
		return plus;
	}
	public void setPlus(JButton plus) {
		this.plus = plus;
	}
	public JButton getMinus() {
		return minus;
	}
	public void setMinus(JButton minus) {
		this.minus = minus;
	}
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public int getMaxAnzahl() {
		return maxAnzahl;
	}
	public void setMaxAnzahl(int maxAnzahl) {
		this.maxAnzahl = maxAnzahl;
	}
	public ArrayList<Aquarium> getAquarien() {
		return aquarien;
	}
	public void setAquarien(ArrayList<Aquarium> aquarien) {
		this.aquarien = aquarien;
	}
	public int getAnzahlAquarien() {
		return anzahlAquarien;
	}
	public void setAnzahlAquarien(int anzahlAquarien) {
		this.anzahlAquarien = anzahlAquarien;
	}
	public int getAbstand() {
		return abstand;
	}
	public void setAbstand(int abstand) {
		this.abstand = abstand;
	}
	public SpielbereichSpiel2 getPanel() {
		return panel;
	}
	public void setPanel(SpielbereichSpiel2 panel) {
		this.panel = panel;
	}
	public int getVerschiebung() {
		return verschiebung;
	}
	public void setVerschiebung(int verschiebung) {
		this.verschiebung = verschiebung;
	}
	public int getReifenDicke() {
		return reifenDicke;
	}
	public void setReifenDicke(int reifenDicke) {
		this.reifenDicke = reifenDicke;
	}

}
