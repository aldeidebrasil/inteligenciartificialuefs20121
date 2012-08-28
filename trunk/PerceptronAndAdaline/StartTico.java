import java.io.FileNotFoundException;

import perceptronsimples.NeuronioTico;


public class StartTico {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws FileNotFoundException{
		
		NeuronioTico tico = new NeuronioTico();

		for(int a=0; a<=4; a++){
			
			tico.DataRequest("dadosAdaline.txt");		 // Executa a leitura do arquivo de dados
			tico.setaPesosAleatorios();			 // Executa a inicialização aleatória dos pesos
			
			System.out.println(" ********************  TESTE: " + a + " ***********************");
			System.out.println("Vetor de pesos incial:" + tico.pesos[0] + " " + tico.pesos[1] + " " + tico.pesos[2] + " " + tico.pesos[3] + " " + tico.pesos[4]);
			
			tico.treinamento();					 // Executa o treinamento da rede
			
			System.out.println("Numero de epocas de treinamento: " + tico.numEpocas);
			System.out.println("Vetor de pesos final:" + tico.pesos[0] + " " + tico.pesos[1] + " " + tico.pesos[2] + " " + tico.pesos[3] + " " + tico.pesos[4]); 
						
			tico.teste(); //
			System.out.println(" ****************************************************** \n");

			//imprimi grafico
			//if(a < 2)
				//tico.plotaGrafico();
			
		}

	}

}
