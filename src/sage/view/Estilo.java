package sage.view;

import javafx.scene.Scene;

	public class Estilo {
		
	    public static void apply(Scene scene) {
	        scene.getStylesheets().add(
	            Estilo.class.getResource("/sage/view/style.css").toExternalForm()
	        );
	    }
	}
