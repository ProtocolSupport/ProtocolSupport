package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldEvent;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.itemstack.FlatteningItemId;
import protocolsupport.protocol.typeremapper.legacy.LegacyEffect;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.HashMapBasedIntMappingTable;

public class WorldEvent extends MiddleWorldEvent {

	protected final HashMapBasedIntMappingTable legacyEffectId = LegacyEffect.REGISTRY.getTable(version);
	protected final ArrayBasedIntMappingTable blockDataRemappingTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);
	protected final ArrayBasedIntMappingTable flatteningItemIdTable = FlatteningItemId.REGISTRY_TO_CLIENT.getTable(version);

	public WorldEvent(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		if (effectId == 2001) {
			data = blockDataRemappingTable.get(data);
		} else if ((effectId == 1010) && (data != 0)) {
			data = flatteningItemIdTable.get(data);
		}
		effectId = legacyEffectId.get(effectId);

		ClientBoundPacketData worldevent = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WORLD_EVENT);
		worldevent.writeInt(effectId);
		PositionSerializer.writePosition(worldevent, position);
		worldevent.writeInt(data);
		worldevent.writeBoolean(disableRelative);
		codec.writeClientbound(worldevent);
	}

}
