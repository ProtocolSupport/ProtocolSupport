package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8;

import java.io.IOException;

import net.minecraft.server.v1_9_R2.BlockPosition;
import net.minecraft.server.v1_9_R2.NBTTagCompound;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.LegacyTileEntityUpdate;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockTileUpdate;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.protocol.typeremapper.nbt.tileupdate.TileNBTTransformer;
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
			serializer.writePosition(position);
			for (String line : LegacyTileEntityUpdate.getSignLines(tag)) {
				serializer.writeString(line);
			}
			return serializer;
		} else {
			PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_UPDATE_TILE_ID, version);
			serializer.writePosition(position);
			serializer.writeByte(type);
			serializer.writeTag(TileNBTTransformer.transform(type, version, tag));
			return serializer;
		}
	}

}
