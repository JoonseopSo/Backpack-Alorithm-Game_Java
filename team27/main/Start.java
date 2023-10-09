package team27.main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import team27.main.RichtungenVonSteuerung.ButtonAL;

/**
 * Diese Datei nicht verschieben und diese main-Methode nutzen, um das Programm
 * zu starten.
 * 
 * @author Progprak-Team
 *
 */
public class Start extends JPanel{

	private static  Maze maze;
	private static Start panelmaze;
	private static JPanel panelcont;
	
	private static Buttonaction buttonaction;
	private static JButton einsZurueck;
	private static ButtonAL actionlistenerFuerButtons;
	private static JButton zurueckZumStart;
	private static Buttonaction buttonaction1 ;
	private static Buttonaction buttonaction2 ;
	private static Buttonaction buttonaction3 ;
	private static Buttonaction buttonaction4 ;

	
	private static CardLayout cl;
	private static int k;
	
	public Start(Maze theMaze) {
		maze = theMaze;
		setBackground(Color.white);

		setFocusable(true);
	}
	
	public static void main(String[] args) {

		JFrame myFrame = new JFrame("GoToGame");
		JPanel buttonPanel = new JPanel();
		 panelcont = new JPanel();
		
		 cl = new CardLayout();
		Spiel1 sp1 = new Spiel1(cl,buttonPanel,panelcont,5);
		Spiel2 sp2 = new Spiel2();
		Spiel3 sp3 = new Spiel3(cl, buttonPanel, panelcont);
		Spiel4 sp4 = new Spiel4(cl, buttonPanel, panelcont, 6, 3, "Training", true, 0);



		panelcont.setLayout(cl);

		buttonPanel.setBackground(Color.white);
		buttonPanel.setLayout(new GridLayout(8, 1));

		JButton anleitung = new JButton("Anleitung");
		 zurueckZumStart = new JButton(" Zurueck zum Start");
		einsZurueck = new JButton("1 Zurueck");

		JLabel label = new JLabel("Entwicklermodus: Spiele direkt starten", SwingConstants.CENTER);

		JButton spiel1 = new JButton("Spiel 1");
		JButton spiel2 = new JButton("Spiel 2");
		JButton spiel3 = new JButton("Spiel 3");
		JButton spiel4 = new JButton("Spiel 4");

		JButton spiel1zu = new JButton("Zurück");
		JButton spiel2zu = new JButton("Zurück");
		JButton spiel3zu = new JButton("Zurück");
		JButton spiel4zu = new JButton("Zurück");

		buttonPanel.add(anleitung);
		buttonPanel.add(zurueckZumStart);
		buttonPanel.add(einsZurueck);
		buttonPanel.add(label);
		buttonPanel.add(spiel1);
		buttonPanel.add(spiel2);
		buttonPanel.add(spiel3);
		buttonPanel.add(spiel4);
		

		//sp1.add(spiel1zu);
		sp2.zurueckbuttonAbholen(spiel2zu);
		//sp3.add(spiel3zu);
//		sp4.add(spiel4zu);

		myFrame.setVisible(true);
		myFrame.setPreferredSize(new Dimension(800, 600));
		myFrame.setMinimumSize(new Dimension(600, 600));
		myFrame.setLocationRelativeTo(null);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//sc = new Scanner(System.in);
		
		buttonaction = new Buttonaction(maze, panelmaze, 5, spiel4, panelcont, buttonPanel, cl);
		panelmaze= new Start(maze);
		actionlistenerFuerButtons = new ButtonAL(einsZurueck, zurueckZumStart, maze, panelmaze);
		
		
		buttonaction1 = new Buttonaction(maze, panelmaze, 1, spiel1, panelcont, buttonPanel, cl);
		buttonaction2 = new Buttonaction(maze, panelmaze, 2, spiel2, panelcont, buttonPanel, cl);
		buttonaction3 = new Buttonaction(maze, panelmaze, 3, spiel3, panelcont, buttonPanel, cl);
		buttonaction4 = new Buttonaction(maze, panelmaze, 4, spiel4, panelcont, buttonPanel, cl);
		
		mazeErzeugen(panelmaze);
		
		
		panelcont.add(sp1, "1");
		panelcont.add(sp2, "2");
		panelcont.add(sp3, "3");
		panelcont.add(sp4, "4");

		cl.show(panelcont, "mp");
		myFrame.add(panelcont, BorderLayout.CENTER);
		myFrame.add(buttonPanel, BorderLayout.EAST);
		myFrame.pack();
		
		
		// Neue Version der Steuerung
		
		//steuerung = new Steuerung(maze, panelmaze, einsZurueck, buttonaction);

		

		 
		 
		anleitung.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Spielregel spr = new Spielregel();
			}
		});
		spiel1zu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(panelcont, "mp");
				buttonPanel.setVisible(true);
			}
		});
		spiel2zu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(panelcont, "mp");
				buttonPanel.setVisible(true);
			}
		});
		spiel3zu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(panelcont, "mp");
				buttonPanel.setVisible(true);
			}
		});
		spiel4zu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(panelcont, "mp");
				buttonPanel.setVisible(true);
			}
		});
		scanner();
	}
	public static void scanner() {
		Scanner sc = new Scanner(System.in);
		
		String eingabe="0";
		
		while(eingabe.equals("1")==false&&eingabe.equals("2")==false&&eingabe.equals("3")==false&&eingabe.equals("4")==false) {
			System.out.println("Gib eine Spielnummer ein!");
			eingabe=sc.next();
			if(eingabe.equals("1")) {
				buttonaction1.action();
				sc.close();
			}else if(eingabe.equals("2")) {
				buttonaction2.action();
				sc.close();
			}else if(eingabe.equals("3")) {
				buttonaction3.action();
				sc.close();
			}else if(eingabe.equals("4")) {
				buttonaction4.action();
				sc.close();
			}
		}

	}
	
	public static void mazeErzeugen(Start s) {
		int mazeLaenge = 9;
		maze = new Maze(mazeLaenge); // builds maze
		
		panelmaze = s;

		// spiegel das Labyrith
		LabyrinthSpiegeln Lab = new LabyrinthSpiegeln();
		Lab.mirrorLabyrinth(maze);

		maze.setSchonGespiegelt(true);
		panelcont.add(panelmaze, "mp");
		cl.show(panelcont, "mp");
		Steuerung steuerung = new Steuerung(maze, panelmaze, einsZurueck, buttonaction);
		buttonaction.setM(maze);
		actionlistenerFuerButtons.setEinsZurueck(einsZurueck);
		actionlistenerFuerButtons.setAufStartfeld(zurueckZumStart);
		actionlistenerFuerButtons.setMaze(maze);
		actionlistenerFuerButtons.setPanelmaze(panelmaze);
		
		buttonaction1.setPanelVonM(panelmaze);
		buttonaction2.setPanelVonM(panelmaze);
		buttonaction3.setPanelVonM(panelmaze);
		buttonaction4.setPanelVonM(panelmaze);
		
		buttonaction1.setM(maze);
		buttonaction2.setM(maze);
		buttonaction3.setM(maze);
		buttonaction4.setM(maze);

		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		maze.draw(g, panelmaze.getHeight(), panelmaze.getWidth());
		
	}
}
