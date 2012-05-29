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
	
	public Rede(int numCamadas){
		
		this.numeroDeCamadas = numCamadas;
		this.camadas = new Camada[numCamadas];
		EQMs = new double[camadas[numeroDeCamadas].getNumeroDeNeuronios()+1]; // "+1" posicao que guarda a media dos EQMs.
		historicoDeSaidas = new float [entradas.length][camadas[numeroDeCamadas].getNumeroDeNeuronios()];
		
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
			
			for(int i = 0; i < entradas.length; i++){ //iterar pela quantidade de entradas
				
				//ajustar saida de cada neuronio de cada camada
				//inicio da fase forwarding
				for(int j = 0; j < camadas.length; j++){
					for(int k = 0; k < camadas[i].getNumeroDeNeuronios(); k++){
						
						if(j==0){ // Camada de entrada. Deve ler os dados da matriz de entradas.
							
							float aux = camadas[j].getNeuronios()[k].SomatorioDaCamadaDeEntrada(i, entradas);
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
			}//fim do for. Aqui todos os neuronios de todas as camadas ja estam com os suas saidas calculadas
			//fase forwarding completa
				
			//inicio da fase de backward (determinar gradientes locais da ultima para a primeira camada)	
				for(int j = camadas.length; j > 0; j--){ //iterar pela quantidade de camadas
					int numNeuronios = camadas[j].getNumeroDeNeuronios();
					for(int k = numNeuronios - 1; k > 0; k--){
						if(k == numNeuronios){
							//camadas[j].getNeuronios()[k].calcularGradienteLocal(entradas[5], i);
							//camadas[j].getNeuronios()[k].ajustaPesos();
						} else{
							double[] gradienteAnterior = new double[camadas[j+1].getNumeroDeNeuronios()];
							for(int a = 0; a < gradienteAnterior.length; a++){
								gradienteAnterior[a] = camadas[j+1].getNeuronios()[a].getGradienteLocal(); //valores do gradiente da camada anterior(j+1)
							}
							for(int a = 0; a < camadas[i].getNumeroDeNeuronios(); a++){ //calculo do gradiente local de cada neuronio da camada
								for(int b = 0; b < camadas[j+1].getNumeroDeNeuronios();b++){ //calcula o gradiente local de um neuronio especifico
									
								}
								
							}
							//camadas[j].getNeuronios()[k].CalculaGradienteLocal(camadas[j+1].getNumeroDeNeuronios(), camada[j+1].getNeuronios());
							//camadas[j].getNeuronios()[k].ajustaPesos();
						}
											
					}
				}
			}
			//Epoca++;
			//CalcularEQM();
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
	 *  Calcula o Erro Quadratico Medio de cada neuronio da camada de saida e a media dos EQMs.
	 * 
	 */
	public void CalcularEQM(){
		
		for(int i = 0; i < camadas[numeroDeCamadas].getNumeroDeNeuronios(); i++){
			
			double aux=0;	
			
			for(int a=0; a < historicoDeSaidas.length; a++){
					
				    aux += Math.pow((historicoDeSaidas[i][a] - entradas[i][entradas[0].length]), 2); // "entradas[i][entradas[0].length]" saida desejada
				
			}
			
			EQMs[i] = aux/2;
		}
	 	
		// Calcular a media dos EQMs.
	}
	

}
