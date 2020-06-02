package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockTileUpdate;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;

public class BlockTileUpdate extends MiddleBlockTileUpdate {

	protected final TileEntityRemapper tileRemapper = TileEntityRemapper.getRemapper(version);

	public BlockTileUpdate(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		tile = tileRemapper.remap(tile);

		ClientBoundPacketData blocktileupdate = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_BLOCK_TILE);
		PositionSerializer.writePosition(blocktileupdate, tile.getPosition());
		blocktileupdate.writeByte(tile.getType().getNetworkId());
		ItemStackSerializer.writeDirectTag(blocktileupdate, tile.getNBT());
		codec.write(blocktileupdate);
	}

}
