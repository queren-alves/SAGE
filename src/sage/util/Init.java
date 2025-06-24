package sage.util;

import java.time.LocalDateTime;

import sage.controller.AmbienteController;
import sage.controller.BlocoController;
import sage.controller.DispositivoController;
import sage.controller.PainelSolarController;
import sage.controller.SensorController;
import sage.controller.UsuarioController;
import sage.model.Ambiente;
import sage.model.Bloco;
import sage.model.Dispositivo;
import sage.model.EventoDesligamento;
import sage.model.PainelSolar;
import sage.model.Sensor;
import sage.model.Usuario;

public class Init {

	public static void init() {
		UsuarioController.persist(new Usuario("Administrador", "admin", true, "admin"));
		
		Bloco bloco = new Bloco(1, "Bloco A");
		BlocoController.persist(bloco);
		Ambiente ambiente = new Ambiente(1, "Sala 1", 1, bloco);
		AmbienteController.persist(ambiente);
		Dispositivo dispositivo = new Dispositivo(1, "AR-001", "Climatização", 14550, ambiente);
		DispositivoController.persist(dispositivo);
		ambiente.getRelatorios().add(new EventoDesligamento(LocalDateTime.now(),250, 45 ));
		Sensor sensor = new Sensor(1, "S-001", "Sensores PI", ambiente);
		SensorController.persist(sensor);
		PainelSolar painel = new PainelSolar(1, "P-001", 300);
		PainelSolarController.persist(painel);
		Ambiente ambiente2 = new Ambiente(2, "Sala 2", 2, bloco);
		ambiente2.getRelatorios().add(new EventoDesligamento(LocalDateTime.now(),400, 60 ));
		AmbienteController.persist(ambiente2);
		Dispositivo dispositivo2 = new Dispositivo(2, "AR-002", "Climatização", 12500, ambiente2);
		DispositivoController.persist(dispositivo2);
		Sensor sensor2 = new Sensor(2, "S-002", "Sensores PI", ambiente2);
		SensorController.persist(sensor2);	
		Bloco bloco2 = new Bloco(2, "Bloco B");
		BlocoController.persist(bloco2);
		Ambiente ambiente3 = new Ambiente(3, "Secretária", 2, bloco2);
		AmbienteController.persist(ambiente3);
		Dispositivo dispositivo4 = new Dispositivo(4, "L-001", "Iluminação", 12500, ambiente3);
		DispositivoController.persist(dispositivo4);
		ambiente3.getRelatorios().add(new EventoDesligamento(LocalDateTime.now(),250, 50 ));
		Sensor sensor3 = new Sensor(3, "S-001", "Sensores Ltda", ambiente3);
		SensorController.persist(sensor3);
		PainelSolar painel2 = new PainelSolar(2, "P-002", 300);
		PainelSolarController.persist(painel2);
		Ambiente ambiente4 = new Ambiente(4, "LAB C-4", 5, bloco2);
		ambiente4.getRelatorios().add(new EventoDesligamento(LocalDateTime.now(),400, 120 ));
		AmbienteController.persist(ambiente4);
		Dispositivo dispositivo3 = new Dispositivo(3, "AR-003", "Climatização", 14550, ambiente4);
		DispositivoController.persist(dispositivo3);
		Sensor sensor4 = new Sensor(4, "S-002", "Sensores Ltda", ambiente4);
		SensorController.persist(sensor4);
		
	}
	
	
}