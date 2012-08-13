package algoritmogenetico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Populacao {

	private ArrayList<Cromossomo> individuos;
	private int tamanhoPopulacao;
	private int tamanhoCromossomo;
    
    public Populacao(int tamPop, int tamCromo){
    	this.tamanhoPopulacao = tamPop;
    	this.tamanhoCromossomo = tamCromo;
    	individuos = new ArrayList<Cromossomo>();
    }
    
    /**
     * Gerar numeros binarios aleatorios nos cromossomos
     */
    public void iniciarPopulacao(){
       
            for(int i = 0; i < tamanhoPopulacao; i++){
                Cromossomo cromossomo = new Cromossomo(tamanhoCromossomo); 
                cromossomo.setGenesAleatorios();
                individuos.add(cromossomo);
            }
    }
    
    /**
     * Ordenar arrayList de objetos Cromossos por ordem descendente de fitness
     */
    public void ordenarPorFitness(){
    	
    	 Collections.sort(individuos, new Comparator<Object>(){
             @Override
             public int compare(Object o1, Object o2){
                 Cromossomo c1 = (Cromossomo) o1;
                 Cromossomo c2 = (Cromossomo) o2;
                 if(c1.getFitness() < c2.getFitness())
                     return 1;
                 else
                     if(c1.getFitness() > c2.getFitness())
                         return -1;
                     else
                         return 0;
             }
         });    	 
    }

    public int getTamanhoCromossomo() {
		return tamanhoCromossomo;
	}

	public void setTamanhoCromossomo(int tamanhoCromossomo) {
		this.tamanhoCromossomo = tamanhoCromossomo;
	}

	public Cromossomo getCromossomo(int pos){    	
    	return individuos.get(pos);    	
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
