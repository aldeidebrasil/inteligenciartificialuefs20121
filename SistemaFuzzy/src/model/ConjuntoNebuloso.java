package model;

public class ConjuntoNebuloso {

	
	private double inicio;
	private double meioInicio; //Ponto do meio na triangular e primeiro ponto com grau igual a 1 na trapezoide
	private double meioFim; //Utilizar com a funcao trapezoide marcando o ultimo ponto com grau igual a 1
	private double fim;
	private FuncaoPertinencia fp;
	private int qtdDePontos; //quantidade de pontos no conjunto nebuloso que irá compor à quantidade de pontos totais
							 //no universo de discurso da variavel de entrada que está associada a esse conjunto nebuloso
	
	/**
	 * Construtor para preparar o calcula da funcao triangular
	 * @param inicio
	 * @param meioInicio
	 * @param fim
	 * @param qtdPontos
	 * @param fp
	 */
	public ConjuntoNebuloso(double inicio, double meioInicio, double fim, int qtdPontos, FuncaoPertinencia fp){
		
		this.inicio = inicio;
		this.meioInicio = meioInicio;
		this.fim = fim;
		this.qtdDePontos = qtdPontos;
		this.fp = fp;
		
	}
	
	/**
	 * Construtor para preparar o calculo da funcao trapezoide
	 * @param inicio
	 * @param meioInicio
	 * @param meioFim
	 * @param fim
	 * @param qtdPontos
	 * @param fp
	 */
	public ConjuntoNebuloso(double inicio, double meioInicio, double meioFim, double fim, int qtdPontos, FuncaoPertinencia fp){
		this(inicio, meioInicio, fim, qtdPontos, fp);
		this.meioFim = meioFim;
	}
	
	/**
	 * Metodo para calcular o grau de pertinencia dos pontos do conjunto nebuloso
	 * @param entrada
	 * @return
	 */
	public double calculaGrauPertinencia(double entrada){
		
		if(entrada < inicio || entrada > fim){
			return 0;
			
		}else if(entrada == meioInicio){
			return meioInicio;
			
		} else if(entrada < meioInicio){
			//verificar tipo de funcao de pertinencia
			if(fp == FuncaoPertinencia.TRIANGULAR)
				return funcaoTriangular(entrada, true);
			else //trapezoide
				return funcaoTrapezoide(entrada, true);
		} else{
			
			if(fp == FuncaoPertinencia.TRIANGULAR)
				return funcaoTriangular(entrada, false);
			else { //trapezoide
				return funcaoTrapezoide(entrada, false);
			}
		}
	}
	/**
	 * Calcula a funcao trapezoide de geracao de grau de pertinencia
	 * @param entrada
	 * @param antesMeio
	 * @return
	 */
	public double funcaoTrapezoide(double entrada, boolean antesMeio){
		
		if(antesMeio){
			return (entrada - inicio) / (meioInicio - inicio); 
		} else {
			if(entrada > meioInicio && entrada < meioFim){
				return 1;
			}else{
				return (fim - entrada) / (fim - meioFim);
			}
		}
		
	}
	
	/**
	 * Calcula a funcao triangular de geracao do grau de pertinencia
	 * @param entrada
	 * @param antesMeio
	 * @return
	 */
	public double funcaoTriangular(double entrada, boolean antesMeio){
		
		if(antesMeio){
			return (entrada - inicio) / (meioInicio - inicio);
		} else {
			return (fim - entrada) / (fim - meioInicio);
		}
	}
	
	public double getInicio() {
		return inicio;
	}

	public void setInicio(double inicio) {
		this.inicio = inicio;
	}

	public double getMeioInicio() {
		return meioInicio;
	}

	public void setMeioInicio(double meio) {
		this.meioInicio = meio;
	}

	public double getMeioFim() {
		return meioFim;
	}

	public void setMeioFim(double meioFim) {
		this.meioFim = meioFim;
	}

	public double getFim() {
		return fim;
	}

	public void setFim(double fim) {
		this.fim = fim;
	}

	public FuncaoPertinencia getFp() {
		return fp;
	}

	public void setFp(FuncaoPertinencia fp) {
		this.fp = fp;
	}

	public int getQtdDePontos() {
		return qtdDePontos;
	}

	public void setQtdDePontos(int qtdDePontos) {
		this.qtdDePontos = qtdDePontos;
	}
	
	
	
}

