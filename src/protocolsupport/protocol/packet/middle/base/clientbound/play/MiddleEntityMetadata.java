package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.NetworkEntityMetadataCodec;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public abstract class MiddleEntityMetadata extends MiddleEntityData {

	protected MiddleEntityMetadata(IMiddlePacketInit init) {
		super(init);
	}

	protected final ArrayMap<NetworkEntityMetadataObject<?>> metadata = new ArrayMap<>(31);

	@Override
	protected void decodeData(ByteBuf serverdata) {
		NetworkEntityMetadataCodec.readDataTo(serverdata, metadata);
	}

	@Override
	protected void cleanup() {
		metadata.clear();
	}

}
