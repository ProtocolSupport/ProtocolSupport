package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractBlockTileUpdate;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.legacy.chat.LegacyChat;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.protocol.types.TileEntityType;
import protocolsupport.protocol.utils.CommonNBT;

public class BlockTileUpdate extends AbstractBlockTileUpdate {

	public BlockTileUpdate(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		codec.write(create(version, cache.getAttributesCache().getLocale(), tile));
	}

	public static ClientBoundPacketData create(ProtocolVersion version, String locale, TileEntity tile) {
		TileEntityType type = tile.getType();
		if (type == TileEntityType.SIGN) {
			ClientBoundPacketData blocksignupdate = ClientBoundPacketData.create(PacketType.CLIENTBOUND_LEGACY_PLAY_UPDATE_SIGN_ID);
			PositionSerializer.writeLegacyPositionS(blocksignupdate, tile.getPosition());
			for (String line : CommonNBT.getSignLines(tile.getNBT())) {
				StringSerializer.writeString(blocksignupdate, version, LegacyChat.clampLegacyText(ChatAPI.fromJSON(line).toLegacyText(locale), 15));
			}
			return blocksignupdate;
		} else {
			ClientBoundPacketData blocktileupdate = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_BLOCK_TILE);
			PositionSerializer.writeLegacyPositionS(blocktileupdate, tile.getPosition());
			blocktileupdate.writeByte(type.getNetworkId());
			ItemStackSerializer.writeShortTag(blocktileupdate, tile.getNBT());
			return blocktileupdate;
		}
	}

}
