package model;

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
				for(int j = 0; j < camadas.length; j++){
					for(int k = 0; k < camadas[amostra].getNumeroDeNeuronios(); k++){

						if(j==0){ // Camada de entrada. Deve ler os dados da matriz de entradas.

							float aux = camadas[j].getNeuronios()[k].SomatorioDaCamadaDeEntrada(amostra, entradas);
							camadas[j].getNeuronios()[k].setSaida(camadas[j].getNeuronios()[k].Sigmoide(aux));

						} else{ // Camada escondida. Deve ler a saída dos neuronios da camada anterior.

							float [] saidaAnterior = new float[camadas[j-1].getNumeroDeNeuronios()];

							for(int a = 0; a < saidaAnterior.length; a++){ // Monta vetor com as saidas da camada anterior
								saidaAnterior[a] = (float) camadas[j-1].getNeuronios()[a].getSaida();
							}

							float aux = camadas[j].getNeuronios()[k].SomatorioDeCamadaEscondida(saidaAnterior);
							camadas[j].getNeuronios()[k].setSaida(camadas[j].getNeuronios()[k].Sigmoide(aux));
						}
					}
				}//Aqui todos os neuronios de todas as camadas ja estam com os suas saidas calculadas
				//fase forwarding completa
				
				
				/*Armazenas as saidas de uma amostra numa posiçao do vetror historicoDeSaidas.
				  Esse vetor sera usado para calcular o EQM da amostra. */
				for(int a=0; a<camadas[numeroDeCamadas].getNumeroDeNeuronios(); a++){
					historicoDeSaidas[amostra][a] = (float) camadas[numeroDeCamadas].getNeuronios()[a].getSaida();
				}

				//inicio da fase de backward (determinar gradientes locais da ultima para a primeira camada)	
				for(int j = camadas.length; j > 0; j--){ //"camadas.length" = quantidade de camadas
					int numNeuronios = camadas[j].getNumeroDeNeuronios();
					int saida =7;
					for(int k = numNeuronios - 1; k > 0; k--){
					if(k == numNeuronios){
							camadas[j].getNeuronios()[k].CalcularGradienteLocal(entradas[amostra][saida]);
							
							//camadas[j].getNeuronios()[k].ajustaPesos();
							saida--;
						} else{
							double[] gradienteAnterior = new double[camadas[j+1].getNumeroDeNeuronios()];
							for(int a = 0; a < gradienteAnterior.length; a++){
								gradienteAnterior[a] = camadas[j+1].getNeuronios()[a].getGradienteLocal(); //valores do gradiente da camada anterior(j+1)
							}
							for(int a = 0; a < camadas[amostra].getNumeroDeNeuronios(); a++){ //calculo do gradiente local de cada neuronio da camada
								for(int b = 0; b < camadas[j+1].getNumeroDeNeuronios(); b++){ //calcula o gradiente local de um neuronio especifico
									camadas[a].getNeuronios()[b].setGradienteLocal(gradienteAnterior, camadas[a].getPesosCorrespondentes(b, camadas[j+1].getNumeroDeNeuronios()), camadas[j+1].getNumeroDeNeuronios());
								}
							}
							//camadas[j].getNeuronios()[k].ajustaPesos();
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
