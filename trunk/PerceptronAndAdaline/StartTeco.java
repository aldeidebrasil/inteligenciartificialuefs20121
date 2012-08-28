import java.io.FileNotFoundException;
import perceptronsimples.NeuronioTeco;


public class StartTeco {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */

	public static void main(String[] args) throws FileNotFoundException {

		NeuronioTeco teco = new NeuronioTeco();

		for(int a=0; a<=4; a++){
			
			teco.DataRequest("dados.txt");		 // Executa a leitura do arquivo de dados
			teco.setaPesosAleatorios();					 // Executa a inicialização aleatória dos pesos
			
			System.out.println(" ********************  TESTE: " + a + " ***********************");
			System.out.println("Vetor de pesos incial:" + teco.pesos[0] + " " + teco.pesos[1] + " " + teco.pesos[2] + " " + teco.pesos[3]);
			
			teco.treinamento();					 // Executa o treinamento da rede
			
			System.out.println("Numero de epocas de treinamento: " + teco.numEpocas);
			System.out.println("Vetor de pesos final:" + teco.pesos[0] + " " + teco.pesos[1] + " " + teco.pesos[2] + " " + teco.pesos[3]); 
						
			teco.teste(); //
			System.out.println(" ****************************************************** \n");
		}

		/*float entradasTreinamento[][] = new float[30][5]; 	//0 -> X0;
															//1 -> X1;
															//2 -> X2;
															//3 -> X3;
															//4 -> saida desejada


		//Leitura do arquivo para inserção dos valores no vetor de entradas 'x'
		File f = new File("dados.txt");
		Scanner scan = new Scanner(f);

		int line = 0;
		while(scan.hasNextLine()){

			String linha = new String();
			linha = scan.nextLine();
			String[] vetx = linha.split(" ");           

			for(int j = 0; j < 4; j++){
				entradasTreinamento[line][j] = Float.parseFloat(vetx[j]);                    
			}
			entradasTreinamento[line][4] = Float.parseFloat(vetx[4]); //saida desejada é a posição 4 da matriz de cada amostra
			line++;
		}



		for(int a=0; a< entradasTreinamento.length; a++){
			// entradasTreinamento[i][j]; i significa o numero da amostra; j indica o indice da entrada
			System.out.print(entradasTreinamento[a][0] + "  "); //x0 
			System.out.print(entradasTreinamento[a][1] + "  "); //x1
			System.out.print(entradasTreinamento[a][2] + "  "); //x2
			System.out.print(entradasTreinamento[a][3] + "  "); //x3
			System.out.print(entradasTreinamento[a][4] + "  "); //saida desejada
			System.out.println("");

		}*/


	}

}
