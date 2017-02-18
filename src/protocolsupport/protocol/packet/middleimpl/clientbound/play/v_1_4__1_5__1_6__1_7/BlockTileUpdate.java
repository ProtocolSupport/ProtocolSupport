package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6__1_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockTileUpdate;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.tileentity.TileEntityUpdateType;
import protocolsupport.protocol.typeremapper.tileentity.TileNBTRemapper;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class BlockTileUpdate extends MiddleBlockTileUpdate<RecyclableCollection<ClientBoundPacketData>> {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		return RecyclableSingletonList.create(createPacketData(version, tag));
	}

	public static ClientBoundPacketData createPacketData(ProtocolVersion version, NBTTagCompoundWrapper tag) {
		TileEntityUpdateType type = TileEntityUpdateType.fromType(TileNBTRemapper.getTileType(tag));
		Position position = TileNBTRemapper.getPosition(tag);
		if (type == TileEntityUpdateType.SIGN) {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.LEGACY_PLAY_UPDATE_SIGN_ID, version);
			serializer.writeLegacyPositionS(position);
			for (String line : TileNBTRemapper.getSignLines(tag)) {
				serializer.writeString(Utils.clampString(ChatAPI.fromJSON(line).toLegacyText(), 15));
			}
			return serializer;
		} else {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_UPDATE_TILE_ID, version);
			serializer.writeLegacyPositionS(position);
			serializer.writeByte(type.getId());
			serializer.writeTag(TileNBTRemapper.remap(version, tag));
			return serializer;
		}
	}

}
