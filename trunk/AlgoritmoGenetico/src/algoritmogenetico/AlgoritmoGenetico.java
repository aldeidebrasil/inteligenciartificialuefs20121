package algoritmogenetico;

public class AlgoritmoGenetico {
	
	private Populacao populacao;
	private float taxaMutacao;
	private float taxaCruzamento;
	private int qtdGeracoes;

	public AlgoritmoGenetico(int tamPop, int qtdGeracoes){
		populacao = new Populacao();
		populacao.setTamanhoPopulacao(tamPop);
		qtdGeracoes = this.qtdGeracoes;
	}
	
	public void rodar(){
		
		populacao.iniciarPopulacao();
		
		int i = 0;
		while(i < qtdGeracoes){
			
		}
		
	}

}
