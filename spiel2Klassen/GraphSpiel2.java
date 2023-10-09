package spiel2Klassen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GraphSpiel2 {
	
	// Die meisten Methoden behandeln Kanten aus E als Paare (a,b), wobei
	// a und b Indizes für Elemente aus V sind und NICHT die Fischart.
	private ArrayList<KnotenSpiel2> V = new ArrayList<KnotenSpiel2>();
	private ArrayList<KanteSpiel2> E = new ArrayList<KanteSpiel2>();
	
	private ArrayList<KanteSpiel2> alleKanten = new ArrayList<KanteSpiel2>();
	
	private ArrayList<ArrayList<KnotenSpiel2>> alleZusammenhangskomponenten = new ArrayList<ArrayList<KnotenSpiel2>>();
	
	private int anzahlKonflikte = 15;
	
	private int anzahlFische = 8;
	
	private int chromatischeZahl;
	private int[] moeglicheFaerbung;
	private boolean moeglicheFaerbungGefunden = false;
	
	private int fehlertoleranz = 0;
	
	public GraphSpiel2(int anzahlKonflikte, int anzahlFische, int fehlertoleranz) {
		this.anzahlKonflikte = anzahlKonflikte;
		this.anzahlFische = anzahlFische;
		this.fehlertoleranz = fehlertoleranz;
		
		fischeGenerieren();
		alleKonflikteGenerieren();
		zufaelligeKonflikte(anzahlKonflikte);
		adjazenzlistenAktualisieren();
		
		alleZusammenhangskomponentenBerechnen();
		/*
		System.out.println("Das ist die Zusammenhangskomponente:");
		arrayListAusgeben(alleZusammenhangskomponenten.get(0));
		System.out.println("Ende");
		*/
		
		/*
		int[] faerbungInZsh = new int[alleZusammenhangskomponenten.get(0).size()];
		int[] faerbungInV = zshZuV(faerbungInZsh, alleZusammenhangskomponenten.get(0));
		
		// i ist die Anzahl der verwendeten Farben
		for(int i = 0; i < anzahlFische; i++) {
			if(moeglicheFaerbungGefunden) {
				break;
			}
			// Fuer jeden Knoten hat das Array eine Stelle.
			// Somit gibt es i^j viele "Zahlen" (Faerbungen)
			// Gehe alle durch
			for(int j = 0; j < Math.pow(i, alleZusammenhangskomponenten.get(0).size()); j++) {
				faerbungInV = zshZuV(faerbungInZsh, alleZusammenhangskomponenten.get(0));
				if(valideFaerbung(faerbungInV, alleZusammenhangskomponenten.get(0))) {
					moeglicheFaerbung = faerbungInV;
					moeglicheFaerbungGefunden = true;
					chromatischeZahl = i;
					break;
				}
				plus1(faerbungInZsh, i);
			}
		}
		System.out.println("Die Faerbung ist: ");
		arrayAusgeben(faerbungInV);
		*/
		
		chromatischeZahl = chromatischeZahlBerechnen();
		//System.out.println("Die chromatische Zahl ist " + chromatischeZahl);
		
	}
	
	public void fischeGenerieren() {
		ArrayList<Integer> fischartenListe = eindeutigeZufallsliste(anzahlFische);
		
		for(int i = 0; i < anzahlFische; i++) {
			V.add(new KnotenSpiel2(fischartenListe.get(i)));
			V.get(i).setIndexInV(i);
		}
	}
	
	// Berechnet alle moeglichen Kanten. Die Menge aller moeglichen
	// Kombinationen V x V kann als Dreiecksarray aufgefasst werden,
	// bei dem man in jeder Zeile alle Elemente rechts von der
	// Hauptdiagonale aufzaehlt
	// TODO: Adjazenzlisten aktualisieren
	public void alleKonflikteGenerieren() {
		for(int i = 0; i < anzahlFische - 1; i++) {
			for(int j = i+1; j < anzahlFische; j++) {
				alleKanten.add(new KanteSpiel2(V.get(i), V.get(j)));
			}
		}
	}
	
	// Fuegt E zufaellige Kanten hinzu
	public void zufaelligeKonflikte(int anzahl) {
		// Wollen zufaellige Indizes von alleKanten waehlen
		ArrayList<Integer> a = eindeutigeZufallsliste(alleKanten.size());
		
		for(int i = 0; i < anzahl; i++) {
			E.add(alleKanten.get(a.get(i)));
		}
	}
	
	// Noch unbenutzt
	public KanteSpiel2 zufaelligeKanteErstellen() {
		Random zufallsgenerator = new Random();
		
		int a = zufallsgenerator.nextInt(anzahlFische);
		int b = zufallsgenerator.nextInt(anzahlFische);
		
		while(a == b) {
			b = zufallsgenerator.nextInt(anzahlFische);
		}
		
		return new KanteSpiel2(V.get(a), V.get(b));
		
	}
	
	// Traegt, nachdem die Kanten erstellt wurden, alles in Adjazenzlisten ein
	public void adjazenzlistenAktualisieren() {
		for(int i = 0; i < E.size(); i++) {
			E.get(i).adjazenzlistenAktualisieren();
		}
	}
	
	// Erstellt eine ArrayList mit nicht-wiederholenden Zufallszahlen von 0 bis "gewuenschte Anzahl - 1"
	public ArrayList<Integer> eindeutigeZufallsliste(int gewuenschteAnzahl) {
		ArrayList<Integer> a = new ArrayList<Integer>();
		
		for(int i = 0; i < gewuenschteAnzahl; i++) {
			a.add(i);
		}
		
		Collections.shuffle(a);
		ArrayList<Integer> b = new ArrayList<Integer>();
		
		for(int i = 0; i < gewuenschteAnzahl; i++) {
			b.add(a.get(i));
		}
		return b;
	}
	
	// Gibt die Fischarten aller Fische in V aus
	public void fischeAusgeben() {
		for(int i = 0; i < V.size(); i++) {
			System.out.println(V.get(i).getFischart());
		}
	}
	
	// Gibt alle Konflikte in der Form von (a,b) aus, wobei
	// a und b die Idizes der entsprechenden Fische in der
	// Knotenliste V sind
	public void konflikteAusgeben() {
		for(int i = 0; i < E.size(); i++) {
			System.out.print(E.get(i).getKnoten1().getIndexInV() + " ");
			System.out.println(E.get(i).getKnoten2().getIndexInV());
		}
	}
	
	////////// DIESER ALGORITHMUS WIRD NICHT MEHR BENUTZT! Plus1 ist besser!/////////////
	// Bestimmt Chromatische Zahl auf dieser Zusammenhangskomponente
	// (Der Algorithmus forscht die gesamte zsh komponente aus. Dh. es reicht,
	// wenn startknoten aus der gewuenschten zshk. ist.
	// TODO: Die Rekursion schlaegt manchmal fehl, wenn die tiefste 
	// Rekursion nicht der letzte Knoten ist.
	public boolean alleFaerbungenTesten(KnotenSpiel2 startknoten, ArrayList<KnotenSpiel2> zusammenhangskomponente, boolean[] betreten, int anzahlFarben, int[] faerbung, int gefaerbteKnoten) {
		
		// Alle rekursionen abbrechen, falls Faerbung gefunden
		if(moeglicheFaerbungGefunden) {
			return true;	// Platzhalter. Dient nur zum Abbruch
		}
		
		// Startknoten wurde betreten
		boolean[] betretenNeu = kopieMitAenderung(betreten,startknoten.getIndexInV(), true);
		
		// Der Startknoten kriegt einmal jede Farbe.
		// Fuer jeweils jede Farbe wird alleFaerbungenTesten bei den Nachbarknoten
		// ausgefuehrt. Dh, man berechnet rekursiv alle Farbmoeglichkeiten
		// mit Startknotenfarbe = grün, dann alle mit Startknotenfarbe = rot usw.
		gefaerbteKnoten++;
		for(int i = 0; i < anzahlFarben; i++) {
			int[] faerbungNeu = kopieMitAenderung(faerbung, startknoten.getIndexInV(), i);
			
			// Falls alle Knoten gefaerbt wurden
			if(gefaerbteKnoten == zusammenhangskomponente.size()) {
				// Pruefe, ob die Faerbung korrekt ist
				if(valideFaerbung(faerbungNeu, zusammenhangskomponente)) {
					// Abspeichern und alle anderen Rekursionsaufrufe beenden
					moeglicheFaerbung = faerbungNeu;
					moeglicheFaerbungGefunden = true;
					chromatischeZahl = anzahlFarben;
					return true;
				}
			}
			
			for(int j = 0; j < startknoten.getAdjazenzliste().size(); j++) {
				if(!betreten[startknoten.getAdjazenzliste().get(j).getIndexInV()]) {	
					alleFaerbungenTesten(startknoten.adjazenzliste.get(j), zusammenhangskomponente, kopie(betretenNeu), anzahlFarben, kopie(faerbungNeu), gefaerbteKnoten);
					// Startet also neuen Aufruf am Nachbarknoten mit gleichen betretenen Arrays (Kopie),
					// gleicher Anzahl an Farben und mit der Faerbung, die diesem Knoten die Farbe i gibt.
				}
			}
		}
		/*
		// Falls alle Knoten gefaerbt wurden
		if(gefaerbteKnoten == zusammenhangskomponente.size()) {
			// Pruefe, ob die Faerbung korrekt ist
			if(valideFaerbung(faerbungNeu, zusammenhangskomponente)) {
				
				// Abspeichern und alle anderen Rekursionsaufrufe beenden
				moeglicheFaerbung = faerbung;
				moeglicheFaerbungGefunden = true;
				chromatischeZahl = anzahlFarben;
				return true;
			}
		}
		return false;
		*/
		
		return false;
	}
	
	// Testet, ob die Faerbung auf der zusammenhangskomponente korrekt ist
	// TODO: Da der Algorithmus jede Adjazenzliste ueberprueft, wird jede Kante
	// zweimal ueberprueft. Laufzeit beachten!
	public boolean valideFaerbung(int[] faerbung, ArrayList<KnotenSpiel2> zusammenhangskomponente) {
		
		boolean valideFaerbung = true;
		// Gehe jeden Knoten durch
		for(int i = 0; i < zusammenhangskomponente.size(); i++) {
			
			// Vergleiche Farbe mit jedem Nachbarn
			for(int j = 0; j < zusammenhangskomponente.get(i).getAdjazenzliste().size(); j++) {
				
				int indexVonKnoten = zusammenhangskomponente.get(i).getIndexInV();
				int indexVonNachbar = zusammenhangskomponente.get(i).getAdjazenzliste().get(j).getIndexInV();
				
				if(faerbung[indexVonKnoten] == faerbung[indexVonNachbar]) {
					valideFaerbung = false;
					break;	// Brauchen nicht weiterzusuchen, wenn die Faerbung bereits invalide ist
					
				}
			}
			
			if(valideFaerbung == false) {
				break;	// Siehe oben
			}
		}
		
		return valideFaerbung;
	}
	
	// Fuehrt rekursiv tiefensuche durch, um eine zusammenhangskomponente 
	// zu berechnen und in knoten zu speichern
	public void tiefensuche(KnotenSpiel2 startknoten, ArrayList<KnotenSpiel2> knoten, boolean[] bereitsBetreten) {
		// Startknoten als betreten markieren
		bereitsBetreten[startknoten.getIndexInV()] = true;
		
		// Startknoten zur Zusammenhangskomponente hinzufuegen
		knoten.add(startknoten);
		
		// In jedem nicht betretenen Nachbarknoten wird tiefensuche ausgefuehrt
		for(int i = 0; i < startknoten.getAdjazenzliste().size(); i++) {
			if(!bereitsBetreten[startknoten.getAdjazenzliste().get(i).getIndexInV()]){
				tiefensuche(startknoten.getAdjazenzliste().get(i), knoten, bereitsBetreten);
			}
		}
	}
	
	// Berechnet alle moeglichen Zusammenhangskomponenten und traegt sie in
	// "alleZusammenhangskomponenten" ein
	public void alleZusammenhangskomponentenBerechnen() {
		// Bemerkung: boolean ist am Anfang immer false
		boolean[] bereitsBetreten = new boolean[anzahlFische];
		
		int anzahlListen = 0;
		// Gehe alle Knoten durch. Falls noch nicht von vorherigen 
		// Tiefensuchen betreten, so starte dort neue Tiefensuche
		for(int i = 0; i < bereitsBetreten.length; i++) {
			if(!bereitsBetreten[i]) {
				// Tiefensuche soll neue Liste fuer jede Zusammenhangskomponente nehmen
				alleZusammenhangskomponenten.add(new ArrayList<KnotenSpiel2>());
				tiefensuche(V.get(i), alleZusammenhangskomponenten.get(anzahlListen), bereitsBetreten);
				anzahlListen++;
			}
		}
	}
	
	// Wird benutzt, um alle moeglichen Faerbungen zu berechnen.
	// Jede Faerbung ist ein int array, wobei die Anzahl der verschiedenen
	// Farben (also die Eintraege) beschraenkt sind durch anzahlFarben.
	// Dieses Array kann als Zahl in Basis anzahlFarben aufgefasst werden.
	// Alle Zahlen koennen dann durch Addition konstruiert werden.
	// Algorithmus ist im Wesentlichen schriftliche Addition
	public void plus1(int[] a, int basis) {
		int indexLetzteZiffer = a.length - 1;
		
		// Beispiel : 76 + 1 = 77, kein Ubertrag, da 6 < 10
		// Haengt also von letzter Stelle ab.
		if(a[indexLetzteZiffer] + 1 < basis) {
			a[indexLetzteZiffer]++;
		}
		else if(a[indexLetzteZiffer] + 1 == basis) {	// Uebertrag
			for(int i = indexLetzteZiffer; i >= 0; i--) {
				// Falls Ubertrag zu addieren selbst wieder einen
				// Uebertrag erzeugt
				if(a[i] + 1 == basis) {
					a[i] = 0;
				}
				// Falls Uebertrag zu addieren keinen Uebertrag erzeugt
				else if(a[i] + 1 < basis) {
					a[i]++;
					break;	// Fertig, da Ubertrag "abgelegt"
				}
				
			}
		}
	}
	// Berechnet chromatische Zahl einer bestimmten zusammenhangskomponenten.
	public int chromatischeZahlVon(ArrayList<KnotenSpiel2> zusammenhangskomponente) {
		
		// Der Algorithmus prueft nur die Faelle anzahlFarben < anzahlKnoten
		// Falls nicht gefunden, so gilt Gleichheit
		int chromZahl = zusammenhangskomponente.size();
		
		int[] faerbungInZsh = new int[zusammenhangskomponente.size()];
		int[] faerbungInV = zshZuV(faerbungInZsh, zusammenhangskomponente);
		
		// i ist die Anzahl der verwendeten Farben
		// Alle Anzahlen von 0 bis 8 durchgehen.
		for(int i = 0; i < anzahlFische; i++) {
			if(moeglicheFaerbungGefunden) {
				break;
			}
			// Fuer jeden Knoten hat das Array eine Stelle.
			// Somit gibt es i^j viele "Zahlen" (Faerbungen)
			// Gehe alle durch.
			for(int j = 0; j < Math.pow(i, zusammenhangskomponente.size()); j++) {
				faerbungInV = zshZuV(faerbungInZsh, zusammenhangskomponente);
				if(valideFaerbung(faerbungInV, zusammenhangskomponente)) {
					moeglicheFaerbungGefunden = true;
					chromZahl = i;
					break;
				}
				plus1(faerbungInZsh, i);
			}
		}
		
		return chromZahl;
		
	}
	
	
	// Berechnet die chromatischeZahl des gesamten Graphen.
	// Ansatz:
	// 0. Bemerkung: chromZahl des ganzen Graphen 
	// = groesste chromZahl aller Zusammenhangskomponenten
	// 1. Berechne chromZahl der groessten Zusammenhangskomponente.
	// 2. Bemerkung: chromZahl ist durch Knotenzahl beschraenkt
	// ---> Berechne uebrige chromZahlen nur, falls deren Knotenzahl
	// nicht bereits kleiner ist
	// 3. Groesste chromZahl von allen zshKomp ist die chromZahl des gesamten Graphen.
	public int chromatischeZahlBerechnen() {
		ArrayList<Integer> chromatischeZahlen = new ArrayList<Integer>();
		
		// Finde groesste Zusammenhangskomponente und berechne zuerst ihre chromatische Zahl
		// Da die chromatische Zahl des gesamten Graphen der chromatischen Zahl der
		// Zusammenhangskomponente mit der hoechsten chromatischen Zahl entspricht,
		// berechne diese dann alle einzeln. Unter Umstaenden reicht die erste bereits, da
		// die chromatische Zahl der anderen zsh komp. durch ihre Knotenzahl beschraenkt ist.
		int index = 0;
		for(int i = 0; i < alleZusammenhangskomponenten.size(); i++) {
			if(alleZusammenhangskomponenten.get(i).size() > i) {
				index = i;
			}
		}
		
		ArrayList<KnotenSpiel2> groessteZshkomp = alleZusammenhangskomponenten.get(index);
		
		int chromatischeZahlGR = chromatischeZahlVon(groessteZshkomp);
		chromatischeZahlen.add(chromatischeZahlGR);
		
		for(int i = 0; i < alleZusammenhangskomponenten.size(); i++) {
			// Die chromatischen Zahlen der anderen zusammenhangskomponenten sind durch
			// ihre Knotenzahl beschraenkt. Ist diese bereits kleiner als 
			// chromatischeZahlGR, so braucht man nicht zu pruefen
			if(i != index) {
				if(alleZusammenhangskomponenten.get(i).size() > chromatischeZahlGR) {	
					chromatischeZahlen.add(chromatischeZahlVon(alleZusammenhangskomponenten.get(i)));
				}
			}
		}
		
		// Finde groesste chromatische Zahl von allen Zusammenhangskomponenten
		
		int max = chromatischeZahlGR;
		for(int i = 0; i < chromatischeZahlen.size(); i++) {
			if(max < chromatischeZahlen.get(i)) {
				max = chromatischeZahlen.get(i);
			}
		}
		
		return max;
	}
	
	// Macht eine Faerbung einer Zsh Komponente zu einer Faerbung auf V
	// Betrifft im Wesentlichen die Indizierung. Ein Array, dessen i-ter Eintrag
	// dem i-ten Knoten der zshkomp entspricht wird zu einem Array fuer ganz V,
	// aber alle Eintraege bleiben immer noch am Index des gleichen Knotens.
	public int[] zshZuV(int[] a, ArrayList<KnotenSpiel2> zusammenhangskomponente) {
		// Fuelle b mit -1
		int[] b = ones(-1, V.size());
		
		// Tut jeden Eintrag von a an die richtige Stelle von b
		for(int i = a.length - 1; i >= 0; i--) {
			b[zusammenhangskomponente.get((a.length - 1) - i).getIndexInV()] = a[i];
		}
		
		return b;
	}
	
	////// Nachfolgende Methoden sind primaer fuer Gemuetlichkeit/////
	// kopiert a in ein neues array (int)
	public int[] kopie(int[] a) {
		int[] b = new int[a.length];
		for(int i = 0; i < a.length; i++) {
			b[i] = a[i];
		}
		
		return b;
	}
	// kopiert a in ein neues array (boolean)
	public boolean[] kopie(boolean[] a) {
		boolean[] b = new boolean[a.length];
		for(int i = 0; i < a.length; i++) {
			b[i] = a[i];
		}
		
		return b;
	}
	
	// Kopiert a in ein neues array, aendert den Wert an b[index] zu neu und
	// returned dann dieses b.
	public int[] kopieMitAenderung(int[] a, int index, int neu) {
		int[] b = kopie(a);
		b[index] = neu;
		return b;
		
	}
	
	public boolean[] kopieMitAenderung(boolean[] a, int index, boolean neu) {
		boolean[] b = kopie(a);
		b[index] = neu;
		return b;
	}
	
	public void arrayAusgeben(int[] a) {
		for(int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
		}
	}
	
	public void arrayListAusgeben(ArrayList<KnotenSpiel2> a) {
		for(int i = 0; i < a.size(); i++) {
			System.out.println(a.get(i).getIndexInV());
		}
	}
	
	// Erstellt array mit entsprechender groesse, was nur zahl in jedem Eintrag hat
	// Name inspiriert von aehnlichem Befehl in Matlab/Julia
	public int[] ones(int zahl, int groesse) {
		
		int[] b = new int[groesse];
		for(int i = 0; i < b.length; i++) {
			b[i] = zahl;
		}
		
		return b;
	}

	public ArrayList<KnotenSpiel2> getV() {
		return V;
	}

	public void setV(ArrayList<KnotenSpiel2> v) {
		V = v;
	}

	public ArrayList<KanteSpiel2> getE() {
		return E;
	}

	public void setE(ArrayList<KanteSpiel2> e) {
		E = e;
	}

	public ArrayList<KanteSpiel2> getAlleKanten() {
		return alleKanten;
	}

	public void setAlleKanten(ArrayList<KanteSpiel2> alleKanten) {
		this.alleKanten = alleKanten;
	}

	public ArrayList<ArrayList<KnotenSpiel2>> getAlleZusammenhangskomponenten() {
		return alleZusammenhangskomponenten;
	}

	public void setAlleZusammenhangskomponenten(ArrayList<ArrayList<KnotenSpiel2>> alleZusammenhangskomponenten) {
		this.alleZusammenhangskomponenten = alleZusammenhangskomponenten;
	}

	public int getAnzahlKonflikte() {
		return anzahlKonflikte;
	}

	public void setAnzahlKonflikte(int anzahlKonflikte) {
		this.anzahlKonflikte = anzahlKonflikte;
	}

	public int getAnzahlFische() {
		return anzahlFische;
	}

	public void setAnzahlFische(int anzahlFische) {
		this.anzahlFische = anzahlFische;
	}

	public int getChromatischeZahl() {
		return chromatischeZahl;
	}

	public void setChromatischeZahl(int chromatischeZahl) {
		this.chromatischeZahl = chromatischeZahl;
	}

	public int[] getMoeglicheFaerbung() {
		return moeglicheFaerbung;
	}

	public void setMoeglicheFaerbung(int[] moeglicheFaerbung) {
		this.moeglicheFaerbung = moeglicheFaerbung;
	}

	public boolean isMoeglicheFaerbungGefunden() {
		return moeglicheFaerbungGefunden;
	}

	public void setMoeglicheFaerbungGefunden(boolean moeglicheFaerbungGefunden) {
		this.moeglicheFaerbungGefunden = moeglicheFaerbungGefunden;
	}

	public int getFehlertoleranz() {
		return fehlertoleranz;
	}

	public void setFehlertoleranz(int fehlertoleranz) {
		this.fehlertoleranz = fehlertoleranz;
	}

}
