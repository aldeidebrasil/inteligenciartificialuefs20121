package algoritmogenetico;

import java.util.ArrayList;
import java.util.Random;

public class Cromossomo {

	private ArrayList<Integer> genes;
	private int tamanho;
	private Random r;
	
	public Cromossomo(){
		genes = new ArrayList<Integer>();
		r = new Random();
	}
	
	public void setGenesAleatorios(){
		for (int i = 0; i < genes.size(); i++) {
			genes.add(r.nextInt(2));
		}
	}
	

}
