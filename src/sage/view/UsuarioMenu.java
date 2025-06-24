package sage.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sage.controller.UsuarioController;
import sage.model.Usuario;
import sage.util.Validar;


public class UsuarioMenu {
	
	 private static Usuario usuarioLogado;

	    public static void setUsuarioLogado(Usuario usuario) {
	        usuarioLogado = usuario;
	    }
	    
	    public static Usuario getUsuarioLogado() {
	        return usuarioLogado;
	    }

    public static void showCadastro() {
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        grid.getStyleClass().add("grid-pane");

        TextField nomeField = new TextField();
        nomeField.setPromptText("Nome do Usuário");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField senhaField = new PasswordField();
        senhaField.setPromptText("Senha");
        ComboBox<String> tipoBox = new ComboBox<>();
        tipoBox.getStyleClass().add("combo-box");
        tipoBox.getItems().addAll("admin", "comum");
        tipoBox.setPromptText("Tipo");

        Button cadastrar = new Button("Cadastrar");
        cadastrar.setOnAction(_ -> {
            if (Validar.campoVazio(nomeField, "Nome") || Validar.campoVazio(usernameField, "Username") ||
                Validar.campoVazio(senhaField, "Senha")) return;
            if (tipoBox.getValue() == null) {
        	Validar.alert("Selecione o tipo do Usuário.");
        	return;
        	}

            String nome = nomeField.getText();
            String username = usernameField.getText();
            String senha = senhaField.getText();
            boolean isAdmin = tipoBox.getValue().equalsIgnoreCase("admin");

            boolean sucesso = UsuarioController.persist(new Usuario(nome, username, isAdmin, senha));
            Validar.alert(sucesso ? "Usuário cadastrado com sucesso!" : "Erro ao cadastrar o Usuário.");
            if (sucesso) stage.close();
        });
        
        Button cancelar = new Button("Voltar");
        cancelar.setOnAction(_ -> stage.close());
        
        HBox botoes = new HBox(10, cadastrar, cancelar);
        botoes.setAlignment(Pos.CENTER);

        grid.add(new Label("Nome:"), 0, 0); grid.add(nomeField, 1, 0);
        grid.add(new Label("Username:"), 0, 1); grid.add(usernameField, 1, 1);
        grid.add(new Label("Senha:"), 0, 2); grid.add(senhaField, 1, 2);
        grid.add(new Label("Tipo:"), 0, 3); grid.add(tipoBox, 1, 3);
        grid.add(botoes, 1, 5);

        Scene scene = new Scene(grid, 320, 230);
        Estilo.apply(scene);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Cadastrar Usuário");
        stage.show();
    }

    public static void showRedefinir() {
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        grid.getStyleClass().add("grid-pane");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField senhaField = new PasswordField();
        senhaField.setPromptText("Nova Senha");

        Button redefinir = new Button("Redefinir");
        redefinir.setOnAction(_ -> {
            if (Validar.campoVazio(usernameField, "Username") || Validar.campoVazio(senhaField, "Senha")) return;
            String username = usernameField.getText();
            String senha = senhaField.getText();

            boolean sucesso = UsuarioController.resetPasswordByUsername(username, senha);
            Validar.alert(sucesso ? "Senha redefinida com sucesso!" : "Erro ao redefinir a senha.");
            if (sucesso) 
            usernameField.clear();
            senhaField.clear();
        });

        Button cancelar = new Button("Voltar");
        cancelar.setOnAction(_ -> stage.close());
        
        HBox botoes = new HBox(10, redefinir, cancelar);
        botoes.setAlignment(Pos.CENTER);
        
        grid.add(new Label("Username:"), 0, 0); grid.add(usernameField, 1, 0);
        grid.add(new Label("Nova Senha:"), 0, 1); grid.add(senhaField, 1, 1);
        grid.add(botoes, 1, 3);

        Scene scene = new Scene(grid, 330, 150);
        Estilo.apply(scene);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Redefinir Senha");
        stage.show();
    }
    
    public static void showExcluir() {
        Stage stage = new Stage();
        VBox layout = new VBox(10);
        layout.getStyleClass().add("vbox"); 

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        Button excluir = new Button("Excluir");
        excluir.setOnAction(_ -> {
            if (Validar.campoVazio(usernameField, "Username")) return;
            String username = usernameField.getText();

            if (usuarioLogado != null && usuarioLogado.getUsername().equals(username)) {
                Validar.alert("Você não pode excluir o usuário atualmente logado.");
                return;
            }

            boolean sucesso = UsuarioController.removeByUsername(username);
            Validar.alert(sucesso ? "Usuário excluído com sucesso!" : "Erro ao excluir o Usuário.");
            if (sucesso) 
            stage.close();
        });
        
        Button cancelar = new Button("Voltar");
        cancelar.setOnAction(_ -> stage.close());
        
        HBox botoes = new HBox(10, excluir, cancelar);
        botoes.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(new Label("Username do Usuário para excluir:"), usernameField, botoes);

        Scene scene = new Scene(layout, 350, 150);
        Estilo.apply(scene);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Excluir Usuário");
        stage.show();
    }
} 
