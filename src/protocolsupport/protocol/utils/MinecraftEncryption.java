package protocolsupport.protocol.utils;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class MinecraftEncryption {

	private MinecraftEncryption() {
	}

	public static Cipher getCipher(int mode, @Nonnull SecretKey key) {
		try {
			Cipher instance = Cipher.getInstance("AES/CFB8/NoPadding");
			instance.init(mode, key, new IvParameterSpec(key.getEncoded()));
			return instance;
		} catch (GeneralSecurityException e) {
			throw new UnchekedGeneralSecurityException("Unable to create cipher", e);
		}
	}

	public static @Nonnull byte[] createHash(@Nonnull PublicKey publicKey, @Nonnull SecretKey secretKey) {
		return createHash("SHA-1", secretKey.getEncoded(), publicKey.getEncoded());
	}

	private static @Nonnull byte[] createHash(@Nonnull String hashAlgoName, @Nonnull byte[]... data) {
		try {
			MessageDigest instance = MessageDigest.getInstance(hashAlgoName);
			for (int length = data.length, i = 0; i < length; ++i) {
				instance.update(data[i]);
			}
			return instance.digest();
		} catch (NoSuchAlgorithmException e) {
			throw new UnchekedGeneralSecurityException("Unable to create hash", e);
		}
	}

	public static class UnchekedGeneralSecurityException extends RuntimeException {

		private static final long serialVersionUID = 898550183386694736L;

		public UnchekedGeneralSecurityException(@Nonnull String message, @Nullable GeneralSecurityException e) {
			super(message, e);
		}

	}

}
