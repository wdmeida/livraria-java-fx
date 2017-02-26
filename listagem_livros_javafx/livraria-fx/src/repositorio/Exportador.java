package repositorio;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import br.com.casadocodigo.livraria.produtos.Produto;

public class Exportador {
	
	public void paraCSV(List<Produto> produtos) throws IOException {
		//Monta o cabeçalho do arquivo que será exportado.
		PrintStream ps = new PrintStream("produtos.csv");
		ps.println("Nome, Descricao, Valor, ISBN");
		
		//Escreve os dados dos produtos recebidos no arquivo.
		produtos.forEach( produto -> {
			ps.println(String.format("%s , %s, %s, %s", 
						produto.getNome(),
						produto.getDescricao(),
						produto.getValor(),
						produto.getIsbn()));
		} );
		ps.close();
	}//paraCSV()
}
