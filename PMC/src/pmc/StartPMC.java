package pmc;

import java.io.*;
import model.Rede;

/**
 * Classe para inicialização da rede
 * @author Douglas e André
 *
 */
public class StartPMC {

	/**
	 * @param args
	 * @throws Exception 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		/* OBS: 
		 * 		Camada de entrada: 5 "saídas" -> 4 delas (X1, X2, X3 e X4) são dados do problema e a outra (X0) é o limiar do neuronio
		 *  	Camada intermediária (posição 0 do vetor camadas): 5 entradas,  5 saídas
		 *  	Camada de saída (posição 1 do vetor de camadas): 5 entradas, 3 saídas 
		 *  */
		
		// 5 é o número de neuronios da camada intermediária. Neste caso a segunda camda, pois a primeira, camada de entrada, e matriz de dados.
		int [] qtdNeuroniosPorCamada = {5,3}; 
		// 4 é número de entradas na camada intermediária e 5 é o número de entrdas da camada de saída
		int [] entradasPorCamadas = {5,5};
		
			
	Rede rede = new Rede(2, qtdNeuroniosPorCamada, entradasPorCamadas);
	
	rede.treinar();
	
	System.out.println(rede.getNumEpocas());
	
	}
	
	
	

}
