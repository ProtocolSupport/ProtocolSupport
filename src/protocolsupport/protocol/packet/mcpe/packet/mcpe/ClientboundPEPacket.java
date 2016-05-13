package protocolsupport.protocol.packet.mcpe.packet.mcpe;

import io.netty.buffer.ByteBuf;

public interface ClientboundPEPacket extends PEPacket {

	public ClientboundPEPacket encode(ByteBuf buf) throws Exception;

}
