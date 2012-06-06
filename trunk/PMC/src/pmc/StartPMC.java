package pmc;

import java.io.*;
import model.Rede;

/**
 * Classe para inicializa��o da rede
 * @author Douglas e Andr�
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

		// 5 � o n�mero de neuronios da camada intermedi�ria. Neste caso a segunda camda, pois a primeira, camada de entrada, e matriz de dados.
		int [] qtdNeuroniosPorCamada = {5,3}; 
		// 4 � n�mero de entradas na camada intermedi�ria e 5 � o n�mero de entrdas da camada de sa�da
		int [] entradasCamadas = {4,5};
		
			
	Rede rede = new Rede(2, qtdNeuroniosPorCamada, entradasCamadas);
	
	rede.treinar();
	
	System.out.println(rede.getNumEpocas());
	
	}
	
	
	

}
