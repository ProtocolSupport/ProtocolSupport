package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;

import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;

public class SpawnPlayerPacket implements ClientboundPEPacket {

	@Override
	public int getId() {
		return 0x88;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
