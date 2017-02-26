package stream;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class TesteEntrada {

	public static void main(String[] args) {
		
		try {
			/*//Ler dados arquivo.
			//InputStream is = new FileInputStream("teste.txt");
			
			//Alterando apenas uma linha, para ler do teclado.
			InputStream is = System.in;
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader reader = new BufferedReader(isr);
			String linha = reader.readLine();
			while (linha != null){
				System.out.println(linha);
				linha = reader.readLine();
			}
			reader.close();*/
			
			//Executa a mesma ação utilizando a classe Scanner.

			/*Scanner sc = new Scanner(System.in);
			System.out.println("Digite seu nome: ");
			String nomeDigitado = sc.nextLine();
			System.out.println("Digite sua idade: ");
			int idadeDigitada = sc.nextInt();
			System.out.println("Nome: " + nomeDigitado);
			System.out.println("Idade: " + idadeDigitada);*/
			
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(new File("teste.txt"));
			while (sc.hasNextLine()){
				System.out.println(sc.nextLine());
			}
		} catch (IOException e) {
			System.out.println("Erro ao tentar ler o arquivo " + e);
		}

	}

}
