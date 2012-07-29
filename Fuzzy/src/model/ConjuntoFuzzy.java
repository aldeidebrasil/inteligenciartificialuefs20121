package model;

import javax.swing.JOptionPane;

/**
 * Essa classe mapeia uma conjunto Fuzzy
 * 
 * @author andre
 *
 */
public class ConjuntoFuzzy {

	private final int triangular = 1;
	private final int trapezoidal = 2;
	private double min, max;
	private String valorLinguistico; 
	private int fp; // tipo da função de pertinencia
	private double baseMenor;
	private double baseMaior;
	private double [] dados;
	private double inicioIntervalo;
	private double finalIntervalo;


	/**
	 * Construtor usado para um conjunto nebuloso que usa função de pertinencia triangular
	 * 
	 * @param min inicio do internavalo não nulo
	 * @param max termino do intervalo não nulo
	 * @param nome nome do conjunto nebuloso
	 * @param inicioIntervalo inicio do universo de discruso
	 * @param finalInteralo final do intervalo de discurso
	 * 
	 */
	public ConjuntoFuzzy(double min, double max, String nome, double inicioIntervalo, double finalInteralo){

		this.min = min;
		this.max = max;
		this.valorLinguistico = nome;
		this.fp = triangular;
		this.inicioIntervalo = inicioIntervalo;
		this.finalIntervalo = finalInteralo;

		
	}
	
	/**
	 * Construtor usado para um conjunto nebuloso que usa função de pertinencia triangular
	 * 
	 * @param min inicio do intervalo não nulo
	 * @param max termino do intervalo não nulo
	 * @param med1 inicio da base superior
	 * @param med2 final da base superior
	 * @param nome nome do conjunto nebuloso
	 * @param inicioIntervalo inicio do universo de discruso
	 * @param finalInteralo final do intervalo de discurso
	 * 
	 */
	public ConjuntoFuzzy(double min, double max, double med1, double med2 ,String nome, double inicioIntervalo, double finalInteralo){

		this.min = min;
		this.max = max;
		this.baseMenor = med1;
		this.baseMaior = med2;
		this.valorLinguistico = nome;
		this.fp = trapezoidal;
		this.inicioIntervalo = inicioIntervalo;
		this.finalIntervalo = finalInteralo;

		
	}
	
	


	/**
	 * Calcula o grau de pertinencia de um valor de entrada para um conjunto nebuloso triangular
	 * 
	 * @param valor dado de entrada
	 * @return 
	 */
	public double calculaPertinencia(double valor){

		double pertinencia = 0; 
		int med = (int) (min+max)/2;

		if(valor < min || valor > max)
			pertinencia = 0;
		else{
			if(valor <= med)
				pertinencia = (valor - min)/(med - min);
			else
				pertinencia = (max - valor)/(max - med);
		}

		return pertinencia;

	}

	/**
	 * Calcula o grau de pertinencia de um valor de entrada para um conjunto nebuloso trapezoidal
	 * 
	 * @param valor dado de entrada
	 * @param baseMenor inicio da base superior
	 * @param baseMaior termino da base superior
	 */
	public double calculaPertinencia(double valor, double baseMenor, double baseMaior){
		
		double pertinencia=0;


			if(valor >= min & valor < baseMenor){
				pertinencia = (valor - min)/(baseMenor - min);
			}
			if(valor >= baseMenor & valor <= baseMaior){
				pertinencia = 1;
			}
			if(valor > baseMaior & valor <= max){
				pertinencia = (max - valor)/(max - baseMaior);
			}
		

		return pertinencia;
	}

	public int getPertinencia(){
		return fp;


	}

	public double getMin() {
		return min;
	}


	public void setMin(double min) {
		this.min = min;
	}


	public double getMax() {
		return max;
	}


	public void setMax(double max) {
		this.max = max;
	}


	public String getValorLinguistico() {
		return valorLinguistico;
	}


	public void setValorLinguistico(String valorLinguistico) {
		this.valorLinguistico = valorLinguistico;
	}


	public double getBaseMenor() {
		return baseMenor;
	}


	public void setBaseMenor(int baseMenor) {
		this.baseMenor = baseMenor;
	}


	public double getBaseMaior() {
		return baseMaior;
	}


	public void setBaseMaior(int baseMaior) {
		this.baseMaior = baseMaior;
	}


	public double getInicioIntervalo() {
		return inicioIntervalo;
	}


	public void setInicioIntervalo(double inicioIntervalo) {
		this.inicioIntervalo = inicioIntervalo;
	}


	public double getFinalIntervalo() {
		return finalIntervalo;
	}


	public void setFinalIntervalo(double finalIntervalo) {
		this.finalIntervalo = finalIntervalo;
	}


}
