package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractChunkCacheBlockTileUpdate;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.protocol.types.TileEntityType;
import protocolsupport.protocol.utils.CommonNBT;

public class BlockTileUpdate extends AbstractChunkCacheBlockTileUpdate {

	public BlockTileUpdate(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		codec.writeClientbound(create(version, tile));
	}

	public static ClientBoundPacketData create(ProtocolVersion version, TileEntity tile) {
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_9_4) && (tile.getType() == TileEntityType.SIGN)) {
			ClientBoundPacketData blocksignupdate = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_LEGACY_PLAY_UPDATE_SIGN);
			PositionSerializer.writeLegacyPositionL(blocksignupdate, tile.getPosition());
			for (String line : CommonNBT.getSignLines(tile.getNBT())) {
				StringSerializer.writeVarIntUTF8String(blocksignupdate, line);
			}
			return blocksignupdate;
		} else {
			ClientBoundPacketData blocktileupdate = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_BLOCK_TILE);
			PositionSerializer.writeLegacyPositionL(blocktileupdate, tile.getPosition());
			blocktileupdate.writeByte(tile.getType().getNetworkId());
			ItemStackSerializer.writeDirectTag(blocktileupdate, tile.getNBT());
			return blocktileupdate;
		}
	}

}
