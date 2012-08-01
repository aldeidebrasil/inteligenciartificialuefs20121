package algoritmogenetico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Populacao {

	ArrayList<Cromossomo> populacao = new ArrayList<Cromossomo>();
	private int tamanhoPopulacao;
    private float desvioPadrao;
    
    public Populacao(){
    	
    }
    
    public void iniciarPopulacao(){
       
            for(int i = 0; i < tamanhoPopulacao; i++){
                Cromossomo cromossomo = new Cromossomo();
                cromossomo.setGenesAleatorios();
                populacao.add(cromossomo);
            }
    }
    
    public Cromossomo getCromossomo(int pos){
    	
    	return populacao.get(pos);
    	
    }
    
    public void ordenarPorFitness(){
    	
    	 Collections.sort(populacao, new Comparator<Object>(){
             @Override
             public int compare(Object o1, Object o2){
                 Cromossomo c1 = (Cromossomo) o1;
                 Cromossomo c2 = (Cromossomo) o2;
                 if(c1.getFitness() > c2.getFitness())
                     return 1;
                 else
                     if(c1.getFitness() < c2.getFitness())
                         return -1;
                     else
                         return 0;
             }//fim do compare()
         });
    	 
    }

	public int getTamanhoPopulacao() {
		return tamanhoPopulacao;
	}

	public void setTamanhoPopulacao(int tamanhoPopulacao) {
		this.tamanhoPopulacao = tamanhoPopulacao;
	}

	public float getDesvioPadrao() {
		return desvioPadrao;
	}

	public void setDesvioPadrao(float desvioPadrao) {
		this.desvioPadrao = desvioPadrao;
	}
    
    

}
