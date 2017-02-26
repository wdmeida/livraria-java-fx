package stream;

import java.io.IOException;
import java.io.PrintStream;

public class TesteSaida {

	public static void main(String[] args) throws IOException {
		
		/*OutputStream os = new FileOutputStream("saida.txt");
		OutputStreamWriter osw = new OutputStreamWriter(os);
		BufferedWriter bw = new BufferedWriter(osw);
		
		bw.write("Testando a escrita em arquivo");
		bw.newLine();
		bw.write("Conteúdo na próxima linha");
		bw.close();*/
		
		//Gerando de maneira mais simples com PrintSteam.
		PrintStream out = new PrintStream("saida.txt");
		out.println("Testando novas forma de escrita.");
		out.println("Conteúdo de mais uma linha");
		out.close();
		
	}

}
