package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_20;

import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleEntitySound;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;

public class EntitySound extends MiddleEntitySound implements
IClientboundMiddlePacketV20 {

	public EntitySound(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData entitysoundPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_SOUND);
		VarNumberCodec.writeVarInt(entitysoundPacket, id);
		MiscDataCodec.writeVarIntEnum(entitysoundPacket, category);
		VarNumberCodec.writeVarInt(entitysoundPacket, entityId);
		entitysoundPacket.writeFloat(volume);
		entitysoundPacket.writeFloat(pitch);
		entitysoundPacket.writeLong(seed);
		io.writeClientbound(entitysoundPacket);
	}

}
