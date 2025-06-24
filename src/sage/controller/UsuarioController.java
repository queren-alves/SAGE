package sage.controller;

import java.util.List;
import java.util.Optional;
import sage.model.Usuario;
import sage.util.Security;

public class UsuarioController {

	/**
	 * Retorna todos os usuários armazenados no banco de dados.
	 *
	 * @return uma lista contendo todos os usuários armazenados.
	 */
	public static List<Usuario> findAll() {
		return Database.getUsuarios();
	}
	
	/**
	 * Salva as alterações no usuário.
	 *
	 * @param usuario o usuário existente com o parâmetro a ser alterado.
	 * @return {@code true} se o usuário foi alterado com sucesso;
	 *         {@code false} caso o usuário não exista no banco de dados.
	 */
	public static boolean save(Usuario usuario) {
		Optional<Usuario> usuarioOpt = findByUsername(usuario.getUsername());
		if (usuarioOpt.isEmpty())
			return false;
		usuarioOpt.get().setNome(usuario.getNome());
		return true;
	}

	/**
	 * Busca um usuário pelo username.
	 *
	 * @param username o identificador único do usuário.
	 * @return um {@code Optional} contendo o usuário encontrado, ou
	 *         {@code Optional.empty()} caso não seja encontrado.
	 */
	public static Optional<Usuario> findByUsername(String username) {
		return Database.getUsuarios().stream().filter(u -> u.getUsername().equals(username)).findFirst();
	}

	/**
	 * Persiste um usuário no banco de dados, adicionando-o à lista de usuários.
	 * 
	 * Verifica se já existe um usuário com o mesmo nome de usuário antes de
	 * adicioná-lo.
	 *
	 * @param usuario o usuário a ser persistido.
	 * @return {@code true} se o usuário foi adicionado com sucesso; {@code false}
	 *         caso já exista um usuário com o mesmo nome de usuário.
	 */
	public static boolean persist(Usuario usuario) {
		if (Database.getUsuarios().stream().anyMatch(u -> u.equals(usuario)))
			return false;
		usuario.setSenha(Security.hashInSHA256(usuario.getSenha()));
		return Database.getUsuarios().add(usuario);
	}

	/**
	 * Redefine uma senha de usuário.
	 *
	 * @param username o username do usuário.
	 * @param password a nova senha do usuário.
	 * @return {@code true} se a senha foi resetada com sucesso; {@code false} caso
	 *         o usuário não exista no banco de dados.
	 */
	public static boolean resetPasswordByUsername(String username, String password) {
		Optional<Usuario> user = findByUsername(username);
		if (user.isEmpty())
			return false;
		user.get().setSenha(Security.hashInSHA256(password));
		return true;
	}

	/**
	 * Remove um usuário do banco de dados.
	 *
	 * @param usuario o usuário a ser removido.
	 * @return {@code true} se o usuário foi removido com sucesso; {@code false}
	 *         caso o usuário não esteja presente no banco de dados.
	 */
	public static boolean remove(Usuario usuario) {
		return Database.getUsuarios().remove(usuario);
	}

	/**
	 * Remove um usuário do banco de dados.
	 *
	 * @param username o username do usuário a ser removido.
	 * @return {@code true} se o usuário foi removido com sucesso; {@code false}
	 *         caso o usuário não esteja presente no banco de dados.
	 */
	public static boolean removeByUsername(String username) {
		Optional<Usuario> usuarioOptional = findByUsername(username);
		if (usuarioOptional.isPresent())
			return remove(usuarioOptional.get());
		return false;
	}

	/**
	 * Busca um usuário pelo nome de usuário e senha.
	 *
	 * @param username o nome de usuário a ser buscado.
	 * @param senha    a senha do usuário a ser verificada.
	 * @return um {@code Optional} contendo o usuário encontrado, se o nome de
	 *         usuário e a senha coincidirem; ou {@code Optional.empty()} caso não
	 *         seja encontrado um usuário correspondente.
	 */
	public static Optional<Usuario> findByUsernameAndSenha(String username, String senha) {
		return Database.getUsuarios().stream()
				.filter(u -> u.getUsername().equals(username) && u.getSenha().equals(Security.hashInSHA256(senha)))
				.findFirst();
	}

}
