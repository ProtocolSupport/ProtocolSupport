package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.LegacyTileEntityUpdate;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockTileUpdate;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.nbt.tileupdate.TileNBTTransformer;
import protocolsupport.protocol.utils.types.NBTTagCompoundWrapper;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BlockTileUpdate extends MiddleBlockTileUpdate<RecyclableCollection<ClientBoundPacketData>> {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		return RecyclableSingletonList.create(createPacketData(version, position, type, tag));
	}

	public static ClientBoundPacketData createPacketData(ProtocolVersion version, Position position, int type, NBTTagCompoundWrapper tag) {
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_9_4) && (type == TileNBTTransformer.TileEntityUpdateType.SIGN.ordinal())) {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.LEGACY_PLAY_UPDATE_SIGN_ID, version);
			serializer.writePosition(position);
			for (String line : LegacyTileEntityUpdate.getSignLines(tag)) {
				serializer.writeString(line);
			}
			return serializer;
		} else {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_UPDATE_TILE_ID, version);
			serializer.writePosition(position);
			serializer.writeByte(type);
			serializer.writeTag(TileNBTTransformer.transform(type, version, tag));
			return serializer;
		}
	}

}
