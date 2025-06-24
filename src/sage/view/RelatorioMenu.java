package sage.view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sage.model.RelatorioDiario;
import sage.service.RelatorioService;
import sage.util.Validar;

import java.time.LocalDate;
import java.util.List;

public class RelatorioMenu {

    private static final RelatorioService relatorioService = new RelatorioService();
    
    private static void ajustarTabela(TableView<?> table) {
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    }

    public static void showRelatorio() {
        Stage stage = new Stage();
        stage.setTitle("Relatórios de Consumo");

        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(10));

        HBox topArea = new HBox(10);
        topArea.setAlignment(Pos.CENTER);
        
        Button gerar = new Button("Gerar Relatório");
        
        TableView<RelatorioDiario> table = new TableView<>();
        TableColumn<RelatorioDiario, String> colAmbiente = new TableColumn<>("Ambiente");
        colAmbiente.setCellValueFactory(new PropertyValueFactory<>("ambienteNome"));
        TableColumn<RelatorioDiario, Integer> colMinutos = new TableColumn<>("Minutos Ligado");
        colMinutos.setCellValueFactory(new PropertyValueFactory<>("minutos"));
        TableColumn<RelatorioDiario, Double> colConsumo = new TableColumn<>("Consumo (kW)");
        colConsumo.setCellValueFactory(new PropertyValueFactory<>("consumo"));
        TableColumn<RelatorioDiario, Double> colGeracao = new TableColumn<>("Geração Solar (kW)");
        colGeracao.setCellValueFactory(new PropertyValueFactory<>("geracao"));
        TableColumn<RelatorioDiario, Double> colFinal = new TableColumn<>("Consumo Final (kW)");
        colFinal.setCellValueFactory(new PropertyValueFactory<>("consumoFinal"));
        TableColumn<RelatorioDiario, Double> colCustoSem = new TableColumn<>("Custo s/ Solar (R$)");
        colCustoSem.setCellValueFactory(new PropertyValueFactory<>("custoSemGeracao"));
        TableColumn<RelatorioDiario, Double> colCustoCom = new TableColumn<>("Custo c/ Solar (R$)");
        colCustoCom.setCellValueFactory(new PropertyValueFactory<>("custoComGeracao"));
        TableColumn<RelatorioDiario, Double> colEconomia = new TableColumn<>("Economia (R$)");
        colEconomia.setCellValueFactory(new PropertyValueFactory<>("economia"));
        
        Label placeholder = new Label("Não há registros para data selecionada.");
        placeholder.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        table.setPlaceholder(placeholder);

        table.getColumns().add(colAmbiente);
        table.getColumns().add(colMinutos);
        table.getColumns().add(colConsumo);
        table.getColumns().add(colGeracao);
        table.getColumns().add(colFinal);
        table.getColumns().add(colCustoSem);
        table.getColumns().add(colCustoCom);
        table.getColumns().add(colEconomia); 
        table.setVisible(false);
        table.setEditable(false);
        ajustarTabela(table);
        layout.setCenter(table);
        
        DatePicker datePicker = new DatePicker();
        
        Label title = new Label("Relatório de consumo por ambiente");
        title.setStyle("-fx-alignment: center; -fx-font-weight: bold");

        topArea.getChildren().addAll(title, datePicker, gerar);
        layout.setTop(topArea);
        
        gerar.setOnAction( _ -> { 
        	for (TableColumn<?, ?> column : table.getColumns()) {
        		column.setStyle("-fx-alignment: CENTER;");
            Text text = new Text(column.getText());
            double width = text.getLayoutBounds().getWidth() + 40;
            column.setMinWidth(width);
        	}
        	carregarRelatorio(datePicker.getValue(), table);
        	table.setVisible(true);
        });
     
        Scene scene = new Scene(layout, 1040, 450);
        Estilo.apply(scene);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    
    public static void showRelatorioGeral() {
    	Stage stage = new Stage();
        stage.setTitle("Relatórios de Consumo");
        GridPane grid = new GridPane();
        grid.setVisible(false);
        grid.setMaxWidth(Region.USE_PREF_SIZE);
        grid.setMaxHeight(Region.USE_PREF_SIZE);
        grid.getStyleClass().add("grid-pane");  

        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(10));
        HBox topArea = new HBox(10);
        topArea.setAlignment(Pos.TOP_CENTER);
        topArea.setMaxWidth(Region.USE_PREF_SIZE);
        BorderPane.setAlignment(topArea, Pos.TOP_CENTER);
        
        DatePicker datePicker = new DatePicker();
        Button gerar = new Button("Gerar Relatório");
        Label title = new Label("Relatório de consumo de todos ambientes");
        title.setStyle("-fx-alignment: center; -fx-font-weight: bold");
        
        gerar.setOnAction(_ -> {
        	grid.getChildren().clear();
            LocalDate data = datePicker.getValue();
            if (data == null) {
                Validar.alert("Selecione uma data válida no calendário.");
                return;
            }
            List<RelatorioDiario> relatorios = new RelatorioService().gerarRelatorioDiario(datePicker.getValue());
            
            if (relatorios.isEmpty()) {
                Label vazio = new Label("Nenhum registro encontrado para esta data.");
                vazio.getStyleClass().add("label-aviso"); 
                grid.add(vazio, 0, 0);
                return;
            }
            
            grid.setVisible(true);
            
            double consumoTotal = 0;
        	double geracaoTotal = 0;
        	double custoSemSolarTotal = 0;
        	double custoComSolarTotal = 0;	
        	double economia;
        	
        	for(RelatorioDiario r : relatorios) {
        		consumoTotal += r.getConsumo();
        		geracaoTotal += r.getGeracao();
        		custoSemSolarTotal += r.getCustoSemGeracao();
        		custoComSolarTotal += r.getCustoComGeracao();
        	}
        	
        	economia = custoSemSolarTotal - custoComSolarTotal;
            
            grid.add(new Label("Consumo total:"), 0, 0); grid.add(new Label (String.format("%.2f kW", consumoTotal)), 1, 0);
            grid.add(new Label("Geração total:"), 0, 1); grid.add(new Label (String.format("%.2f kW", geracaoTotal)), 1, 1);
            grid.add(new Label("Custo sem Solar:"), 0, 2); grid.add(new Label (String.format("R$ %.2f", custoSemSolarTotal)), 1, 2);
            grid.add(new Label("Custo com Solar:"), 0, 3); grid.add(new Label (String.format("R$ %.2f", custoComSolarTotal)), 1, 3);
            grid.add(new Label("Economia total:"), 0, 4); grid.add(new Label (String.format("R$ %.2f", economia)), 1, 4);
            
        });
        
        topArea.getChildren().addAll(title, datePicker, gerar);
        layout.setTop(topArea);
        layout.setCenter(grid);
        BorderPane.setAlignment(grid, Pos.CENTER);
        
        Scene scene = new Scene(layout, 700, 500);
        Estilo.apply(scene);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private static void carregarRelatorio(LocalDate data, TableView<RelatorioDiario> table) {
        List<RelatorioDiario> lista = relatorioService.gerarRelatorioDiario(data);
        table.setItems(FXCollections.observableArrayList(lista));
    }
}
