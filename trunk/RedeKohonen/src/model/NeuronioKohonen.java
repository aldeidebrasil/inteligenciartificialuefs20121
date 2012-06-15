package model;

import java.util.ArrayList;
import java.util.Random;

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
		for(int i = 0; i < 4; i++){
			pesos[i] = aleatorio.nextFloat();			
		}
	}
	
	public void calculaNorma(float[] entradas){
		
		double normaEntrada = 0, normaPesos = 0;
		
		//Revisar para ter certeza da formula de calculo de norma
		for(int i = 0; i < entradas.length; i++){
			normaEntrada += Math.pow(entradas[i] - pesos[i], 2); // || v || = somatorio(x - w)�
		}
		
		
		for(int i = 0; i < pesos.length; i++){
			normaPesos += Math.pow(entradas[i] - pesos[i], 2); // || v || = somatorio(x - w)�
		}
	
		norma = normaEntrada - normaPesos;
	}
	
	/**
	 * 
	 * @return: Adiciona no ArrayList de vizinhos os indices dos neuronios que s�o vizinhos ao indicado pelo parametro posicao
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
	 * M�todo executado para ajustar os pesos do neuronio vencedor e vizinhos
	 * @param taxaDeAprendizado: taxa passada pela rede
	 * @param entradas: vetor de entradas correspondete aos dados da amostra sendo analisada pela rede
	 */
	public void ajustaPesos(double taxaAprendizagem, float[] entradas){
		
		for(int i = 0; i < pesos.length; i++){
			pesos[i] += taxaAprendizagem*entradas[i]*pesos[i];
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
