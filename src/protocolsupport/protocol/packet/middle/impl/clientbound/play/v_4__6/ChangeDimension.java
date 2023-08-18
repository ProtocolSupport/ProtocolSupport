package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__6;

import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__13.AbstractChunkCacheChangeDimension;
import protocolsupport.protocol.typeremapper.legacy.LegacyDimension;
import protocolsupport.protocol.types.Difficulty;

public class ChangeDimension extends AbstractChunkCacheChangeDimension implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6
{

	public ChangeDimension(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeChangeDimension(int dimensionId) {
		ClientBoundPacketData changedimension = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_RESPAWN);
		changedimension.writeInt(dimensionId);
		MiscDataCodec.writeByteEnum(changedimension, Difficulty.HARD);
		changedimension.writeByte(gamemodeCurrent.getId());
		changedimension.writeShort(256);
		StringCodec.writeShortUTF16BEString(changedimension, LegacyDimension.getWorldType(worldFlat));
		io.writeClientbound(changedimension);
	}

}
