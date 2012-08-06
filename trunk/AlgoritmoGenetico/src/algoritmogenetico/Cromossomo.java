package algoritmogenetico;

import java.util.ArrayList;
import java.util.Random;

public class Cromossomo {

	private ArrayList<Integer> genes;
	private double valorCromossomo;
	private double fitness;
	private int tamanho;
	private Random aleatorio;
	
	/**
	 * Construtor para inicializar os cromossos. O cromossomo terá uma representação em genes binarios do
	 * valor double do fitness
	 * @param tamanho
	 */
	public Cromossomo(int tamanho){
		this.tamanho = tamanho;
		genes = new ArrayList<Integer>();
		aleatorio = new Random();
	}
	
	/**
	 * Gerar o numero decimal com aproximação de 6 casas decimais e parte inteira variando de -1 a 2
	 * 
	 */
	public void converterFitnessBinarioParaDecimal(){
		
		int exp = 0;
		float soma = 0;
		
		//Convertendo de binario para a base 10
		for (int i = genes.size() - 1; i >= 0; i--) {
			soma += genes.get(i) * Math.pow(2, exp/*i - genes.size() - 1*/);
			exp++;
		}
		
		//Gerando o valor com seis casas decimais
		valorCromossomo = -1 + (soma * 3)/4194303;
		
	}
	
	/**
	 * Calculo do fitness com base do valor do cromossomo. Em suma resumi-se a uma função onde
	 * ValorCromossomo é X e o fitness é Y
	 */
	public void calcularFitness(){
		
		fitness = (valorCromossomo * Math.sin(10 * Math.PI * valorCromossomo)) + 1 ; //verificar se é +2 mesmo ou se deixa +1
		
	}
	
	/**
	 * Gerador de bits aleatorios
	 */
	public void setGenesAleatorios(){
		for (int i = 0; i < tamanho; i++) {
			genes.add(aleatorio.nextInt(2));
		}
	}

	public ArrayList<Integer> getGenes() {
		return genes;
	}

	public void setGenes(ArrayList<Integer> genes) {
		this.genes = genes;
	}

	public double getValorCromossomo() {
		return valorCromossomo;
	}

	public void setValorCromossomo(double fitness) {
		this.valorCromossomo = fitness;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public int getTamanho() {
		return tamanho;
	}

	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}

}
