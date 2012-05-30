package model;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Andre e Douglas
 */
public class Neuronio {

	private float[][] entradas;
	public float[] pesos;
	private double entradaPonderada;
	private double saida;
	private float taxaDeAprendizado;
	private boolean erroExiste;
	private Random aleatorio;
	private double gradienteLocal;
	
	public int numEpocas = 0;	

	public Neuronio() {
		aleatorio = new Random();		
		pesos = new float[4];
		gradienteLocal = 0;
		
		for(int i =0; i <=3; i++){
			pesos[i]=(float) 0.0;

		}

	}

	/**
	 * Atribui valores aleatï¿½rios ao vetor de pesos da rede.
	 * A aleatoriedade entre 0 e 1 ï¿½ garantida com o uso do java.util.Random 
	 * 
	 */
	public void setaPesosAleatorios() {
		this.pesos[0] = (float) aleatorio.nextFloat();
		this.pesos[1] = (float) aleatorio.nextFloat();
		this.pesos[2] = (float) aleatorio.nextFloat();
		this.pesos[3] = (float) aleatorio.nextFloat();

	}

	/**
	 * Realiza a soma ponderada das entradas do neuronio. Tem como entrada o 
	 * numero da amostra atual e devolve o valor a ser enviado para a funçao de ativaçao. 
	 * 
	 * @param i : numero da amostra atual
	 * @return somatorio : somatorio ponderado das entradas do neuronio
	 */
	public float Somatorio(int i) {
		
		return  entradas[i][0] * pesos[0] + // x0*w0
				entradas[i][1] * pesos[1] + // x1*w1
				entradas[i][2] * pesos[2] + // x2*w2
				entradas[i][3] * pesos[3] + // x3*w3
				entradas[i][4] * pesos[4];  // x4*w4
	}

	/**
	 * Realiza a soma ponderada das entradas de um neuronio da camada escondida. Tem como entrada o 
	 * vetor com as saidas da camada anterior e devolve o valor a ser enviado para a funçao de ativaçao. 
	 * 
	 * @param entrada : vetor das saidas da camada antetior
	 * @return somatorio : somatorio ponderado das entradas do neuronio
	 */
	public float SomatorioDeCamadaEscondida(float [] entrada) {

		float aux = 0;
		
		for(int a = 0; a < entrada.length;a++){
			aux+= entrada[a] * pesos[a];
		}
		
		this.entradaPonderada = aux;
		return  aux;
	}
	
	/**
	 * Realiza a soma ponderada das entradas de um neuronio da camada de entrada. Tem como entrada o 
	 * numero da amostra atual e a matriz de dados. Devolve o valor a ser enviado para a funçao de 
	 * ativaçao. 
	 * 
	 * @param i : numero da amostra atual
	 * @param entradas : matriz de dados montada a partir do arquivo de teste.
	 * @return somatorio : somatorio ponderado das entradas do neuronio
	 */
	public float SomatorioDaCamadaDeEntrada(int i, float[][]entradas) {

		this.entradas = entradas;
		float aux = 0;
		
		for(int a = 0; a < entradas[0].length;a++){
			aux+= entradas[i][a] * pesos[a];
		}
		
		return  aux;
	}
	
	/**
	 * Ajusta o valor do vetor de peso para a amostra atual, se o resultado encontrado for diferente da resposta desejada.
	 * 
	 * @param saidaDaFuncao : saida/resposta da funï¿½ï¿½o bipoloar
	 * @param amostra : numero da amostra que estï¿½ sendo testada (amostra atual) 
	 */
	public void ajustaPesos(float saidaDaFuncao, int amostra) {

		if (saidaDaFuncao != entradas[amostra][4]) {

			// w          w-1             n            d(k): saida esperada        y: saida    x(k): vetor de entrada   
			pesos[0] = pesos[0] + taxaDeAprendizado * (entradas[amostra][4] - saidaDaFuncao) * entradas[amostra][0];
			pesos[1] = pesos[1] + taxaDeAprendizado * (entradas[amostra][4] - saidaDaFuncao) * entradas[amostra][1];
			pesos[2] = pesos[2] + taxaDeAprendizado * (entradas[amostra][4] - saidaDaFuncao) * entradas[amostra][2];
			pesos[3] = pesos[3] + taxaDeAprendizado * (entradas[amostra][4] - saidaDaFuncao) * entradas[amostra][3];

			erroExiste=true;

		}
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
	 * @param numAmostra
	 */
	public void CalcularGradienteLocal(float saidaDesejada, int numAmostra){
		 
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
	
	public void setEntradas(float[][] entradasTreino) {

		this.entradas = entradasTreino;

	}
	
	public void setTaxaDeAprendizado(float taxa) {
		this.taxaDeAprendizado = taxa;
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
//	 * Realiza o treinamento da rede. Realiza o somatorio ponderado de uma amostra 
//	 * e passa esse valor para a funï¿½ï¿½o de ativaï¿½ï¿½o, caso este valor seja diferente
//	 * da resposta desejada o vetor de pesos ï¿½ ajustado.
//	 */
//	public void treinamento() {
//
//
//		//setPesos(); // inicializa vetor de pesos com valores aleatorios
//		taxaDeAprendizado = (float) 0.01; // inicializa taxa de aprendizado
//
//
//		int epocaSemErros=0;
//		numEpocas=0;
//
//
//		while (epocaSemErros < 2) {
//
//			erroExiste = false;
//
//			//System.out.println("EPOCA: " + numEpocas);	
//
//			for (int i = 0; i < entradas.length; i++) {
//
//				// Soma Ponderada das Entradas
//				float somatorio = Somatorio(i);
//				//System.out.println("somatorio = " + somatorio);
//
//				// Resultado da soma ponderada passando pela funï¿½ï¿½o de ativaï¿½ï¿½o
//				//int saidaFuncaoSinal = funcaoDeAtivacaoBipolar(somatorio);
//				//System.out.println("saida funcao = " + saidaFuncaoSinal + "  |  saida desejada= " + entradas[i][4]);
//
//				// Ajuste dos valores do vetor de pesos "w"
//				//ajustaPesos(saidaFuncaoSinal, i);
//			}
//
//			numEpocas++;
//
//			if(!erroExiste)
//				epocaSemErros++;
//			else
//				epocaSemErros=0;
//
//		}
//	}
//
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
