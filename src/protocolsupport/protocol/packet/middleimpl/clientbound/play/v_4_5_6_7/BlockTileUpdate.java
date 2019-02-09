package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockTileUpdate;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.storage.netcache.TileDataCache;
import protocolsupport.protocol.typeremapper.legacy.chat.LegacyChat;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.protocol.utils.types.TileEntity;
import protocolsupport.protocol.utils.types.TileEntityType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BlockTileUpdate extends MiddleBlockTileUpdate {

	public BlockTileUpdate(ConnectionImpl connection) {
		super(connection);
	}

	protected final TileEntityRemapper tileremapper = TileEntityRemapper.getRemapper(version);

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		return RecyclableSingletonList.create(create(
			version, cache.getAttributesCache().getLocale(),
			tileremapper.remap(tile, cache.getTileCache().getBlockData(
				TileDataCache.getChunkCoordsFromPosition(position), TileDataCache.getLocalCoordFromPosition(position)
			))
		));
	}

	public static ClientBoundPacketData create(ProtocolVersion version, String locale, TileEntity tile) {
		TileEntityType type = tile.getType();
		if (type == TileEntityType.SIGN) {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.LEGACY_PLAY_UPDATE_SIGN_ID);
			PositionSerializer.writeLegacyPositionS(serializer, tile.getPosition());
			for (String line : CommonNBT.getSignLines(tile.getNBT())) {
				StringSerializer.writeString(serializer, version, LegacyChat.clampLegacyText(ChatAPI.fromJSON(line).toLegacyText(locale), 15));
			}
			return serializer;
		} else {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_UPDATE_TILE_ID);
			PositionSerializer.writeLegacyPositionS(serializer, tile.getPosition());
			serializer.writeByte(type.getNetworkId());
			ItemStackSerializer.writeTag(serializer, version, tile.getNBT());
			return serializer;
		}
	}

}
