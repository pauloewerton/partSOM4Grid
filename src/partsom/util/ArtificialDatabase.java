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

/**
 * Utility to generate artificial databases in form of bidimensional vectors
 * of random numbers.
 * @author Paulo Ewerton
 * @version 1.0
**/
public class ArtificialDatabase {

    /**
     * Generates database and writes it to a plain text file.
     * @param args Follows this order: number of lines (single instances of data),
     * number of columns (category of data), minimum value for a instance,
     * maximum value for a instance and finally the database name.
    **/
    public static void main (String args[]) {
    
        int numberOfLines = Integer.parseInt(args[0]);
        int numberOfColumns = Integer.parseInt(args[1]);
        double minimumValue = Double.parseDouble(args[2]);
        double maximumValue = Double.parseDouble(args[3]);
        String databaseName = args[4];
        
        double[][] data = Utilitarios.getRandomVector(numberOfLines, numberOfColumns, minimumValue, maximumValue);
        
        String stringData = Utilitarios.getDadosStr(data, numberOfLines, numberOfColumns);
        PartSomUtil.writeStringToFile(stringData, databaseName);
    }
}