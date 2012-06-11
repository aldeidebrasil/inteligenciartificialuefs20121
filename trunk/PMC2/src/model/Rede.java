package model;

import java.io.*;
import java.util.*;

/**
 * 
 * @author Douglas e André
 *
 */

public class Rede {

	private int numeroDeCamadas;
	private Camada[] camadas;
	private double taxaAprendizagem;
	private double erro;
	private double eqm_atual;
	private double eqm_anterior;
	private float[][] entradas = new float[130][8];
	private float[][] historicoDeSaidas; // armazena as saidas de cada epoca de treinamento para o calculo do EQM.
	private double [] EQMs; // armazena os EQMs individuais e a media dos mesmos.
	private double EQM_Medio; // armazena a media dos EQMs.
	private final int EPOCA;
	private int numEpocas;


	public Rede(int numCamadas, int [] qtdNeuroniosPorCamada, int [] qtdEntradas){

		try {
			DataRequest("treina.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.numeroDeCamadas = numCamadas;
		this.camadas = new Camada[numCamadas];

		for(int i=0; i < qtdNeuroniosPorCamada.length; i++){
			this.camadas[i] = new Camada(qtdEntradas[i], qtdNeuroniosPorCamada[i]);
			this.camadas[i].setNumeroDeNeuronios(qtdNeuroniosPorCamada[i]);
		}

		EQMs = new double [entradas.length]; //new double[camadas[numeroDeCamadas-1].getNumeroDeNeuronios()];
		historicoDeSaidas = new float [entradas.length][camadas[numeroDeCamadas-1].getNumeroDeNeuronios()];
		EPOCA = entradas.length;
		numEpocas=0;

	}

	/**
	 * Faz a leitura do arquivo de dados no formato ".txt" e transporta dos dados para uma matriz de floats.
	 * Deve ser passado como parametro a nome do arquivo de texto.
	 * 
	 * @param arquivo
	 * @throws IOException 
	 */
	public void DataRequest(String arquivo) throws IOException{

		//entradas = new float[][];     //0 -> X0;
		//1 -> X1;
		//2 -> X2;
		//3 -> X3;
		//4 -> X4;
		//5 -> Y1;
		//6 -> Y2;
		//7 -> Y3;

		//Contar a quantidade de linhas do arquivo
		File arquivoLeitura = new File(arquivo);  
		LineNumberReader linhaLeitura = new LineNumberReader(new FileReader(arquivoLeitura));  
		linhaLeitura.skip(arquivoLeitura.length());  
		int qtdLinha = linhaLeitura.getLineNumber()+1;


		//Leitura do arquivo para inserção dos valores no vetor de entradas 'x'
		File f = new File(arquivo);
		Scanner scan = new Scanner(f);

		int line = 0;
		while(scan.hasNextLine()){

			String linha = new String();
			linha = scan.nextLine();
			String[] vetx = linha.split(" ");           
			//entradas = new float[qtdLinha][vetx.length]; // ERRADO: A CADA ITERARAÇAO UMA NOVA INSTANCIA DE 'entradas' É CRIADA E OS VALORES ANTIGOS SAO PERDIDOS //

			// Os dados de cada amostra são transferidos para a matriz de entradas
			for(int j = 0; j < vetx.length; j++)
				entradas[line][j] = Float.parseFloat(vetx[j]);

			line++;
		}
	}

	/**
	 * Realiza o treino da Rede
	 * 
	 */
	public void treinar(){

		setPesosCamadas();
		taxaAprendizagem = 0.1;
		erro = 10E-6;
		eqm_anterior = 10E9;
		eqm_atual = 0;

		while(Math.abs(eqm_atual - eqm_anterior) > erro){
			System.out.println("Época: " + numEpocas);
			for(int amostra = 0; amostra < EPOCA; amostra++){ //iterar pela quantidade de entradas. "EPOCA = Quantidade de Entradas"

				//ajustar saida de cada neuronio de cada camada
				//inicio da fase forwarding
				System.out.println("Amostra atual: " + amostra);
				for(int camada = 0; camada < camadas.length; camada++){
					float saidaAnterior[] = null;

					for(int neuronio = 0; neuronio < camadas[camada].getNumeroDeNeuronios(); neuronio++){ // este tava rodando com "amostra", porem deve rodar com "camada"

						if(camada==0){ // Camada de entrada. Deve ler os dados da matriz de entradas.

							float aux = camadas[camada].getNeuronios()[neuronio].SomatorioDaCamadaDeEntrada(amostra, entradas, neuronio, camadas[camada].getPesos());
							camadas[camada].getNeuronios()[neuronio].setSaida(camadas[camada].getNeuronios()[neuronio].Sigmoide(aux));

							/*
							System.out.println("Camada:" + camada);
							System.out.println(" \t Numero de neuronios da camada: " + camadas[camada].getNumeroDeNeuronios());
							System.out.println(" \t Neuronio atual: " + neuronio);
							System.out.println(" \t Somatorio ponderado das entradas do neurorio " + neuronio + ": " + aux);
							System.out.println(" \t Saida do neuronio " + neuronio + ": " + camadas[camada].getNeuronios()[neuronio].getSaida());*/



						} else{ // Camada escondida. Deve ler a saída dos neuronios da camada anterior.

							/*System.out.println("Camada:" + camada);
							System.out.println(" \t Numero de neuronios da camada: " + camadas[camada].getNumeroDeNeuronios());
							System.out.println(" \t Neuronio atual: " + neuronio);*/

							if (neuronio==0){ // Apenas uma vez (na primeira passada) instacia o vetor e pega os valores dos neuronios da camada anterior
								saidaAnterior = new float[camadas[camada-1].getNumeroDeNeuronios()]; 
								for(int a = 0; a < saidaAnterior.length; a++){ // Monta vetor com as saidas dos neuronios da camada anterior
									saidaAnterior[a] = (float) camadas[camada-1].getNeuronios()[a].getSaida();
								}
							}

							float aux = camadas[camada].getNeuronios()[neuronio].SomatorioDeCamadaEscondida(saidaAnterior, neuronio, camadas[camada].getPesos());
							camadas[camada].getNeuronios()[neuronio].setSaida(camadas[camada].getNeuronios()[neuronio].Sigmoide(aux));

							/*
							System.out.println(" \t Somatorio ponderado das entradas do neurorio " + neuronio + ": " + aux);
							System.out.println(" \t Saida do neuronio " + neuronio + ": " + camadas[camada].getNeuronios()[neuronio].getSaida());*/
						}
					}
				}/*Aqui todos os neuronios de todas as camadas ja estam com os suas saidas calculadas
				fase forwarding completa*/


				/*Armazenas as saidas de uma amostra numa posiçao do vetror historicoDeSaidas.
				  Esse vetor sera usado para calcular o EQM da amostra. */
				for(int a=0; a<camadas[numeroDeCamadas-1].getNumeroDeNeuronios(); a++){
					historicoDeSaidas[amostra][a] = (float) camadas[numeroDeCamadas-1].getNeuronios()[a].getSaida();
				}

				//inicio da fase de backward (determinar gradientes locais: da ultima para a primeira camada)	
				double gradienteCamadaAtualMaisUm [] = null;
				for(int j = camadas.length; j > 0; j--){ 

					/* 
					 * Aqui usa-se 'j-1' como camada atual e 'j' como "camada atual + 1" porque o java começa a contar do zero. 
					 * Melhor explicando, quando fazemos 'j = camadas.length' pegamos a quantidade absoluta de camadas, mas a primeira
					 * posição do vetor camadas não é o 1 (um) e sim o 0 (zero).
					 * 
					 * Exemplo: Vamos imaginar que o existam 3 camadas na rede, camadas.length retorna 3 mas as três posições do vetor 
					 * que se pode acessar são 0 , 1 e 2 (o java começa a contar do zero). Assim se 'j==3' e eu quero acessar a terceira camada
					 * uso 'j-1'. Da mesma forma se 'j-1==1' e eu quero acessar a "camada atual + 1" uso apenas 'j'.
					 *   
					 * OBS: o mesmo raciocínio vale para o 'k-1' usado para pegar o neuronio atual.        
					 */
					int CAMADA_ATUAL = j-1; 
					int CAMADA_ATUAL_MAIS_UM = j;

					int numNeuronios = camadas[CAMADA_ATUAL].getNumeroDeNeuronios(); 
					int saidaDesejada =7; // posição da saída desejada na matriz de entradas;

					for(int k = numNeuronios; k > 0; k--){
						if(CAMADA_ATUAL == camadas.length-1){ // camada de saida

							// Calcular gradiente local do k-esimo neuronio da camada j
							camadas[CAMADA_ATUAL].getNeuronios()[k-1].CalcularGradienteLocal(entradas[amostra][saidaDesejada]);

							double saidasNeuroniosCamadaAtualMeunosUm[] = new double [camadas[CAMADA_ATUAL-1].getNumeroDeNeuronios()];
							for(int z=0; z<saidasNeuroniosCamadaAtualMeunosUm.length; z++){
								saidasNeuroniosCamadaAtualMeunosUm[z] = camadas[CAMADA_ATUAL-1].getNeuronios()[z].getSaida();
							}

							//Ajuste dos pesos: recebe a saida do neuronio correspondente da j-esima camada -1.
							camadas[CAMADA_ATUAL].ajustaPesosDaCamada(k-1, taxaAprendizagem, saidasNeuroniosCamadaAtualMeunosUm); 

							saidaDesejada--;

						} else{ // camadas mais internas

							if(k==numNeuronios){ // Apenas na primeira iteração instacia o vetor e armazenas os valores nele
								
								gradienteCamadaAtualMaisUm = new double[camadas[CAMADA_ATUAL_MAIS_UM].getNumeroDeNeuronios()]; 

								for(int a = 0; a < gradienteCamadaAtualMaisUm.length; a++)
									gradienteCamadaAtualMaisUm[a] = camadas[CAMADA_ATUAL_MAIS_UM].getNeuronios()[a].getGradienteLocal(); //valores do gradiente da camada anterior(j+1)
							}

							//calcula o gradiente local de um neuronio da camada atual
							camadas[CAMADA_ATUAL].getNeuronios()[k-1].calcularGradienteLocal(gradienteCamadaAtualMaisUm, camadas[CAMADA_ATUAL_MAIS_UM].getPesos(), k-1);


							// Depois de calcular o gradiente local de um neuronio, deve-se ajustar a matriz de pesos da camada (deve atualizar "a porção" do neuronio em questão)
							if(CAMADA_ATUAL>0 && CAMADA_ATUAL != camadas.length-1){ // camadas.length-1 é a camada de saída (que já foi calculada no 'if' da linha 184')



								/*
								 * Doug, como temos duas camadas apenas esse if não é usado pq a camada de saida é calculada no if da linha 184
								 * e a camada intermediária faz interface com matriz de entradas... Mesmo assim, vamos deixar esse if aqui só
								 * por uma questão de genericidade.
								 * 
								 */

								// camadas escondidas: usam a saida de um neuronio da camada anterior
								camadas[CAMADA_ATUAL].getNeuronios()[k-1].ajustaPesos(camadas[CAMADA_ATUAL].getNeuronios()[k-1].getSaida(), k-1, taxaAprendizagem);

							}else{ 

								// camada de entrada: usa a matriz de dados de treinamento
								double saidasMatrizDeDados[] = new double [entradas[0].length-3];
								for(int z=0; z<saidasMatrizDeDados.length; z++){
									saidasMatrizDeDados[z] = entradas[amostra][z];
								}
								camadas[CAMADA_ATUAL].ajustaPesosDaCamada(k-1, taxaAprendizagem, saidasMatrizDeDados);
							}

						}

					}
				}

				calculaEQM(amostra);

			}
			numEpocas++;
			calculaEQMmedio();
			System.out.println("Erro atual: " + Math.abs(eqm_atual - eqm_anterior));
		}	
		
	}

	/**
	 * Inicializa o vetor de pesos dos neuronio de cada camada
	 */
	public void setPesosCamadas(){

		for(int i = 0; i < camadas.length; i++){

			camadas[i].PesosAleatorios();

		}
	}

	/**
	 *  Calcula o Erro Quadratico Medio para uma amostra.
	 * 
	 */
	public void calculaEQM(int amostra){

		double aux=0;
		int saidaDesejada=5;
		for(int i = 0; i < camadas[numeroDeCamadas-1].getNumeroDeNeuronios(); i++){

			aux += Math.pow((historicoDeSaidas[amostra][i] - entradas[amostra][saidaDesejada]), 2); // "entradas[amostra][entradas[0].length]" saida desejada
			saidaDesejada++;
		}

		EQMs[amostra] = aux/2;
	}

	/**
	 *  Calcula a media para o Erro Quadratico Medio.
	 * 
	 */
	public void calculaEQMmedio(){

		double aux=0;

		for(int i = 0; i < EQMs.length; i++ ){

			aux += EQMs[i];

		}

		EQM_Medio = aux/entradas.length;
	}

	public int getNumEpocas() {
		return numEpocas;
	}

	public void setNumEpocas(int numEpocas) {
		this.numEpocas = numEpocas;
	}



}
