package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;

//this packet isn't actually sent and is used to do some player actions after packet sending
public abstract class MethodCallPacket implements ClientboundPEPacket {

	@Override
	public int getId() {
		return -1;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		return this;
	}

	protected abstract void call();

}
