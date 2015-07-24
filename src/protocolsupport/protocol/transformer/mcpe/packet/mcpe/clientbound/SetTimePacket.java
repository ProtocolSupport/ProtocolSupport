package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;

import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;

public class SetTimePacket implements ClientboundPEPacket {

	@Override
	public int getId() {
		return 0x86;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		//no time update for now
		buf.writeInt(20 * 60);
		buf.writeByte((byte) 0x00);
		return this;
	}

}
