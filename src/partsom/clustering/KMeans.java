/*
 * 2010, http://partSOM4OurGrid.sourceforge.net
 * This file is part of partSOM4OurGrid 
 *
 * partSOM4OurGrid is free software: you can redistribute it and/or modify it under the
 * terms of the Artistic License 2.0 as published by the OSI.
 * 
 * This program is distributed in hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
 * FITNESS FOR A PARTICULAR PURPOSE. See the Artistic License 2.0
 * for more details. 
 * 
 * You should have received a copy of the Artistic License 2.0
 * along with this program. If not, see <http://www.osi.org/licenses/>.
 * 
 */

package partsom.clustering;

public class KMeans {

	private double[][] dados; //Conjunto de entradas (linhas) da tabela
	private double[][] centroides; //Matriz com os centr�ides?
	private int[] classes; //Matriz com as classes?
	private int k; //Vari�vel desconhecida
	private int nDados; //N�mero de linhas da tabela
	private int dim; //N�mero de colunas da tabela

	//Construtor recebe o conjunto de entrada e 'k' como argumentos
    public KMeans(double[][] dados, int k) throws KMeansException {
        //Se o conjunto de entrada n�o tiver elementos, ou se a quantidade de elementos for menor ou igual
        //a zero, ou 'k' for menor ou igual a zero, ou a quantidade de elementos for menor ou igual a 'k'
        //invalide o conjunto de entrada
		if(dados == null || dados.length <= 0 || k <= 0 || dados.length < k)
			throw new KMeansException("Dados de entrada ou numero de grupos invalido !!!");
        //Incializa��o incial
		this.dados = dados;
		this.k = k;
		this.nDados = dados.length;
		this.dim = dados[0].length;		
		this.inicializar();
	}
	
	public void make() {
		if(dados == null || centroides == null || nDados != dados.length || k != centroides.length)
			throw new RuntimeException("Algor�tmo inconsistente !!!");
		boolean convergencia = false;
		while(!convergencia){
			convergencia = classifica();
			calculaCentroides();
		}
	}

	private void inicializar(){
        //Centroides � matriz de dimens�o formada por n�mero 'k' de linhas e n�mero 'dim'
        //(quantidade de atributos do conjunto de entrada, colunas) de colunas
		this.centroides = new double[k][dim];
        //Classes � vetor de tamanho igual a quantidade de dados do conjunto de entrada
		this.classes = new int[nDados];
        //La�o que itera sobre o n�mero de linhas
		for(int i = 0; i < k; i++)
            //La�o que itera sobre o n�mero de colunas
			for(int j = 0; j < dim; j++)
                //Centroides recebe exatemente os mesmos elementos do conjunto de entrada
				centroides[i][j] = dados[i][j];
        //La�o que itera sobre o n�mero de linhas
		for(int i = 0; i < nDados; i++)
            //Vetor classes � totalmente preenchido com zero
			classes[i] = 0;
	}
		
	private void calculaCentroides(){
        //Count � vetor de tamanho igual a 'k' (acho que � o n�mero de linhas do conjunto de entrada
        //ou seja k == nDados)
		int[] count = new int[k];
        //C�pia da matriz de centroides (mesmo tamanho)
		double[][] centroides2 = new double[k][dim];
        //La�o que itera sobre a quantidade de linhas
		for(int i = 0; i < nDados; i++){
            //centI recebe classes[i], ou seja, zero
			int centI = classes[i];
            //La�o que itera sobre quantidade de colunas
			for(int j = 0; j < dim; j++)
                //Matriz c�pia de centroides preenchida somando-se o conte�do de cada �ndice de
                //centroides2 com o conte�do do conjunto de dados
                //Ou seja, linha '0' de centroides ter� seus valores recalculados pelo som�torio
                //a cada itera��o
				centroides2[centI][j] += dados[i][j];
            //count que � vetor igual a 'k', em seu �ndice '0' (centI) � incrementado em 1
			count[centI] += 1;
		}
        //La�o que itera sobre o n�mero de linhas
		for(int i = 0; i < k; i++){
            //Se count[i] diferente de 0
			if(count[i] != 0){
                //Itere sobre o n�mero de colunas
				for(int j = 0; j < dim; j++)
                    //Matriz centroides � preenchida pelos valores presentes em cada elemento
                    //da c�pia de centroides (centroides2) dividos por 1?
					centroides[i][j] = centroides2[i][j]/count[i];
			}
		}
	}

	private boolean classifica(){
		boolean convergencia = true;
        //La�o que itera sobre o n�mero de linhas
		for(int i = 0; i < nDados; i++){
            //Vetor dado recebe o vetor com as colunas de cada linha do conjunto de entrada
			double[] dado = dados[i];
            //distanceMin � maior double poss�vel em Java
			double distanceMin = Double.MAX_VALUE;
			int ind = 0;
            //La�o que itera sobre o n�mero de colunas
			for(int j = 0; j < k; j++){
                //Vetor centroide recebe o vetor com as colunas de cada linha do vetor centroides
				double[] centroide = centroides[j];
                //dist � a dist�ncia euclidiana entre o vetor dado (linha do conjunto de entrada)
                //e o vetor centroide (linha da matriz de centroides)
				double dist = distanceEuclidiana(dado, centroide);
                //Se a dist�ncia for menor que o maior double existente
				if(dist < distanceMin){
                    //A dist�ncia m�nima passa a ser a dist�ncia a cada itera��o
					distanceMin = dist;
                    //ind � o �ltimo �ndice da coluna, ou seja, 'k'
					ind = j;
				}
			}
            //Se o elemento do vetor classes for diferente de ind 
            //(na verdade sempre vai ser, j� que todo elemento de classes � igual a 0)
			if(classes[i] != ind){
                //Elemento de classes recebe ind
				classes[i] = ind;
				convergencia = false;
			}
		}
		return convergencia;
	}

	private double distanceEuclidiana(double[] dado, double[] centroide){
		double sum = 0;
        //La�o que itera sobre o n�mero de colunas
		for(int j = 0; j < dim; j++)
            //Somat�rio � o valor da subtra��o elevado ao quadrado
			sum += Math.pow(Math.abs(dado[j]-centroide[j]), 2);
        //Retorna a raiz quadrada do som�torio
		return Math.sqrt(sum);
	}
	
	public int[] sortCentroides() {
		if(centroides.length != k)
			throw new RuntimeException("Algor�tmo inconsistente !!!");
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
