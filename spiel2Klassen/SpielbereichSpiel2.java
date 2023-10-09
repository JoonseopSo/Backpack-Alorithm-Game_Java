package spiel2Klassen;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class SpielbereichSpiel2 extends JPanel implements MouseListener, ActionListener{
	
	private Tabelle tabelle;
	private Fisch[] fische;
	private Transporter transporter;
	private GraphSpiel2 graph;
	
	// Welcher Fisch vom Cursor gefuehrt wird. Auf -1, wenn keiner
	// gehalten wird.
	private int gehaltenerFisch = -1;
	
	// X und Y position von Maus
	private int mausX;
	private int mausY;
	
	private int pausiert = -1;
	private boolean verloren = false;
	private boolean sollOptimalwertAnzeigen = false;
	
	// Timer, der gestartet wird, wenn ein Fisch per Drag and Drop bewegt wird.
	// Sorgt fuer wiederholtes repainten.
	private Timer repaintFuerDragAndDrop;

	// Zufallszahl, die entscheidet, ob der Ausgang links oder rechts ist
	double ausgangLR = Math.random();
	
	private int anzahlFische;
	private int fehlertoleranz;
	private int anzahlKonflikte;
	
	public SpielbereichSpiel2(GraphSpiel2 graph) {
		this.graph = graph;
		anzahlFische = graph.getAnzahlFische();
		fehlertoleranz = graph.getFehlertoleranz();
		anzahlKonflikte = graph.getAnzahlKonflikte();
		
		tabelleFestlegen();
		transporter = new Transporter(this);
		tabelleAnPanelgroesseAnpassen();
		
		addMouseListener(this);
		
		// 1000/30 ~ 33. Pro Sekunde 30 Bilder, also ein Bild alle 33 MS.
		repaintFuerDragAndDrop = new Timer(33, this);
		
		setBackground(new Color(255, 255, 224));
	}
	
	public void neuerSpielbereich(GraphSpiel2 graph) {
		this.graph = graph;
		anzahlFische = graph.getAnzahlFische();
		fehlertoleranz = graph.getFehlertoleranz();
		anzahlKonflikte = graph.getAnzahlKonflikte();
		
		tabelleFestlegen();
		//transporter = new Transporter(this);
		transporter.setVerschiebung(0);
		transporter.aquarienLeeren();
		
		tabelleAnPanelgroesseAnpassen();
		repaint();
	}
	
	
	// Erstellt Tabelle mit den generierten Fischen von Graph
	// V(i) ~ fische[i]
	public void tabelleFestlegen() {
		fische = new Fisch[anzahlFische];
		for(int i = 0; i < anzahlFische; i++) {
			fische[i] = new Fisch(graph.getV().get(i).getFischart());
		}
		
		tabelle = new Tabelle(fische, anzahlFische, 3, graph);
	}
	
	// Zeilen- und Spaltenabstaende der Tabelle anpassen
	public void tabelleAnPanelgroesseAnpassen() {
		int zeilenanzahl = tabelle.getZeilenanzahl();
		int spaltenanzahl = tabelle.getSpaltenanzahl();
		
		tabelle.setZeilenabstand(30);
		tabelle.setKleinerSpaltenabstand(30);
		tabelle.setMittlererSpaltenabstand(60);
		tabelle.setGrosserSpaltenabstand(400);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(pausiert == 1 || verloren) {
			return;
		}
		
		// Fuer Lesbarkeit
		int mx = e.getX();
		int my = e.getY();
		
		mausX = mx;
		mausY = my;
		
		int tabX = tabelle.getPosX();
		int tabBreite = tabelle.getKleinerSpaltenabstand();
		int tabY = tabelle.getPosY();
		
		// Pruefe, ob und (wenn ja) welcher Fisch angeklickt wurde
		// Falls in erster Spalte...
		if((mx > tabX) && (mx < tabY + tabBreite)) {
			// Pruefe, in welcher Zeile...
			for(int i = 0; i < tabelle.getZeilenanzahl(); i++) {
				if(my > tabY + i * tabelle.getZeilenabstand() && my < tabY + (i+1) * tabelle.getZeilenabstand()) {
					// Nur verschieben koennen, falls der Fisch
					// noch NICHT bereits in einem Aquarium ist
					if(fische[i].getInAquarium() == -1) {	
						gehaltenerFisch = i;
						fische[i].setGehalten(true);
						
						// Starte wiederholte repaints. Wird beendet, sobald
						// Maustaste losgelassen wird
						repaintFuerDragAndDrop.start();
					}
				}
			}
		}
		
		for(int i = 0; i < fische.length; i++) {
			// Nur verschieben können, wenn angeklickt und bereits in Aquarium
			if(fische[i].hoversOverFisch(mx, my) && fische[i].getInAquarium() != -1) {
				gehaltenerFisch = i;
				fische[i].setGehalten(true);
				
				int welchesAquarium = fische[i].getInAquarium();
				// Aus welchem Aquarium der Fisch geholt wurde
				Aquarium aquarium = transporter.getAquarien().get(welchesAquarium);
				aquarium.fischEntfernen(fische[i].getFischart());
				aquarium.setLeer(aquarium.pruefenObLeer());
				
				
				repaintFuerDragAndDrop.start();
				
			}
		}
		
		// Starte wiederholte repaints. Wird beendet, sobald
		// Maustaste losgelassen wird
		//repaintFuerDragAndDrop.start();
		repaint();
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		if(pausiert == 1) {
			return;
		}
		
		Point mausPos = MouseInfo.getPointerInfo().getLocation();
		SwingUtilities.convertPointFromScreen(mausPos, this);
		
		// Aktualisiert gespeicherte Mausposition
		mausX = (int) mausPos.getX();
		mausY = (int) mausPos.getY();
		
		// Sofern auch tatsaechlich ein Fisch gehalten wird
		if(gehaltenerFisch != -1) {
			boolean aquariumGefunden = false;
			for(int i = 0; i < transporter.getAnzahlAquarien(); i++) {
				if(transporter.getAquarien().get(i).hoversOverAquarium(mausX, mausY)) {
					aquariumGefunden = true;
					// Fuegt gehaltenen Fisch zum Aquarium hinzu 
					// und speichert, in welchem Aquarium der Fisch jetzt ist
					transporter.getAquarien().get(i).getFische().add(fische[gehaltenerFisch]);
					transporter.getAquarien().get(i).setLeer(false);
					fische[gehaltenerFisch].setInAquarium(i);
				}
			}
			if(!aquariumGefunden) {
				fische[gehaltenerFisch].setGehalten(false);
				fische[gehaltenerFisch].setInAquarium(-1);
			}
		}
		
		if(gehaltenerFisch != -1) {
			fische[gehaltenerFisch].setGehalten(false);
		}
		gehaltenerFisch = -1;
		
		transporter.minusSchalten();
		
		repaintFuerDragAndDrop.stop();
		repaint();
		
	}
	
	// Malt gehaltenen Fisch am Cursor
	public void fischAnCursorMalen(Graphics2D g) {
		if(gehaltenerFisch != -1) {
			fische[gehaltenerFisch].symbolMalen(g, mausX, mausY);
		}
	}
	
	public void ausgangMalen(Graphics2D g) {
		
		int reifenDicke = transporter.getReifenDicke();
		int TposX = transporter.getPosX();
		int TposY = transporter.getPosY();
		
		g.setStroke(new BasicStroke(4));
		g.setColor(Color.GRAY);
		
		if(ausgangLR < 0.5) {
			g.drawLine(0, TposY + reifenDicke, 0, TposY - reifenDicke);
			g.drawLine(0, TposY - reifenDicke, 50, TposY - reifenDicke);
		}
		
		if(ausgangLR >= 0.5) {
			g.drawLine(getWidth(), TposY + reifenDicke, getWidth(), TposY - reifenDicke);
			g.drawLine(getWidth(), TposY - reifenDicke, getWidth() - 50, TposY - reifenDicke);
		}
		
		g.setStroke(new BasicStroke(1));
	}
	
	public boolean loesungPruefen() {
		
		// Pruefe, ob alle Fische auf Aquarien verteilt wurden
		for(int i = 0; i < anzahlFische; i++) {
			if(fische[i].getInAquarium() == -1) {
				System.out.println("Nicht alle Fische verteilt!" + i);
				for(int j = 0; j < anzahlFische; j++) {
					System.out.println(fische[j].getInAquarium());
				}
				return false;
			}
		}
		
		if(transporter.getAnzahlAquarien() - graph.getChromatischeZahl() > fehlertoleranz) {
			System.out.println("Fehlertoleranz ueberschritten");
			return false;
		}
		
		for(int i = 0; i < graph.getE().size(); i++) {
			KanteSpiel2 kante = graph.getE().get(i);
			if(fische[kante.getKnoten1().getIndexInV()].getInAquarium() == fische[kante.getKnoten2().getIndexInV()].getInAquarium()) {
				System.out.println("Konflikt!");
				return false;
			}
		}
		
		return true;
	}
	
	public void wertEinerOptimalloesungAnzeigen(Graphics2D g) {
		
		char opt = Integer.toString(graph.getChromatischeZahl()).charAt(0);
		char[] s = {'O', 'p', 't', 'i', 'm', 'a', 'l', ':', opt};
		
		int posX = tabelle.getPosX();
		int posY = tabelle.getPosY() + (tabelle.getZeilenanzahl() + 1) * tabelle.getZeilenabstand();
		
		g.drawChars(s, 0, s.length, posX , posY);
	}
	
	// Wird von cursor ausgeloest, wenn Maus gedruecktgehalten.
	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println("Repainten...");
		
		// Speichert mausPosition (Koordinaten relativ ZUM GESAMTBILDSCHIRM!!!!)
		Point mausPos = MouseInfo.getPointerInfo().getLocation();
		
		// Konvertiert Koordinaten zu relativen Koordinaten (relativ 
		//zu DIESEM Panel)
		SwingUtilities.convertPointFromScreen(mausPos, this);
		
		// Aktualisiert gespeicherte Mausposition
		mausX = (int) mausPos.getX();
		mausY = (int) mausPos.getY();
		repaint();
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		//tabelleAnPanelgroesseAnpassen();
		g.setColor(Color.BLACK);
		tabelle.tabelleMalen(g2);
		transporter.setPosY((int) (getHeight() * 0.9));
		transporter.transporterMalen(g2, 100, transporter.getPosY());
		transporter.aquarienMalen(g2);
		transporter.fischeInAquarienMalen(g2);
		ausgangMalen(g2);
		fischAnCursorMalen(g2);
		
		if(sollOptimalwertAnzeigen) {
			wertEinerOptimalloesungAnzeigen(g2);
		}
		
	}


	public Tabelle getTabelle() {
		return tabelle;
	}


	public void setTabelle(Tabelle tabelle) {
		this.tabelle = tabelle;
	}


	public Fisch[] getFische() {
		return fische;
	}


	public void setFische(Fisch[] fische) {
		this.fische = fische;
	}


	public Transporter getTransporter() {
		return transporter;
	}


	public void setTransporter(Transporter transporter) {
		this.transporter = transporter;
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	public int getGehaltenerFisch() {
		return gehaltenerFisch;
	}


	public void setGehaltenerFisch(int gehaltenerFisch) {
		this.gehaltenerFisch = gehaltenerFisch;
	}


	public int getMausX() {
		return mausX;
	}


	public void setMausX(int mausX) {
		this.mausX = mausX;
	}


	public int getMausY() {
		return mausY;
	}


	public void setMausY(int mausY) {
		this.mausY = mausY;
	}


	public Timer getRepaintFuerDragAndDrop() {
		return repaintFuerDragAndDrop;
	}


	public void setRepaintFuerDragAndDrop(Timer repaintFuerDragAndDrop) {
		this.repaintFuerDragAndDrop = repaintFuerDragAndDrop;
	}


	public double getAusgangLR() {
		return ausgangLR;
	}


	public void setAusgangLR(double ausgangLR) {
		this.ausgangLR = ausgangLR;
	}


	public GraphSpiel2 getGraph() {
		return graph;
	}


	public void setGraph(GraphSpiel2 graph) {
		this.graph = graph;
	}


	public int getAnzahlFische() {
		return anzahlFische;
	}


	public void setAnzahlFische(int anzahlFische) {
		this.anzahlFische = anzahlFische;
	}


	public int getFehlertoleranz() {
		return fehlertoleranz;
	}


	public void setFehlertoleranz(int fehlertoleranz) {
		this.fehlertoleranz = fehlertoleranz;
	}


	public int getAnzahlKonflikte() {
		return anzahlKonflikte;
	}


	public void setAnzahlKonflikte(int anzahlKonflikte) {
		this.anzahlKonflikte = anzahlKonflikte;
	}


	public int getPausiert() {
		return pausiert;
	}


	public void setPausiert(int pausiert) {
		this.pausiert = pausiert;
	}

	public boolean isVerloren() {
		return verloren;
	}

	public void setVerloren(boolean verloren) {
		this.verloren = verloren;
	}

	public boolean isSollOptimalwertAnzeigen() {
		return sollOptimalwertAnzeigen;
	}

	public void setSollOptimalwertAnzeigen(boolean sollOptimalwertAnzeigen) {
		this.sollOptimalwertAnzeigen = sollOptimalwertAnzeigen;
	}

}

