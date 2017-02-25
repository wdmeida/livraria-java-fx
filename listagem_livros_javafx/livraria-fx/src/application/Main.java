package application;
	
import br.com.casadocodigo.livraria.produtos.Produto;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import repositorio.RepositorioDeProdutos;


public class Main extends Application {
	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public void start(Stage primaryStage) {
		
		Group group = new Group();
		Scene scene = new Scene(group, 690, 510);
		
		//Define a fonte dos dados que preencherão a tabela.
		ObservableList<Produto> produtos = new RepositorioDeProdutos().lista();
		
		//Define a tabela, e as colunas que devem ser preenchidas na mesma.
		TableView tableView = new TableView(produtos);
		
		TableColumn nomeColumn = new TableColumn("Nome");
		nomeColumn.setMinWidth(180);
		nomeColumn.setCellValueFactory(new PropertyValueFactory("nome"));
		
		TableColumn descColumn = new TableColumn("Descrição");
		descColumn.setMinWidth(230);
		descColumn.setCellValueFactory(new PropertyValueFactory("descricao"));
		
		TableColumn valorColumn = new TableColumn("Valor");
		valorColumn.setMinWidth(60);
		valorColumn.setCellValueFactory(new PropertyValueFactory("valor"));
		
		TableColumn isbnColumn = new TableColumn("ISBN");
		isbnColumn.setMinWidth(180);
		isbnColumn.setCellValueFactory(new PropertyValueFactory("isbn"));
		
		//Adiciona as colunas a tabela.
		tableView.getColumns().addAll(nomeColumn, descColumn, valorColumn, isbnColumn);
		
		//Cria um componente vBox para envolver a tabela e assim acertar o espaçamento.
		final VBox vBox = new VBox(tableView);
		vBox.setPadding(new Insets(70, 0, 0, 10));
		
		//Cria um label e define suas propriedades.
		Label label = new Label("Listagem de Livros");
		label.setFont(Font.font("Lucida Grande", FontPosture.REGULAR, 30));
		label.setPadding(new Insets(20, 0, 10, 10));
		
		//Adiciona os componentes criados ao cenário.
		group.getChildren().addAll(label, vBox);
		primaryStage.setScene(scene);
		
		//Define o título e exibe o cenário.
		primaryStage.setTitle("Sistema da livraria com Java FX");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
