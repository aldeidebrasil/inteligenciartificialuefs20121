package fuzzy;

import model.ConjuntoNebuloso;
import model.FuncaoPertinencia;
import model.VariavelEntrada;

public class SistemaNebuloso {

	private VariavelEntrada temperatura;
	private VariavelEntrada volume;
	private VariavelEntrada pressao;

	/**
	 * Configurar o sistema nebuloso
	 */
	public SistemaNebuloso(){
		configurarConjuntosNebulosos();
	}
	
	/**
	 * Configura os conjuntos nebulosos para os valores dados no problema
	 */
	public void configurarConjuntosNebulosos(){
		
		temperatura = new VariavelEntrada(500, 0.8);
		ConjuntoNebuloso baixa = new ConjuntoNebuloso(800, 800, 900, 1000, 250, FuncaoPertinencia.TRAPEZOIDE);
		ConjuntoNebuloso media = new ConjuntoNebuloso(900, 1000, 1100, 250, FuncaoPertinencia.TRIANGULAR);
		ConjuntoNebuloso alta = new ConjuntoNebuloso(1000, 1100, 1100, 1200, 250, FuncaoPertinencia.TRAPEZOIDE);

		temperatura.getConjunto().add(baixa);
		temperatura.getConjunto().add(media);
		temperatura.getConjunto().add(alta);

		//temperatura.discretizarPontos();
		double[] resultados = new double[temperatura.getConjunto().size()];
		resultados = temperatura.calcularGrauPertinenciaNosConjuntos(935);
//
		System.out.println("--> Temperatura");
		System.out.println("Baixa: " + resultados[0]);
		System.out.println("Media: " + resultados[1]);
		System.out.println("Alta: " + resultados[2]);

		//----------CONFIGURANDO O VOLUME---------------
		volume = new VariavelEntrada(500, 0.02);
		baixa = new ConjuntoNebuloso(2, 2, 4.5, 7, 250, FuncaoPertinencia.TRAPEZOIDE);
		media = new ConjuntoNebuloso(4.5, 7, 9.5, 250, FuncaoPertinencia.TRIANGULAR);
		alta = new ConjuntoNebuloso(7, 9.5, 12, 12, 250, FuncaoPertinencia.TRAPEZOIDE);

		volume.getConjunto().add(baixa);
		volume.getConjunto().add(media);
		volume.getConjunto().add(alta);

		volume.discretizarPontos();
 		resultados = volume.calcularGrauPertinenciaNosConjuntos(8.75);

		System.out.println("--> Volume");
		System.out.println("Baixo: " + resultados[0]);
		System.out.println("Medio: " + resultados[1]);
		System.out.println("Alto: " + resultados[2]);

		//---------CONFIGURANDO A PRESSAO--------------
		pressao = new VariavelEntrada(500, 0.016);
		baixa = new ConjuntoNebuloso(4, 4, 5, 8, 250, FuncaoPertinencia.TRAPEZOIDE);
		media = new ConjuntoNebuloso(6, 8, 10, 250, FuncaoPertinencia.TRIANGULAR);
		alta = new ConjuntoNebuloso(8, 11, 12, 12, 250, FuncaoPertinencia.TRAPEZOIDE);

		pressao.getConjunto().add(baixa);
		pressao.getConjunto().add(media);
		pressao.getConjunto().add(alta);

		pressao.discretizarPontos();
		resultados = pressao.calcularGrauPertinenciaNosConjuntos(5);
		
		System.out.println("--> Pressão");
		System.out.println("Baixa: " + resultados[0]);
		System.out.println("Media: " + resultados[1]);
		System.out.println("Alta: " + resultados[2]);
		
	}
	
	public static void main(String args[]){
		
		SistemaNebuloso sn = new SistemaNebuloso();
		
		
	}
	
	
	
}
