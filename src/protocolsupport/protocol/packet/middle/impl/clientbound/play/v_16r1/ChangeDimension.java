package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_16r1;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleChangeDimension;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r1;
import protocolsupport.protocol.typeremapper.legacy.LegacyDimension;

public class ChangeDimension extends MiddleChangeDimension implements IClientboundMiddlePacketV16r1 {

	public ChangeDimension(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData changedimension = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_RESPAWN);
		StringCodec.writeVarIntUTF8String(changedimension, LegacyDimension.getStringId(dimension));
		StringCodec.writeVarIntUTF8String(changedimension, world);
		changedimension.writeLong(hashedSeed);
		changedimension.writeByte(gamemodeCurrent.getId());
		changedimension.writeByte(gamemodePrevious.getId());
		changedimension.writeBoolean(worldDebug);
		changedimension.writeBoolean(worldFlat);
		changedimension.writeBoolean(keepEntityMetadata);
		io.writeClientbound(changedimension);
	}

}
