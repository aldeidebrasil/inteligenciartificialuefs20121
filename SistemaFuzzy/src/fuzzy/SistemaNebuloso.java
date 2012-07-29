package fuzzy;

import java.util.Scanner;

import model.ConjuntoNebuloso;
import model.FuncaoPertinencia;
import model.VariavelEntrada;

public class SistemaNebuloso {

	private VariavelEntrada temperatura;
	private VariavelEntrada volume;
	private VariavelEntrada pressao;
	private double[][] resultadoFuzzificacao;
	private double[][] matrizAtivacao;

	/**
	 * Configurar o sistema nebuloso
	 */
	public SistemaNebuloso(){
		configurarConjuntosNebulosos();
	}
	
	public void zerarMatriz(double mat[][]){
		
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				mat[i][j] = -1;
			}
			
		}
	}
	
	/**
	 * Retornar a temperatura e a pressao lidas do teclado
	 * @return
	 */
	public double[] getDados(){
		
		Scanner scan = new Scanner(System.in);
		double[] dados = new double[2];
		
		System.out.println("Digite a temperatura(800-1200):");
		dados[0] = scan.nextDouble();
		
		System.out.println("Digite o volume(2-12):");
		dados[1] = scan.nextDouble();
		
		scan.close();
		
		return dados;
		
		
	}
	/**
	 * Configura os conjuntos nebulosos para os valores dados no problema
	 */
	public void configurarConjuntosNebulosos(){
		
		temperatura = new VariavelEntrada(500, 0.8);
		ConjuntoNebuloso baixa = new ConjuntoNebuloso(800, 800, 900, 1000, 250, FuncaoPertinencia.TRAPEZOIDE);
		ConjuntoNebuloso media = new ConjuntoNebuloso(900, 1000, 1100, 250, FuncaoPertinencia.TRIANGULAR);
		ConjuntoNebuloso alta = new ConjuntoNebuloso(1000, 1100, 1100, 1200, 250, FuncaoPertinencia.TRAPEZOIDE);

		temperatura.getConjuntos().add(baixa);
		temperatura.getConjuntos().add(media);
		temperatura.getConjuntos().add(alta);

		//temperatura.discretizarPontos();
//		double[] resultados = new double[temperatura.getConjunto().size()];
//		resultados = temperatura.calcularGrauPertinenciaNosConjuntos(935);
//
//		System.out.println("--> Temperatura");
//		System.out.println("Baixa: " + resultados[0]);
//		System.out.println("Media: " + resultados[1]);
//		System.out.println("Alta: " + resultados[2]);

		//----------CONFIGURANDO O VOLUME---------------
		volume = new VariavelEntrada(500, 0.02);
		baixa = new ConjuntoNebuloso(2, 2, 4.5, 7, 250, FuncaoPertinencia.TRAPEZOIDE);
		media = new ConjuntoNebuloso(4.5, 7, 9.5, 250, FuncaoPertinencia.TRIANGULAR);
		alta = new ConjuntoNebuloso(7, 9.5, 12, 12, 250, FuncaoPertinencia.TRAPEZOIDE);

		volume.getConjuntos().add(baixa);
		volume.getConjuntos().add(media);
		volume.getConjuntos().add(alta);

		//volume.discretizarPontos();
// 		resultados = volume.calcularGrauPertinenciaNosConjuntos(8.75);
//
//		System.out.println("--> Volume");
//		System.out.println("Baixo: " + resultados[0]);
//		System.out.println("Medio: " + resultados[1]);
//		System.out.println("Alto: " + resultados[2]);

		//---------CONFIGURANDO A PRESSAO--------------
		pressao = new VariavelEntrada(500, 0.016);
		baixa = new ConjuntoNebuloso(4, 4, 5, 8, 250, FuncaoPertinencia.TRAPEZOIDE);
		media = new ConjuntoNebuloso(6, 8, 10, 250, FuncaoPertinencia.TRIANGULAR);
		alta = new ConjuntoNebuloso(8, 11, 12, 12, 250, FuncaoPertinencia.TRAPEZOIDE);

		pressao.getConjuntos().add(baixa);
		pressao.getConjuntos().add(media);
		pressao.getConjuntos().add(alta);

		//pressao.discretizarPontos();
//		resultados = pressao.calcularGrauPertinenciaNosConjuntos(5);
//		
//		System.out.println("--> Pressão");
//		System.out.println("Baixa: " + resultados[0]);
//		System.out.println("Media: " + resultados[1]);
//		System.out.println("Alta: " + resultados[2]);
		
	}
	
	/**
	 * Composicao max-min
	 * @param mat1: matriz resultante de um operador mamdani
	 * @param mat2: matriz resultante de outro operador mamdani
	 */
	public void compor(double[][] mat1, double mat2[][]){
		
		
		
	}
	
	/**
	 * Operador de agregacao máximo
	 */
	public void agregar(){
		
		
	}
	
	/**
	 * Calcula o grau de pertinencia nos conjuntos para o seguimento do sistema nebuloso
	 * @param temp
	 * @param vol
	 */
	public void fuzzificar(double temp, double vol){
		
		resultadoFuzzificacao = new double[2][3];
		
		resultadoFuzzificacao[0] = temperatura.calcularGrauPertinenciaNosConjuntos(temp);
		resultadoFuzzificacao[1] = volume.calcularGrauPertinenciaNosConjuntos(vol);		
		
	}
	
	/**
	 * Aplicar o operador mandani nas regras ativadas, ou seja, se entrar no if, aplica-se MIN(x, y)
	 * Primeiro indice do vetor resultadoFuzzificao indica a variavel de entrada(0 -> temperatura, 1 -> volume)
	 * Segundo indice do vetor resultadoFuzzificao indica a variavel linguistica(baixa, media e alta)
	 * Regras que nao foram ativadas possuirão o valor zero na matriz de ativacao
	 */
	public void inferir(){
		
		matrizAtivacao = new double[3][3];
		zerarMatriz(matrizAtivacao);
		
		
		        //temperatura baixa              volume pequeno
		if(resultadoFuzzificacao[0][0] > 0 && resultadoFuzzificacao[1][0] > 0){
			
			matrizAtivacao[0][0] = Math.min(resultadoFuzzificacao[0][0], resultadoFuzzificacao[1][0]);
			
				//temperatura baixa						volume medio
		} else if(resultadoFuzzificacao[0][0] > 0 && resultadoFuzzificacao[1][1] > 0) {
			
			matrizAtivacao[0][1] = Math.min(resultadoFuzzificacao[0][0], resultadoFuzzificacao[1][1]);
			
				//temperatura baixa						volume grande
		} else if(resultadoFuzzificacao[0][0] > 0 && resultadoFuzzificacao[1][2] > 0){
			
			matrizAtivacao[0][2] = Math.min(resultadoFuzzificacao[0][0], resultadoFuzzificacao[1][2]);
			
				//temperatura media						volume pequeno
		} else if(resultadoFuzzificacao[0][1] > 0 && resultadoFuzzificacao[1][0] > 0){
			
			matrizAtivacao[1][0] = Math.min(resultadoFuzzificacao[0][1], resultadoFuzzificacao[1][0]);
			
				//temperatura media						volume medio
		} else if(resultadoFuzzificacao[0][1] > 0 && resultadoFuzzificacao[1][1] > 0){
			
			matrizAtivacao[1][1] = Math.min(resultadoFuzzificacao[0][1], resultadoFuzzificacao[1][1]);
			
				//temperatura media						volume grande
		} else if(resultadoFuzzificacao[0][1] > 0 && resultadoFuzzificacao[1][2] > 0){
			
			matrizAtivacao[1][2] = Math.min(resultadoFuzzificacao[0][1], resultadoFuzzificacao[1][2]);
			
				//temperatura alta						volume pequeno
		} else if(resultadoFuzzificacao[0][2] > 0 && resultadoFuzzificacao[1][0] > 0){
			
			matrizAtivacao[2][0] = Math.min(resultadoFuzzificacao[0][2], resultadoFuzzificacao[1][0]);
			
				//temperatura alta						volume medio
		} else if(resultadoFuzzificacao[0][2] > 0 && resultadoFuzzificacao[1][1] > 0){
			
			matrizAtivacao[2][1] = Math.min(resultadoFuzzificacao[0][2], resultadoFuzzificacao[1][1]);
			
				//temperatura alta						volume grande
		} else if(resultadoFuzzificacao[0][2] > 0 && resultadoFuzzificacao[1][2] > 0){
			
			matrizAtivacao[2][2] = Math.min(resultadoFuzzificacao[0][2], resultadoFuzzificacao[1][2]);
			
		}
		
	}
	
	/**
	 * Realiza o método de centro de massa para desfuzzificar
	 */
	public void desfuzzificar(){
		
	}
	
	public static void main(String args[]){
		
		SistemaNebuloso sn = new SistemaNebuloso();
		
		//Pegar os dados de temperatura e volume no console
		double[] dados = sn.getDados();
		
		//gerar graus de pertinencia nas variaveis de temperatura e volume
		sn.fuzzificar(dados[0], dados[1]);
		
		sn.inferir();
		
		sn.desfuzzificar();
		
	}
	
}
