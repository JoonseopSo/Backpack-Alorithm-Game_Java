package spiel1Klassen;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

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
import javax.swing.Timer;

import team27.main.Maze;
import team27.main.Spiel1;
import team27.main.Start;

public class Buttonpanelsp1 extends JPanel {

	private Items[] items;
	private Itemfelder itemfelder;
	private Koffer koffer;

	public JButton back;
	private JLabel highscore;
	private JButton reset;
	public JLabel punkte;
	private JLabel schranke;
	private JLabel timer;
	private JButton anleitung;
	private JLabel level;
	private JButton start;
	private JButton pause;
	private JButton ende;
	public JButton loesungsubmit;
	private JComboBox dropdown;
	private JLabel score_1;
	private JLabel score_2;
	private JLabel score_3;
	private File file;
	private FileWriter filewriter;
	private Scanner scanner;
	private String[] highscoreliste;
	private String names;
	private boolean spielinpause = false;
	private boolean loesungmittimer = false;
	private boolean start_standard = true;
	public int zielwert;
	private int summegewicht;
	private JButton speichern;
	private JFrame savepoint;
	private JTextField textfield;
	public int summewert;
	public Timer time;
	private Timer time2;
	private int seconds;
	private CardLayout cl;
	private JPanel buttonPanel;
	private JPanel panelcont;
	private Spiel1 spiel1;
	private CardLayout card;
	private JPanel spiel1card;
	private Maze maze;
	private int k, pre_level, post_level;

	public Buttonpanelsp1(CardLayout cl, JPanel buttonPanel, JPanel panelcont, CardLayout card, JPanel spiel1card,
			Itemfelder itemfelder) {
		this.cl = cl;
		this.buttonPanel = buttonPanel;
		this.panelcont = panelcont;
		this.card = card;
		this.spiel1card = spiel1card;
		this.itemfelder = itemfelder;

		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("P"), "Pause");
		getActionMap().put("Pause", pause_key);

		back = new JButton("Zurück zum Labyrinth");
		anleitung = new JButton("Anleitung");
		schranke = new JLabel("Zielwert:  ");
		highscore = new JLabel("Highscoreboard");
		score_1 = new JLabel("1:  ");
		score_2 = new JLabel("2:  ");
		score_3 = new JLabel("3:  ");
		String[] schwierigkeit = { "Training", "Mittel", "Schwer" };
		dropdown = new JComboBox(schwierigkeit);
		highscoreliste = new String[6];
		start = new JButton("Starte Spiel");
		punkte = new JLabel("Punkte:   0");
		level = new JLabel("Level : ");
		timer = new JLabel("Timer : ");
		pause = new JButton("Pause");
		reset = new JButton("Reset");
		loesungsubmit = new JButton("Loesung abgeben");
		ende = new JButton("Spiel enden");
		speichern = new JButton("Speichern");
//Korrekturhilfe Einlesen
		file = new File("src/resources/spiel1.txt");
		scanner = null;

		try {
			// Korrekturhilfe Einlesen
			scanner = new Scanner(file);
		} catch (FileNotFoundException e1) {
			System.out.println("File not found");
		}
		for (int i = 0; i < 6; i++) {
			if (scanner.hasNextLine() == true) {
				highscoreliste[i] = scanner.nextLine();
				if (scanner.hasNextLine()) {
					scanner.nextLine();
				}
			}
		}
		scanner.close();
		if (highscoreliste[0] != null) {
			score_1 = new JLabel("1. " + highscoreliste[0] + " : " + highscoreliste[1]);
		} else {
			score_1 = new JLabel("1. -  0");
		}
		if (highscoreliste[2] != null) {
			score_2 = new JLabel("2. " + highscoreliste[2] + " : " + highscoreliste[3]);
		} else {
			score_2 = new JLabel("2. -  0");
		}
		if (highscoreliste[4] != null) {
			score_3 = new JLabel("3. " + highscoreliste[4] + " : " + highscoreliste[5]);
		} else {
			score_3 = new JLabel("3. -  0");
		}

		setBackground(new Color(204, 200, 255));
		GridLayout gl = new GridLayout(16, 1);
		setLayout(gl);

		add(back, gl);
		add(anleitung, gl);
		add(reset, gl);
		add(schranke, gl);
		add(highscore, gl);
		add(score_1, gl);
		add(score_2, gl);
		add(score_3, gl);
		add(dropdown, gl);
		add(start, gl);
		add(punkte, gl);
		add(level, gl);
		add(timer, gl);
		add(pause, gl);

		add(loesungsubmit, gl);
		add(ende, gl);
		pause.setEnabled(false);
		start.setEnabled(false);
		loesungsubmit.setEnabled(false);
		ende.setEnabled(false);
		if (start_standard = true) {
			start.setEnabled(true);
			k = 5;
			pre_level = 1;
			seconds = 0;
			start_standard = false;
		}

		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(panelcont, "mp");
				buttonPanel.setVisible(true);
				Start s = new Start(maze);
				s.mazeErzeugen(s);

			}

		});
		anleitung.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JFrame anleitung = new JFrame();
				anleitung.setLayout(new GridLayout(4, 1));
				JLabel text = new JLabel("Koffer hat maximal Traglast, Items haben Gewicht und Wert",
						SwingConstants.CENTER);
				JLabel text2 = new JLabel("Füllen Sie das Koffer, sodass es maximalen Wert bei zulässigem Traglast hat",
						SwingConstants.CENTER);
				JLabel text3 = new JLabel("Mit Drag&Drop das Koffer befüllen", SwingConstants.CENTER);
				JLabel text4 = new JLabel("Mit W A S D Koffer bewegen", SwingConstants.CENTER);
				anleitung.add(text);
				anleitung.add(text2);
				anleitung.add(text3);
				anleitung.add(text4);
				anleitung.dispose();
				anleitung.setBackground(Color.white);
				anleitung.setSize(600, 600);
				anleitung.setMinimumSize(new Dimension(600, 600));
				anleitung.setLocationRelativeTo(null);
				anleitung.setVisible(true);
				time.stop();
				if (spielinpause == true) {
					reset.setEnabled(true);
					pause.setText("Spiel fortsetzen");
					spiel1.setGamerunning(false);
					dropdown.setEnabled(true);
					time.stop();

				}

			}

		});
		speichern.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				names = textfield.getText();
				save();
				savepoint.setVisible(false);
				// savepoint.dispose();

			}

		});
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ende.setEnabled(true);
				spiel1 = new Spiel1(cl, buttonPanel, panelcont, k);
				koffer = new Koffer();
				items = new Items[k];

				for (int i = 0; i < k; i++) {
					items[i] = new Items();
				}

				spiel1card.add(spiel1.getItemfelder(), "spiel1");
				if (k == 5) {
					zielwert = spiel1.getItemfelder().getZielwerttraining();
					schranke.setText("Zielwert:  " + zielwert);
					spiel1.setTrainingspiel(true);
				} else if (k == 7) {
					zielwert = spiel1.getItemfelder().getZielwertmittel();
					schranke.setText("Zielwert:  " + zielwert);
					spiel1.setMittelspiel(true);
				} else if (k == 9) {
					zielwert = spiel1.getItemfelder().getZielwertschwer();
					schranke.setText("Zielwert:  " + zielwert);
					spiel1.setSchwerspiel(true);

				}

				if (spielinpause = true) {
					summewert = 0;
					punkte.setText("Punkte:  " + summewert);
					pause.setText("Pause");

				}

				card.show(spiel1card, "spiel1");
				try {
					// Korrekturhilfe Einlesen
					scanner = new Scanner(file);
				} catch (FileNotFoundException e1) {
					System.out.println("File not found");
				}
				for (int i = 0; i < 4; i++) {
					if (scanner.hasNextLine() == true) {
						highscoreliste[i] = scanner.nextLine();
						if (scanner.hasNextLine()) {
							scanner.nextLine();
						}
					}
				}
				scanner.close();
				pre_level = 1;
				level.setText("Level : " + pre_level);
				spiel1.setGamerunning(true);

				start.setEnabled(false);
				pause.setEnabled(true);
				pause.setText("Pause");
				ende.setText("Spiel enden");
				loesungsubmit.setText("Loesung abgeben");
				loesungsubmit.setEnabled(true);
				dropdown.setEnabled(false);
				reset.setEnabled(false);
				back.setEnabled(false);

				timer();
				timer2();
				time2.start();
				time.start();

			}

		});
		dropdown.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (dropdown.getSelectedIndex() == 0) {
					k = 5;
					pre_level = 1;
					seconds = 0;
					start.setEnabled(true);

				}
				if (dropdown.getSelectedIndex() == 1) {
					k = 7;
					seconds = 31;
					pre_level = 1;
					start.setEnabled(true);

				}
				if (dropdown.getSelectedIndex() == 2) {
					k = 9;
					seconds = 16;
					pre_level = 1;
					start.setEnabled(true);

				}

			}

		});
		pause.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (pause.getText() == "Pause") {
					time.stop();
					time2.stop();
					start.setEnabled(true);
					reset.setEnabled(true);
					dropdown.setEnabled(true);
					loesungsubmit.setEnabled(true);

					spiel1.setGamerunning(false);
					spielinpause = true;
					pause.setText("Spiel fortsetzen");
				} else if (pause.getText() == "Spiel fortsetzen") {
					reset.setEnabled(false);
					time.start();
					time2.start();
					pause.setText("Pause");
					spiel1.setGamerunning(true);
					dropdown.setEnabled(false);
					start.setEnabled(false);
					spielinpause = false;
					loesungsubmit.setEnabled(true);
				}
			}

		});
		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					// Korrekturhilfe Einlesen 
					filewriter = new FileWriter(file);
					filewriter.close();
				} catch (IOException e1) {

				}
				for (int i = 0; i < 4; i++) {
					highscoreliste[i] = null;
				}
				score_1.setText("1. -  0");
				score_2.setText("2. -  0");
				score_3.setText("3. -  0");

			}

		});
		loesungsubmit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (loesungsubmit.getText() == "Loesung abgeben") {
					time.stop();
					if (spiel1.getSummewert() >= zielwert
							&& spiel1.getSummegewicht() <= spiel1.getItemfelder().getGewichtsschranke()) {

						spiel1.setSummewertausbp1(summewert);
						punkte.setText("Punkte:  " + summewert);
						spiel1.getItemfelder().setSpielgewonnen(true);
						spiel1.setGamerunning(false);
						pause.setEnabled(false);
						pause.setText("Pause");
						start.setEnabled(false);
						dropdown.setEnabled(false);
						time.start();
						time2.start();
						repaint();
						loesungsubmit.setText("Nächste Level");

					} else if (spiel1.getSummewert() < zielwert
							|| spiel1.getSummegewicht() > spiel1.getItemfelder().getGewichtsschranke()) {
						spiel1.getItemfelder().setSpielverloren(true);
						spiel1.setGamerunning(false);
						start.setEnabled(false);
						back.setEnabled(true);
						dropdown.setEnabled(false);
						pause.setEnabled(false);
						loesungsubmit.setEnabled(false);
						pause.setText("Pause");
						repaint();

					}
				} else if (loesungsubmit.getText() == "Nächste Level") {
					loesungsubmit.setText("Loesung abgeben");
					time.start();
					time2.start();
					spiel1.getItemfelder().setSpielverloren(false);
					spiel1.getItemfelder().setSpielgewonnen(false);
//					time.restart();
					spiel1 = new Spiel1(cl, buttonPanel, panelcont, k);
					koffer = new Koffer();
					items = new Items[k];

					for (int i = 0; i < k; i++) {
						items[i] = new Items();
					}

					spiel1card.add(spiel1.getItemfelder(), "spiel1");

					card.show(spiel1card, "spiel1");
					post_level = pre_level;
					pre_level = pre_level + 1;
					level.setText("Level : " + pre_level);
					start.setEnabled(false);
					pause.setEnabled(true);
					loesungsubmit.setEnabled(true);
					spiel1.setGamerunning(true);

					if (k == 7) {
						seconds = 31;
						spiel1.setMittelspiel(true);
						zielwert = spiel1.getItemfelder().getZielwertmittel();
						schranke.setText("Zielwert:  " + zielwert);
					}
					if (k == 9) {
						seconds = 16;
						spiel1.setSchwerspiel(true);
						zielwert = spiel1.getItemfelder().getZielwertschwer();
						schranke.setText("Zielwert:  " + zielwert);
					}
					if (k == 5) {
						spiel1.setTrainingspiel(true);
						zielwert = spiel1.getItemfelder().getZielwerttraining();
						schranke.setText("Zielwert:  " + zielwert);
					}

				}
			}

		});
		ende.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (ende.getText() == "Spiel enden") {
					time.stop();
					back.setEnabled(true);
					start.setEnabled(false);
					reset.setEnabled(true);
					pause.setEnabled(false);
					loesungsubmit.setEnabled(false);
					spiel1.setGamerunning(false);
					ende.setText("Neues Spiel");
					speichern();
				} else if (ende.getText() == "Neues Spiel") {
					start.setEnabled(true);
					ende.setText("Spiel enden");
					ende.setEnabled(false);
					dropdown.setEnabled(true);
					if (k == 9) {
						seconds = 16;
					} else if (k == 7) {
						seconds = 31;
					} else if (k == 5) {
						seconds = 0;

					}
				}
			}

		});

	}

	public void timer() {

		time = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (k == 5) {

					seconds++;
					timer.setText("Timer : " + seconds);

				} else if (k == 7) {

					seconds--;
					timer.setText("Timer : " + seconds);
					if (seconds == 0) {
						time.stop();

						back.setEnabled(true);
						reset.setEnabled(true);
						pause.setEnabled(false);
						loesungsubmit.setEnabled(false);
						spiel1.setGamerunning(false);
						spiel1.getItemfelder().setSpielverloren(true);
						speichern();
					}

				} else if (k == 9) {

					seconds--;
					timer.setText("Timer : " + seconds);
					if (seconds == 0) {
						time.stop();
						back.setEnabled(true);
						reset.setEnabled(true);
						pause.setEnabled(false);
						loesungsubmit.setEnabled(false);
						spiel1.setGamerunning(false);
						spiel1.getItemfelder().setSpielverloren(true);
						speichern();
					}

				}

			}

		});

	}

	public void timer2() {
		time2 = new Timer(50, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (spiel1.getItemfelder().isSpielgewonnen() == true) {

					pause.setEnabled(false);
					start.setEnabled(false);
					loesungsubmit.setText("Nächste Level");
					summewert += spiel1.getSummewert();
					spiel1.setSummewertausbp1(summewert);
					punkte.setText("Punkte:  " + summewert);
					spiel1.getItemfelder().setSpielgewonnen(true);
					spiel1.setGamerunning(false);

					time2.stop();
					time.stop();

				} else if (spiel1.getItemfelder().isSpielgewonnen() == false) {

				}
				if (spiel1.getItemfelder().isSpielverloren() == true) {

					start.setEnabled(false);
					back.setEnabled(true);
					pause.setEnabled(false);
					loesungsubmit.setEnabled(false);
					ende.setText("Spiel enden");
					spiel1.getItemfelder().setSpielverloren(true);
					spiel1.setGamerunning(false);
					loesungmittimer = true;
					time2.stop();
					time.stop();

				} else if (spiel1.getItemfelder().isSpielverloren() == false) {

				}

			}

		});
	}

	Action pause_key = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (spiel1.getItemfelder().isSpielverloren() == false
					&& spiel1.getItemfelder().isSpielgewonnen() == false) {
				if (pause.getText() == "Pause") {
					time.stop();
					time2.stop();
					start.setEnabled(true);
					reset.setEnabled(true);
					dropdown.setEnabled(true);
					spiel1.setGamerunning(false);
					spielinpause = true;
					pause.setText("Spiel fortsetzen");
				} else if (pause.getText() == "Spiel fortsetzen") {
					reset.setEnabled(false);
					time.start();
					time2.start();
					pause.setText("Pause");
					spielinpause = false;
					spiel1.setGamerunning(true);
					dropdown.setEnabled(false);
					start.setEnabled(false);
				}
			}
		}

	};

	public void speichern() {
		back.setEnabled(true);
		if (highscoreliste[0] == null) {
			namespeichern();

		} else if ((highscoreliste[2] == null && summewert <= Integer.parseInt(highscoreliste[1]))
				|| highscoreliste[2] == null && summewert > Integer.parseInt(highscoreliste[1])) {
			namespeichern();

		} else if ((highscoreliste[4] == null && summewert <= Integer.parseInt(highscoreliste[3]))
				|| (highscoreliste[4] == null && summewert > Integer.parseInt(highscoreliste[3]))
				|| (highscoreliste[4] == null && summewert > Integer.parseInt(highscoreliste[1]))) {
			namespeichern();

		} else if ((summewert > Integer.parseInt(highscoreliste[1]))
				|| (summewert > Integer.parseInt(highscoreliste[3]))
				|| (summewert > Integer.parseInt(highscoreliste[5]))) {
			namespeichern();
		}
	}

	public void namespeichern() {
		savepoint = new JFrame();
		setBackground(new Color(204, 229, 255));
		savepoint.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		savepoint.setPreferredSize(new Dimension(300, 200));
		savepoint.setMinimumSize(new Dimension(300, 200));
		savepoint.setLocationRelativeTo(null);
		savepoint.setLayout(new GridLayout(3, 1));
		JLabel eingabe = new JLabel("Namen eingeben", SwingConstants.CENTER);
		textfield = new JTextField(50);
		savepoint.getContentPane().add(eingabe);
		savepoint.getContentPane().add(textfield);
		savepoint.getContentPane().add(speichern);
		savepoint.pack();
		savepoint.setVisible(true);

	}

	public void save() {
		back.setEnabled(true);
//falls kein eintrag
		if (highscoreliste[0] == null) {
			String summwert_recent = String.valueOf(summewert);
			try {
				// Korrekturhilfe Einlesen
				filewriter = new FileWriter(file);
				filewriter.write(names);
				filewriter.write("\n\r");
				filewriter.write(summwert_recent);
				filewriter.close();
				try {
					scanner = new Scanner(file);
				} catch (FileNotFoundException e1) {
					System.out.println("File not found");
				}
				for (int i = 0; i <= 5; i++) {
					if (scanner.hasNextLine() == true) {
						highscoreliste[i] = scanner.nextLine();
						if (scanner.hasNextLine()) {
							scanner.nextLine();
						}
					}
				}
				scanner.close();
				score_1.setText("1. " + highscoreliste[0] + " : " + highscoreliste[1]);
				score_2.setText("2. -  0");
				score_3.setText("3. -  0");

			} catch (IOException e1) {

			}
			// falls 1 eintrag und platz2
		} else if (highscoreliste[2] == null && summewert <= Integer.parseInt(highscoreliste[1])) {
			String summwert_recent = String.valueOf(summewert);
			try {
				// Korrekturhilfe Einlesen
				filewriter = new FileWriter(file);
				filewriter.write(highscoreliste[0]);
				filewriter.write("\n\r");
				filewriter.write(highscoreliste[1]);
				filewriter.write("\n\r");
				filewriter.write(names);
				filewriter.write("\n\r");
				filewriter.write(summwert_recent);
				filewriter.close();
				try {
					// Korrekturhilfe Einlesen
					scanner = new Scanner(file);
				} catch (FileNotFoundException e1) {
					System.out.println("File not found");
				}
				for (int i = 0; i <= 5; i++) {
					if (scanner.hasNextLine() == true) {
						highscoreliste[i] = scanner.nextLine();
						if (scanner.hasNextLine()) {
							scanner.nextLine();
						}
					}
				}
				scanner.close();
				score_1.setText("1. " + highscoreliste[0] + " : " + highscoreliste[1]);
				score_2.setText("2. " + highscoreliste[2] + " : " + highscoreliste[3]);
				score_3.setText("3. -  0");

			} catch (IOException e1) {

			}
			// falls 2 eintrag und platz 1
		} else if (highscoreliste[2] == null && summewert > Integer.parseInt(highscoreliste[1])) {
			String summwert_recent = String.valueOf(summewert);
			try {
				// Korrekturhilfe Einlesen
				filewriter = new FileWriter(file);
				filewriter.write(names);
				filewriter.write("\n\r");
				filewriter.write(summwert_recent);
				filewriter.write("\n\r");
				filewriter.write(highscoreliste[0]);
				filewriter.write("\n\r");
				filewriter.write(highscoreliste[1]);
				filewriter.write("\n\r");
				filewriter.close();
				try {
					// Korrekturhilfe Einlesen
					scanner = new Scanner(file);
				} catch (FileNotFoundException e1) {
					System.out.println("File not found");
				}
				for (int i = 0; i <= 5; i++) {
					if (scanner.hasNextLine() == true) {
						highscoreliste[i] = scanner.nextLine();
						if (scanner.hasNextLine()) {
							scanner.nextLine();
						}
					}
				}
				scanner.close();
				score_1.setText("1. " + highscoreliste[0] + " : " + highscoreliste[1]);
				score_2.setText("2. " + highscoreliste[2] + " : " + highscoreliste[3]);
				score_3.setText("3. -  0");

			} catch (IOException e1) {

			}
			// falls 3 eintrag und platz 3
		} else if (highscoreliste[4] == null && summewert <= Integer.parseInt(highscoreliste[3])) {
			String summwert_recent = String.valueOf(summewert);
			try {
				// Korrekturhilfe Einlesen
				filewriter = new FileWriter(file);
				filewriter.write(highscoreliste[0]);
				filewriter.write("\n\r");
				filewriter.write(highscoreliste[1]);
				filewriter.write("\n\r");
				filewriter.write(highscoreliste[2]);
				filewriter.write("\n\r");
				filewriter.write(highscoreliste[3]);
				filewriter.write("\n\r");
				filewriter.write(names);
				filewriter.write("\n\r");
				filewriter.write(summwert_recent);
				filewriter.close();
				try {
					// Korrekturhilfe Einlesen
					scanner = new Scanner(file);
				} catch (FileNotFoundException e1) {
					System.out.println("File not found");
				}
				for (int i = 0; i <= 5; i++) {
					if (scanner.hasNextLine() == true) {
						highscoreliste[i] = scanner.nextLine();
						if (scanner.hasNextLine()) {
							scanner.nextLine();
						}
					}
				}
				scanner.close();
				score_1.setText("1. " + highscoreliste[0] + " : " + highscoreliste[1]);
				score_2.setText("2. " + highscoreliste[2] + " : " + highscoreliste[3]);
				score_3.setText("3. " + highscoreliste[4] + " : " + highscoreliste[5]);

			} catch (IOException e1) {

			}
			// falls 3 eintrag und platz 1
		} else if (highscoreliste[4] == null && summewert > Integer.parseInt(highscoreliste[1])) {
			String summwert_recent = String.valueOf(summewert);
			try {
				// Korrekturhilfe Einlesen
				filewriter = new FileWriter(file);
				filewriter.write(names);
				filewriter.write("\n\r");
				filewriter.write(summwert_recent);
				filewriter.write("\n\r");
				filewriter.write(highscoreliste[0]);
				filewriter.write("\n\r");
				filewriter.write(highscoreliste[1]);
				filewriter.write("\n\r");
				filewriter.write(highscoreliste[2]);
				filewriter.write("\n\r");
				filewriter.write(highscoreliste[3]);
				filewriter.close();
				try {
					// Korrekturhilfe Einlesen
					scanner = new Scanner(file);
				} catch (FileNotFoundException e1) {
					System.out.println("File not found");
				}
				for (int i = 0; i <= 5; i++) {
					if (scanner.hasNextLine() == true) {
						highscoreliste[i] = scanner.nextLine();
						if (scanner.hasNextLine()) {
							scanner.nextLine();
						}
					}
				}
				scanner.close();
				score_1.setText("1. " + highscoreliste[0] + " : " + highscoreliste[1]);
				score_2.setText("2. " + highscoreliste[2] + " : " + highscoreliste[3]);
				score_3.setText("3. " + highscoreliste[4] + " : " + highscoreliste[5]);

			} catch (IOException e1) {

			}
			// falls eintrag 3 und platz 2
		} else if (highscoreliste[4] == null && summewert > Integer.parseInt(highscoreliste[3])) {
			String summwert_recent = String.valueOf(summewert);
			try {
				// Korrekturhilfe Einlesen
				filewriter = new FileWriter(file);
				filewriter.write(highscoreliste[0]);
				filewriter.write("\n\r");
				filewriter.write(highscoreliste[1]);
				filewriter.write("\n\r");
				filewriter.write(names);
				filewriter.write("\n\r");
				filewriter.write(summwert_recent);
				filewriter.write("\n\r");
				filewriter.write(highscoreliste[2]);
				filewriter.write("\n\r");
				filewriter.write(highscoreliste[3]);
				filewriter.close();
				try {
					// Korrekturhilfe Einlesen
					scanner = new Scanner(file);
				} catch (FileNotFoundException e1) {
					System.out.println("File not found");
				}
				for (int i = 0; i <= 5; i++) {
					if (scanner.hasNextLine() == true) {
						highscoreliste[i] = scanner.nextLine();
						if (scanner.hasNextLine()) {
							scanner.nextLine();
						}
					}
				}
				scanner.close();
				score_1.setText("1. " + highscoreliste[0] + " : " + highscoreliste[1]);
				score_2.setText("2. " + highscoreliste[2] + " : " + highscoreliste[3]);
				score_3.setText("3. " + highscoreliste[4] + " : " + highscoreliste[5]);

			} catch (IOException e1) {

			}
		} else if (summewert > Integer.parseInt(highscoreliste[1])) {
			String summwert_recent = String.valueOf(summewert);
			try {
				// Korrekturhilfe Einlesen
				filewriter = new FileWriter(file);
				filewriter.write(names);
				filewriter.write("\n\r");
				filewriter.write(summwert_recent);
				filewriter.write("\n\r");
				filewriter.write(highscoreliste[0]);
				filewriter.write("\n\r");
				filewriter.write(highscoreliste[1]);
				filewriter.write("\n\r");
				filewriter.write(highscoreliste[2]);
				filewriter.write("\n\r");
				filewriter.write(highscoreliste[3]);
				filewriter.close();
				try {
					// Korrekturhilfe Einlesen
					scanner = new Scanner(file);
				} catch (FileNotFoundException e1) {
					System.out.println("File not found");
				}
				for (int i = 0; i <= 5; i++) {
					if (scanner.hasNextLine() == true) {
						highscoreliste[i] = scanner.nextLine();
						if (scanner.hasNextLine()) {
							scanner.nextLine();
						}
					}
				}
				scanner.close();
				score_1.setText("1. " + highscoreliste[0] + " : " + highscoreliste[1]);
				score_2.setText("2. " + highscoreliste[2] + " : " + highscoreliste[3]);
				score_3.setText("3. " + highscoreliste[4] + " : " + highscoreliste[5]);

			} catch (IOException e1) {

			}
		} else if (summewert > Integer.parseInt(highscoreliste[3])) {
			String summwert_recent = String.valueOf(summewert);
			try {
				// Korrekturhilfe Einlesen
				filewriter = new FileWriter(file);
				filewriter.write(highscoreliste[0]);
				filewriter.write("\n\r");
				filewriter.write(highscoreliste[1]);
				filewriter.write("\n\r");
				filewriter.write(names);
				filewriter.write("\n\r");
				filewriter.write(summwert_recent);
				filewriter.write("\n\r");
				filewriter.write(highscoreliste[2]);
				filewriter.write("\n\r");
				filewriter.write(highscoreliste[3]);
				filewriter.close();
				try {
					// Korrekturhilfe Einlesen
					scanner = new Scanner(file);
				} catch (FileNotFoundException e1) {
					System.out.println("File not found");
				}
				for (int i = 0; i <= 5; i++) {
					if (scanner.hasNextLine() == true) {
						highscoreliste[i] = scanner.nextLine();
						if (scanner.hasNextLine()) {
							scanner.nextLine();
						}
					}
				}
				scanner.close();
				score_1.setText("1. " + highscoreliste[0] + " : " + highscoreliste[1]);
				score_2.setText("2. " + highscoreliste[2] + " : " + highscoreliste[3]);
				score_3.setText("3. " + highscoreliste[4] + " : " + highscoreliste[5]);

			} catch (IOException e1) {

			}
		} else if (summewert > Integer.parseInt(highscoreliste[5])) {
			String summwert_recent = String.valueOf(summewert);
			try {
				// Korrekturhilfe Einlesen
				filewriter = new FileWriter(file);
				filewriter.write(highscoreliste[0]);
				filewriter.write("\n\r");
				filewriter.write(highscoreliste[1]);
				filewriter.write("\n\r");
				filewriter.write(highscoreliste[2]);
				filewriter.write("\n\r");
				filewriter.write(highscoreliste[3]);
				filewriter.write("\n\r");
				filewriter.write(names);
				filewriter.write("\n\r");
				filewriter.write(summwert_recent);
				filewriter.close();
				try {
					// Korrekturhilfe Einlesen
					scanner = new Scanner(file);
				} catch (FileNotFoundException e1) {
					System.out.println("File not found");
				}
				for (int i = 0; i <= 5; i++) {
					if (scanner.hasNextLine() == true) {
						highscoreliste[i] = scanner.nextLine();
						if (scanner.hasNextLine()) {
							scanner.nextLine();
						}
					}
				}
				scanner.close();
				score_1.setText("1. " + highscoreliste[0] + " : " + highscoreliste[1]);
				score_2.setText("2. " + highscoreliste[2] + " : " + highscoreliste[3]);
				score_3.setText("3. " + highscoreliste[4] + " : " + highscoreliste[5]);

			} catch (IOException e1) {

			}
		}
		summewert = 0;
		punkte.setText("Aktueller Score: " + summewert);
	}

	public JButton getBack() {
		return back;
	}

	public void setBack(JButton back) {
		this.back = back;
	}

	public JLabel getPunkte() {
		return punkte;
	}

	public void setPunkte(JLabel punkte) {
		this.punkte = punkte;
	}

	public int getZielwert() {
		return zielwert;
	}

	public void setZielwert(int zielwert) {
		this.zielwert = zielwert;
	}

	public int getSummewert() {
		return summewert;
	}

	public void setSummewert(int summewert) {
		this.summewert = summewert;
	}

	public JButton getLoesungsubmit() {
		return loesungsubmit;
	}

	public void setLoesungsubmit(JButton loesungsubmit) {
		this.loesungsubmit = loesungsubmit;
	}

	public int getSummegewicht() {
		return summegewicht;
	}

	public void setSummegewicht(int summegewicht) {
		this.summegewicht = summegewicht;
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

}