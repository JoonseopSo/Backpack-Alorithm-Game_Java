package spiel2Klassen;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Aquarium {
	private ArrayList<Fisch> fische = new ArrayList<Fisch>();
	
	private int breite;
	private int hoehe;
	
	private int posX;
	private int posY;
	
	private boolean leer = true;
	
	// TODO Hardcoded!
	
	public Aquarium() {
		breite = 50;
		hoehe = 50;
	}
	
	public void aquariumMalen(Graphics2D g, int posX, int posY) {
		g.setStroke(new BasicStroke(4));
		g.drawRect(posX, posY, breite, hoehe);
		g.setStroke(new BasicStroke(1));
	}
	
	// Testet, ob die Position (des Cursors) im Aquarium ist
	public boolean hoversOverAquarium(int x, int y) {
		if( (x > posX) && (x < posX + breite) && (y > posY) && (y < posY + hoehe) ) {
			return true;
		}
		return false;	
	}
	
	public void fischeMalen(Graphics2D g) {
		int abstand = 25;
		int inset = 5;
		for(int i = 0; i < fische.size(); i++) {
			fische.get(i).symbolMalen(g, inset + posX + i*abstand, inset + posY);
			// Aktualisiere Fischposition
			fische.get(i).setPosX(inset + posX + i*abstand);
			fische.get(i).setPosY(inset + posY);
		}
	}
	
	public void fischEntfernen(int welcheArt) {
		for(int i = 0; i < fische.size(); i++) {
			if(fische.get(i).getFischart() == welcheArt) {
				fische.remove(i);
			}
		}
	}
	
	public void alleFischeEntfernen() {
		fische.removeAll(fische);
	}
	
	public boolean pruefenObLeer() {
		if(fische.size() == 0) {
			return true;
		}
		return false;
	}
	
	public void fischHinzufuegen(Fisch fisch) {
		fische.add(fisch);
	}

	public ArrayList<Fisch> getFische() {
		return fische;
	}

	public void setFische(ArrayList<Fisch> fische) {
		this.fische = fische;
	}

	public int getBreite() {
		return breite;
	}

	public void setBreite(int breite) {
		this.breite = breite;
	}

	public int getHoehe() {
		return hoehe;
	}

	public void setHoehe(int hoehe) {
		this.hoehe = hoehe;
	}

	public boolean isLeer() {
		return leer;
	}

	public void setLeer(boolean leer) {
		this.leer = leer;
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

}
