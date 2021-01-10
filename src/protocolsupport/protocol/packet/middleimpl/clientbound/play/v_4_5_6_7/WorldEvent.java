package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldEvent;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;
import protocolsupport.protocol.typeremapper.itemstack.PreFlatteningItemIdData;
import protocolsupport.protocol.typeremapper.legacy.LegacyEffect;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.HashMapBasedIntMappingTable;

public class WorldEvent extends MiddleWorldEvent {

	protected final HashMapBasedIntMappingTable legacyEffectId = LegacyEffect.REGISTRY.getTable(version);
	protected final ArrayBasedIntMappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);

	public WorldEvent(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		if (effectId == 2001) {
			data = PreFlatteningBlockIdData.getIdFromCombinedId(BlockRemappingHelper.remapPreFlatteningBlockDataNormal(blockDataRemappingTable, data));
		} else if ((effectId == 1010) && (data != 0)) {
			data = PreFlatteningItemIdData.getIdFromLegacyCombinedId(PreFlatteningItemIdData.getLegacyCombinedIdByModernId(data));
		}
		effectId = legacyEffectId.get(effectId);

		ClientBoundPacketData worldevent = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WORLD_EVENT);
		worldevent.writeInt(effectId);
		PositionSerializer.writeLegacyPositionB(worldevent, position);
		worldevent.writeInt(data);
		worldevent.writeBoolean(disableRelative);
		codec.writeClientbound(worldevent);
	}

}
