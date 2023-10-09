package spiel4Klassen;
//Jonte Jakob 7380571
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import team27.main.Spiel4;
import team27.main.RichtungenVonSteuerung.Richtung;

public class Spielbereich extends JPanel{
	private Burg[] burgen;
	private Dorf[] doerfer;
	private Thron thron;
	private String richtung;
	private Buttons buttons;
	private int nextBurg = 100;
	private int thronOn = 100;
	private boolean thronMalen = true;
	private boolean verloren = false;
	private boolean gewonnen = false;
	private boolean anzeigen = false;
	private Spiel4 spiel4;
	private boolean[] optDoerfer;
	private Scanner scan;

	public Spielbereich(Dorf[] doerfer, Burg[] burgen, Buttons buttons) {
		this.burgen = burgen;
		this.doerfer = doerfer;
		this.buttons = buttons;
		this.spiel4 = spiel4;
		thron = new Thron();
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "nachRechts");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "nachRechts");
		getActionMap().put("nachRechts", rechts);
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "nachUnten");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "nachUnten");
		getActionMap().put("nachUnten", unten);
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "nachLinks");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "nachLinks");
		getActionMap().put("nachLinks", links);
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "nachOben");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "nachOben");
		getActionMap().put("nachOben", oben);
	}
	
	public void drawDoerfer(Graphics g, int frameHeight, int frameWidth) {
		for (int i = 0 ; i < 100 ; i++) {
			doerfer[i].setDorfX(i%10);
			doerfer[i].setDorfY((i-doerfer[i].getDorfX())/10);
			doerfer[i].setDorfX(doerfer[i].getDorfX()*frameWidth/10);
			doerfer[i].setDorfY(doerfer[i].getDorfY()*frameHeight/10);
			if (doerfer[i].isExistiert() == true){
				g.setColor(Color.black);
				g.drawRect(doerfer[i].getDorfX()-1, doerfer[i].getDorfY()-1, 32, 32);
				if (thronOn == i) {
					g.setColor(Color.yellow);
					g.fillRect(doerfer[i].getDorfX(), doerfer[i].getDorfY(), 30, 30);
				}
				else{
					g.setColor(Color.red);
					g.fillRect(doerfer[i].getDorfX(), doerfer[i].getDorfY(), 30, 30);
				}
				if (anzeigen == true && optDoerfer[i] == true) {
					g.setColor(Color.BLUE);
					g.fillRect(doerfer[i].getDorfX()-1, doerfer[i].getDorfY()-1, 35, 35);
				}
			}
		}
	}
	
	public void drawThron(Graphics g) {
		if (thronMalen == true) {	
			if (thron.isFirstTime() == true) {
				g.setColor(Color.yellow);
				thron.setThronX(4*(getWidth()/5) + (getWidth()/10));
				thron.setThronY(350);
				g.fillRect(thron.getThronX(), thron.getThronY(), 16, 16);
			}
			else {
				g.setColor(Color.yellow);
				g.fillRect(thron.getThronX(), thron.getThronY(), 16, 16);
				
			}
		}
	}
	
	public void drawBurgen(Graphics g) {
		for( int i = 0 ; i < burgen.length; i++) {
			if (nextBurg == i) {
				g.setColor(Color.green);
			}
			else{
				g.setColor(Color.black);
			}
			burgen[i].setStartX(4*(getWidth()/5) + (getWidth()/10));
			burgen[i].setStartY(5 + i * 50);
			if(burgen[i].isGotMoved() == false) {
				burgen[i].setBurgX(4*(getWidth()/5) + (getWidth()/10));
				burgen[i].setBurgY(5 + i * 50);
				burgen[i].setAltX(burgen[i].getStartX());
				burgen[i].setAltY(burgen[i].getStartY());
				g.fillRect(burgen[i].getBurgX(), burgen[i].getBurgY(), 16 ,16);
			}
			else if(burgen[i].getPlaced() != -1){
				g.fillRect(doerfer[burgen[i].getPlaced()].getDorfX()+6, doerfer[burgen[i].getPlaced()].getDorfY()+6, 16 ,16);
			}
			else {
				g.fillRect(burgen[i].getBurgX(), burgen[i].getBurgY(), 16 ,16);
			}
		}
	}
	Action rechts = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (buttons.isSpielLaueft() == true) {	
				if (getWidth() - 50 > thron.getThronX()) {
					thron.setThronX(thron.getThronX() +30);
				}
				thron.setFirstTime(false);
				for (int i = 0; i < doerfer.length; i++) {
					if(thron.getThronX() >= doerfer[i].getDorfX() -10 && 
						thron.getThronX() <= doerfer[i].getDorfX() + 35 && 
						thron.getThronY() >= doerfer[i].getDorfY() -10 && 
						thron.getThronY() <= doerfer[i].getDorfY() + 35 &&
						doerfer[i].isExistiert() == true) {
						boolean schonVerschoben = false;
						for (int j = 0; j < burgen.length; j++) {	
							if (burgen[j].getPlaced() == i) {
								buttons.getTimer().cancel();
								thronMalen = false;
								thronOn = i;
								int opt = 0;
								int[] maxdistance = new int[doerfer.length];
								for (int k = 0; k < doerfer.length; k++) {
									maxdistance[k] = Integer.MAX_VALUE;
								}
								for (int k = 0; k < burgen.length; k++) {
									if (burgen[k].getPlaced() != -1) {
										CenterCelectionPlayer algo = new CenterCelectionPlayer(doerfer, burgen[k].getPlaced());
										int[] disttemp = algo.getMaxdistance();
										for(int l=0; l < doerfer.length;l++) {
											if (disttemp[l] < maxdistance[l]) {
												maxdistance[l] = disttemp[l];
											}
										}
									}
								}
								for (int k = 0; k < doerfer.length;k++) {
									if( doerfer[k].isExistiert() == true) {
										if (opt < maxdistance[k]) {
											opt = maxdistance[k];
										}
									}
								}
									if (buttons.getSchwere() == "Training" && opt <= buttons.getAkspiel().getOpt() * 2) {
										buttons.setDeinscore(buttons.getDeinscore() + 6);
										buttons.getScore().setText("Aktueller Score: " + buttons.getDeinscore());
										buttons.setSpielLaueft(false);
										buttons.getLoesung().setText("Nächstes Level");
										buttons.getPause().setEnabled(false);
										gewonnen = true;
										repaint();
										Timer timer = new Timer();
										timer.schedule(new TimerTask() {
					
											@Override
											public void run() {
												// TODO Auto-generated method stub
												gewonnen = false;
												drawGewonnen(getGraphics(), "Gewonnen", 200,200);
												repaint();
											}
											
										}, 2000);
									}
									else if (buttons.getSchwere() == "Mittel" && opt <= buttons.getOptMittel()) {
										buttons.setDeinscore(buttons.getDeinscore() + 12);
										buttons.getScore().setText("Aktueller Score: " + buttons.getDeinscore());
										buttons.setSpielLaueft(false);
										buttons.getLoesung().setText("Nächstes Level");
										buttons.getPause().setEnabled(false);
										gewonnen = true;
										repaint();
										Timer timer = new Timer();
										timer.schedule(new TimerTask() {
					
											@Override
											public void run() {
												// TODO Auto-generated method stub
												gewonnen = false;
												drawGewonnen(getGraphics(), "Gewonnen", 200,200);
												repaint();
											}
											
										}, 2000);
									}
	
									else if (buttons.getSchwere() == "Schwer" && opt <= buttons.getOptSchwer()) {
										buttons.setDeinscore(buttons.getDeinscore() + 18);
										buttons.getScore().setText("Aktueller Score: " + buttons.getDeinscore());
										buttons.setSpielLaueft(false);
										buttons.getLoesung().setText("Nächstes Level");
										buttons.getPause().setEnabled(false);
										gewonnen = true;
										repaint();
										Timer timer = new Timer();
										timer.schedule(new TimerTask() {
					
											@Override
											public void run() {
												// TODO Auto-generated method stub
												gewonnen = false;
												drawGewonnen(getGraphics(), "Gewonnen", 200,200);
												repaint();
											}
											
										}, 2000);
										
									}
	
									else {
										verloren = true;
										anzeigen = true;
										buttons.setSpielLaueft(false);
										buttons.getPause().setEnabled(false);
										buttons.getPause().setText("Pause");
										buttons.getEnde().setText("Neues Spiel");
										buttons.getLoesung().setEnabled(false);
										Timer timer = new Timer();
										timer.schedule(new TimerTask() {
	
											@Override
											public void run() {
												// TODO Auto-generated method stub
												verloren = false;
												drawVerloren(getGraphics(), "Verloren", 200,200);
												repaint();
											}
											
										}, 2000);
										buttons.highscore();
									}
							}
							else {
								if (schonVerschoben == false ) {
									schonVerschoben = true;
									thron.setThronX(thron.getThronX() -30);
								}
							}
						}
					}
				}
				repaint();
			}
		}
	};
	
	Action links = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {	
			if (buttons.isSpielLaueft() == true) {
				if (thron.getThronX() > 30) {
					thron.setThronX(thron.getThronX() -30);
				}
				thron.setFirstTime(false);
				for (int i = 0; i < doerfer.length; i++) {
					if(thron.getThronX() >= doerfer[i].getDorfX() -10 && 
						thron.getThronX() <= doerfer[i].getDorfX() + 35 && 
						thron.getThronY() >= doerfer[i].getDorfY() -10 && 
						thron.getThronY() <= doerfer[i].getDorfY() + 35 &&
						doerfer[i].isExistiert() == true) {
						boolean schonVerschoben = false;
						for (int j = 0; j < burgen.length; j++) {	
							if (burgen[j].getPlaced() == i) {
								buttons.getTimer().cancel();
								thronMalen = false;
								thronOn = i;
								int opt = 0;
								int[] maxdistance = new int[doerfer.length];
								for (int k = 0; k < doerfer.length; k++) {
									maxdistance[k] = Integer.MAX_VALUE;
								}
								for (int k = 0; k < burgen.length; k++) {
									if (burgen[k].getPlaced() != -1) {
										CenterCelectionPlayer algo = new CenterCelectionPlayer(doerfer, burgen[k].getPlaced());
										int[] disttemp = algo.getMaxdistance();
										for(int l=0; l < doerfer.length;l++) {
											if (disttemp[l] < maxdistance[l]) {
												maxdistance[l] = disttemp[l];
											}
										}
									}
								}
								for (int k = 0; k < doerfer.length;k++) {
									if( doerfer[k].isExistiert() == true) {
										if (opt < maxdistance[k]) {
											opt = maxdistance[k];
										}
									}
								}
								if (buttons.getSchwere() == "Training" && opt <= buttons.getAkspiel().getOpt() * 2) {
									buttons.setDeinscore(buttons.getDeinscore() + 6);
									buttons.getScore().setText("Aktueller Score: " + buttons.getDeinscore());
									buttons.setSpielLaueft(false);
									buttons.getLoesung().setText("Nächstes Level");
									buttons.getPause().setEnabled(false);
									gewonnen = true;
									repaint();
									Timer timer = new Timer();
									timer.schedule(new TimerTask() {
				
										@Override
										public void run() {
											// TODO Auto-generated method stub
											gewonnen = false;
											drawGewonnen(getGraphics(), "Gewonnen", 200,200);
											repaint();
										}
										
									}, 2000);
								}
								else if (buttons.getSchwere() == "Mittel" && opt <= buttons.getOptMittel()) {
									buttons.setDeinscore(buttons.getDeinscore() + 12);
									buttons.getScore().setText("Aktueller Score: " + buttons.getDeinscore());
									buttons.setSpielLaueft(false);
									buttons.getLoesung().setText("Nächstes Level");
									buttons.getPause().setEnabled(false);
									gewonnen = true;
									repaint();
									Timer timer = new Timer();
									timer.schedule(new TimerTask() {
				
										@Override
										public void run() {
											// TODO Auto-generated method stub
											gewonnen = false;
											drawGewonnen(getGraphics(), "Gewonnen", 200,200);
											repaint();
										}
										
									}, 2000);
								}

								else if (buttons.getSchwere() == "Schwer" && opt <= buttons.getOptSchwer()) {
									buttons.setDeinscore(buttons.getDeinscore() + 18);
									buttons.getScore().setText("Aktueller Score: " + buttons.getDeinscore());
									buttons.setSpielLaueft(false);
									buttons.getLoesung().setText("Nächstes Level");
									buttons.getPause().setEnabled(false);
									gewonnen = true;
									repaint();
									Timer timer = new Timer();
									timer.schedule(new TimerTask() {
				
										@Override
										public void run() {
											// TODO Auto-generated method stub
											gewonnen = false;
											drawGewonnen(getGraphics(), "Gewonnen", 200,200);
											repaint();
										}
										
									}, 2000);
								}

								else {
									verloren = true;
									anzeigen = true;
									buttons.setSpielLaueft(false);
									buttons.getPause().setEnabled(false);
									buttons.getPause().setText("Pause");
									buttons.getEnde().setText("Neues Spiel");
									buttons.getLoesung().setEnabled(false);
									Timer timer = new Timer();
									timer.schedule(new TimerTask() {

										@Override
										public void run() {
											// TODO Auto-generated method stub
											verloren = false;
											drawVerloren(getGraphics(), "Verloren", 200,200);
											repaint();
										}
										
									}, 2000);
									buttons.highscore();
								}
							}
							else {
								if (schonVerschoben == false ) {
									schonVerschoben = true;
									thron.setThronX(thron.getThronX() +30);
								}
							}
						}
					}
				}
				repaint();
			}
		}
	};
	
	Action oben = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (buttons.isSpielLaueft() == true) {	
				if (thron.getThronY() > 30) {
					thron.setThronY(thron.getThronY() -30);
				}
				thron.setFirstTime(false);
				for (int i = 0; i < doerfer.length; i++) {
					if(thron.getThronX() >= doerfer[i].getDorfX() -10 && 
						thron.getThronX() <= doerfer[i].getDorfX() + 35 && 
						thron.getThronY() >= doerfer[i].getDorfY() -10 && 
						thron.getThronY() <= doerfer[i].getDorfY() + 35 &&
						doerfer[i].isExistiert() == true) {
						boolean schonVerschoben = false;
						for (int j = 0; j < burgen.length; j++) {	
							if (burgen[j].getPlaced() == i) {
								buttons.getTimer().cancel();
								thronMalen = false;
								thronOn = i;
								int opt = 0;
								int[] maxdistance = new int[doerfer.length];
								for (int k = 0; k < doerfer.length; k++) {
									maxdistance[k] = Integer.MAX_VALUE;
								}
								for (int k = 0; k < burgen.length; k++) {
									if (burgen[k].getPlaced() != -1) {
										CenterCelectionPlayer algo = new CenterCelectionPlayer(doerfer, burgen[k].getPlaced());
										int[] disttemp = algo.getMaxdistance();
										for(int l=0; l < doerfer.length;l++) {
											if (disttemp[l] < maxdistance[l]) {
												maxdistance[l] = disttemp[l];
											}
										}
									}
								}
								for (int k = 0; k < doerfer.length;k++) {
									if( doerfer[k].isExistiert() == true) {
										if (opt < maxdistance[k]) {
											opt = maxdistance[k];
										}
									}
								}
								if (buttons.getSchwere() == "Training" && opt <= buttons.getAkspiel().getOpt() * 2) {
									buttons.setDeinscore(buttons.getDeinscore() + 6);
									buttons.getScore().setText("Aktueller Score: " + buttons.getDeinscore());
									buttons.setSpielLaueft(false);
									buttons.getLoesung().setText("Nächstes Level");
									buttons.getPause().setEnabled(false);
									gewonnen = true;
									repaint();
									Timer timer = new Timer();
									timer.schedule(new TimerTask() {
				
										@Override
										public void run() {
											// TODO Auto-generated method stub
											gewonnen = false;
											drawGewonnen(getGraphics(), "Gewonnen", 200,200);
											repaint();
										}
										
									}, 2000);
								}
								else if (buttons.getSchwere() == "Mittel" && opt <= buttons.getOptMittel()) {
									buttons.setDeinscore(buttons.getDeinscore() + 12);
									buttons.getScore().setText("Aktueller Score: " + buttons.getDeinscore());
									buttons.setSpielLaueft(false);
									buttons.getLoesung().setText("Nächstes Level");
									buttons.getPause().setEnabled(false);
									gewonnen = true;
									repaint();
									Timer timer = new Timer();
									timer.schedule(new TimerTask() {
				
										@Override
										public void run() {
											// TODO Auto-generated method stub
											gewonnen = false;
											drawGewonnen(getGraphics(), "Gewonnen", 200,200);
											repaint();
										}
										
									}, 2000);
								}

								else if (buttons.getSchwere() == "Schwer" && opt <= buttons.getOptSchwer()) {
									buttons.setDeinscore(buttons.getDeinscore() + 18);
									buttons.getScore().setText("Aktueller Score: " + buttons.getDeinscore());
									buttons.setSpielLaueft(false);
									buttons.getLoesung().setText("Nächstes Level");
									buttons.getPause().setEnabled(false);
									gewonnen = true;
									repaint();
									Timer timer = new Timer();
									timer.schedule(new TimerTask() {
				
										@Override
										public void run() {
											// TODO Auto-generated method stub
											gewonnen = false;
											drawGewonnen(getGraphics(), "Gewonnen", 200,200);
											repaint();
										}
										
									}, 2000);
								}

								else {
									verloren = true;
									anzeigen = true;
									buttons.setSpielLaueft(false);
									buttons.getPause().setEnabled(false);
									buttons.getPause().setText("Pause");
									buttons.getEnde().setText("Neues Spiel");
									buttons.getLoesung().setEnabled(false);
									Timer timer = new Timer();
									timer.schedule(new TimerTask() {

										@Override
										public void run() {
											// TODO Auto-generated method stub
											verloren = false;
											drawVerloren(getGraphics(), "Verloren", 200,200);
											repaint();
										}
										
									}, 2000);
									buttons.highscore();
								}
							}
							else {
								if (schonVerschoben == false ) {
									schonVerschoben = true;
									thron.setThronY(thron.getThronY() +30);
								}
							}
						}
					}
				}
				repaint();
			}
		}
	};
	
	Action unten = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (buttons.isSpielLaueft() == true) {	
				if (thron.getThronY() < getHeight() -50) {
					thron.setThronY(thron.getThronY() +30);
				}
				thron.setFirstTime(false);
				for (int i = 0; i < doerfer.length; i++) {
					if(thron.getThronX() >= doerfer[i].getDorfX() -10 && 
						thron.getThronX() <= doerfer[i].getDorfX() + 35 && 
						thron.getThronY() >= doerfer[i].getDorfY() -10 && 
						thron.getThronY() <= doerfer[i].getDorfY() + 35 &&
						doerfer[i].isExistiert() == true) {
						boolean schonVerschoben = false;
						for (int j = 0; j < burgen.length; j++) {	
							if (burgen[j].getPlaced() == i) {
								buttons.getTimer().cancel();
								thronMalen = false;
								thronOn = i;
								int opt = 0;
								int[] maxdistance = new int[doerfer.length];
								for (int k = 0; k < doerfer.length; k++) {
									maxdistance[k] = Integer.MAX_VALUE;
								}
								for (int k = 0; k < burgen.length; k++) {
									if (burgen[k].getPlaced() != -1) {
										CenterCelectionPlayer algo = new CenterCelectionPlayer(doerfer, burgen[k].getPlaced());
										int[] disttemp = algo.getMaxdistance();
										for(int l=0; l < doerfer.length;l++) {
											if (disttemp[l] < maxdistance[l]) {
												maxdistance[l] = disttemp[l];
											}
										}
									}
								}
								for (int k = 0; k < doerfer.length;k++) {
									if( doerfer[k].isExistiert() == true) {
										if (opt < maxdistance[k]) {
											opt = maxdistance[k];
										}
									}
								}
								if (buttons.getSchwere() == "Training" && opt <= buttons.getAkspiel().getOpt() * 2) {
									buttons.setDeinscore(buttons.getDeinscore() + 6);
									buttons.getScore().setText("Aktueller Score: " + buttons.getDeinscore());
									buttons.setSpielLaueft(false);
									buttons.getLoesung().setText("Nächstes Level");
									buttons.getPause().setEnabled(false);
									gewonnen = true;
									repaint();
									Timer timer = new Timer();
									timer.schedule(new TimerTask() {
				
										@Override
										public void run() {
											// TODO Auto-generated method stub
											gewonnen = false;
											drawGewonnen(getGraphics(), "Gewonnen", 200,200);
											repaint();
										}
										
									}, 2000);
								}
								else if (buttons.getSchwere() == "Mittel" && opt <= buttons.getOptMittel()) {
									buttons.setDeinscore(buttons.getDeinscore() + 12);
									buttons.getScore().setText("Aktueller Score: " + buttons.getDeinscore());
									buttons.setSpielLaueft(false);
									buttons.getLoesung().setText("Nächstes Level");
									buttons.getPause().setEnabled(false);
									gewonnen = true;
									repaint();
									Timer timer = new Timer();
									timer.schedule(new TimerTask() {
				
										@Override
										public void run() {
											// TODO Auto-generated method stub
											gewonnen = false;
											drawGewonnen(getGraphics(), "Gewonnen", 200,200);
											repaint();
										}
										
									}, 2000);
								}

								else if (buttons.getSchwere() == "Schwer" && opt <= buttons.getOptSchwer()) {
									buttons.setDeinscore(buttons.getDeinscore() + 18);
									buttons.getScore().setText("Aktueller Score: " + buttons.getDeinscore());
									buttons.setSpielLaueft(false);
									buttons.getLoesung().setText("Nächstes Level");
									buttons.getPause().setEnabled(false);
									gewonnen = true;
									repaint();
									Timer timer = new Timer();
									timer.schedule(new TimerTask() {
				
										@Override
										public void run() {
											// TODO Auto-generated method stub
											gewonnen = false;
											drawGewonnen(getGraphics(), "Gewonnen", 200,200);
											repaint();
										}
										
									}, 2000);
								}

								else {
									verloren = true;
									anzeigen = true;
									buttons.setSpielLaueft(false);
									buttons.getPause().setEnabled(false);
									buttons.getPause().setText("Pause");
									buttons.getEnde().setText("Neues Spiel");
									buttons.getLoesung().setEnabled(false);
									Timer timer = new Timer();
									timer.schedule(new TimerTask() {

										@Override
										public void run() {
											// TODO Auto-generated method stub
											verloren = false;
											drawVerloren(getGraphics(), "Verloren", 200,200);
											repaint();
										}
										
									}, 2000);
									buttons.highscore();
								}
							}
							else {
								if (schonVerschoben == false ) {
									schonVerschoben = true;
									thron.setThronY(thron.getThronY() -30);
								}
							}
						}
					}
				}
				repaint();
			}
		}
	};
	
	public void drawVerloren(Graphics g, String verloren, int x, int y) {
		if (this.verloren == true) {
			g.setColor(Color.black);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
			g.drawString(verloren, x, y);
		}
		repaint();
	}
	
	public void drawGewonnen (Graphics g, String gewonnen, int x, int y) {
		if (this.gewonnen == true) {
			g.setColor(Color.black);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
			g.drawString(gewonnen, x, y);
		}
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.blue);
		g.drawRect(4*(getWidth()/5), 0,  getWidth()/5, getHeight());
		drawDoerfer(g, getHeight(), 4*(getWidth()/5));
		drawBurgen(g);
		drawThron(g);
		if (verloren == true) {
			drawVerloren(g, "Verloren", 200, 200);
		}
		if (gewonnen == true) {
			drawGewonnen(g, "Gewonnen", 200, 200);
		}
		revalidate();
	}

	public boolean isGewonnen() {
		return gewonnen;
	}

	public void setGewonnen(boolean gewonnen) {
		this.gewonnen = gewonnen;
	}

	public int getNextBurg() {
		return nextBurg;
	}

	public void setNextBurg(int nextBurg) {
		this.nextBurg = nextBurg;
	}

	public boolean isVerloren() {
		return verloren;
	}

	public void setVerloren(boolean verloren) {
		this.verloren = verloren;
	}

	public boolean[] getOptDoerfer() {
		return optDoerfer;
	}

	public void setOptDoerfer(boolean[] optDoerfer) {
		this.optDoerfer = optDoerfer;
	}

	public boolean isAnzeigen() {
		return anzeigen;
	}

	public void setAnzeigen(boolean anzeigen) {
		this.anzeigen = anzeigen;
	}
}
