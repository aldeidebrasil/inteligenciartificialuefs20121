package fuzzy;

import model.ConjuntoNebuloso;
import model.FuncaoPertinencia;
import model.VariavelEntrada;

public class SistemaFuzzy {

	public static void main(String args[]){
		VariavelEntrada temperatura = new VariavelEntrada(500);
		ConjuntoNebuloso baixa = new ConjuntoNebuloso(800, 800, 900, 1000, 250, FuncaoPertinencia.TRAPEZOIDE);
		ConjuntoNebuloso media = new ConjuntoNebuloso(900, 1000, 1100, 250, FuncaoPertinencia.TRIANGULAR);
		ConjuntoNebuloso alta = new ConjuntoNebuloso(1000, 1100, 1100, 1200, 250, FuncaoPertinencia.TRAPEZOIDE);
		
		temperatura.getConjunto().add(baixa);
		temperatura.getConjunto().add(media);
		temperatura.getConjunto().add(alta);
		
		double[] resultados = new double[temperatura.getConjunto().size()];
		resultados = temperatura.calcularGrauPertinenciaDosConjuntos(950);
		System.out.println(resultados[0]);
		System.out.println(resultados[1]);
		System.out.println(resultados[2]);
		//temperatura.getConjunto().add(new ConjuntoNebuloso(, meio, fim, qtdPontos, fp))
	}
	
}
