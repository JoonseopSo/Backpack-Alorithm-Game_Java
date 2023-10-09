package spiel2Klassen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;


/**
 * 
 * @author akin1
 *
 * Erstellt Tabelle mit entsprechender Position und Zeilenabstand. Die Anzahl der
 * Zeilen und Spalten hängt von den Einträgen ab, die in die Tabelle sollen. Die 
 * Tabelle muss aber quadratisch sein.
 */
public class Tabelle{
	
	// Erster index ist Zeile und zweiter Index die Spalte
	// Soll spaeter ein Array mit Fischen werden!
	private Fisch[] fische;
	
	private GraphSpiel2 graph;
	
	private int posX = 50;
	private int posY = 50;
	
	private int zeilenanzahl;
	private int spaltenanzahl;
	
	private int zeilenabstand = 50;
	private int kleinerSpaltenabstand = 20;
	private int mittlererSpaltenabstand = 60;
	private int grosserSpaltenabstand = 50;
	
	public Tabelle(Fisch[] fische, int zeilenanzahl, int spaltenanzahl, GraphSpiel2 graph) {
		this.fische = fische; 
		this.graph = graph;
		
		this.zeilenanzahl = zeilenanzahl;
		this.spaltenanzahl = spaltenanzahl;
	}
	
	/**
	 * Malt horizontale Linien der Tabelle.
	 * 
	 * @param g Gehört zur paint Methode, die diese Tabelle malen will.
	 */
	public void horizonzaleLinienMalen(Graphics2D g) {
		
		int x1 = posX;
		int y1 = posY;
		
		int x2 = posX + kleinerSpaltenabstand + mittlererSpaltenabstand + grosserSpaltenabstand;
		int y2 = y1;
		
		g.drawLine(x1, y1, x2, y2);
		// Jede Linie einzeln malen.
		// Kleiner gleich, weil erste und letzte Linie auch gemalt wird.
		for(int i = 1; i <= zeilenanzahl; i++) {
			y1 = y1 + zeilenabstand;
			y2 = y1;
			g.drawLine(x1, y1, x2, y2);
		}
	}
	
	/**
	 * Malt vertikale Linien der Tabelle.
	 * 
	 * @param g Gehört zur paint Methode, die diese Tabelle malen will.
	 */
	public void vertikaleLinienMalen(Graphics2D g) {
		
		// Erste Spalte malen (etwas kleiner als die anderen).
		int x1 = posX;
		int y1 = posY;
		
		int x2 = x1;
		int y2 = posY + zeilenanzahl * zeilenabstand;
		
		g.drawLine(x1, y1, x2, y2);
		
		x1 = posX + kleinerSpaltenabstand;
		x2 = x1;
		
		g.drawLine(x1, y1, x2, y2);
		
		
		x1 = x1 + mittlererSpaltenabstand;
		x2 = x1;
		
		g.drawLine(x1, y1, x2, y2);
		
		x1 = x1 + grosserSpaltenabstand;
		x2 = x1;
		
		g.drawLine(x1, y1, x2, y2);
		/*
		for(int i = 2; i <= spaltenanzahl; i++) {
			x1 = x1 + spaltenabstand;
			x2 = x1;
			g.drawLine(x1, y1, x2, y2);
		}
		*/
	}
	
	public void fischIconsMalen(Graphics2D g) {
		int inset = kleinerSpaltenabstand/3;
		
		for(int i = 0; i < fische.length; i++) {
			
			// Falls der Fisch bereits in einem Aquarium ist, wird er
			// in der Tabelle nicht gezeichnet
			if(fische[i].getInAquarium() != -1) {
				continue;
			}
			
			int positionX = posX + inset;
			int positionY = posY + i*zeilenabstand + inset;
			
			if(fische[i].isGehalten()) {
				fische[i].symbolFarblosMalen(g, positionX, positionY);
			}else {
				fische[i].symbolMalen(g, positionX, positionY);
			}
		}
	}
	
	public void namenEintragen(Graphics2D g) {
		g.setColor(Color.BLACK);
		int inset = 20;
		
		char[] s0 = {'A', 'n', 'n', 'a'};
		char[] s1 = {'B', 'o', 'b'};
		char[] s2 = {'C', 'a', 'n'};
		char[] s3 = {'D', 'o', 'r', 'a'};
		char[] s4 = {'E', 'n', 'z', 'o'};
		char[] s5 = {'F', 'a', 'u', 's', 't'};
		char[] s6 = {'G', 'e', 'r', 'd'};
		char[] s7 = {'H', 'o', 'r', 's', 't'};
		char[] s8 = {'I', 'n', 'g', 'r', 'i', 'd'};
		char[] s9 = {'J', 'a', 'n'};
		
		for(int i = 0; i < fische.length; i++) {
			int positionX = posX + kleinerSpaltenabstand + inset;
			int positionY = posY + i * zeilenabstand + inset;
			
			switch (fische[i].getFischart()) {
			case 0:
				g.drawChars(s0, 0, 4, positionX, positionY);
				break;
			case 1:
				g.drawChars(s1, 0, 3, positionX, positionY);
				break;
			case 2:
				g.drawChars(s2, 0, 3, positionX, positionY);
				break;
			case 3:
				g.drawChars(s3, 0, 4, positionX, positionY);
				break;
			case 4:
				g.drawChars(s4, 0, 4, positionX, positionY);
				break;
			case 5:
				g.drawChars(s5, 0, 5, positionX, positionY);
				break;
			case 6:
				g.drawChars(s6, 0, 4, positionX, positionY);
				break;
			case 7:
				g.drawChars(s7, 0, 5, positionX, positionY);
				break;
			case 8:
				g.drawChars(s8, 0, 6, positionX, positionY);
				break;
			case 9:
				g.drawChars(s9, 0, 3, positionX, positionY);
				break;
			}
			
		}
	}
	
	public void konflikteEintragen(Graphics2D g) {
		g.setColor(Color.BLACK);
		int inset = 20;
		
		char[] s0 = {'A', 'n', 'n', 'a', ',' , ' '};
		char[] s1 = {'B', 'o', 'b', ',' , ' '};
		char[] s2 = {'C', 'a', 'n',',', ' '};
		char[] s3 = {'D', 'o', 'r', 'a', ',' , ' '};
		char[] s4 = {'E', 'n', 'z', 'o', ',' , ' '};
		char[] s5 = {'F', 'a', 'u', 's', 't', ',' , ' '};
		char[] s6 = {'G', 'e', 'r', 'd', ',' , ' '};
		char[] s7 = {'H', 'o', 'r', 's', 't', ',' , ' '};
		char[] s8 = {'I', 'n', 'g', 'r', 'i', 'd', ',' , ' '};
		char[] s9 = {'J', 'a', 'n', ',' , ' '};
		
		for(int i = 0; i < fische.length; i++) {
			for(int j = 0; j < graph.getV().get(i).getAdjazenzliste().size(); j++) {
				
				// Das einzige, was sich geander hat, ist das + 3*kleinerSpaltenabstand
				int positionX = posX + kleinerSpaltenabstand + 3*kleinerSpaltenabstand + inset + j * (kleinerSpaltenabstand + 10);
				int positionY = posY + i * zeilenabstand + inset;
				
				// Zaehle alle Fische in Adjazenzliste auf
				switch (graph.getV().get(i).getAdjazenzliste().get(j).getFischart()) {
				case 0:
					g.drawChars(s0, 0, 4 + 2, positionX, positionY);
					break;
				case 1:
					g.drawChars(s1, 0, 3 + 2, positionX, positionY);
					break;
				case 2:
					g.drawChars(s2, 0, 3 + 2, positionX, positionY);
					break;
				case 3:
					g.drawChars(s3, 0, 4 + 2, positionX, positionY);
					break;
				case 4:
					g.drawChars(s4, 0, 4 + 2, positionX, positionY);
					break;
				case 5:
					g.drawChars(s5, 0, 5 + 2, positionX, positionY);
					break;
				case 6:
					g.drawChars(s6, 0, 4 + 2, positionX, positionY);
					break;
				case 7:
					g.drawChars(s7, 0, 5 + 2, positionX, positionY);
					break;
				case 8:
					g.drawChars(s8, 0, 6 + 2, positionX, positionY);
					break;
				case 9:
					g.drawChars(s9, 0, 3 + 2, positionX, positionY);
					break;
				}
			}
		}
			
	}
	
	/**
	 * Malt die gesamte Tabelle.
	 * 
	 * @param g Gehört zur paint Methode, die diese Tabelle malen will.
	 */
	public void tabelleMalen(Graphics2D g) {
		horizonzaleLinienMalen(g);
		vertikaleLinienMalen(g);
		fischIconsMalen(g);
		namenEintragen(g);
		konflikteEintragen(g);
	}
	
	

	public Fisch[] getFische() {
		return fische;
	}

	public void setFische(Fisch[] eintraege) {
		this.fische = fische;
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

	public int getZeilenabstand() {
		return zeilenabstand;
	}

	public void setZeilenabstand(int zeilenabstand) {
		this.zeilenabstand = zeilenabstand;
	}

	public int getZeilenanzahl() {
		return zeilenanzahl;
	}

	public void setZeilenanzahl(int zeilenanzahl) {
		this.zeilenanzahl = zeilenanzahl;
	}

	public int getSpaltenanzahl() {
		return spaltenanzahl;
	}

	public void setSpaltenanzahl(int spaltenanzahl) {
		this.spaltenanzahl = spaltenanzahl;
	}

	public int getKleinerSpaltenabstand() {
		return kleinerSpaltenabstand;
	}

	public void setKleinerSpaltenabstand(int kleinerSpaltenabstand) {
		this.kleinerSpaltenabstand = kleinerSpaltenabstand;
	}

	public int getMittlererSpaltenabstand() {
		return mittlererSpaltenabstand;
	}

	public void setMittlererSpaltenabstand(int mittlererSpaltenabstand) {
		this.mittlererSpaltenabstand = mittlererSpaltenabstand;
	}

	public int getGrosserSpaltenabstand() {
		return grosserSpaltenabstand;
	}

	public void setGrosserSpaltenabstand(int grosserSpaltenabstand) {
		this.grosserSpaltenabstand = grosserSpaltenabstand;
	}
}

