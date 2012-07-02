/*
 * 2010, http://github.com/pauloewerton/partSOM4Grid
 * This file is part of partSOM4Grid 
 *
 * partSOM4Grid is free software: you can redistribute it and/or modify it under the
 * terms of the Artistic License 2.0 as published by the OSI.
 * 
 * This program is distributed in hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
 * FITNESS FOR A PARTICULAR PURPOSE. See the Artistic License 2.0
 * for more details. 
 * 
 * You should have received a copy of the Artistic License 2.0
 * along with this program. See <www.opensource.org/licenses/artistic-license-2.0>.
 * 
 */

package partsom.clustering;

public class KMeans {

	private double[][] dados;
	private double[][] centroides;
	private int[] classes;
	private int k;
	private int nDados;
	private int dim;

    public KMeans(double[][] dados, int k) throws KMeansException {

		if(dados == null || dados.length <= 0 || k <= 0 || dados.length < k)
			throw new KMeansException("Dados de entrada ou numero de grupos invalido !!!");
		this.dados = dados;
		this.k = k;
		this.nDados = dados.length;
		this.dim = dados[0].length;		
		this.inicializar();
	}
	
	public void make() {
		if(dados == null || centroides == null || nDados != dados.length || k != centroides.length)
			throw new RuntimeException("Algorítmo inconsistente !!!");
		boolean convergencia = false;
		while(!convergencia){
			convergencia = classifica();
			calculaCentroides();
		}
	}

	private void inicializar(){

		this.centroides = new double[k][dim];
		this.classes = new int[nDados];
		
		for(int i = 0; i < k; i++)

			for(int j = 0; j < dim; j++)

				centroides[i][j] = dados[i][j];
		
		for(int i = 0; i < nDados; i++)
			classes[i] = 0;
	}
		
	private void calculaCentroides(){

		int[] count = new int[k];
		double[][] centroides2 = new double[k][dim];

		for(int i = 0; i < nDados; i++){

			int centI = classes[i];

			for(int j = 0; j < dim; j++)
				centroides2[centI][j] += dados[i][j];
			count[centI] += 1;
		}

		for(int i = 0; i < k; i++){

			if(count[i] != 0){

				for(int j = 0; j < dim; j++)

					centroides[i][j] = centroides2[i][j]/count[i];
			}
		}
	}

	private boolean classifica(){
		boolean convergencia = true;

		for(int i = 0; i < nDados; i++){
			double[] dado = dados[i];
			double distanceMin = Double.MAX_VALUE;
			int ind = 0;

			for(int j = 0; j < k; j++){
				double[] centroide = centroides[j];
				double dist = distanceEuclidiana(dado, centroide);

				if(dist < distanceMin){
					distanceMin = dist;
					ind = j;
				}
			}
			
			if(classes[i] != ind){
				classes[i] = ind;
				convergencia = false;
			}
		}
		return convergencia;
	}

	private double distanceEuclidiana(double[] dado, double[] centroide){
		double sum = 0;
		for(int j = 0; j < dim; j++)
			sum += Math.pow(Math.abs(dado[j]-centroide[j]), 2);
		return Math.sqrt(sum);
	}
	
	public int[] sortCentroides() {
		if(centroides.length != k)
			throw new RuntimeException("Algorítmo inconsistente !!!");
		boolean isOrder;
		int[] classesMap = new int[k];		
		for(int i = 0; i < classesMap.length; i++)
			classesMap[i] = i;
		do{
			isOrder = true;
			for(int i = 0; i < (k-1); i++){
				double[] c1 = centroides[i];
				double[] c2 = centroides[i+1];
				double sum1 = 0;
				double sum2 = 0;
				int ind1 = classesMap[i];
				int ind2 = classesMap[i+1];
				for(int j = 0; j < dim; j++){
					sum1 += c1[j];
					sum2 += c2[j];
				}
				if(sum1 > sum2){
					centroides[i] = c2;
					centroides[i+1] = c1;
					classesMap[i] = ind2;
					classesMap[i+1] = ind1;
					isOrder = false;
				}

			}
		}while(!isOrder);
		int[] classesMapAux = (int[])classesMap.clone();
		for(int i = 0; i < k; i++)
			classesMap[classesMapAux[i]] = i;
		return classesMap;
	}
		
	public double[][] getCentroides() {
		return centroides;
	}

	public int[] getClasses() {
		return classes;
	}

	public double[][] getDados() {
		return dados;
	}

	public int getDim() {
		return dim;
	}

	public int getK() {
		return k;
	}

	public int getNDados() {
		return nDados;
	}

}
