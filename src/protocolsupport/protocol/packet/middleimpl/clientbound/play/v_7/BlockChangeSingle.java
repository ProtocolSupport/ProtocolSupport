package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockChangeSingle;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BlockChangeSingle extends MiddleBlockChangeSingle {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_BLOCK_CHANGE_SINGLE_ID, version);
		PositionSerializer.writeLegacyPositionB(serializer, position);
		id = IdRemapper.BLOCK.getTable(version).getRemap(id);
		VarNumberSerializer.writeVarInt(serializer, MinecraftData.getBlockIdFromState(id));
		serializer.writeByte(MinecraftData.getBlockDataFromState(id));
		return RecyclableSingletonList.create(serializer);
	}

}
