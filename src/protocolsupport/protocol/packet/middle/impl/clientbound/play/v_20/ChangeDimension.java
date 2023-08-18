package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_20;

import protocolsupport.protocol.codec.OptionalCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleChangeDimension;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;

public class ChangeDimension extends MiddleChangeDimension implements IClientboundMiddlePacketV20 {

	public ChangeDimension(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData changedimensionPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_RESPAWN);
		StringCodec.writeVarIntUTF8String(changedimensionPacket, worldType);
		StringCodec.writeVarIntUTF8String(changedimensionPacket, worldName);
		changedimensionPacket.writeLong(hashedSeed);
		changedimensionPacket.writeByte(gamemodeCurrent.getId());
		changedimensionPacket.writeByte(gamemodePrevious.getId());
		changedimensionPacket.writeBoolean(worldDebug);
		changedimensionPacket.writeBoolean(worldFlat);
		changedimensionPacket.writeByte(keepDataFlags);
		OptionalCodec.writeOptional(changedimensionPacket, deathPosition, PositionCodec::writeWorldPosition);
		VarNumberCodec.writeVarInt(changedimensionPacket, portalCooldown);
		io.writeClientbound(changedimensionPacket);
	}

}
