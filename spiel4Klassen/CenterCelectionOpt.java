package spiel4Klassen;
//Jonte Jakob 7380571
public class CenterCelectionOpt{
	private int[] abstand = new int[100];
	private int opt = Integer.MAX_VALUE;
	private int start;
	private int[] maxdistance;
	private Dorf[] doerfer;
	public CenterCelectionOpt(Dorf[] doerfer, int burgenAnzahl) {
		this.doerfer = doerfer;
		maxdistance = new int[doerfer.length];
		
		for (int i = 0; i < 100; i++) {
			for (int g = 0 ; g < 100 ; g++) {
				maxdistance[g] = Integer.MAX_VALUE;
			}
			if (doerfer[i].isExistiert() == true) {
				start = i;
			
				dijkstras(start);
				int j = 0;
				while(j<burgenAnzahl - 1) {
					int distance_temp = 0;
					for (int k = 0; k < doerfer.length;k++) {
						if (doerfer[k].isExistiert() == true && maxdistance[k] > distance_temp) {
							start = k;
							distance_temp = maxdistance[k];
						}
					}
					dijkstras(start);
					j++;
				}
				int abstandtemp = 0;
				for (int h = 0; h < doerfer.length; h++) {
					if (doerfer[h].isExistiert() == true && maxdistance[h] > abstandtemp) {
						abstandtemp = maxdistance[h];
					}
				}
				abstand[i] = abstandtemp;
			}
		}
		for (int i = 0; i<doerfer.length; i++) {
			if (doerfer[i].isExistiert() == true && opt > abstand[i]) {
				opt = abstand[i];
			}
		}
		System.out.println(opt);
	}
	
	public void dijkstras(int start) {
		int u;
		int[] distance = new int [doerfer.length];
		boolean[] queue = new boolean[doerfer.length];
		for (int i = 0; i < doerfer.length; i++) {
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

	public int getOpt() {
		return opt;
	}

	public void setOpt(int opt) {
		this.opt = opt;
	}
}
