package sage.model;

import java.util.Objects;

public class Usuario {

	private String nome;
	private String username;
	private String senha;
	private boolean admin;

	/**
	 * Inicializa uma nova instância de {@code Usuario}.
	 * 
	 * @param nome     o nome real do usuário
	 * @param username o nome que será usado como identificação no login.
	 * @param senha    a senha do usuário.
	 */
	public Usuario(String nome, String username, boolean admin, String senha) {
		this.nome = nome;
		this.username = username;
		this.senha = senha;
		this.setAdmin(admin);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Override
	public int hashCode() {
		return Objects.hash(username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(username, other.username);
	}

}