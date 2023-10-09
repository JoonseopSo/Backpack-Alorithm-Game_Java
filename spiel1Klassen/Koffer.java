package spiel1Klassen;

public class Koffer {
	private boolean kofferistda;
	private int kofferx_links;
	private int koffery_oben;
	private int kofferx_rechts;
	private int koffery_unten;
	private boolean itemImKoffer;
	private int start_koffer_x;
	private int start_koffer_y;
	private boolean koffer_bewegt;
	private int koffergewicht;
	private boolean kofferaction;
	private boolean kofferauffeld;
	private boolean kofferclicked;
	
	
	public boolean isKofferclicked() {
		return kofferclicked;
	}


	public void setKofferclicked(boolean kofferclicked) {
		this.kofferclicked = kofferclicked;
	}


	public Koffer() {
		setKofferclicked(false);
		setKoffer_bewegt(false);
		setKofferaction(false);
		setKofferauffeld(true);
	}
	
	
	public boolean isKoffer_bewegt() {
		return koffer_bewegt;
	}


	public void setKoffer_bewegt(boolean koffer_bewegt) {
		this.koffer_bewegt = koffer_bewegt;
	}


	public int getStart_koffer_x() {
		return start_koffer_x;
	}
	public void setStart_koffer_x(int start_koffer_x) {
		this.start_koffer_x = start_koffer_x;
	}
	public int getStart_koffer_y() {
		return start_koffer_y;
	}



	public void setStart_koffer_y(int start_koffer_y) {
		this.start_koffer_y = start_koffer_y;
	}
	private int gewichtsschranke;
	
	
	public int getGewichtsschranke() {
		return gewichtsschranke;
	}
	public void setGewichtsschranke(int gewichtsschranke) {
		this.gewichtsschranke = gewichtsschranke;
	}
	public boolean isKofferistda() {
		return kofferistda;
	}
	public int getKoffergewicht() {
		return koffergewicht;
	}


	public void setKoffergewicht(int koffergewicht) {
		this.koffergewicht = koffergewicht;
	}


	public void setKofferistda(boolean kofferistda) {
		this.kofferistda = kofferistda;
	}

	public int getKofferx_links() {
		return kofferx_links;
	}
	public void setKofferx_links(int kofferx_links) {
		this.kofferx_links = kofferx_links;
	}
	public int getKoffery_oben() {
		return koffery_oben;
	}
	public boolean isKofferauffeld() {
		return kofferauffeld;
	}


	public void setKofferauffeld(boolean kofferauffeld) {
		this.kofferauffeld = kofferauffeld;
	}


	public void setKoffery_oben(int koffery_oben) {
		this.koffery_oben = koffery_oben;
	}
	public int getKofferx_rechts() {
		return kofferx_rechts;
	}
	public void setKofferx_rechts(int kofferx_rechts) {
		this.kofferx_rechts = kofferx_rechts;
	}
	public int getKoffery_unten() {
		return koffery_unten;
	}
	public void setKoffery_unten(int koffery_unten) {
		this.koffery_unten = koffery_unten;
	}
	public boolean isItemImKoffer() {
		return itemImKoffer;
	}
	public void setItemImKoffer(boolean itemImKoffer) {
		this.itemImKoffer = itemImKoffer;
	}


	public boolean isKofferaction() {
		return kofferaction;
	}


	public void setKofferaction(boolean kofferaction) {
		this.kofferaction = kofferaction;
	}

}
