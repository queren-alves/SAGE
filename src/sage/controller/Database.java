package sage.controller;

import java.util.ArrayList;
import java.util.List;

import sage.model.Ambiente;
import sage.model.Bloco;
import sage.model.Dispositivo;
import sage.model.PainelSolar;
import sage.model.Sensor;
import sage.model.Usuario;

public class Database {

	private static List<Bloco> blocos = new ArrayList<Bloco>();
	private static List<Ambiente> ambientes = new ArrayList<Ambiente>();
	private static List<Dispositivo> dispositivos = new ArrayList<Dispositivo>();
	private static List<Sensor> sensores = new ArrayList<Sensor>();
	private static List<Usuario> usuarios = new ArrayList<Usuario>();
	private static List<PainelSolar> paineisSolares = new ArrayList<PainelSolar>();

	public static List<Bloco> getBlocos() {
		return blocos;
	}

	public static void setBlocos(List<Bloco> blocos) {
		Database.blocos = blocos;
	}

	public static List<Usuario> getUsuarios() {
		return usuarios;
	}

	public static void setUsuarios(List<Usuario> usuarios) {
		Database.usuarios = usuarios;
	}

	public static List<PainelSolar> getPaineisSolares() {
		return paineisSolares;
	}

	public static void setPaineisSolares(List<PainelSolar> paineisSolares) {
		Database.paineisSolares = paineisSolares;
	}

	public static List<Ambiente> getAmbientes() {
		return ambientes;
	}

	public static void setAmbientes(List<Ambiente> ambientes) {
		Database.ambientes = ambientes;
	}

	public static List<Dispositivo> getDispositivos() {
		return dispositivos;
	}

	public static void setDispositivos(List<Dispositivo> dispositivos) {
		Database.dispositivos = dispositivos;
	}

	public static List<Sensor> getSensores() {
		return sensores;
	}

	public static void setSensores(List<Sensor> sensores) {
		Database.sensores = sensores;
	}

}
