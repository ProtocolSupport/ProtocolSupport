package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public abstract class MiddleEntityMetadata extends MiddleEntity {

	protected MiddleEntityMetadata(MiddlePacketInit init) {
		super(init);
	}

	protected final ArrayMap<NetworkEntityMetadataObject<?>> metadata = new ArrayMap<>(31);

	@Override
	protected void decode(ByteBuf serverdata) {
		super.decode(serverdata);
		NetworkEntityMetadataSerializer.readDataTo(serverdata, metadata);
	}

	@Override
	protected void cleanup() {
		metadata.clear();
	}

}
