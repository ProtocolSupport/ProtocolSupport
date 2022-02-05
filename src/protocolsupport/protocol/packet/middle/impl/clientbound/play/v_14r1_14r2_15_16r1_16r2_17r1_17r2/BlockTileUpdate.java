package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_14r1_14r2_15_16r1_16r2_17r1_17r2;

import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleBlockTileUpdate;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV15;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;
import protocolsupport.protocol.typeremapper.legacy.LegacyTileEntityId;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;

public class BlockTileUpdate extends MiddleBlockTileUpdate implements
IClientboundMiddlePacketV14r1,
IClientboundMiddlePacketV14r2,
IClientboundMiddlePacketV15,
IClientboundMiddlePacketV16r1,
IClientboundMiddlePacketV16r2,
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2 {

	protected final TileEntityRemapper tileRemapper = TileEntityRemapper.getRemapper(version);

	public BlockTileUpdate(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		tile = tileRemapper.remap(tile);

		ClientBoundPacketData blocktileupdate = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_BLOCK_TILE);
		PositionCodec.writePosition(blocktileupdate, tile.getPosition());
		blocktileupdate.writeByte(LegacyTileEntityId.toLegacyId(tile.getType()));
		ItemStackCodec.writeDirectTag(blocktileupdate, tile.getNBT());
		io.writeClientbound(blocktileupdate);
	}

}
