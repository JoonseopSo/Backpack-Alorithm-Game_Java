package spiel2Klassen;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;

public class Fisch {
	
	private int fischart;
	
	private ArrayList<Fisch> konflikte;
	
	private int icongroesse = 15;
	
	// Ob Fisch gerade von Cursor gehalten wird
	private boolean gehalten = false;
	
	private int posX;
	private int posY;
	
	// Ob Fisch gerade in einem Aquarium ist (und falls ja, in welchem)
	private int inAquarium = -1;
	
	private boolean inTabelle = true;
	
	public Fisch(int fischart) {
		this.fischart = fischart;
	}
	
	public void symbolMalen(Graphics2D g, int x, int y) {
		
		g.setStroke(new BasicStroke(3));
		switch (fischart) {
		case 0:
			g.setColor(Color.GREEN);
			g.drawRect(x, y, icongroesse, icongroesse);
			break;
		case 1:
			g.setColor(Color.RED);
			g.drawOval(x, y, icongroesse, icongroesse);
			break;
		case 2:
			g.setColor(Color.BLUE);
			g.fillRect(x, y, icongroesse, icongroesse);
			break;
		case 3: 
			g.setColor(Color.GRAY);
			g.fillOval(x, y, icongroesse, icongroesse);
			break;
		case 4:
			g.setColor(Color.BLACK);
			g.draw3DRect(x, y, icongroesse, icongroesse, false);
			break;
		case 5:
			g.setColor(Color.PINK);
			//Kreuz malen
			g.drawLine(x, y, x+icongroesse, y+icongroesse);
			g.drawLine(x, y+icongroesse, x+icongroesse, y);
			break;
		case 6:
			g.setColor(Color.ORANGE);
			g.fill3DRect(x, y, icongroesse, icongroesse, true);
			break;
		case 7:
			g.setColor(new Color(17, 113, 105));
			g.drawRoundRect(x, y, icongroesse, icongroesse, icongroesse/2, icongroesse/2);
			break;
		case 8:
			g.setColor(Color.MAGENTA);
			// Kreuz in Kästchen
			g.drawRect(x, y, icongroesse, icongroesse);
			g.drawLine(x, y, x+icongroesse, y+icongroesse);
			g.drawLine(x, y+icongroesse, x+icongroesse, y);
			break;
		case 9:
			g.setColor(Color.CYAN);
			g.drawOval(x, y, icongroesse, icongroesse);
			g.drawLine(x, y, x+icongroesse, y+icongroesse);
			g.drawLine(x, y+icongroesse, x+icongroesse, y);
			break;
			
		default:
			System.out.print("keine valide Fischart!");
			break;
			
		}
		g.setStroke(new BasicStroke(1));
	}
	
	public void symbolFarblosMalen(Graphics2D g, int x, int y) {
		
		g.setColor(Color.GRAY);
		
		g.setStroke(new BasicStroke(3));
		switch (fischart) {
		case 0:
			g.drawRect(x, y, icongroesse, icongroesse);
			break;
		case 1:
			g.drawOval(x, y, icongroesse, icongroesse);
			break;
		case 2:
			g.fillRect(x, y, icongroesse, icongroesse);
			break;
		case 3: 
			g.fillOval(x, y, icongroesse, icongroesse);
			break;
		case 4:
			g.draw3DRect(x, y, icongroesse, icongroesse, false);
			break;
		case 5:
			//Kreuz malen
			g.drawLine(x, y, x+icongroesse, y+icongroesse);
			g.drawLine(x, y+icongroesse, x+icongroesse, y);
			break;
		case 6:
			g.fill3DRect(x, y, icongroesse, icongroesse, true);
			break;
		case 7:
			g.drawRoundRect(x, y, icongroesse, icongroesse, icongroesse/2, icongroesse/2);
			break;
		case 8:
			// Kreuz in Kästchen
			g.drawRect(x, y, icongroesse, icongroesse);
			g.drawLine(x, y, x+icongroesse, y+icongroesse);
			g.drawLine(x, y+icongroesse, x+icongroesse, y);
			break;
		case 9:
			g.drawOval(x, y, icongroesse, icongroesse);
			g.drawLine(x, y, x+icongroesse, y+icongroesse);
			g.drawLine(x, y+icongroesse, x+icongroesse, y);
			break;
			
		default:
			System.out.print("keine valide Fischart!");
			break;
			
		}
		g.setStroke(new BasicStroke(1));
	}
	
	// True, wenn Koordinaten auf Fischicon
	public boolean hoversOverFisch(int x, int y) {
		if((x >= posX) && (x <= posX + icongroesse) && (y >= posY) && (y <= posY + icongroesse)) {
			return true;
		}
		return false;
	}

	public int getFischart() {
		return fischart;
	}

	public void setFischart(int fischart) {
		this.fischart = fischart;
	}

	public int getIcongroesse() {
		return icongroesse;
	}

	public void setIcongroesse(int icongroesse) {
		this.icongroesse = icongroesse;
	}

	public boolean isGehalten() {
		return gehalten;
	}

	public void setGehalten(boolean gehalten) {
		this.gehalten = gehalten;
	}

	public int getInAquarium() {
		return inAquarium;
	}

	public void setInAquarium(int inAquarium) {
		this.inAquarium = inAquarium;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public boolean isInTabelle() {
		return inTabelle;
	}

	public void setInTabelle(boolean inTabelle) {
		this.inTabelle = inTabelle;
	}


}
