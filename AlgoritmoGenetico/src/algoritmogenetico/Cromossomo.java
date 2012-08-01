package algoritmogenetico;

import java.util.ArrayList;
import java.util.Random;

public class Cromossomo {

	private ArrayList<Integer> genes;
	private double fitness;
	private int tamanho;
	private Random r;
	
	public Cromossomo(int tamanho){
		genes = new ArrayList<Integer>();
		r = new Random();
	}
	
	public float calculaFitness(){
		float fit;
		
		fit = 0; //transformar a codificacao de binario para um valor entre -1 e 2
		
		return fit;
	}
	
	public void setGenesAleatorios(){
		for (int i = 0; i < genes.size(); i++) {
			genes.add(r.nextInt(2));
		}
	}

	public ArrayList<Integer> getGenes() {
		return genes;
	}

	public void setGenes(ArrayList<Integer> genes) {
		this.genes = genes;
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
