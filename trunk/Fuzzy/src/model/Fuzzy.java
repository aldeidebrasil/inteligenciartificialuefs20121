package model;

import java.util.ArrayList;
import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * 
 * 
 * @author andre
 *
 */
public class Fuzzy {

	public XYSeries pontos;
	XYSeriesCollection dados = new XYSeriesCollection();

	private ArrayList<ConjuntoFuzzy> temperatura;
	private ArrayList<ConjuntoFuzzy> volume;
	private ArrayList<ConjuntoFuzzy> pressao;


	private double entrada[];
	private double entradaTemp[];
	private double entradaVol[];
	private double entradaPressao[];
	private double grauPertinenciaTemp[][];
	private double grauPertinenciaVol [][];
	private double grauPertinenciaPressao[][];
	private double implicacaoPressao[][];
	private double agregacaoPressao[];


	public Fuzzy(){

		temperatura = new ArrayList<ConjuntoFuzzy>();
		volume = new ArrayList<ConjuntoFuzzy>();
		pressao = new ArrayList<ConjuntoFuzzy>();
		pontos = new XYSeries("Agregação");

	}

	/**
	 * Adiciona um conjunto fuzzy a um array de conjuntos fuzzy específico. 
	 * 
	 * @param seleciona seleciona qual array vai receber o novo conjunto
	 * @param conjunto novo conjunto fuzzy
	 */
	public void addConjuntoFuzzy(int seleciona, ConjuntoFuzzy conjunto){

		if(seleciona == 1)
			temperatura.add(conjunto);
		else if(seleciona == 2)
			volume.add(conjunto);
		else if(seleciona == 3)
			pressao.add(conjunto);

	}


	/**
	 * Realiza a discretização do intervalo abrangido por um conjunto nebulo com base no numero de pontos passados como parametros 
	 * 
	 * @param variavel Indica qual a variável linguistica deve seu intervalo discretizado. 
	 * Este valor é importante para o armarzenamento correto dos valores discretizados.
	 * 
	 * @param cj Conjunto nebuloso pertencente a uma variável linguistica
	 * @param numeroDePontos Quantidade de pontos do universo de discurso
	 */
	public void discretizaIntervalo(int variavel, ArrayList<ConjuntoFuzzy> cj, int numeroDePontos){
		int k =0;
		entrada = new double[numeroDePontos];

		// Este laço encontra os n valores do universo de discurso que são diferentes de zero 

		//double inicio = cj.get(k).getMin();
		double passo = (cj.get(k).getFinalIntervalo()-cj.get(k).getInicioIntervalo())/numeroDePontos;
		double aux = cj.get(k).getInicioIntervalo();

		for(int i=0; i < numeroDePontos; i++){

			entrada[i] = aux;
			aux += passo;
			//pontos.add(i, aux);
		}

		if(variavel==1)
			entradaTemp = entrada; 
		else if(variavel ==2)
			entradaVol = entrada;
		else if(variavel ==3)
			entradaPressao = entrada;

	}



	/**
	 * Calcula os graus de pertinencia de todos os conjuntos nebulosos de uma variável linguistica.
	 * 
	 * @param variavel Indica para qual variável linguistica deve ser calculado os graus de pertinencia.
	 * Este valor é importante para o armarzenamento correto dos graus de pertinencia calculados.
	 * 
	 * @param cj Conjunto nebuloso pertencente a uma variável linguistica.
	 * @param numeroDePontos Quantidade de pontos do universo de discurso.
	 */
	public void calculaGrauPertinencia(int variavel, ArrayList<ConjuntoFuzzy> cj, int numeroDePontos){
		int k =0;
		double grausDePertinencia[][] = new double[cj.size()][numeroDePontos];

		while(k < cj.size()){

			//double inicio = cj.get(k).getMin();
			//double fim = cj.get(k).getMax();
			int metodo = cj.get(k).getPertinencia();
			//double passo = (cj.get(k).getFinalIntervalo()-cj.get(k).getInicioIntervalo())/numeroDePontos;
			//double aux = cj.get(k).getInicioIntervalo();

			// Este laço calcula o grau de pertinencia de cada valor encontrado no laço acima com os universos de discurso.
			for(int i=0; i < numeroDePontos; i++){

				if(metodo == 1){
					grausDePertinencia[k][i] = cj.get(k).calculaPertinencia(entrada[i]);
					//pontos.add(entrada[i],grausDePertinencia[k][i]);
				}

				else					
					grausDePertinencia[k][i] = cj.get(k).calculaPertinencia(entrada[i],cj.get(k).getBaseMenor(), cj.get(k).getBaseMaior());
				//pontos.add(entrada[i],grausDePertinencia[k][i]);
			}
			k++;
			//dados.addSeries(pontos);
		}

		if(variavel==1)
			grauPertinenciaTemp = grausDePertinencia;
		else if(variavel ==2)
			grauPertinenciaVol = grausDePertinencia;
		else if(variavel ==3)
			grauPertinenciaPressao = grausDePertinencia;

	}

	/**
	 * Fuzzyfica todos os conjuntos nebulosos de uma variável linguistica.
	 * 
	 * @param variavel Indica qual a variável linguistica deve ser fuzzyficada. 
	 * Este valor é importante para o armarzenamento correto dos valores discretizados e dos graus de pertinencia calculados.
	 * 
	 * @param cj Conjunto nebuloso pertencente a uma variável linguistica
	 * @param numeroDePontos Quantidade de pontos do universo de discurso
	 * @return
	 */
	public void fuzzifica(int variavel, ArrayList<ConjuntoFuzzy> cj, int numeroDePontos){

		//double grausDePertinencia[][] = new double[cj.size()][numeroDePontos];

		discretizaIntervalo(variavel, cj, numeroDePontos);
		calculaGrauPertinencia(variavel, cj, numeroDePontos);

	}

	/**
	 * Realiza a composição das regras ativadas utilizando operador Max-Min e  
	 * tomando como base os graus de perticencia da temperatura e da pressao 
	 * passados como parametros.
	 * 
	 * @param temp Graus de pertinencia da temperatura: [0] baixo; [1] media; [2] alta 
	 * @param pressao Graus de pertinencia do volume: [0] baixo; [1] medio; [2] alto
	 */
	public void composicaoMaxMin(double[] temp, double[] vol){

		double r1 = 0, r2 = 0, r3 = 0, r4 = 0, r5 = 0, r6 = 0, r7 = 0, r8 = 0, r9 = 0;
		double pres_baixa, pres_media, pres_alta;
		boolean[] ativouRegra = new boolean[9];
		implicacaoPressao = new double[9][entradaVol.length];

		//R1: SE temp baixa & vol peq ENTAO pres baixa
		if(temp[0] > 0 && vol[0] > 0){ 
			r1 = Math.min(temp[0], vol[0]);
			ativouRegra[0] = true;
			for(int  i=0; i < implicacaoPressao[0].length; i++){
				implicacaoPressao[0][i] = Math.min(r1, grauPertinenciaPressao[0][i]);
			}
		}

		// R2: SE temp media & vol peq ENTAO: pres baixa
		if(temp[1] > 0 && vol[0] > 0){
			r2 = Math.min(temp[1], vol[0]);
			ativouRegra[1] = true;
			for(int  i=0; i < implicacaoPressao[0].length; i++){
				implicacaoPressao[1][i] = Math.min(r2, grauPertinenciaPressao[0][i]);
			}
		}

		//R3 SE temp alta & vol peq ENTAO: pres media
		if(temp[2] > 0 && vol[0] > 0){
			r3 = Math.min(temp[2], vol[0]);
			ativouRegra[2] = true;
			for(int  i=0; i < implicacaoPressao[0].length; i++){
				implicacaoPressao[2][i] = Math.min(r3, grauPertinenciaPressao[1][i]);
			}
		}

		//R4 SE temp baixa & vol medio ENTAO: pres baixa
		if(temp[0] > 0 && vol[1] > 0){
			r4 = Math.min(temp[0], vol[1]);
			ativouRegra[3] = true;
			for(int  i=0; i < implicacaoPressao[0].length; i++){
				implicacaoPressao[3][i] = Math.min(r3, grauPertinenciaPressao[0][i]);
			}
		}

		//R5 SE temp media & vol medio ENTAO: pres media
		if(temp[1] > 0 && vol[1] > 0){
			r5 = Math.min(temp[1], vol[1]);
			ativouRegra[4] = true;
			for(int  i=0; i < implicacaoPressao[0].length; i++){
				implicacaoPressao[4][i] = Math.min(r4, grauPertinenciaPressao[1][i]);
			}
		}

		//R6 SE temp alta & vol medio ENTAO: pres alta
		if(temp[2] > 0 && vol[1] > 0){
			r6 = Math.min(temp[2], vol[1]);
			ativouRegra[5] = true;
			for(int  i=0; i < implicacaoPressao[0].length; i++){
				implicacaoPressao[5][i] = Math.min(r6, grauPertinenciaPressao[2][i]);
			}
		}

		//R7 SE temp baixa & vol grande ENTAO: pres media
		if(temp[0] > 0 && vol[2] > 0){
			r7 = Math.min(temp[0], vol[2]);
			ativouRegra[6] = true;
			for(int  i=0; i < implicacaoPressao[0].length; i++){
				implicacaoPressao[6][i] = Math.min(r7, grauPertinenciaPressao[1][i]);
			}
		}

		//R8 SE temp media & vol grande ENTAO: pres alta
		if(temp[1] > 0 && vol[2] > 0){
			r8 = Math.min(temp[1], vol[2]);
			ativouRegra[7] = true;
			for(int  i=0; i < implicacaoPressao[0].length; i++){
				implicacaoPressao[7][i] = Math.min(r8, grauPertinenciaPressao[2][i]);
			}
		}

		//R9 SE temp alta & vol grande ENTAO: pres alta
		if(temp[2] > 0 && vol[2] > 0){
			r9 = Math.min(temp[2], vol[2]);
			ativouRegra[8] = true;
			for(int  i=0; i < implicacaoPressao[0].length; i++){
				implicacaoPressao[8][i] = Math.min(r9, grauPertinenciaPressao[2][i]);
			}
		}

		/*pres_baixa = Math.max(r1, Math.max(r2, r3));
		pres_media = Math.max(r4, Math.max(r5, r6));
		pres_alta = Math.max(r7, Math.max(r8, r9));*/

		//implicacaoMadani(pres_baixa, pres_media, pres_alta);
		agregacaoMax(implicacaoPressao,ativouRegra);
	}

//	/**
//	 * Realiza a implicação do tipo mandani
//	 * 
//	 * @param pb pressao baixa após composicao Max-Min
//	 * @param pm pressao media após composicao Max-Min
//	 * @param pa pressao após composicao Max-Min
//	 * 
//	 * @return
//	 */
//	public void implicacaoMadani(double pb, double pm, double pa){
//
//		
//
//		// Aplicação da implicação Mandani para a pressão baixa
//		for(int  i=0; i < implicacaoPressao[0].length; i++){
//			implicacaoPressao[0][i] = Math.min(pb, grauPertinenciaPressao[0][i]);
//		}
//
//		// Aplicaçãoo da implicação Mandani para pressão media
//		for(int  i=0; i < implicacaoPressao[0].length; i++){
//			implicacaoPressao[1][i] = Math.min(pm, grauPertinenciaPressao[1][i]);
//		}
//
//		// Aplicação da implicaçao Mandani para pressão alta
//		for(int  i=0; i < implicacaoPressao[0].length; i++){
//			implicacaoPressao[2][i] = Math.min(pa, grauPertinenciaPressao[2][i]);
//		}
//
//		agregacaoMax(implicacaoPressao);
//	}

	/**
	 * Aplica o operador de agragação tipo máximo para agregar os os conjuntos nebulosos de pressao.
	 * 
	 * @param impPres
	 */
	public void agregacaoMax(double impPres[][], boolean[] ativouRegra){
		agregacaoPressao = new double[impPres[0].length];
		double[] maximosAtivados = new double[500];
		double m01,m23,m45,m67,m8;
		double m0123, m4567;
		double m01234567;
		
		for(int i = 0; i < impPres[0].length; i++ ){
			
			for (int j = 0; j < 9; j++) {
				if (ativouRegra[j]) {
					maximosAtivados[j] = impPres[j][i];
				}
				
				m01 = Math.max(maximosAtivados[0], maximosAtivados[1]);
				m23 = Math.max(maximosAtivados[2], maximosAtivados[3]);
				m45 = Math.max(maximosAtivados[4], maximosAtivados[5]);
				m67 = Math.max(maximosAtivados[6], maximosAtivados[7]);
				m0123 = Math.max(m01, m23);
				m4567 = Math.max(m45, m67);
				m01234567 = Math.max(m0123, m4567);
				m8 = Math.max(maximosAtivados[8], m01234567);
				
				agregacaoPressao[i] = m8;
			}
			
			
			
			pontos.add(entradaPressao[i],agregacaoPressao[i]);
		}
		dados.addSeries(pontos);
		plotaGrafico();
	}


	public double desfuzzificacao(){



		/*
		 * encontrando os limites da agregação: 
		 * 
		 * esq = primeiro valor cuja pertinencia é não nula
		 * dir = ultimo valor cuja pertinencia é não nula 
		 * 
		 * */

		int esq, dir;

		int k =0;

		while(agregacaoPressao[k]==0 && k < agregacaoPressao.length-1)
			k++;

		esq = k;

		while(agregacaoPressao[k]!=0 && k < agregacaoPressao.length-1)
			k++;

		dir = k;

		// Cálculo do centro de massa

		double somaPesoPonderado=0;
		double somaPeso =0;

		for(int i = esq; i <= dir; i++){
			somaPesoPonderado += entradaPressao[i]*agregacaoPressao[i];
			somaPeso += agregacaoPressao[i];
		}

		return (somaPesoPonderado/somaPeso);

	}

	public ArrayList<ConjuntoFuzzy> getTemperatura(){
		return temperatura;
	}



	public ArrayList<ConjuntoFuzzy> getVolume() {
		return volume;
	}

	public ArrayList<ConjuntoFuzzy> getPressao(){
		return pressao;
	}



	/**
	 * Método para analisar de forma mais visual o resultado do sistema fuzzy
	 */
	public void plotaGrafico() {

		//XYSeriesCollection dados = new XYSeriesCollection();
		//dados.addSeries(pontos);

		JFreeChart grafico = ChartFactory.createXYLineChart("",
				"",
				"",
				dados, PlotOrientation.VERTICAL, true, true, true);

		ChartPanel panel = new ChartPanel(grafico);
		JFrame frame = new JFrame();
		frame.setSize(640, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}

	/**
	 * Localiza um valor de pertiencia previamente calculado na fuzzyficação
	 * 
	 * @param valor
	 * @return
	 */
	public double[] encontraTemperatura(double valor){

		int k=0;
		double temps[] = new double[temperatura.size()];

		if(valor <= 800){
			k=0;
		}
		else if(valor >= 1200){
			k=499;
		} else if(valor > 800 && valor < 1200){

			while (entradaTemp[k]< valor){
				k++;
			}}

		for(int i=0; i<temps.length; i++)
			temps[i] = grauPertinenciaTemp[i][k];

		return temps;

	}


	/**
	 * Localiza um valor de pertiencia previamente calculado na fuzzyficação
	 * 
	 * @param valor
	 * @return
	 */
	public double[] encontraVolume(double valor){

		int k=0;
		double vol[] = new double[volume.size()];

		if(valor <= 2){
			k=0;
		}
		else if(valor >= 12){
			k=499;
		} else if(valor > 2 && valor < 12){
			while (entradaVol[k]< valor){
				k++;
			}
		}

		for(int i =0; i<vol.length; i++)
			vol[i] = grauPertinenciaVol[i][k];

		return vol;

	}


	/**
	 * Localiza um valor de pertiencia previamente calculado na fuzzyficação
	 * 
	 * @param valor
	 * @return
	 */
	public double[] encontraPressao(double valor){

		int k=0;
		double pres[] = new double[pressao.size()];

		while (entradaPressao[k]< valor){
			k++;
		}

		for(int i =0; i<pres.length; i++)
			pres[i] = grauPertinenciaPressao[i][k];

		return pres;

	}

	public double[][] getGrauPertinenciaTemp() {
		return grauPertinenciaTemp;
	}

	public void setGrauPertinenciaTemp(double[][] grauPertinenciaTemp) {
		this.grauPertinenciaTemp = grauPertinenciaTemp;
	}

	public double[][] getGrauPertinenciaVol() {
		return grauPertinenciaVol;
	}

	public void setGrauPertinenciaVol(double[][] grauPertinenciaVol) {
		this.grauPertinenciaVol = grauPertinenciaVol;
	}

	public double[][] getGrauPertinenciaPressao() {
		return grauPertinenciaPressao;
	}

	public void setGrauPertinenciaPressao(double[][] grauPertinenciaPressao) {
		this.grauPertinenciaPressao = grauPertinenciaPressao;
	}



}
