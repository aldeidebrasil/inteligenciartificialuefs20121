package algoritmogenetico;

import java.util.ArrayList;
import java.util.Random;

public class AlgoritmoGenetico {
	
	private Populacao populacao;
	private float taxaMutacao;
	private float taxaCruzamento;
	private int qtdGeracoes;
	private Random rand;
	
	public AlgoritmoGenetico(int tamPop,int tamCromo, int qtdGer, float txCruz, 
			float txMut){
		
		populacao = new Populacao(tamPop, tamCromo);
		taxaCruzamento = txCruz;
		taxaMutacao = txMut;
		qtdGeracoes = qtdGer;
		rand = new Random();
	}
	
	public void rodar(){
		
		populacao.iniciarPopulacao();
		converterFitnessBinarioParaDecimal();
		calcularFitness();
		//populacao.ordenarPorFitness();
		
		int geracoes = 0;
		while(geracoes < qtdGeracoes){
			
			ArrayList<Cromossomo> cromossomosSelecionados = selecionarCromossomosPorRoleta(
					populacao, populacao.getIndividuos().size());
			ArrayList<Cromossomo> resultCruzamento = cruzamentoUmPonto((ArrayList<Cromossomo>)cromossomosSelecionados.clone());
			ArrayList<Cromossomo> resultMutacao = mutacaoUmPonto((ArrayList<Cromossomo>)cromossomosSelecionados.clone());
			
			populacao.getIndividuos().addAll(resultCruzamento);
			populacao.getIndividuos().addAll(resultMutacao);
			
			converterFitnessBinarioParaDecimal();
			calcularFitness();
			cortar();
			
			geracoes++;
		}
		//System.out.println();
	}
	
	/**
	 * Realiza a muta��o em um determinado ponto aleatorio para uma determinada
	 * taxa de muta��o
	 * @param cromossomosSelecionados
	 * @return
	 */
	 public ArrayList<Cromossomo> mutacaoUmPonto(ArrayList<Cromossomo> cromossomosSelecionados){

		 ArrayList<Cromossomo> resultMutacao = new ArrayList<Cromossomo>();
		 
		 for(int i = 0; i < cromossomosSelecionados.size(); i++){
			 int ponto = rand.nextInt(cromossomosSelecionados.size() - 1);
			 Cromossomo individuo = cromossomosSelecionados.get(ponto);
			 int gene = rand.nextInt(individuo.getTamanho() - 1);
	        
			 //Se o gene for 0, ser� substituido por 1 e vice-versa.
			 if(rand.nextFloat() < taxaMutacao){
				 if(individuo.getGenes().get(gene) == 0)
					 individuo.getGenes().set(gene, 1);
				 else
					 individuo.getGenes().set(gene, 0);
			 }
			 	resultMutacao.add(individuo);
		 }
		 return resultMutacao;
	 }

	 /**
	  * Realiza o cruzamento em um determinado ponto aleatorio para uma determinada
	  * taxa de cruzamento
	  * @param cromossomosSelecionados
	  * @return
	  */
	 public ArrayList<Cromossomo> cruzamentoUmPonto(ArrayList<Cromossomo> cromossomosSelecionados) {
	        
	        ArrayList<Cromossomo> filhos = new ArrayList<Cromossomo>();
	        int qtdCruzamentos = cromossomosSelecionados.size();
	        int ponto;
	        
	        int cont = 0;
	        while (cont < qtdCruzamentos) {
	            Cromossomo pai = cromossomosSelecionados.get(rand.nextInt(cromossomosSelecionados.size() - 1));
	            Cromossomo mae = cromossomosSelecionados.get(rand.nextInt(cromossomosSelecionados.size() - 1));

	            Cromossomo filho1 = new Cromossomo(pai.getGenes().size());
	            Cromossomo filho2 = new Cromossomo(mae.getGenes().size());

	            ponto = rand.nextInt(pai.getTamanho()-1);
	            if(rand.nextFloat() < taxaCruzamento){
	            	for (int i = 0; i < pai.getGenes().size(); i++) {
	            		if (i < ponto) {
	            			filho1.getGenes().add(i, pai.getGenes().get(i));
	            			filho2.getGenes().add(i, mae.getGenes().get(i));
	            		} else {
	            			filho1.getGenes().add(i,mae.getGenes().get(i));
	            			filho2.getGenes().add(i,pai.getGenes().get(i));
	            		}
	            	}

	            	filhos.add(filho1);
	            	filhos.add(filho2);
	            }
	            cont++; //verificar se o cont deve ser incrementado somente
	            		//quando cruzar ou n�o
	        }
	        return filhos;

	    }
	 
	 /**
	  * Selecionar Cromossomos(individuos) para o possivel cruzamento ou muta��o atrav�s do m�todo da roleta
	  * @param populacao
	  * @param qtdSele: tamanho da populacao
	  * @return
	  */
	 public ArrayList<Cromossomo> selecionarCromossomosPorRoleta(Populacao populacao, int qtdSele) {

	        ArrayList<Cromossomo> cromossomosSelecionados = new ArrayList<Cromossomo>();
	        double sum = 0;
	        int cont = 0;

	        for (int i = 0; i < populacao.getTamanhoPopulacao(); i++) {
	            sum += populacao.getCromossomo(i).getValorCromossomo();
	        }

	        while (cont < qtdSele) {
	            double numAleatorio = rand.nextDouble();

	            for (int i = 0; i < populacao.getTamanhoPopulacao(); i++) {
	                //sum += populacao.getCromossomo(i).getFitness();
	                if (sum >= numAleatorio) {
	                    cromossomosSelecionados.add(populacao.getCromossomo(i));
	                    break;
	                }
	            }
	            cont++;
	        }

	        return cromossomosSelecionados;
	    }
	 
	 /**
	  * Calcular fitness de cada cromossomo(indiv�duo)
	  */
	 public void calcularFitness(){
		 
		 for (int i = 0; i < populacao.getIndividuos().size(); i++) {
			populacao.getCromossomo(i).calcularFitness();
		}
		 
	 }
	 
	 /**
	  * Executar esse m�todo toda vez que altera��es forem realizadas nos indiv�duos
	  * atr�ves de muta��o e cruzamento
	  */
	 public void converterFitnessBinarioParaDecimal(){
		 for (int i = 0; i < populacao.getIndividuos().size(); i++) {
			populacao.getCromossomo(i).converterFitnessBinarioParaDecimal();
		}
	 }
	 
	 /**
	  * Retirar os individuos com os menores valores de fitness
	  */
	 public void cortar(){
		 populacao.ordenarPorFitness();
		 
		 for (int i = populacao.getIndividuos().size() - 1; i >= populacao.getTamanhoPopulacao(); i--) {
			populacao.getIndividuos().remove(i);
		}
	 }
	 
	 /**
	  * Retorna o indiv�duo com melhor fitness, ou seja, a melhor solucao 
	  * encontrada para uma execu��o do algoritmo gen�tico
	  * @return
	  */
	 public Cromossomo getMelhorResultado(){
		 
		 populacao.ordenarPorFitness();
		 
		 return populacao.getCromossomo(0);
		 
	 }

	public Populacao getPopulacao() {
		return populacao;
	}

	public void setPopulacao(Populacao populacao) {
		this.populacao = populacao;
	}

	public float getTaxaMutacao() {
		return taxaMutacao;
	}

	public void setTaxaMutacao(float taxaMutacao) {
		this.taxaMutacao = taxaMutacao;
	}

	public float getTaxaCruzamento() {
		return taxaCruzamento;
	}

	public void setTaxaCruzamento(float taxaCruzamento) {
		this.taxaCruzamento = taxaCruzamento;
	}

	public int getQtdGeracoes() {
		return qtdGeracoes;
	}

	public void setQtdGeracoes(int qtdGeracoes) {
		this.qtdGeracoes = qtdGeracoes;
	}

	 
}
