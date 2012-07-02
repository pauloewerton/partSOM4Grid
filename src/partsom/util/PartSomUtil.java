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

package partsom.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Utility class to support operations in the rest of the project classes.
 * @author Paulo Ewerton
 * @version 1.0
**/
public final class PartSomUtil {

    /**
     * Generates a string from an index vector.
     * @param indexVector A vector of integers, result of the local partSOM.
     * @return A string representing the index vector to be written on a file.
    **/
    public static String writeIndexVectorToString(int[] indexVector) {
    
        String indexVectorString = "";
        
        for (int i = 0; i < indexVector.length; i++){
        
            indexVectorString += indexVector[i] + "\n";
        }
        
        return indexVectorString;
    }
    
    /**
     * Writes a string (generated from running the previous method) on a plain
     * text file.
     * @param stringToWrite The string used to generate the text file.
     * @param fileName Name it whatever you want.
    **/
    public static File writeStringToFile(String stringToWrite, String fileName) {
    
        File file = new File(fileName);
        FileWriter writer = null;
        
        try{
            
            writer = new FileWriter(file);
            writer.write(stringToWrite);
            writer.close();
        
        } catch (IOException ex) {
        
            ex.printStackTrace();
        }
        
        return file;
    }
    
    /**
     * Generates an index vector from a text file.
     * @param file The file from which the data must be gotten.
     * @param numberOfLines Length of the file.
     * @return A vector of integers with the data from the file.
    **/
    public static int[] getIndexVectorFromFile(File file, int numberOfLines) {
    
        Scanner scan = null;
        int indexVector[] = new int[numberOfLines];
        
        try{
        
            scan = new Scanner(file);
            int i = 0;
            
            while (scan.hasNextLine()) {
            
                indexVector[i] = scan.nextInt();
                scan.nextLine();
                i++;
            }
            
            scan.close();
            
        } catch (Exception ex) {
            
            ex.printStackTrace();
        }
        
        return indexVector;
    }
    
    /**
     * Writes a result from running partSOM on a plain text file.
     * @param data The original database,
     * @param classes The most representative units of the database.
     * @param databaseName Name it whatever you want.
    **/
    public static void writeClusteringResultToFile(double[][] data, int[] classes, 
                                                   String databaseName) {
    
        File output = new File("ClusteringResult" + databaseName);
        
        try{
            
            FileWriter writer = new FileWriter(output);
            
            for (int i = 0; i < data.length; i++) {
            
                writer.write("Input number: " + (i + 1) + " ");
                
                for (int j = 0; j < data[0].length; j++) {
                
                    writer.write(data[i][j] + "\t");
                }
                
                writer.write(" Class " + (classes[i] + 1) + "\n");
            }
            
            writer.close();
            
        } catch (IOException ex) {
        
            ex.printStackTrace();
        }
    }
}