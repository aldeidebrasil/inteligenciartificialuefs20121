package model;

import java.util.Random;

public class Camada {
	
	private int numeroDeEntradas;
	private int numeroDeNeuronios;
	private float[][] pesos;
	private Neuronio neuronios[];
	private Random aleatorio;
	private float[][] pesosIniciais;
	
	
	public Camada(int entradas, int numNeuronios){
		
		numeroDeEntradas = entradas;
		numeroDeNeuronios = numNeuronios;
		this.neuronios = new Neuronio[numeroDeNeuronios];
		
		for(int i = 0; i < numeroDeNeuronios; i++){
			this.neuronios[i] = new Neuronio();
		}
		
		this.pesos = new float[numeroDeNeuronios][numeroDeEntradas];
					
	}
	
	/**
	 * Coloca pesos aleatorios no vetor de pesos e guarda uma copia 
	 * do vetor de pesos inicial.
	 * 
	 */
	public void PesosAleatorios(){
		this.aleatorio = new Random();
		for(int a=0; a<numeroDeNeuronios; a++){
			for (int b=0; b<numeroDeEntradas; b++)
				pesos[a][b] = aleatorio.nextFloat();
		}
		
		pesosIniciais = pesos;
	}

	public float[] getPesosCorrespondentes(int numPosicao, int numNeuronios){
		float[] pesosCorrespondentes = new float[numNeuronios];
		
		for(int i = 0; i < numNeuronios; i++){
			pesosCorrespondentes[i] = neuronios[i].getPesos()[numPosicao];
		}
		
		return pesosCorrespondentes;
	}
	public Neuronio[] getNeuronios() {
		return neuronios;
	}

	public void setNeuronios(Neuronio[] neuronios) {
		this.neuronios = neuronios;
	}

	public int getNumeroDeNeuronios() {
		return numeroDeNeuronios;
	}

	public void setNumeroDeNeuronios(int numeroDeNeuronios) {
		this.numeroDeNeuronios = numeroDeNeuronios;
	}

}
