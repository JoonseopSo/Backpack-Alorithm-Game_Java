package spiel2Klassen;

import java.util.ArrayList;

public class KnotenSpiel2 {
	
	// Liste der Knoten, die zu diesem adjazent sind
	ArrayList<KnotenSpiel2> adjazenzliste = new ArrayList<KnotenSpiel2>();
	
	// Grad des Knotens
	private int grad;
	
	// Fischart, die der Knoten repraesentiert
	private int fischart;
	
	// Knotenfarbe
	private int farbe;
	
	private int indexInV;
	
	public KnotenSpiel2(int fischart) {
		this.fischart = fischart;
	}

	public ArrayList<KnotenSpiel2> getAdjazenzliste() {
		return adjazenzliste;
	}

	public void setAdjazenzliste(ArrayList<KnotenSpiel2> adjazenzliste) {
		this.adjazenzliste = adjazenzliste;
	}

	public int getGrad() {
		return grad;
	}

	public void setGrad(int grad) {
		this.grad = grad;
	}

	public int getFischart() {
		return fischart;
	}

	public void setFischart(int fischart) {
		this.fischart = fischart;
	}

	public int getFarbe() {
		return farbe;
	}

	public void setFarbe(int farbe) {
		this.farbe = farbe;
	}

	public int getIndexInV() {
		return indexInV;
	}

	public void setIndexInV(int indexInV) {
		this.indexInV = indexInV;
	}

}
