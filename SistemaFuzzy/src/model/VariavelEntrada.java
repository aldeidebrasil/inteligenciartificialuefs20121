package model;

import java.util.ArrayList;

public class VariavelEntrada {

	private ArrayList<ConjuntoNebuloso> conjunto;
	private int qtdPontosTotal;
	
	public VariavelEntrada(int qtdDePontos){
		conjunto = new ArrayList<ConjuntoNebuloso>();
		qtdPontosTotal = qtdDePontos;
	}

	/**
	 * Calcular o grau de pertinencia de um elemento em todos os conjuntos nebulosos da variavel de entrada
	 * @param entrada
	 * @return
	 */
	public double[] calcularGrauPertinenciaNosConjuntos(double entrada){
		
		double[] grausDePertinencia = new double[conjunto.size()];
		
		for (int i = 0; i < grausDePertinencia.length; i++) {
			grausDePertinencia[i] = conjunto.get(i).calculaGrauPertinencia(entrada);
		}
		return grausDePertinencia;
	}
	
	/**
	 * Gerar os pontos que deve ser discretizados em cada variavel de entrada 
	 */
	public void gerarPontos() {
		
		
	}
	
	public ArrayList<ConjuntoNebuloso> getConjunto() {
		return conjunto;
	}

	public void setConjunto(ArrayList<ConjuntoNebuloso> conjunto) {
		this.conjunto = conjunto;
	}

	public int getQtdPontosTotal() {
		return qtdPontosTotal;
	}

	public void setQtdPontosTotal(int qtdPontosTotal) {
		this.qtdPontosTotal = qtdPontosTotal;
	}
	
	
	
}
