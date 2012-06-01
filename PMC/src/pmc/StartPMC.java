package pmc;

import java.io.*;
import model.Rede;

/**
 * Classe para inicialização da rede
 * @author Douglas e André
 *
 */
public class StartPMC {

	/**
	 * @param args
	 * @throws Exception 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		Rede rede = new Rede(3);
//		rede.treinar();
		
		File arquivoLeitura = new File("treina.txt");  
		LineNumberReader linhaLeitura = new LineNumberReader(new FileReader(arquivoLeitura));  
		linhaLeitura.skip(arquivoLeitura.length());  
		int qtdLinha = linhaLeitura.getLineNumber();
		System.out.println(qtdLinha);
	}

}
