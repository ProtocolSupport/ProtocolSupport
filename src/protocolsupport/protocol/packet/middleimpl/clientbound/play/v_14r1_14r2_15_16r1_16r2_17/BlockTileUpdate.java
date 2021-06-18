package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockTileUpdate;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;

public class BlockTileUpdate extends MiddleBlockTileUpdate {

	protected final TileEntityRemapper tileRemapper = TileEntityRemapper.getRemapper(version);

	public BlockTileUpdate(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		tile = tileRemapper.remap(tile);

		ClientBoundPacketData blocktileupdate = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_BLOCK_TILE);
		PositionCodec.writePosition(blocktileupdate, tile.getPosition());
		blocktileupdate.writeByte(tile.getType().getNetworkId());
		ItemStackCodec.writeDirectTag(blocktileupdate, tile.getNBT());
		codec.writeClientbound(blocktileupdate);
	}

}
