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

		// 5 é o número de neuronios da camada intermediária. Neste caso a segunda camda, pois a primeira, camada de entrada, e matriz de dados.
		int [] qtdNeuroniosPorCamada = {5,3}; 
		// 4 é número de entradas na camada intermediária e 5 é o número de entrdas da camada de saída
		int [] entradasCamadas = {4,5};
		
			
	Rede rede = new Rede(2, qtdNeuroniosPorCamada, entradasCamadas);
	
	rede.treinar();
	
	System.out.println(rede.getNumEpocas());
	
	}
	
	
	

}
