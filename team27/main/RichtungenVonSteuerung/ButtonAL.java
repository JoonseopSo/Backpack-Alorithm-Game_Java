package team27.main.RichtungenVonSteuerung;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import team27.main.Maze;

public class ButtonAL implements ActionListener {

	private JButton einsZurueck;
	private JButton aufStartfeld;
	private Maze maze;
	private JPanel panelmaze;

	public ButtonAL(JButton einsZurueck, JButton aufStartfeld, Maze maze, JPanel panelmaze) {
		this.einsZurueck = einsZurueck;
		this.aufStartfeld = aufStartfeld;
		this.maze = maze;
		this.panelmaze = panelmaze;

		einsZurueck.setEnabled(false);

		einsZurueck.addActionListener(this);
		aufStartfeld.addActionListener(this);
	}

	public Maze getMaze() {
		return maze;
	}

	public void setMaze(Maze maze) {
		this.maze = maze;
	}

	public JButton getEinsZurueck() {
		return einsZurueck;
	}

	public void setEinsZurueck(JButton einsZurueck) {
		this.einsZurueck = einsZurueck;
	}

	public JButton getAufStartfeld() {
		return aufStartfeld;
	}

	public void setAufStartfeld(JButton aufStartfeld) {
		this.aufStartfeld = aufStartfeld;
	}

	/**
	 * Autor: Jonte Jakob Wenn der Zurueck-Button gedrückt wird, wird die Farbe
	 * wieder auf die Farbe des vorherigen Feldes gesetzt
	 */
	public void FigurStatusZurueck() {
		int hoehe = maze.getDraw_height();
		if (maze.getPath1()[maze.getI_Figur() + maze.getJ_Figur() * hoehe] == true
				&& maze.getPath2()[maze.getI_Figur() + maze.getJ_Figur() * hoehe] == true && maze.isSollRotSein_alt() == false) {
			maze.setSollRotSein(false);
			maze.setDifferentpaths(0);
		} else if (maze.getPath1()[maze.getI_Figur() + maze.getJ_Figur() * hoehe] == true
				&& maze.getPath2()[maze.getI_Figur() + maze.getJ_Figur() * hoehe] == false && maze.isSollRotSein_alt() == false) {
			maze.setSollRotSein(false);
			maze.setDifferentpaths(1);
		} else if (maze.getPath1()[maze.getI_Figur() + maze.getJ_Figur() * hoehe] == false
				&& maze.getPath2()[maze.getI_Figur() + maze.getJ_Figur() * hoehe] == true && maze.isSollRotSein_alt() == false) {
			maze.setSollRotSein(false);
			maze.setDifferentpaths(2);
		}
		maze.setSollRotSein_alt(maze.isSollRotSein());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == einsZurueck) {
			maze.setI_Figur(maze.getI_Figur_alt());
			maze.setJ_Figur(maze.getJ_Figur_alt());
			FigurStatusZurueck();
			int i = maze.getI_Figur();
			int j = maze.getJ_Figur();

			maze.getCells()[i + j * maze.getDraw_height()].setVonFigurBetreten(false);

			einsZurueck.setEnabled(false);

			panelmaze.repaint();

		}

		if (e.getSource() == aufStartfeld) {
			int i_start = maze.getDraw_height() / 2 - 1;
			int j_start = maze.getDraw_height() / 2 - 1;

			maze.setI_Figur(i_start);
			maze.setJ_Figur(j_start);
			maze.setDifferentpaths(0);

			maze.besuchteFelderClear();

			maze.setFigurUnbewegt(true);
			einsZurueck.setEnabled(false);
			maze.setSollRotSein(false);

			panelmaze.repaint();
			
		}

	}

	public JPanel getPanelmaze() {
		return panelmaze;
	}

	public void setPanelmaze(JPanel panelmaze) {
		this.panelmaze = panelmaze;
	}

}
