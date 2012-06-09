package model;

/**
 *
 * @author André e Douglas
 */

public class Neuronio {

	
	public float[] pesos;
	private double entradaPonderada;
	private double saida;
	private double gradienteLocal;	
	public int numEpocas = 0;	

	public Neuronio(int qtdPesos) {
				
		pesos = new float[qtdPesos];
		gradienteLocal = 0;
		
		for(int i =0; i < qtdPesos; i++){
			pesos[i]= 0;
		}
	}

	/**
	 * Realiza a soma ponderada das entradas de um neuronio da camada escondida. Tem como entrada o 
	 * vetor com as saidas da camada anterior e devolve o valor a ser enviado para a funçao de ativaçao. 
	 * 
	 * @param entrada : vetor das saidas da camada antetior
	 * @return somatorio : somatorio ponderado das entradas do neuronio
	 */
	
	public float SomatorioDeCamadaEscondida(float [] entrada, int num_neuronio, float [][] pesos) {

		float aux = 0;
		
		for(int a = 0; a < entrada.length-3;a++){ // A subtração de 3 é porque as três ultimas posições são as saídas desejadas
 			aux+= entrada[a] * pesos[num_neuronio][a];
		}
		
		this.entradaPonderada = aux;
		return  aux;
	}
	
	/**
	 * Realiza a soma ponderada das entradas de um neuronio da camada de entrada. Tem como entrada o 
	 * numero da amostra atual e a matriz de dados. Devolve o valor a ser enviado para a funçao de 
	 * ativaçao. 
	 * 
	 * @param num_amostra : numero da amostra atual
	 * @param entradas : matriz de dados montada a partir do arquivo de teste.
	 * @param num_neuronio : numero do neuronio que se deseja calcular o somatorio das entradas
	 * @param pesos : matriz de pesos da camada onde o neuronio está.
	 * @return somatorio : somatorio ponderado das entradas do neuronio.
	 */
	
	public float SomatorioDaCamadaDeEntrada(int num_amostra, float[][]entradas, int num_neuronio, float [][] pesos) {

		// this.entradas = entradas; Nao precisa!  Vamos usar apenas os valores da camada que é passado como argumento
		float aux = 0;
		
		for(int a = 0; a < entradas[0].length-3;a++){ // A subtração de 3 é porque as três ultimas posições são as saídas desejadas
			aux+= entradas[num_amostra][a] * pesos[num_neuronio][a];
		}
		return  aux;
	}
	
	/**
	 * Ajusta o valor do vetor de peso para a amostra atual, se o resultado encontrado for diferente da resposta desejada.
	 * 
	 * @param saidaDoNeuronioAnterior : saida/resposta da funcao sigmoide
	 * @param amostra : numero da amostra que esta sendo testada (amostra atual) 
	 */
	public void ajustaPesos(double saidaDoNeuronioAnterior, int posicaoSinapse, double taxaDeAprendizagem) {
			
		pesos[posicaoSinapse]+= taxaDeAprendizagem*gradienteLocal*saidaDoNeuronioAnterior; 
	}

	/**
	 * Funcao de ativação Sigmoide
	 * 
	 * @param x
	 */
	public float Sigmoide(double x){
		
		return  (float) 1/(float) (1+ Math.exp(-0.5 * x));
	}
	
	
	/**
	 * Calcula a derivada da Sigmoide no somatorio das entradas ponderadas 
	 * A derivada é utilizada para o cálculo do gradiente local
	 * @param somatorio
	 * @return
	 */
	public double DerivadaSigmoide(){ 
				
		return (0.5 * entradaPonderada * (1 - entradaPonderada));
				
	}
	
	/**
	 * 
	 * @param saidaDesejada
	 * @param saidaNeuronio
	 */
	public void CalcularGradienteLocal(float saidaDesejada){
		 
		//Equação de calculo da ultima camada (No caso do backpropagation a primeira)
		gradienteLocal = (saidaDesejada - saida) * DerivadaSigmoide();
		
	}
	
	/**
	 * 
	 * @param qtdNeuroniosCamadaAnt
	 * @param neuroniosCamadaAnt
	 */
	public void CalcularGradienteLocal(int qtdNeuroniosCamadaAnt, Neuronio[] neuroniosCamadaAnt){
		
		for(int i = 0; i == qtdNeuroniosCamadaAnt; i++){
			gradienteLocal += neuroniosCamadaAnt[i].getGradienteLocal() * neuroniosCamadaAnt[i].getPesos()[i];
		}
		
		gradienteLocal *= DerivadaSigmoide(); 
	}

	public double getSaida() {
		return saida;
	}

	public void setSaida(double saida) {
		this.saida = saida;
	}
	
	public double getEntradaPonderada() {
		return entradaPonderada;
	}

	public void setEntradaPonderada(double saidaPonderada) {
		this.entradaPonderada = saidaPonderada;
	}

	public float[] getPesos() {
		return pesos;
	}

	public void setPesos(float[] pesos) {
		this.pesos = pesos;
	}

	public double getGradienteLocal() {
		return gradienteLocal;
	}

	public void setGradienteLocal(double gradienteLocal) {
		this.gradienteLocal = gradienteLocal;
	}
	
	/**
	 * 
	 * @param gradienteAnterior
	 * @param pesosAnterior : Vetor dos pesos sinapticos correspondentes a neuronio atual
	 * @param numNeuronios : Quantidade de neuronios da camada anterior
	 */
	public void setGradienteLocal(double[] gradienteAnterior, float[] pesosCorrespondentes, int numNeuronios){
		double gradiente = 0;
		for(int i = 0; i < numNeuronios; i++){
			gradiente += gradienteAnterior[i] * pesosCorrespondentes[i]; 
			gradiente*= DerivadaSigmoide();
		}
		this.gradienteLocal = gradiente;
	}
	
//	/**
//	 * Realiza o teste da rede.
//	 * 
//	 * @throws FileNotFoundException
//	 */
//	public void teste() throws FileNotFoundException {
//
//
//		for(int i=0; i <entradas.length; i++){
//			for(int j=0; j<entradas[0].length; j++ )
//				entradas[i][j]=0;
//		}
//
//		DataRequest("testePerceptron.txt");
//
//		System.out.print("Saida da rede: ");
//		for (int i = 0; i < 9; i++) { // quantidade de amostras de teste
//
//			// Soma Ponderada das Entradas
//			float somatorio = Somatorio(i);
//			//System.out.println("somatorio = " + somatorio);
//
//			// Resultado da soma ponderada passando pela funï¿½ï¿½o de ativaï¿½ï¿½o
//			//int saidaFuncaoSinal = funcaoDeAtivacaoBipolar(somatorio);
//			//System.out.print(saidaFuncaoSinal);
//
//
//		}
//		System.out.println("");
//
//	}
//
//	//System.out.println("Acertos: " + (numeroDeAcertos*100)/9 + "%");
//
//
//
//	/**
//	 * Faz a leitura do arquivo de dados no formato ".txt" e transporta dos dados para uma matriz de floats.
//	 * Deve ser passado como parametro a nome do arquivo de texto.
//	 * 
//	 * @param arquivo
//	 * @throws FileNotFoundException
//	 */
//	public void DataRequest(String arquivo) throws FileNotFoundException{
//
//		entradas = new float[30][5]; 	//0 -> X0;
//										//1 -> X1;
//										//2 -> X2;
//										//3 -> X3;
//										//4 -> saida desejada
//
//		//Leitura do arquivo para inserção dos valores no vetor de entradas 'x'
//		File f = new File(arquivo);
//		Scanner scan = new Scanner(f);
//
//		int line = 0;
//		while(scan.hasNextLine()){
//
//			String linha = new String();
//			linha = scan.nextLine();
//			String[] vetx = linha.split(" ");           
//
//			for(int j = 0; j < 5; j++){
//				entradas[line][j] = Float.parseFloat(vetx[j]);                    
//			}
//			entradas[line][4] = Float.parseFloat(vetx[4]); //saida desejada é a posicação 4 da matriz de cada amostra
//			line++;
//		}
//	}
	
	
}
