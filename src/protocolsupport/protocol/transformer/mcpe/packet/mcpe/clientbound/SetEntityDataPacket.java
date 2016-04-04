package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import gnu.trove.map.TIntObjectMap;
import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.utils.PEDataWatcherSerializer;
import protocolsupport.utils.datawatcher.DataWatcherObject;

public class SetEntityDataPacket implements ClientboundPEPacket {

	protected int entityId;
	protected TIntObjectMap<DataWatcherObject<?>> meta;

	public SetEntityDataPacket(int entityId, TIntObjectMap<DataWatcherObject<?>> meta) {
		this.entityId = entityId;
		this.meta = meta;
	}

	@Override
	public int getId() {
		return PEPacketIDs.SET_ENTITY_DATA_PACKET;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		buf.writeLong(entityId);
		buf.writeBytes(PEDataWatcherSerializer.encode(meta));
		return this;
	}

}
