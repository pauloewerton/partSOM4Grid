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
	private double[][] centroides; //Matriz com os centróides?
	private int[] classes; //Matriz com as classes?
	private int k; //Variável desconhecida
	private int nDados; //Número de linhas da tabela
	private int dim; //Número de colunas da tabela

	//Construtor recebe o conjunto de entrada e 'k' como argumentos
    public KMeans(double[][] dados, int k) throws KMeansException {
        //Se o conjunto de entrada não tiver elementos, ou se a quantidade de elementos for menor ou igual
        //a zero, ou 'k' for menor ou igual a zero, ou a quantidade de elementos for menor ou igual a 'k'
        //invalide o conjunto de entrada
		if(dados == null || dados.length <= 0 || k <= 0 || dados.length < k)
			throw new KMeansException("Dados de entrada ou numero de grupos invalido !!!");
        //Incialização incial
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
        //Centroides é matriz de dimensão formada por número 'k' de linhas e número 'dim'
        //(quantidade de atributos do conjunto de entrada, colunas) de colunas
		this.centroides = new double[k][dim];
        //Classes é vetor de tamanho igual a quantidade de dados do conjunto de entrada
		this.classes = new int[nDados];
        //Laço que itera sobre o número de linhas
		for(int i = 0; i < k; i++)
            //Laço que itera sobre o número de colunas
			for(int j = 0; j < dim; j++)
                //Centroides recebe exatemente os mesmos elementos do conjunto de entrada
				centroides[i][j] = dados[i][j];
        //Laço que itera sobre o número de linhas
		for(int i = 0; i < nDados; i++)
            //Vetor classes é totalmente preenchido com zero
			classes[i] = 0;
	}
		
	private void calculaCentroides(){
        //Count é vetor de tamanho igual a 'k' (acho que é o número de linhas do conjunto de entrada
        //ou seja k == nDados)
		int[] count = new int[k];
        //Cópia da matriz de centroides (mesmo tamanho)
		double[][] centroides2 = new double[k][dim];
        //Laço que itera sobre a quantidade de linhas
		for(int i = 0; i < nDados; i++){
            //centI recebe classes[i], ou seja, zero
			int centI = classes[i];
            //Laço que itera sobre quantidade de colunas
			for(int j = 0; j < dim; j++)
                //Matriz cópia de centroides preenchida somando-se o conteúdo de cada índice de
                //centroides2 com o conteúdo do conjunto de dados
                //Ou seja, linha '0' de centroides terá seus valores recalculados pelo somátorio
                //a cada iteração
				centroides2[centI][j] += dados[i][j];
            //count que é vetor igual a 'k', em seu índice '0' (centI) é incrementado em 1
			count[centI] += 1;
		}
        //Laço que itera sobre o número de linhas
		for(int i = 0; i < k; i++){
            //Se count[i] diferente de 0
			if(count[i] != 0){
                //Itere sobre o número de colunas
				for(int j = 0; j < dim; j++)
                    //Matriz centroides é preenchida pelos valores presentes em cada elemento
                    //da cópia de centroides (centroides2) dividos por 1?
					centroides[i][j] = centroides2[i][j]/count[i];
			}
		}
	}

	private boolean classifica(){
		boolean convergencia = true;
        //Laço que itera sobre o número de linhas
		for(int i = 0; i < nDados; i++){
            //Vetor dado recebe o vetor com as colunas de cada linha do conjunto de entrada
			double[] dado = dados[i];
            //distanceMin é maior double possível em Java
			double distanceMin = Double.MAX_VALUE;
			int ind = 0;
            //Laço que itera sobre o número de colunas
			for(int j = 0; j < k; j++){
                //Vetor centroide recebe o vetor com as colunas de cada linha do vetor centroides
				double[] centroide = centroides[j];
                //dist é a distância euclidiana entre o vetor dado (linha do conjunto de entrada)
                //e o vetor centroide (linha da matriz de centroides)
				double dist = distanceEuclidiana(dado, centroide);
                //Se a distância for menor que o maior double existente
				if(dist < distanceMin){
                    //A distância mínima passa a ser a distância a cada iteração
					distanceMin = dist;
                    //ind é o último índice da coluna, ou seja, 'k'
					ind = j;
				}
			}
            //Se o elemento do vetor classes for diferente de ind 
            //(na verdade sempre vai ser, já que todo elemento de classes é igual a 0)
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
        //Laço que itera sobre o número de colunas
		for(int j = 0; j < dim; j++)
            //Somatório é o valor da subtração elevado ao quadrado
			sum += Math.pow(Math.abs(dado[j]-centroide[j]), 2);
        //Retorna a raiz quadrada do somátorio
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
