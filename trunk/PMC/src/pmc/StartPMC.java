package pmc;

import java.io.*;
import model.*;

/**
 * Classe para inicializa��o da rede
 * @author Andr� e Douglas
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
		 * 		Camada de entrada: 5 "sa�das" -> 4 delas (X1, X2, X3 e X4) s�o dados do problema e a outra (X0) � o limiar do neuronio
		 *  	Camada intermedi�ria (posi��o 0 do vetor camadas): 5 entradas,  5/10/15 sa�das
		 *  	Camada de sa�da (posi��o 1 do vetor de camadas): 5/10/15 entradas, 3 sa�das 
		*/
		
		// posi��o 0 � o n�mero de neuronios da camada intermedi�ria. Neste caso a segunda camada, pois a primeira, camada de entrada, e matriz de dados.
		int [] qtdNeuroniosPorCamada = {qtdNeuroniosCamadaIntermediaria,3}; 
		
		// posi��o 0 � n�mero de entradas na camada intermedi�ria e a posi��o 1 � o n�mero de entradas da camada de sa�da
		int [] entradasPorCamadas = {5,qtdNeuroniosCamadaIntermediaria};
			
		Rede rede = new Rede(2, qtdNeuroniosPorCamada, entradasPorCamadas);
	
		rede.treinar();
	
		System.out.println(rede.getNumEpocas());
	}
}
