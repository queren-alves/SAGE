package sage.view;

import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sage.simulation.AmbienteSimulation;
import sage.simulation.DispositivoSimulation;
import sage.simulation.SensorSimulation;
import sage.util.Validar;

public class SimulacaoMenu {

    public static void showSimulacao() {
        Stage stage = new Stage();
        VBox layout = new VBox(10);
        layout.getStyleClass().add("vbox");
        
        TextArea textArea = new TextArea();
        textArea.getStyleClass().add("text-area");
        textArea.setEditable(false);
        textArea.setStyle("-fx-control-inner-background: #2b2b2b; -fx-text-fill: white;");
        
        TextField ciclosField = new TextField();
        ciclosField.setPromptText("Quantidade de ciclos");

        Button iniciar = new Button("Iniciar Simulação");
        iniciar.setOnAction(_ -> {
            try {
                int ciclos = Integer.parseInt(ciclosField.getText());
                textArea.clear();
                AmbienteSimulation ambiente = new AmbienteSimulation(); 
                Consumer<String> saida = msg -> Platform.runLater(() -> textArea.appendText(msg + "\n"));
                ambiente.setOutput(saida);
        	    DispositivoSimulation luzSala1 = new DispositivoSimulation("Iluminação - Sala 1", 10, "Iluminação");
        	    DispositivoSimulation arSala2 = new DispositivoSimulation("Ar condicionado - Sala 2", 750, "Climatização");
        	    SensorSimulation sensorSala1 = new SensorSimulation("Sala 1", luzSala1);
        	    SensorSimulation sensorSala2 = new SensorSimulation("Sala 2", arSala2);
        	    
        	    luzSala1.setOutput(saida);
        	    arSala2.setOutput(saida); 
        	    ambiente.setOutput(msg -> Platform.runLater(() -> textArea.appendText(msg + "\n")));
        	    ambiente.addSensor(sensorSala1);
        	    ambiente.addSensor(sensorSala2);
                	   
                Thread thread = new Thread(() -> {
                    try {
                        ambiente.runCycles(ciclos);
                        Platform.runLater(() -> {
                            Validar.alert("A simulação foi concluída com sucesso!");
                            textArea.clear();
                            ciclosField.clear();
                        });
                    } catch (InterruptedException e) {
                        Platform.runLater(() -> {
                        	Validar.alert("Insira um número válido.");
                        });
                    }
                });
                
                thread.setDaemon(true);
                thread.start();
            } catch (NumberFormatException e) {
            	Validar.alert("Insira um número válido.");
            }
        });

        layout.getChildren().addAll(new Label("Digite a quantidade de ciclos que deseja simular:"), ciclosField, iniciar, textArea);

        Scene scene = new Scene(layout, 600, 400);
        Estilo.apply(scene);
        stage.setScene(scene);
        stage.setTitle("Simulação do Funcionamento dos Sensores");
        stage.show();
    }
}
