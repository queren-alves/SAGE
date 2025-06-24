package sage.view;

import java.util.Optional;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sage.controller.BlocoController;
import sage.model.Bloco;
import sage.util.Validar;

public class BlocoMenu {
	
	public static void showCadastro() {
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        grid.getStyleClass().add("grid-pane");

        TextField idField = new TextField();
        idField.setPromptText("ID do Bloco");
        TextField nomeField = new TextField();
        nomeField.setPromptText("Nome do Bloco");

        Button cadastrar = new Button("Cadastrar");
        cadastrar.setOnAction(_ -> {
        	if (Validar.campoVazio(idField, "ID") || Validar.campoInvalido(idField, "ID", "inteiro")) return;
            if (Validar.campoVazio(nomeField, "Nome")) return;
            
            int id = Integer.parseInt(idField.getText());
            String nome = nomeField.getText();
            boolean sucesso = BlocoController.persist(new Bloco(id, nome));
            Validar.alert(sucesso ? "Bloco cadastrado com sucesso!" : "Erro ao cadastrar o Bloco.");
            if (sucesso) {
                idField.clear();
                nomeField.clear();
            }
        });
        
        Button cancelar = new Button("Voltar");
        cancelar.setOnAction(_ -> stage.close());
        
        HBox botoes = new HBox(10, cadastrar, cancelar);
        botoes.setAlignment(Pos.CENTER);
        
        grid.add(new Label("ID:"), 0, 0); grid.add(idField, 1, 0);
        grid.add(new Label("Nome:"), 0, 1); grid.add(nomeField, 1, 1);  
        grid.add(botoes, 1, 3);

        Scene scene = new Scene(grid, 300, 150);
        Estilo.apply(scene);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Cadastrar Bloco");
        stage.show();
    }

    public static void showEditar() {
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        grid.getStyleClass().add("grid-pane");

        TextField idField = new TextField();
        idField.setPromptText("ID do Bloco");
        TextField nomeField = new TextField();
        nomeField.setEditable(false);
        nomeField.setPromptText("Novo nome do Bloco");

        Button buscar = new Button("Buscar");
        Button editar = new Button("Salvar Alterações");
        
        buscar.setOnAction(_ -> {
        		if (Validar.campoVazio(idField, "ID") || Validar.campoInvalido(idField, "ID", "inteiro")) return;
                int id = Integer.parseInt(idField.getText());
                Optional<Bloco> opt = BlocoController.findById(id);
                if (opt.isEmpty()) {
                    Validar.alert("Bloco não encontrado.");
                    return;
                }

                Bloco b = opt.get();
                nomeField.setText(b.getNome());
                
                idField.setEditable(false);
                nomeField.setEditable(true);
        });
        
        editar.setOnAction(_ -> {
            if (Validar.campoVazio(nomeField, "Nome")) return;
        
            int id = Integer.parseInt(idField.getText());
            String nome = nomeField.getText();
            
            Bloco novo = new Bloco(id, nome);
            BlocoController.save(novo);
            Validar.alert("Bloco alterado com sucesso.");
            idField.clear();
	        nomeField.clear(); 
	        idField.setEditable(true); 
            nomeField.setEditable(false);
        });
        
        Button voltar = new Button("Voltar");
        voltar.setOnAction( _-> stage.close());
        
        grid.add(new Label("ID:"), 0, 0); grid.add(idField, 1, 0);
        grid.add(buscar, 2, 0);
        grid.add(new Label("Novo Nome:"), 0, 1); grid.add(nomeField, 1, 1);
        grid.add(editar, 1, 4);
        grid.add(voltar, 2, 4);

        Scene scene = new Scene(grid, 380, 150);
        Estilo.apply(scene);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Editar Bloco");
        stage.show();
    }

    public static void showExcluir() {
        Stage stage = new Stage();
        
        VBox vbox = new VBox(10);
        vbox.getStyleClass().add("vbox");       

        TextField idField = new TextField();
        idField.setPromptText("ID do Bloco para excluir");
        
        Button excluir = new Button("Excluir");
        excluir.setOnAction(_ -> {
        	if (Validar.campoVazio(idField, "ID") || Validar.campoInvalido(idField, "ID", "inteiro")) return;
            int id = Integer.parseInt(idField.getText());
            Optional<Bloco> b = BlocoController.findById(id);
            if (b.isEmpty()) {
                Validar.alert("Bloco não encontrado.");
                return;
            }
            if (!b.get().getAmbientes().isEmpty()) {
                Validar.alert("Erro ao excluir, o Bloco possui Ambientes.");
                return;
            }
            boolean sucesso = BlocoController.removeById(id);
            Validar.alert(sucesso ? "Bloco excluído com sucesso!" : "Erro ao excluir o Bloco.");
            idField.clear();
        });
        
        Button voltar = new Button("Voltar");
        voltar.setOnAction( _-> stage.close());
        
        HBox botoes = new HBox(10, excluir, voltar);
        botoes.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(new Label("ID do Bloco para excluir:"), idField, botoes);
        Scene scene = new Scene(vbox, 350, 150);
        Estilo.apply(scene);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Excluir Bloco");
        stage.show();
    }

}