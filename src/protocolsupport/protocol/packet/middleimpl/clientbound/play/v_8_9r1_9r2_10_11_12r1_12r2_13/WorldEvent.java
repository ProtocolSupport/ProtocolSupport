package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldEvent;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;
import protocolsupport.protocol.typeremapper.legacy.LegacyEffect;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.HashMapBasedIdRemappingTable;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class WorldEvent extends MiddleWorldEvent {

	protected final HashMapBasedIdRemappingTable legacyEffectId = LegacyEffect.REGISTRY.getTable(version);
	protected final ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);

	public WorldEvent(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		if (effectId == 2001) {
			data = blockDataRemappingTable.getRemap(data);
			if (version.isBefore(ProtocolVersion.MINECRAFT_1_13)) {
				data = PreFlatteningBlockIdData.convertCombinedIdToM12(PreFlatteningBlockIdData.getCombinedId(data));
			}
		}
		effectId = legacyEffectId.getRemap(effectId);
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_WORLD_EVENT_ID);
		serializer.writeInt(effectId);
		PositionSerializer.writeLegacyPositionL(serializer, position);
		serializer.writeInt(data);
		serializer.writeBoolean(disableRelative);
		return RecyclableSingletonList.create(serializer);
	}

}
