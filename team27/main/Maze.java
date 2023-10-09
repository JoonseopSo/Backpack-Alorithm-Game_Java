package team27.main;

import java.awt.*;
import java.util.*;

/**
 * Autor: Carolin Bous Diese Klasse erzeugt ein Labyrith mit dem Algorithmus von
 * Kruskal in Kruskal.java Diese wird dann noch 3 mal gespiegelt
 */

public class Maze {

	private int margin = 60;
	private Cell[] cells;
	private boolean[] path1;
	private boolean[] path2;
	private int north_border = 0;
	private int east_border = 1;
	private int south_border = 2;
	private int west_border = 3;
	private int square_size;
	private int draw_height;
	private int differentpaths;

	// Koordinaten der Figur. Wird spaeter auf Koordinaten von Startfeld gesetzt.
	private int i_Figur = 0;
	private int j_Figur = 0;

	private boolean sollRotSein = false;
	private boolean sollRotSein_alt = false;

	// Vorherige Koordinaten der Figur
	private int i_Figur_alt = 0;
	private int j_Figur_alt = 0;

	private boolean schonGespiegelt = false;

	private boolean figurUnbewegt = true;

	/*
	 * 
	 * 
	 * 
	 * */
	public Maze(int height) {
		path1 = new boolean[height * height];
		path2 = new boolean[height * height];

		draw_height = height;
		cells = new Cell[height * height]; // number of required cells

		for (int i = 0; i < height * height; i++) {// creates for each required cell one object
			cells[i] = new Cell();
		}

		makeBorders(height);// creates each possible wall
		clearBorders(); // destroys wall to create a maze
		generateSecondPath();
		findPath1();
		findPath2();
		cells[draw_height * draw_height - 1].borders[1] = 0;
		cells[draw_height * draw_height - 1].borders[2] = 0;
		cells[0].borders[3] = -2;

		// Startposition festlegen
		i_Figur = height - 1;
		j_Figur = height - 1;

		i_Figur_alt = height - 1;
		j_Figur_alt = height - 1;
	}

	/*
	 * Creates Each possible boarder until a grid is created Sets each outer Border
	 * to -1, to recognise which border should not be deleted
	 * 
	 */
	public void makeBorders(int height) {// -1 border wall, 1 normal wall, 0 no wall
		for (int i = 0; i < height * height; i++) {// Set each wall possible
			cells[i].borders[north_border] = 1;
			cells[i].borders[east_border] = 1;
			cells[i].borders[south_border] = 1;
			cells[i].borders[west_border] = 1;
		}

		// Set outer borders to -1, mark them, they may not be deleted
		for (int i = 0; i < draw_height; i++) {
			cells[i].borders[north_border] = -1;
			cells[draw_height * draw_height - i - 1].borders[south_border] = -1;
		}
		for (int i = 0; i < draw_height * draw_height; i += draw_height) {
			cells[i].borders[west_border] = -1;
			cells[draw_height * draw_height - i - 1].borders[east_border] = -1;
		}
	}

	/*
	 * Destroys walls till a maze is formed and every cell is in one Set Kruskal
	 * Operations are Used, Union, makeSet, proveAllConnected, findSetWithX Deletes
	 * manually the outer east and south wall to connect later each maze
	 */
	public void clearBorders() {
		int number_Elements = draw_height * draw_height;

		Kruskal set = new Kruskal(number_Elements);
		Random generator = new Random();

		while (set.proveAllConnected() == false) // while there a more than one set, so maze is not ready
		{
			int cellIndex1;
			int border1;
			int cellIndex2;
			int border2;
			do {
				cellIndex1 = generator.nextInt(draw_height * draw_height);
				border1 = generator.nextInt(4);

				if (border1 == north_border && cells[cellIndex1].borders[border1] != -1) {
					cellIndex2 = cellIndex1 - draw_height;
					border2 = 2;
				} else if (border1 == east_border && cells[cellIndex1].borders[border1] != -1) {
					cellIndex2 = cellIndex1 + 1;
					border2 = 3;
				} else if (border1 == south_border && cells[cellIndex1].borders[border1] != -1) {
					cellIndex2 = cellIndex1 + draw_height;
					border2 = 0;
				} else if (border1 == west_border && cells[cellIndex1].borders[border1] != -1) {
					cellIndex2 = cellIndex1 - 1;
					border2 = 1;
				} else {
					border2 = -1;
					cellIndex2 = -1;
				}
			} while (border2 == -1 || cellIndex2 == -1);

			if (cells[cellIndex1].borders[border1] != -1) {
				int x1 = set.findSetWithX(cellIndex1);
				int x2 = set.findSetWithX(cellIndex2);
				if (set.findSetWithX(cellIndex1) != set.findSetWithX(cellIndex2)) {

					cells[cellIndex1].borders[border1] = 0;
					cells[cellIndex2].borders[border2] = 0;

					set.union2Sets(x1, x2, cellIndex2); // 2 Set zusammenführen um Weg entstehen zu lassen
				}
			}
		}

	}

	public void generateSecondPath() {
		Random generator = new Random();
		// Create Second Path
		int cellIndexSecondPath = generator.nextInt(draw_height * draw_height - 1 - draw_height - draw_height)
				+ draw_height + 1;
		int borderSecondPath = generator.nextInt(4);
		boolean secondPath = false;
		while (secondPath == false) {
			if (cells[cellIndexSecondPath].borders[borderSecondPath] == 1) {

				if (borderSecondPath == north_border && (cells[cellIndexSecondPath - 1].borders[borderSecondPath] != 0
						&& cells[cellIndexSecondPath + 1].borders[borderSecondPath] != 0)) {
					cells[cellIndexSecondPath].borders[0] = 0;
					cells[cellIndexSecondPath - draw_height].borders[2] = 0;
					secondPath = true;
				}
				if (borderSecondPath == east_border
						&& (cells[cellIndexSecondPath - draw_height].borders[borderSecondPath] != 0
								&& cells[cellIndexSecondPath + draw_height].borders[borderSecondPath] != 0)) {
					cells[cellIndexSecondPath].borders[1] = 0;
					cells[cellIndexSecondPath + 1].borders[3] = 0;
					secondPath = true;
				}
				if (borderSecondPath == south_border && (cells[cellIndexSecondPath - 1].borders[borderSecondPath] != 0
						&& cells[cellIndexSecondPath + 1].borders[borderSecondPath] != 0)) {
					cells[cellIndexSecondPath].borders[2] = 0;
					cells[cellIndexSecondPath + draw_height].borders[0] = 0;
					secondPath = true;
				}
				if (borderSecondPath == west_border
						&& (cells[cellIndexSecondPath + draw_height].borders[borderSecondPath] != 0
								&& cells[cellIndexSecondPath - draw_height].borders[borderSecondPath] != 0)) {
					cells[cellIndexSecondPath].borders[3] = 0;
					cells[cellIndexSecondPath - 1].borders[1] = 0;
					secondPath = true;
				}
			}
			cellIndexSecondPath = generator.nextInt(draw_height * draw_height - 1 - draw_height - draw_height)
					+ draw_height + 1;
			borderSecondPath = generator.nextInt(4);
		}
	}

	/**
	 * Autor: Jonte Jakob Hier wird der (erste) kürzeste Weg bestimmt mithilfe des
	 * Dijkstra-Algorithmus bestimmt. der Weg wird in einem boolean-Array
	 * gespeichert, welches true ist, falls die Zelle auf dem kürzesten Weg liegt
	 * und false sonst.
	 */
	public void findPath1() // finds a path in the maze
	{

		if (draw_height != 1) // more than 1 cell
		{
			dijkstras1(0); // start top left

			path1[0] = true; // start cell is true
			path1[draw_height * draw_height - 1] = true; // target cell is true

			int current = cells[draw_height * draw_height - 1].flagVisited; // go from target backwards
			while (current != 0) // go back to 0, start
			{
				path1[current] = true;
				current = cells[current].flagVisited;
			}
		}

		for (int i = 0; i < cells.length; i++) {
			cells[i].flagVisited = -1;
		}
	}

	/**
	 * Autor: Jonte Jakob Dijkstra-Algorithmus Version 1. Wenn 2 Zellen noch nicht
	 * bearbeitet wurden und die gleiche Anzahl der Schritte haben, die zu Ihnen
	 * gebraucht wird, dann wird die Zelle mit dem niedrigeren Index zuerst
	 * bearbeitet
	 * 
	 * @param cell Startzelle
	 */
	public void dijkstras1(int cell) {
		int u;
		int[] distance = new int[cells.length];
		boolean[] queue = new boolean[cells.length];
		for (int i = 0; i < cells.length; i++) {
			distance[i] = Integer.MAX_VALUE;
			queue[i] = false;
		}
		distance[cell] = 0;
		for (int i = 1; i < cells.length; i++) {
			int min = Integer.MAX_VALUE;
			int vertex = -1;
			for (int j = 0; j < cells.length; j++) {
				if (queue[j] == false && distance[j] < min) {
					min = distance[j];
					vertex = j;
				}
			}
			u = vertex;
			queue[u] = true;
			if (cells[u].borders[0] == 0) {
				if (queue[u - draw_height] == false && distance[u] + 1 < distance[u - draw_height]) {
					distance[u - draw_height] = distance[u] + 1;
					cells[u - draw_height].flagVisited = u;
				}
			}
			if (cells[u].borders[1] == 0) {
				if (queue[u + 1] == false && distance[u] + 1 < distance[u + 1]) {
					distance[u + 1] = distance[u] + 1;
					cells[u + 1].flagVisited = u;
				}
			}
			if (cells[u].borders[2] == 0) {
				if (queue[u + draw_height] == false && distance[u] + 1 < distance[u + draw_height]) {
					distance[u + draw_height] = distance[u] + 1;
					cells[u + draw_height].flagVisited = u;
				}
			}
			if (cells[u].borders[3] == 0) {
				if (queue[u - 1] == false && distance[u] + 1 < distance[u - 1]) {
					distance[u - 1] = distance[u] + 1;
					cells[u - 1].flagVisited = u;
				}
			}
		}
	}

	/**
	 * Autor: Jonte Jakob Dijkstra-Algorithmus Version 2. Wenn 2 Zellen noch nicht
	 * bearbeitet wurden und die gleiche Anzahl der Schritte haben, die zu Ihnen
	 * gebraucht wird, dann wird die Zelle mit dem höheren Index zuerst bearbeitet
	 * 
	 * @param cell Startzelle
	 */
	public void dijkstras2(int cell) {
		int u;
		int[] distance = new int[cells.length];
		boolean[] queue = new boolean[cells.length];
		for (int i = 0; i < cells.length; i++) {
			distance[i] = Integer.MAX_VALUE;
			queue[i] = false;
		}
		distance[cell] = 0;
		for (int i = 1; i < cells.length; i++) {
			int min = Integer.MAX_VALUE;
			int vertex = -1;
			for (int j = cells.length - 1; j >= 0; j--) {
				if (queue[j] == false && distance[j] < min) {
					min = distance[j];
					vertex = j;
				}
			}
			u = vertex;
			queue[u] = true;
			if (cells[u].borders[0] == 0) {
				if (queue[u - draw_height] == false && distance[u] + 1 < distance[u - draw_height]) {
					distance[u - draw_height] = distance[u] + 1;
					cells[u - draw_height].flagVisited = u;
				}
			}
			if (cells[u].borders[1] == 0) {
				if (queue[u + 1] == false && distance[u] + 1 < distance[u + 1]) {
					distance[u + 1] = distance[u] + 1;
					cells[u + 1].flagVisited = u;
				}
			}
			if (cells[u].borders[2] == 0) {
				if (queue[u + draw_height] == false && distance[u] + 1 < distance[u + draw_height]) {
					distance[u + draw_height] = distance[u] + 1;
					cells[u + draw_height].flagVisited = u;
				}
			}
			if (cells[u].borders[3] == 0) {
				if (queue[u - 1] == false && distance[u] + 1 < distance[u - 1]) {
					distance[u - 1] = distance[u] + 1;
					cells[u - 1].flagVisited = u;
				}
			}
		}
	}

	/**
	 * Autor: Jonte Jakob Hier wird der (zweite) kürzeste Weg bestimmt mithilfe des
	 * Dijkstra-Algorithmus bestimmt. der Weg wird in einem boolean-Array
	 * gespeichert, welches true ist, falls die Zelle auf dem kürzesten Weg liegt
	 * und false sonst.
	 */
	public void findPath2() // finds a path in the maze
	{

		if (draw_height != 1) // more than 1 cell
		{
			dijkstras2(0); // start top left

			path2[0] = true; // start cell is true
			path2[draw_height * draw_height - 1] = true; // target cell is true

			int current = cells[draw_height * draw_height - 1].flagVisited; // go from target backwards
			while (current != 0) // go back to 0, start
			{
				path2[current] = true;
				current = cells[current].flagVisited;
			}
		}

	}

	/*
	 * Draws the borders of the maze, depending if there is an entry for it Draws
	 * them from top to down, from left to right (see if Abfrage j!=0)
	 */
	public void draw(Graphics g, int frameHeight, int frameWidth) {

		if (frameHeight <= frameWidth) { // chose smallest side to draw maze
			square_size = (frameHeight / 2 - 50) / draw_height * 2;
		} else {
			square_size = (frameWidth / 2 - 50) / draw_height * 2;
		}

		g.setColor(Color.BLACK);

		for (int i = 0; i < draw_height; i++) {
			int count = i;
			for (int j = 0; j < draw_height; j++) {

				if (j != 0) {
					count = count + draw_height;
				}

				if (cells[count].borders[0] != 0) {
					g.drawLine((i * square_size) + margin, (j * square_size) + margin, ((i + 1) * square_size) + margin,
							(j * square_size) + margin);
				}

				if (cells[count].borders[1] != 0) {
					g.drawLine(((i + 1) * square_size) + margin, (j * square_size) + margin,
							((i + 1) * square_size) + margin, ((j + 1) * square_size) + margin);
				}

				if (cells[count].borders[2] != 0) {
					g.drawLine((i * square_size) + margin, ((j + 1) * square_size) + margin,
							((i + 1) * square_size) + margin, ((j + 1) * square_size) + margin);
				}

				if (cells[count].borders[3] != 0) {
					g.drawLine((i * square_size) + margin, (j * square_size) + margin, (i * square_size) + margin,
							((j + 1) * square_size) + margin);
				}
			}
		}

		g.setColor(Color.BLUE);
		betreteneFelderKennzeichnen(g);

		g.setColor(new Color(51, 204, 255));

		// Zielfelder
		g.drawRect(margin - square_size, margin, square_size, square_size);
		g.fillRect(margin - square_size, margin, square_size, square_size);

		g.drawRect(draw_height * square_size + margin, margin, square_size, square_size);
		g.fillRect(draw_height * square_size + margin, margin, square_size, square_size);

		g.drawRect(margin - square_size, draw_height * square_size + margin - square_size, square_size, square_size);
		g.fillRect(margin - square_size, draw_height * square_size + margin - square_size, square_size, square_size);

		g.drawRect(draw_height * square_size + margin, draw_height * square_size + margin - square_size, square_size,
				square_size);
		g.fillRect(draw_height * square_size + margin, draw_height * square_size + margin - square_size, square_size,
				square_size);

		g.setColor(Color.BLACK);
		if (i_Figur == -1 && j_Figur == 0&&!sollRotSein) {
			g.setColor(Color.pink);
		}

		g.drawString("Spiel 1", margin - square_size - 10, margin - 4);
		g.setColor(Color.BLACK);
		if (i_Figur == draw_height && j_Figur == 0&&!sollRotSein) {
			g.setColor(Color.pink);
		}

		g.drawString("Spiel 2", draw_height * square_size + margin, margin - 4);
		g.setColor(Color.BLACK);
		if (i_Figur == -1 && j_Figur == draw_height - 1&&!sollRotSein) {
			g.setColor(Color.pink);
		}

		g.drawString("Spiel 3", margin - square_size - 10, draw_height * square_size + margin + square_size / 2+5);
		g.setColor(Color.BLACK);
		if (i_Figur == draw_height && j_Figur == draw_height - 1&&!sollRotSein) {
			g.setColor(Color.pink);
		}

		g.drawString("Spiel 4", draw_height * square_size + margin - 4,
				draw_height * square_size + margin + square_size / 2+5);

		figurfarbeAnpassen(g);
		figurMalen(g);

		// Startfeld
		g.setColor(new Color(154, 154, 154));
		g.drawRect((draw_height * square_size) / 2 + margin - square_size + 4,
				(draw_height * square_size) / 2 + margin - square_size + 4, square_size - 4, square_size - 4);
		g.setColor(new Color(254, 254, 254));
	}

	public void figurfarbeAnpassen(Graphics g) {
		int index = i_Figur + j_Figur * draw_height;

		if (schonGespiegelt) {
			if (istZielfeld() && !sollRotSein) {
				g.setColor(Color.GREEN);

			} else if (istZielfeld() && sollRotSein) {
				g.setColor(Color.RED);
			} else if ((!cells[index].isVonFigurBetreten() && !sollRotSein) || figurUnbewegt) {
				g.setColor(Color.GREEN);
			}

			else {
				g.setColor(Color.RED);
				sollRotSein = true; // Soll ab jetzt rot bleiben

			}
		}
	}

	/**
	 * Autor: Jonte Jakob In dieser Methode wird die Farbe der Figur bestimmt. Wenn
	 * die Figur von beiden Wegen abkommt, wird sie auf rot gesetzt. Wenn die Figur
	 * nur von einem Weg abkommt
	 * 
	 * @return
	 */
	public void FarbeFigur() {
		int hoehe = getDraw_height();
		sollRotSein_alt = sollRotSein;
		if ((cells[(draw_height/2 - 1) + (draw_height/2 - 1) * (draw_height) + 1].isVonFigurBetreten() == true ||
				cells[(draw_height/2 - 1) + (draw_height/2 - 1) * (draw_height) + draw_height].isVonFigurBetreten() == true) &&
				(i_Figur + j_Figur * draw_height == (draw_height/2) - 1 + (draw_height/2 - 1) * draw_height + 1 ||
				i_Figur + j_Figur * draw_height == (draw_height/2) - 1 + (draw_height/2 - 1) * draw_height + draw_height)){
			setSollRotSein(true);
		}
		if (differentpaths == 0) {
			if (getPath1()[getI_Figur() + getJ_Figur() * hoehe] == false
					&& getPath2()[getI_Figur() + getJ_Figur() * hoehe] == false) {
				setSollRotSein(true);
			} else if (getPath1()[getI_Figur() + getJ_Figur() * hoehe] == true
					&& getPath2()[getI_Figur() + getJ_Figur() * hoehe] == false) {
				differentpaths = 1;
			} else if (getPath1()[getI_Figur() + getJ_Figur() * hoehe] == false
					&& getPath2()[getI_Figur() + getJ_Figur() * hoehe] == true) {
				differentpaths = 2;
			}
		} else if (differentpaths == 1) {
			if (getPath1()[getI_Figur() + getJ_Figur() * hoehe] == false) {
				setSollRotSein(true);
			}
		} else if (differentpaths == 2) {
			if (getPath2()[getI_Figur() + getJ_Figur() * hoehe] == false) {
				setSollRotSein(true);
			}
		}
	}


	public void figurMalen(Graphics g) {
		// "+ square_size/4" macht es dann mittig
		g.fillRect(i_Figur * square_size + margin + square_size / 4, j_Figur * square_size + margin + square_size / 4,
				square_size / 2, square_size / 2);

	}

	public void betreteneFelderKennzeichnen(Graphics g) {
		for (int i = 0; i < draw_height; i++) {
			for (int j = 0; j < draw_height; j++) {
				// Falls Zelle betreten wurde, male ein Oval
				if (cells[i + j * draw_height].isVonFigurBetreten()) {
					g.fillOval(i * square_size + margin + square_size / 2, j * square_size + margin + square_size / 2,
							square_size / 4, square_size / 4);
				}
			}
		}
	}

	public void besuchteFelderClear() {
		for (int i = 0; i < draw_height; i++) {
			for (int j = 0; j < draw_height; j++) {
				cells[i + j * draw_height].setVonFigurBetreten(false);
			}
		}
	}

	public boolean istZielfeld() {
		if (i_Figur == -1 && j_Figur == 0 || i_Figur == -1 && j_Figur == draw_height - 1
				|| i_Figur == draw_height && j_Figur == 0 || i_Figur == draw_height && j_Figur == draw_height - 1) {
			return true;

		}
		return false;

	}

	/*
	 * Needed to mirror the maze
	 */
	public Cell[] getCells() {
		return cells;
	}

	/*
	 * Needed to mirror the maze
	 */
	public void setCells(Cell[] cells) {
		this.cells = cells;
		draw_height = draw_height * 2;
	}

	public int getI_Figur() {
		return i_Figur;
	}

	public void setI_Figur(int i_Figur) {
		this.i_Figur = i_Figur;
	}

	public int getJ_Figur() {
		return j_Figur;
	}

	public void setJ_Figur(int j_Figur) {
		this.j_Figur = j_Figur;
	}

	public int getMargin() {
		return margin;
	}

	public void setMargin(int margin) {
		this.margin = margin;
	}

	public int getNorth_border() {
		return north_border;
	}

	public void setNorth_border(int north_border) {
		this.north_border = north_border;
	}

	public int getEast_border() {
		return east_border;
	}

	public void setEast_border(int east_border) {
		this.east_border = east_border;
	}

	public int getSouth_border() {
		return south_border;
	}

	public void setSouth_border(int south_border) {
		this.south_border = south_border;
	}

	public int getWest_border() {
		return west_border;
	}

	public void setWest_border(int west_border) {
		this.west_border = west_border;
	}

	public int getSquare_size() {
		return square_size;
	}

	public void setSquare_size(int square_size) {
		this.square_size = square_size;
	}

	public int getDraw_height() {
		return draw_height;
	}

	public void setDraw_height(int draw_height) {
		this.draw_height = draw_height;
	}

	public int getI_Figur_alt() {
		return i_Figur_alt;
	}

	public void setI_Figur_alt(int i_Figur_alt) {
		this.i_Figur_alt = i_Figur_alt;
	}

	public int getJ_Figur_alt() {
		return j_Figur_alt;
	}

	public void setJ_Figur_alt(int j_Figur_alt) {
		this.j_Figur_alt = j_Figur_alt;
	}

	public boolean isIstRot() {
		return sollRotSein;
	}

	public void setIstRot(boolean sollRotSein) {
		this.sollRotSein = sollRotSein;
	}

	public boolean isSollRotSein() {
		return sollRotSein;
	}

	public void setSollRotSein(boolean sollRotSein) {
		this.sollRotSein = sollRotSein;
	}

	public boolean isSchonGespiegelt() {
		return schonGespiegelt;
	}

	public void setSchonGespiegelt(boolean schonGespiegelt) {
		this.schonGespiegelt = schonGespiegelt;
	}

	public boolean isFigurUnbewegt() {
		return figurUnbewegt;
	}

	public void setFigurUnbewegt(boolean figurUnbewegt) {
		this.figurUnbewegt = figurUnbewegt;
	}

	public int getDifferentpaths() {
		return differentpaths;
	}

	public void setDifferentpaths(int differentpaths) {
		this.differentpaths = differentpaths;
	}

	// berechnet Länge des kürzesten Weges
	public int getPathLength(boolean[] path) {
		int count = 0;
		for (int i = 0; i < path.length; i++) {
			if (path[i] == true) {
				count++;
			}
		}
		return count;
	}

	public boolean[] getPath1() {
		return path1;
	}

	// gibt true oder false zurück jenachdem ob i auf dem weg liegt
	public boolean getPath1Value(int i) {
		return path1[i];
	}

	public void setPath1(boolean[] path) {
		this.path1 = path;
	}

	// gibt true oder false zurück jenachdem ob i auf dem weg liegt
	public boolean getPath2Value(int i) {
		return path2[i];
	}

	public boolean[] getPath2() {
		return path2;
	}

	public void setPath2(boolean[] path) {
		this.path2 = path;

	}
	
	public boolean isSollRotSein_alt() {
		return sollRotSein_alt;
	}

	public void setSollRotSein_alt(boolean sollRotSein_alt) {
		this.sollRotSein_alt = sollRotSein_alt;
	}
}