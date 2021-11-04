package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddleResourcePackStatus extends ServerBoundMiddlePacket {

	protected MiddleResourcePackStatus(IMiddlePacketInit init) {
		super(init);
	}

	protected int result;

	@Override
	protected void write() {
		ServerBoundPacketData resourcepackstatus = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_RESOURCE_PACK_STATUS);
		VarNumberCodec.writeVarInt(resourcepackstatus, result);
		io.writeServerbound(resourcepackstatus);
	}

}
