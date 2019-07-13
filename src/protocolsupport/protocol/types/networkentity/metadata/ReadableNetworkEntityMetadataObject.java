package protocolsupport.protocol.types.networkentity.metadata;

import io.netty.buffer.ByteBuf;

public abstract class ReadableNetworkEntityMetadataObject<T> extends NetworkEntityMetadataObject<T> {

	public abstract void readFromStream(ByteBuf from);

}
