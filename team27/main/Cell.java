package team27.main;

/**
 * Hilfsklasse um Zellen Objekte zu erzeugen
 */

public class Cell {
	int[] borders = new int[4]; // 4 border
	int flagVisited;
	private boolean vonFigurBetreten = false;

	public Cell() {
		borders = new int[4];
		flagVisited = -1;
	}

	public int[] getBorders() {
		return borders;
	}

	public void setBorders(int[] borders) {
		this.borders = borders;
	}

	public int isFlagVisited() {
		return flagVisited;
	}

	public void setFlagVisited(int flagVisited) {
		this.flagVisited = flagVisited;
	}

	public boolean isVonFigurBetreten() {
		return vonFigurBetreten;
	}

	public void setVonFigurBetreten(boolean vonFigurBetreten) {
		this.vonFigurBetreten = vonFigurBetreten;
	}
}
