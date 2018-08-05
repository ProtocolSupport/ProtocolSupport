package protocolsupport.protocol.pipeline.version.util.encoder;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class AbstractModernPacketEncoder extends AbstractPacketEncoder {

	public AbstractModernPacketEncoder(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writePacketId(ByteBuf to, int packetId) {
		VarNumberSerializer.writeVarInt(to, packetId);
	}

}
