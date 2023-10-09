package team27.main;

import spiel3Klassen.Zeichnen;
import java.awt.*;
import java.awt.geom.Line2D;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;
import javax.swing.*;

public class Spiel3 extends JPanel implements MouseListener, MouseMotionListener{
	
	private JLabel punkteLabel;
	private CardLayout cl;
	private JPanel buttonPanel;
	private JPanel panelcont;
	private JLabel highScore;
	private JButton zurueck;
	private JButton start;
	private JButton beenden;
	private JButton pause;
	private JButton reset;
	private JButton loesungEinreichen;
	private JButton anleitung;
	private JPanel spiel3;
	private JComboBox dropdown;
	private JLabel schranke;
	private int anzahl=4;
	private double multiplikator=0;
	boolean startB=false;
	private JPanel panelcontSpiel;
	private Zeichnen graphenzeichen;
	private int levelInt;
	private JLabel level;
	private int punkte=0;
	private JButton buttonOK  = new JButton("OK");;
	private JFrame highscore;
	private String input;
	private JTextField tfName;	
	private Zeichnen g;
	private Line2D[] linien;
	private LinkedList line;
	private LinkedList knoten ;
	private boolean [][] adjMatrixB;
	private int x1last;
	private int x2last;
	private int x1;
	private int x2;
	private boolean pauseB;
	private Timer t;
	private JLabel timer;
	private TimerTask task;
	private int vorherPunkte=0;
	private int temp;

	private Maze maze;
	
	private LinkedList zu;
	
	private boolean go;
	private int highscore1;
	private  int highscore2;
	private int highscore3;
	private String erster;
	private String zweiter;
	private String dritter;
	private JLabel ersterL;
	private JLabel zweiterL;
	private JLabel dritterL;
	private Set<Integer>unique;
	
    private boolean pauseStartAgain;
	
	  int imageX, imageY;
	  private static Image imgE;
	  private MouseListener ml2;
	  private String selecteschwierig2;;
	  



	
	Spiel3(CardLayout cl, JPanel buttonPanel, JPanel panelcont){
		frontEnd();
		pauseStartAgain=false;
		buttonAction();
		this.cl=cl;
		this.buttonPanel=buttonPanel;
		this.panelcont=panelcont;
		pauseB=false;
		Point p = MouseInfo.getPointerInfo().getLocation();
		int x=p.x;
		int y=p.y;
		adjMatrixB= new boolean [15][15];
		for(int i=0; i<15; i++) {
			for(int j=0;j<15;j++) {
				adjMatrixB[i][j]=false;
			}
		} 
		knoten=new LinkedList();
		line= new LinkedList();

		    
		    
	}
	
	public void frontEnd() {
		JPanel buttonPanelSpiel = new JPanel();
		panelcontSpiel = new JPanel();
		spiel3= new JPanel(new BorderLayout());
		spiel3.setBackground(Color.yellow);
		JLabel textDummy= new JLabel("Klicke auf START!", SwingConstants.CENTER);
		spiel3.add(textDummy, BorderLayout.CENTER);

		GridLayout grid= new GridLayout(16,1);
		buttonPanelSpiel.setLayout(grid);
		
		//Buttons
		highScore= new JLabel("Highscore Board");
		
		ersterL= new JLabel("1. "+erster+": "+highscore1);
		zweiterL= new JLabel("2. "+zweiter+ ": " +highscore2);
		dritterL= new JLabel("3. "+dritter+": "+highscore3);
		readCSV();
		
		zurueck= new JButton("Zurueck zum Labyrinth");
		start= new JButton("Start");
		beenden= new JButton("Beenden");
		pause= new JButton("Pausieren");
		pause.setEnabled(false);
		reset= new JButton("Reset Highscore");
		loesungEinreichen= new JButton("Loesung abgeben");
		loesungEinreichen.setEnabled(false);
		anleitung= new JButton("Anleitung");
		
		//JLabels
		int levelAktuell=1;
		level= new JLabel("Level: "+Integer.toString(levelAktuell));
		timer= new JLabel("Timer: 0");
		schranke= new JLabel("Schranke: "+"");
		//int punkteaktuell=0;
		punkteLabel= new JLabel("Punkte: 0");
		
		//Dropdown
		String[] schwierigkeit= {"Training", "mittel", "schwer"};
		dropdown = new JComboBox(schwierigkeit);
		
		buttonPanelSpiel.add(anleitung,grid);
		buttonPanelSpiel.add(highScore,grid);
		buttonPanelSpiel.add(ersterL);
		buttonPanelSpiel.add(zweiterL);
		buttonPanelSpiel.add(dritterL);
		buttonPanelSpiel.add(dropdown);
		buttonPanelSpiel.add(punkteLabel);
		buttonPanelSpiel.add(level);
		buttonPanelSpiel.add(timer);
		buttonPanelSpiel.add(schranke);
		buttonPanelSpiel.add(reset,grid);
		buttonPanelSpiel.add(zurueck,grid);
		buttonPanelSpiel.add(start,grid);
		buttonPanelSpiel.add(pause,grid);
		buttonPanelSpiel.add(beenden,grid);
		buttonPanelSpiel.add(loesungEinreichen,grid);
		

		BorderLayout broderl=new BorderLayout(2,1);
		panelcontSpiel.setLayout(broderl);


	     //imgp.addMouseMotionListener();
	       
		
		
		panelcontSpiel.add(spiel3, broderl.CENTER);
		
		
		BorderLayout border= new BorderLayout();
		this.setLayout(border);
		this.add(panelcontSpiel, border.CENTER);
		this.add(buttonPanelSpiel, border.EAST);
		//DragImage i=new DragImage();
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("P"), "pause");
		getActionMap().put("pause", pauseT);
		
		
//		   ml2=new MouseAdapter() {
//			  public void mouseDragged(MouseEvent e ) {
//					System.out.print("DRAGED");
////				    imageX = e.getX();
////				    imageY = e.getY();
////				    repaint();
//				}
//				public void mouseClicked() {
//					System.out.println("CLICK");
//				}
//		  };
		

		
	
	}

	
	Action pauseT = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			reset.setEnabled(true);
			graphenzeichen.removeMouseListener(ml);
			dropdown.setEnabled(true);
			go=false;
			pause.setText("Fortsetzten");
			start.setEnabled(true);
			
			if(pauseB==true) {
				if(selecteschwierig2 != (String)dropdown.getSelectedItem()) {
					dropdown.setSelectedItem(selecteschwierig2);
				}
				graphenzeichen.addMouseListener(ml);
				go=true;
				pause.setText("Pause");
				start.setEnabled(false); 
				reset.setEnabled(false);
				dropdown.setEnabled(false);
			}
		}

	};
	
	public void buttonAction() {
		
		//boolean anleitungoffen=false;
		anleitung.addActionListener(e->{
			pause.setEnabled(false);
	    	pauseT.setEnabled(false);
			JFrame anleitungHier = new JFrame();
			anleitungHier.setLayout(new GridLayout(5, 1));
			JLabel text = new JLabel("Verbinde die Knoten des Graphen durch anklicken der Kanten", SwingConstants.CENTER);
			JLabel text2 = new JLabel("Erzeuge so einen minimalen Spannbaum mit minimalen Kosten", SwingConstants.CENTER);
			JLabel text3 = new JLabel("Die Kostenschranke variiert je nach Schwierigkeit.", SwingConstants.CENTER);
			JLabel text4 = new JLabel("Lösungen kleiner oder gleich der Schranke gelten als gültig.", SwingConstants.CENTER);
			JLabel text5 = new JLabel("Einreichen durch Loesung abgeben Button.", SwingConstants.CENTER);
			//Spiel Pausieren
			anleitungHier.add(text);
			anleitungHier.add(text2);
			anleitungHier.add(text3);
			anleitungHier.add(text4);
			anleitungHier.add(text5);
			anleitungHier.setBackground(Color.white);
			anleitungHier.setSize(600, 600);			
			anleitungHier.setMinimumSize(new Dimension(600,600));
			anleitungHier.setLocationRelativeTo(null);
			anleitungHier.setVisible(true);
			if(startB==true) {
				go=false;
				graphenzeichen.removeMouseListener(ml);
			}
			anleitungHier.addWindowListener(new WindowAdapter() {
			    public void windowClosing(WindowEvent e) {
			    	pause.setEnabled(true);
			    	pauseT.setEnabled(true);
			    	pause.setText("Fortsetzten");
			    	anleitungHier.dispose();
			    }
			});
		});
		
		start.addActionListener(e->{
			beenden.setEnabled(true);
			loesungEinreichen.setEnabled(true);
			reset.setEnabled(false);
			dropdown.setEnabled(false);
			readCSV();	

			go=true;
			if(pauseStartAgain==true) {
				task.cancel();

				task.run();
			}
			pause.setEnabled(true);
	    	pauseT.setEnabled(true);
			pause.setText("Pause");
			zurueck.setEnabled(false);
			for(int i=0; i<15; i++) {
				for(int j=0;j<15;j++) {
					adjMatrixB[i][j]=false;
				}
			} 
			knoten.clear();
			line.clear();
			level.setText("Level: "+levelInt);
			panelcontSpiel.remove(spiel3);
			start.setEnabled(false);
			
			String selecteschwierig;
			selecteschwierig = (String)dropdown.getSelectedItem();
			selecteschwierig2 = (String)dropdown.getSelectedItem();
			
			t= new Timer();
			if(selecteschwierig =="Training") {
				anzahl=4;
				multiplikator=2;
				final Integer timerI=Integer.MAX_VALUE;
				
			    task = new TimerTask( ){
			    	private int i = 0;
			        public void run(){
			            if (i <= timerI && go==true) {
			            	pauseB=false;
			            	int minuten=i/60;
			            	int sekunden= i % 60;
			                timer.setText("Timer: "+minuten+" min. " + sekunden+ " sec.");
			                i++;
			                if(graphenzeichen!=null&&graphenzeichen.isAbgabegueltig()==true) {
			                	einreichen();
			                }

			            }else if(i <= timerI && go==false) {
			            	pauseB=true;
			            }
			            else {
			            	t.cancel();
			            	go=false;
			            	i=0;
			            	gameOver();
			            	
			            }
			        }
			    };
			    t.scheduleAtFixedRate(task, 0, 1000); //1000ms = 1s
			}
			else if(selecteschwierig =="mittel") {
				anzahl=8;
				multiplikator=1.5;
				 task = new TimerTask(){
			    	private int i = 60;
			        public void run(){
			        	//System.out.println("TimerTask mittel run methode");
			            if (i >= 0&& go==true) {
			            	pauseB=false;
			            	//System.out.println("IN If von mittel timer"+i);
			            	int minuten=i/60;
			            	int sekunden= i % 60;
			                timer.setText("Timer: "+minuten+" min. " + sekunden+ " sec.");
			                i--;
			                if(graphenzeichen!=null&&graphenzeichen.isAbgabegueltig()==true) {
			                	einreichen();
			                }

			            }else if(i >=0  && go==false) {
			            	pauseB=true;
			            }
			            else {
			            	t.cancel();
			            	go=false;
			            	i=60;
			            	gameOver();
			            	System.out.println("Timer canceld 60 ");
			            }
			        }
			    };
			    t.scheduleAtFixedRate(task, 0, 1000); //1000ms = 1sec
			}
			else{
				anzahl=12;
				multiplikator=1.2;
				
			    task = new TimerTask(){
			    	private int i = 30;
			        public void run(){
			            if (i >=0&&go==true) {
			            	pauseB=false;
			            	int minuten=i/60;
			            	int sekunden= i % 60;
			                timer.setText("Timer: "+minuten+" min. " + sekunden+ " sec.");
			                i--; 
			                if(graphenzeichen!=null&&graphenzeichen.isAbgabegueltig()==true) {
			                	einreichen();
			                }

			            }else if(i >=0  && go==false) {
			            	pauseB=true;
			            }
			            else {
			            	t.cancel();
			            	go=false;
			            	i=30;
			            	gameOver();
			            	repaint();
			            	//System.out.println("Timer canceld 30");
			            }
			        }

			    };
			    t.scheduleAtFixedRate(task, 0, 1000); //1000ms = 1sec 
			}
			if(startB==true) {
				panelcontSpiel.remove(graphenzeichen);
				//t.cancel();
				//timer.setText("Timer: 0" );
			}
			if(pauseB==true) {
				pauseB=false;
				pause.setEnabled(true);
		    	pauseT.setEnabled(true);
				graphenzeichen= new Zeichnen(anzahl);
				schranke.setText("Schranke "+ Math.ceil((double)graphenzeichen.getSchranke()*multiplikator));
			}else {
				graphenzeichen= new Zeichnen(anzahl);
				schranke.setText("Schranke "+ Math.ceil((double)graphenzeichen.getSchranke()*multiplikator));
			}
			
			
			panelcontSpiel.add(graphenzeichen);
			startB=true;
			repaint();
			graphenzeichen.addMouseListener(ml);
			punkteLabel.setText("Punkte: 0");
			
			
			//String selecteschwierig2;
			//selecteschwierig2 = (String)dropdown.getSelectedItem();
			//panelcontSpiel.add(imgp);
			//imgp.setLayout(null);

			//repaint();
			
//			try {
//				imgE = ImageIO.read(getClass().getResource("/resources/extrarohr.jpg"));
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}
//			
//			JLabel imgE= new JLabel(new ImageIcon(getClass().getResource("/resources/extrarohr.jpg")));
//			imgE.setBorder(new LineBorder(Color.BLACK, 2));
//			imgE.setPreferredSize(new Dimension(50,50));
//			imgE.setVisible(true);
//			graphenzeichen.add(imgE);
//			imgE.setTransferHandler(new TransferHandler("TEXT"));
//			
//			imgE.addMouseListener(ml2);
			
		});
		beenden.addActionListener(e->{
			start.setEnabled(false);
			pause.setEnabled(false);
			reset.setEnabled(true);
			loesungEinreichen.setEnabled(false);
	    	pauseT.setEnabled(false);
			dropdown.setEnabled(false);
			if(startB==true) {
				graphenzeichen.removeMouseListener(ml);
				graphenzeichen.setShowOpt(true);
				boolean tempBB=openHighscoreEingabe();
				if(tempBB==false) {
					gameOver();
					repaint();
				}
				task.cancel();
				levelInt=0;
				punkte=0;
				level.setText("Level: "+levelInt);
				graphenzeichen.setShowOpt(true);
				repaint();
			}
			zurueck.setEnabled(true);
			repaint();
			beenden.setEnabled(false);

			//startB=false;

		});
		pause.addActionListener(e->{
			pauseStartAgain=true;
			reset.setEnabled(true);
			graphenzeichen.removeMouseListener(ml);
			dropdown.setEnabled(true);
			go=false;
			pause.setText("Fortsetzten");
			start.setEnabled(true);
			
	
			if(pauseB==true) {
				//System.out.println((String)dropdown.getSelectedItem());
				if(selecteschwierig2 != (String)dropdown.getSelectedItem()) {
					dropdown.setSelectedItem(selecteschwierig2);
				}
				
				pauseStartAgain=false;
				graphenzeichen.addMouseListener(ml);
				go=true;
				pause.setText("Pause");
				start.setEnabled(false);
				reset.setEnabled(false);
				dropdown.setEnabled(false);
			}

		});
		dropdown.addActionListener(e->{
			
		});
		reset.addActionListener(e->{
			resetCSV();
			readCSV();
			invalidate();
			repaint();
		});
		loesungEinreichen.addActionListener(e->{
			t.cancel();
			graphenzeichen.setShowOpt(true);
			//repaint();
			if(unique!=null) {
				punkte=unique.size();
				if(unique.size()==anzahl) {
					vorherPunkte=vorherPunkte+unique.size();
				}else {
					vorherPunkte=vorherPunkte-unique.size();
				}
			}

			if(graphenzeichen.getSumme()<(double)graphenzeichen.getSchranke()*multiplikator&&punkte==anzahl&&go==true) {
				
				punkte=0;
				levelInt++;
				beenden.setEnabled(true);
				loesungEinreichen.setEnabled(true);
				reset.setEnabled(false);
				dropdown.setEnabled(false);
				readCSV();
				
				go=true;
				pause.setEnabled(true);
		    	pauseT.setEnabled(true);
				pause.setText("Pause");
				zurueck.setEnabled(false);
				for(int i=0; i<15; i++) {
					for(int j=0;j<15;j++) {
						adjMatrixB[i][j]=false;
					}
				} 
				knoten.clear();
				line.clear();
				//level.setText("Level: "+levelInt);
				//panelcontSpiel.remove(spiel3);
				start.setEnabled(false);
				
				String selecteschwierig;
				selecteschwierig = (String)dropdown.getSelectedItem();
				
				t= new Timer();
				if(selecteschwierig =="Training") {
					anzahl=4;
					multiplikator=2;
					final Integer timerI=Integer.MAX_VALUE;
					
				    task = new TimerTask( ){
				    	private int i = 0;
				        public void run(){
				            if (i <= timerI && go==true) {
				            	pauseB=false;
				                timer.setText("Timer: " + i++);
				                if(graphenzeichen!=null&&graphenzeichen.isAbgabegueltig()==true) {
				                	einreichen();
				                }

				            }else if(i <= timerI && go==false) {
				            	pauseB=true;
				            }
				            else {
				            	t.cancel();
				            	go=false;
				            	i=0;
				            	gameOver();
				            }
				        }
				    };
				    t.scheduleAtFixedRate(task, 0, 1000); //1000ms = 1s
				}
				else if(selecteschwierig =="mittel") {
					anzahl=8;
					multiplikator=1.5;
					 task = new TimerTask(){
				    	private int i = 60;
				        public void run(){
				            if (i >= 0&& go==true) {
				            	pauseB=false;
				                timer.setText("Timer: " + i--); 
				                if(graphenzeichen!=null&&graphenzeichen.isAbgabegueltig()==true) {
				                	einreichen();
				                }
				                
				            }else if(i >=0  && go==false) {
				            	pauseB=true;
				            }
				            else {
				            	t.cancel();
				            	go=false;
				            	i=60;
				            	gameOver();
				            }
				        }
				    };
				    t.scheduleAtFixedRate(task, 0, 1000); //1000ms = 1sec
				}
				else{
					anzahl=12;
					multiplikator=1.2;
					
				    task = new TimerTask(){
				    	private int i = 30;
				        public void run(){
				            if (i >=0&&go==true) {
				            	pauseB=false;
				                timer.setText("Timer: " + i--);  
				                if(graphenzeichen!=null&&graphenzeichen.isAbgabegueltig()==true) {
				                	einreichen();
				                }

				            }else if(i >=0  && go==false) {
				            	pauseB=true;
				            }
				            else {
				            	t.cancel();
				            	//System.out.println("Timer canceld");
				            	go=false;
				            	i=30;
				            	gameOver();
				            }
				        }

				    };
				    t.scheduleAtFixedRate(task, 0, 1000); //1000ms = 1sec 
				}

					panelcontSpiel.remove(graphenzeichen);

				if(pauseB==true) {
					pauseB=false;
					pause.setEnabled(true);
			    	pauseT.setEnabled(true);
					graphenzeichen= new Zeichnen(anzahl);
					schranke.setText("Schranke "+ Math.ceil((double)graphenzeichen.getSchranke()*multiplikator));
				}else {
					graphenzeichen= new Zeichnen(anzahl);
					schranke.setText("Schranke "+ Math.ceil((double)graphenzeichen.getSchranke()*multiplikator));
				}
				panelcontSpiel.add(graphenzeichen);
				//punkteLabel.setText("Punkte: 0");
				//startB=true;
				repaint();
				graphenzeichen.addMouseListener(ml);
				level.setText("Level: "+ levelInt);
				//task.run();
			}else {
				graphenzeichen.setGameOver(true);
				graphenzeichen.removeMouseListener(ml);
			}
			
			
			//start.setEnabled(true); implement restart automatisch
			//task.cancel();
			//timer.setText("Timer: 0" );
			loesungEinreichen.setEnabled(false);
			//einreichen();
		});
	    buttonOK.addActionListener(e->{
	    	zurueck.setEnabled(true);
	    	input=  tfName.getText();
	    	
	    	highscore.dispose();
	    	proveOverrride();
			writeCSV();
	    	readCSV();
	    	reset.setEnabled(true);
	    	
	    	loesungEinreichen.setEnabled(false);
	    });
		zurueck.addActionListener(e->{
			reset.setEnabled(true);
			dropdown.setEnabled(true);
			cl.show(panelcont, "mp");
			buttonPanel.setVisible(true);
			if(startB==false) {
				start.setEnabled(true);
			}else {
				panelcontSpiel.remove(graphenzeichen);
				panelcontSpiel.add(spiel3);
				levelInt=1;
				task.cancel();
				graphenzeichen.setKnotenBfalse();
			}
			knoten.clear();
			line.clear();
			start.setEnabled(true);
			levelInt=0;
			punkteLabel.setText("Punkte: 0");
			level.setText("Level: "+levelInt);
			punkte=0;
			vorherPunkte=0;
			timer.setText("Timer: 0" );
			
			Start s= new Start(maze);
			s.mazeErzeugen(s);
			startB=false;
			s.repaint();
		});
	}
	
	public void gameOver() {
		graphenzeichen.setGameOver(true);
		graphenzeichen.removeMouseListener(ml);
		repaint();
	}
	
	public boolean openHighscoreEingabe() {

			if(vorherPunkte>= highscore3||vorherPunkte>=highscore2||vorherPunkte>=highscore3) {
				highscore = new JFrame();
				highscore.setLayout(new GridLayout(4, 1));
				JLabel text = new JLabel("Tippe deinen Namen", SwingConstants.CENTER);
				
				highscore.setBackground(Color.white);
				highscore.setSize(300, 200);			
				highscore.setMinimumSize(new Dimension(300,200));
				highscore.setLocationRelativeTo(null);
				highscore.setVisible(true);
				tfName = new JTextField("Max Mustermann");
			   
				tfName.setForeground(Color.GRAY);

				highscore.setLayout(new GridLayout(3, 1));
				highscore.add(text);
				highscore.add(tfName);

			    highscore.add(buttonOK);
			    zurueck.setEnabled(true);
			    return true;
			}
			return false;
	}
	
	public int proveOverrride() {
		if(unique.size()==anzahl) {
			
		}else {
			temp=temp-unique.size();
		}
		
		if(temp>=highscore1) {
			String tempS=erster;
			int tempI= highscore1;
			highscore1=temp;
			erster=input;
			dritter=zweiter;
			highscore3=highscore2;
			zweiter=tempS;
			highscore2=tempI;
			return 1;
		}else if(temp>=highscore2) {
			int tempI=highscore2;
			String tempS=zweiter;
			highscore2=temp;
			zweiter=input;
			dritter=tempS;
			highscore3=tempI;
			return 2;
		}else if(temp>=highscore3) {
			highscore3=temp;
			dritter=input;
			return 3;
		}else {
			return 0;
		}
	}
	public void einreichen() {
		graphenzeichen.setAbgabegueltig(false);
		
		t.cancel();
		graphenzeichen.setShowOpt(true);
		repaint();
		if(unique!=null) {
			punkte=unique.size();
			if(unique.size()==anzahl) {
				vorherPunkte=vorherPunkte+unique.size();
			}else {
				vorherPunkte=vorherPunkte-unique.size();
			}

		}

		if(graphenzeichen.getSumme()<(double)graphenzeichen.getSchranke()*multiplikator&&punkte==anzahl&&go==true) {
			
			punkte=0;
			levelInt++;
			beenden.setEnabled(true);
			loesungEinreichen.setEnabled(true);
			reset.setEnabled(false);
			dropdown.setEnabled(false);
			readCSV();
			
			go=true;
			pause.setEnabled(true);
	    	pauseT.setEnabled(true);
			pause.setText("Pause");
			zurueck.setEnabled(false);
			for(int i=0; i<15; i++) {
				for(int j=0;j<15;j++) {
					adjMatrixB[i][j]=false;
				}
			} 
			knoten.clear();
			line.clear();
			//level.setText("Level: "+levelInt);
			//panelcontSpiel.remove(spiel3);
			start.setEnabled(false);
			
			String selecteschwierig;
			selecteschwierig = (String)dropdown.getSelectedItem();
			
			t= new Timer();
			if(selecteschwierig =="Training") {
				anzahl=4;
				multiplikator=2;
				final Integer timerI=Integer.MAX_VALUE;
				
			    task = new TimerTask( ){
			    	private int i = 0;
			        public void run(){
			            if (i <= timerI && go==true) {
			            	pauseB=false;
			                timer.setText("Timer: " + i++);
			                if(graphenzeichen!=null&&graphenzeichen.isAbgabegueltig()==true) {
			                	einreichen();
			                }
			            }else if(i <= timerI && go==false) {
			            	pauseB=true;
			            }
			            else {
			            	t.cancel();
			            	go=false;
			            	i=0;
			            	gameOver();
			            }
			        }
			    };
			    t.scheduleAtFixedRate(task, 0, 1000); //1000ms = 1s
			}
			else if(selecteschwierig =="mittel") {
				anzahl=8;
				multiplikator=1.5;
				 task = new TimerTask(){
			    	private int i = 60;
			        public void run(){
			            if (i >= 0&& go==true) {
			            	pauseB=false;
			                timer.setText("Timer: " + i--); 
			                if(graphenzeichen!=null&&graphenzeichen.isAbgabegueltig()==true) {
			                	einreichen();
			                }
			            }else if(i >=0  && go==false) {
			            	pauseB=true;
			            }
			            else {
			            	t.cancel();
			            	go=false;
			            	i=60;
			            	gameOver();
			            }
			        }
			    };
			    t.scheduleAtFixedRate(task, 0, 1000); //1000ms = 1sec
			}
			else{
				anzahl=12;
				multiplikator=1.2;
				
			    task = new TimerTask(){
			    	private int i = 30;
			        public void run(){
			            if (i >=0&&go==true) {
			            	pauseB=false;
			                timer.setText("Timer: " + i--);  
			                if(graphenzeichen!=null&&graphenzeichen.isAbgabegueltig()==true) {
			                	einreichen();
			                }
			            }else if(i >=0  && go==false) {
			            	pauseB=true;
			            }
			            else {
			            	t.cancel();
			            	//System.out.println("Timer canceld");
			            	go=false;
			            	i=30;
			            	gameOver();
			            }
			        }

			    };
			    t.scheduleAtFixedRate(task, 0, 1000); //1000ms = 1sec 
			}

				panelcontSpiel.remove(graphenzeichen);

			if(pauseB==true) {
				pauseB=false;
				pause.setEnabled(true);
		    	pauseT.setEnabled(true);
				graphenzeichen= new Zeichnen(anzahl);
				schranke.setText("Schranke "+ Math.ceil((double)graphenzeichen.getSchranke()*multiplikator));
			}else {
				graphenzeichen= new Zeichnen(anzahl);
				schranke.setText("Schranke "+ Math.ceil((double)graphenzeichen.getSchranke()*multiplikator));
			}
			panelcontSpiel.add(graphenzeichen);
			//punkteLabel.setText("Punkte: 0");
			//startB=true;
			repaint();
			graphenzeichen.addMouseListener(ml);
			level.setText("Level: "+ levelInt);
			//task.run();
		}else {
			graphenzeichen.setGameOver(true);
			graphenzeichen.removeMouseListener(ml);
		}
		
		
		//start.setEnabled(true); implement restart automatisch
		//task.cancel();
		//timer.setText("Timer: 0" );
		loesungEinreichen.setEnabled(false);
		
	}
	public boolean getStartB() {
		return startB;
	}

	public void setStartB(boolean startB) {
		this.startB = startB;
	}
	
	public void readCSV() {
        
        String [] splittet= new String[6];
        
        //Korrekturhilfe Einlesen
        String path="src/resources/highscoreData.txt";
        int count=0;
        try 
        {
            Scanner scan = new Scanner(new FileReader(path));
            String data;  
            while(scan.hasNextLine())
            {	if(count==0) {
            		data=scan.next();
            		splittet=data.split(";");
            		highscore1= Integer.valueOf(splittet[1]);
            		erster=splittet[0];
            	}
            	if(count==1) {
            		data=scan.next();
            		splittet=data.split(";");
                	highscore2=Integer.valueOf(splittet[1]);
                	zweiter=splittet[0];
            	}
            	if(count==2) {
            		data=scan.next();
            		splittet=data.split(";");
                	highscore3=Integer.valueOf(splittet[1]);
                	dritter=splittet[0];
            	}
            	count++;
            }
            scan.close();
            count=0;
        } catch(Exception e) {
        }
    	
    	//Sortieren
    	if(highscore1<highscore2) {
    		int tempI;
    		String tempS;
    		tempI=highscore2;
    		tempS= zweiter;
    		highscore2=highscore1;
    		zweiter=erster;
    		highscore1=tempI;
    		erster=tempS;
    		
    	}if(highscore1<highscore3){
    		int tempI;
    		String tempS;
    		tempI=highscore3;
    		tempS= dritter;
    		highscore3=highscore1;
    		dritter=erster;
    		highscore1=tempI;
    		erster=tempS;
    		
    	}if(highscore2<highscore3){
    		int tempI;
    		String tempS;
    		tempI=highscore3;
    		tempS= dritter;
    		highscore3=highscore2;
    		dritter=zweiter;
    		highscore2=tempI;
    		zweiter=tempS;
    	}
    	updateHighscore();
	}
	public void writeCSV() {
		PrintWriter w;
		try {
			
			//Korrekturhilfe Einlesen
			w = new PrintWriter(new FileWriter("src/resources/highscoreData.txt"));
			w.write(erster+";"+highscore1+"\n"); 
			w.write(zweiter+";"+highscore2+"\n"); 
			w.write(dritter+";"+highscore3);
			w.flush();
			w.close();
		} catch (IOException e) {}

	}
	public void resetCSV() {
		try {

			//Korrekturhilfe Einlesen
			PrintWriter wr = new PrintWriter(new PrintWriter("src/resources/highscoreData.txt"));
			 wr.print("-;0\n");
			 wr.print("-;0\n");
			 wr.print("-;0");

			 wr.flush();
			 wr.close();
			 wr.flush();
			
			} catch ( IOException ioex ) {}

		readCSV();
		updateHighscore();
		buttonPanel.repaint();
	}
	public void updateHighscore() {
		ersterL.setText("1. "+erster+": "+highscore1);
		zweiterL.setText("2. "+zweiter+ ": " +highscore2);
		dritterL.setText("3. "+dritter+": "+highscore3);
	}
	
	
	public void usedlines() {
		line= new LinkedList();
		
		for(int i=0;i<graphenzeichen.getArray().length;i++) {
			if(graphenzeichen.getArray()[i]!=null) {
				line.add(graphenzeichen.getArray()[i]);
			}
		}
	}
	
	public void addLine(int index) {
		if(graphenzeichen.getKnotenBIndex(index)==false) {
			if(connectedRed(index)==true) {
				graphenzeichen.getKnotenIndex(index);
				graphenzeichen.setKnotenBIndex(true, index);
				knoten.add(graphenzeichen.getKnoten()[index][0]);
				knoten.add(graphenzeichen.getKnoten()[index][1]);
			}
		}else {
			if(removeLine(index)==true) {
				graphenzeichen.setKnotenBIndex(false, index);
			}
		}
	}
	
	public boolean removeLine(int index) {
		int x1remove=graphenzeichen.getKnoten()[index][0];
		int x2remove =graphenzeichen.getKnoten()[index][1];
		if((x1remove== x1last&& x2remove==x2last)||(x1remove== x2last&& x2remove== x1last)) {
			adjMatrixB[x1][x2]=false;
			adjMatrixB[x2][x1]=false;
			knoten.remove(Integer.valueOf(x1));
			knoten.remove(Integer.valueOf(x2));
			return true;
		}
		return false;
	}
	
	public boolean connectedRed(int index) {
		int first= (int) graphenzeichen.getGraph().getAusgesucht().getFirst();

		x1=graphenzeichen.getKnoten()[index][0]; //knoten der jeweiligen geklickten Linie
		x2=graphenzeichen.getKnoten()[index][1];
		
		if(x1==first ||x2==first) {
			adjMatrixB[x1][x2]=true;
			adjMatrixB[x2][x1]=true;
			x1last=x1;
			x2last=x2;
			return true;
		}else{
			for(int j=0; j<15;j++) {
				if((adjMatrixB[x1][j])) {
					adjMatrixB[x1][x2]=true;
					adjMatrixB[x2][x1]=true;
					x1last=x1;
					x2last=x2;
					return true;
				}
				if((adjMatrixB[x2][j])) {
					adjMatrixB[x1][x2]=true;
					adjMatrixB[x2][x1]=true;
					x1last=x1;
					x2last=x2;
					return true;
				}
			}
		}
		return false;
	}
	
	
	MouseListener ml=new MouseAdapter() {
	@Override
	public void mouseClicked(MouseEvent e) {
		loesungEinreichen.setEnabled(true);
		Point p=e.getPoint();
		Rectangle rec= new Rectangle(5,5);
		rec.setFrameFromCenter(p.x,p.y,p.x+5/2,p.y+5/2);
		
		if(startB==true) {
			usedlines();
			for(Object l: line) {
				if(l!=null&& ((Line2D) l).intersects(rec)) {
					//System.out.println("Linie Geklickt"+ l);
					for(int i=0; i<graphenzeichen.getArray().length; i++) {
						if(l==graphenzeichen.getArrayIndex(i)){
							
							addLine(i);

						}
					}
				}
			}			
		}
		unique = new HashSet<Integer>(knoten);
		int punkte=unique.size();
		//updatePunkte(punkte);
		//this.punkte=unique.size();
		//int temp= punkte+this.punkte;
		 temp=punkte+vorherPunkte;
		punkteLabel.setText("Punkte: "+temp);
		
		invalidate();
		repaint();
	}};
	public void updatePunkte(int p) {
		punkte=p;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	
	public void updateLabel() {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("Mouse Dragged");
//	    imageX = e.getX();
//	    imageY = e.getY();
//	    imgp.setImageX(imageX);
//	    imgp.setImagey(imageY);
	    repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("Mouse ;oved");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	} 
}
