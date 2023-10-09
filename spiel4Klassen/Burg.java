package spiel4Klassen;
//Jonte Jakob 7380571
public class Burg {
	private int burgX;
	private int burgY;
	private boolean gotMoved;
	private int startX;
	private int startY;
	private int altX;
	private int altY;
	private int placed;
	
	public Burg(){
		setGotMoved(false);
		placed = -1;
	}
	
	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public int getAltX() {
		return altX;
	}

	public void setAltX(int altX) {
		this.altX = altX;
	}

	public int getAltY() {
		return altY;
	}

	public void setAltY(int altY) {
		this.altY = altY;
	}

	public int getBurgX() {
		return burgX;
	}

	public void setBurgX(int burgX) {
		this.burgX = burgX;
	}

	public int getBurgY() {
		return burgY;
	}

	public void setBurgY(int burgY) {
		this.burgY = burgY;
	}

	

	public boolean isGotMoved() {
		return gotMoved;
	}

	public void setGotMoved(boolean gotMoved) {
		this.gotMoved = gotMoved;
	}

	public int getPlaced() {
		return placed;
	}

	public void setPlaced(int placed) {
		this.placed = placed;
	}
}
