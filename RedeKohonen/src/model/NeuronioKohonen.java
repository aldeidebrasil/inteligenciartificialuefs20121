package model;

import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * @author André e Douglas
 *
 */
public class NeuronioKohonen {

	private ArrayList<Integer> vizinhos;
	private float[] pesos;
	private Random aleatorio;
	private double norma;
	
	public NeuronioKohonen(int posicao){
		norma = 0;
		aleatorio = new Random();
		pesos = new float[3];
		vizinhos = new ArrayList<Integer>();
		setVizinhos(posicao);
		
	}
	
	public void setPesoAleatorio(){
		for(int i = 0; i < pesos.length; i++){
			pesos[i] = aleatorio.nextFloat();			
		}
	}
	
	/**
	 * Calculo a distancia entre o neuronio e o padrao através das entradas e dos pesos
	 * @param entradas: vetor de entradas atual passado pela rede
	 */
	public void calculaNorma(float[] entradas){
		
		double normaTemp = 0;
		
		// Calculo das normas do vetor de entradas
		for(int i = 0; i < entradas.length; i++){
			normaTemp += Math.pow(entradas[i] - pesos[i], 2); // || v || = sqrt[somatorio(x - w)²]
		}
		
		// Tirar a raiz quadrada para finalizar o calculo
		norma = Math.sqrt(normaTemp);
	}
	
	/**
	 * 
	 * @return: Adiciona no ArrayList de vizinhos os indices dos neuronios que são vizinhos ao neuronio indicado pelo parametro posicao
	 * @param posicao: inteiro entre 1 e 16
	 */
	public void setVizinhos(int posicao) {

		//Verifica se o neuronio tem vizinho a sua direita
		if (posicao + 1 != 5 && posicao + 1 != 9 && posicao + 1 != 13
				&& posicao + 1 != 17) {
			vizinhos.add(posicao + 1);
		}
		//Verifica se o neuronio tem vizinho a sua esquerda
		if (posicao - 1 != 0 && posicao - 1 != 4 && posicao - 1 != 8
				&& posicao - 1 != 12) {
			vizinhos.add(posicao - 1);
		}
		//Verifica se o neuronio tem vizinho em baixo
		if (posicao + 4 <= 16) {
			vizinhos.add(posicao + 4);
		}
		//Verifica se o neuronio tem vizinho em cima
		if (posicao - 4 >= 1) {
			vizinhos.add(posicao - 4);
		}
	}
	
	/**
	 * Método executado para ajustar os pesos do neuronio vencedor e vizinhos
	 * @param taxaDeAprendizado: taxa passada pela rede
	 * @param entradas: vetor de entradas correspondete aos dados da amostra sendo analisada pela rede
	 */
	public void ajustaPesos(double taxaAprendizagem, float[] entradas){
		
		for(int i = 0; i < pesos.length; i++){
			pesos[i] += taxaAprendizagem*(entradas[i] - pesos[i]);
		}
	}
	
	public ArrayList<Integer> getVizinhos(){
		return vizinhos;
	}
	
	public void setNorma(double norma){
		this.norma = norma;
	}
	
	public double getNorma(){
		return norma;
	}
	
}
