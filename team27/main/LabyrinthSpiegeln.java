package team27.main;

public class LabyrinthSpiegeln {
	
	/**
	 * Autor: Jonte Jakob
	 * 
	 * Modifiziert das eingegebene Array, sodass die Einträge genau an die Stellen kommen, an denen die Labyrinthfelder sind, wenn man das eingegebene 
	 * Labyrinth spiegeln und rotieren würde. Dies beinhaltet die Zellen und die beiden Wege, die identisch sind, wenn es nur einen
	 * kürzesten Weg gibt und ansonsten verschieden.
	 * @param labyrinth_temp ist das vorher generierte Array, welches das Ausgangslabyrinth darstellt
	 * @return labyrinth. Dies ist das Array was zeilenweise das Labyrinth darstellt, also die ersten x Einträge repräsentieren
	 * die erste Zeile des Labyrinths. Die zweiten x Einträge die zweite Zeile und so weiter.
	 */
	public void mirrorLabyrinth(Maze maze) {
		Cell[] labyrinth = new Cell[maze.getCells().length * 4]; 			//erstelle neues Array, um die gespiegelten Teile zuz speichern
		for (int n = 0 ; n < Math.sqrt(labyrinth.length) ; n++){				//Gehe eine Zeile des großen Labyrinths ab
			for (int i = (int) (Math.sqrt(maze.getCells().length)*n) ; i < Math.sqrt(maze.getCells().length) + Math.sqrt(maze.getCells().length)*n; i ++) { 		//Gehe eine halbe Zeile des großen Labyrinths ab
					if ((n * Math.sqrt(labyrinth.length)) + i - Math.sqrt(maze.getCells().length) * n < labyrinth.length / 2) {  		// befülle nur die obere Hälfte des Labyrinths
							labyrinth[(int) ((n * Math.sqrt(labyrinth.length)) + i - Math.sqrt(maze.getCells().length) * n)] = new Cell();  //von vorne auffüllen
							int value_border0 = maze.getCells()[i%maze.getCells().length].borders[0];		//speichere die Wände zwischen, damit es keine doppelten Zeiger gibt
							int value_border1 = maze.getCells()[i%maze.getCells().length].borders[1];
							int value_border2 = maze.getCells()[i%maze.getCells().length].borders[2];
							int value_border3 = maze.getCells()[i%maze.getCells().length].borders[3];
							int value_border4 = maze.getCells()[i%maze.getCells().length].borders[0];
							int value_border5 = maze.getCells()[i%maze.getCells().length].borders[1];
							int value_border6 = maze.getCells()[i%maze.getCells().length].borders[2];
							int value_border7 = maze.getCells()[i%maze.getCells().length].borders[3];
							labyrinth[(int) ((n * Math.sqrt(labyrinth.length)) + i - Math.sqrt(maze.getCells().length) * n)].borders[0] = value_border0;  // Wände speichern
							labyrinth[(int) ((n * Math.sqrt(labyrinth.length)) + i - Math.sqrt(maze.getCells().length) * n)].borders[1] = value_border1;
							labyrinth[(int) ((n * Math.sqrt(labyrinth.length)) + i - Math.sqrt(maze.getCells().length) * n)].borders[2] = value_border2;
							labyrinth[(int) ((n * Math.sqrt(labyrinth.length)) + i - Math.sqrt(maze.getCells().length) * n)].borders[3] = value_border3;
							labyrinth[(int) (((n + 1) * Math.sqrt(labyrinth.length)) - (i + 1 - Math.sqrt(maze.getCells().length)*n))] = new Cell(); //von hinten auffüllen
							labyrinth[(int) (((n + 1) * Math.sqrt(labyrinth.length)) - (i + 1 - Math.sqrt(maze.getCells().length)*n))].borders[0] = value_border4; 	// Wände speichern
							labyrinth[(int) (((n + 1) * Math.sqrt(labyrinth.length)) - (i + 1 - Math.sqrt(maze.getCells().length)*n))].borders[1] = value_border5;
							labyrinth[(int) (((n + 1) * Math.sqrt(labyrinth.length)) - (i + 1 - Math.sqrt(maze.getCells().length)*n))].borders[2] = value_border6;
							labyrinth[(int) (((n + 1) * Math.sqrt(labyrinth.length)) - (i + 1 - Math.sqrt(maze.getCells().length)*n))].borders[3] = value_border7;
				}
			}
		}
		
		for (int k = 0 ; k < labyrinth.length / 2 ; k++) {								//untere Hälfte vom Labyrinth auffüllen
					labyrinth[labyrinth.length - (k+1)] = new Cell();	
					int value_border0 = labyrinth[k].borders[0];
					int value_border1 = labyrinth[k].borders[1];
					int value_border2 = labyrinth[k].borders[2];
					int value_border3 = labyrinth[k].borders[3];
					labyrinth[labyrinth.length - (k+1)].borders[0] = value_border0;
					labyrinth[labyrinth.length - (k+1)].borders[1] = value_border1;
					labyrinth[labyrinth.length - (k+1)].borders[2] = value_border2;
					labyrinth[labyrinth.length - (k+1)].borders[3] = value_border3;
		}
		
		for (int i = (int) Math.sqrt(maze.getCells().length) ; i < labyrinth.length  ; i = (int) (i + 2*Math.sqrt(maze.getCells().length))) {		//rechte und linke Wand auf der rechten Hälfte tauschen
			for (int k = i ; k < (int)(i +  Math.sqrt(maze.getCells().length)) ; k++) {
				int links_temp = labyrinth[k].borders[3];
				int rechts_temp = labyrinth[k].borders[1];
				labyrinth[k].borders[3] = rechts_temp;
				labyrinth[k].borders[1] = links_temp;
			}
		}
		
		for (int i = labyrinth.length / 2 ; i < labyrinth.length  ; i++) {		//obere und untere Wand auf der unteren Hälfte tauschen
			int oben_temp = labyrinth[i].borders[0];
			int unten_temp = labyrinth[i].borders[2];
			labyrinth[i].borders[2] = oben_temp;
			labyrinth[i].borders[0] = unten_temp;
		}
		
		boolean[] path1 = new boolean[maze.getPath1().length * 4];
		for (int n = 0 ; n < Math.sqrt(path1.length) ; n++){				//Gehe eine Zeile des großen Labyrinths ab
			for (int i = (int) (Math.sqrt(maze.getCells().length)*n) ; i < Math.sqrt(maze.getPath1().length) + Math.sqrt(maze.getCells().length)*n; i ++) { 		//Gehe eine halbe Zeile des großen Labyrinths ab
					if ((n * Math.sqrt(path1.length)) + i - Math.sqrt(maze.getPath1().length) * n < path1.length / 2) {  		// befülle nur die obere Hälfte des Labyrinths
							boolean pathtemp1 = maze.getPath1Value(i%maze.getPath1().length);
							boolean pathtemp2 = maze.getPath1Value(i%maze.getPath1().length);
							path1[(int) ((n * Math.sqrt(labyrinth.length)) + i - Math.sqrt(maze.getCells().length) * n)] = pathtemp1;  //von vorne auffüllen
							path1[(int) (((n + 1) * Math.sqrt(labyrinth.length)) - (i + 1 - Math.sqrt(maze.getPath1().length)*n))] = pathtemp2; //von hinten auffüllen
				}
			}
		}
		
		for (int k = 0 ; k < path1.length / 2 ; k++) {								//untere Hälfte vom Labyrinth auffüllen
				boolean pathtemp3 = path1[k];	
				path1[path1.length - (k+1)] = pathtemp3;
		}
		
		boolean[] path2 = new boolean[maze.getPath2().length * 4];
		for (int n = 0 ; n < Math.sqrt(path2.length) ; n++){				//Gehe eine Zeile des großen Labyrinths ab
			for (int i = (int) (Math.sqrt(maze.getCells().length)*n) ; i < Math.sqrt(maze.getPath2().length) + Math.sqrt(maze.getCells().length)*n; i ++) { 		//Gehe eine halbe Zeile des großen Labyrinths ab
					if ((n * Math.sqrt(path2.length)) + i - Math.sqrt(maze.getPath2().length) * n < path2.length / 2) {  		// befülle nur die obere Hälfte des Labyrinths
							boolean pathtemp1 = maze.getPath2Value(i%maze.getPath2().length);
							boolean pathtemp2 = maze.getPath2Value(i%maze.getPath2().length);
							path2[(int) ((n * Math.sqrt(labyrinth.length)) + i - Math.sqrt(maze.getCells().length) * n)] = pathtemp1;  //von vorne auffüllen
							path2[(int) (((n + 1) * Math.sqrt(labyrinth.length)) - (i + 1 - Math.sqrt(maze.getPath2().length)*n))] = pathtemp2; //von hinten auffüllen
				}
			}
		}
		
		for (int k = 0 ; k < path1.length / 2 ; k++) {								//untere Hälfte vom Labyrinth auffüllen
				boolean pathtemp3 = path2[k];	
				path2[path2.length - (k+1)] = pathtemp3;
		}
		
		
		maze.setCells(labyrinth);
		maze.setPath1(path1);
		maze.setPath2(path2);
		
		
	}

}
