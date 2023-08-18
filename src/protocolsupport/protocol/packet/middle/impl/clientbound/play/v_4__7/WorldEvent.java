package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__7;

import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleWorldEvent;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;
import protocolsupport.protocol.typeremapper.itemstack.PreFlatteningItemIdData;
import protocolsupport.protocol.typeremapper.legacy.LegacyEffect;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.HashMapBasedIntMappingTable;

public class WorldEvent extends MiddleWorldEvent implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6,
IClientboundMiddlePacketV7
{

	protected final HashMapBasedIntMappingTable legacyEffectId = LegacyEffect.REGISTRY.getTable(version);
	protected final ArrayBasedIntMappingTable blockDataRemappingTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);

	public WorldEvent(IMiddlePacketInit init) {
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

		ClientBoundPacketData worldevent = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WORLD_EVENT);
		worldevent.writeInt(effectId);
		PositionCodec.writePositionIBI(worldevent, position);
		worldevent.writeInt(data);
		worldevent.writeBoolean(disableRelative);
		io.writeClientbound(worldevent);
	}

}
