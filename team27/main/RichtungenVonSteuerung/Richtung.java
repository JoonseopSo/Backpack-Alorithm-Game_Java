package team27.main.RichtungenVonSteuerung;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import team27.main.Cell;
import team27.main.Maze;
import team27.main.Start;

public class Richtung extends AbstractAction implements ActionListener {

	private Maze m;
	private Start panelVonM;
	private String gewuenschteRichtung;
	private JButton einsZurueck;

	/**
	 * Erstellt eine Action, die die Figur des Labyrinths in die gewuenschte
	 * Richtung bewegt, sofern sie ausgefuehrt wird.
	 * 
	 * @param m                   Das maze der Figur
	 * @param panelVonM           Das panel von dem Maze
	 * @param gewuenschteRichtung Die Richtung, in die die Figur bewegt werden soll,
	 *                            wenn diese Action ausgefuehrt wird.
	 */
	public Richtung(Maze m, Start panelVonM, String gewuenschteRichtung, JButton einsZurueck) {
		this.m = m;
		this.panelVonM = panelVonM;
		this.gewuenschteRichtung = gewuenschteRichtung;
		this.einsZurueck = einsZurueck;

	}

	/**
	 * Je nach gewuenschter Richtung wird bei Ausfuehrunng die Figur in die
	 * entsprechende Richtung bewegt.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		Cell[] zellen = m.getCells();

		// Koordinaten der Figur
		int i = m.getI_Figur();
		int j = m.getJ_Figur();

		int i_alt = m.getI_Figur_alt();
		int j_alt = m.getJ_Figur_alt();

		int hoehe = m.getDraw_height();

		if (m.istZielfeld()) {
			if (i == -1 && j == 0 || i == -1 && j == m.getDraw_height() - 1) {
				if (gewuenschteRichtung == "rechts") {
					if (i_alt != i || j_alt != j) {
						m.setI_Figur_alt(i);
						m.setJ_Figur_alt(j);
					}
					m.setI_Figur(i + 1);

					m.setFigurUnbewegt(false);
					einsZurueck.setEnabled(true);
					panelVonM.repaint();
				}
			}
			if (i == hoehe && j == 0 || i == hoehe && j == hoehe - 1) {
				if (gewuenschteRichtung == "links") {
					if (i_alt != i || j_alt != j) {
						m.setI_Figur_alt(i);
						m.setJ_Figur_alt(j);
					}
					m.setI_Figur(i - 1);

					m.setFigurUnbewegt(false);
					einsZurueck.setEnabled(true);
					panelVonM.repaint();
				}
			}

		} else {

			// Alle Waende, die um die aktuelle Position der Figur herum sind.

			int[] waende = zellen[i + j * hoehe].getBorders();

			// Falls W oder Pfeiltaste oben gedrueckt wird
			if (gewuenschteRichtung == "oben") {
				int obereWand = waende[0];

				// Prüfe, ob keine Wand im Weg ist
				if (obereWand == 0) {

					// Alte Felder nur aktualisieren, falls alt ungleich neu
					// (sonst bleibt alt = neu für immer)
					if (i_alt != i || j_alt != j) {
						m.setI_Figur_alt(i);
						m.setJ_Figur_alt(j);
					}
					// Koordinaten auf neues Feld setzen
					m.setJ_Figur(j - 1);

					m.setFigurUnbewegt(false);
					einsZurueck.setEnabled(true);
					m.FarbeFigur();
					// Besuchtes Feld markieren
					zellen[i + j * hoehe].setVonFigurBetreten(true);
					panelVonM.repaint();
				}

			}

			// Analog
			if (gewuenschteRichtung == "rechts") {
				int rechteWand = waende[1];

				if (rechteWand == 0) {

					if (i_alt != i || j_alt != j) {
						m.setI_Figur_alt(i);
						m.setJ_Figur_alt(j);
					}

					m.setI_Figur(i + 1);

					m.setFigurUnbewegt(false);
					einsZurueck.setEnabled(true);
					m.FarbeFigur();
					zellen[i + j * hoehe].setVonFigurBetreten(true);
					panelVonM.repaint();
				}
				if (rechteWand == -2) {
					if ((i == hoehe - 1 && j == hoehe - 1) || (i == hoehe - 1 && j == 0)) {

						m.setI_Figur_alt(i);
						m.setJ_Figur_alt(j);
						m.setI_Figur(i + 1);

						zellen[i + j * hoehe].setVonFigurBetreten(true);
						panelVonM.repaint();
					}
				}
			}

			if (gewuenschteRichtung == "unten") {
				int untereWand = waende[2];

				if (untereWand == 0) {

					if (i_alt != i || j_alt != j) {
						m.setJ_Figur_alt(j);
						m.setI_Figur_alt(i);
					}

					m.setJ_Figur(j + 1);

					m.setFigurUnbewegt(false);
					einsZurueck.setEnabled(true);
					m.FarbeFigur();
					zellen[i + j * hoehe].setVonFigurBetreten(true);
					panelVonM.repaint();
				}
			}

			if (gewuenschteRichtung == "links") {
				int linkeWand = waende[3];

				if (linkeWand == 0) {

					if (i_alt != i || j_alt != j) {
						m.setI_Figur_alt(i);
						m.setJ_Figur_alt(j);
						;
					}

					m.setI_Figur(i - 1);

					m.setFigurUnbewegt(false);
					einsZurueck.setEnabled(true);
					m.FarbeFigur();
					zellen[i + j * hoehe].setVonFigurBetreten(true);
					panelVonM.repaint();
				}
				if (linkeWand == -2) {
					if ((i == 0 && j == 0) || (i == 0 && j == hoehe - 1)) {

						m.setI_Figur_alt(i);
						m.setJ_Figur_alt(j);

						m.setI_Figur(i - 1);

						m.setFigurUnbewegt(false);
						einsZurueck.setEnabled(true);

						zellen[i + j * hoehe].setVonFigurBetreten(true);
						panelVonM.repaint();
					}
					if (linkeWand == -2) {
						if ((i == 0 && j == 0) || (i == 0 && j == hoehe - 1)) {

							m.setI_Figur_alt(i);
							m.setJ_Figur_alt(j);
							m.setI_Figur(i - 1);

							zellen[i + j * hoehe].setVonFigurBetreten(true);
							panelVonM.repaint();
						}
					}
				}
			}
		}

	}

	public Maze getM() {
		return m;
	}

	public void setM(Maze m) {
		this.m = m;
	}

	public Start getPanelVonM() {
		return panelVonM;
	}

	public void setPanelVonM(Start panelVonM) {
		this.panelVonM = panelVonM;
	}

	public String getGewuenschteRichtung() {
		return gewuenschteRichtung;
	}

	public void setGewuenschteRichtung(String gewuenschteRichtung) {
		this.gewuenschteRichtung = gewuenschteRichtung;
	}

}
