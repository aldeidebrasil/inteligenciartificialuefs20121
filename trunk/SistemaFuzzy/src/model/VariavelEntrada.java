package model;

import java.util.ArrayList;

public class VariavelEntrada {

	private ArrayList<ConjuntoNebuloso> conjuntosNebulosos;
	private int qtdPontosTotal;
	private double intervalo;
	
	public VariavelEntrada(int qtdDePontos, double intervalo){
		conjuntosNebulosos = new ArrayList<ConjuntoNebuloso>();
		qtdPontosTotal = qtdDePontos;
		this.intervalo = intervalo;
	}

	/**
	 * Calcular o grau de pertinencia de um elemento em todos os conjuntos nebulosos da variavel de entrada
	 * @param entrada
	 * @return
	 */
	public double[] calcularGrauPertinenciaNosConjuntos(double entrada){
		
		double[] grausDePertinencia = new double[conjuntosNebulosos.size()];
		
		for (int i = 0; i < grausDePertinencia.length; i++) {
			grausDePertinencia[i] = conjuntosNebulosos.get(i).calculaGrauPertinencia(entrada);
		}
		return grausDePertinencia;
	}
	
	/**
	 * Gerar os pontos que devem ser discretizados nos conjuntos nebulosos
	 * @param intervalo
	 */
	public void discretizarPontos() {
		
		for (int i = 0; i < conjuntosNebulosos.size(); i++) {
			conjuntosNebulosos.get(i).discretizarPontos(intervalo);
		}
	}
	
	public ArrayList<ConjuntoNebuloso> getConjunto() {
		return conjuntosNebulosos;
	}

	public void setConjunto(ArrayList<ConjuntoNebuloso> conjunto) {
		this.conjuntosNebulosos = conjunto;
	}

	public int getQtdPontosTotal() {
		return qtdPontosTotal;
	}

	public void setQtdPontosTotal(int qtdPontosTotal) {
		this.qtdPontosTotal = qtdPontosTotal;
	}
	
	
	
}
