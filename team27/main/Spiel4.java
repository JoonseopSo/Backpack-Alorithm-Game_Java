package team27.main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import spiel4Klassen.Burg;
import spiel4Klassen.Buttons;
import spiel4Klassen.CenterCelectionOpt;
import spiel4Klassen.CenterCelectionOpt2;
import spiel4Klassen.CenterCelectionPlayer;
import spiel4Klassen.DoerferErstellen;
import spiel4Klassen.Dorf;
import spiel4Klassen.Spielbereich;
import spiel4Klassen.Startbildschirm;

public class Spiel4  extends JPanel implements MouseListener, MouseMotionListener{
	private Burg[] burgen;
	private Dorf[] doerfer = new Dorf[100];
	private DoerferErstellen erstelleDoerfer;
	private Spielbereich game;
	private Buttons buttons;
	private CardLayout cl;
	private JPanel buttonPanel;
	private JPanel panelcont;
	private boolean grabbedBurg = false;
	private int current = -1;
	private int opt;
	private boolean[] optDoerfer;
	
	public Spiel4(CardLayout cl, JPanel buttonPanel, JPanel panelcont, int anzahlDoerfer, 
			int anzahlBurgen, String schwere, boolean firstTime, int highscore, Buttons buttons){
		this.cl = cl;
		this.buttonPanel = buttonPanel;
		this.panelcont = panelcont;
		burgen = new Burg[anzahlBurgen];
		for(int i = 0; i<anzahlBurgen;i++) {
			burgen[i] = new Burg();
		}
		JPanel spiele = new JPanel();
		erstelleDoerfer = new DoerferErstellen();
		BorderLayout border = new BorderLayout();
		this.setLayout(border);
		doerfer = erstelleDoerfer.erstelleDoerfer(anzahlDoerfer);
		CenterCelectionOpt2 algo = new CenterCelectionOpt2(doerfer, anzahlBurgen, schwere);
		opt = algo.getOpt();
		optDoerfer = algo.getOptDoerfer();
		Startbildschirm start = new Startbildschirm();
		CardLayout cardLayout = new CardLayout();
//		buttons = new Buttons(cl, buttonPanel, panelcont, doerfer, burgen, cardLayout, spiele, schwere, firstTime, this, highscore);
		
		this.buttons = buttons;
		buttons.setCardLayout(cardLayout);
		buttons.setDoerfer(doerfer);
		buttons.setBurgen(burgen);
		buttons.setCardLayout(cardLayout);
		buttons.setSpiele(spiele);
		buttons.setSchwere(schwere);;
		buttons.setFirstTime(firstTime);
		buttons.setAkspiel(this);
		buttons.setDeinscore(highscore);
		game = new Spielbereich(doerfer, burgen, buttons);
		game.addMouseListener(this);
		game.addMouseMotionListener(this);
		buttons.setGame(game);
		spiele.setLayout(cardLayout);
		spiele.add(start, "start");
		spiele.add(game, "game");
		if (firstTime == false) {
			cardLayout.show(spiele, "game");
		}
		add(spiele, BorderLayout.CENTER);
		game.setOptDoerfer(optDoerfer);
		buttons.setOpt(opt);
		
		buttons.setBackground(Color.red);
		add(buttons,BorderLayout.EAST);
		repaint();
		validate();
	}
	
	public Spiel4(CardLayout cl, JPanel buttonPanel, JPanel panelcont, int anzahlDoerfer, 
			int anzahlBurgen, String schwere, boolean firstTime, int highscore){
		this.cl = cl;
		this.buttonPanel = buttonPanel;
		this.panelcont = panelcont;
		burgen = new Burg[anzahlBurgen];
		for(int i = 0; i<anzahlBurgen;i++) {
			burgen[i] = new Burg();
		}
		JPanel spiele = new JPanel();
		erstelleDoerfer = new DoerferErstellen();
		BorderLayout border = new BorderLayout();
		this.setLayout(border);
		doerfer = erstelleDoerfer.erstelleDoerfer(anzahlDoerfer);
		CenterCelectionOpt2 algo = new CenterCelectionOpt2(doerfer, anzahlBurgen, schwere);
		opt = algo.getOpt();
		optDoerfer = algo.getOptDoerfer();
		Startbildschirm start = new Startbildschirm();
		CardLayout cardLayout = new CardLayout();
		buttons = new Buttons(cl, buttonPanel, panelcont, doerfer, burgen, cardLayout, spiele, schwere, firstTime, this, highscore);
		game = new Spielbereich(doerfer, burgen, buttons);
		game.addMouseListener(this);
		game.addMouseMotionListener(this);
		buttons.setGame(game);
		spiele.setLayout(cardLayout);
		spiele.add(start, "start");
		spiele.add(game, "game");
		if (firstTime == false) {
			cardLayout.show(spiele, "game");
		}
		add(spiele, BorderLayout.CENTER);
		game.setOptDoerfer(optDoerfer);
		
		buttons.setBackground(Color.red);
		add(buttons,BorderLayout.EAST);
		repaint();
		validate();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		for (int i = 0; i < doerfer.length; i++) {
			if(e.getX() >= doerfer[i].getDorfX() && 
				e.getX() <= doerfer[i].getDorfX() + 25 && 
				e.getY() >= doerfer[i].getDorfY() && 
				e.getY() <= doerfer[i].getDorfY() + 25 &&
				doerfer[i].isExistiert() == true
				&& buttons.isSpielLaueft() == true) {
					CenterCelectionPlayer algo = new CenterCelectionPlayer(doerfer, i);
					int[] disttemp = algo.getMaxdistance();
					int minDistance = Integer.MAX_VALUE;
					for (int k = 0; k < doerfer.length; k++) {
						for(int j = 0; j < burgen.length;j++) {
							if (burgen[j].getPlaced() == k) {
								if (disttemp[k] < minDistance) {
									minDistance = disttemp[k];
									game.setNextBurg(j);
								}
							}
						}
					}
			}
		}
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if (buttons.isSpielLaueft() == true) {
			for (int i = 0; i < doerfer.length; i++) {	
				for (int j = 0; j < burgen.length; j++) {
					if (burgen[j].getPlaced() == i) {
						doerfer[i].setGotBurg(true);
					}
					else {
						doerfer[i].setGotBurg(false);
					}
				}
				if (grabbedBurg == true && e.getX() >= doerfer[i].getDorfX() && 
						e.getX() <= doerfer[i].getDorfX() + 25 && 
						e.getY() >= doerfer[i].getDorfY() && 
						e.getY() <= doerfer[i].getDorfY() + 25 &&
						doerfer[i].isExistiert() == true &&
						doerfer[i].isGotBurg() == false) {
					burgen[current].setBurgX(doerfer[i].getDorfX() + 6);
					burgen[current].setBurgY(doerfer[i].getDorfY() + 6);
					burgen[current].setAltX(burgen[current].getBurgX());
					burgen[current].setAltY(burgen[current].getBurgY());
					burgen[current].setPlaced(i);
					game.drawBurgen(game.getGraphics());
					grabbedBurg = false;
					current = -1;
					game.setNextBurg(-1);
					repaint();
				}
				else if (current != -1){
					burgen[current].setBurgX(burgen[current].getAltX());
					burgen[current].setBurgY(burgen[current].getAltY());
					repaint();
				}
			}
			if (grabbedBurg == true && e.getX() >= 4*(game.getWidth()/5)) {
				burgen[current].setBurgX(burgen[current].getStartX());
				burgen[current].setBurgY(burgen[current].getStartY());
				burgen[current].setAltX(burgen[current].getBurgX());
				burgen[current].setAltY(burgen[current].getBurgY());
				burgen[current].setGotMoved(false);
				grabbedBurg = false;
				burgen[current].setPlaced(-1);
				current = -1;
				game.setNextBurg(-1);
				repaint();
			}
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
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if(buttons.isSpielLaueft() == true) {
			for (int i = 0; i < burgen.length; i++) {
				if (grabbedBurg == false || current == i) {
					if( e.getX() >= burgen[i].getBurgX() -15 && e.getX() <= burgen[i].getBurgX() + 27 && 
							e.getY() >= burgen[i].getBurgY() -15 && e.getY() <= burgen[i].getBurgY() + 27) {
						burgen[i].setBurgX(e.getX()-6);
						burgen[i].setBurgY(e.getY()-6);
						game.drawBurgen(game.getGraphics());
						burgen[i].setPlaced(-1);
						burgen[i].setGotMoved(true);
						grabbedBurg = true;
						current = i;
						repaint();
						validate();
					}
				}
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public int getOpt() {
		return opt;
	}

	public void setOpt(int opt) {
		this.opt = opt;
	}

	public boolean[] getOptDoerfer() {
		return optDoerfer;
	}

	public void setOptDoerfer(boolean[] optDoerfer) {
		this.optDoerfer = optDoerfer;
	}
	
}
