package spiel2Klassen;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import team27.main.Spiel2;

public class RichtungenSpiel2 extends AbstractAction{

	private Transporter transporter;
	private String richtung;
	private Spiel2 panel;
	private int schrittweite;
	
	public RichtungenSpiel2(Transporter transporter, String richtung, Spiel2 panel) {
		this.transporter = transporter;
		this.richtung = richtung;
		this.panel = panel;
		
		// Schrittweite ist 10% der Panelbreite von Spiel2
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(panel.getPausiert() == 1 || panel.isVerloren()) {
			return;
		}
		schrittweite = (int) (panel.getSpielbereich().getWidth() * 0.05);
		int toleranzZurSeite = (int) (panel.getSpielbereich().getWidth() * 0.05);
		
		boolean ausgangLinks = false;
		boolean ausgangRechts = false;
		
		if(panel.getSpielbereich().getAusgangLR() < 0.5) {
			ausgangLinks = true;
		}
		
		if(panel.getSpielbereich().getAusgangLR() >= 0.5) {
			ausgangRechts = true;
		}
		
		// Position des Transporters. Bei Verschiebung des Transporters
		// aendert sich "transporter.verschiebung"
		int TposX = transporter.getPosX() + transporter.getVerschiebung();
		
		if(richtung == "links" && (TposX - schrittweite > toleranzZurSeite || ausgangLinks)) {
			transporter.setVerschiebung(transporter.getVerschiebung() - schrittweite);
			panel.repaint();
		}
		if(richtung == "rechts" && (TposX + transporter.getLaenge() + schrittweite < panel.getSpielbereich().getWidth() - toleranzZurSeite || ausgangRechts)) {
			transporter.setVerschiebung(transporter.getVerschiebung() + schrittweite);
			panel.repaint();
		}
		
		// Abgeben der Loesung
		if(ausgangLinks && TposX + transporter.getLaenge() <= 0 ) {
			System.out.println(panel.getSpielbereich().loesungPruefen());
			panel.loesungAbgeben();
		}
		
		if(ausgangRechts && TposX >= panel.getSpielbereich().getWidth()) {
			System.out.println(panel.getSpielbereich().loesungPruefen());
			panel.loesungAbgeben();
		}
		
	}
	
	

}
