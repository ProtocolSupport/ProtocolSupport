package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldEvent;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.itemstack.FlatteningItemId;
import protocolsupport.protocol.typeremapper.legacy.LegacyEffect;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.HashMapBasedIdRemappingTable;

public class WorldEvent extends MiddleWorldEvent {

	protected final HashMapBasedIdRemappingTable legacyEffectId = LegacyEffect.REGISTRY.getTable(version);
	protected final ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
	protected final ArrayBasedIdRemappingTable flatteningItemIdTable = FlatteningItemId.REGISTRY_TO_CLIENT.getTable(version);

	public WorldEvent(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		if (effectId == 2001) {
			data = blockDataRemappingTable.getRemap(data);
		} else if ((effectId == 1010) && (data != 0)) {
			data = flatteningItemIdTable.getRemap(data);
		}
		effectId = legacyEffectId.getRemap(effectId);

		ClientBoundPacketData worldevent = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WORLD_EVENT);
		worldevent.writeInt(effectId);
		PositionSerializer.writePosition(worldevent, position);
		worldevent.writeInt(data);
		worldevent.writeBoolean(disableRelative);
		codec.write(worldevent);
	}

}
