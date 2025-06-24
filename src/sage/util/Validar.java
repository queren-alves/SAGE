package sage.util;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sage.view.Estilo;

public class Validar {

    public static boolean campoVazio(TextField campo, String nomeCampo) {
        if (campo.getText().trim().isEmpty()) {
            alert("O campo \"" + nomeCampo + "\" não pode estar vazio.");
            return true;
        }
        return false;
    }

    public static boolean campoInvalido(TextField campo, String nome, String tipo) {
        String texto = campo.getText().trim();
        try {
            switch (tipo.toLowerCase()) {
                case "inteiro":
                    Integer.parseInt(texto);
                    break;
                case "real":
                    Double.parseDouble(texto);
                    break;
                default:
                    alert("Tipo inválido");
            }
            return false;
        } catch (NumberFormatException e) {
           alert("O campo \"" + nome + "\" deve conter um valor do tipo " + tipo + ".");
            return true;
        }
    }
    
    public static void alert(String mensagem) {
       show(mensagem);
    }
    
    public static void show(String mensagem) {
    	Stage popup = new Stage(StageStyle.UNDECORATED);        
        Label msg = new Label(mensagem);
        msg.getStyleClass().add("popup-label");
        Button ok = new Button("OK");
        ok.setOnAction(_ -> popup.close());
        ok.getStyleClass().add("popup-button");
        
        VBox layout = new VBox(15, msg, ok);
        layout.setAlignment(Pos.CENTER);
        layout.getStyleClass().add("popup-layout");
        
        Scene scene = new Scene(layout);
        scene.setFill(Color.TRANSPARENT);
        Estilo.apply(scene);
        popup.setScene(scene);
        popup.initStyle(StageStyle.TRANSPARENT); 
        scene.setFill(Color.TRANSPARENT);
        layout.setStyle("-fx-background-color: #1f1f1f; -fx-background-radius: 10;");
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.showAndWait();
    	}
   
    
    public static boolean intInvalido(String texto, String nomeCampo) {
        try {
            Integer.parseInt(texto.trim());
            return false;
        } catch (NumberFormatException e) {
            alert("O campo \"" + nomeCampo + "\" deve conter um número inteiro válido.");
            return true;
        }
    }

}

