package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleWorldEvent;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV10;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV11;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV13;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r2;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;
import protocolsupport.protocol.typeremapper.itemstack.FlatteningItemId;
import protocolsupport.protocol.typeremapper.itemstack.PreFlatteningItemIdData;
import protocolsupport.protocol.typeremapper.legacy.LegacyEffect;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.HashMapBasedIntMappingTable;

public class WorldEvent extends MiddleWorldEvent implements
IClientboundMiddlePacketV8,
IClientboundMiddlePacketV9r1,
IClientboundMiddlePacketV9r2,
IClientboundMiddlePacketV10,
IClientboundMiddlePacketV11,
IClientboundMiddlePacketV12r1,
IClientboundMiddlePacketV12r2,
IClientboundMiddlePacketV13 {

	protected final HashMapBasedIntMappingTable legacyEffectId = LegacyEffect.REGISTRY.getTable(version);
	protected final ArrayBasedIntMappingTable blockDataRemappingTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);
	protected final ArrayBasedIntMappingTable flatteningItemIdTable = FlatteningItemId.REGISTRY_TO_CLIENT.getTable(version);

	public WorldEvent(IMiddlePacketInit init) {
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

		ClientBoundPacketData worldevent = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WORLD_EVENT);
		worldevent.writeInt(effectId);
		PositionCodec.writePositionLXYZ(worldevent, position);
		worldevent.writeInt(data);
		worldevent.writeBoolean(disableRelative);
		io.writeClientbound(worldevent);
	}

}
