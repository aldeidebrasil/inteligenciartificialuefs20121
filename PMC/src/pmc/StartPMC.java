package pmc;

import model.Rede;
/**
 * Classe para inicializa��o da rede
 * @author Douglas e Andr�
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
