package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;

public class SetTimePacket implements ClientboundPEPacket {

	@Override
	public int getId() {
		return PEPacketIDs.SET_TIME_PACKET;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		//no time update for now
		buf.writeInt(20 * 60);
		buf.writeBoolean(false);
		return this;
	}

}
