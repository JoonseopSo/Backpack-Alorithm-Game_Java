package spiel4Klassen;
// Jonte Jakob 7380571
public class Dorf {
	private boolean existiert;
	private int dorfX;
	private int dorfY;
	private boolean gotBurg;
	
	public boolean isExistiert() {
		return existiert;
	}

	public void setExistiert(boolean existiert) {
		this.existiert = existiert;
	}

	public int getDorfY() {
		return dorfY;
	}

	public void setDorfY(int dorfY) {
		this.dorfY = dorfY;
	}

	public int getDorfX() {
		return dorfX;
	}

	public void setDorfX(int dorfX) {
		this.dorfX = dorfX;
	}

	public boolean isGotBurg() {
		return gotBurg;
	}

	public void setGotBurg(boolean gotBurg) {
		this.gotBurg = gotBurg;
	}
}
