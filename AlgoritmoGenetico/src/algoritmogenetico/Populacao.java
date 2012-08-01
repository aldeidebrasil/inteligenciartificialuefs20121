package algoritmogenetico;

import java.util.ArrayList;

public class Populacao {

	ArrayList<Cromossomo> populacao = new ArrayList<Cromossomo>();
	private int tamanhoPopulacao;
    private float desvioPadrao;
    
    public Populacao(){
    	
    }
    
    public void iniciarPopulacao(){
        
       
            for(int i = 0; i < tamanhoPopulacao; i++){
                Cromossomo cromo = new Cromossomo();
                cromo.setGenesAleatorios();
                populacao.add(cromo);
            }
    }
    
    public Cromossomo getCromossomo(int pos){
    	
    	return populacao.get(pos);
    	
    }

}
