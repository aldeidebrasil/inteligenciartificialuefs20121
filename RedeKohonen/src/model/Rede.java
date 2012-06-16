package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;


public class Rede {

	private HashMap<Integer, NeuronioKohonen> neuronios;
	private int[] neuroniosVencedoresAtuais;
	private int[] neuroniosVencedoresEpocaAnterior;
	private double taxaAprendizagem;
	private float[][] entradas = new float[120][3];
	private int numEpocas;
	private boolean modificar;
	
	public Rede(){
		
		neuroniosVencedoresAtuais = new int[120];
		zerarVetor(neuroniosVencedoresAtuais);
		
		neuroniosVencedoresEpocaAnterior = new int[120];
		zerarVetor(neuroniosVencedoresEpocaAnterior);
		
		neuronios = new HashMap<Integer, NeuronioKohonen>();
	
		for(int i = 1; i <= 16; i++){
			neuronios.put(i, new NeuronioKohonen(i)); //Cada neuronio tem uma ID correspondente a sua posição no SOM(Self Organization Map).
		}											  //Essa ID varia entre 1 e 16, inclusive.
	}
	
	/**
	 * Realiza o treino de rede
	 * 
	 */
	public void treinar(){
		
		modificar = true;
		taxaAprendizagem = 0.001;
		numEpocas = 0;		
		Set<Integer> chaves = neuronios.keySet();
		for(int chave: chaves){
			neuronios.get(chave).setPesoAleatorio(); //Gerar pesos aleatorios nos neuronios
		}
		
		try{
			requisitarDados("treina.txt");
		} catch(IOException e){
			e.getMessage();
		}
		
		while(modificar){ //enquanto algum vetor de pesos for modificado
			
			for(int i = 0; i < entradas.length; i++){ //iterar pela quantidade de amostras
				
				Set<Integer> indices = neuronios.keySet();
				for(int chave: indices){
					neuronios.get(chave).calculaNorma(entradas[i]); //Calcula a norma para todos os neuronios
				}
				
				//retorna indice/chave do neuronio vencedor
				int neuronioVencedor = getNeuronioVencedor();
				//guarda o indice do neuronio vencedor no vetor
				neuroniosVencedoresAtuais[i] = neuronioVencedor;
				
				System.out.println("O neuronio vencedor da amostra "+ i +" é: "+ neuronioVencedor);
				
				//retorna a lista de indices de vizinhos do neuronio vencedor
				ArrayList<Integer> vizinhos = neuronios.get(neuronioVencedor).getVizinhos(); 
				
				//ajusta os pesos do neuronio vencedor
				neuronios.get(neuronioVencedor).ajustaPesos(taxaAprendizagem, entradas[i]);
				
				//ajusta os pesos dos neuronios vizinhos
				for(int a = 0; a < vizinhos.size(); a++){
					neuronios.get(vizinhos.get(a)).ajustaPesos(taxaAprendizagem, entradas[i]);
				}
				
			}
			numEpocas++;
			isModificou();
			neuroniosVencedoresEpocaAnterior = neuroniosVencedoresAtuais.clone();
			zerarVetor(neuroniosVencedoresAtuais);
		}
	}
	
	public void testar(){
		
		entradas = new float[12][3];
		
		try{
			requisitarDados("teste.txt");
		} catch(Exception e){
			e.getStackTrace();
		}
		
		for(int i = 0; i < entradas.length; i++){
			
			Set<Integer> indices = neuronios.keySet();
			for(int chave: indices){
				neuronios.get(chave).calculaNorma(entradas[i]); //Calcula a norma para todos os neuronios
			}
		
			//retorna indice do neuronio vencedor
			int neuronioVencedor = getNeuronioVencedor();
		
			System.out.println("A amostra "+ i + " é classificada pelo neuronio " + neuronioVencedor);
			
		}
	}
	
	/**
	 * Método de cálculo do neuronio vencedor. A menor norma indica o neuronio vencedor.
	 * @return
	 */
	public int getNeuronioVencedor(){
		
		double menorNorma = 100;
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
	public void requisitarDados(String arquivo) throws IOException{

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
	
	/**
	 * Método para verificar se o neuronio vencedor da época atual é igual ao neuronio vencedor da época anterior.
	 * Essa verificação é feita entre as épocas a fim de suspender o treinamento ou não.
	 */
	public void isModificou(){
		
		for(int i = 0; i < neuroniosVencedoresAtuais.length; i++){
			//Se algum neuronio vencedor for diferente, continua o algoritmo
			if(neuroniosVencedoresAtuais[i] != neuroniosVencedoresEpocaAnterior[i]){ 
				modificar = true;
				return;
			} 
		}
		
		modificar = false;
	}
	
	public void zerarVetor(int[] vetor){
		for(int i = 0; i < vetor.length; i++){
			vetor[i] = 0;
		}
	}

	public int getNumEpocas() {
		return numEpocas;
	}

	public void setNumEpocas(int numEpocas) {
		this.numEpocas = numEpocas;
	}
	

}
