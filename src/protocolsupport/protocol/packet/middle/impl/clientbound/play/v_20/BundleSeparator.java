package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_20;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleBundleSeparator;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;

public class BundleSeparator extends MiddleBundleSeparator implements
IClientboundMiddlePacketV20 {

	public BundleSeparator(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		io.writeClientbound(ClientBoundPacketData.create(ClientBoundPacketType.PLAY_BUNDLE_SEPARATOR));
	}

}
