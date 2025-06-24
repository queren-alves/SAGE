package sage.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javafx.stage.Stage;
import sage.controller.UsuarioController;
import sage.model.Usuario;
import sage.util.Init;

public class Main extends Application{
	
    @Override
    public void start(Stage primaryStage) {
    	Init.init();
    	primaryStage.setTitle("SAGE - Sistema de Automação e Gerenciamento Energético");

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        
        Image logo = new Image(getClass().getResourceAsStream("/headersage.png"));
	    ImageView logoView = new ImageView(logo);
	    logoView.setFitHeight(200);
	    logoView.setPreserveRatio(true);
	    BorderPane layout = new BorderPane();
	    layout.setCenter(logoView);
	    
	    Label time = new Label("Data/Hora: "+  LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        time.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
        Label welcome = new Label("BEM VINDO(A)");
        welcome.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        Button login = new Button("Login");
        login.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        Button exit = new Button("Sair");
        exit.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        HBox botoes = new HBox(10, login, exit);
        botoes.setAlignment(Pos.CENTER);

        Label status = new Label();
        status.setStyle("-fx-text-fill: white;");
        
        login.setOnAction(_ -> showLoginForm(primaryStage));
        exit.setOnAction(_ -> {primaryStage.close();
    	});

        root.getChildren().addAll(time, layout, welcome, botoes);

        Scene scene = new Scene(root, 550, 400);
        scene.getStylesheets().add(getClass().getResource("/sage/view/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void showLoginForm(Stage stage) {
        VBox loginForm = new VBox(10);
        loginForm.getStyleClass().add("vbox");
        Image logo = new Image(getClass().getResourceAsStream("/headersage.png"));
	    ImageView logoView = new ImageView(logo);
	    logoView.setFitHeight(150);
	    logoView.setPreserveRatio(true);
	    BorderPane layout = new BorderPane();
	    layout.setCenter(logoView);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Usuário");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Senha");

        Button login = new Button("Entrar");
        login.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
        Button exit = new Button("Cancelar");
        exit.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
        Label status = new Label();
        
        HBox botoes = new HBox(10, login, exit);
        botoes.getStyleClass().add("hbox");
	
	        login.setOnAction(_ -> {
	            String user = usernameField.getText();
	            String pass = passwordField.getText();
	
	            Optional<Usuario> usuarioOpt = UsuarioController.findByUsernameAndSenha(user, pass);
	
	            if (usuarioOpt.isPresent()) {
	                Usuario usuario = usuarioOpt.get();
	                UsuarioMenu.setUsuarioLogado(usuario); 

	                MainMenu menu = new MainMenu();

	                if (usuario.isAdmin()) {
	                    menu.adminMenu(stage); 
	                } else {
	                    menu.comumMenu(stage);
	                }
	            } else {
	                status.setText("Usuário ou senha inválidos");
	                usernameField.clear();
	                passwordField.clear();
	            }
        }); 
	        exit.setOnAction(_ ->{ stage.close();
	        Stage start = new Stage();
	        new Main().start(start);
	        });

        Label loginLabel = new Label("Login");
        loginLabel.setStyle("-fx-font-weight: bold;");
        loginForm.getChildren().addAll(layout, loginLabel, usernameField, passwordField, botoes, status);

        Scene scene = new Scene(loginForm, 500, 350);
        loginForm.setStyle("-fx-background-color: #121212");
        scene.getStylesheets().add(getClass().getResource("/sage/view/style.css").toExternalForm());
        stage.setScene(scene);        
    }
		
	public static void main(String[] args) throws InterruptedException {	
		launch(args);
	}
}