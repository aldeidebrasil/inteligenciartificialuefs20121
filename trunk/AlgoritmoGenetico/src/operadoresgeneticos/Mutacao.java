package operadoresgeneticos;

import java.util.ArrayList;
import java.util.Random;

import algoritmogenetico.Cromossomo;

public class Mutacao {

	private float taxaDeMutacao; //taxa de mutação dos cromossomos
    Random rand = new Random();//gera numeros aleatórios

    public void MutacaoUmPonto(ArrayList<Cromossomo> populacao){

        int ponto = rand.nextInt(populacao.size() - 1);
        Cromossomo individuo = populacao.get(ponto);
        int gene = rand.nextInt(individuo.getTamanho() - 1);
        
      //Se o gene for zero, será substituido po um e vice-versa.
        if(rand.nextFloat() < taxaDeMutacao){
            if((Integer)individuo.getGenes().get(gene) == 0)
                individuo.getGenes().set(gene, 1);
            else
                individuo.getGenes().set(gene, 0);
        }
        populacao.set(ponto, individuo);
    }
	
}
