package protocolsupport.zplatform.impl.glowstone.injector.packets;

import com.flowpowered.network.MessageHandler;

import net.glowstone.net.GlowSession;
import net.glowstone.net.message.handshake.HandshakeMessage;
import protocolsupport.protocol.packet.handler.AbstractHandshakeListener;
import protocolsupport.zplatform.impl.glowstone.network.GlowStoneNetworkManagerWrapper;
import protocolsupport.zplatform.network.NetworkState;

public class GlowStoneHandshakeStartPacketHandler implements MessageHandler<GlowSession, HandshakeMessage> {

	@Override
	public void handle(GlowSession session, HandshakeMessage msg) {
		AbstractHandshakeListener listener = (AbstractHandshakeListener) GlowStoneNetworkManagerWrapper.getPacketListener(session);
		listener.handleSetProtocol(NetworkState.byNetworkId(msg.getState()), msg.getAddress(), msg.getPort());
	}

}
