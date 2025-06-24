package sage.util;

import java.security.MessageDigest;

public class Security {

	/**
	 * Gera o hash SHA-256 da string fornecida.
	 *
	 * @param string A string para a qual o hash será gerado.
	 * @return O hash em formato SHA-256 correspondente à string fornecida.
	 */
	public static String hashInSHA256(String string) {
		try {
			final MessageDigest digest = MessageDigest.getInstance("SHA-256");
			final byte[] hash = digest.digest(string.getBytes("UTF-8"));
			final StringBuilder hexString = new StringBuilder();
			for (int i = 0; i < hash.length; i++) {
				final String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}
