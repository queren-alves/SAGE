package sage.view;

import javafx.collections.FXCollections;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sage.controller.*;
import sage.model.*;
import sage.util.Validar;


public class VisualizarMenu {

    private static final int LARGURA_JANELA = 600;
    private static final int ALTURA_MINIMA = 200;
    
    private static void ajustarTabela(TableView<?> table) {
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    }

    private static int calcularAltura(TableView<?> table) {
        int linhas = table.getItems().size();
        return Math.max(ALTURA_MINIMA, linhas * 28 + 60); 
    }
    
    public static void showBlocos() {
        Stage stage = new Stage();
        TableView<Bloco> table = new TableView<>();
        table.setEditable(UsuarioMenu.getUsuarioLogado().isAdmin());
        table.getStyleClass().add("table-view");

        TableColumn<Bloco, Integer> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        id.setEditable(false); 
        id.setCellFactory(_ -> {
            TextFieldTableCell<Bloco, Integer> cell = new TextFieldTableCell<>(new IntegerStringConverter());
            cell.setStyle("-fx-alignment: CENTER;");
            return cell;
        });

        TableColumn<Bloco, String> nome = new TableColumn<>("Nome");
        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        nome.setCellFactory(TextFieldTableCell.forTableColumn());
        nome.setOnEditCommit(event -> {
            String novoNome = event.getNewValue().trim();
            Bloco bloco = event.getRowValue();

            if (novoNome.isEmpty()) {
                Validar.alert("O nome não pode estar em branco.");
                bloco.setNome(event.getOldValue());
                table.refresh();
            } else {
                bloco.setNome(novoNome);
                BlocoController.save(bloco);
            }
        });
        
        Label placeholder = new Label("Nenhum Bloco cadastrado.");
        placeholder.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        table.setPlaceholder(placeholder);

        table.getColumns().add(id);
        table.getColumns().add(nome);
      
        table.setItems(FXCollections.observableArrayList(BlocoController.findAll()));

        ajustarTabela(table);

        VBox root = new VBox(table);
        Scene scene = new Scene(root, LARGURA_JANELA, calcularAltura(table));
        Estilo.apply(scene);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Visualizar Blocos");
        stage.show();
    }

    public static void showAmbientes() {
    	Stage stage = new Stage();
        TableView<Ambiente> table = new TableView<>();
        table.setEditable(UsuarioMenu.getUsuarioLogado().isAdmin());
        table.getStyleClass().add("table-view");

        TableColumn<Ambiente, Integer> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        id.setEditable(false);
        id.setCellFactory(_ -> {
            TextFieldTableCell<Ambiente, Integer> cell = new TextFieldTableCell<>(new IntegerStringConverter());
            cell.setStyle("-fx-alignment: CENTER;");
            return cell;
        });

        TableColumn<Ambiente, String> nome = new TableColumn<>("Nome");
        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        nome.setCellFactory(TextFieldTableCell.forTableColumn());
        nome.setOnEditCommit(event -> {
            String novoNome = event.getNewValue().trim();
            Ambiente ambiente = event.getRowValue();

            if (novoNome.isEmpty()) {
                Validar.alert("O nome não pode estar em branco.");
                table.refresh();
            } else {
                ambiente.setNome(novoNome);
                AmbienteController.save(ambiente);
            }
        });

        TableColumn<Ambiente, Integer> blocoId = new TableColumn<>("ID do Bloco");
        blocoId.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getBloco().getId()));
        blocoId.setEditable(false);
        blocoId.setCellFactory(_ -> {
            TextFieldTableCell<Ambiente, Integer> cell = new TextFieldTableCell<>(new IntegerStringConverter());
            cell.setStyle("-fx-alignment: CENTER;");
            return cell;
        });

        TableColumn<Ambiente, Integer> tempo = new TableColumn<>("Tempo Ocioso (min)");
        tempo.setCellValueFactory(new PropertyValueFactory<>("tempoOciosidade"));
        tempo.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tempo.setCellFactory(_ -> {
            TextFieldTableCell<Ambiente, Integer> cell = new TextFieldTableCell<>(new IntegerStringConverter());
            cell.setStyle("-fx-alignment: CENTER;");
            return cell;
        });
        
        tempo.setOnEditCommit(event -> {
            Ambiente ambiente = event.getRowValue();
            Integer novoTempo = event.getNewValue();

            if (novoTempo == null || novoTempo < 0) {
                Validar.alert("Tempo Ocioso inválido.");
                table.refresh();
                return;
            }

            ambiente.setTempoOciosidade(novoTempo);
            AmbienteController.save(ambiente);
        });
        
        Label placeholder = new Label("Nenhum Ambiente cadastrado.");
        placeholder.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        table.setPlaceholder(placeholder);
        

        table.getColumns().add(id);
        table.getColumns().add(nome);
        table.getColumns().add(blocoId);
        table.getColumns().add(tempo);

        table.setItems(FXCollections.observableArrayList(AmbienteController.findAll()));

        ajustarTabela(table);

        VBox root = new VBox(table);
        Scene scene = new Scene(root, LARGURA_JANELA, calcularAltura(table));
        Estilo.apply(scene);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Visualizar Ambientes");
        stage.show();
    }

    public static void showUsuarios() {
        Stage stage = new Stage();
        TableView<Usuario> table = new TableView<>();
        table.setEditable(UsuarioMenu.getUsuarioLogado().isAdmin());
        table.getStyleClass().add("table-view");

        TableColumn<Usuario, String> nome = new TableColumn<>("Nome");
        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        nome.setCellFactory(TextFieldTableCell.forTableColumn());
        nome.setOnEditCommit(event -> {
            String novoNome = event.getNewValue().trim();
            Usuario usuario = event.getRowValue();

            if (novoNome.isEmpty()) {
                Validar.alert("O nome não pode estar em branco.");
                table.refresh();
            } else {
                usuario.setNome(novoNome);
                UsuarioController.save(usuario);
            }
        });

        TableColumn<Usuario, String> tipo = new TableColumn<>("Tipo");
        tipo.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createStringBinding(
                () -> cellData.getValue().isAdmin() ? "Admin" : "Comum"));
        tipo.setEditable(false);

        TableColumn<Usuario, String> user = new TableColumn<>("Username");
        user.setCellValueFactory(new PropertyValueFactory<>("username"));
        user.setEditable(false);
        
        Label placeholder = new Label("Nenhum Usuário cadastrado.");
        placeholder.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        table.setPlaceholder(placeholder);

        table.getColumns().add(nome);
        table.getColumns().add(tipo);
        table.getColumns().add(user);

        table.setItems(FXCollections.observableArrayList(UsuarioController.findAll()));

        ajustarTabela(table);

        VBox root = new VBox(table);
        Scene scene = new Scene(root, LARGURA_JANELA, calcularAltura(table));
        Estilo.apply(scene);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Visualizar Usuários");
        stage.show();
    }

    public static void showDispositivos() {
        Stage stage = new Stage();
        TableView<Dispositivo> table = new TableView<>();
        table.setEditable(UsuarioMenu.getUsuarioLogado().isAdmin());
        table.getStyleClass().add("table-view");

        TableColumn<Dispositivo, Integer> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        id.setEditable(false);
        id.setCellFactory(_ -> {
            TextFieldTableCell<Dispositivo, Integer> cell = new TextFieldTableCell<>(new IntegerStringConverter());
            cell.setStyle("-fx-alignment: CENTER;");
            return cell;
        });

        TableColumn<Dispositivo, String> nome = new TableColumn<>("Nome");
        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        nome.setCellFactory(TextFieldTableCell.forTableColumn());
        nome.setOnEditCommit(event -> {
            String novoNome = event.getNewValue().trim();
            Dispositivo d = event.getRowValue();

            if (novoNome.isEmpty()) {
                Validar.alert("O nome não pode estar em branco.");
                table.refresh();
            } else {
                d.setNome(novoNome);
                DispositivoController.save(d);
            }
        });

        TableColumn<Dispositivo, Integer> ambienteId = new TableColumn<>("ID do Ambiente");
        ambienteId.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAmbiente().getId()));
        ambienteId.setEditable(false);
        ambienteId.setCellFactory(_ -> {
            TextFieldTableCell<Dispositivo, Integer> cell = new TextFieldTableCell<>(new IntegerStringConverter());
            cell.setStyle("-fx-alignment: CENTER;");
            return cell;
        });

        TableColumn<Dispositivo, Double> consumo = new TableColumn<>("Consumo (W)");
        consumo.setCellValueFactory(new PropertyValueFactory<>("consumo"));
        consumo.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        consumo.setCellFactory(_ -> {
            TextFieldTableCell<Dispositivo, Double> cell = new TextFieldTableCell<>(new DoubleStringConverter());
            cell.setStyle("-fx-alignment: CENTER;");
            return cell;
        });
        consumo.setOnEditCommit(event -> {
            Dispositivo d = event.getRowValue();
            Double novo = event.getNewValue();

            if (novo == null || novo < 0) {
                Validar.alert("Consumo inválido.");
                table.refresh();
            } else {
                d.setConsumo(novo);
                DispositivoController.save(d);
            }
        });

        TableColumn<Dispositivo, String> tipo = new TableColumn<>("Tipo");
        tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        tipo.setCellFactory(TextFieldTableCell.forTableColumn());
        tipo.setOnEditCommit(event -> {
            String novoTipo = event.getNewValue().trim();
            Dispositivo d = event.getRowValue();

            if (novoTipo.isEmpty()) {
                Validar.alert("O tipo não pode estar em branco.");
                table.refresh();
            } else {
                d.setTipo(novoTipo);
                DispositivoController.save(d);
            }
        });

        Label placeholder = new Label("Nenhum Dispositivo cadastrado.");
        placeholder.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        table.setPlaceholder(placeholder);
        
        table.getColumns().add(id);
        table.getColumns().add(nome);
        table.getColumns().add(ambienteId);
        table.getColumns().add(consumo);
        table.getColumns().add(tipo);
        
        table.setItems(FXCollections.observableArrayList(DispositivoController.findAll()));

        ajustarTabela(table);

        VBox root = new VBox(table);
        Scene scene = new Scene(root, LARGURA_JANELA, calcularAltura(table));
        Estilo.apply(scene);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Visualizar Dispositivos");
        stage.show();
    }
 
    public static void showSensores() {
        Stage stage = new Stage();
        TableView<Sensor> table = new TableView<>();
        table.setEditable(UsuarioMenu.getUsuarioLogado().isAdmin());
        table.getStyleClass().add("table-view");

        TableColumn<Sensor, Integer> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        id.setEditable(false);
        id.setCellFactory(_ -> {
            TextFieldTableCell<Sensor, Integer> cell = new TextFieldTableCell<>(new IntegerStringConverter());
            cell.setStyle("-fx-alignment: CENTER;");
            return cell;
        });

        TableColumn<Sensor, String> nome = new TableColumn<>("Nome");
        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        nome.setCellFactory(TextFieldTableCell.forTableColumn());
        nome.setOnEditCommit(event -> {
            String novoNome = event.getNewValue().trim();
            Sensor sensor = event.getRowValue();

            if (novoNome.isEmpty()) {
                Validar.alert("O nome não pode estar em branco.");
                table.refresh();
            } else {
                sensor.setNome(novoNome);
                SensorController.save(sensor);
            }
        });

        TableColumn<Sensor, Integer> ambienteId = new TableColumn<>("ID do Ambiente");
        ambienteId.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAmbiente().getId()));
        ambienteId.setEditable(false);
        ambienteId.setCellFactory(_ -> {
            TextFieldTableCell<Sensor, Integer> cell = new TextFieldTableCell<>(new IntegerStringConverter());
            cell.setStyle("-fx-alignment: CENTER;");
            return cell;
        });

        TableColumn<Sensor, String> fabricante = new TableColumn<>("Fabricante");
        fabricante.setCellValueFactory(new PropertyValueFactory<>("fabricante"));
        fabricante.setCellFactory(TextFieldTableCell.forTableColumn());
        fabricante.setOnEditCommit(event -> {
            String novo = event.getNewValue().trim();
            Sensor sensor = event.getRowValue();

            if (novo.isEmpty()) {
                Validar.alert("O fabricante não pode estar em branco.");
                table.refresh();
            } else {
                sensor.setFabricante(novo);
                SensorController.save(sensor);
            }
        });
        
        Label placeholder = new Label("Nenhum Sensor cadastrado.");
        placeholder.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        table.setPlaceholder(placeholder);
        

        table.getColumns().add(id);
        table.getColumns().add(nome);
        table.getColumns().add(ambienteId);
        table.getColumns().add(fabricante);
      
        table.setItems(FXCollections.observableArrayList(SensorController.findAll()));

        ajustarTabela(table);

        VBox root = new VBox(table);
        Scene scene = new Scene(root, LARGURA_JANELA, calcularAltura(table));
        Estilo.apply(scene);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Visualizar Sensores");
        stage.show();
    }
    
    public static void showPaineisSolares() {
        Stage stage = new Stage();
        TableView<PainelSolar> table = new TableView<>();
        table.setEditable(UsuarioMenu.getUsuarioLogado().isAdmin());
        table.getStyleClass().add("table-view");

        TableColumn<PainelSolar, Integer> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        id.setEditable(false);
        id.setCellFactory(_ -> {
            TextFieldTableCell<PainelSolar, Integer> cell = new TextFieldTableCell<>(new IntegerStringConverter());
            cell.setStyle("-fx-alignment: CENTER;");
            return cell;
        });

        TableColumn<PainelSolar, String> nome = new TableColumn<>("Nome");
        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        nome.setCellFactory(TextFieldTableCell.forTableColumn());
        nome.setOnEditCommit(event -> {
            String novoNome = event.getNewValue().trim();
            PainelSolar painel = event.getRowValue();

            if (novoNome.isEmpty()) {
                Validar.alert("O nome não pode estar em branco.");
                table.refresh();
            } else {
                painel.setNome(novoNome);
                PainelSolarController.save(painel);
            }
        });

        TableColumn<PainelSolar, Double> geracao = new TableColumn<>("Geração (W)");
        geracao.setCellValueFactory(new PropertyValueFactory<>("geracao"));
        geracao.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        geracao.setCellFactory(_ -> {
            TextFieldTableCell<PainelSolar, Double> cell = new TextFieldTableCell<>(new DoubleStringConverter());
            cell.setStyle("-fx-alignment: CENTER;");
            return cell;
        });
        
        Label placeholder = new Label("Nenhum Painel Solar cadastrado.");
        placeholder.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        table.setPlaceholder(placeholder);
        
        geracao.setOnEditCommit(event -> {
            PainelSolar painel = event.getRowValue();
            Double novaGeracao = event.getNewValue();

            if (novaGeracao == null || novaGeracao < 0) {
                Validar.alert("Geração inválida.");
                table.refresh();
            } else {
                painel.setGeracao(novaGeracao);
                PainelSolarController.save(painel);
            }
        });

        table.getColumns().add(id);
        table.getColumns().add(nome);
        table.getColumns().add(geracao);
        table.setItems(FXCollections.observableArrayList(PainelSolarController.findAll()));

        ajustarTabela(table);

        VBox root = new VBox(table);
        Scene scene = new Scene(root, LARGURA_JANELA, calcularAltura(table));
        Estilo.apply(scene);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Visualizar Painéis Solares");
        stage.show();
    }
}
