package spiel3Klassen;

import java.awt.*;
import java.util.*;

public class Grapherzeugen <T>{
	private int [][] adjMatrix;
	private int anzahlKnoten;
	private Graph g= new Graph();
	private LinkedList ausgesucht;
	private int start;
	private int schranke;
	 boolean[] selectedb ;
	
	public Grapherzeugen(int anzahlKnoten) {
		setAdjMatrix(new int[15][15]);
		this.anzahlKnoten=anzahlKnoten;
		ausgesucht= new LinkedList(); 
		create();
		fillMatrix();
		choseFromAll();
		createmissingKanten();
		gewichteErzeugen();
		Prim();

	}
	public void fillMatrix() {
		for(int i=0; i< getAdjMatrix().length; i++) {
			for(int j=0;j<getAdjMatrix().length;j++) {
				getAdjMatrix()[i][j]=0;
			}
		}
	}
	
	public void choseFromAll() {

		Random generator = new Random();
		
		int knoten1=generator.nextInt(15);
		int knoten2Index;
		int knotenAnzahl= g.getKnotenCount(knoten1);
		knoten2Index=generator.nextInt(knotenAnzahl);
		int wert=g.getMapValue(knoten1, knoten2Index);

		ausgesucht.add(knoten1);
		
		while(ausgesucht.size()!=anzahlKnoten) {
			
			if(!ausgesucht.contains(wert)) {
				ausgesucht.add(wert);
			}
			getAdjMatrix()[knoten1][wert]=1;
			getAdjMatrix()[wert][knoten1]=1;
			knoten1=wert;
			int runden=0;
			loop: do{
				runden++;
				knotenAnzahl= g.getKnotenCount(knoten1);
				knoten2Index=generator.nextInt(knotenAnzahl);
				wert=g.getMapValue(knoten1, knoten2Index);
				if(runden==knotenAnzahl) {
					break loop;
				}
			}while(getAdjMatrix()[knoten1][wert]==1);
		}	
	}
	
	public void createmissingKanten() {
		for(int i=0; i<ausgesucht.size();i++) {
			for(int j=ausgesucht.size()-1; j>0;j--) {
				int element= (int) ausgesucht.get(i);
				int element2= (int) ausgesucht.get(j);
				if(g.hasKante(element, element2)) {
					getAdjMatrix()[element][element2]=1;
					getAdjMatrix()[element2][element]=1;
				}
			}
		}
	}
	
	public void gewichteErzeugen() {
		Random generator = new Random();
		for(int i =0;i<getAdjMatrix().length;i++) {
			for(int j=0; j<getAdjMatrix().length;j++) {
				if(getAdjMatrix()[i][j]==1) {
					int gewicht=generator.nextInt(20)+1;
					getAdjMatrix()[i][j]=gewicht;
					getAdjMatrix()[j][i]=gewicht;
				}else if(getAdjMatrix()[j][i]==0){
					getAdjMatrix()[j][i]=0;
					getAdjMatrix()[i][j]=0;
				}
			}
		}
	}
	
	public void Prim( ) {
		 int unendlich = 9999999;
		 int number_edge=0;
		 schranke=0;
		
		  selectedb = new boolean[getAdjMatrix().length];
		
		 Arrays.fill(selectedb, false);
		 
		 setStart((int) ausgesucht.get(0));
		 selectedb[(int) ausgesucht.get(0)] = true;
		 int minimum;

		 while (number_edge < anzahlKnoten - 1) {
		   minimum = unendlich;
		   int x = 0; 
		   int y = 0;
		   for (int i = 0; i < getAdjMatrix().length; i++) {
		     if (selectedb[i] == true) {
		       for (int j = 0; j < getAdjMatrix().length; j++) {
		         if (!selectedb[j] && getAdjMatrix()[i][j] != 0) {
		           if (minimum > getAdjMatrix()[i][j]) {
		        	   minimum = getAdjMatrix()[i][j];
		        	   x = i;
		        	   y = j;
		           }
		         }
		       }
		     }
		   }
		   //System.out.println(x + " - " + y + " :  " + adjMatrix[x][y]);
		   schranke+=getAdjMatrix()[x][y];
		   selectedb[y] = true;
		   number_edge++;
		 }
	}
	

	public boolean[] getSelectedb() {
		return selectedb;
	}
	public void setSelectedb(boolean[] selectedb) {
		this.selectedb = selectedb;
	}
	public void test() {
		for(int i =0;i<getAdjMatrix().length;i++) {
			for(int j=0; j<getAdjMatrix().length;j++) {
				if(getAdjMatrix()[i][j]!=0) {
					//System.out.println("i: "+i+", j:"+j+" Matrix Wert:"+getAdjMatrix()[i][j]);
				}
			}
		}
		//System.out.println("Ende");
	}
	
	public void create() {
		g.addKante(0, 2, true);
	    g.addKante(2, 6, true);
	    g.addKante(6, 9, true);
	    g.addKante(9, 14, true);
	    g.addKante(14, 8, true);
	    g.addKante(8, 4, true);
	    g.addKante(13, 4, true);
	    g.addKante(13, 3, true);
	    g.addKante(8, 7, true);
	    g.addKante(7, 14, true);
	    
	    g.addKante(7, 9, true);
	    //g.addKante(7, 6, true);
	    g.addKante(7, 11, true);
	    g.addKante(7, 5, true);
	    g.addKante(7, 10, true);
	    //g.addKante(7, 4, true);
	    g.addKante(4, 10, true);
	    //g.addKante(13, 10, true);
	    g.addKante(10, 3, true);
	    g.addKante(3, 1, true);
	    
	    g.addKante(1, 10, true);
	    g.addKante(1, 12, true);
	    g.addKante(12, 10, true);
	    //g.addKante(10, 5, true);
	    g.addKante(5, 12, true);
	    //g.addKante(5, 11, true);
	    g.addKante(0, 5, true);
	    g.addKante(12, 0, true);
	    g.addKante(0, 11, true);
	    g.addKante(2, 11, true);
	    
	    g.addKante(11, 6, true);
	    //g.addKante(12, 2, true);
	   // g.addKante(2, 9, true);
	    //g.addKante(6, 14, true);
	    //g.addKante(8, 9, true);
	    g.addKante(8, 10, true);
	    //g.addKante(13, 8, true);
	    g.addKante(4, 3, true);
	   // g.addKante(13, 1, true);
	    //g.addKante(3, 12, true);
	    
	    //g.addKante(0, 1, true);
	    g.addKante(12, 11, true);
	    //g.addKante(3, 12, true);
	    g.addKante(5, 6, true);
	    g.addKante(11, 9, true);
	    //g.addKante(0, 6, true);
	    //g.addKante(4, 14, true);
	    //g.addKante(8, 9, true);
	    g.addKante(8, 5, true);
	    //g.addKante(5, 4, true);
	}
	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getSchranke() {
		return schranke;
	}
	public void setSchranke(int schranke) {
		this.schranke = schranke;
	}
	public int [][] getAdjMatrix() {
		return adjMatrix;
	}
	
	public int getAdjMatrixIndex(int index1,int index2) {
		return adjMatrix[index1][index2];
	}
	public void setAdjMatrix(int [][] adjMatrix) {
		this.adjMatrix = adjMatrix;
	}
	
	public LinkedList getAusgesucht() {
		return ausgesucht;
	}
	
	public int getAusgesuchtIndex(int index) {
		return (int) ausgesucht.get(index);
	}

	public void setAusgesucht(LinkedList ausgesucht) {
		this.ausgesucht = ausgesucht;
	}
}
