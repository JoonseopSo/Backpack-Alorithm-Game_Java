package spiel1Klassen;

public class Items {
	private boolean dragged;
	private int itemx;
	private int itemy;
	private boolean bewegt;
	private int s_x;
	private int s_y;
	private int a_x;
	private int a_y;
	private boolean gelegt;
	private int gewicht;
	private int wert;
	private boolean itemaufmaus;

	public Items() {
		setBewegt(false);
		setGelegt(false);
		setDragged(false);
		setItemaufmaus(false);

	}

	public int getGewicht() {
		return gewicht;
	}

	public void setGewicht(int gewicht) {
		this.gewicht = gewicht;
	}

	public int getWert() {
		return wert;
	}

	public void setWert(int wert) {
		this.wert = wert;
	}


	public boolean isGelegt() {
		return gelegt;
	}

	public void setGelegt(boolean b) {
		this.gelegt = b;
	}

	public boolean isBewegt() {
		return bewegt;
	}

	public void setBewegt(boolean bewegt) {
		this.bewegt = bewegt;
	}

	public int getS_x() {
		return s_x;
	}

	public void setS_x(int s_x) {
		this.s_x = s_x;
	}

	public int getS_y() {
		return s_y;
	}

	public void setS_y(int s_y) {
		this.s_y = s_y;
	}

	public int getA_x() {
		return a_x;
	}

	public void setA_x(int a_x) {
		this.a_x = a_x;
	}

	public int getA_y() {
		return a_y;
	}

	public void setA_y(int a_y) {
		this.a_y = a_y;
	}

	public int getItemx() {
		return itemx;
	}

	public void setItemx(int itemx) {
		this.itemx = itemx;
	}

	public int getItemy() {
		return itemy;
	}

	public void setItemy(int itemy) {
		this.itemy = itemy;
	}

	public boolean isDragged() {
		return dragged;
	}

	public void setDragged(boolean dragged) {
		this.dragged = dragged;
	}

	public boolean isItemaufmaus() {
		return itemaufmaus;
	}

	public void setItemaufmaus(boolean itemaufmaus) {
		this.itemaufmaus = itemaufmaus;
	}

}
