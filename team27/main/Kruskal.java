package team27.main;

/**
 * Autor: Carolin Bous Diese Klasse wird für die Labyrinth Erzeugung benötigt
 * und liefert die Operationen die für Kruskals algortihmus benötigt werden
 */

public class Kruskal {
	private int[] allElements;

	public Kruskal(int number_Elements) {
		allElements = new int[number_Elements];

		makeSet();
	}

	public void makeSet() {
		for (int i = 0; i < allElements.length; i++) {
			allElements[i] = i;
		}
	}

	public void union2Sets(int set1, int set2, int cellIndex2) {
		int x = allElements[cellIndex2];
		for (int i = 0; i < allElements.length; i++) {
			if (x == allElements[i]) {
				allElements[i] = set1;
			}
		}
	}

	public int findSetWithX(int x) {
		return allElements[x];
	}

	public boolean proveAllConnected() {
		boolean prove = false;
		loop: for (int i = 0; i < allElements.length - 1; i++) {
			if (allElements[i] == allElements[i + 1]) { //
				prove = true;
			} else {
				prove = false;
				break loop;
			}
		}
		return prove;
	}
}