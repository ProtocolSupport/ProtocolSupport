package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4_1_5_1_6_1_7;

import java.io.IOException;

import net.minecraft.server.v1_9_R2.BlockPosition;
import net.minecraft.server.v1_9_R2.NBTTagCompound;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.legacyremapper.LegacyTileEntityUpdate;
import protocolsupport.protocol.legacyremapper.LegacyUtils;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockTileUpdate;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.protocol.typeremapper.nbt.tileupdate.TileNBTTransformer;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BlockTileUpdate extends MiddleBlockTileUpdate<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		return RecyclableSingletonList.create(createPacketData(version, position, type, tag));
	}

	public static PacketData createPacketData(ProtocolVersion version, BlockPosition position, int type, NBTTagCompound tag) {
		if (type == LegacyTileEntityUpdate.TileEntityUpdateType.SIGN.ordinal()) {
			PacketData serializer = PacketData.create(ClientBoundPacket.LEGACY_PLAY_UPDATE_SIGN_ID, version);
			serializer.writeInt(position.getX());
			serializer.writeShort(position.getY());
			serializer.writeInt(position.getZ());
			for (String line : LegacyTileEntityUpdate.getSignLines(tag)) {
				serializer.writeString(Utils.clampString(LegacyUtils.toText(ChatAPI.fromJSON(line)), 15));
			}
			return serializer;
		} else {
			PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_UPDATE_TILE_ID, version);
			serializer.writeInt(position.getX());
			serializer.writeShort(position.getY());
			serializer.writeInt(position.getZ());
			serializer.writeByte(type);
			serializer.writeTag(TileNBTTransformer.transform(type, version, tag));
			return serializer;
		}
	}

}
