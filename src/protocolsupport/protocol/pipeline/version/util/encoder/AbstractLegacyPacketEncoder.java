package protocolsupport.protocol.pipeline.version.util.encoder;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;

public abstract class AbstractLegacyPacketEncoder extends AbstractPacketEncoder {

	public AbstractLegacyPacketEncoder(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writePacketId(ByteBuf to, int packetId) {
		to.writeByte(packetId);
	}

}
