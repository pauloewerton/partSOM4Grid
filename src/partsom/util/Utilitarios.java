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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public final class Utilitarios {

	public static double[][] getRandomVector(int nL, int nC, double min, double max){
		double[][] dados = new double[nL][nC];
		for(int i = 0; i < nL; i++)
			for(int j = 0; j < nC; j++)					
				dados[i][j] = (min+Math.round(Math.random()*(max-min)));
		return dados;
	}
	
	public static double[][] getDados(String dadosStr){
		StringTokenizer tokenizer = new StringTokenizer(dadosStr);
		int iniSubString = 0;
		int fimSubString = dadosStr.indexOf("\n");
		if(fimSubString < 0) 
			return null;
		String linha = dadosStr.substring(iniSubString, fimSubString);
		StringTokenizer tokenizerLinha = new StringTokenizer(linha);
		int nTokens = tokenizer.countTokens();
		int nColunas = tokenizerLinha.countTokens();
		int nDados = nTokens/nColunas;
		double[][] dados = new double[nDados][nColunas];
		try{
			int i = 0;
			while(tokenizer.hasMoreTokens()){
				for(int j = 0; j < nColunas; j++)
					dados[i][j] = Double.parseDouble(tokenizer.nextToken());									
				i++;
			}
			if(i != nDados)
				return null;
			return dados;
		}catch(NumberFormatException e){
			return null;
		}
	}

	public static String getDadosStr(double[][] dados, int nL, int nC){
		String dadosStr = "";
		for(int i = 0; i < nL; i++){
			for(int j = 0; j < nC; j++)
				dadosStr += dados[i][j]+(j == nC-1?"":"\t"); 
			dadosStr += "\n";
		}
		return dadosStr;
	}
			
	public static String getConteudoArquivo(File filetxt){
		String conteudo = null;
		try {
			InputStream in = new FileInputStream(filetxt);
			BufferedReader input = new BufferedReader(
					new InputStreamReader(in));			
			String linha = input.readLine();
			conteudo = "";
			while(linha != null){
				conteudo += linha+"\n";
				linha = input.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return conteudo;
	}

}
