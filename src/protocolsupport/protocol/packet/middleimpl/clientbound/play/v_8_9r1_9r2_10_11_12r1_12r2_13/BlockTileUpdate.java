package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractBlockTileUpdate;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.protocol.types.TileEntityType;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BlockTileUpdate extends AbstractBlockTileUpdate {

	public BlockTileUpdate(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<? extends IPacketData> toData() {
		return RecyclableSingletonList.create(create(version, tile));
	}

	public static ClientBoundPacketData create(ProtocolVersion version, TileEntity tile) {
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_9_4) && (tile.getType() == TileEntityType.SIGN)) {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_LEGACY_PLAY_UPDATE_SIGN_ID);
			PositionSerializer.writeLegacyPositionL(serializer, tile.getPosition());
			for (String line : CommonNBT.getSignLines(tile.getNBT())) {
				StringSerializer.writeVarIntUTF8String(serializer, line);
			}
			return serializer;
		} else {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_BLOCK_TILE);
			PositionSerializer.writeLegacyPositionL(serializer, tile.getPosition());
			serializer.writeByte(tile.getType().getNetworkId());
			ItemStackSerializer.writeDirectTag(serializer, tile.getNBT());
			return serializer;
		}
	}

}
