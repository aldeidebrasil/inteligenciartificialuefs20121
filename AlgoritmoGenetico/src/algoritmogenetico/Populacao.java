package algoritmogenetico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Populacao {

	private ArrayList<Cromossomo> individuos = new ArrayList<Cromossomo>();
	private int tamanhoPopulacao;
	private int tamanhoCromossomo;
    
    public Populacao(int tamPop, int tamCromo){
    	this.tamanhoPopulacao = tamPop;
    	this.tamanhoCromossomo = tamCromo;
    }
    
    public void iniciarPopulacao(){
       
            for(int i = 0; i < tamanhoPopulacao; i++){
                Cromossomo cromossomo = new Cromossomo(tamanhoCromossomo); //substituir pelo tal tamanho 'm'
                cromossomo.setGenesAleatorios();
                individuos.add(cromossomo);
            }
    }
    
    public Cromossomo getCromossomo(int pos){
    	
    	return individuos.get(pos);
    	
    }
    
    public void ordenarPorFitness(){
    	
    	 Collections.sort(individuos, new Comparator<Object>(){
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

	public ArrayList<Cromossomo> getIndividuos() {
		return individuos;
	}

	public void setIndividuos(ArrayList<Cromossomo> individuos) {
		this.individuos = individuos;
	}
    
	
    

}
