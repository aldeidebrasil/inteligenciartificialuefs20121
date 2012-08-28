package perceptronsimples;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class NeuronioTico { //Adaline

	private float[][] entradas;
	public float[] pesos;
	private float taxaDeAprendizado;
	private Random aleatorio;
	private int numeroDeAcertos;
	public int numEpocas = 0;
	private float EQM;
	private float EQMant;
	private float erro;
	private XYSeries dadosEqm;

	public NeuronioTico(){
		
		aleatorio = new Random();
		numeroDeAcertos =0;
		EQM = 0;
		pesos = new float[5];

		for(int i =0; i <=4; i++){
			pesos[i]=(float) 0.0;

		}
		
	}
	
	public void setEntradas(float[][] entradasTreino) {

		this.entradas = entradasTreino;

	}


	/**
	 * Atribui valores aleatórios ao vetor de pesos da rede.
	 * A aleatoriedade entre 0 e 1 é garantida com o uso do java.util.Random 
	 * 
	 */
	public void setaPesosAleatorios() {
		this.pesos[0] = (float) aleatorio.nextFloat();
		this.pesos[1] = (float) aleatorio.nextFloat();
		this.pesos[2] = (float) aleatorio.nextFloat();
		this.pesos[3] = (float) aleatorio.nextFloat();
		this.pesos[4] = (float) aleatorio.nextFloat();

	}

	public void setTaxaDeAprendizado(float taxa) {
		this.taxaDeAprendizado = taxa;
	}

	public float Somatorio(int i) {

		return  entradas[i][0] * pesos[0] + // x0*w0
				entradas[i][1] * pesos[1] + // x1*w1
				entradas[i][2] * pesos[2] + // x2*w2
				entradas[i][3] * pesos[3] + // x3*w3
				entradas[i][4] * pesos[4];  // x4*w4
	}
	
	public void CalcEQM() {
		
		float u = 0;
		EQM = 0;
		for(int i = 0; i < entradas.length; i++){
			u = Somatorio(i);
			EQM = EQM + (float) Math.pow(entradas[i][5] - u,2); // (d(k) - u(k))²: saida desejada com a saida gerada pelo combinador linear
		}
		EQM = EQM/entradas.length;
	}

	/**
	 * Retorna a saída do neuronio após a análise do valor ponderado. 
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
	 * @param saidaCombLinear : saida/resposta da função bipoloar
	 * @param amostra : numero da amostra que está sendo testada (amostra atual) 
	 */
	public void ajustaPesos(float saidaCombLinear, int amostra) {

		if (saidaCombLinear != entradas[amostra][4]) {

			// w          w-1             n               d(k): saida esperada             y: saida          x(k): vetor de entrada   
			pesos[0] = pesos[0] + taxaDeAprendizado * (entradas[amostra][5] - saidaCombLinear) * entradas[amostra][0];
			pesos[1] = pesos[1] + taxaDeAprendizado * (entradas[amostra][5] - saidaCombLinear) * entradas[amostra][1];
			pesos[2] = pesos[2] + taxaDeAprendizado * (entradas[amostra][5] - saidaCombLinear) * entradas[amostra][2];
			pesos[3] = pesos[3] + taxaDeAprendizado * (entradas[amostra][5] - saidaCombLinear) * entradas[amostra][3];
			pesos[4] = pesos[4] + taxaDeAprendizado * (entradas[amostra][5] - saidaCombLinear) * entradas[amostra][4];

		}
	}

	/**
	 * Realiza o treinamento da rede. Realiza o somatorio ponderado de uma amostra 
	 * e passa esse valor para a função de ativação, caso este valor seja diferente
	 * da resposta desejada o vetor de pesos é ajustado.
	 */
	public void treinamento() {


		setaPesosAleatorios(); // inicializa vetor de pesos com valores aleatorios
		taxaDeAprendizado = (float) 0.0025; // inicializa taxa de aprendizado
		erro = (float) 0.000001;
		numEpocas = 0;
		EQMant = 10000000;
		CalcEQM();
		
		dadosEqm = new XYSeries("Erro Quadrático Médio");
		
		while (Math.abs(EQM - EQMant) > erro) {
			
			EQMant = EQM;

			//System.out.println("EPOCA: " + numEpocas);	

			for (int i = 0; i < entradas.length; i++) {

				//System.out.println("somatorio = " + somatorio);
				float somatorio = Somatorio(i);
				
				// Ajuste dos valores do vetor de pesos "w"
				ajustaPesos(somatorio, i);
			}

			numEpocas++;
			
			dadosEqm.add(numEpocas, EQM);
			CalcEQM();

		}
	}

	/**
	 * Realiza o teste da rede.
	 * 
	 * @throws FileNotFoundException
	 */
	public void teste() throws FileNotFoundException {


		for(int i=0; i < entradas.length; i++){
			for(int j=0; j< entradas[0].length; j++ )
				entradas[i][j]=0;
		}

		DataRequest("testeAdaline.txt");

		System.out.print("Saida da rede: ");
		for (int i = 0; i <= 14; i++) { // quantidade de amostras de teste

			// Soma Ponderada das Entradas
			float somatorio = Somatorio(i);
			//System.out.println("somatorio = " + somatorio);

			// Resultado da soma ponderada passando pela função de ativação
			int saidaFuncaoSinal = funcaoDeAtivacaoBipolar(somatorio);
			System.out.print(saidaFuncaoSinal);
		}
		System.out.println();
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

		entradas = new float[35][6]; 	//0 -> X0;
										//1 -> X1;
										//2 -> X2;
										//3 -> X3;
										//4 -> X4
										//5 -> saida desejada

		//Leitura do arquivo para inserção dos valores no vetor de entradas 'x'
		File f = new File(arquivo);
		Scanner scan = new Scanner(f);

		int line = 0;
		while(scan.hasNextLine()){

			String linha = new String();
			linha = scan.nextLine();
			String[] vetx = linha.split(" ");           

			for(int j = 0; j <= 5; j++){
				entradas[line][j] = Float.parseFloat(vetx[j]);                    
			}
			
			line++;
		}


	}

	public void plotaGrafico() {
		
		XYSeriesCollection dados = new XYSeriesCollection();
		dados.addSeries(dadosEqm);
		
		JFreeChart grafico = ChartFactory.createXYLineChart("Gráfico do Erro Quadrático Médio",
															"Número de épocas",
															"Erro Quadratico Médio",
															dados, PlotOrientation.VERTICAL, true, true, true);
		
		ChartPanel panel = new ChartPanel(grafico);
		JFrame frame = new JFrame();
		frame.setSize(640, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}
	
}
