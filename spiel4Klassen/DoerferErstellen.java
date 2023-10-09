package spiel4Klassen;
//Jonte Jakob 7380571
import java.util.Random;

public class DoerferErstellen {
	
	public DoerferErstellen() {
		
	}
	public Dorf[] erstelleDoerfer(int anzahl) {
		Dorf[] doerfer = new Dorf[100];
		for (int i = 0 ; i < 100 ; i++) {
			doerfer[i] = new Dorf();
			doerfer[i].setExistiert(false);
		}
		int j = 0;
		while (j < anzahl) {
			Random rand = new Random();
			int dorfnummer = rand.nextInt(100);
			if (doerfer[dorfnummer].isExistiert() == false) {
				doerfer[dorfnummer].setExistiert(true);
				j++;				
			}
		}
		return doerfer;
	}
}
