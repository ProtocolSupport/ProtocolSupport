package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldEvent;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;
import protocolsupport.protocol.typeremapper.itemstack.FlatteningItemId;
import protocolsupport.protocol.typeremapper.itemstack.PreFlatteningItemIdData;
import protocolsupport.protocol.typeremapper.legacy.LegacyEffect;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.HashMapBasedIntMappingTable;

public class WorldEvent extends MiddleWorldEvent {

	protected final HashMapBasedIntMappingTable legacyEffectId = LegacyEffect.REGISTRY.getTable(version);
	protected final ArrayBasedIntMappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
	protected final ArrayBasedIntMappingTable flatteningItemIdTable = FlatteningItemId.REGISTRY_TO_CLIENT.getTable(version);

	public WorldEvent(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		if (effectId == 2001) {
			data = blockDataRemappingTable.get(data);
			if (version.isBefore(ProtocolVersion.MINECRAFT_1_13)) {
				data = PreFlatteningBlockIdData.convertCombinedIdToM12(PreFlatteningBlockIdData.getCombinedId(data));
			}
		} else if ((effectId == 1010) && (data != 0)) {
			if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_13)) {
				data = flatteningItemIdTable.get(data);
			} else {
				data = PreFlatteningItemIdData.getIdFromLegacyCombinedId(PreFlatteningItemIdData.getLegacyCombinedIdByModernId(data));
			}
		}
		effectId = legacyEffectId.get(effectId);

		ClientBoundPacketData worldevent = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WORLD_EVENT);
		worldevent.writeInt(effectId);
		PositionSerializer.writeLegacyPositionL(worldevent, position);
		worldevent.writeInt(data);
		worldevent.writeBoolean(disableRelative);
		codec.writeClientbound(worldevent);
	}

}
