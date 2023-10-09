package spiel4Klassen;
//Jonte Jakob 7380571
public class CenterCelectionPlayer {
	private int opt = Integer.MAX_VALUE;
	private int[] maxdistance;
	private Dorf[] doerfer;
	public CenterCelectionPlayer(Dorf[] doerfer, int start) {
		this.doerfer = doerfer;
		maxdistance = new int[doerfer.length];
		
			for (int g = 0 ; g < 100 ; g++) {
				maxdistance[g] = Integer.MAX_VALUE;
			}
			
			dijkstras(start);
		
			int abstandtemp = 0;
			for (int h = 0; h < doerfer.length; h++) {
				if (doerfer[h].isExistiert() == true && maxdistance[h] > abstandtemp) {
					abstandtemp = maxdistance[h];
				}
			}
			opt = abstandtemp;
		
		
		
	}
	
	public int getOpt() {
		return opt;
	}

	public void setOpt(int opt) {
		this.opt = opt;
	}

	public int[] getMaxdistance() {
		return maxdistance;
	}

	public void setMaxdistance(int[] maxdistance) {
		this.maxdistance = maxdistance;
	}

	public void dijkstras(int start) {
		int u;
		int[] distance = new int [doerfer.length];
		boolean[] queue = new boolean[doerfer.length];
		for (int i = 0; i < 100; i++) {
			distance[i] = Integer.MAX_VALUE;
			queue[i] = false;
		}
		distance[start] = 0;
		for (int i = 0; i < doerfer.length; i++) {
			int min = Integer.MAX_VALUE;
			int vertex = -1;
			for (int j = 0; j < doerfer.length; j++) {
				if (queue[j] == false && distance[j] < min) {
					min = distance[j];
					vertex = j;
				}
			}
			u = vertex;
			queue[u] = true;
			if (u < 9) {
				if (u != 0) {
					if(queue[u-1] == false && distance[u] + 1 < distance[u-1]) {
						distance[u-1] = distance[u] +1;
					}
				}
				if (u != 9) {
					if(queue[u+1] == false && distance[u] + 1 < distance[u+1]) {
						distance[u+1] = distance[u] +1;
					}
				}
				if(queue[u+10] == false && distance[u] + 1 < distance[u+10]) {
					distance[u+10] = distance[u] +1;
				}
			}
			else if (u%10 == 9) {
				if(queue[u-1] == false && distance[u] + 1 < distance[u-1]) {
					distance[u-1] = distance[u] +1;
				}
				if (u != 9) {
					if(queue[u-10] == false && distance[u] + 1 < distance[u-10]) {
						distance[u-10] = distance[u] +1;
					}
				}
				if (u != 99) {
					if(queue[u+10] == false && distance[u] + 1 < distance[u+10]) {
						distance[u+10] = distance[u] +1;
					}
				}
			}
			else if (u%10 == 0) {
				if (u != 0) {
					if(queue[u-10] == false && distance[u] + 1 < distance[u-10]) {
						distance[u-10] = distance[u] +1;
					}
				}
				if(queue[u+1] == false && distance[u] + 1 < distance[u+1]) {
					distance[u+1] = distance[u] +1;
				}
				if(u != 90) {
					if(queue[u+10] == false && distance[u] + 1 < distance[u+10]) {
						distance[u+10] = distance[u] +1;
					}
				}
			}
			else if (u > 89) {
				if (u != 90) {
					if(queue[u-1] == false && distance[u] + 1 < distance[u-1]) {
						distance[u-1] = distance[u] +1;
					}
				}
				if (u != 99) {
					if(queue[u+1] == false && distance[u] + 1 < distance[u+1]) {
						distance[u+1] = distance[u] +1;
					}
				}
				if(queue[u-10] == false && distance[u] + 1 < distance[u-10]) {
					distance[u-10] = distance[u] +1;
				}
			}
			else {
				if(queue[u-1] == false && distance[u] + 1 < distance[u-1]) {
					distance[u-1] = distance[u] +1;
				}
				if(queue[u+1] == false && distance[u] + 1 < distance[u+1]) {
					distance[u+1] = distance[u] +1;
				}
				if(queue[u+10] == false && distance[u] + 1 < distance[u+10]) {
					distance[u+10] = distance[u] +1;
				}
				if(queue[u-10] == false && distance[u] + 1 < distance[u-10]) {
					distance[u-10] = distance[u] +1;
				}
			}
		}
		for (int i = 0 ; i < doerfer.length ; i++) {
			if (maxdistance[i] > distance[i]) {	
				maxdistance[i] = distance[i];
			}
		}
	}
}
