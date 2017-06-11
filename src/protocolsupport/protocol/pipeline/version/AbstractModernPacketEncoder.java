package protocolsupport.protocol.pipeline.version;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.Connection;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.NetworkDataCache;

public abstract class AbstractModernPacketEncoder extends AbstractPacketEncoder {

	public AbstractModernPacketEncoder(Connection connection, NetworkDataCache storage) {
		super(connection, storage);
	}

	@Override
	protected void writePacketId(ByteBuf to, int packetId) {
		VarNumberSerializer.writeVarInt(to, packetId);
	}

}
