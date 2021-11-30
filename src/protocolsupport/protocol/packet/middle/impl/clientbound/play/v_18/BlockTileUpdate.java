package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_18;

import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleBlockTileUpdate;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV18;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;

public class BlockTileUpdate extends MiddleBlockTileUpdate implements IClientboundMiddlePacketV18 {

	public BlockTileUpdate(IMiddlePacketInit init) {
		super(init);
	}

	protected final TileEntityRemapper tileRemapper = TileEntityRemapper.getRemapper(version);

	@Override
	protected void write() {
		tile = tileRemapper.remap(tile);

		ClientBoundPacketData blocktileupdate = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_BLOCK_TILE);
		PositionCodec.writePosition(blocktileupdate, tile.getPosition());
		VarNumberCodec.writeVarInt(blocktileupdate, tile.getType().getNetworkId());
		ItemStackCodec.writeDirectTag(blocktileupdate, tile.getNBT());
		io.writeClientbound(blocktileupdate);
	}

}
