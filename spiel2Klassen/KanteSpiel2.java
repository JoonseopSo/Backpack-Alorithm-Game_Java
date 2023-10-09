package spiel2Klassen;

public class KanteSpiel2 {
	
	private KnotenSpiel2 knoten1;
	private KnotenSpiel2 knoten2;
	
	public KanteSpiel2(KnotenSpiel2 knoten1, KnotenSpiel2 knoten2) {
		this.knoten1 = knoten1;
		this.knoten2 = knoten2;
	}
	
	// Vergleicht anhand der Indizes in V, ob die dadurch definierte
	// Kante tatsaechlich diese Kante ist. Dabei ist (a,b) = (b,a)
	public boolean istKante(int a, int b) {
		if(knoten1.getIndexInV() == a && knoten2.getIndexInV() == b) {
			return true;
		}
		
		if(knoten1.getIndexInV() == b && knoten2.getIndexInV() == a) {
			return true;
		}
		
		return false;
	}
	
	// Ist diese Kante (a,b), so wird 1 ausgegeben.
	// Ist sie (b,a), so wird -1 ausgegeben.
	// Sonst wird 0 ausgegeben.
	public int istWelcheKante(int a, int b) {
		if(knoten1.getIndexInV() == a && knoten2.getIndexInV() == b) {
			return 1;
		}
		
		if(knoten1.getIndexInV() == b && knoten2.getIndexInV() == a) {
			return -1;
		}
		
		return 0;
	}
	
	// Testet, ob die Kante an einer der beiden Seiten a beruehrt
	public boolean beruehrt(int a) {
		if(knoten1.getIndexInV() == a || knoten2.getIndexInV() == a) {
			return true;
		}
		
		return false;
	}
	
	// Aktualisiert die Adjazenzlisten der beiden verbundenen Knoten
	public void adjazenzlistenAktualisieren() {
		knoten1.getAdjazenzliste().add(knoten2);
		knoten2.getAdjazenzliste().add(knoten1);
	}
	
	public int indexK1() {
		return knoten1.getIndexInV();
	}
	
	public int indexK2() {
		return knoten2.getIndexInV();
	}

	public KnotenSpiel2 getKnoten1() {
		return knoten1;
	}

	public void setKnoten1(KnotenSpiel2 knoten1) {
		this.knoten1 = knoten1;
	}

	public KnotenSpiel2 getKnoten2() {
		return knoten2;
	}

	public void setKnoten2(KnotenSpiel2 knoten2) {
		this.knoten2 = knoten2;
	}

}
