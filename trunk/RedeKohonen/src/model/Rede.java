package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;


public class Rede {

	
	private HashMap<Integer, NeuronioKohonen> neuronios;
	private double taxaAprendizagem;
	private float[][] entradas = new float[120][3];
	private int numEpocas;
	
	public Rede(){
		
		neuronios = new HashMap<Integer, NeuronioKohonen>();
	
		for(int i = 1; i <= 16; i++){
			neuronios.put(i, new NeuronioKohonen(i)); //Cada neuronio tem uma ID correspondente a sua posição no SOM(Self Organization Map)
		}											  //Essa ID varia entre 1 e 16
		
		taxaAprendizagem = 0.001;
		numEpocas = 0;
	}
	
	/**
	 * Realiza o treino de rede
	 */
	public void treinar(){
		
		boolean modificou = true;
		
		while(modificou){ //enquanto algum vetor de pesos for modificado
			
			for(int i = 0; i < entradas.length; i++){ //iterar pela quantidade de amostras
				
				Set<Integer> chaves = neuronios.keySet();
				for(int chave: chaves){
					neuronios.get(chave).calculaNorma(entradas[i]); //Calcula a norma para todos os neuronios
				}
				
				//retorna indice do neuronio vencedor
				int neuronioVencedor = getNeuronioMenorNorma();
				//retorna a lista de indices de vizinhos do neuronio vencedor
				ArrayList<Integer> vizinhos = neuronios.get(neuronioVencedor).getVizinhos(); 
				
				//ajusta os pesos do neuronio vencedor
				neuronios.get(neuronioVencedor).ajustaPesos(taxaAprendizagem, entradas[i]);
				
				//ajusta os pesos dos neuronios vizinhos
				for(int chave: vizinhos){
					neuronios.get(vizinhos.get(chave)).ajustaPesos(taxaAprendizagem, entradas[i]);
				}
				
			}
		}
	}
	
	public int getNeuronioMenorNorma(){
		
		double menorNorma = 10000;
		int neuronioMenorNorma = -1;
		
		Set<Integer> chaves = neuronios.keySet();
		for(int chave: chaves){
			if(neuronios.get(chave).getNorma() < menorNorma){
				 neuronioMenorNorma = chave;
				 menorNorma = neuronios.get(chave).getNorma();
			}
		}
		
		return neuronioMenorNorma;
	}
	/**
	 * Faz a leitura do arquivo de dados no formato ".txt" e transporta dos dados para uma matriz de floats.
	 * Deve ser passado como parametro a nome do arquivo de texto.
	 * 
	 * @param arquivo
	 * @throws IOException 
	 */
	public void DataRequest(String arquivo) throws IOException{

		//entradas = new float[][];     //0 -> X0;
		//1 -> X1;
		//2 -> X2;
		//3 -> X3;
		//4 -> X4;
		//5 -> Y1;
		//6 -> Y2;
		//7 -> Y3;

		//Leitura do arquivo para inserção dos valores no vetor de entradas 'x'
		File f = new File(arquivo);
		Scanner scan = new Scanner(f);

		int line = 0;
		while(scan.hasNextLine()){

			String linha = new String();
			linha = scan.nextLine();
			String[] vetx = linha.split(" ");           

			// Os dados de cada amostra são transferidos para a matriz de entradas
			for(int j = 0; j < vetx.length; j++){
				entradas[line][j] = Float.parseFloat(vetx[j]);
			}
			line++;
		}
	}
}
