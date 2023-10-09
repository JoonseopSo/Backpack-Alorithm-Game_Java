package team27.main;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;

import spiel1Klassen.Buttonpanelsp1;

public class Buttonaction extends AbstractAction {

	private Maze m;
	private Start panelVonM;
	private int Spielnummer;
	private JButton Spiel;
	private JPanel panelcont;
	private JPanel buttonPanel;
	private CardLayout cl;
	private Scanner sc;
	

	public Buttonaction(Maze m, Start panelVonM, int Spielnummer, JButton Spiel, JPanel panelcont,
			JPanel buttonPanel, CardLayout cl) {
		this.m = m;
		this.panelVonM = panelVonM;
		this.Spielnummer = Spielnummer;
		this.Spiel = Spiel;
		this.panelcont = panelcont;
		this.buttonPanel = buttonPanel;
		this.cl = cl;
		


		if (Spielnummer != 5) {
			Spiel.addActionListener(this);
		}
	}
	
	public void action() {
		if (Spielnummer == 1) {

			cl.show(panelcont, "1");
			Spiel1 sp1 = new Spiel1(cl,buttonPanel,panelcont,5);
			panelcont.add(sp1,"1");
			cl.show(panelcont, "1");
			buttonPanel.setVisible(false);

		}
		if (Spielnummer == 2) {

			cl.show(panelcont, "2");
			buttonPanel.setVisible(false);

		}
		if (Spielnummer == 3) {

			cl.show(panelcont, "3");
			buttonPanel.setVisible(false);

		}
		if (Spielnummer == 4) {

			Spiel4 sp4 = new Spiel4(cl, buttonPanel, panelcont, 6 ,3, "Training", true, 0);
			panelcont.add(sp4,"4");
			cl.show(panelcont, "4");
			buttonPanel.setVisible(false);

		}
	}
	
	
	public Start getPanelVonM() {
		return panelVonM;
	}

	public void setPanelVonM(Start panelVonM) {
		this.panelVonM = panelVonM;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (Spielnummer == 1) {

			cl.show(panelcont, "1");
			Spiel1 sp1 = new Spiel1(cl,buttonPanel,panelcont,5);
			panelcont.add(sp1,"1");
			cl.show(panelcont, "1");
			buttonPanel.setVisible(false);

		}
		if (Spielnummer == 2) {

			cl.show(panelcont, "2");
			buttonPanel.setVisible(false);

		}
		if (Spielnummer == 3) {

			cl.show(panelcont, "3");
			buttonPanel.setVisible(false);

		}
		if (Spielnummer == 4) {

			Spiel4 sp4 = new Spiel4(cl, buttonPanel, panelcont, 6 ,3, "Training", true, 0);
			panelcont.add(sp4,"4");
			cl.show(panelcont, "4");
			buttonPanel.setVisible(false);

		}
		if (Spielnummer == 5 && !m.isSollRotSein()) {

			int i = m.getI_Figur();
			int j = m.getJ_Figur();
			int hoehe = m.getDraw_height();

			if (i == hoehe && j == hoehe - 1) {
				Spiel4 sp4 = new Spiel4(cl, buttonPanel, panelcont, 6, 3, "Training", true, 0);
				panelcont.add(sp4,"4");
				cl.show(panelcont, "4");
				buttonPanel.setVisible(false);

			}
			if (i == -1 && j == hoehe - 1) {

				cl.show(panelcont, "3");
				buttonPanel.setVisible(false);
			}
			if (i == hoehe && j == 0) {

				cl.show(panelcont, "2");
				buttonPanel.setVisible(false);
			}
			if (i == -1 && j == 0) {

				cl.show(panelcont, "1");
				buttonPanel.setVisible(false);
			}

		}

	}

	public Maze getM() {
		return m;
	}

	public void setM(Maze m) {
		this.m = m;
	}

}
