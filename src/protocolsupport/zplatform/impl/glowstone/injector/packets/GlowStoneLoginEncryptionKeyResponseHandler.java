//package protocolsupport.zplatform.impl.glowstone.injector.packets;
//
//import java.security.PrivateKey;
//
//import javax.crypto.Cipher;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//
//import com.flowpowered.network.MessageHandler;
//
//import net.glowstone.net.GlowSession;
//import net.glowstone.net.message.login.EncryptionKeyResponseMessage;
//import protocolsupport.protocol.packet.handler.AbstractLoginListener;
//import protocolsupport.protocol.packet.handler.AbstractLoginListener.EncryptionPacketWrapper;
//import protocolsupport.zplatform.impl.glowstone.network.GlowStoneNetworkManagerWrapper;
//
//public class GlowStoneLoginEncryptionKeyResponseHandler implements MessageHandler<GlowSession, EncryptionKeyResponseMessage> {
//
//	@Override
//	public void handle(GlowSession session, EncryptionKeyResponseMessage msg) {
//		((AbstractLoginListener) GlowStoneNetworkManagerWrapper.getPacketListener(session)).handleEncryption(new EncryptionPacketWrapper() {
//			@Override
//			public SecretKey getSecretKey(PrivateKey key) {
//				try {
//					Cipher rsaCipher = Cipher.getInstance("RSA");
//					rsaCipher.init(Cipher.DECRYPT_MODE, key);
//					return new SecretKeySpec(rsaCipher.doFinal(msg.getSharedSecret()), "AES");
//				} catch (Exception e) {
//					throw new RuntimeException("Unable to decrypt key", e);
//				}
//			}
//
//			@Override
//			public byte[] getNonce(PrivateKey key) {
//				try {
//					Cipher rsaCipher = Cipher.getInstance("RSA");
//					rsaCipher.init(Cipher.DECRYPT_MODE, key);
//					return rsaCipher.doFinal(msg.getVerifyToken());
//				} catch (Exception e) {
//					throw new RuntimeException("Unable to decrypt key", e);
//				}
//			}
//		});
//	}
//
//}
