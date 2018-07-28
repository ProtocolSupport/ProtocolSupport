//package protocolsupport.zplatform.impl.glowstone.injector.packets;
//
//import com.flowpowered.network.MessageHandler;
//
//import net.glowstone.net.GlowSession;
//import net.glowstone.net.message.status.StatusPingMessage;
//import protocolsupport.protocol.packet.handler.AbstractStatusListener;
//import protocolsupport.zplatform.impl.glowstone.network.GlowStoneNetworkManagerWrapper;
//
//public class GlowStoneStatusServerPingHandler implements MessageHandler<GlowSession, StatusPingMessage> {
//
//	@Override
//	public void handle(GlowSession session, StatusPingMessage msg) {
//		((AbstractStatusListener) GlowStoneNetworkManagerWrapper.getPacketListener(session)).handlePing(msg.getTime());
//	}
//
//}
