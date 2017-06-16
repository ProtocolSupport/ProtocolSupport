package protocolsupport.protocol.pipeline.version;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.Connection;
import protocolsupport.protocol.storage.NetworkDataCache;

public abstract class AbstractLegacyPacketEncoder extends AbstractPacketEncoder {

	public AbstractLegacyPacketEncoder(Connection connection, NetworkDataCache storage) {
		super(connection, storage);
	}

	@Override
	protected void writePacketId(ByteBuf to, int packetId) {
		to.writeByte(packetId);
	}

}
