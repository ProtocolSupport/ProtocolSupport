//package protocolsupport.zplatform.impl.glowstone.injector.packets;
//
//import com.flowpowered.network.MessageHandler;
//
//import net.glowstone.net.GlowSession;
//import net.glowstone.net.message.login.LoginStartMessage;
//import protocolsupport.protocol.packet.handler.AbstractLoginListener;
//import protocolsupport.zplatform.impl.glowstone.network.GlowStoneNetworkManagerWrapper;
//
//public class GlowStoneLoginStartHandler implements MessageHandler<GlowSession, LoginStartMessage> {
//
//	@Override
//	public void handle(GlowSession session, LoginStartMessage msg) {
//		((AbstractLoginListener) GlowStoneNetworkManagerWrapper.getPacketListener(session)).handleLoginStart(msg.getUsername());
//	}
//
//}
