package operadoresgeneticos;

import java.util.ArrayList;
import java.util.Random;

import algoritmogenetico.Cromossomo;

public class CruzamentoUmPonto {

	 private ArrayList<Cromossomo> cromossomosSelecionados;
	 private int qtdCruzamentos;
	 private int ponto;
	 private Random rand = new Random();
	 
	 public CruzamentoUmPonto(int qtdCruz){
		 qtdCruzamentos = qtdCruz;
	 }

	    public ArrayList<Cromossomo> cruzar() {
	        
	        ArrayList<Cromossomo> filhos = new ArrayList<Cromossomo>();

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

		public ArrayList<Cromossomo> getCromossomosSelecionados() {
			return cromossomosSelecionados;
		}

		public void setCromossomosSelecionados(
				ArrayList<Cromossomo> cromossomosSelecionados) {
			this.cromossomosSelecionados = cromossomosSelecionados;
		}

		public int getQtdCruzamentos() {
			return qtdCruzamentos;
		}

		public void setQtdCruzamentos(int qtdeCruzamentos) {
			this.qtdCruzamentos = qtdeCruzamentos;
		}


	    
	
}
