package selecao;

import java.util.ArrayList;

import algoritmogenetico.Cromossomo;
import algoritmogenetico.Populacao;

public class Elitismo {

	private float porcentagem;
    private ArrayList<Cromossomo> cromossomosSelecionados;

    public ArrayList<Cromossomo> selecionarCromossomos(Populacao pop) {

        cromossomosSelecionados = new ArrayList<Cromossomo>();
        porcentagem = Math.round((porcentagem / 100) * pop.getTamanhoPopulacao());

        pop.ordenarPorFitness(); //ordenamento da população baseado no valor de fitness
        
        for (int i = 0; i < porcentagem; i++) {
            cromossomosSelecionados.add(pop.getCromossomo(i));
        }

        return cromossomosSelecionados;
    }

    public void setPorcentagem(float porcentagem) {
        this.porcentagem = porcentagem;
    }
	
    
    
}
