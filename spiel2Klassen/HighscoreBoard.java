package spiel2Klassen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class HighscoreBoard {
	
	private String name1;
	private int score1;
	
	private String name2;
	private int score2;
	
	private String name3;
	private int score3;
	
	public HighscoreBoard() {
		//highscoresLesen();
		//highscoresAusgebenBR();
	}
	
	public void highscoresLesen() {
		String pfad = this.getClass().getResource("/resources/highscoresSpiel2.txt").getPath();
		
		File datei = new File(pfad);
		
		try {
			Scanner scanner = new Scanner(datei);
			
			int zeile = 0;
			while(scanner.hasNextLine() && zeile <= 6) {
				zeile++;
				
				if(zeile == 1) {
					name1 = scanner.next();
					continue;
				}
				
				if(zeile == 2) {
					score1 = Integer.parseInt(scanner.next());
					continue;
				}
				
				if(zeile == 3) {
					name2 = scanner.next();
					continue;
				}
				
				if(zeile == 4) {
					score2 = Integer.parseInt(scanner.next());
					continue;
				}
				
				if(zeile == 5) {
					name3 = scanner.next();
					continue;
				}
				
				if(zeile == 6) {
					score3 = Integer.parseInt(scanner.next());
					continue;
				}
			}
			scanner.close();
			
			
		} catch (FileNotFoundException e) {
			System.out.println("Datei nicht gefunden!");
			e.printStackTrace();
		}
	}
	
	public void highscoreUebernehmen(String name, int score) {
		
		// Entsprechenden Score ueberschreiben
		if(score >= score3 && score < score2) {
			name3 = name;
			score3 = score;
		}
		else if(score >= score2 && score < score1){
			name2 = name;
			score2 = score;
		}
		else if(score >= score1) {
			name1 = name;
			score1 = score;
		}
		
		String pfad = this.getClass().getResource("/resources/highscoresSpiel2.txt").getPath();
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(pfad));
			writer.write(name1 + "\n" );
			writer.write(score1 + "\n" );
			writer.write(name2 + "\n" );
			writer.write(score2 + "\n" );
			writer.write(name3 + "\n" );
			writer.write(score3 + "\n" );
			writer.close();
			
		} catch (IOException e) {
			System.out.println("Writer hat Datei nicht gefunden!");
			e.printStackTrace();
		}
	}
	
	public void highscoresAusgebenBR() {
		String pfad = this.getClass().getClassLoader().getResource("resources/highscoresSpiel2.txt").getPath();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(pfad));
			
			String ausgabe;
			while((ausgabe = reader.readLine()) != null) {
				System.out.println(ausgabe);
			}
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Reader hat Datei nicht gefunden!");
			e.printStackTrace();
		}
	}
	
	public void highscoresAusgeben() {
		System.out.println(name1);
		System.out.println(score1);
		System.out.println(name2);
		System.out.println(score2);
		System.out.println(name3);
		System.out.println(score3);
	}

}
