//package protocolsupport.zplatform.impl.glowstone.injector.packets;
//
//import java.text.MessageFormat;
//
//import com.flowpowered.network.MessageHandler;
//
//import net.glowstone.net.GlowSession;
//import net.glowstone.net.message.handshake.HandshakeMessage;
//import protocolsupport.api.utils.NetworkState;
//import protocolsupport.protocol.packet.handler.AbstractHandshakeListener;
//import protocolsupport.zplatform.impl.glowstone.network.GlowStoneNetworkManagerWrapper;
//
//public class GlowStoneHandshakeStartPacketHandler implements MessageHandler<GlowSession, HandshakeMessage> {
//
//	@Override
//	public void handle(GlowSession session, HandshakeMessage msg) {
//		AbstractHandshakeListener listener = (AbstractHandshakeListener) GlowStoneNetworkManagerWrapper.getPacketListener(session);
//		listener.handleSetProtocol(networkStatebyNetworkId(msg.getState()), msg.getAddress(), msg.getPort());
//	}
//
//	private static NetworkState networkStatebyNetworkId(int id) {
//		switch (id) {
//			case 0: {
//				return NetworkState.HANDSHAKING;
//			}
//			case 1: {
//				return NetworkState.STATUS;
//			}
//			case 2: {
//				return NetworkState.LOGIN;
//			}
//			case 3: {
//				return NetworkState.PLAY;
//			}
//			default: {
//				throw new IllegalArgumentException(MessageFormat.format("Unknown network state id {0}", id));
//			}
//		}
//	}
//
//}
