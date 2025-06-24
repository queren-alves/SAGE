package sage.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sage.controller.AmbienteController;
import sage.controller.DispositivoController;
import sage.model.Ambiente;
import sage.model.Dispositivo;
import sage.util.Validar;

import java.util.Optional;

public class DispositivoMenu {

	public static void showCadastro() {
	    Stage stage = new Stage();

	    GridPane grid = new GridPane();
	    grid.setAlignment(Pos.CENTER);
	    grid.getStyleClass().add("grid-pane");

	    TextField idField = new TextField();
	    idField.setPromptText("ID do Dispositivo");
	    TextField nomeField = new TextField();
	    nomeField.setPromptText("Nome do Dispositivo");
	    ComboBox<String> tipoBox = new ComboBox<>();
	    tipoBox.getItems().addAll("Climatização", "Iluminação");
	    tipoBox.getStyleClass().add("combo-box");
	    tipoBox.setPromptText("Tipo do Dispositivo");
	    TextField consumoField = new TextField();
	    consumoField.setPromptText("Consumo do Dispositivo");
	    TextField ambienteIdField = new TextField();
	    ambienteIdField.setPromptText("ID do Ambiente");

	    Button salvar = new Button("Salvar");
	    salvar.setOnAction(_ -> {
	        if (Validar.campoVazio(idField, "ID") || Validar.campoInvalido(idField, "ID", "inteiro")) return;
	        if (Validar.campoVazio(nomeField, "Nome")) return;
	        if (tipoBox.getValue() == null) {
	            Validar.alert("Selecione o tipo do dispositivo.");
	            return;
	        }
	        if (Validar.campoVazio(consumoField, "Consumo") || Validar.campoInvalido(consumoField, "Consumo", "real")) return;
	        if (Validar.campoVazio(ambienteIdField, "ID do Ambiente") || Validar.campoInvalido(ambienteIdField, "ID do Ambiente", "inteiro")) return;

	        int id = Integer.parseInt(idField.getText());
	        String nome = nomeField.getText();
	        String tipo = tipoBox.getValue();
	        double consumo = Double.parseDouble(consumoField.getText());
	        int ambienteId = Integer.parseInt(ambienteIdField.getText());

	        Optional<Ambiente> ambienteOpt = AmbienteController.findById(ambienteId);
	        if (ambienteOpt.isEmpty()) {
	            Validar.alert("Ambiente não encontrado.");
	            return;
	        }

	        Dispositivo dispositivo = new Dispositivo(id, nome, tipo, consumo, ambienteOpt.get());

	        if (DispositivoController.persist(dispositivo)) {
	            Validar.alert("Dispositivo cadastrado com sucesso!");
	            idField.clear();
	            nomeField.clear();
	            tipoBox.setValue(null);
	            consumoField.clear();
	            ambienteIdField.clear();
	        } else {
	            Validar.alert("Erro ao cadastrar o dispositivo.");
	        }
	    });

	    Button voltar = new Button("Voltar");
	    voltar.setOnAction(_ -> stage.close());

	    HBox botoes = new HBox(10, salvar, voltar);

	    grid.add(new Label("ID:"), 0, 0); grid.add(idField, 1, 0);
	    grid.add(new Label("Nome:"), 0, 1); grid.add(nomeField, 1, 1);
	    grid.add(new Label("Tipo:"), 0, 2); grid.add(tipoBox, 1, 2);
	    grid.add(new Label("Consumo (W):"), 0, 3); grid.add(consumoField, 1, 3);
	    grid.add(new Label("ID do Ambiente:"), 0, 4); grid.add(ambienteIdField, 1, 4);
	    grid.add(botoes, 1, 6);

	    Scene scene = new Scene(grid, 400, 270);
	    Estilo.apply(scene);
	    stage.setScene(scene);
	    stage.setResizable(false);
	    stage.setTitle("Cadastrar Dispositivo");
	    stage.show();
	}

	public static void showEditar() {
	    Stage stage = new Stage();
	    GridPane grid = new GridPane();
	    grid.getStyleClass().add("grid-pane");

	    TextField idField = new TextField();
	    idField.setPromptText("ID do Dispositivo");
	    TextField nomeField = new TextField();
	    nomeField.setPromptText("Nome do Dispositivo");
	    nomeField.setEditable(false);
	    ComboBox<String> tipoBox = new ComboBox<>();
	    tipoBox.getStyleClass().add("combo-box");
	    tipoBox.getItems().addAll("Climatização", "Iluminação");
	    tipoBox.setPromptText("Tipo do Dispositivo");
	    tipoBox.setDisable(true);
	    TextField consumoField = new TextField();
	    consumoField.setEditable(false);
	    consumoField.setPromptText("Consumo do Dispositivo");
	    
	    Button buscar = new Button("Buscar");
	    Button salvar = new Button("Salvar Alterações");
	    Button voltar = new Button("Voltar");
	    voltar.setOnAction(_ -> stage.close());

	    buscar.setOnAction(_ -> {
	        if (Validar.campoVazio(idField, "ID") || Validar.campoInvalido(idField, "ID", "inteiro")) return;
	        int id = Integer.parseInt(idField.getText());

	        Optional<Dispositivo> opt = DispositivoController.findById(id);
	        if (opt.isEmpty()) {
	            Validar.alert("Dispositivo não encontrado.");
	            return;
	        }

	        Dispositivo d = opt.get();
	        nomeField.setText(d.getNome());
	        tipoBox.setValue(d.getTipo());
	        consumoField.setText(String.valueOf(d.getConsumo()));

	        idField.setEditable(false);
	        nomeField.setEditable(true);
	        tipoBox.setDisable(false);
	        consumoField.setEditable(true);
	    });

	    salvar.setOnAction(_ -> {
	        if (Validar.campoVazio(nomeField, "Nome")) return;
	        if (tipoBox.getValue() == null) {
	            Validar.alert("Selecione o tipo do dispositivo.");
	            return;
	        }
	        if (Validar.campoVazio(consumoField, "Consumo") || Validar.campoInvalido(consumoField, "Consumo", "real")) return;

	        int id = Integer.parseInt(idField.getText());
	        String nome = nomeField.getText();
	        String tipo = tipoBox.getValue();
	        double consumo = Double.parseDouble(consumoField.getText());

	        Optional<Dispositivo> opt = DispositivoController.findById(id);
	        if (opt.isEmpty()) {
	            Validar.alert("Dispositivo não encontrado para edição.");
	            return;
	        }

	        Dispositivo antigo = opt.get();
	        Dispositivo novo = new Dispositivo(id, nome, tipo, consumo, antigo.getAmbiente());

	        DispositivoController.save(novo);
	        Validar.alert("Dispositivo editado com sucesso.");

	        idField.clear();
	        nomeField.clear();
	        tipoBox.setValue(null);
	        consumoField.clear();
	        idField.setEditable(true);
	        nomeField.setEditable(false);
	        tipoBox.setDisable(true);
	        consumoField.setEditable(false);
	    });

	    grid.add(new Label("ID:"), 0, 0); grid.add(idField, 1, 0); grid.add(buscar, 2, 0);
	    grid.add(new Label("Novo Nome:"), 0, 1); grid.add(nomeField, 1, 1);
	    grid.add(new Label("Novo Tipo:"), 0, 2); grid.add(tipoBox, 1, 2);
	    grid.add(new Label("Novo Consumo (W):"), 0, 3); grid.add(consumoField, 1, 3);
	    grid.add(salvar, 1, 4); grid.add(voltar, 2, 4);

	    Scene scene = new Scene(grid, 430, 200);
	    Estilo.apply(scene);
	    stage.setScene(scene);
	    stage.setResizable(false);
	    stage.setTitle("Editar Dispositivo");
	    stage.show();
	}

    public static void showExcluir() {
    	Stage stage = new Stage();
        
        VBox vbox = new VBox(10);
        vbox.getStyleClass().add("vbox");       

        TextField idField = new TextField();
        idField.setPromptText("ID do Dispositivo para excluir");
        
        Button excluir = new Button("Excluir");
        excluir.setOnAction(_ -> {
        	if (Validar.campoVazio(idField, "ID") || Validar.campoInvalido(idField, "ID", "inteiro")) return;
            int id = Integer.parseInt(idField.getText());
            Optional<Dispositivo> disp = DispositivoController.findById(id);
            if (disp.isEmpty()) {
                Validar.alert("Dispositivo não encontrado.");
                return;
            }
        
            boolean sucesso = DispositivoController.removeById(id);
            Validar.alert(sucesso ? "Dispositivo excluído com sucesso!" : "Erro ao excluir o Dispositivo.");
                idField.clear();
        });

        Button voltar = new Button("Voltar");
        voltar.setOnAction( _-> stage.close());
        
        HBox botoes = new HBox(10, excluir, voltar);
        botoes.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(new Label("ID do Dispositivo para excluir:"), idField, botoes);
        Scene scene = new Scene(vbox, 350, 150);
        Estilo.apply(scene);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Excluir Dispositivo");
        stage.show();
    }
    
}