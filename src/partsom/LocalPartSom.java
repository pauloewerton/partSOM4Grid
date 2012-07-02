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
 
package partsom;

import partsom.util.Utilitarios;
import partsom.clustering.KMeans;

/**
 * This is the local version of the partSOM architecture, where the prototype-
 * based algorithm (in this case K-Means) is applied to the databases. Index 
 * vectors and codebooks are generated as result.
 * @author Paulo Ewerton
 * @version 1.0
**/

public class LocalPartSom {

    private KMeans kmeans;
    private double[][] data;
    private int numberOfClusters;
    
    private double[][] codebook;
    private int[] indexVector;
    private int[] classes;

    /**
     * Constructor.
     * @param data The database represented by a matrix (multimensional vector)
     * of numbers.
     * @param numberOfClusters Used by the K-Means algorithm to group similar
     * data together.
    **/
    public LocalPartSom(double[][] data, int numberOfClusters) {
        this.data = data;
        this.numberOfClusters = numberOfClusters;
    }
    
    /**
     * Applies the prototype-based algorithm and get codebook and the most
     * representative classes from the database.
    **/
    public void executeClustering() {
        try{
            kmeans = new KMeans(data, numberOfClusters);
            kmeans.make();
            codebook = kmeans.getCentroides();
            classes = kmeans.getClasses();
            calcIndexVector();
        
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * Generates the index vector based upon a similarity measure, this case
     * the Euclidian Distance (also used by the K-Means algorithm to cluster 
     * similar data).
    **/
    private void calcIndexVector() {
		
        indexVector = new int[data.length];
		
        for (int i = 0; i < data.length; i++) {
			double[] dataLines = data[i];
			double minDistance = Double.MAX_VALUE;
			int index = 0;
			
            for (int j = 0; j < numberOfClusters; j++) {
                double[] codebookLines = codebook[j];
				double distance = calcEuclidianDistance(dataLines, codebookLines);
				
                if (distance < minDistance) {
					
                    minDistance = distance;
					index = j;
				}
			}
            indexVector[i] = index;
		}
	}
    
    /**
     * Simple implementation of the Euclidian Distance algorithm.
     * @param vectorLines1 First array of data to be compared to the second one.
     * @param vectorLines2 Second array of data.
     * @return A double with the Euclidian Distance calculated.
    **/
    private double calcEuclidianDistance(double[] vectorLines1, double[] vectorLines2) {
        double sum = 0;
		
        for (int j = 0; j < data[0].length; j++) {
            sum += Math.pow(Math.abs(vectorLines1[j] - vectorLines2[j]), 2);

        }            		
        return Math.sqrt(sum);
	}
    
    public int[] getIndexVector(){
    
        return indexVector;
    }
    
    public double[][] getCodebook(){
    
        return codebook;
    }
    
    public int[] getClasses(){
    
        return classes;
    }
    
    public double[][] getData(){
    
        return data;
    }
}