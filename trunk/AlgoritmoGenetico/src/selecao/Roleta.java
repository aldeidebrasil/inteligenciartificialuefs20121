package selecao;

import java.util.ArrayList;
import java.util.Random;

import algoritmogenetico.Cromossomo;
import algoritmogenetico.Populacao;

public class Roleta {

	private ArrayList<Cromossomo> cromossomosSelecionados;
    private int qtdeSele;
    Random rand = new Random();

    public ArrayList<Cromossomo> selecionarCromossomos(Populacao populacao) {

        cromossomosSelecionados = new ArrayList<Cromossomo>();
        float sum = 0;
        int cont = 0;

        for (int i = 0; i < populacao.getTamanhoPopulacao(); i++) {
            sum += populacao.getCromossomo(i).getFitness();
        }

        while (cont < qtdeSele) {
            int alea = rand.nextInt((int) sum);
            sum = 0;

            for (int i = 0; i < populacao.getTamanhoPopulacao(); i++) {
                sum += populacao.getCromossomo(i).getFitness();
                if (sum >= alea) {
                    cromossomosSelecionados.add(populacao.getCromossomo(i));
                    break;
                }
            }
            cont++;
        }

        return cromossomosSelecionados;
    }

    public void setQtdeSele(int qtdeSele) {
        this.qtdeSele = qtdeSele;
    }
	
}
