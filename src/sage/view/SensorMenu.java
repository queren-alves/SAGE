package sage.view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sage.controller.AmbienteController;
import sage.controller.SensorController;
import sage.model.Ambiente;
import sage.model.Sensor;
import sage.util.Validar;

import java.util.Optional;

public class SensorMenu {

    public static void showCadastro() {
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        grid.getStyleClass().add("grid-pane");
        
        TextField idField = new TextField();
        idField.setPromptText("ID do Sensor");
        TextField nomeField = new TextField();
        nomeField.setPromptText("Nome do Sensor");
        TextField fabricanteField = new TextField();
        fabricanteField.setPromptText("Fabricante do Sensor");
        TextField ambienteIdField = new TextField();
        ambienteIdField.setPromptText("ID do Ambiente");

        Button salvar = new Button("Salvar");
        salvar.setOnAction(_ -> {
            if (Validar.campoVazio(idField, "ID") || Validar.campoInvalido(idField, "ID", "inteiro")) return;
            if (Validar.campoVazio(nomeField, "Nome")) return;
            if (Validar.campoVazio(fabricanteField, "Fabricante")) return;
            if (Validar.campoVazio(ambienteIdField, "ID do Ambiente") || Validar.campoInvalido(ambienteIdField, "ID do Ambiente", "inteiro")) return;

            int id = Integer.parseInt(idField.getText());
            String nome = nomeField.getText();
            String fabricante = fabricanteField.getText();
            int ambienteId = Integer.parseInt(ambienteIdField.getText());

            Optional<Ambiente> ambiente = AmbienteController.findById(ambienteId);
            if (ambiente.isEmpty()) {
                Validar.alert("Ambiente não encontrado.");
                return;
            }

            boolean sucesso = SensorController.persist(new Sensor(id, nome, fabricante, ambiente.get()));
            Validar.alert(sucesso ? "Sensor cadastrado com sucesso!" : "Erro ao cadastrar o Sensor.");
            if (sucesso) 
            	idField.clear();
	        	nomeField.clear();
	        	fabricanteField.clear(); ;
	        	ambienteIdField.clear(); ;
        });

        Button voltar = new Button("Voltar");
        voltar.setOnAction(_ -> stage.close());

        HBox botoes = new HBox(10, salvar, voltar);
        botoes.getStyleClass().add("hbox");

        grid.add(new Label("ID:"), 0, 0); grid.add(idField, 1, 0);
        grid.add(new Label("Nome:"), 0, 1); grid.add(nomeField, 1, 1);
        grid.add(new Label("Fabricante:"), 0, 2); grid.add(fabricanteField, 1, 2);
        grid.add(new Label("ID do Ambiente:"), 0, 3); grid.add(ambienteIdField, 1, 3);
        grid.add(botoes, 1, 5);

        Scene scene = new Scene(grid, 400, 250);
        Estilo.apply(scene);
        stage.setScene(scene);
        stage.setTitle("Cadastrar Sensor");
        stage.setResizable(false);
        stage.show();
    }

    public static void showEditar() {
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        grid.getStyleClass().add("grid-pane");
        
        TextField idField = new TextField();
        idField.setPromptText("ID do Sensor");
        TextField nomeField = new TextField();
        nomeField.setEditable(false);
        nomeField.setPromptText("Novo Nome");
        TextField fabricanteField = new TextField();
        fabricanteField.setEditable(false);
        fabricanteField.setPromptText("Novo Fabricante");

        Button buscar = new Button("Buscar");
        Button salvar = new Button("Salvar Alterações");
        Button voltar = new Button("Voltar");

        buscar.setOnAction(_ -> {
            if (Validar.campoVazio(idField, "ID") || Validar.campoInvalido(idField, "ID", "inteiro")) return;
            int id = Integer.parseInt(idField.getText());

            Optional<Sensor> opt = SensorController.findById(id);
            if (opt.isEmpty()) {
                Validar.alert("Sensor não encontrado.");
                return;
            }

            Sensor sensor = opt.get();
            nomeField.setText(sensor.getNome());
            fabricanteField.setText(sensor.getFabricante());
            nomeField.setEditable(true);
            fabricanteField.setEditable(true);
            idField.setEditable(false);
        });

        salvar.setOnAction(_ -> {
            if (Validar.campoVazio(nomeField, "Nome")) return;
            if (Validar.campoVazio(fabricanteField, "Fabricante")) return;
            int id = Integer.parseInt(idField.getText());

            Optional<Sensor> opt = SensorController.findById(id);
            if (opt.isEmpty()) {
                Validar.alert("Sensor não encontrado para edição.");
                return;
            }

            Sensor antigo = opt.get();
            Sensor novo = new Sensor(id, nomeField.getText(), fabricanteField.getText(), antigo.getAmbiente());
            SensorController.save(novo);
            Validar.alert("Sensor alterado com sucesso.");
            idField.clear();
            nomeField.clear();
            fabricanteField.clear();
            nomeField.setEditable(false);
            fabricanteField.setEditable(false);
            idField.setEditable(true);
        });

        voltar.setOnAction(_ -> stage.close());
        
        grid.add(new Label("ID:"), 0, 0); grid.add(idField, 1, 0); grid.add(buscar, 2, 0);
        grid.add(new Label("Nome:"), 0, 1); grid.add(nomeField, 1, 1);
        grid.add(new Label("Fabricante:"), 0, 2); grid.add(fabricanteField, 1, 2);
        grid.add(salvar, 1, 3); grid.add(voltar, 2, 3);

        Scene scene = new Scene(grid, 430, 220);
        Estilo.apply(scene);
        stage.setScene(scene);
        stage.setTitle("Editar Sensor");
        stage.setResizable(false);
        stage.show();
    }

    public static void showExcluir() {
        Stage stage = new Stage();
        VBox layout = new VBox(10);
        layout.getStyleClass().add("vbox");

        TextField idField = new TextField();
        idField.setPromptText("ID do Sensor para excluir");

        Button excluir = new Button("Excluir");
        Button voltar = new Button("Voltar");

        excluir.setOnAction(_ -> {
            if (Validar.campoVazio(idField, "ID") || Validar.campoInvalido(idField, "ID", "inteiro")) return;
            int id = Integer.parseInt(idField.getText());
            boolean sucesso = SensorController.removeById(id);
            Validar.alert(sucesso ? "Sensor excluído com sucesso!" : "Erro ao excluir o Sensor.");
            if (sucesso) stage.close();
        });

        voltar.setOnAction(_ -> stage.close());      
        HBox botoes = new HBox(10, excluir, voltar);
        botoes.getStyleClass().add("hbox");

        layout.getChildren().addAll(new Label("ID do Sensor para excluir:"), idField, botoes);

        Scene scene = new Scene(layout, 350, 150);
        Estilo.apply(scene);
        stage.setScene(scene);
        stage.setTitle("Excluir Sensor");
        stage.setResizable(false);
        stage.show();
    }
} 