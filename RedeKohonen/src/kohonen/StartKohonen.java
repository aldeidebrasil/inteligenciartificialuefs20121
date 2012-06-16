package kohonen;

import model.Rede;

public class StartKohonen {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Rede rede = new Rede();
		
		rede.treinar();
		
		System.out.println("A rede treinou em "+ rede.getNumEpocas()+ " Épocas.");
		
		rede.testar();
		
		System.out.println("A rede treinou em "+ rede.getNumEpocas()+ " Épocas.");
		
	}

}
