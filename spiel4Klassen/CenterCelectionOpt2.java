package spiel4Klassen;
//Jonte Jakob 7380571
public class CenterCelectionOpt2 {
	private int[] abstand = new int[100];
	private int opt = Integer.MAX_VALUE;
	private int opt_temp = 0;
	private int start;
	private int[][] maxdistance;
	private Dorf[] doerfer;
	private String schwere;
	private int picked;
	private boolean[] optDoerfer;
	
	public CenterCelectionOpt2(Dorf[] doerfer, int burgenAnzahl, String schwere) {
		this.doerfer = doerfer;
		maxdistance = new int[doerfer.length][burgenAnzahl];
		optDoerfer = new boolean[doerfer.length];
		for (int a = 0; a < doerfer.length; a++) {
			optDoerfer[a] = false;
			for(int s = 0; s < burgenAnzahl; s++) {
				maxdistance[a][s] = Integer.MAX_VALUE;
			}
		}
		
		for (int i = 0; i < doerfer.length; i++) {
			if (doerfer[i].isExistiert() == true) {
				picked = 1;
				for (int a = 0; a < doerfer.length; a++) {
						maxdistance[a][0] = Integer.MAX_VALUE;
				}
				dijkstras(i,0);
				for (int j = i+1; j < doerfer.length; j++) {
					if (doerfer[j].isExistiert() == true) {
						picked = 2;
						for (int a = 0; a < doerfer.length; a++) {
							maxdistance[a][1] = Integer.MAX_VALUE;
						}
						dijkstras(j,1);
						for (int k = j+1; k < doerfer.length; k++) {
							picked = 3;
							if (doerfer[k].isExistiert() == true) {
								for (int a = 0; a < doerfer.length; a++) {
									maxdistance[a][2] = Integer.MAX_VALUE;
								}
								dijkstras(k,2);
								if (schwere == "Training" && picked == 3) {
									opt_temp = 0;
									for (int t = 0; t<doerfer.length; t++) {
										if (doerfer[t].isExistiert() == true && opt_temp < maxdistance[t][2]) {
											opt_temp = maxdistance[t][2];
										}
									}
									if (opt_temp < opt) {
										for (int r = 0; r < doerfer.length; r++) {
											optDoerfer[r] = false;
										}
										optDoerfer[i] = true;
										optDoerfer[j] = true;
										optDoerfer[k] = true;
										opt = opt_temp;
									}
								}
								if (schwere == "Mittel" || schwere == "Schwer") {
									for (int h = k+1; h < doerfer.length; h++) {
										picked = 4;
										if (doerfer[h].isExistiert() == true) {
											for (int a = 0; a < doerfer.length; a++) {
												maxdistance[a][3] = Integer.MAX_VALUE;
											}
											dijkstras(h,3);
											for (int g = h+1; g < doerfer.length; g++) {
												picked = 5;
												if (doerfer[g].isExistiert() == true) {
													for (int a = 0; a < doerfer.length; a++) {
														maxdistance[a][4] = Integer.MAX_VALUE;
												}
													dijkstras(g,4);
													if (schwere == "Schwer" && picked == 5) {
														opt_temp = 0;
														for (int t = 0; t<doerfer.length; t++) {
															if (doerfer[t].isExistiert() == true && opt_temp < maxdistance[t][4]) {
																opt_temp = maxdistance[t][4];
																
															}
														}
														if (opt_temp < opt) {
															for (int r = 0; r < doerfer.length; r++) {
																optDoerfer[r] = false;
															}
															optDoerfer[i] = true;
															optDoerfer[j] = true;
															optDoerfer[k] = true;
															optDoerfer[h] = true;
															optDoerfer[g] = true;
															opt = opt_temp;
														}
													}
													if (schwere == "Mittel") {
														for (int f = g+1; f < doerfer.length; f++) {
															picked = 6;
															if (doerfer[f].isExistiert() == true) {
																for (int a = 0; a < doerfer.length; a++) {
																	maxdistance[a][5] = Integer.MAX_VALUE;
															}
																dijkstras(f,5);
																if (schwere == "Mittel" && picked == 6) {
																	opt_temp = 0;
																	for (int t = 0; t<doerfer.length; t++) {
																		if (doerfer[t].isExistiert() == true && opt_temp < maxdistance[t][5]) {
																			opt_temp = maxdistance[t][5];
																			
																		}
																	}
																	if (opt_temp < opt) {
																		for (int r = 0; r < doerfer.length; r++) {
																			optDoerfer[r] = false;
																		}
																		optDoerfer[i] = true;
																		optDoerfer[j] = true;
																		optDoerfer[k] = true;
																		optDoerfer[h] = true;
																		optDoerfer[g] = true;
																		optDoerfer[f] = true;
																		opt = opt_temp;
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void dijkstras(int start, int zeile) {
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
			if (zeile == 0) {	
				maxdistance[i][zeile] = distance[i];
			}
			else if (maxdistance[i][zeile-1] > distance[i]) {	
				maxdistance[i][zeile] = distance[i];
			}
			else {
				maxdistance[i][zeile] = maxdistance[i][zeile-1];
			}
		}
	}

	public int getOpt() {
		return opt;
	}

	public void setOpt(int opt) {
		this.opt = opt;
	}

	public boolean[] getOptDoerfer() {
		return optDoerfer;
	}

	public void setOptDoerfer(boolean[] optDoerfer) {
		this.optDoerfer = optDoerfer;
	}
}
