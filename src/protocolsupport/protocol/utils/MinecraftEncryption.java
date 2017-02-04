package protocolsupport.protocol.utils;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class MinecraftEncryption {

	public static Cipher getCipher(int mode, SecretKey key) {
		try {
			Cipher instance = Cipher.getInstance("AES/CFB8/NoPadding");
			instance.init(mode, key, new IvParameterSpec(key.getEncoded()));
			return instance;
		} catch (GeneralSecurityException e) {
			throw new RuntimeException("Unable to get decrypter", e);
		}
	}

	public static byte[] createHash(final PublicKey publicKey, final SecretKey secretKey) {
		return createHash("SHA-1", new byte[][] { secretKey.getEncoded(), publicKey.getEncoded() });
	}

	private static byte[] createHash(final String hashAlgoName, final byte[]... data) {
		try {
			MessageDigest instance = MessageDigest.getInstance(hashAlgoName);
			for (int length = data.length, i = 0; i < length; ++i) {
				instance.update(data[i]);
			}
			return instance.digest();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Unable to create hash", e);
		}
	}

}
