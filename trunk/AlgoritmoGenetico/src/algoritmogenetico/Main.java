package algoritmogenetico;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		AlgoritmoGenetico ag = new AlgoritmoGenetico(100, 22, 200, 0.7f, 0.01f);
		ag.rodar();
		
		System.out.println("Melhor resultado obtido foi: " + ag.getMelhorResultado().getValorCromossomo());
		System.out.println("Fitness: " + ag.getMelhorResultado().getFitness());

	}

}
