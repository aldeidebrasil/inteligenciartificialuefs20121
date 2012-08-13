package model;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Fuzzy f = new Fuzzy();
		
		//Temperatura: Cria os conjuntos nebulosos baixo, medio e alto e os adiciona a variável linguistica temperatura
		ConjuntoFuzzy t1 = new ConjuntoFuzzy(800, 1000, 800, 900,"baixa", 800, 1200);
		ConjuntoFuzzy t2 = new ConjuntoFuzzy(900, 1100, "media", 800, 1200);
		ConjuntoFuzzy t3 = new ConjuntoFuzzy(1000, 1200, 1100 ,1200,"alta", 800, 1200);
		
		f.addConjuntoFuzzy(1, t1);
		f.addConjuntoFuzzy(1, t2);
		f.addConjuntoFuzzy(1, t3);
		
		//Volume: Cria os conjuntos nebulosos baixo, medio e alto e os adiciona a variável linguistica volume 
		ConjuntoFuzzy v1 = new ConjuntoFuzzy(2, 7, 2, 4.5 ,"baixo",  2, 12);
		ConjuntoFuzzy v2 = new ConjuntoFuzzy(4.5, 9.5, "medio", 2, 12);
		ConjuntoFuzzy v3 = new ConjuntoFuzzy(7, 12, 9.5, 12,"alto", 2, 12);
		
		f.addConjuntoFuzzy(2, v1);
		f.addConjuntoFuzzy(2, v2);
		f.addConjuntoFuzzy(2, v3);
		
		//Pressão: Cria os conjuntos nebulosos baixo, medio e alto e os adiciona a variável linguistica pressão
		ConjuntoFuzzy p1 = new ConjuntoFuzzy(4, 8, 4, 5,"baixa",  4, 12);
		ConjuntoFuzzy p2 = new ConjuntoFuzzy(6, 10, "media", 4, 12);
		ConjuntoFuzzy p3 = new ConjuntoFuzzy(8, 12, 11, 12,"alta", 4, 12);
		
		f.addConjuntoFuzzy(3, p1);
		f.addConjuntoFuzzy(3, p2);
		f.addConjuntoFuzzy(3, p3);
		
		
		 //Fuzzifica o intervalo total em 500 pontos e armazena o resultado para cada valor		
		f.fuzzifica(1, f.getTemperatura(), 500);
		f.fuzzifica(2, f.getVolume(), 500);
		f.fuzzifica(3, f.getPressao(), 500);
		
		
		
		//f.plotaGrafico();

		double t[] = f.encontraTemperatura(965); 
		double v[] = f.encontraVolume(11);
		
		f.composicaoMaxMin(t, v);
	
		System.out.println("Pressão: " + f.desfuzzificacao());
		
		System.out.println(" =D ");

	}

}
