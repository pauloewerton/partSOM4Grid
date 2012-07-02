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

package partsom;

import partsom.clustering.KMeans;

/**
 * This is the central version of the partSOM architecture, where codebooks and
 * index vectors are received (in a central processing unit) and the database is
 * remounted. A remounted database, equivalent to the original one, is generated
 * as result.
 * @author Paulo Ewerton
 * @version 1.0
**/ 

public class CentralPartSom {

    private int[] indexVector;
    private double[][] codebook;
    
    private KMeans kmeans;
    private double[][] remountedData;
    private int[] classes;
    private int numberOfClusters;
    
    /**
     * Constructor.
     * @param indexVector Index vector created by the local partSOM.
     * @param codebook Codebook created by the local partSOM.
    **/
    public CentralPartSom(int[] indexVector, double[][] codebook) {
    
        this.indexVector = indexVector;
        this.codebook = codebook;
        this.numberOfClusters = codebook.length;
    }
    
    /**
     * Remounts the database from codebooks and index vectors.
    **/
    public void remountData() {
    
        remountedData = new double[indexVector.length][codebook[0].length];
        
        for (int i = 0; i < indexVector.length; i++) { 
        
            for (int j = 0; j < codebook[0].length; j++) {
            
                remountedData[i][j] = codebook[indexVector[i]][j];
            }
        }
    }
    
    /**
     * Applies the prototype-based algorithm, this time to the remounted data.
     *
    **/
    public void executeClustering() {
    
        remountData();
    
        try{
        
            kmeans = new KMeans(remountedData, numberOfClusters);
            kmeans.make();
            classes = kmeans.getClasses();
        
        } catch (Exception ex){
        
            ex.printStackTrace();
        }
    }
    
    public double[][] getRemountedData() {
    
        return remountedData;
    }
    
    public int[] getClasses() {
    
        return classes;
    }
}