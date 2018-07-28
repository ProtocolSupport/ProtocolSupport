package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockChangeSingle;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.block.BlockIdRemappingHelper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BlockChangeSingle extends MiddleBlockChangeSingle {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_BLOCK_CHANGE_SINGLE_ID);
		PositionSerializer.writePosition(serializer, position);
		VarNumberSerializer.writeVarInt(serializer, BlockIdRemappingHelper.remapToCombinedIdNormal(version, id));
		return RecyclableSingletonList.create(serializer);
	}

}
