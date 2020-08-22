package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public abstract class MiddleEntityMetadata extends MiddleEntity {

	public MiddleEntityMetadata(MiddlePacketInit init) {
		super(init);
	}

	protected final ArrayMap<NetworkEntityMetadataObject<?>> metadata = new ArrayMap<>(31);

	@Override
	protected void readServerData(ByteBuf serverdata) {
		super.readServerData(serverdata);
		NetworkEntityMetadataSerializer.readDataTo(serverdata, metadata);
	}

	@Override
	protected void cleanup() {
		metadata.clear();
	}

}
