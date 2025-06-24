package sage.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sage.controller.AmbienteController;
import sage.controller.BlocoController;
import sage.model.Ambiente;
import sage.model.Bloco;
import sage.util.Validar;

import java.util.Optional;

public class AmbienteMenu {

    public static void showCadastro() {
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        grid.getStyleClass().add("grid-pane");

        TextField idField = new TextField();
        idField.setPromptText("ID do Ambiente");
        TextField nomeField = new TextField();
        nomeField.setPromptText("Nome do Ambiente");
        TextField tempoField = new TextField();
        tempoField.setPromptText("Tempo de Ociosidade");
        TextField blocoIdField = new TextField();
        blocoIdField.setPromptText("ID do Bloco");

        Button cadastrar = new Button("Cadastrar");
        cadastrar.setOnAction(_ -> {
        	if (Validar.campoVazio(idField, "ID") || Validar.campoInvalido(idField, "ID", "inteiro")) return;
            if (Validar.campoVazio(nomeField, "Nome")) return;
            if (Validar.campoVazio(tempoField, "Tempo de Ociosidade") || Validar.campoInvalido(tempoField, "Tempo de Ociosidade", "inteiro")) return;
            if (Validar.campoVazio(blocoIdField, "ID do Bloco") || Validar.campoInvalido(blocoIdField, "ID do Bloco", "inteiro")) return;
            
            int id = Integer.parseInt(idField.getText());
            String nome = nomeField.getText();
            int tempo = Integer.parseInt(tempoField.getText());
            int blocoId = Integer.parseInt(blocoIdField.getText());

            Optional<Bloco> bloco = BlocoController.findById(blocoId);
            if (bloco.isEmpty()) {
                Validar.alert("Bloco não existente.");
                return;
            }

            boolean sucesso = AmbienteController.persist(new Ambiente(id, nome, tempo, bloco.get()));
            Validar.alert(sucesso ? "Ambiente cadastrado com sucesso!" : "Erro ao cadastrar o Ambiente.");
            if (sucesso) {
                idField.clear();
                nomeField.clear();
                tempoField.clear();
                blocoIdField.clear();
            }
        });
        
        Button cancelar = new Button("Voltar");
        cancelar.setOnAction(_ -> stage.close());
        
        HBox botoes = new HBox(10, cadastrar, cancelar);
        botoes.setAlignment(Pos.CENTER);
        
        grid.add(new Label("ID:"), 0, 0); grid.add(idField, 1, 0);
        grid.add(new Label("Nome:"), 0, 1); grid.add(nomeField, 1, 1);
        grid.add(new Label("Tempo de Ociosidade (min):"), 0, 2); grid.add(tempoField, 1, 2);
        grid.add(new Label("ID do Bloco:"), 0, 3); grid.add(blocoIdField, 1, 3);
        grid.add(botoes, 1, 5);

        Scene scene = new Scene(grid, 400, 250);
        Estilo.apply(scene);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Cadastrar Ambiente");
        stage.show();
    }

    public static void showEditar() {
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        grid.getStyleClass().add("grid-pane");

        TextField idField = new TextField();
        idField.setPromptText("ID do Ambiente ");
        TextField nomeField = new TextField();
        nomeField.setPromptText("Novo nome do Ambiente");
        nomeField.setEditable(false);
        TextField tempoField = new TextField();
        tempoField.setEditable(false);
        tempoField.setPromptText("Nova ociosidade (min)");
  
        Button buscar = new Button("Buscar");
        Button editar = new Button("Salvar Alterações");
        
        
        buscar.setOnAction(_ -> {
        		if (Validar.campoVazio(idField, "ID") || Validar.campoInvalido(idField, "ID", "inteiro")) return;
                int id = Integer.parseInt(idField.getText());
                Optional<Ambiente> opt = AmbienteController.findById(id);
                if (opt.isEmpty()) {
                    Validar.alert("Ambiente não encontrado.");
                    return;
                }

                Ambiente a = opt.get();
                nomeField.setText(a.getNome());
                tempoField.setText(String.valueOf(a.getTempoOciosidade()));
                
                idField.setEditable(false);
                nomeField.setEditable(true);
                tempoField.setEditable(true);
        });
        
        editar.setOnAction(_ -> {
            if (Validar.campoVazio(nomeField, "Nome")) return;
            if (Validar.campoVazio(tempoField, "Tempo de Ociosidade") || Validar.campoInvalido(tempoField, "Tempo de Ociosidade", "inteiro")) return;
        
            int id = Integer.parseInt(idField.getText());
            String nome = nomeField.getText();
            int tempo = Integer.parseInt(tempoField.getText());

            Optional<Ambiente> opt = AmbienteController.findById(id);
            if (opt.isEmpty()) {
                Validar.alert("Ambiente não encontrado para edição.");
                return;
            }
            
            Ambiente antigo = opt.get();
            Ambiente novo = new Ambiente(id, nome, tempo, antigo.getBloco());
            AmbienteController.save(novo);
            Validar.alert("Ambiente alterado com sucesso.");
                idField.clear();
                nomeField.clear();
                tempoField.clear(); 
                nomeField.setEditable(false);
                tempoField.setEditable(false);;
    	        idField.setEditable(true);
        });
        
        Button voltar = new Button("Voltar");
        voltar.setOnAction( _-> stage.close());
        
        grid.add(new Label("ID:"), 0, 0); grid.add(idField, 1, 0);
        grid.add(buscar, 2, 0);
        grid.add(new Label("Novo Nome:"), 0, 1); grid.add(nomeField, 1, 1);
        grid.add(new Label("Nova Ociosidade:"), 0, 2); grid.add(tempoField, 1, 2);
        grid.add(editar, 1, 4);
        grid.add(voltar, 2, 4);

        Scene scene = new Scene(grid, 400, 200);
        Estilo.apply(scene);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Editar Ambiente");
        stage.show();
    }

    public static void showExcluir() {
        Stage stage = new Stage();
        
        VBox vbox = new VBox(10);
        vbox.getStyleClass().add("vbox");       

        TextField idField = new TextField();
        idField.setPromptText("ID do Ambiente para excluir");
        
        Button excluir = new Button("Excluir");
        excluir.setOnAction(_ -> {
        	if (Validar.campoVazio(idField, "ID") || Validar.campoInvalido(idField, "ID", "inteiro")) return;
            int id = Integer.parseInt(idField.getText());
            Optional<Ambiente> ambiente = AmbienteController.findById(id);
            if (ambiente.isEmpty()) {
                Validar.alert("Ambiente não encontrado.");
                return;
            }
            if (!ambiente.get().getDispositivos().isEmpty() || !ambiente.get().getSensores().isEmpty()) {
                Validar.alert("Erro ao excluir, o Ambiente possui Dispositivos ou Sensores.");
                return;
            }
            boolean sucesso = AmbienteController.removeById(id);
            Validar.alert(sucesso ? "Ambiente excluído com sucesso!" : "Erro ao excluir o Ambiente.");
            idField.clear();
                
        });
        
        Button voltar = new Button("Voltar");
        voltar.setOnAction( _-> stage.close());
        
        HBox botoes = new HBox(10, excluir, voltar);
        botoes.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(new Label("ID do Ambiente para excluir:"), idField, botoes);
        Scene scene = new Scene(vbox, 350, 150);
        Estilo.apply(scene);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Excluir Ambiente");
        stage.show();
    }
}