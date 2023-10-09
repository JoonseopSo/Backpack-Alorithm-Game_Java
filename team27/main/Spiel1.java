package team27.main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import spiel1Klassen.Buttonpanelsp1;
import spiel1Klassen.Itemfelder;
import spiel1Klassen.Items;
import spiel1Klassen.Koffer;

import spiel1Klassen.Startpanel;

public class Spiel1 extends JPanel implements MouseListener, MouseMotionListener, ActionListener {

	private Itemfelder itemfelder;
	private Items[] items;
	private Koffer koffer;
	private JLabel kofferrest;
	private static BufferedImage img;
	private Buttonpanelsp1 bp1;
	private CardLayout cl;
	private JPanel buttonPanel;
	private JPanel panelcont;
	private JPanel spiel1card;
	private boolean grabbed;
	private boolean gamerunning=false;
	private boolean trainingspiel=false;
	private boolean mittelspiel=false;
	private boolean schwerspiel=false;
	private int summewertausbp1;
	private int punkteausbp1;
	private int itemnummer = -1;
	private int k;
	private int summegewicht;
	private int summewert;
	private int restgewicht;
	int mausX;
	int mausY;
	int optwert;

	public Spiel1(CardLayout cl, JPanel buttonPanel, JPanel panelcont, int k) {
		setBackground(new Color(204, 229, 255));
		this.cl = cl;
		this.buttonPanel = buttonPanel;
		this.panelcont = panelcont;
		this.k = k;
		Startpanel startpanel = new Startpanel();
		CardLayout card = new CardLayout();
		koffer = new Koffer();
		items = new Items[k];

		for (int i = 0; i < k; i++) {
			items[i] = new Items();
		}

		spiel1card = new JPanel();
		bp1 = new Buttonpanelsp1(this.cl, buttonPanel, panelcont, card, spiel1card, itemfelder);
		itemfelder = new Itemfelder(items, koffer, k, this, bp1);
		itemfelder.addMouseListener(this);
		itemfelder.addMouseMotionListener(this);

		setRestgewicht(itemfelder.getGewichtsschranke());

		spiel1card.setLayout(card);
		spiel1card.add(startpanel, "start");
		spiel1card.add(itemfelder, "spiel1");
		card.show(spiel1card, "start");

		BorderLayout border = new BorderLayout();
		this.setLayout(border);
	//	bp1 = new Buttonpanelsp1(this.cl, buttonPanel, panelcont, card, spiel1card, itemfelder);
		add(bp1, BorderLayout.EAST);
		add(spiel1card, BorderLayout.CENTER);
		validate();
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (gamerunning == true) {
			for (int j = 0; j < k; j++) {
				if (grabbed == false || itemnummer == j) {
					if (items[j].isGelegt() == false) {
						if (e.getX() >= items[j].getItemx()
								&& e.getX() <= items[j].getItemx() + itemfelder.getFeld_w() - itemfelder.getMargin()
								&& e.getY() >= items[j].getItemy()
								&& e.getY() <= items[j].getItemy() + itemfelder.getFeld_h() - itemfelder.getMargin()) {
							items[j].setItemx(e.getX() - itemfelder.getFeld_w()/6);
							items[j].setItemy(e.getY() - itemfelder.getFeld_h()/6);
							items[j].setBewegt(true);
							grabbed = true;
							itemnummer = j;
							items[j].setDragged(false);
							items[j].setItemaufmaus(true);

							itemfelder.repaint();

						}
					}
				}
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (gamerunning == true) {

			if (e.getX() >= koffer.getKofferx_links() && e.getX() <= koffer.getKofferx_rechts()
					&& e.getY() >= koffer.getKoffery_oben() && e.getY() <= koffer.getKoffery_unten()) {
				if (koffer.isKofferclicked() == false) {

					koffer.setKofferclicked(true);

					repaint();

				} else if (koffer.isKofferclicked() == true) {

					koffer.setKofferclicked(false);
					repaint();
				}

			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (gamerunning == true) {

			for (int j = 0; j < k; j++) {
				if (items[j].isItemaufmaus() == true) {
					if (koffer.isKofferauffeld() == true) {
						if (grabbed == true || itemnummer == j) {

							if (e.getX() >= koffer.getKofferx_links() && e.getX() <= koffer.getKofferx_rechts()
									&& e.getY() >= koffer.getKoffery_oben() && e.getY() <= koffer.getKoffery_unten()) {

								summegewicht += items[itemnummer].getGewicht();
								summewert += items[itemnummer].getWert();
								setRestgewicht(getRestgewicht() - items[itemnummer].getGewicht());
								items[j].setItemaufmaus(false);

								items[j].setDragged(true);
								grabbed = false;
								items[j].setGelegt(true);

								repaint();

								break;

							} else {
								items[j].setItemx(items[j].getA_x());
								items[j].setItemy(items[j].getA_y());
								items[j].setItemaufmaus(false);
								grabbed = false;
								repaint();
							}

						}
					} else {
						items[j].setItemx(items[j].getA_x());
						items[j].setItemy(items[j].getA_y());
						items[j].setItemaufmaus(false);
						grabbed = false;
						repaint();
					}
				}else {
					grabbed=false;
					items[j].setItemaufmaus(false);
				}
			}
			repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public int getSummegewicht() {
		return summegewicht;
	}

	public void setSummegewicht(int summegewicht) {
		this.summegewicht = summegewicht;
	}

	public int getSummewert() {
		return summewert;
	}

	public void setSummewert(int summewert) {
		this.summewert = summewert;
	}

	public int getRestgewicht() {
		return restgewicht;
	}

	public void setRestgewicht(int restgewicht) {
		this.restgewicht = restgewicht;
	}

	public Itemfelder getItemfelder() {
		return itemfelder;
	}

	public void setItemfelder(Itemfelder itemfelder) {
		this.itemfelder = itemfelder;
	}

	public boolean isGamerunning() {
		return gamerunning;
	}

	public void setGamerunning(boolean gamerunning) {
		this.gamerunning = gamerunning;
	}

	public boolean isTrainingspiel() {
		return trainingspiel;
	}

	public void setTrainingspiel(boolean trainingspiel) {
		this.trainingspiel = trainingspiel;
	}

	public boolean isMittelspiel() {
		return mittelspiel;
	}

	public void setMittelspiel(boolean mittelspiel) {
		this.mittelspiel = mittelspiel;
	}

	public boolean isSchwerspiel() {
		return schwerspiel;
	}

	public void setSchwerspiel(boolean schwerspiel) {
		this.schwerspiel = schwerspiel;
	}

	public int getSummewertausbp1() {
		return summewertausbp1;
	}

	public void setSummewertausbp1(int summewertausbp1) {
		this.summewertausbp1 = summewertausbp1;
	}

	public int getPunkteausbp1() {
		return punkteausbp1;
	}

	public void setPunkteausbp1(int punkteausbp1) {
		this.punkteausbp1 = punkteausbp1;
	}

	public Buttonpanelsp1 getBp1() {
		return bp1;
	}

	public void setBp1(Buttonpanelsp1 bp1) {
		this.bp1 = bp1;
	}

}
