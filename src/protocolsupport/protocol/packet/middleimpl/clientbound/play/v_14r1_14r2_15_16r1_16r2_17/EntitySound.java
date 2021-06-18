package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntitySound;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class EntitySound extends MiddleEntitySound {

	public EntitySound(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData entitysound = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_SOUND);
		VarNumberCodec.writeVarInt(entitysound, id);
		MiscDataCodec.writeVarIntEnum(entitysound, category);
		VarNumberCodec.writeVarInt(entitysound, entityId);
		entitysound.writeFloat(volume);
		entitysound.writeFloat(pitch);
		codec.writeClientbound(entitysound);
	}

}
