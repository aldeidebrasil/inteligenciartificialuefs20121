/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package perceptronsimples;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author andre
 */
public class NeuronioTeco { //Perceptron

	private float[][] entradas;
	public float[] pesos;
	private float taxaDeAprendizado;
	private boolean erroExiste;
	private Random aleatorio;
	private int numeroDeAcertos;
	public int numEpocas = 0;

	public NeuronioTeco() {
		aleatorio = new Random();
		numeroDeAcertos =0;
		pesos = new float[4];

		for(int i =0; i <=3; i++){
			pesos[i]=(float) 0.0;

		}

	}

	public void setEntradas(float[][] entradasTreino) {

		this.entradas = entradasTreino;

	}

	public void setEntradasTeste(float[][] entradasTeste) {
		this.entradas = entradasTeste;
	}

	/**
	 * Atribui valores aleat�rios ao vetor de pesos da rede.
	 * A aleatoriedade entre 0 e 1 � garantida com o uso do java.util.Random 
	 * 
	 */
	public void setaPesosAleatorios() {
		this.pesos[0] = (float) aleatorio.nextFloat();
		this.pesos[1] = (float) aleatorio.nextFloat();
		this.pesos[2] = (float) aleatorio.nextFloat();
		this.pesos[3] = (float) aleatorio.nextFloat();

	}

	public void setTaxaDeAprendizado(float taxa) {
		this.taxaDeAprendizado = taxa;
	}

	/**
	 * Realiza a soma ponderada das entradas do neuronio. Tem como entrada o 
	 * numero da amostra atual e devolve o valor a ser enviado para a fun��o de ativa��o. 
	 * 
	 * @param i : numero da amostra atual
	 * @return somatorio : somatorio ponderado das entradas do neuronio
	 */
	public float Somatorio(int i) {

		return  entradas[i][0] * pesos[0] + // x0*w0
				entradas[i][1] * pesos[1] + // x1*w1
				entradas[i][2] * pesos[2] + // x2*w2
				entradas[i][3] * pesos[3];  // x3*w3
	}

	/**
	 * Rertona a sa�da do neuronio ap�s a an�lise do valor ponderado. 
	 * 
	 * @param somaPonderada : soma ponderada dos valores de entrada
	 * @return resposta : Resposta do neuronio
	 */
	public int funcaoDeAtivacaoBipolar(float somaPonderada) {
		int resposta;

		if (somaPonderada > 0) {
			resposta = 1;
		} else {
			resposta = -1;
		}

		return resposta;
	}

	/**
	 * Ajusta o valor do vetor de peso para a amostra atual, se o resultado encontrado for diferente da resposta desejada.
	 * 
	 * @param saidaDaFuncao : saida/resposta da fun��o bipoloar
	 * @param amostra : numero da amostra que est� sendo testada (amostra atual) 
	 */
	public void ajustaPesos(float saidaDaFuncao, int amostra) {

		if (saidaDaFuncao != entradas[amostra][4]) {

			// w          w-1             n               d(k): saida esperada             y: saida          x(k): vetor de entrada   
			pesos[0] = pesos[0] + taxaDeAprendizado * (entradas[amostra][4] - saidaDaFuncao) * entradas[amostra][0];
			pesos[1] = pesos[1] + taxaDeAprendizado * (entradas[amostra][4] - saidaDaFuncao) * entradas[amostra][1];
			pesos[2] = pesos[2] + taxaDeAprendizado * (entradas[amostra][4] - saidaDaFuncao) * entradas[amostra][2];
			pesos[3] = pesos[3] + taxaDeAprendizado * (entradas[amostra][4] - saidaDaFuncao) * entradas[amostra][3];

			erroExiste=true;

		}
	}

	/**
	 * Realiza o treinamento da rede. Realiza o somatorio ponderado de uma amostra 
	 * e passa esse valor para a fun��o de ativa��o, caso este valor seja diferente
	 * da resposta desejada o vetor de pesos � ajustado.
	 */
	public void treinamento() {


		//setPesos(); // inicializa vetor de pesos com valores aleatorios
		taxaDeAprendizado = (float) 0.01; // inicializa taxa de aprendizado


		int epocaSemErros=0;
		numEpocas=0;


		while (epocaSemErros < 2) {

			erroExiste = false;

			//System.out.println("EPOCA: " + numEpocas);	

			for (int i = 0; i < entradas.length; i++) {

				// Soma Ponderada das Entradas
				float somatorio = Somatorio(i);
				//System.out.println("somatorio = " + somatorio);

				// Resultado da soma ponderada passando pela fun��o de ativa��o
				int saidaFuncaoSinal = funcaoDeAtivacaoBipolar(somatorio);
				//System.out.println("saida funcao = " + saidaFuncaoSinal + "  |  saida desejada= " + entradas[i][4]);

				// Ajuste dos valores do vetor de pesos "w"
				ajustaPesos(saidaFuncaoSinal, i);
			}

			numEpocas++;

			if(!erroExiste)
				epocaSemErros++;
			else
				epocaSemErros=0;


		}


	}

	/**
	 * Realiza o teste da rede.
	 * 
	 * @throws FileNotFoundException
	 */
	public void teste() throws FileNotFoundException {


		for(int i=0; i <entradas.length; i++){
			for(int j=0; j<entradas[0].length; j++ )
				entradas[i][j]=0;
		}

		DataRequest("testePerceptron.txt");

		System.out.print("Saida da rede: ");
		for (int i = 0; i < 9; i++) { // quantidade de amostras de teste

			// Soma Ponderada das Entradas
			float somatorio = Somatorio(i);
			//System.out.println("somatorio = " + somatorio);

			// Resultado da soma ponderada passando pela fun��o de ativa��o
			int saidaFuncaoSinal = funcaoDeAtivacaoBipolar(somatorio);
			System.out.print(saidaFuncaoSinal);


		}
		System.out.println("");

	}

	//System.out.println("Acertos: " + (numeroDeAcertos*100)/9 + "%");



	/**
	 * Faz a leitura do arquivo de dados no formato ".txt" e transporta dos dados para uma matriz de floats.
	 * Deve ser passado como parametro a nome do arquivo de texto.
	 * 
	 * @param arquivo
	 * @throws FileNotFoundException
	 */
	public void DataRequest(String arquivo) throws FileNotFoundException{

		entradas = new float[30][5]; 	//0 -> X0;
										//1 -> X1;
										//2 -> X2;
										//3 -> X3;
										//4 -> saida desejada

		//Leitura do arquivo para inser��o dos valores no vetor de entradas 'x'
		File f = new File(arquivo);
		Scanner scan = new Scanner(f);

		int line = 0;
		while(scan.hasNextLine()){

			String linha = new String();
			linha = scan.nextLine();
			String[] vetx = linha.split(" ");           

			for(int j = 0; j < 4; j++){
				entradas[line][j] = Float.parseFloat(vetx[j]);                    
			}
			entradas[line][4] = Float.parseFloat(vetx[4]); //saida desejada � a posi��o 4 da matriz de cada amostra
			line++;
		}


	}

}
