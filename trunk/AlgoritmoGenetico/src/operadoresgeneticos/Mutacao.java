package operadoresgeneticos;

import java.util.ArrayList;
import java.util.Random;

import algoritmogenetico.Cromossomo;

public class Mutacao {

	private float taxaDeMutacao; //taxa de muta��o dos cromossomos
    Random rand = new Random();//gera numeros aleat�rios

    /**
     * Faz a muta��o de um cromossomo em apenas um ponto do conjunto de genes.
     * @param populacao
     * Recebe um ArrayList com todos os individuos da popula��o
     */
    public void MutacaoUmPonto(ArrayList<Cromossomo> populacao){

        int ponto = rand.nextInt(populacao.size() - 1);
        Cromossomo individuo = populacao.get(ponto);
        int gene = rand.nextInt(individuo.getTamanho() - 1);
        
      //Se o gene for zero, ser� substituido po um e vice-versa.
        if(rand.nextFloat() < taxaDeMutacao){
            if((Integer)individuo.getGenes().get(gene) == 0)
                individuo.getGenes().set(gene, 1);
            else
                individuo.getGenes().set(gene, 0);
        }//fim do if
        populacao.set(ponto, individuo);
    }//fim do m�todo OnePointMutation
	
}
