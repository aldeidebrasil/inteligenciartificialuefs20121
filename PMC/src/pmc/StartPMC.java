package pmc;

import model.Rede;
/**
 * Classe para inicialização da rede
 * @author Douglas e André
 *
 */
public class StartPMC {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Rede rede = new Rede(3);
		rede.treinar();
	}

}
