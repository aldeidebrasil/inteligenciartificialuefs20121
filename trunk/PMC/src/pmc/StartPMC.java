package pmc;

import java.io.*;
import model.*;

/**
 * Classe para inicialização da rede
 * @author André e Douglas
 *
 */

public class StartPMC {

	/**
	 * @param args
	 * @throws Exception 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws Exception {

		int qtdNeuroniosCamadaIntermediaria = 5;
		
		/* OBS: 
		 * 		Camada de entrada: 5 "saídas" -> 4 delas (X1, X2, X3 e X4) são dados do problema e a outra (X0) é o limiar do neuronio
		 *  	Camada intermediária (posição 0 do vetor camadas): 5 entradas,  5/10/15 saídas
		 *  	Camada de saída (posição 1 do vetor de camadas): 5/10/15 entradas, 3 saídas 
		*/
		
		// posição 0 é o número de neuronios da camada intermediária. Neste caso a segunda camada, pois a primeira, camada de entrada, e matriz de dados.
		int [] qtdNeuroniosPorCamada = {qtdNeuroniosCamadaIntermediaria,3}; 
		
		// posição 0 é número de entradas na camada intermediária e a posição 1 é o número de entradas da camada de saída
		int [] entradasPorCamadas = {5,qtdNeuroniosCamadaIntermediaria};
			
		Rede rede = new Rede(2, qtdNeuroniosPorCamada, entradasPorCamadas);
	
		rede.treinar();
	
		System.out.println(rede.getNumEpocas());
	}
}
