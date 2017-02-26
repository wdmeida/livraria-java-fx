package application;
	
import java.io.IOException;

import br.com.casadocodigo.livraria.produtos.Produto;
import dao.ProdutoDAO;
import io.Exportador;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public void start(Stage primaryStage) {
		
		Group group = new Group();
		Scene scene = new Scene(group, 690, 510);
		
		//Adiciona o arquivo que contém o css responsável por estilizar os componentes.
		scene.getStylesheets().add(getClass()
				.getResource("application.css").toExternalForm());
		
		
		//Define a fonte dos dados que preencherão a tabela.
		ObservableList<Produto> produtos = new ProdutoDAO().lista();
		
		//Define a tabela, e as colunas que devem ser preenchidas na mesma.
		TableView tableView = new TableView(produtos);
		
		TableColumn nomeColumn = criaColuna("Nome", 180, "nome");
		TableColumn descColumn = criaColuna("Descrição", 230, "descricao");
		TableColumn valorColumn = criaColuna("Valor", 60, "valor");
		TableColumn isbnColumn = criaColuna("ISBN", 180, "isbn");
		
		//Adiciona as colunas a tabela.
		tableView.getColumns().addAll(nomeColumn, descColumn, valorColumn, isbnColumn);
		
		//Cria um componente vBox para envolver a tabela e assim acertar o espaçamento.
		final VBox vBox = new VBox(tableView);
		vBox.setId("vbox");
		
		//Cria um label para o título e define suas propriedades.
		Label label = new Label("Listagem de Livros");
		//Define um identificador único ao elemento.
		label.setId("label-listagem");
		
		//Cria um label para mostrar o progresso de exportação.
		Label progresso = new Label();
		progresso.setId("label-progresso");
		
		//Calcula o valor total dos produtos no estoque.
		double valorTotal = produtos.stream()
				.mapToDouble(Produto::getValor).sum();
		
		//Cria um label para exibir informações do estoque.
		Label labelFooter = new Label(
				String.format("Você tem R$ %.2f em estoque, "
						+ "com um total de %d produtos.", valorTotal, produtos.size()));
		labelFooter.setId("label-footer");
		
		//Cria o button Exportar CSV e define sua propriedades.
		Button button = new Button("Exportar CSV");
		
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
		group.getChildren().addAll(label, vBox, button, progresso, labelFooter);
		
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
	
	private TableColumn<Produto, String> criaColuna(String titulo, int largura, String atributo) {
		
		TableColumn<Produto, String> column = new TableColumn<Produto, String>(titulo);
		column.setMinWidth(largura);
		column.setCellValueFactory(new PropertyValueFactory<Produto, String>(atributo));
		
		return column;
	}//criaColuna()
	
	public static void main(String[] args) {
		launch(args);
	}//main()
}
