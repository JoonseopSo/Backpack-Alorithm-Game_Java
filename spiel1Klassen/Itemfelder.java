package spiel1Klassen;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import team27.main.Cell;
import team27.main.Spiel1;

public class Itemfelder extends JPanel implements MouseMotionListener {

	private Items[] items;
	private Koffer koffer;
	private Spiel1 spiel1;
	private Buttonpanelsp1 bp1;

	static BufferedImage img;

	private JLabel kofferrest;

	private int x_item;
	private int y_item;
	private int feld;
	private int feld_w;
	private int feld_h;
	private int grid_w;
	private int grid_h;

	private int margin = 60;
	int k;
	int random;
	ArrayList arr;
	int[] gewichtarray;
	int[] wertarray;
	int gewichtsschranke;
	int zielwerttraining;
	int zielwertmittel;
	int zielwertschwer;
	int optwert;
	private boolean spielverloren = false, spielgewonnen = false;
	Random rand;
	int l;
	private int j_koffer;
	private int i_koffer;

	int[][] felder = { { 1, 2, 3, 4 }, { 5, 0, 0, 6 }, { 7, 0, 0, 8 }, { 9, 10, 11, 12 } };
	int[][] grid = new int[32][40];

	public Itemfelder(Items[] items, Koffer koffer, int k, Spiel1 spiel1, Buttonpanelsp1 bp1) {
		setBackground(new Color(204, 229, 255));
		this.items = items;
		this.k = k;
		this.koffer = koffer;
		this.spiel1 = spiel1;
		this.bp1 = bp1;

		arr = new ArrayList();
		for (int i = 0; i < 12; i++) {
			arr.add(i + 1);
		}
		Collections.shuffle(arr);
		l = (int) ((Math.random() * (4)));

		j_koffer = 13;
		i_koffer = 13;
		wertarray(k);
		gewichtarray(k);
		gewichtsschranke();
		optwert = rucksackproblem(wertarray(k), gewichtarray(k), gewichtsschranke);

		zielwerttraining = (int) Math.floor(optwert * 0.5);
		zielwertmittel = (int) Math.floor(optwert * 0.7);
		zielwertschwer = (int) Math.floor(optwert * 0.9);

		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "nachOben");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "nachOben");
		getActionMap().put("nachOben", oben);

		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "nachRechts");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "nachRechts");
		getActionMap().put("nachRechts", rechts);

		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "nachUnten");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "nachUnten");
		getActionMap().put("nachUnten", unten);

		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "nachLinks");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "nachLinks");
		getActionMap().put("nachLinks", links);

	}

	public int[] gewichtarray(int k) {
		gewichtarray = new int[k];
		rand = new Random();
		for (int i = 0; i < gewichtarray.length; i++) {
			gewichtarray[i] = (int) ((Math.random() * (6 - 1)) + 1);

		}

		return gewichtarray;
	}

	public int[] wertarray(int k) {
		wertarray = new int[k];
		rand = new Random();
		for (int i = 0; i < wertarray.length; i++) {
			wertarray[i] = (int) ((Math.random() * (70 - 1)) + 1);

		}

		return wertarray;
	}

	public int gewichtsschranke() {

		gewichtsschranke = (int) ((Math.random() * 21) + 4);

		return gewichtsschranke;

	}

	public int rucksackproblem(int wertarray[], int gewichtarray[], int gewichtsschranke) {

		int k = gewichtarray.length;

		int[][] matrix = new int[k + 1][gewichtsschranke + 1];

		for (int a = 0; a <= gewichtsschranke; a++) {
			matrix[0][a] = 0;
		}
		for (int b = 0; b <= k; b++) {
			matrix[b][0] = 0;
		}

		for (int i = 1; i <= k; i++) {

			for (int weight = 1; weight <= gewichtsschranke; weight++) {

				if (gewichtarray[i - 1] <= weight) {

					matrix[i][weight] = Math.max(wertarray[i - 1] + matrix[i - 1][weight - gewichtarray[i - 1]],
							matrix[i - 1][weight]);
				} else {

					matrix[i][weight] = matrix[i - 1][weight];
				}
			}

		}

		return matrix[k][gewichtsschranke];

	}

	public void drawItemfelder(Graphics g, int frameHeight, int frameWidth) {
		feld_w = frameWidth / 32 * 8;
		feld_h = (frameHeight - 30) / 32 * 8;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {

				if (felder[i][j] == 0) {

				} else {
					g.setColor(Color.blue);

					g.drawRect(0, 10, 4 * feld_w, 4 * feld_h);

				}

			}
		}

	}

	public void drawgridfeld(Graphics g, int frameHeight, int framewidth) {
		grid_w = framewidth / 32;
		grid_h = ((frameHeight - 30) / 32);

		for (int i = 0; i < 40; i++) {
			for (int j = 0; j < 32; j++) {

			}
		}

	}

	public void itemsmalen(Graphics g, int k) {

		int count = 0;

		for (int j = 0; j < k; j++) {
			random = (int) arr.get(j);

			for (int a = 0; a < 4; a++) {
				for (int b = 0; b < 4; b++) {
					if (felder[b][a] == random) {

						items[j].setS_x((a * feld_w) + feld_w / 3);
						items[j].setS_y((b * feld_h) + feld_h / 3);
						items[j].setGewicht(gewichtarray[count]);
						items[j].setWert(wertarray[count]);
						String wt = String.valueOf(items[j].getWert());
						String gw = String.valueOf(items[j].getGewicht());
						count = count + 1;

						if (items[j].isBewegt() == false) {
							items[j].setItemx((a * feld_w) + feld_w / 3);
							items[j].setItemy((b * feld_h) + feld_h / 3);
							items[j].setA_x(items[j].getS_x());
							items[j].setA_y(items[j].getS_y());
							g.setColor(new Color(209, 98, 0));
							g.fillRoundRect(items[j].getItemx(), items[j].getItemy(), feld_w / 3, feld_h / 3 + 10, 10,
									10);
							g.setColor(Color.black);
							g.drawString("Gewicht: " + gw, items[j].getItemx(), items[j].getItemy() + feld_h / 3 + 20);
							g.drawString("Wert: " + wt, items[j].getItemx(), items[j].getItemy() - 2);
						}

						else if (items[j].isDragged() == false) {

							g.setColor(new Color(209, 98, 0));
							g.fillRoundRect(items[j].getItemx(), items[j].getItemy(), feld_w / 3, feld_h / 3 + 10, 10,
									10);
							g.setColor(Color.black);
							g.drawString("Gewicht: " + gw, items[j].getItemx(), items[j].getItemy() + feld_h / 3 + 20);
							g.drawString("Wert: " + wt, items[j].getItemx(), items[j].getItemy() - 2);
						}

					}
				}

			}

		}

	}

	public void koffermalen(Graphics g) {

		String gewschrank = String.valueOf(gewichtsschranke);

		try {
			img = ImageIO.read(getClass().getResource("/resources/Koffer.jpg"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		koffer.setKofferx_links(grid_w * j_koffer);
		koffer.setKofferx_rechts(grid_w * (j_koffer + 6));
		koffer.setKoffery_oben(grid_h * i_koffer);
		koffer.setKoffery_unten(grid_h * (i_koffer + 6));
		g.setColor(Color.red);
		g.drawString("Maximal Gewicht :  " + gewschrank, 10, 10);
		g.drawImage(img, grid_w * j_koffer, grid_h * i_koffer, feld_w * 3 / 4, feld_h * 3 / 4, this);

	}

	public void ausgangmalen(Graphics g, int frameHeight, int frameWidth) {

		g.setColor(Color.black);
		g.fillRect(l * feld_w, 4 * feld_h + 10, feld_w, 50);
		g.setColor(Color.yellow);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 22));
		g.drawString("AUSGANG", l * feld_w, 4 * feld_h + 30);
	}

	public void restschranke(Graphics g) {
		if (koffer.isKofferclicked() == true) {
			String rest = String.valueOf(spiel1.getRestgewicht());
			if (spiel1.getRestgewicht() >= 0) {
				g.setColor(Color.yellow);
				g.drawString(rest, grid_w * j_koffer + feld_w * 3 / 9, grid_h * i_koffer + feld_h * 3 / 8);
			} else {
				g.setColor(Color.yellow);
				g.drawString("Überschritten!", grid_w * j_koffer, grid_h * i_koffer + feld_h * 3 / 8);

			}
		} else if (koffer.isKofferclicked() == false) {

		}
	}

	public void spielergebnis(Graphics g, String deinText1, String deinText2, String deinText3, String deinText4) {

		g.setColor(Color.red);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
		g.drawString(deinText1, 9 * grid_w, 14 * grid_h);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		g.drawString(deinText2, 9 * grid_w, 16 * grid_h);
		g.drawString(deinText3, 9 * grid_w, 18 * grid_h);
		g.drawString(deinText4, 9 * grid_w, 20 * grid_h);

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		drawItemfelder(g, getHeight(), getWidth());
		drawgridfeld(g, getHeight(), getWidth());
		itemsmalen(g, k);
		koffermalen(g);
		ausgangmalen(g, getHeight(), getWidth());
		restschranke(g);
		if (isSpielverloren() == true) {
			spielergebnis(g, "VERLOREN", "Mit Reskapazität:  " + spiel1.getRestgewicht(),
					"Optimale Lösung:  " + optwert, "Deine Lösung:  " + spiel1.getSummewert());
		}
		if (isSpielgewonnen() == true) {
			spielergebnis(g, "GEWONNEN", "Mit Reskapazität:  " + spiel1.getRestgewicht(),
					"Optimale Lösung:  " + optwert, "Deine Lösung:  " + spiel1.getSummewert());
		}
		repaint();

	}

	Action oben = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (spiel1.isGamerunning() == true) {
				if (i_koffer > 0) {
					i_koffer = i_koffer - 1;
					if (i_koffer <= 27) {
						koffer.setKofferauffeld(true);
					}
					repaint();
				}
			}
		}

	};
	Action unten = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (spiel1.isGamerunning() == true) {
				if (i_koffer < 27 || ((j_koffer >= l * 8) && (j_koffer <= (l * 8) + 2))) {
					i_koffer = i_koffer + 1;
					if (i_koffer > 27) {

						koffer.setKofferauffeld(false);
						if (i_koffer >= 32) {
							if (spiel1.isTrainingspiel() == true) {
								if (spiel1.getSummewert() >= zielwerttraining
										&& spiel1.getSummegewicht() <= spiel1.getItemfelder().getGewichtsschranke()) {
									spiel1.getItemfelder().setSpielgewonnen(true);
									spiel1.setGamerunning(false);
									repaint();

								} else {
									spiel1.getItemfelder().setSpielverloren(true);
									spiel1.setGamerunning(false);
									repaint();

								}
							} else if (spiel1.isMittelspiel() == true) {
								if (spiel1.getSummewert() >= zielwertmittel
										&& spiel1.getSummegewicht() <= spiel1.getItemfelder().getGewichtsschranke()) {

									spiel1.getItemfelder().setSpielgewonnen(true);
									spiel1.setGamerunning(false);
									repaint();

								} else {
									spiel1.getItemfelder().setSpielverloren(true);
									spiel1.setGamerunning(false);
									repaint();

								}
							} else if (spiel1.isSchwerspiel() == true) {
								if (spiel1.getSummewert() >= zielwertschwer
										&& spiel1.getSummegewicht() <= spiel1.getItemfelder().getGewichtsschranke()) {

									spiel1.getItemfelder().setSpielgewonnen(true);
									spiel1.setGamerunning(false);
									repaint();
								} else {
									spiel1.getItemfelder().setSpielverloren(true);
									spiel1.setGamerunning(false);
									repaint();
								}

							}
						}

					}
				}
			}
		}
	};
	Action links = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (spiel1.isGamerunning() == true) {
				if (i_koffer <= 27) {
					if (j_koffer > 0) {

						j_koffer = j_koffer - 1;

						repaint();
					}
				}
			}
		}
	};
	Action rechts = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (spiel1.isGamerunning() == true) {
				if (i_koffer <= 27) {
					if (j_koffer < 26) {
						j_koffer = j_koffer + 1;

						repaint();
					}
				}
			}
		}
	};

	public static BufferedImage getImg() {
		return img;
	}

	public static void setImg(BufferedImage img) {
		Itemfelder.img = img;
	}

	public int getFeld() {
		return feld;
	}

	public void setFeld(int feld) {
		this.feld = feld;
	}

	public int getZielwerttraining() {
		return zielwerttraining;
	}

	public void setZielwerttraining(int zielwerttraining) {
		this.zielwerttraining = zielwerttraining;
	}

	public int getZielwertmittel() {
		return zielwertmittel;
	}

	public void setZielwertmittel(int zielwertmittel) {
		this.zielwertmittel = zielwertmittel;
	}

	public int getZielwertschwer() {
		return zielwertschwer;
	}

	public void setZielwertschwer(int zielwertschwer) {
		this.zielwertschwer = zielwertschwer;
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public Items[] getItems() {
		return items;
	}

	public void setItems(Items[] items) {
		this.items = items;
	}

	public int getMargin() {
		return margin;
	}

	public void setMargin(int margin) {
		this.margin = margin;
	}

	public int getRandom() {
		return random;
	}

	public void setRandom(int random) {
		this.random = random;
	}

	public int getX_item() {
		return x_item;
	}

	public void setX_item(int x_item) {
		this.x_item = x_item;
	}

	public int getY_item() {
		return y_item;
	}

	public int getFeld_w() {
		return feld_w;
	}

	public void setFeld_w(int feld_w) {
		this.feld_w = feld_w;
	}

	public int getFeld_h() {
		return feld_h;
	}

	public void setFeld_h(int feld_h) {
		this.feld_h = feld_h;
	}

	public int getGewichtsschranke() {
		return gewichtsschranke;
	}

	public void setGewichtsschranke(int gewichtsschranke) {
		this.gewichtsschranke = gewichtsschranke;
	}

	public void setY_item(int y_item) {
		this.y_item = y_item;
	}

	public ArrayList getArr() {
		return arr;
	}

	public int getOptwert() {
		return optwert;
	}

	public void setOptwert(int optwert) {
		this.optwert = optwert;
	}

	public void setArr(ArrayList arr) {
		this.arr = arr;
	}

	public int[] getGewichtarray() {
		return gewichtarray;
	}

	public void setGewichtarray(int[] gewichtarray) {
		this.gewichtarray = gewichtarray;
	}

	public int getJ_koffer() {
		return j_koffer;
	}

	public void setJ_koffer(int j_koffer) {
		this.j_koffer = j_koffer;
	}

	public int getI_koffer() {
		return i_koffer;
	}

	public void setI_koffer(int i_koffer) {
		this.i_koffer = i_koffer;
	}

	public int[] getWertarray() {
		return wertarray;
	}

	public void setWertarray(int[] wertarray) {
		this.wertarray = wertarray;
	}

	public Random getRand() {
		return rand;
	}

	public void setRand(Random rand) {
		this.rand = rand;
	}

	public int getL() {
		return l;
	}

	public void setL(int l) {
		this.l = l;
	}

	public int[][] getFelder() {
		return felder;
	}

	public boolean isSpielverloren() {
		return spielverloren;
	}

	public void setSpielverloren(boolean spielverloren) {
		this.spielverloren = spielverloren;
	}

	public boolean isSpielgewonnen() {
		return spielgewonnen;
	}

	public void setSpielgewonnen(boolean spielgewonnen) {
		this.spielgewonnen = spielgewonnen;
	}

	public void setFelder(int[][] felder) {
		this.felder = felder;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}