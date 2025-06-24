package sage.view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Menu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainMenu {

	public void adminMenu(Stage stage) {
		Image logo = new Image(getClass().getResourceAsStream("/sage.png"));
	    ImageView logoView = new ImageView(logo);
	    logoView.setFitHeight(500);
	    logoView.setPreserveRatio(true);
		
        BorderPane layout = new BorderPane();

        MenuBar menuBar = new MenuBar();
        menuBar.getStyleClass().add("menu-bar");
        Menu menuBloco = new Menu("Bloco"); 
        MenuItem cadBloco = new MenuItem("Cadastrar");
        cadBloco.setOnAction(_ -> BlocoMenu.showCadastro());
        MenuItem ediBloco = new MenuItem("Editar");
        ediBloco.setOnAction(_ -> BlocoMenu.showEditar());
        MenuItem excBloco = new MenuItem("Excluir");
        excBloco.setOnAction(_ -> BlocoMenu.showExcluir());
        menuBloco.getItems().addAll(cadBloco, ediBloco, excBloco);

        Menu menuAmbiente = new Menu("Ambiente"); 
        MenuItem cadAmbiente = new MenuItem("Cadastrar");
        cadAmbiente.setOnAction(_ -> AmbienteMenu.showCadastro());
        MenuItem ediAmbiente = new MenuItem("Editar");
        ediAmbiente.setOnAction(_ -> AmbienteMenu.showEditar());
        MenuItem excAmbiente = new MenuItem("Excluir");
        excAmbiente.setOnAction(_ -> AmbienteMenu.showExcluir());
        menuAmbiente.getItems().addAll(cadAmbiente, ediAmbiente, excAmbiente);
        
        Menu menuDispositivo = new Menu("Dispositivo"); 
        MenuItem cadDispositivo = new MenuItem("Cadastrar");
        cadDispositivo.setOnAction(_ -> DispositivoMenu.showCadastro());
        MenuItem ediDispositivo = new MenuItem("Editar");
        ediDispositivo.setOnAction(_ -> DispositivoMenu.showEditar());
        MenuItem excDispositivo = new MenuItem("Excluir");
        excDispositivo.setOnAction(_ -> DispositivoMenu.showExcluir());
        menuDispositivo.getItems().addAll(cadDispositivo, ediDispositivo, excDispositivo); 
        
        Menu menuSensor = new Menu("Sensor"); 
        MenuItem cadSensor = new MenuItem("Cadastrar");
        cadSensor.setOnAction(_ -> SensorMenu.showCadastro());
        MenuItem ediSensor = new MenuItem("Editar");
        ediSensor.setOnAction(_ -> SensorMenu.showEditar());
        MenuItem excSensor = new MenuItem("Excluir");
        excSensor.setOnAction(_ -> SensorMenu.showExcluir());
        menuSensor.getItems().addAll(cadSensor, ediSensor, excSensor); 
        
        Menu menuPainel = new Menu("Painel Solar"); 
        MenuItem cadPainel = new MenuItem("Cadastrar");
        cadPainel.setOnAction(_ -> PainelSolarMenu.showCadastro());
        MenuItem ediPainel = new MenuItem("Editar");
        ediPainel.setOnAction(_ -> PainelSolarMenu.showEditar());
        MenuItem excPainel = new MenuItem("Excluir");
        excPainel.setOnAction(_ -> PainelSolarMenu.showExcluir());
        menuPainel.getItems().addAll(cadPainel, ediPainel, excPainel);
        
        Menu menuUsuario = new Menu("Usuário"); 
        MenuItem cadUsuario = new MenuItem("Cadastrar");
        cadUsuario.setOnAction(_ -> UsuarioMenu.showCadastro());
        MenuItem ediUsuario = new MenuItem("Redefinir");
        ediUsuario.setOnAction(_ -> UsuarioMenu.showRedefinir());
        MenuItem excUsuario = new MenuItem("Excluir");
        excUsuario.setOnAction(_ -> UsuarioMenu.showExcluir());
        menuUsuario.getItems().addAll(cadUsuario, ediUsuario, excUsuario);
        
        Menu menuVisualizar = new Menu("Cadastros"); 
        MenuItem ambVisu = new MenuItem("Ambientes");
        ambVisu.setOnAction(_ -> VisualizarMenu.showAmbientes());
        MenuItem bloVisu = new MenuItem("Blocos");
        bloVisu.setOnAction(_ -> VisualizarMenu.showBlocos());
        MenuItem dispVisu = new MenuItem("Dispositivos");
        dispVisu.setOnAction(_ -> VisualizarMenu.showDispositivos());
        MenuItem senVisu = new MenuItem("Sensores");
        senVisu.setOnAction(_ -> VisualizarMenu.showSensores());
        MenuItem paiVisu = new MenuItem("Painéis Solares");
        paiVisu.setOnAction(_ -> VisualizarMenu.showPaineisSolares());
        MenuItem usuVisu = new MenuItem("Usuários");
        usuVisu.setOnAction(_ -> VisualizarMenu.showUsuarios());
        menuVisualizar.getItems().addAll(ambVisu, bloVisu, dispVisu, senVisu, paiVisu, usuVisu);
        
        Menu menuRelatorio = new Menu("Relátorios"); 
        MenuItem atual = new MenuItem("Consumo por Ambiente");
        atual.setOnAction(_ -> RelatorioMenu.showRelatorio());
        MenuItem geral = new MenuItem("Consumo Geral");
        geral.setOnAction(_ -> RelatorioMenu.showRelatorioGeral());
        
        menuRelatorio.getItems().addAll(atual, geral);

        Menu menuOutros = new Menu("Outros");
        MenuItem simulacao = new MenuItem("Simulação");
        simulacao.setOnAction(_ -> SimulacaoMenu.showSimulacao());
        MenuItem logout = new MenuItem("Logout");
        logout.setOnAction(_ -> {
        	stage.close();
            Stage login = new Stage();
            new Main().start(login);
        });
        
        menuOutros.getItems().addAll(simulacao, new SeparatorMenuItem(), logout);

        menuBar.getMenus().addAll(menuBloco, menuAmbiente, menuDispositivo, menuSensor, menuPainel, menuUsuario, menuVisualizar, menuRelatorio ,menuOutros);

        layout.setTop(menuBar);
        layout.setCenter(logoView);
 
        Scene scene = new Scene(layout);
        scene.getStylesheets().add(getClass().getResource("/sage/view/style.css").toExternalForm());
        stage.setTitle("SAGE - Menu Adminstrador");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setResizable(true);
        stage.show();
    }
	
	public void comumMenu(Stage stage) {
		Image logo = new Image(getClass().getResourceAsStream("/sage.png"));
	    ImageView logoView = new ImageView(logo);
	    logoView.setFitHeight(500);
	    logoView.setPreserveRatio(true);
		
        BorderPane layout = new BorderPane();

        MenuBar menuBar = new MenuBar();
        menuBar.getStyleClass().add("menu-bar");

        Menu menuVisualizar = new Menu("Cadastros"); 
        MenuItem ambVisu = new MenuItem("Ambientes");
        ambVisu.setOnAction(_ -> VisualizarMenu.showAmbientes());
        MenuItem bloVisu = new MenuItem("Blocos");
        bloVisu.setOnAction(_ -> VisualizarMenu.showBlocos());
        MenuItem dispVisu = new MenuItem("Dispositivos");
        dispVisu.setOnAction(_ -> VisualizarMenu.showDispositivos());
        MenuItem senVisu = new MenuItem("Sensores");
        senVisu.setOnAction(_ -> VisualizarMenu.showSensores());
        MenuItem paiVisu = new MenuItem("Painéis Solares");
        paiVisu.setOnAction(_ -> VisualizarMenu.showPaineisSolares());
        MenuItem usuVisu = new MenuItem("Usuários");
        usuVisu.setOnAction(_ -> VisualizarMenu.showUsuarios());
        menuVisualizar.getItems().addAll(ambVisu, bloVisu, dispVisu, senVisu, paiVisu, usuVisu);
        
        Menu menuRelatorio = new Menu("Relátorios"); 
        MenuItem atual = new MenuItem("Consumo por Ambiente");
        atual.setOnAction(_ -> RelatorioMenu.showRelatorio());
        MenuItem geral = new MenuItem("Consumo Geral");
        geral.setOnAction(_ -> RelatorioMenu.showRelatorioGeral());
        
        menuRelatorio.getItems().addAll(atual, geral);
        
        Menu menuOutros = new Menu("Outros");
        MenuItem logout = new MenuItem("Logout");
        logout.setOnAction(_ -> {
        	stage.close();
            Stage login = new Stage();
            new Main().start(login);
        });
        
        menuOutros.getItems().addAll(logout);
      
        menuBar.getMenus().addAll(menuVisualizar, menuRelatorio, menuOutros);

        layout.setTop(menuBar);
        layout.setCenter(logoView);
     
        Scene scene = new Scene(layout, 1000, 700);
        scene.getStylesheets().add(getClass().getResource("/sage/view/style.css").toExternalForm());
        stage.setTitle("SAGE - Menu");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setResizable(true);
        stage.show();
		
	}
}