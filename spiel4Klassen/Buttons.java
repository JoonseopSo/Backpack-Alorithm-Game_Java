package spiel4Klassen;
//Jonte Jakob 7380571
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import team27.main.Maze;
import team27.main.Spiel4;
import team27.main.Start;

public class Buttons extends JPanel{
	
	private JLabel highscores;
	private JLabel first;
	private JLabel second;
	private JLabel third;
	private JButton zurueck;
	private JLabel score;
	private JButton reset;
	private JLabel level;
	private JLabel zeit;
	private JButton anleitung;
	private JButton start;
	private JButton pause;
	private JButton ende;
	private JButton loesung;
	private CardLayout cl;
	private JPanel buttonPanel;
	private JPanel panelcont;
	private JLabel optSolve;
	private int deinscore;
	private Burg[] burgen;
	private Dorf[] doerfer;
	private int[] maxdistance;
	private int opt;
	private Spielbereich game;
	private boolean spielLaueft = false;
	private boolean spielBeendet = false;
	private JComboBox schwierigkeit;
	private int anzahlDorf;
	private int anzahlBurg;
	private int difficulty;
	private CardLayout cardLayout;
	private JPanel spiele;
	private String schwere;
	private String schwere_alt;
	private Spiel4 akspiel;
	public int time;
	private Timer timer;
	private TimerTask timertask;
	private Buttons buttons;
	private boolean firstTime = true;
	private Maze maze;
	private File datei;
	private Scanner scan;
	private String[] bestenliste;
	private int aklevel = 0;
	private FileWriter writer;
	private boolean cancelClock = false;
	private int optMittel;
	private String name;
	private JButton speichern;
	private JTextField nameField;
	private JFrame myFrame;
	private int optSchwer;
	
	

	
	public Buttons(CardLayout cl, JPanel buttonPanel, JPanel panelcont,
			Dorf[] doerfer1, Burg[] burgen1, CardLayout cardLayout, JPanel spiele,
			String schwere1, boolean firstTime1, Spiel4 spiel4, int score_alt) {
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("P"), "Pause");
		getActionMap().put("Pause", pause2);
		deinscore = score_alt;
		akspiel = spiel4;
		buttons = this;
		this.spiele = spiele;
		this.cardLayout = cardLayout;
		this.burgen = burgen1;
		this.doerfer = doerfer1;
		this.cl = cl;
		this.buttonPanel = buttonPanel;
		this.panelcont = panelcont;
		opt = spiel4.getOpt();
		maxdistance = new int[doerfer.length];
		for (int i = 0; i < doerfer.length; i++) {
			maxdistance[i] = Integer.MAX_VALUE;
		}
		bestenliste = new String[6];
		this.schwere = schwere1;
		this.schwere_alt = schwere1;
		String[] auswahl = {"Training","Mittel","Schwer"};
		datei = new File("src/resources/HS_Spiel4.txt");
		scan = null; 
		
		try {
			scan = new Scanner(datei);
		} catch (FileNotFoundException e1) {
			System.out.println("File not found");
		}
		for (int i = 0; i < 6; i++) {
			if (scan.hasNextLine() == true) {
				bestenliste[i] = scan.nextLine();
				if (scan.hasNextLine()) {
					scan.nextLine();
				}
			}
		}
		scan.close();
		schwierigkeit = new JComboBox(auswahl);
		if (schwere == "Training") {
			schwierigkeit.setSelectedIndex(0);
		}
		else if (schwere == "Mittel") {
			schwierigkeit.setSelectedIndex(1);
		}
		else if (schwere == "Schwer") {
			schwierigkeit.setSelectedIndex(2);
		}
		highscores = new JLabel("Highscoreboard:");
		if (bestenliste[0] != null) {
			first = new JLabel("1. " + bestenliste[0] + " " + bestenliste[1]);
		}
		else {
			first = new JLabel("1. -  0");
		}
		if (bestenliste[2] != null) {
			second = new JLabel("2. " + bestenliste[2] + " " + bestenliste[3]);
		}
		else {
			second = new JLabel("2. -  0");
		}
		if (bestenliste[4] != null) {
			third = new JLabel("3. " + bestenliste[4] + " " + bestenliste[5]);
		}
		else {
			third = new JLabel("3. -  0");
		}
		
		zurueck = new JButton("Zum Labyrinth");
		score = new JLabel("Aktueller Score: " + deinscore);
		reset = new JButton("Reset Highscore");
		anleitung = new JButton("Anleitung");
		level = new JLabel("Level: " + aklevel);
		zeit = new JLabel("Timer: " );
		start = new JButton("Start");
		pause = new JButton("Pause");
		ende = new JButton("Spiel beenden");
		loesung = new JButton("Loesung abgeben");
		speichern = new JButton("Speichern");
		
		
		loesung.setEnabled(false);
		ende.setEnabled(false);
		pause.setEnabled(false);
		if (this.firstTime ==  true) {
			start.setEnabled(true);
			schwere = "Training";
			schwere_alt = "Training"; 
			optSolve = new JLabel("Zu erreichende Distanz: " );
			anzahlBurg = 3;
			anzahlDorf = 6;
			reset.setEnabled(true);
			firstTime = false;
		}
		GridLayout layout = new GridLayout(16,1);
		setLayout(layout);
		
		add(zurueck, layout);
		add(optSolve,layout);
		add(highscores,layout);
		add(first,layout);
		add(second,layout);
		add(third,layout);
		add(reset, layout);
		add(anleitung, layout);
		add(schwierigkeit, layout);
		add(score, layout);
		add(level, layout);
		add(zeit, layout);
		add(start, layout);
		add(pause, layout);
		add(ende, layout);
		add(loesung, layout);
		
		
		zurueck.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(panelcont, "mp");
				buttonPanel.setVisible(true);
				Start s = new Start(maze);
				s.mazeErzeugen(s);
			}
		});
			
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Spiel4 spiel = new Spiel4(cl,buttonPanel,panelcont, anzahlDorf, anzahlBurg, schwere, false, deinscore, buttons);
				panelcont.add(spiel, "4");
				cl.show(panelcont, "4");
				schwere_alt = schwere;
				spielLaueft = true;
				aklevel = 1;
				level.setText("Level: 1");
				cardLayout.show(spiele, "game");
				try {
					scan = new Scanner(datei);
				} catch (FileNotFoundException e1) {
					System.out.println("File not found");
				}
				for (int i = 0; i < 6; i++) {
					if (scan.hasNextLine() == true) {
						bestenliste[i] = scan.nextLine();
						if (scan.hasNextLine()){
							scan.nextLine();
						}
					}
				}
				scan.close();
				if (cancelClock == true) {
					timer.cancel();
				}
				deinscore = 0;
				score.setText("Aktueller Score: " + deinscore);
				zurueck.setEnabled(false);
				cancelClock = true;
				start.setEnabled(false);
				pause.setText("Pause");
				reset.setEnabled(false);
				loesung.setEnabled(true);
				ende.setEnabled(true);
				pause.setEnabled(true);
				schwierigkeit.setEnabled(false);
				if (schwere == "Training") {
					optSolve.setText("Zu erreichende Distanz: " + opt*2 + " ");
				}
				else if (schwere == "Mittel") {
					if (Math.floor(akspiel.getOpt()*1.5) == akspiel.getOpt()*1.5) {
						optMittel = (int) (akspiel.getOpt() * 1.5);
					}
					else {
						optMittel = (int) Math.floor(akspiel.getOpt() * 1.5) + 1;
					}
					optSolve.setText("Zu erreichende Distanz: " + optMittel + " ");
				}
				else if (schwere == "Schwer") {
					if (Math.floor(akspiel.getOpt()*1.2) == akspiel.getOpt()*1.2) {
						optSchwer = (int) (akspiel.getOpt() * 1.2);
					}
					else {
						optSchwer = (int) Math.floor(akspiel.getOpt() * 1.2) + 1;
					}
					optSolve.setText("Zu erreichende Distanz: " + optSchwer + " ");
				}
				startTimer();
			}
		});
		
		anleitung.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame anleitung = new JFrame("Anleitung");
				anleitung.setLayout(new GridLayout(7,1));
				JLabel prinzip1 = new JLabel("Platziere die Burgen(schwarze Quadrate) per Drag&Drop auf den Dörfern(rote Quadrate), " , SwingConstants.CENTER);
				JLabel prinzip2 = new JLabel("sodass mit der Manhattan-Metrik die maximale Distanz zwischen einem Dorf ohne Burg und der nächstgelegenden Burg möglichst gering ist.", SwingConstants.CENTER);
				JLabel steuerung1 = new JLabel("Logge deine Lösung ein, indem du auf 'Loesung abgeben' drückst oder den Thron(gelbes Quadrat)", SwingConstants.CENTER);
				JLabel steuerung2 = new JLabel(" mithilfe der Pfeiltasen/WASD auf ein Dorf ohne Burg steuerst.", SwingConstants.CENTER);
				JLabel beginner = new JLabel("Training: Beschütze mit 3 Burgen 6 Dörfer in unbegrenzter Zeit", SwingConstants.CENTER);
				JLabel medium = new JLabel("Mittel: Beschütze mit 6 Burgen 10 Dörfer in 20 Sekunden", SwingConstants.CENTER);
				JLabel hard = new JLabel("Schwer: Beschütze mit 5 Burgen 25 Dörfer in 90 Sekunden", SwingConstants.CENTER);
				anleitung.add(prinzip1);
				anleitung.add(prinzip2);
				anleitung.add(steuerung1);
				anleitung.add(steuerung2);
				anleitung.add(beginner);
				anleitung.add(medium);
				anleitung.add(hard);
				if (spielLaueft == true) {
					reset.setEnabled(true);
					pause.setText("Fortsetzen");
					spielLaueft = false;
					schwierigkeit.setEnabled(true);
				}
				anleitung.dispose();
				anleitung.setSize(850,600);
				anleitung.setResizable(false);
				anleitung.setLocationRelativeTo(null);
				anleitung.setVisible(true);
				
				}
			
		});
		
		pause.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (pause.getText() == "Pause") {
					start.setEnabled(true);
					reset.setEnabled(true);
					pause.setText("Fortsetzen");
					spielLaueft = false;
					schwierigkeit.setEnabled(true);
				}
				else {
					reset.setEnabled(false);
					pause.setText("Pause");
					spielLaueft = true;
					schwierigkeit.setEnabled(false);
					schwierigkeit.setSelectedItem(schwere_alt);
					start.setEnabled(false);
				}
			}
			
		});
		
		ende.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(ende.getText() == "Spiel beenden") {
					timer.cancel();
					spielLaueft = false;
					zurueck.setEnabled(true);
					reset.setEnabled(true);
					pause.setEnabled(false);
					pause.setText("Pause");
					ende.setText("Neues Spiel");
					loesung.setEnabled(false);
					game.setAnzeigen(true);
					game.drawVerloren(game.getGraphics(), "Verloren", 200,200);
					repaint();
					Timer timer = new Timer();
					timer.schedule(new TimerTask() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							game.setVerloren(false);
							game.drawVerloren(game.getGraphics(), "Verloren", 200,200);
							repaint();
						}
						
					}, 2000);
					highscore();
				}
				else {
					start.setEnabled(true);
					cardLayout.show(spiele, "start");
					ende.setText("Spiel beenden");	
					ende.setEnabled(false);
					schwierigkeit.setSelectedIndex(0);
					schwierigkeit.setEnabled(true);
					}
			}
			
		});
		
		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					writer = new FileWriter(datei);
					writer.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
				}
				for ( int i = 0; i < 6; i++) {
					bestenliste[i] = null;
				}
				first.setText("1. -  0");
				second.setText("2. -  0");
				third.setText("3. -  0");
			}
			
		});
		
		loesung.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (loesung.getText() == "Loesung abgeben") {
					timer.cancel();
					opt = 0;
					for (int i = 0; i < doerfer.length; i++) {
						maxdistance[i] = Integer.MAX_VALUE;
					}
					for (int i = 0; i < burgen.length; i++) {
						if (burgen[i].getPlaced() != -1) {
							CenterCelectionPlayer algo = new CenterCelectionPlayer(doerfer, burgen[i].getPlaced());
							int[] disttemp = algo.getMaxdistance();
							for(int j=0; j < doerfer.length;j++) {
								if (disttemp[j] < maxdistance[j]) {
									maxdistance[j] = disttemp[j];
								}
							}
						}
					}
					for (int i = 0; i < doerfer.length;i++) {
						if( doerfer[i].isExistiert() == true) {
							if (opt < maxdistance[i]) {
								opt = maxdistance[i];
							}
						}
					}
					if (schwere == "Training" && opt <= akspiel.getOpt() * 2) {
						deinscore = deinscore + 6;
						score.setText("Aktueller Score: " + deinscore);
						loesung.setText("Nächstes Level");
						pause.setEnabled(false);
						game.setGewonnen(true);
						game.drawGewonnen(game.getGraphics(), "Gewonnen", 200,200);
						repaint();
						Timer timer = new Timer();
						timer.schedule(new TimerTask() {
	
							@Override
							public void run() {
								// TODO Auto-generated method stub
								game.setGewonnen(false);
								game.drawGewonnen(game.getGraphics(), "Gewonnen", 200,200);
								repaint();
							}
							
						}, 2000);
					}
					else if (schwere == "Mittel" && opt <= optMittel) {
						deinscore = deinscore +12;
						score.setText("Aktueller Score: " + deinscore);
						loesung.setText("Nächstes Level");
						pause.setEnabled(false);
						game.setGewonnen(true);
						game.drawGewonnen(game.getGraphics(), "Gewonnen", 200,200);
						repaint();
						Timer timer = new Timer();
						timer.schedule(new TimerTask() {
	
							@Override
							public void run() {
								// TODO Auto-generated method stub
								game.setGewonnen(false);
								game.drawGewonnen(game.getGraphics(), "Gewonnen", 200,200);
								repaint();
							}
							
						}, 2000);
					}
					else if ( schwere == "Schwer" && opt <= akspiel.getOpt() * 1.2) {
						deinscore = deinscore +18;
						score.setText("Aktueller Score: " + deinscore);
						loesung.setText("Nächstes Level");
						pause.setEnabled(false);
						game.setGewonnen(true);
						game.drawGewonnen(game.getGraphics(), "Gewonnen", 200,200);
						repaint();
						Timer timer = new Timer();
						timer.schedule(new TimerTask() {
	
							@Override
							public void run() {
								// TODO Auto-generated method stub
								game.setGewonnen(false);
								game.drawGewonnen(game.getGraphics(), "Gewonnen", 200,200);
								repaint();
							}
							
						}, 2000);
					}
					else {
						zurueck.setEnabled(true);
						game.setVerloren(true);
						game.setAnzeigen(true);
						game.drawVerloren(game.getGraphics(), "Verloren", 200,200);
						spielLaueft = false;
						cardLayout.show(spiele, "game");
						pause.setEnabled(false);
						pause.setText("Pause");
						ende.setText("Neues Spiel");
						loesung.setEnabled(false);
						repaint();
						Timer timer = new Timer();
						timer.schedule(new TimerTask() {
	
							@Override
							public void run() {
								// TODO Auto-generated method stub
								game.setVerloren(false);
								game.drawVerloren(game.getGraphics(), "Verloren", 200,200);
								repaint();
							}
							
						}, 2000);
						highscore();
					}
				}
				else {
					loesung.setText("Loesung abgeben");
					Spiel4 spiel = new Spiel4(cl,buttonPanel,panelcont, anzahlDorf, anzahlBurg, schwere, false, deinscore, buttons);
					panelcont.add(spiel, "4");
					cl.show(panelcont, "4");
					spielLaueft = true;
					cardLayout.show(spiele, "game");
					aklevel += 1;
					level.setText("Level: " + aklevel);
					start.setEnabled(false);
					reset.setEnabled(false);
					loesung.setEnabled(true);
					ende.setEnabled(true);
					pause.setEnabled(true);
					schwierigkeit.setEnabled(false);
					if (schwere == "Training") {
						optSolve.setText("Zu erreichende Distanz: " + opt*2 + " ");
					}
					else if (schwere == "Mittel") {
						if (Math.floor(akspiel.getOpt()*1.5) == akspiel.getOpt()*1.5) {
							optMittel = (int) (akspiel.getOpt() * 1.5);
						}
						else {
							optMittel = (int) Math.floor(akspiel.getOpt() * 1.5) + 1;
						}
						optSolve.setText("Zu erreichende Distanz: " + optMittel + " ");
					}
					else if (schwere == "Schwer") {
						if (Math.floor(akspiel.getOpt()*1.2) == akspiel.getOpt()*1.2) {
							optSchwer = (int) (akspiel.getOpt() * 1.2);
						}
						else {
							optSchwer = (int) Math.floor(akspiel.getOpt() * 1.2) + 1;
						}
						optSolve.setText("Zu erreichende Distanz: " + optSchwer + " ");
					}
					startTimer();
				}
			}
		});
		schwierigkeit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (schwierigkeit.getSelectedItem() == "Training"){
					schwere = "Training";
					anzahlBurg = 3;
					anzahlDorf = 6;
				}
				else if (schwierigkeit.getSelectedItem() == "Mittel"){
					schwere = "Mittel";
					anzahlBurg = 6;
					anzahlDorf = 10;
				}
				else if (schwierigkeit.getSelectedItem() == "Schwer"){
					schwere = "Schwer";
					anzahlBurg = 5;
					anzahlDorf = 25;
				}
			}
			
		});
		
		speichern.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				name = nameField.getText();
				highscore2();
				myFrame.setVisible(false);
			}
			
		});
	}
	
	Action pause2 = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if(pause.isEnabled() == true) {
				if (pause.getText() == "Pause") {
					start.setEnabled(true);
					reset.setEnabled(true);
					pause.setText("Fortsetzen");
					spielLaueft = false;
					schwierigkeit.setEnabled(true);
				}
				else {
					start.setEnabled(false);
					reset.setEnabled(false);
					pause.setText("Pause");
					spielLaueft = true;
					schwierigkeit.setEnabled(false);
					schwierigkeit.setSelectedItem(schwere_alt);
				}
			}
		}
		
	};
	
	public void startTimer() {
		if(schwere == "Training") {
			time = 0;
			int t = Integer.MAX_VALUE;
			timer = new Timer();
			timertask = new TimerTask(){
				public void run() {
					if (time <= t && spielLaueft == true) {
						time++;
						zeit.setText("Timer: " + time);
					}
					else if (time <= t && spielLaueft == false) {
						
					}
					else {
						timer.cancel();
					}
				}
			};
			timer.scheduleAtFixedRate(timertask, 1000, 1000);
		}
		else if (schwere == "Mittel") {
			time = 20;
			int t = 0;
			timer = new Timer();
			timertask = new TimerTask(){
				public void run() {
					if (time > t &&  spielLaueft == true) {
						time--;
						zeit.setText("Timer: " + time);
					}
					else if (time > t &&  spielLaueft == false) {
						
					}
					else {
						
						zurueck.setEnabled(true);
						game.setVerloren(true);
						game.setAnzeigen(true);
						game.drawVerloren(game.getGraphics(), "Verloren", 200,200);
						spielLaueft = false;
						cardLayout.show(spiele, "game");
						pause.setEnabled(false);
						pause.setText("Pause");
						ende.setText("Neues Spiel");
						loesung.setEnabled(false);
						repaint();
						Timer timer2 = new Timer();
						timer2.schedule(new TimerTask() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								game.setVerloren(false);
								game.drawVerloren(game.getGraphics(), "Verloren", 200,200);
								repaint();
							}
							
						}, 2000);
						timer.cancel();
						highscore();
					}
				}
			};
			timer.scheduleAtFixedRate(timertask, 1000, 1000);

		}
		else if (schwere == "Schwer") {
			time = 90;
			int t = 0;
			timer = new Timer();
			timertask = new TimerTask(){
				public void run() {
					if (time > t &&  spielLaueft == true) {
						time--;
						zeit.setText("Timer: " + time);
					}
					else if (time > t &&  spielLaueft == false) {
						
					}
					else {
						
						zurueck.setEnabled(true);
						game.setVerloren(true);
						game.setAnzeigen(true);
						game.drawVerloren(game.getGraphics(), "Verloren", 200,200);
						spielLaueft = false;
						cardLayout.show(spiele, "game");
						pause.setEnabled(false);
						pause.setText("Pause");
						ende.setText("Neues Spiel");
						loesung.setEnabled(false);
						repaint();
						Timer timer2 = new Timer();
						timer2.schedule(new TimerTask() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								game.setVerloren(false);
								game.drawVerloren(game.getGraphics(), "Verloren", 200,200);
								repaint();
							}
							
						}, 2000);
						highscore();
						timer.cancel();
					}
					
				}
			};
			timer.scheduleAtFixedRate(timertask, 1000, 1000);

		}
	}
	
	public void highscore() {
		zurueck.setEnabled(true);
		
		if (bestenliste[0] == null) {
			name();
		}
		else if (bestenliste[2] == null && deinscore <= Integer.parseInt(bestenliste[1])) {
			name();
		}
		else if (bestenliste[2] == null && deinscore > Integer.parseInt(bestenliste[1])) {
			name();
		}
		else if (bestenliste[4] == null && deinscore <= Integer.parseInt(bestenliste[3])) {
			name();
		}
		else if (bestenliste[4] == null && deinscore > Integer.parseInt(bestenliste[1])) {
			name();
		}
		else if (bestenliste[4] == null && deinscore > Integer.parseInt(bestenliste[3])) {
			name();
		}
		else if (deinscore > Integer.parseInt(bestenliste[1])) {
			name();
		}
		else if (deinscore > Integer.parseInt(bestenliste[3])) {
			name();
		}
		else if (deinscore > Integer.parseInt(bestenliste[5])) {
			name();
		}
	}
	
	public void highscore2() {
		zurueck.setEnabled(true);
		
		if (bestenliste[0] == null) {
			String urscore = String.valueOf(deinscore);
			try {
				writer = new FileWriter(datei);
				writer.write(name);
				writer.write("\n\r");
				writer.write(urscore);
				writer.close();
				try {
					scan = new Scanner(datei);
				} catch (FileNotFoundException e1) {
					System.out.println("File not found");
				}
				for (int i = 0; i < 6; i++) {
					if (scan.hasNextLine() == true) {
						bestenliste[i] = scan.nextLine();
						if (scan.hasNextLine()) {
							scan.nextLine();
						}
					}
				}
				scan.close();
				first.setText("1. " + bestenliste[0] + " " + bestenliste[1]);
				second.setText("2. -  0");
				third.setText("3. -  0");
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
			}
		}
		else if (bestenliste[2] == null && deinscore <= Integer.parseInt(bestenliste[1])) {
			String urscore = String.valueOf(deinscore);
			try {
				writer = new FileWriter(datei);
				writer.write(bestenliste[0]);
				writer.write("\n\r");
				writer.write(bestenliste[1]);
				writer.write("\n\r");
				writer.write(name);
				writer.write("\n\r");
				writer.write(urscore);
				writer.close();
				try {
					scan = new Scanner(datei);
				} catch (FileNotFoundException e1) {
					System.out.println("File not found");
				}
				for (int i = 0; i < 6; i++) {
					if (scan.hasNextLine() == true) {
						bestenliste[i] = scan.nextLine();
						if (scan.hasNextLine()) {
							scan.nextLine();
						}
					}
				}
				scan.close();
				first.setText("1. " + bestenliste[0] + " " + bestenliste[1]);
				second.setText("2. " + bestenliste[2] + " " + bestenliste[3]);
				third.setText("3. -  0");
				
			} catch (IOException e1) {
				// TODO Auto-generated catch blo
			}
		}
		else if (bestenliste[2] == null && deinscore > Integer.parseInt(bestenliste[1])) {
			String urscore = String.valueOf(deinscore);
			try {
				writer = new FileWriter(datei);
				writer.write(name);
				writer.write("\n\r");
				writer.write(urscore);
				writer.write("\n\r");
				writer.write(bestenliste[0]);
				writer.write("\n\r");
				writer.write(bestenliste[1]);
				writer.write("\n\r");
				writer.close();
				try {
					scan = new Scanner(datei);
				} catch (FileNotFoundException e1) {
					System.out.println("File not found");
				}
				for (int i = 0; i < 6; i++) {
					if (scan.hasNextLine() == true) {
						bestenliste[i] = scan.nextLine();
						if (scan.hasNextLine()) {
							scan.nextLine();
						}
					}
				}
				scan.close();
				first.setText("1. " + bestenliste[0] + " " + bestenliste[1]);
				second.setText("2. " + bestenliste[2] + " " + bestenliste[3]);
				third.setText("3. -  0");
				
			} catch (IOException e1) {
				// TODO Auto-generated catch blo
			}
		}
		else if (bestenliste[4] == null && deinscore <= Integer.parseInt(bestenliste[3])) {
			String urscore = String.valueOf(deinscore);
			try {
				writer = new FileWriter(datei);
				writer.write(bestenliste[0]);
				writer.write("\n\r");
				writer.write(bestenliste[1]);
				writer.write("\n\r");
				writer.write(bestenliste[2]);
				writer.write("\n\r");
				writer.write(bestenliste[3]);
				writer.write("\n\r");
				writer.write(name);
				writer.write("\n\r");
				writer.write(urscore);
				writer.close();
				try {
					scan = new Scanner(datei);
				} catch (FileNotFoundException e1) {
					System.out.println("File not found");
				}
				for (int i = 0; i < 6; i++) {
					if (scan.hasNextLine() == true) {
						bestenliste[i] = scan.nextLine();
						if (scan.hasNextLine()) {
							scan.nextLine();
						}
					}
				}
				scan.close();
				first.setText("1. " + bestenliste[0] + " " + bestenliste[1]);
				second.setText("2. " + bestenliste[2] + " " + bestenliste[3]);
				third.setText("3. " + bestenliste[4] + " " + bestenliste[5]);
				
			} catch (IOException e1) {
				// TODO Auto-generated catch blo
			}
		}
		else if (bestenliste[4] == null && deinscore > Integer.parseInt(bestenliste[1])) {
			String urscore = String.valueOf(deinscore);
			try {
				writer = new FileWriter(datei);
				writer.write(name);
				writer.write("\n\r");
				writer.write(urscore);
				writer.write("\n\r");
				writer.write(bestenliste[0]);
				writer.write("\n\r");
				writer.write(bestenliste[1]);
				writer.write("\n\r");
				writer.write(bestenliste[2]);
				writer.write("\n\r");
				writer.write(bestenliste[3]);
				writer.close();
				try {
					scan = new Scanner(datei);
				} catch (FileNotFoundException e1) {
					System.out.println("File not found");
				}
				for (int i = 0; i < 6; i++) {
					if (scan.hasNextLine() == true) {
						bestenliste[i] = scan.nextLine();
						if (scan.hasNextLine()) {
							scan.nextLine();
						}
					}
				}
				scan.close();
				first.setText("1. " + bestenliste[0] + " " + bestenliste[1]);
				second.setText("2. " + bestenliste[2] + " " + bestenliste[3]);
				third.setText("3. " + bestenliste[4] + " " + bestenliste[5]);
				
			} catch (IOException e1) {
				// TODO Auto-generated catch blo
			}
		}
		else if (bestenliste[4] == null && deinscore > Integer.parseInt(bestenliste[3])) {
			String urscore = String.valueOf(deinscore);
			try {
				writer = new FileWriter(datei);
				writer.write(bestenliste[0]);
				writer.write("\n\r");
				writer.write(bestenliste[1]);
				writer.write("\n\r");
				writer.write(name);
				writer.write("\n\r");
				writer.write(urscore);
				writer.write("\n\r");
				writer.write(bestenliste[2]);
				writer.write("\n\r");
				writer.write(bestenliste[3]);
				writer.close();
				try {
					scan = new Scanner(datei);
				} catch (FileNotFoundException e1) {
					System.out.println("File not found");
				}
				for (int i = 0; i < 6; i++) {
					if (scan.hasNextLine() == true) {
						bestenliste[i] = scan.nextLine();
						if (scan.hasNextLine()) {
							scan.nextLine();
						}
					}
				}
				scan.close();
				first.setText("1. " + bestenliste[0] + " " + bestenliste[1]);
				second.setText("2. " + bestenliste[2] + " " + bestenliste[3]);
				third.setText("3. " + bestenliste[4] + " " + bestenliste[5]);
				
			} catch (IOException e1) {
				// TODO Auto-generated catch blo
			}
		}
		else if (deinscore > Integer.parseInt(bestenliste[1])) {
			String urscore = String.valueOf(deinscore);
			try {
				writer = new FileWriter(datei);
				writer.write(name);
				writer.write("\n\r");
				writer.write(urscore);
				writer.write("\n\r");
				writer.write(bestenliste[0]);
				writer.write("\n\r");
				writer.write(bestenliste[1]);
				writer.write("\n\r");
				writer.write(bestenliste[2]);
				writer.write("\n\r");
				writer.write(bestenliste[3]);
				writer.close();
				try {
					scan = new Scanner(datei);
				} catch (FileNotFoundException e1) {
					System.out.println("File not found");
				}
				for (int i = 0; i < 6; i++) {
					if (scan.hasNextLine() == true) {
						bestenliste[i] = scan.nextLine();
						if (scan.hasNextLine()) {
							scan.nextLine();
						}
					}
				}
				scan.close();
				first.setText("1. " + bestenliste[0] + " " + bestenliste[1]);
				second.setText("2. " + bestenliste[2] + " " + bestenliste[3]);
				third.setText("3. " + bestenliste[4] + " " + bestenliste[5]);
				
			} catch (IOException e1) {
				// TODO Auto-generated catch blo
			}
		}
		else if (deinscore > Integer.parseInt(bestenliste[3])) {
			String urscore = String.valueOf(deinscore);
			try {
				writer = new FileWriter(datei);
				writer.write(bestenliste[0]);
				writer.write("\n\r");
				writer.write(bestenliste[1]);
				writer.write("\n\r");
				writer.write(name);
				writer.write("\n\r");
				writer.write(urscore);
				writer.write("\n\r");
				writer.write(bestenliste[2]);
				writer.write("\n\r");
				writer.write(bestenliste[3]);
				writer.close();
				try {
					scan = new Scanner(datei);
				} catch (FileNotFoundException e1) {
					System.out.println("File not found");
				}
				for (int i = 0; i < 6; i++) {
					if (scan.hasNextLine() == true) {
						bestenliste[i] = scan.nextLine();
						if (scan.hasNextLine()) {
							scan.nextLine();
						}
					}
				}
				scan.close();
				first.setText("1. " + bestenliste[0] + " " + bestenliste[1]);
				second.setText("2. " + bestenliste[2] + " " + bestenliste[3]);
				third.setText("3. " + bestenliste[4] + " " + bestenliste[5]);
				
			} catch (IOException e1) {
				// TODO Auto-generated catch blo
			}
		}
		else if (deinscore > Integer.parseInt(bestenliste[5])) {
			String urscore = String.valueOf(deinscore);
			try {
				writer = new FileWriter(datei);
				writer.write(bestenliste[0]);
				writer.write("\n\r");
				writer.write(bestenliste[1]);
				writer.write("\n\r");
				writer.write(bestenliste[2]);
				writer.write("\n\r");
				writer.write(bestenliste[3]);
				writer.write("\n\r");
				writer.write(name);
				writer.write("\n\r");
				writer.write(urscore);
				writer.close();
				try {
					scan = new Scanner(datei);
				} catch (FileNotFoundException e1) {
					System.out.println("File not found");
				}
				for (int i = 0; i < 6; i++) {
					if (scan.hasNextLine() == true) {
						bestenliste[i] = scan.nextLine();
						if (scan.hasNextLine()) {
							scan.nextLine();
						}
					}
				}
				scan.close();
				first.setText("1. " + bestenliste[0] + " " + bestenliste[1]);
				second.setText("2. " + bestenliste[2] + " " + bestenliste[3]);
				third.setText("3. " + bestenliste[4] + " " + bestenliste[5]);
				
			} catch (IOException e1) {
				// TODO Auto-generated catch blo
			}
		}
		deinscore = 0;
		score.setText("Aktueller Score: " + deinscore);
	}
	
	public void name() {
		myFrame = new JFrame();
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setSize(300, 200);
		myFrame.setResizable(false);
		myFrame.setLocationRelativeTo(null);
		myFrame.setLayout(new GridLayout(3,1));
		JLabel eingabe = new JLabel("Namen eingeben", SwingConstants.CENTER);
		nameField = new JTextField(50);
		myFrame.getContentPane().add(eingabe);
		myFrame.getContentPane().add(nameField);
		myFrame.getContentPane().add(speichern);
		myFrame.pack();
		myFrame.setVisible(true);
		
	}
	
	public boolean isSpielLaueft() {
		return spielLaueft;
	}
	
	public void setSpielLaueft(boolean spielLaueft) {
		this.spielLaueft = spielLaueft;
	}

	public int getOpt() {
		return opt;
	}

	public void setOpt(int opt) {
		this.opt = opt;
	}

	public Spiel4 getAkspiel() {
		return akspiel;
	}

	public void setAkspiel(Spiel4 akspiel) {
		this.akspiel = akspiel;
	}

	public int getDeinscore() {
		return deinscore;
	}

	public void setDeinscore(int deinscore) {
		this.deinscore = deinscore;
	}

	public Spielbereich getGame() {
		return game;
	}

	public void setGame(Spielbereich game) {
		this.game = game;
	}

	public JButton getZurueck() {
		return zurueck;
	}

	public void setZurueck(JButton zurueck) {
		this.zurueck = zurueck;
	}

	public JLabel getHighscore() {
		return score;
	}

	public void setHighscore(JLabel highscore) {
		this.score = highscore;
	}

	public JButton getReset() {
		return reset;
	}

	public void setReset(JButton reset) {
		this.reset = reset;
	}

	public JLabel getLevel() {
		return level;
	}

	public void setLevel(JLabel level) {
		this.level = level;
	}

	public JLabel getZeit() {
		return zeit;
	}

	public void setZeit(JLabel zeit) {
		this.zeit = zeit;
	}

	public JButton getAnleitung() {
		return anleitung;
	}

	public void setAnleitung(JButton anleitung) {
		this.anleitung = anleitung;
	}

	public JButton getStart() {
		return start;
	}

	public void setStart(JButton start) {
		this.start = start;
	}

	public JButton getPause() {
		return pause;
	}

	public void setPause(JButton pause) {
		this.pause = pause;
	}

	public JButton getEnde() {
		return ende;
	}

	public void setEnde(JButton ende) {
		this.ende = ende;
	}

	public JButton getLoesung() {
		return loesung;
	}

	public void setLoesung(JButton loesung) {
		this.loesung = loesung;
	}

	public CardLayout getCl() {
		return cl;
	}

	public void setCl(CardLayout cl) {
		this.cl = cl;
	}

	public JPanel getButtonPanel() {
		return buttonPanel;
	}

	public void setButtonPanel(JPanel buttonPanel) {
		this.buttonPanel = buttonPanel;
	}

	public JPanel getPanelcont() {
		return panelcont;
	}

	public void setPanelcont(JPanel panelcont) {
		this.panelcont = panelcont;
	}

	public Burg[] getBurgen() {
		return burgen;
	}

	public void setBurgen(Burg[] burgen) {
		this.burgen = burgen;
	}

	public Dorf[] getDoerfer() {
		return doerfer;
	}

	public void setDoerfer(Dorf[] doerfer) {
		this.doerfer = doerfer;
	}

	public int[] getMaxdistance() {
		return maxdistance;
	}

	public void setMaxdistance(int[] maxdistance) {
		this.maxdistance = maxdistance;
	}

	public boolean isSpielBeendet() {
		return spielBeendet;
	}

	public void setSpielBeendet(boolean spielBeendet) {
		this.spielBeendet = spielBeendet;
	}

	public JComboBox getSchwierigkeit() {
		return schwierigkeit;
	}

	public void setSchwierigkeit(JComboBox schwierigkeit) {
		this.schwierigkeit = schwierigkeit;
	}

	public int getAnzahlDorf() {
		return anzahlDorf;
	}

	public void setAnzahlDorf(int anzahlDorf) {
		this.anzahlDorf = anzahlDorf;
	}

	public int getAnzahlBurg() {
		return anzahlBurg;
	}

	public void setAnzahlBurg(int anzahlBurg) {
		this.anzahlBurg = anzahlBurg;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public CardLayout getCardLayout() {
		return cardLayout;
	}

	public void setCardLayout(CardLayout cardLayout) {
		this.cardLayout = cardLayout;
	}

	public JPanel getSpiele() {
		return spiele;
	}

	public void setSpiele(JPanel spiele) {
		this.spiele = spiele;
	}

	public String getSchwere() {
		return schwere;
	}

	public void setSchwere(String schwere) {
		this.schwere = schwere;
	}

	public String getSchwere_alt() {
		return schwere_alt;
	}

	public void setSchwere_alt(String schwere_alt) {
		this.schwere_alt = schwere_alt;
	}

	public Action getPause2() {
		return pause2;
	}

	public void setPause2(Action pause2) {
		this.pause2 = pause2;
	}

	public boolean isFirstTime() {
		return firstTime;
	}

	public void setFirstTime(boolean firstTime) {
		this.firstTime = firstTime;
	}
	
	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public JLabel getOptSolve() {
		return optSolve;
	}

	public void setOptSolve(JLabel optSolve) {
		this.optSolve = optSolve;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public TimerTask getTimertask() {
		return timertask;
	}

	public void setTimertask(TimerTask timertask) {
		this.timertask = timertask;
	}

	public Buttons getButtons() {
		return buttons;
	}

	public void setButtons(Buttons buttons) {
		this.buttons = buttons;
	}

	public Maze getMaze() {
		return maze;
	}

	public void setMaze(Maze maze) {
		this.maze = maze;
	}

	public File getDatei() {
		return datei;
	}

	public void setDatei(File datei) {
		this.datei = datei;
	}

	public FileWriter getWriter() {
		return writer;
	}

	public void setWriter(FileWriter writer) {
		this.writer = writer;
	}
	
	public JLabel getHighscores() {
		return highscores;
	}

	public void setHighscores(JLabel highscores) {
		this.highscores = highscores;
	}

	public JLabel getFirst() {
		return first;
	}

	public void setFirst(JLabel first) {
		this.first = first;
	}

	public JLabel getSecond() {
		return second;
	}

	public void setSecond(JLabel second) {
		this.second = second;
	}

	public JLabel getThird() {
		return third;
	}

	public void setThird(JLabel third) {
		this.third = third;
	}

	public JLabel getScore() {
		return score;
	}

	public void setScore(JLabel score) {
		this.score = score;
	}

	public Scanner getScan() {
		return scan;
	}

	public void setScan(Scanner scan) {
		this.scan = scan;
	}

	public String[] getBestenliste() {
		return bestenliste;
	}

	public void setBestenliste(String[] bestenliste) {
		this.bestenliste = bestenliste;
	}

	public int getAklevel() {
		return aklevel;
	}

	public void setAklevel(int aklevel) {
		this.aklevel = aklevel;
	}

	public int getOptMittel() {
		return optMittel;
	}

	public void setOptMittel(int optMittel) {
		this.optMittel = optMittel;
	}

	public int getOptSchwer() {
		return optSchwer;
	}

	public void setOptSchwer(int optSchwer) {
		this.optSchwer = optSchwer;
	}

}
