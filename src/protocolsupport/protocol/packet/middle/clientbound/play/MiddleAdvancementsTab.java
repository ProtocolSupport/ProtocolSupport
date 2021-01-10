package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;

public abstract class MiddleAdvancementsTab extends ClientBoundMiddlePacket {

	public MiddleAdvancementsTab(MiddlePacketInit init) {
		super(init);
	}

	protected String identifier;

	@Override
	protected void decode(ByteBuf serverdata) {
		if (serverdata.readBoolean()) {
			identifier = StringSerializer.readVarIntUTF8String(serverdata);
		} else {
			identifier = null;
		}
	}

}
