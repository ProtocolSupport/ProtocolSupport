//package protocolsupport.zplatform.impl.glowstone.injector.packets;
//
//import com.flowpowered.network.MessageHandler;
//
//import net.glowstone.net.GlowSession;
//import net.glowstone.net.message.status.StatusRequestMessage;
//import protocolsupport.protocol.packet.handler.AbstractStatusListener;
//import protocolsupport.zplatform.impl.glowstone.network.GlowStoneNetworkManagerWrapper;
//
//public class GlowStoneStatusServerInfoRequestHandler implements MessageHandler<GlowSession, StatusRequestMessage> {
//
//	@Override
//	public void handle(GlowSession session, StatusRequestMessage msg) {
//		((AbstractStatusListener) GlowStoneNetworkManagerWrapper.getPacketListener(session)).handleStatusRequest();
//	}
//
//}
