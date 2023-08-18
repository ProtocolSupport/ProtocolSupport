package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__13.AbstractChunkCacheBlockTileUpdate;
import protocolsupport.protocol.typeremapper.legacy.LegacyChat;
import protocolsupport.protocol.typeremapper.legacy.LegacyTileEntityId;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.protocol.types.TileEntityType;
import protocolsupport.protocol.utils.CommonNBT;

public class BlockTileUpdate extends AbstractChunkCacheBlockTileUpdate implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6,
IClientboundMiddlePacketV7
{

	public BlockTileUpdate(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		io.writeClientbound(create(version, cache.getClientCache().getLocale(), tile));
	}

	public static ClientBoundPacketData create(ProtocolVersion version, String locale, TileEntity tile) {
		TileEntityType type = tile.getType();
		if (type == TileEntityType.SIGN) {
			ClientBoundPacketData blocksignupdate = ClientBoundPacketData.create(ClientBoundPacketType.LEGACY_PLAY_UPDATE_SIGN);
			PositionCodec.writePositionISI(blocksignupdate, tile.getPosition());
			for (String line : CommonNBT.getSignLines(tile.getNBT())) {
				StringCodec.writeString(blocksignupdate, version, LegacyChat.clampLegacyText(ChatAPI.fromJSON(line, true).toLegacyText(locale), 15));
			}
			return blocksignupdate;
		} else {
			ClientBoundPacketData blocktileupdate = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_BLOCK_TILE);
			PositionCodec.writePositionISI(blocktileupdate, tile.getPosition());
			blocktileupdate.writeByte(LegacyTileEntityId.toLegacyId(type));
			ItemStackCodec.writeShortTag(blocktileupdate, tile.getNBT());
			return blocktileupdate;
		}
	}

}
