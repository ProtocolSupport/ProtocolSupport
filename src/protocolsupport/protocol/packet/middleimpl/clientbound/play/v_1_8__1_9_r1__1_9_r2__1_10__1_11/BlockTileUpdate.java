package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockTileUpdate;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.nbt.tileupdate.TileEntityUpdateType;
import protocolsupport.protocol.typeremapper.nbt.tileupdate.TileNBTTransformer;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class BlockTileUpdate extends MiddleBlockTileUpdate<RecyclableCollection<ClientBoundPacketData>> {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		return RecyclableSingletonList.create(createPacketData(version, tag));
	}

	public static ClientBoundPacketData createPacketData(ProtocolVersion version, NBTTagCompoundWrapper tag) {
		TileEntityUpdateType type = TileEntityUpdateType.fromType(TileNBTTransformer.getTileType(tag));
		Position position = TileNBTTransformer.getPosition(tag);
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_9_4) && (type == TileEntityUpdateType.SIGN)) {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.LEGACY_PLAY_UPDATE_SIGN_ID, version);
			serializer.writePosition(position);
			for (String line : TileNBTTransformer.getSignLines(tag)) {
				serializer.writeString(line);
			}
			return serializer;
		} else {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_UPDATE_TILE_ID, version);
			serializer.writePosition(position);
			serializer.writeByte(type.getId());
			serializer.writeTag(TileNBTTransformer.transform(version, tag));
			return serializer;
		}
	}

}
