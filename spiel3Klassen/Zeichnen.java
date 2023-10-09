package spiel3Klassen;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;



public class Zeichnen extends JPanel implements MouseListener, MouseMotionListener{
	private Grapherzeugen graph;
	private int knotenanzahl;
	private boolean clicked;
	private Rectangle sensor= new Rectangle(20,20);
	private int summe=0;
	private boolean gameOver=false;
	
	private Line2D linie0_2;
	private Line2D linie2_6;
	private Line2D linie6_9;
	private Line2D linie9_14;
	private Line2D linie14_8;
	private Line2D linie8_4;
	private Line2D linie13_4;
	private Line2D linie13_3;
	private Line2D linie8_7;
	private Line2D linie7_14;
	private Line2D linie7_9;
	private Line2D linie7_6;
	private Line2D linie7_11;
	private Line2D linie7_5;
	private Line2D linie7_10;
	private Line2D linie7_4;
	private Line2D linie4_10;
	private Line2D linie13_10;
	private Line2D linie10_3;
	private Line2D linie3_1;
	private Line2D linie1_10;
	private Line2D linie1_12;
	private Line2D linie12_10;
	private Line2D linie10_5;
	private Line2D linie5_12;
	private Line2D linie5_11;
	private Line2D linie0_5;
	private Line2D linie12_0;
	private Line2D linie0_11;
	private Line2D linie2_11;
	private Line2D linie11_6;
	private Line2D linie2_12;
	private Line2D linie2_9;
	private Line2D linie6_14;
	private Line2D linie9_8;
	private Line2D linie8_10;
	private Line2D linie13_8;
	private Line2D linie4_3;
	private Line2D linie13_1;
	private Line2D linie3_12;
	private Line2D linie0_1;
	private Line2D linie12_11;
	private Line2D linie6_5;
	private Line2D linie9_11;
	private Line2D linie0_6;
	private Line2D linie4_12;
	private Line2D linie8_9;
	private Line2D linie8_5;
	private Line2D linie5_4;
	private Line2D[] a;
	private int [][]knoten;
	private boolean[] knotenB= new boolean[49];
	private int knotenAnzahl;
	private Set<Integer> unique;
    private int groesse;
    private static Image imgL;
    private static Image imgE;
    private int optimal=0;
    private boolean showOpt=false;
    
    private int ximageL=50;
    private int  yImageL=15;
    private  int imageX, imageY;

    private  Rectangle2D.Double rec;
      
     private int rohrx,rohry;
     private boolean getroffen;
     private boolean abgabegueltig;
    
	public Zeichnen(int knotenanzahl) {
		graph= new Grapherzeugen(knotenanzahl);
		this.knotenanzahl=knotenanzahl;
		for(int i=0; i< knotenB.length; i++) {
			knotenB[i]=false;
		}	
		
		   ximageL=50;
		    yImageL=15;
		    
			rohrx=20;
			rohry=20;
			
	}
	
	public void extraRohr(Graphics g) {
		
		try {
			imgL = ImageIO.read(getClass().getResource("/resources/lueftung.jpg"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//g.drawImage(imgK, 50, 15, 40, 40,null);
		try {
			imgE = ImageIO.read(getClass().getResource("/resources/extrarohr.jpg"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//JLabel imgE= new JLabel(new ImageIcon("/resources/extrarohr.jpg"));
		//this.add(imgE);
		//imgE.setTransferHandler(new TransferHandler("TEXT"));
		//imgE.addMouseListener(new MouseAdapter() {
//			public void mouseDragged(MouseEvent e ) {
//			    imageX = e.getX();
//			    imageY = e.getY();
//			    System.out.println("DRAGED");
//			    repaint();
//			}
//			public void mouseClicked() {
//				System.out.println("CLICK");
//			}
		//});
		//g.drawImage(imgE, 50, 60, 50, 40,null);
		
		g.drawImage(imgL, ximageL, yImageL, 40, 40,null);

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
	
	Action oben = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			yImageL=yImageL-10;
			repaint();
			Rectangle2D img= new Rectangle2D.Double(ximageL, yImageL, 40,40);
			
			if(getroffen==true&&img.intersects(rec)) {
				//System.out.println("getroffen");
				abgabegueltig=true;
			}
		}

	};
	Action unten = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			yImageL=yImageL+10;
			repaint();
			Rectangle2D img= new Rectangle2D.Double(ximageL, yImageL, 40,40);
			
			if(getroffen==true&&img.intersects(rec)) {
				//System.out.println("getroffen");
				abgabegueltig=true;
			}
		}
	};
	Action links = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			ximageL=ximageL-10;
			repaint();
			Rectangle2D img= new Rectangle2D.Double(ximageL, yImageL, 40,40);
			
			if(getroffen==true&&img.intersects(rec)) {
				//System.out.println("getroffen");
				abgabegueltig=true;
			}
		}
	};
	Action rechts = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			ximageL=ximageL+10;
			repaint();
			Rectangle2D img= new Rectangle2D.Double(ximageL, yImageL, 40,40);
			
			if(getroffen==true&&img.intersects(rec)) {
				//System.out.println("getroffen");
				abgabegueltig=true;
			}
		}
	};
	

	
	
	public static Image getImgL() {
		return imgL;
	}
	public static Image getImgK() {
		return imgE;
	}
	
	@Override
	public void paint(Graphics g) {
		

		Graphics2D g2 = (Graphics2D) g;
		super.paint(g2);

		int height= this.getHeight();
		int width= this.getWidth();
		
		extraRohr(g2);
		//dragCO();
		Timer t = new Timer();
		//g2.drawImage(bild, 20,20, null);
		 rec=  new Rectangle2D.Double(rohrx,rohry,30,20);
		
		 g2.draw(rec);
		
		
		
		TimerTask task = new TimerTask( ){
	    	private int i = 0;
	        public void run(){
	            if (i<5) {
	            	i++;
	            }
	            else {
	            	t.cancel();
	            	showOpt=false;
	            	gameOver=false;
	            	repaint();
	            }
	        }
	    };
	    t.scheduleAtFixedRate(task, 0, 3000);
		
		if(showOpt==true) {
			task.run();
			g2.drawString("Optimale Lösung: "+graph.getSchranke(), width/2,height/12);
			if(gameOver==true) {
				g2.drawString("GAMEOVER", width/2,height/2);
			}
		}
		
		g2.setColor(Color.black);
		LinkedList knotenElement=new LinkedList();
		//knotenAnzahl=1;
			int first= (int) graph.getAusgesucht().getFirst();
			
			//Draw first
			g2.setColor(Color.red);
			if(first==0) {
				knotenElement.add(0);
				g2.fillOval(width/5*3, height/5, 10,10);
			}
			if(first==1) {
				knotenElement.add(1);
				g2.fillOval(width/5, height/5*3, 10,10);
			}
			if(first==2) {
				knotenElement.add(2);
				g2.fillOval(width-25, height/5, 10,10);
			}
			if(first==3) {
				knotenElement.add(3);
				g2.fillOval(width/5, height/5*4, 10,10);
			}
			if(first==4) {
				knotenElement.add(4);
				g2.fillOval(width/5*2, height-30, 10,10);
			}
			if(first==5) {
				knotenElement.add(5);
				g2.fillOval(width/5*3, height/5*3,10,10);
			}
			if(first==6) {
				knotenElement.add(6);
				g2.fillOval(width-25, height/5*3,10,10);
			}
			if(first==7) {
				knotenElement.add(7);
				g2.fillOval(width/5*4, height/5*4, 10,10);
			}
			if(first==8) {
				knotenElement.add(8);
				g2.fillOval(width/5*4, height-35, 10,10);
			}
			if(first==9) {
				knotenElement.add(9);
				g2.fillOval(width-23, height/5*4, 10,10);
			}
			if(first==10) {
				knotenElement.add(10);
				g2.fillOval(width/5*2, height/5*4, 10,10);
			}
			if(first==11) {
				knotenElement.add(11);
				g2.fillOval(width/5*4, height/5*2, 10,10);
			}
			if(first==12) {
				knotenElement.add(12);
				g2.fillOval(width/5*2, height/5*2, 10,10);
			}
			if(first==13) {
				knotenElement.add(13);
				g2.fillOval(width/5, height-30, 10,10 );
			}
			if(first==14) {
				knotenElement.add(14);
				g2.fillOval(width-25, height-25,10, 10);
			}
			
			g2.setColor(Color.BLACK);
			
			//draw_ knoten
			if(graph.getAusgesucht().contains(0)) {
				g2.drawOval(width/5*3, height/5, 10,10);
			}
			if(graph.getAusgesucht().contains(1)) {
				g2.drawOval(width/5, height/5*3, 10,10);
			}
			if(graph.getAusgesucht().contains(2)) {
				g2.drawOval(width-25, height/5, 10,10);
			}
			if(graph.getAusgesucht().contains(3)) {
				g2.drawOval(width/5, height/5*4, 10,10);
			}
			if(graph.getAusgesucht().contains(4)) {
				g2.drawOval(width/5*2, height-30, 10,10);
			}
			if(graph.getAusgesucht().contains(5)) {
				g2.drawOval(width/5*3, height/5*3,10,10);
			}
			if(graph.getAusgesucht().contains(6)) {
				g2.drawOval(width-25, height/5*3,10,10);
			}
			if(graph.getAusgesucht().contains(7)) {
				g2.drawOval(width/5*4, height/5*4, 10,10);
			}
			if(graph.getAusgesucht().contains(8)) {
				g2.drawOval(width/5*4, height-35, 10,10);
			}
			if(graph.getAusgesucht().contains(9)) {
				g2.drawOval(width-23, height/5*4, 10,10);
			}
			if(graph.getAusgesucht().contains(10)) {
				g2.drawOval(width/5*2, height/5*4, 10,10);
			}
			if(graph.getAusgesucht().contains(11)) {
				g2.drawOval(width/5*4, height/5*2, 10,10);
			}
			if(graph.getAusgesucht().contains(12)) {
				g2.drawOval(width/5*2, height/5*2, 10,10);
			}
			if(graph.getAusgesucht().contains(13)) {
				g2.drawOval(width/5, height-30, 10,10 );
			}
			if(graph.getAusgesucht().contains(14)) {
				g2.drawOval(width-25, height-25,10, 10);
			}
			summe=0;
			//Kanten Zeichnen
			if(graph.getAdjMatrixIndex(0,2)!=0) {
				if(knotenB[0]) {
					knotenElement.add(0);
					knotenElement.add(2);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(0,2);
				}
				 linie0_2 = new Line2D.Double(width/5*3, height/5, width-25, height/5);
				g2.draw(linie0_2);
				int wert=graph.getAdjMatrixIndex(0, 2);
				String s=Integer.toString(wert);
				g2.drawString(s, width/5*3+width/10, height/5);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(2,6)!=0) {
				if(knotenB[1]) {
					knotenElement.add(6);
					knotenElement.add(2);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(2,6);
				}
				linie2_6=new Line2D.Double(width-25, height/5, width-25, height/5*3);
				g2.draw(linie2_6);
				//g.drawLine(width-25, height/5, width-25, height/5*3);
				int wert=graph.getAdjMatrixIndex(2, 6);
				String s=Integer.toString(wert);
				g2.drawString(s, width-25,  height/5+height/10);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(6,9)!=0) {
				if(knotenB[2]) {
					knotenElement.add(6);
					knotenElement.add(9);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(6,9);
				}
				linie6_9= new Line2D.Double(width-25, height/5*3, width-23, height/5*4);
				g2.draw(linie6_9);
				//g.drawLine(width-25, height/5*3, width-23, height/5*4);
				int wert=graph.getAdjMatrixIndex(6, 9);
				String s=Integer.toString(wert);
				g2.drawString(s, width-25,  height/5*3+height/10);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(9,14)!=0) {
				if(knotenB[3]) {
					knotenElement.add(9);
					knotenElement.add(14);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(9,14);
				}
				linie9_14= new Line2D.Double(width-23, height/5*4, width-25, height-25);
				g2.draw(linie9_14);
				//g.drawLine(width-23, height/5*4, width-25, height-25);
				int wert=graph.getAdjMatrixIndex(9, 14);
				String s=Integer.toString(wert);
				g2.drawString(s,width-23,height/5*4+height/10);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(14,8)!=0) {
				if(knotenB[4]) {
					knotenElement.add(14);
					knotenElement.add(8);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(14,8);
				}
				linie14_8= new Line2D.Double(width-25, height-25, width/5*4, height-35);
				g2.draw(linie14_8);
				//g.drawLine(width-25, height-25, width/5*4, height-35);
				int wert=graph.getAdjMatrixIndex(14, 8);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5*4+width/10,height-25);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(8,4)!=0) {
				if(knotenB[5]) {
					knotenElement.add(8);
					knotenElement.add(4);
					knotenanzahl++;
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(8,4);
				}
				 linie8_4= new Line2D.Double(width/5*4, height-35, width/5*2, height-30);
				g2.draw(linie8_4);
				//g.drawLine(width/5*4, height-35, width/5*2, height-30);
				int wert=graph.getAdjMatrixIndex(8, 4);
				String s=Integer.toString(wert);
				g2.drawString(s, width/5*2+width/10,height-30);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(13,4)!=0) {
				if(knotenB[6]) {
					knotenElement.add(13);
					knotenElement.add(4);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(13,4);
				}
				 linie13_4= new Line2D.Double(width/5, height-30,width/5*2, height-30);
				g2.draw(linie13_4);
				//g.drawLine(width/5, height-30,width/5*2, height-30);
				int wert=graph.getAdjMatrixIndex(13, 4);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5+width/10, height-30);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(13,3)!=0) {
				if(knotenB[7]) {
					knotenElement.add(13);
					knotenElement.add(3);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(13,3);
				}
				 linie13_3= new Line2D.Double(width/5, height-30, width/5, height/5*4);
				g2.draw(linie13_3);
				//g.drawLine(width/5, height-30, width/5, height/5*4);
				int wert=graph.getAdjMatrixIndex(13, 3);
				String s=Integer.toString(wert);
				g2.drawString(s, width/5, height/5*4+height/10);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(8,7)!=0) {
				if(knotenB[8]) {
					knotenElement.add(8);
					knotenElement.add(7);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(8,7);
				}
				 linie8_7= new Line2D.Double(width/5*4, height-35, width/5*4, height/5*4);
				g2.draw(linie8_7);
				//g.drawLine(width/5*4, height-35, width/5*4, height/5*4);
				int wert=graph.getAdjMatrixIndex(8, 7);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5*4, height/5*4+height/10);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(7,14)!=0) {
				if(knotenB[9]) {
					knotenElement.add(7);
					knotenElement.add(14);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(7,14);
				}
				 linie7_14= new Line2D.Double(width/5*4, height/5*4, width-25, height-25);
				g2.draw(linie7_14);
				//g.drawLine(width/5*4, height/5*4, width-25, height-25);
				int wert=graph.getAdjMatrixIndex(7, 14);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5*4+width/10/2, height/5*4+height/10/2);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(7,9)!=0) {
				if(knotenB[10]) {
					knotenElement.add(7);
					knotenElement.add(9);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(7,9);
				}
				linie7_9= new Line2D.Double(width/5*4, height/5*4, width-23, height/5*4);
				g2.draw(linie7_9);
				//g.drawLine(width/5*4, height/5*4, width-23, height/5*4);
				int wert=graph.getAdjMatrixIndex(7, 9);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5*4+width/10, height/5*4);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(7,6)!=0) {
				if(knotenB[11]) {
					knotenElement.add(7);
					knotenElement.add(6);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(7,6);
				}
				 linie7_6 = new Line2D.Double(width/5*4, height/5*4, width-25, height/5*3);
				g2.draw(linie7_6);
				//g.drawLine(width/5*4, height/5*4, width-25, height/5*3);
				int wert=graph.getAdjMatrixIndex(7, 6);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5*4+width/15, height/5*4-height/10);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(7,11)!=0) {
				if(knotenB[12]) {
					knotenElement.add(7);
					knotenElement.add(11);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(7,11);
				}
				 linie7_11 = new Line2D.Double(width/5*4, height/5*4, width/5*4, height/5*2);
				g2.draw(linie7_11);
				//g.drawLine(width/5*4, height/5*4, width/5*4, height/5*2);
				int wert=graph.getAdjMatrixIndex(7, 11);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5*4, height/5*2+height/10);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(7,5)!=0) {
				if(knotenB[13]) {
					knotenElement.add(7);
					knotenElement.add(5);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(7,5);
				}
				 linie7_5 = new Line2D.Double(width/5*4, height/5*4, width/5*3, height/5*3);
				g2.draw(linie7_5);
				//g.drawLine(width/5*4, height/5*4, width/5*3, height/5*3);
				int wert=graph.getAdjMatrixIndex(7, 5);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5*3+width/20, height/5*3+height/20);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(7,10)!=0) {
				if(knotenB[14]) {
					knotenElement.add(10);
					knotenElement.add(7);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(7,10);
				}
				 linie7_10 = new Line2D.Double(width/5*4, height/5*4, width/5*2, height/5*4);
				g2.draw(linie7_10);
				//g.drawLine(width/5*4, height/5*4, width/5*2, height/5*4);
				int wert=graph.getAdjMatrixIndex(7, 10);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5*2+width/8, height/5*4);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(7,4)!=0) {
				if(knotenB[15]) {
					knotenElement.add(7);
					knotenElement.add(4);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(7,4);
				}
				linie7_4 = new Line2D.Double(width/5*4, height/5*4, width/5*2, height-30);
				g2.draw(linie7_4);
				//g.drawLine(width/5*4, height/5*4, width/5*2, height-30);
				int wert=graph.getAdjMatrixIndex(7, 4);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5*2+width/15, height-30-height/22);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(4,10)!=0) {
				if(knotenB[16]) {
					knotenElement.add(10);
					knotenElement.add(4);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(4,10);
				}
				linie4_10=new Line2D.Double(width/5*2, height-30, width/5*2, height/5*4);
				g2.draw(linie4_10);
				//g.drawLine(width/5*2, height-30, width/5*2, height/5*4);
				int wert=graph.getAdjMatrixIndex(4, 10);
				String s=Integer.toString(wert);
				g2.drawString(s, width/5*2, height/5*4+height/10);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(13,10)!=0) {
				if(knotenB[17]) {
					knotenElement.add(13);
					knotenElement.add(10);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(13,10);
				}
				 linie13_10= new Line2D.Double(width/5, height-30,width/5*2, height/5*4);
				g2.draw(linie13_10);
				//g.drawLine(width/5, height-30,width/5*2, height/5*4);
				int wert=graph.getAdjMatrixIndex(13, 10);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5+width/15, height-30-height/18);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(10,3)!=0) {
				if(knotenB[18]) {
					knotenElement.add(10);
					knotenElement.add(3);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(10,3);
				}
				 linie10_3= new Line2D.Double(width/5*2, height/5*4, width/5, height/5*4);
				g2.draw(linie10_3);
				//g.drawLine(width/5*2, height/5*4, width/5, height/5*4);
				int wert=graph.getAdjMatrixIndex(10, 3);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5+width/10, height/5*4);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(3,1)!=0) {
				if(knotenB[19]) {
					knotenElement.add(3);
					knotenElement.add(1);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(3,1);
				}
				 linie3_1= new Line2D.Double(width/5, height/5*4, width/5, height/5*3);
				g2.draw(linie3_1);
				//g.drawLine(width/5, height/5*4, width/5, height/5*3);
				int wert=graph.getAdjMatrixIndex(3, 1);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5, height/5*3+height/10);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(1,10)!=0) {
				if(knotenB[20]) {
					knotenElement.add(10);
					knotenElement.add(1);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(1,10);
				}
				 linie1_10= new Line2D.Double(width/5, height/5*3,width/5*2, height/5*4);
				g2.draw(linie1_10);
				//g.drawLine(width/5, height/5*3,width/5*2, height/5*4);
				int wert=graph.getAdjMatrixIndex(1, 10);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5+width/10, height/5*3+height/10);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(1,12)!=0) {
				if(knotenB[21]) {
					knotenElement.add(1);
					knotenElement.add(12);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(1,12);
				}
				 linie1_12= new Line2D.Double(width/5, height/5*3, width/5*2, height/5*2);
				g2.draw(linie1_12);
				//g.drawLine(width/5, height/5*3, width/5*2, height/5*2);
				int wert=graph.getAdjMatrixIndex(1, 12);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5+width/15, height/5*3-height/15);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(12,10)!=0) {
				if(knotenB[22]) {
					knotenElement.add(10);
					knotenElement.add(12);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(12,10);
				}
				 linie12_10= new Line2D.Double(width/5*2, height/5*2,width/5*2, height/5*4);
				g2.draw(linie12_10);
				//g.drawLine(width/5*2, height/5*2,width/5*2, height/5*4);
				int wert=graph.getAdjMatrixIndex(12, 10);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5*2, height/5*2+height/10);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(10,5)!=0) {
				if(knotenB[23]) {
					knotenElement.add(10);
					knotenElement.add(5);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(10,5);
				}
				 linie10_5= new Line2D.Double(width/5*2, height/5*4, width/5*3, height/5*3);
				g2.draw(linie10_5);
				//g.drawLine(width/5*2, height/5*4, width/5*3, height/5*3);
				int wert=graph.getAdjMatrixIndex(10, 5);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5*2+width/15, height/5*4-height/15);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(5,12)!=0) {
				if(knotenB[24]) {
					knotenElement.add(5);
					knotenElement.add(12);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(5,12);
				}
				 linie5_12= new Line2D.Double(width/5*3, height/5*3, width/5*2, height/5*2);
				g2.draw(linie5_12);
				//g.drawLine(width/5*3, height/5*3, width/5*2, height/5*2);
				int wert=graph.getAdjMatrixIndex(5, 12);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5*2+width/12, height/5*2+height/15);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(5,11)!=0) {
				if(knotenB[25]) {
					knotenElement.add(5);
					knotenElement.add(11);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(5,11);
				}
				 linie5_11= new Line2D.Double(width/5*3, height/5*3, width/5*4, height/5*2);
				g2.draw(linie5_11);
				//g.drawLine(width/5*3, height/5*3, width/5*4, height/5*2);
				int wert=graph.getAdjMatrixIndex(5, 11);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5*3+width/11, height/5*3-height/15);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(0,5)!=0) {
				if(knotenB[26]) {
					knotenElement.add(0);
					knotenElement.add(5);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(0,5);
				}
				 linie0_5= new Line2D.Double(width/5*3, height/5, width/5*3, height/5*3);
				g2.draw(linie0_5);
				//g.drawLine(width/5*3, height/5, width/5*3, height/5*3);
				int wert=graph.getAdjMatrixIndex(0, 5);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5*3, height/5+height/4);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(12,0)!=0) {
				if(knotenB[27]) {
					knotenElement.add(0);
					knotenElement.add(12);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(12,0);
				}
				 linie12_0= new Line2D.Double(width/5*2, height/5*2, width/5*3, height/5);
				g2.draw(linie12_0);
				//g.drawLine(width/5*2, height/5*2, width/5*3, height/5);
				int wert=graph.getAdjMatrixIndex(12, 0);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5*2+width/10, height/5*2-height/10);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(0,11)!=0) {
				if(knotenB[28]) {
					knotenElement.add(0);
					knotenElement.add(11);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(0,11);
				}
				linie0_11= new Line2D.Double(width/5*3, height/5, width/5*4, height/5*2);
				g2.draw(linie0_11);
				//g.drawLine(width/5*3, height/5, width/5*4, height/5*2);
				int wert=graph.getAdjMatrixIndex(0, 11);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5*3+width/15, height/5+height/15);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(2,11)!=0) {
				if(knotenB[29]) {
					knotenElement.add(11);
					knotenElement.add(2);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(2,11);
				}
				 linie2_11= new Line2D.Double(width-25, height/5, width/5*4, height/5*2);
				g2.draw(linie2_11);
				//g.drawLine(width-25, height/5, width/5*4, height/5*2);
				int wert=graph.getAdjMatrixIndex(2, 11);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5*4+width/15, height/5*2-height/15);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(11,6)!=0) {
				if(knotenB[30]) {
					knotenElement.add(11);
					knotenElement.add(6);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(11,6);
				}
				 linie11_6= new Line2D.Double(width/5*4, height/5*2, width-25, height/5*3);
				g2.draw(linie11_6);
				//g.drawLine(width/5*4, height/5*2, width-25, height/5*3);
				int wert=graph.getAdjMatrixIndex(11, 6);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5*4+width/12, height/5*2+height/10);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(2,12)!=0) {
				if(knotenB[31]) {
					knotenElement.add(12);
					knotenElement.add(2);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(2,12);
				}
				 linie2_12= new Line2D.Double(width-25, height/5, width/5*2, height/5*2);
				g2.draw(linie2_12);
				//g.drawLine(width-25, height/5, width/5*2, height/5*2);
				int wert=graph.getAdjMatrixIndex(2, 12);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5*2+width/10, height/5*2-height/18);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(2,9)!=0) {
				if(knotenB[32]) {
					knotenElement.add(9);
					knotenElement.add(2);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(2,9);
				}
				 linie2_9= new Line2D.Double(width-25, height/5, width-23, height/5*4);
				g2.draw(linie2_9);
				//g.drawLine(width-25, height/5, width-23, height/5*4);
				int wert=graph.getAdjMatrixIndex(2, 9);
				String s=Integer.toString(wert);
				g2.drawString(s,width-25, height/5+height/5);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(6,14)!=0) {
				if(knotenB[33]) {
					knotenElement.add(6);
					knotenElement.add(14);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(6,14);
				}
				 linie6_14= new Line2D.Double(width-25, height/5*3, width-25, height-25);
				g2.draw(linie6_14);
				//g.drawLine(width-25, height/5*3, width-25, height-25);
				int wert=graph.getAdjMatrixIndex(6, 14);
				String s=Integer.toString(wert);
				g2.drawString(s,width-25, height/5*3+height/5);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(9,8)!=0) {
				if(knotenB[34]) {
					knotenElement.add(9);
					knotenElement.add(8);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(9,8);
				}
				 linie9_8= new Line2D.Double(width-23, height/5*4, width/5*4, height-35);
				g2.draw(linie9_8);
				//g.drawLine(width-23, height/5*4, width/5*4, height-35);
				int wert=graph.getAdjMatrixIndex(9, 8);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5*4-width/15, height-35+height/10);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(8,10)!=0) {
				if(knotenB[35]) {
					knotenElement.add(10);
					knotenElement.add(8);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(8,10);
				}
				 linie8_10= new Line2D.Double(width/5*4, height-35, width/5*2, height/5*4);
				g2.draw(linie8_10);
				//g.drawLine(width/5*4, height-35, width/5*2, height/5*4);
				int wert=graph.getAdjMatrixIndex(8, 10);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5*2+width/10, height/5*4+height/15);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(13,8)!=0) {
				if(knotenB[36]) {
					knotenElement.add(13);
					knotenElement.add(8);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(13,8);
				}
				 linie13_8= new Line2D.Double(width/5, height-30,width/5*4, height-35);
				g2.draw(linie13_8);
				//g.drawLine(width/5, height-30,width/5*4, height-35);
				int wert=graph.getAdjMatrixIndex(13, 8);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5+width/5, height-30);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(4,3)!=0) {
				if(knotenB[37]) {
					knotenElement.add(4);
					knotenElement.add(3);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(4,3);
				}
				 linie4_3= new Line2D.Double(width/5*2, height-30, width/5, height/5*4);
				g2.draw(linie4_3);
				//g.drawLine(width/5*2, height-30, width/5, height/5*4);
				int wert=graph.getAdjMatrixIndex(4, 3);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5+width/12, height/5*4+height/12);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(13,1)!=0) {
				if(knotenB[38]) {
					knotenElement.add(1);
					knotenElement.add(13);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(13,1);
				}
				 linie13_1= new Line2D.Double(width/5, height-30,width/5, height/5*3);
				g2.draw(linie13_1);
				//g.drawLine(width/5, height-30,width/5, height/5*3);
				int wert=graph.getAdjMatrixIndex(13, 1);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5, height/5*3+height/5);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(3,12)!=0) {
				if(knotenB[39]) {
					knotenElement.add(3);
					knotenElement.add(12);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(3,12);
				}
				 linie3_12= new Line2D.Double(width/5, height/5*4,width/5*2, height/5*2);
				g2.draw(linie3_12);
				//g.drawLine(width/5, height/5*4,width/5*2, height/5*2);
				int wert=graph.getAdjMatrixIndex(3, 12);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5+width/20, height/5*4-height/15);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(0,1)!=0) {
				if(knotenB[40]) {
					knotenElement.add(0);
					knotenElement.add(1);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(0,1);
				}
				 linie0_1= new Line2D.Double(width/5*3, height/5,width/5, height/5*3);
				g2.draw(linie0_1);
				//g.drawLine(width/5*3, height/5,width/5, height/5*3);
				int wert=graph.getAdjMatrixIndex(0, 1);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5+width/5, height/5*3-height/5);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(12,11)!=0) {
				if(knotenB[41]) {
					knotenElement.add(11);
					knotenElement.add(12);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(12,11);
				}
				 linie12_11= new Line2D.Double(width/5*2, height/5*2, width/5*4, height/5*2);
				g2.draw(linie12_11);
				//g.drawLine(width/5*2, height/5*2, width/5*4, height/5*2);
				int wert=graph.getAdjMatrixIndex(12, 11);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5*2+width/10, height/5*2);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(6,5)!=0) {
				if(knotenB[42]) {
					knotenElement.add(5);
					knotenElement.add(6);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(6,5);
				}
				 linie6_5= new Line2D.Double(width-25, height/5*3, width/5*3, height/5*3);
				g2.draw(linie6_5);
				//g.drawLine(width-25, height/5*3, width/5*3, height/5*3);
				int wert=graph.getAdjMatrixIndex(6, 5);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5*3+width/5, height/5*3);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(9,11)!=0) {
				if(knotenB[43]) {
					knotenElement.add(9);
					knotenElement.add(11);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(9,11);
				}
				 linie9_11= new Line2D.Double(width-23, height/5*4, width/5*4, height/5*2);
				g2.draw(linie9_11);
				//g.drawLine(width-23, height/5*4, width/5*4, height/5*2);
				int wert=graph.getAdjMatrixIndex(9, 11);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5*4+width/15, height/5*2+height/7);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(0,6)!=0) {
				if(knotenB[44]) {
					knotenElement.add(0);
					knotenElement.add(6);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(0,6);
				}
				 linie0_6= new Line2D.Double(width/5*3, height/5, width-25, height/5*3);
				g2.draw(linie0_6);
				//g.drawLine(width/5*3, height/5, width-25, height/5*3);
				int wert=graph.getAdjMatrixIndex(0, 6);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5*3+width/15, height/5+height/15);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(4,12)!=0) {
				if(knotenB[45]) {
					knotenElement.add(12);
					knotenElement.add(4);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(4,12);
				}
				 linie4_12= new Line2D.Double(width/5*2, height-30, width/5*2, height/5*2);
				g2.draw(linie4_12);
				//g.drawLine(width/5*2, height-30, width/5*2, height/5*2);
				int wert=graph.getAdjMatrixIndex(4, 12);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5*2, height/5*2+height/5);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(8,9)!=0) {
				if(knotenB[46]) {
					knotenElement.add(9);
					knotenElement.add(8);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(8,9);
				}
				 linie8_9= new Line2D.Double(width/5*4, height-35, width-23, height/5*4);
				g2.draw(linie8_9);
				//g.drawLine(width/5*4, height-35, width-23, height/5*4);
				int wert=graph.getAdjMatrixIndex(8, 9);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5*4+width/10, height-35-height/10);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(8,5)!=0) {
				if(knotenB[47]) {
					knotenElement.add(8);
					knotenElement.add(5);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(8,5);
				}
				 linie8_5= new Line2D.Double(width/5*4, height-35, width/5*3, height/5*3);
				g2.draw(linie8_5);
				//g.drawLine(width/5*4, height-35, width/5*3, height/5*3);
				int wert=graph.getAdjMatrixIndex(8, 5);
				String s=Integer.toString(wert);
				g2.drawString(s,width/5*3+width/25, height/5*3+height/15);
				g2.setColor(Color.black);
			}
			if(graph.getAdjMatrixIndex(5,4)!=0) {
				if(knotenB[48]) {
					knotenElement.add(5);
					knotenElement.add(4);
					g2.setColor(Color.red);
					summe+=graph.getAdjMatrixIndex(5,4);
				}
				linie5_4= new Line2D.Double(width/5*3, height/5*3, width/5*2, height-30);
				g2.draw(linie5_4);
				int wert=graph.getAdjMatrixIndex(5, 4);
				String s=Integer.toString(wert);
				g2.drawString(s, width/5*2+width/10, height-30-height/5);
				g2.setColor(Color.black);
			}
			//extraRohr(g2);
			//System.out.println(summe);
			
			//Works
//			 Events myListener = new Events();
//			 for(int i=0; i<array.length;i++) {
//				 System.out.println(array[i]);//null
//			 }
//			 this.addMouseListener(myListener);
			a =new Line2D[]{ linie0_2, linie2_6, linie6_9, linie9_14,
					 linie14_8, linie8_4, linie13_4, linie13_3,
					 linie8_7, linie7_14, linie7_9, linie7_6,
					 linie7_11, linie7_5, linie7_10, linie7_4,
					 linie4_10, linie13_10, linie10_3, linie3_1,
					 linie1_10, linie1_12, linie12_10, linie10_5,
					 linie5_12, linie5_11, linie0_5, linie12_0, linie0_11,
					 linie2_11, linie11_6, linie2_12, linie2_9,
					 linie6_14, linie9_8, linie8_10, linie13_8,
					 linie4_3, linie13_1, linie3_12, linie0_1,
					 linie12_11, linie6_5, linie9_11, linie0_6,
					 linie4_12, linie8_9, linie8_5, linie5_4};
			
			knoten =new int[][]{ {0,2},{2,6},{6,9},{9,14},{14,8},{8,4},{13,4},{13,3},{8,7},{7,14},
								 {7,9},{7,6},{7,11},{7,5},{7,10},{7,4},{4,10},{13,10},{10,3},{3,1},
								 {1,10},{1,12},{12,10},{10,5},{5,12},{5,11},{0,5},{12,0},{0,11},
								 {2,11},{11,6},{2,12},{2,9},{6,14},{9,8},{8,10},{13,8},{4,3},{13,1},
								 {3,12},{0,1},{12,11},{6,5},{9,11},{0,6},{4,12},{8,9},{8,5},{5,4}};
			
			//test();
			knotenAnzahl=getKnotenCount(knotenElement);
			//System.out.println("Groesse "+knotenAnzahl);
			
			//RepaintManager.currentManager(null).setDoubleBufferingEnabled(false);
			
			
			
			
			this.addMouseMotionListener(ml2);
			this.addMouseListener(ml3);
		    
//		    
//		    g2.drawImage(image, 100, 100, this);
		   // drawit();
		  
		 //   g2.drawImage(drawit(), imageX, imageY, this);

	}
	public JLabel drawit() {
	    Image image = Toolkit.getDefaultToolkit().getImage("/resources/lueftung.jpg");
	    image = image.getScaledInstance(60, 60, Image.SCALE_DEFAULT);
	    ImageIcon ic= new ImageIcon(image);
	    JLabel jl= new JLabel(ic);
		//this.add(new DragImage(image));
	    //DragImage d=new DragImage(image);
	    
	    MouseMotionListener ml2= new MouseAdapter() {
	    	public void mouseDragged(MouseEvent e) {
	    			
	    		    rohrx = e.getX();
	    		    rohry = e.getY();
	    		   // System.out.print("DRAGGED");
	    		    repaint();
	    	}
	    
	    };
	    jl.addMouseMotionListener(ml2);
	   /// this.add(jl, new BorderLayout().NORTH);
	    return jl;
	}
//	
	public boolean isShowOpt() {
		return showOpt;
	}

	public void setShowOpt(boolean showOpt) {
		this.showOpt = showOpt;
	}
	
	public void setGameOver(boolean b) {
		this.gameOver=b;
		repaint();
	}
	public int getKnotenCount(LinkedList knotenElement) {
		unique = new HashSet<Integer>(knotenElement);
		//System.out.println("Unique Hash: "+unique);
		knotenAnzahl=unique.size();
		return unique.size();
	}
	
	public int getKnotenAnzahl() {
		//repaint();
		return knotenAnzahl;
	}

	public void setKnotenAnzahl(int knotenAnzahl) {
		this.knotenAnzahl = knotenAnzahl;
	}

	public Set <Integer> getUnique() {
		return unique;
	}

	public Grapherzeugen getGraph() {
		return graph;
	}

	public void setGraph(Grapherzeugen graph) {
		this.graph = graph;
	}

	public int getSumme() {
		return summe;
	}

	public void setSumme(int summe) {
		this.summe = summe;
	}

	public void test() {
		for(int i =0;i<graph.getAdjMatrix().length;i++) {
			for(int j=0; j<graph.getAdjMatrix().length;j++) {
				if(graph.getAdjMatrix()[i][j]!=0) {
					//System.out.println("i: "+i+", j:"+j+" Matrix Wert:"+graph.getAdjMatrix()[i][j]);
				}
			}
		}
		//System.out.println("Ende");

	}

	
	public int getSchranke() {
		return graph.getSchranke();	
	}

	public Line2D getLinie0_2() {
		return linie0_2;
	}

	public void setLinie0_2(Line2D linie0_2) {
		this.linie0_2 = linie0_2;
	}

	public Line2D getLinie2_6() {
		return linie2_6;
	}

	public void setLinie2_6(Line2D linie2_6) {
		this.linie2_6 = linie2_6;
	}

	public Line2D getLinie6_9() {
		return linie6_9;
	}

	public void setLinie6_9(Line2D linie6_9) {
		this.linie6_9 = linie6_9;
	}

	public Line2D getLinie9_14() {
		return linie9_14;
	}

	public void setLinie9_14(Line2D linie9_14) {
		this.linie9_14 = linie9_14;
	}

	public Line2D getLinie14_8() {
		return linie14_8;
	}

	public void setLinie14_8(Line2D linie14_8) {
		this.linie14_8 = linie14_8;
	}

	public Line2D getLinie8_4() {
		return linie8_4;
	}

	public Line2D[] getArray() {
		return a;
	}
	public Line2D getArrayIndex(int index) {
		return a[index];
	}

	public void setArray(Line2D[] array) {
		this.a = array;
	}

	public void setLinie8_4(Line2D linie8_4) {
		this.linie8_4 = linie8_4;
	}

	public Line2D getLinie13_4() {
		return linie13_4;
	}

	public void setLinie13_4(Line2D linie13_4) {
		this.linie13_4 = linie13_4;
	}

	public Line2D getLinie13_3() {
		return linie13_3;
	}

	public void setLinie13_3(Line2D linie13_3) {
		this.linie13_3 = linie13_3;
	}

	public Line2D getLinie8_7() {
		return linie8_7;
	}

	public void setLinie8_7(Line2D linie8_7) {
		this.linie8_7 = linie8_7;
	}

	public Line2D getLinie7_14() {
		return linie7_14;
	}

	public void setLinie7_14(Line2D linie7_14) {
		this.linie7_14 = linie7_14;
	}

	public Line2D getLinie7_9() {
		return linie7_9;
	}

	public void setLinie7_9(Line2D linie7_9) {
		this.linie7_9 = linie7_9;
	}

	public Line2D getLinie7_6() {
		return linie7_6;
	}

	public void setLinie7_6(Line2D linie7_6) {
		this.linie7_6 = linie7_6;
	}

	public Line2D getLinie7_11() {
		return linie7_11;
	}

	public void setLinie7_11(Line2D linie7_11) {
		this.linie7_11 = linie7_11;
	}

	public Line2D getLinie7_5() {
		return linie7_5;
	}

	public void setLinie7_5(Line2D linie7_5) {
		this.linie7_5 = linie7_5;
	}

	public Line2D getLinie7_10() {
		return linie7_10;
	}

	public void setLinie7_10(Line2D linie7_10) {
		this.linie7_10 = linie7_10;
	}

	public Line2D getLinie7_4() {
		return linie7_4;
	}

	public void setLinie7_4(Line2D linie7_4) {
		this.linie7_4 = linie7_4;
	}

	public Line2D getLinie4_10() {
		return linie4_10;
	}

	public void setLinie4_10(Line2D linie4_10) {
		this.linie4_10 = linie4_10;
	}

	public Line2D getLinie13_10() {
		return linie13_10;
	}

	public void setLinie13_10(Line2D linie13_10) {
		this.linie13_10 = linie13_10;
	}

	public Line2D getLinie10_3() {
		return linie10_3;
	}

	public void setLinie10_3(Line2D linie10_3) {
		this.linie10_3 = linie10_3;
	}

	public Line2D getLinie3_1() {
		return linie3_1;
	}

	public void setLinie3_1(Line2D linie3_1) {
		this.linie3_1 = linie3_1;
	}

	public Line2D getLinie1_10() {
		return linie1_10;
	}

	public void setLinie1_10(Line2D linie1_10) {
		this.linie1_10 = linie1_10;
	}

	public Line2D getLinie1_12() {
		return linie1_12;
	}

	public void setLinie1_12(Line2D linie1_12) {
		this.linie1_12 = linie1_12;
	}

	public Line2D getLinie12_10() {
		return linie12_10;
	}

	public void setLinie12_10(Line2D linie12_10) {
		this.linie12_10 = linie12_10;
	}

	public Line2D getLinie10_5() {
		return linie10_5;
	}

	public void setLinie10_5(Line2D linie10_5) {
		this.linie10_5 = linie10_5;
	}

	public Line2D getLinie5_12() {
		return linie5_12;
	}

	public void setLinie5_12(Line2D linie5_12) {
		this.linie5_12 = linie5_12;
	}

	public Line2D getLinie5_11() {
		return linie5_11;
	}

	public void setLinie5_11(Line2D linie5_11) {
		this.linie5_11 = linie5_11;
	}

	public Line2D getLinie0_5() {
		return linie0_5;
	}

	public void setLinie0_5(Line2D linie0_5) {
		this.linie0_5 = linie0_5;
	}

	public Line2D getLinie12_0() {
		return linie12_0;
	}

	public void setLinie12_0(Line2D linie12_0) {
		this.linie12_0 = linie12_0;
	}

	public Line2D getLinie0_11() {
		return linie0_11;
	}

	public void setLinie0_11(Line2D linie0_11) {
		this.linie0_11 = linie0_11;
	}

	public Line2D getLinie2_11() {
		return linie2_11;
	}

	public void setLinie2_11(Line2D linie2_11) {
		this.linie2_11 = linie2_11;
	}

	public Line2D getLinie11_6() {
		return linie11_6;
	}

	public void setLinie11_6(Line2D linie11_6) {
		this.linie11_6 = linie11_6;
	}

	public Line2D getLinie2_12() {
		return linie2_12;
	}

	public void setLinie2_12(Line2D linie2_12) {
		this.linie2_12 = linie2_12;
	}

	public Line2D getLinie2_9() {
		return linie2_9;
	}

	public void setLinie2_9(Line2D linie2_9) {
		this.linie2_9 = linie2_9;
	}

	public Line2D getLinie6_14() {
		return linie6_14;
	}

	public void setLinie6_14(Line2D linie6_14) {
		this.linie6_14 = linie6_14;
	}

	public Line2D getLinie9_8() {
		return linie9_8;
	}

	public void setLinie9_8(Line2D linie9_8) {
		this.linie9_8 = linie9_8;
	}

	public Line2D getLinie8_10() {
		return linie8_10;
	}

	public void setLinie8_10(Line2D linie8_10) {
		this.linie8_10 = linie8_10;
	}

	public Line2D getLinie13_8() {
		return linie13_8;
	}

	public void setLinie13_8(Line2D linie13_8) {
		this.linie13_8 = linie13_8;
	}

	public Line2D getLinie4_3() {
		return linie4_3;
	}

	public void setLinie4_3(Line2D linie4_3) {
		this.linie4_3 = linie4_3;
	}

	public Line2D getLinie13_1() {
		return linie13_1;
	}

	public void setLinie13_1(Line2D linie13_1) {
		this.linie13_1 = linie13_1;
	}

	public Line2D getLinie3_12() {
		return linie3_12;
	}

	public void setLinie3_12(Line2D linie3_12) {
		this.linie3_12 = linie3_12;
	}

	public Line2D getLinie0_1() {
		return linie0_1;
	}

	public void setLinie0_1(Line2D linie0_1) {
		this.linie0_1 = linie0_1;
	}

	public Line2D getLinie12_11() {
		return linie12_11;
	}

	public void setLinie12_11(Line2D linie12_11) {
		this.linie12_11 = linie12_11;
	}

	public Line2D getLinie6_5() {
		return linie6_5;
	}

	public void setLinie6_5(Line2D linie6_5) {
		this.linie6_5 = linie6_5;
	}

	public Line2D getLinie9_11() {
		return linie9_11;
	}

	public void setLinie9_11(Line2D linie9_11) {
		this.linie9_11 = linie9_11;
	}

	public Line2D getLinie0_6() {
		return linie0_6;
	}

	public void setLinie0_6(Line2D linie0_6) {
		this.linie0_6 = linie0_6;
	}

	public Line2D getLinie4_12() {
		return linie4_12;
	}

	public void setLinie4_12(Line2D linie4_12) {
		this.linie4_12 = linie4_12;
	}

	public Line2D getLinie8_9() {
		return linie8_9;
	}

	public void setLinie8_9(Line2D linie8_9) {
		this.linie8_9 = linie8_9;
	}

	public Line2D getLinie8_5() {
		return linie8_5;
	}

	public void setLinie8_5(Line2D linie8_5) {
		this.linie8_5 = linie8_5;
	}

	public Line2D getLinie5_4() {
		return linie5_4;
	}

	public void setLinie5_4(Line2D linie5_4) {
		this.linie5_4 = linie5_4;
	}

	public int[][] getKnoten() {
		return knoten;
	}

	public void setKnoten(int[][] knoten) {
		this.knoten = knoten;
	}
	
	public int[] getKnotenIndex(int index) {
		return knoten[index];
	}


	public boolean[] getKnotenB() {
		return knotenB;
	}

	public void setKnotenB(boolean[] knotenB) {
		this.knotenB = knotenB;
	}
	
	public boolean getKnotenBIndex(int index) {
		return knotenB[index];
	}

	public void setKnotenBIndex(boolean set, int index) {
		this.knotenB[index] = set;
	}
	public void setKnotenBfalse() {
		for(int i=0; i<knotenB.length; i++) {
			knotenB[i]=false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		rohrx=20;
		rohry=20;
		repaint();
//		System.out.print("RELEASE OUTSIDE");
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
	    imageX = e.getX();
	    imageY = e.getY();
//	    repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	 public boolean isAbgabegueltig() {
		return abgabegueltig;
	}

	public void setAbgabegueltig(boolean abgabegueltig) {
		this.abgabegueltig = abgabegueltig;
	}

	MouseMotionListener ml2= new MouseAdapter() {
	    	public void mouseDragged(MouseEvent e) {
	    		getroffen=false;
	    		Point p=e.getPoint();
	    		Rectangle rect= new Rectangle(5,5);
	    		rect.setFrameFromCenter(p.x,p.y,p.x+5/2,p.y+5/2);
	    		
	    			if(rec.intersects(rect)) {

	    				int x = e.getX();
			    		int y = e.getY();
		    		    rohrx = x;
		    		    rohry = y;
			    		repaint();   
			    		p=e.getPoint();
			    		rect= new Rectangle(5,5);
			    		rect.setFrameFromCenter(p.x,p.y,p.x+5/2,p.y+5/2);
	    			}
	    	}
	    };
	    
	    MouseListener ml3= new MouseAdapter() {
	    	public void mouseReleased(MouseEvent e) {
    			int height= getHeight();
    			int width= getWidth();

	    		Point p=e.getPoint();

    			Rectangle2D ov0= new Rectangle2D.Double(width/5*3, height/5, 10,10);
    			Rectangle2D ov1= new Rectangle2D.Double(width/5, height/5*3, 10,10);
    			Rectangle2D ov2= new Rectangle2D.Double(width-25, height/5, 10,10);
    			Rectangle2D ov3= new Rectangle2D.Double(width/5, height/5*4, 10,10);
    			Rectangle2D ov4= new Rectangle2D.Double(width/5*2, height-30, 10,10);
    			Rectangle2D ov5= new Rectangle2D.Double(width/5*3, height/5*3,10,10);
    			Rectangle2D ov6= new Rectangle2D.Double(width-25, height/5*3,10,10);
    			Rectangle2D ov7= new Rectangle2D.Double(width/5*4, height/5*4, 10,10);
    			Rectangle2D ov8= new Rectangle2D.Double(width/5*4, height-35, 10,10);
    			Rectangle2D ov9= new Rectangle2D.Double(width-23, height/5*4, 10,10);
    			Rectangle2D ov10= new Rectangle2D.Double(width/5*2, height/5*4, 10,10);
    			Rectangle2D ov11= new Rectangle2D.Double(width/5*4, height/5*2, 10,10);
    			Rectangle2D ov12= new Rectangle2D.Double(width/5*2, height/5*2, 10,10);
    			Rectangle2D ov13= new Rectangle2D.Double(width/5, height-30, 10,10 );
    			Rectangle2D ov14= new Rectangle2D.Double(width-25, height-25,10, 10);
    			
    			if(ov0.intersects(rec)&&(knotenB[0]==true||knotenB[26]==true||knotenB[27]==true||knotenB[28]==true||knotenB[40]==true||knotenB[44]==true)) {
    				//System.out.println("OVAL RECHTECK 0");
    				getroffen=true;
    			}else if(ov1.intersects(rec)&&(knotenB[19]==true||knotenB[20]==true||knotenB[21]==true||knotenB[38]==true||knotenB[40]==true)) {
    				//System.out.println("OVAL RECHTECK 1");#
    				getroffen=true;
    			}else if(ov2.intersects(rec)&&(knotenB[0]==true||knotenB[1]==true||knotenB[29]==true||knotenB[31]==true||knotenB[32]==true)) {
    				//System.out.println("OVAL RECHTECK 2");
    				getroffen=true;
    			}else if(ov3.intersects(rec)&&(knotenB[7]==true||knotenB[18]==true||knotenB[19]==true||knotenB[37]==true||knotenB[39]==true)) {
    				//System.out.println("OVAL RECHTECK 3");
    				getroffen=true;
    			}else if(ov4.intersects(rec)&&(knotenB[5]==true||knotenB[15]==true||knotenB[16]==true||knotenB[37]==true||knotenB[45]==true||knotenB[48]==true)) {
    				//System.out.println("OVAL RECHTECK 4");
    				getroffen=true;
    			}else if(ov5.intersects(rec)&&(knotenB[13]==true||knotenB[23]==true||knotenB[24]==true||knotenB[25]==true||knotenB[26]==true||knotenB[42]==true||knotenB[47]==true||knotenB[48]==true)) {
    				//System.out.println("OVAL RECHTECK 5");
    				getroffen=true;
    			}else if(ov6.intersects(rec)&&(knotenB[1]==true||knotenB[2]==true||knotenB[11]==true||knotenB[30]==true||knotenB[33]==true||knotenB[42]==true||knotenB[44]==true)) {
    				//System.out.println("OVAL RECHTECK 6");
    				getroffen=true;
    			}else if(ov7.intersects(rec)&&(knotenB[8]==true||knotenB[9]==true||knotenB[10]==true||knotenB[11]==true||knotenB[12]==true||knotenB[13]==true||knotenB[14]==true||knotenB[15]==true)) {
    				//System.out.println("OVAL RECHTECK 7");
    				getroffen=true;
    			}else if(ov8.intersects(rec)&&(knotenB[4]==true||knotenB[5]==true||knotenB[8]==true||knotenB[34]==true||knotenB[35]==true||knotenB[36]==true||knotenB[46]==true||knotenB[47]==true)) {
    				//System.out.println("OVAL RECHTECK 8");
    				getroffen=true;
    			}else if(ov9.intersects(rec)&&(knotenB[2]==true||knotenB[3]==true||knotenB[10]==true||knotenB[32]==true||knotenB[34]==true||knotenB[43]==true||knotenB[46]==true)) {
    				//System.out.println("OVAL RECHTECK 9");
    				getroffen=true;
    			}else if(ov10.intersects(rec)&&(knotenB[14]==true||knotenB[16]==true||knotenB[17]==true||knotenB[18]==true||knotenB[20]==true||knotenB[22]==true||knotenB[23]==true||knotenB[35]==true)) {
    				//System.out.println("OVAL RECHTECK 10");
    				getroffen=true;
    			}else if(ov11.intersects(rec)&&(knotenB[12]==true||knotenB[25]==true||knotenB[28]==true||knotenB[29]==true||knotenB[30]==true||knotenB[41]==true||knotenB[43]==true)) {
    				//System.out.println("OVAL RECHTECK 11");
    				getroffen=true;
    			}else if(ov12.intersects(rec)&&(knotenB[21]==true||knotenB[22]==true||knotenB[24]==true||knotenB[27]==true||knotenB[31]==true||knotenB[39]==true||knotenB[41]==true||knotenB[45]==true)) {
    				//System.out.println("OVAL RECHTECK 12");
    				getroffen=true;
    			}else if(ov13.intersects(rec)&&(knotenB[6]==true||knotenB[7]==true||knotenB[17]==true||knotenB[36]==true||knotenB[38]==true)) {
    				//System.out.println("OVAL RECHTECK 13");
    				getroffen=true;
    			}else if(ov14.intersects(rec)&&(knotenB[3]==true||knotenB[4]==true||knotenB[9]==true||knotenB[33]==true)) {
    				//System.out.println("OVAL RECHTECK 14");
    				getroffen=true;
    			}else {
        			rohrx=20;
        			rohry=20;
        			repaint();
    			}
    			//System.out.println(getroffen);
	    	}
	    
	    };
}
