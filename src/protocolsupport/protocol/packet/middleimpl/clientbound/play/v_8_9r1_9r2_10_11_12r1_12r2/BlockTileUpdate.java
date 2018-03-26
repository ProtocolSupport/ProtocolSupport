package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockTileUpdate;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.tileentity.TileNBTRemapper;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.protocol.utils.types.TileEntityType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class BlockTileUpdate extends MiddleBlockTileUpdate {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		return RecyclableSingletonList.create(createPacketData(connection.getVersion(), TileEntityType.getByNetworkId(type), position, tag));
	}

	public static ClientBoundPacketData createPacketData(ProtocolVersion version, TileEntityType type, Position position, NBTTagCompoundWrapper tag) {
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_9_4) && (type == TileEntityType.SIGN)) {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.LEGACY_PLAY_UPDATE_SIGN_ID);
			PositionSerializer.writePosition(serializer, position);
			for (String line : TileNBTRemapper.getSignLines(tag)) {
				StringSerializer.writeString(serializer, version, line);
			}
			return serializer;
		} else {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_UPDATE_TILE_ID);
			PositionSerializer.writePosition(serializer, position);
			serializer.writeByte(type.getNetworkId());
			ItemStackSerializer.writeTag(serializer, version, TileNBTRemapper.remap(version, tag));
			return serializer;
		}
	}

}
