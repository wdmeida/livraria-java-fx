package application;
	
import java.io.IOException;

import br.com.casadocodigo.livraria.produtos.Produto;
import dao.ProdutoDAO;
import io.Exportador;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;

public class Main extends Application {
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public void start(Stage primaryStage) {
		
		Group group = new Group();
		Scene scene = new Scene(group, 690, 510);
		
		//Define a fonte dos dados que preencherão a tabela.
		ObservableList<Produto> produtos = new ProdutoDAO().lista();
		
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
		
		//Cria um label para o título e define suas propriedades.
		Label label = new Label("Listagem de Livros");
		label.setFont(Font.font("Lucida Grande", FontPosture.REGULAR, 30));
		label.setPadding(new Insets(20, 0, 10, 10));
		
		//Cria um label para mostrar o progresso de exportação.
		Label progresso = new Label();
		progresso.setLayoutX(485);
		progresso.setLayoutY(30);
		
		//Cria o button Exportar CSV e define sua propriedades.
		Button button = new Button("Exportar CSV");
		button.setLayoutX(575);
		button.setLayoutY(25);
		
		//Define a ação que será executada ao clicar no botão.
		button.setOnAction(event -> {		
			
			//Utiliza uma API do Java FX para executar código assíncrono.
			Task<Void> task = new Task<Void>() {

				@Override
				protected Void call() throws Exception {
					dormePorVinteSegundos();
					exportarEmCSV(produtos);
					return null;
				}
			};
			
			//Executa uma ação assim que a task assincrona começa a ser executada.
			task.setOnRunning(e -> progresso.setText("Exportando..."));
			//Executa uma ação assim que a task for concluída.
			task.setOnSucceeded(e -> progresso.setText("Concluído!"));
			
			new Thread(task).start();
		});
		
		//Adiciona os componentes criados ao grupo de elementos do cenário.
		group.getChildren().addAll(label, vBox, button, progresso);
		
		//Define o título e exibe o cenário.
		primaryStage.setTitle("Sistema da livraria com Java FX");
		primaryStage.setScene(scene);
		primaryStage.show();
	}//start()
	
	private void dormePorVinteSegundos() {
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			System.out.println("Ops, ocorreu um erro: " + e);
		}
	}//dormePorVinteSegundos()
	
	private void exportarEmCSV(ObservableList<Produto> produtos){
		try {
			new Exportador().paraCSV(produtos);
		} catch (IOException e) {
			System.out.println("Erro ao exportar: " + e);
		}
	}//exportarEmCSV()
	
	public static void main(String[] args) {
		launch(args);
	}//main()
}
