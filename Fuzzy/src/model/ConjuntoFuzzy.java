package model;

/**
 * Essa classe mapeia uma conjunto Fuzzy
 * 
 * @author andre
 *
 */
public class ConjuntoFuzzy {
	
	private double min, max;
	private String valorLinguistico; 
	
	/**
	 * 
	 * @param min inicio do internavalo não nulo
	 * @param max termino do intervalo não nulo
	 */
	public ConjuntoFuzzy(double min, double max, String nome){
		
		this.min = min;
		this.max = max;
		this.valorLinguistico = nome;
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
		
		if(valor <= min || valor >= max)
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
	 * @param m inicio da base superior
	 * @param n termino da base superior
	 */
	public double calculaPertinencia(double valor, int m, int n){
		
		double pertinencia;
		
		if(valor<= min || valor >= max)
			pertinencia = 0;
		else{
			if(valor < m)
				pertinencia = (valor - min)/(m - min);
			else if(valor >= m && valor <= n)
				pertinencia = 1;
			else
				pertinencia = (max - valor)/(max - n);
		}
			
		return pertinencia;
	}

}
