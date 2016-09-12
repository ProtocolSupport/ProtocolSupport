package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6__1_7;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.legacyremapper.LegacyTileEntityUpdate;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockTileUpdate;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.protocol.typeremapper.nbt.tileupdate.TileNBTTransformer;
import protocolsupport.protocol.utils.types.NBTTagCompoundWrapper;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BlockTileUpdate extends MiddleBlockTileUpdate<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		return RecyclableSingletonList.create(createPacketData(version, position, type, tag));
	}

	public static PacketData createPacketData(ProtocolVersion version, Position position, int type, NBTTagCompoundWrapper tag) {
		if (type == TileNBTTransformer.TileEntityUpdateType.SIGN.ordinal()) {
			PacketData serializer = PacketData.create(ClientBoundPacket.LEGACY_PLAY_UPDATE_SIGN_ID, version);
			serializer.writeLegacyPositionS(position);
			for (String line : LegacyTileEntityUpdate.getSignLines(tag)) {
				serializer.writeString(Utils.clampString(ChatAPI.fromJSON(line).toLegacyText(), 15));
			}
			return serializer;
		} else {
			PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_UPDATE_TILE_ID, version);
			serializer.writeLegacyPositionS(position);
			serializer.writeByte(type);
			serializer.writeTag(TileNBTTransformer.transform(type, version, tag));
			return serializer;
		}
	}

}
