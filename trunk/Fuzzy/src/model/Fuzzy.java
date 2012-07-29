package model;

import java.util.ArrayList;

/**
 * 
 * 
 * @author andre
 *
 */
public class Fuzzy {


	private ArrayList<ConjuntoFuzzy> temperatura;
	private ArrayList<ConjuntoFuzzy> volume;
	private ArrayList<ConjuntoFuzzy> pressao;


	public Fuzzy(){

		temperatura = new ArrayList<ConjuntoFuzzy>();
		volume = new ArrayList<ConjuntoFuzzy>();
		pressao = new ArrayList<ConjuntoFuzzy>();

	}

	/**
	 * Adiciona um conjunto fuzzy a um array de conjuntos fuzzy específico. 
	 * 
	 * @param seleciona seleciona qual array vai receber o novo conjunto
	 * @param conjunto novo conjunto fuzzy
	 */
	public void addConjuntoFuzzy(int seleciona, ConjuntoFuzzy conjunto){

		if(seleciona == 1)
			temperatura.add(conjunto);
		else if(seleciona == 2)
			volume.add(conjunto);
		else if(seleciona == 3)
			pressao.add(conjunto);

	}

	

	/**
	 * Este metodo realiza a fuzzificação dos dados de entrada. 
	 *  
	 * @param inicio primeiro ponto da fuzzificação
	 * @param fim ultimo ponto da fuzzificação 
	 * @param passo iteração do laço de fuzzificação
	 * @param dados vetor de dados de entrada
	 * @param cj conjunto fuzzy ao qual os dados se referem (Temperatura, Volume ou Pressao)
	 * @param metodo indica se a função de pertinencia será triangular (0) OU trapezoidal (1)
	 */
	public void fuzzifica(double inicio, double fim, double passo, double [] dados, ArrayList<ConjuntoFuzzy> cj, int metodo){

		int k =0;
		while(k < cj.size()){
			for(double i=inicio; i < fim; i = passo){

			}
		}
	}

	public void desfuzzificacao(){

	}

}
