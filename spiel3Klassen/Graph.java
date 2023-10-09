package spiel3Klassen;

import java.util.*;

public class Graph<T> {

	private Map<T, List<T> > map = new HashMap<>();

	 public void addKnoten(T s){
	     map.put(s, new LinkedList<T>());
	 }

	 public void addKante(T von,T nach, boolean ungerichtet){
	     if (!map.containsKey(von)) {
	    	 addKnoten(von);
	     }
	     if (!map.containsKey(nach)) {
	    	 addKnoten(nach);
	     }
	     map.get(von).add(nach);
	     if (ungerichtet == true) {
	         map.get(nach).add(von);
	     }
	 }
	 public int getKnotenCount( int knoten){
	         return map.get(knoten).size();
	 }
	 
	 public boolean hasKante(T s, T d){
	     if (map.get(s).contains(d)) {
	         return true;
	     }
	     else {
	         return false;
	     }
	 }
	 
	 public boolean hasKnoten(T s){
	     if (map.containsKey(s)) {
	         return true;
	     }
	     else {
	         return false;
	     }
	 }

	public Map<T, List<T>> getMap() {
		return map;
	}

	public void setMap(Map<T, List<T>> map) {
		this.map = map;
	}
	
	public Integer getMapValue(int indexKnoten,int  indexinListe) {
		return (Integer) map.get(indexKnoten).get(indexinListe);
	}
}