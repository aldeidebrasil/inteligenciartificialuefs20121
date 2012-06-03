package model;

import java.io.*;
import java.util.*;

public class Rede {

	private int numeroDeCamadas;
	private Camada[] camadas;
	private double taxaAprendizagem;
	private double erro;
	private double eqm_atual;
	private double eqm_anterior;
	private float[][] entradas;
	private float[][] historicoDeSaidas; // armazena as saidas de cada epoca de treinamento para o calculo do EQM.
	private double [] EQMs; // armazena os EQMs individuais e a media dos mesmos.
	private double EQM_Medio; // armazena a media dos EQMs.
	private final int EPOCA;
	private int numEpocas;


	public Rede(int numCamadas){

		this.numeroDeCamadas = numCamadas;
		this.camadas = new Camada[numCamadas];
		EQMs = new double[camadas[numeroDeCamadas].getNumeroDeNeuronios()];
		historicoDeSaidas = new float [entradas.length][camadas[numeroDeCamadas].getNumeroDeNeuronios()];
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
		int qtdLinha = linhaLeitura.getLineNumber();

		//Leitura do arquivo para inserção dos valores no vetor de entradas 'x'
		File f = new File(arquivo);
		Scanner scan = new Scanner(f);

		int line = 0;
		while(scan.hasNextLine()){

			String linha = new String();
			linha = scan.nextLine();
			String[] vetx = linha.split(" ");           
			entradas = new float[qtdLinha][vetx.length];

			// Os dados de cada amostra são transferidos para a matriz de entradas
			for(int j = 0; j < vetx.length; j++){
				entradas[line][j] = Float.parseFloat(vetx[j]);                   
			}
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
		eqm_anterior = 1000000000;
		eqm_atual = 0;

		while(Math.abs(eqm_atual - eqm_anterior) > erro){

			for(int amostra = 0; amostra < EPOCA; amostra++){ //iterar pela quantidade de entradas. "EPOCA = Quantidade de Entradas"

				//ajustar saida de cada neuronio de cada camada
				//inicio da fase forwarding
				for(int camada = 0; camada < camadas.length; camada++){
					for(int neuronio = 0; neuronio < camadas[amostra].getNumeroDeNeuronios(); neuronio++){

						if(camada==0){ // Camada de entrada. Deve ler os dados da matriz de entradas.

							float aux = camadas[camada].getNeuronios()[neuronio].SomatorioDaCamadaDeEntrada(amostra, entradas);
							camadas[camada].getNeuronios()[neuronio].setSaida(camadas[camada].getNeuronios()[neuronio].Sigmoide(aux));

						} else{ // Camada escondida. Deve ler a saída dos neuronios da camada anterior.

							float [] saidaAnterior = new float[camadas[camada-1].getNumeroDeNeuronios()];

							for(int a = 0; a < saidaAnterior.length; a++){ // Monta vetor com as saidas dos neuronios da camada anterior
								saidaAnterior[a] = (float) camadas[camada-1].getNeuronios()[a].getSaida();
							}

							float aux = camadas[camada].getNeuronios()[neuronio].SomatorioDeCamadaEscondida(saidaAnterior);
							camadas[camada].getNeuronios()[neuronio].setSaida(camadas[camada].getNeuronios()[neuronio].Sigmoide(aux));
						}
					}
				}/*Aqui todos os neuronios de todas as camadas ja estam com os suas saidas calculadas
				fase forwarding completa*/


				/*Armazenas as saidas de uma amostra numa posiçao do vetror historicoDeSaidas.
				  Esse vetor sera usado para calcular o EQM da amostra. */
				for(int a=0; a<camadas[numeroDeCamadas].getNumeroDeNeuronios(); a++){
					historicoDeSaidas[amostra][a] = (float) camadas[numeroDeCamadas].getNeuronios()[a].getSaida();
				}

				//inicio da fase de backward (determinar gradientes locais: da ultima para a primeira camada)	
				for(int j = camadas.length; j > 0; j--){ //"camadas.length" = quantidade de camadas
					int numNeuronios = camadas[j].getNumeroDeNeuronios();
					int saida =7;
					for(int k = numNeuronios - 1; k > 0; k--){
						if(k == numNeuronios){ // camada de saida
							// Calcular gradiente local do k-esimo neuronio da camada j
							camadas[j].getNeuronios()[k].CalcularGradienteLocal(entradas[amostra][saida]);
							//Ajuste dos pesos: recebe a saida do neuronio correspondente da j-esima camada -1.
							camadas[j].getNeuronios()[k].ajustaPesos(camadas[j-1].getNeuronios()[k].getSaida(), k, taxaAprendizagem);  
							saida--;
						} else{ // camadas mais internas
							double[] gradienteAnterior = new double[camadas[j+1].getNumeroDeNeuronios()];
							for(int a = 0; a < gradienteAnterior.length; a++){
								gradienteAnterior[a] = camadas[j+1].getNeuronios()[a].getGradienteLocal(); //valores do gradiente da camada anterior(j+1)
							}
							for(int a = 0; a < camadas[j].getNumeroDeNeuronios(); a++){ //calculo do gradiente local de cada neuronio da camada
								for(int b = 0; b < camadas[j+1].getNumeroDeNeuronios(); b++){ //calcula o gradiente local de um neuronio especifico
									camadas[a].getNeuronios()[b].setGradienteLocal(gradienteAnterior, camadas[a].getPesosCorrespondentes(b, camadas[j+1].getNumeroDeNeuronios()), camadas[j+1].getNumeroDeNeuronios());
								}
							}

							// Depois de calcular o gradiente local de uma camada, deve-se ajustar a matriz de pesos da camada
							if(j!=0){ // camadas escondidas: usam a saida de um neuronio da camada anterior
								camadas[j].getNeuronios()[k].ajustaPesos(camadas[j-1].getNeuronios()[k].getSaida(), k, taxaAprendizagem);
							}else{ // camada de entrada: usa a matriz de dados de treinamento
								camadas[j].getNeuronios()[k].ajustaPesos(entradas[amostra][k], k, taxaAprendizagem); 
							}

						}

					}
				}

				calculaEQM(amostra);

			}
			numEpocas++;
			calculaEQMmedio();
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
		for(int i = 0; i < camadas[numeroDeCamadas].getNumeroDeNeuronios(); i++){

			aux += Math.pow((historicoDeSaidas[amostra][i] - entradas[amostra][entradas[0].length]), 2); // "entradas[amostra][entradas[0].length]" saida desejada

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

}
