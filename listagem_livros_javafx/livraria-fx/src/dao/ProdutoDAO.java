package dao;

import static javafx.collections.FXCollections.observableArrayList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.casadocodigo.livraria.Autor;
import br.com.casadocodigo.livraria.produtos.LivroFisico;
import br.com.casadocodigo.livraria.produtos.Produto;
import db.ConnectionFactory;
import javafx.collections.ObservableList;
/**
 * Cuida do acesso aos dados da classe {@link Produto}.
 * 
 * @author Wagner Almeida
 * @since 1.0
 */
public class ProdutoDAO {
	
	/**
	 * Lista todos os produtos do banco de dados.
	 * 
	 * @return {@link ObservableList} com todos os produtos.
	 * @throws RuntimeException em caso de erros.
	 */
	public ObservableList<Produto> lista() {
		
		ObservableList<Produto> produtos = observableArrayList();
		PreparedStatement ps;
		
		try (Connection conn = new ConnectionFactory().getConnection()){
			
			ps = conn.prepareStatement("select * from produtos");
			ResultSet resultSet = ps.executeQuery();
			
			while (resultSet.next()) {
				LivroFisico livro = new LivroFisico(new Autor());
				livro.setNome(resultSet.getString("nome"));
				livro.setDescricao(resultSet.getString("descricao"));
				livro.setValor(resultSet.getDouble("valor"));
				livro.setIsbn(resultSet.getString("isbn"));
				produtos.add(livro);
			}
			resultSet.close();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return produtos;
	}//ObservaleList()
	
	/**
	 * Adiciona um novo {@link Produto} ao banco de dados.
	 * @param produto que deverá ser adicionado no banco.
	 * @throws RuntimeException em caso de erros.
	 */
	public void adiciona(Produto produto) {
		PreparedStatement ps;
		
		try (Connection conn = new ConnectionFactory().getConnection()){
			ps = conn.prepareStatement("insert into produtos (nome, descricao, valor, isbn) values (?,?,?,?)");
			
			ps.setString(1, produto.getNome());
			ps.setString(2, produto.getDescricao());
			ps.setDouble(3, produto.getValor());
			ps.setString(4, produto.getIsbn());
			
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}//adiciona()
}
