package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockTileUpdate;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.tileentity.TileNBTRemapper;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.protocol.utils.types.TileEntityType;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class BlockTileUpdate extends MiddleBlockTileUpdate {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		return RecyclableSingletonList.create(createPacketData(connection.getVersion(), cache.getLocale(), TileEntityType.getByNetworkId(type), position, tag));
	}

	public static ClientBoundPacketData createPacketData(ProtocolVersion version, String locale, TileEntityType type, Position position, NBTTagCompoundWrapper tag) {
		if (type == TileEntityType.SIGN) {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.LEGACY_PLAY_UPDATE_SIGN_ID, version);
			PositionSerializer.writeLegacyPositionS(serializer, position);
			for (String line : TileNBTRemapper.getSignLines(tag)) {
				StringSerializer.writeString(serializer, version, Utils.clampString(ChatAPI.fromJSON(line).toLegacyText(locale), 15));
			}
			return serializer;
		} else {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_UPDATE_TILE_ID, version);
			PositionSerializer.writeLegacyPositionS(serializer, position);
			serializer.writeByte(type.getNetworkId());
			ItemStackSerializer.writeTag(serializer, version, TileNBTRemapper.remap(version, tag));
			return serializer;
		}
	}

}
