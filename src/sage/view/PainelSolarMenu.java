package sage.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sage.controller.PainelSolarController;
import sage.model.PainelSolar;
import sage.util.Validar;
import java.util.Optional;


public class PainelSolarMenu {

	public static void showCadastro() {
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        grid.getStyleClass().add("grid-pane");
        
        TextField idField = new TextField();
        idField.setPromptText("ID do Painel Solar");
        TextField nomeField = new TextField();
        nomeField.setPromptText("Nome do Painel Solar");
        TextField geracaoField = new TextField();
        geracaoField.setPromptText("Geração do Painel Solar");
        
        Button salvar = new Button("Salvar");
        salvar.setOnAction(_ -> {
        	if (Validar.campoVazio(idField, "ID") || Validar.campoInvalido(idField, "ID", "inteiro")) return;
            if (Validar.campoVazio(nomeField, "Nome")) return;
            if (Validar.campoVazio(geracaoField, "Geração")|| Validar.campoInvalido(geracaoField, "Geração", "real")) return;

        	int id = Integer.parseInt(idField.getText());
        	String nome = nomeField.getText();
        	double geracao = Double.parseDouble(geracaoField.getText());

        	PainelSolar dispositivo = new PainelSolar(id, nome, geracao);

        	if (PainelSolarController.persist(dispositivo)) {
        		Validar.alert("Painel Solar cadastrado com sucesso.");
        	        idField.clear();
        	        nomeField.clear();
        	        geracaoField.clear();   
        	} else {
        		Validar.alert("Erro ao cadastrar o Painel Solar.");
        	}
        });
        
        Button voltar = new Button("Voltar");
        voltar.setOnAction( _-> stage.close());
        
        HBox botoes = new HBox(10, salvar, voltar);
        botoes.setAlignment(Pos.CENTER);

        grid.add(new Label("ID:"), 0, 0); grid.add(idField, 1, 0);
        grid.add(new Label("Nome:"), 0, 1); grid.add(nomeField, 1, 1);
        grid.add(new Label("Geração (W):"), 0, 2); grid.add(geracaoField, 1, 2);

        grid.add(botoes, 1, 4);

        Scene scene = new Scene(grid, 380, 170);
        Estilo.apply(scene);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Cadastrar Painel Solar");
        stage.show();
    }

    public static void showEditar() {
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        grid.getStyleClass().add("grid-pane");

        TextField idField = new TextField();
        idField.setPromptText("ID do Painel Solar");
        TextField nomeField = new TextField();
        nomeField.setEditable(false);
        nomeField.setPromptText("Nome do Painel Solar");
        TextField geracaoField = new TextField();
        geracaoField.setEditable(false);
        geracaoField.setPromptText("Geração do Painel Solar");

        Button buscar = new Button("Buscar");
        Button salvar = new Button("Salvar Alterações");
        Button voltar = new Button("Voltar");
        voltar.setOnAction(_ -> stage.close());

        buscar.setOnAction(_ -> {
        		if (Validar.campoVazio(idField, "ID") || Validar.campoInvalido(idField, "ID", "inteiro")) return;
                int id = Integer.parseInt(idField.getText());
                Optional<PainelSolar> opt = PainelSolarController.findById(id);
                if (opt.isEmpty()) {
                    Validar.alert("Painel Solar não encontrado.");
                    return;
                }

                PainelSolar p = opt.get();
                nomeField.setText(p.getNome());
                geracaoField.setText(String.valueOf(p.getGeracao()));
                
                idField.setEditable(false);
                nomeField.setEditable(true);
                geracaoField.setEditable(true);
        });

        salvar.setOnAction(_ -> {
	            if (Validar.campoVazio(nomeField, "Nome")) return;
	            if (Validar.campoVazio(geracaoField, "Geração") || Validar.campoInvalido(geracaoField, "Geração", "real")) return;
         
                int id = Integer.parseInt(idField.getText());
                String nome = nomeField.getText();
                double geracao = Double.parseDouble(geracaoField.getText());

                PainelSolar novo = new PainelSolar(id, nome, geracao);
                PainelSolarController.save(novo);
                Validar.alert("Painel Solar editado com sucesso.");
                idField.clear();
    	        nomeField.clear();
    	        geracaoField.clear(); 
    	        idField.setEditable(true);
                nomeField.setEditable(false);
                geracaoField.setEditable(false);
        });

        grid.add(new Label("ID:"), 0, 0); grid.add(idField, 1, 0);
        grid.add(buscar, 2, 0);
        grid.add(new Label("Novo Nome:"), 0, 1); grid.add(nomeField, 1, 1);
        grid.add(new Label("Nova Geração (W):"), 0, 2); grid.add(geracaoField, 1, 2);
        grid.add(salvar, 1, 4);
        grid.add(voltar, 2, 4);

        Scene scene = new Scene(grid, 430, 200);
        Estilo.apply(scene);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Editar Painel Solar");
        stage.show();
    }

    public static void showExcluir() {
    	Stage stage = new Stage();
        VBox vbox = new VBox(10);
        vbox.getStyleClass().add("vbox");       

        TextField idField = new TextField();
        idField.setPromptText("ID do Painel Solar para excluir");
        
        Button excluir = new Button("Excluir");
        excluir.setOnAction(_ -> {
        	if (Validar.campoVazio(idField, "ID") || Validar.campoInvalido(idField, "ID", "inteiro")) return;
            int id = Integer.parseInt(idField.getText());
            Optional<PainelSolar> painel = PainelSolarController.findById(id);
            if (painel.isEmpty()) {
                Validar.alert("Painel Solar não encontrado.");
                return;
            }
        
            boolean sucesso = PainelSolarController.removeById(id);
            Validar.alert(sucesso ? "Painel Solar excluído com sucesso!" : "Erro ao excluir o Painel Solar.");
                idField.clear();
        });

        Button voltar = new Button("Voltar");
        voltar.setOnAction( _-> stage.close());
        
        HBox botoes = new HBox(10, excluir, voltar);
        botoes.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(new Label("ID do Painel Solar para excluir:"), idField, botoes);
        Scene scene = new Scene(vbox, 350, 150);
        Estilo.apply(scene);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Excluir Painel Solar");
        stage.show();
    }
	
}