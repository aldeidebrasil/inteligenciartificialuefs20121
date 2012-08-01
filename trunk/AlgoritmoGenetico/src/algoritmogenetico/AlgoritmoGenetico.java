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
		
		int i = 0;
		while(i < qtdGeracoes){
			
		}
		
	}
	
	 public void MutacaoUmPonto(){

	        int ponto = rand.nextInt(populacao.getIndividuos().size() - 1);
	        Cromossomo individuo = populacao.getIndividuos().get(ponto);
	        int gene = rand.nextInt(individuo.getTamanho() - 1);
	        
	      //Se o gene for zero, será substituido po um e vice-versa.
	        if(rand.nextFloat() < taxaMutacao){
	            if(individuo.getGenes().get(gene) == 0)
	                individuo.getGenes().set(gene, 1);
	            else
	                individuo.getGenes().set(gene, 0);
	        }
	        populacao.getIndividuos().set(ponto, individuo);
	    }

	 public ArrayList<Cromossomo> cruzar(ArrayList<Cromossomo> cromossomosSelecionados) {
	        
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
	            cont++;
	        }
	        return filhos;

	    }
	 
}
