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
	
	public float [][] getPesos(){
		return pesos;
	}
	
	/**
	 * Este método atualiza a matriz de pesos da camada.
	 * Com esse método o ajuste dos pesos passa a ser responsabilidade da camada e não mais do neuronio. (Já que a matriz de pesos está na camada)
	 * Os neuronios apenas usarão os valores da matriz de pesos nos cálculos que precisarem.
	 * 
	 * Explicação:
	 * Podemos imaginar a matriz de pesos como vários vetores um sobre o outro, cada vez que este método é chamado ele atualiza
	 * uma linha da matriz (um do vetores empilhados). Isso é equivalente a atualizar um vetor de pesos em cada neuronio, contudo
	 * mais simples pois requer menor passagem de parametros.  
	 * 
	 * @param posicaoNeuronio
	 * @param taxaAprendizado
	 * @param saidasNeuroniosCamadaAnterior
	 */
	public void ajustaPesosDaCamada(int posicaoNeuronio, double taxaAprendizado, double [] saidasNeuroniosCamadaAnterior){
		
		/*
		 * gradienteNeuronio Gradiente local do neuronio para o qual está atualizando a "sua porção" da matriz de pesos.
		 */
		double gradienteNeuronio = neuronios[posicaoNeuronio].getGradienteLocal();
		
		for(int a=0; a<pesos[0].length; a++){
			
			pesos[posicaoNeuronio][a]+= taxaAprendizado*gradienteNeuronio*saidasNeuroniosCamadaAnterior[a];
		}
		
	}

}
