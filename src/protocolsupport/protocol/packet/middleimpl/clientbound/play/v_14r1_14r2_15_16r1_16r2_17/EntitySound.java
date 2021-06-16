package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntitySound;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class EntitySound extends MiddleEntitySound {

	public EntitySound(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData entitysound = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_SOUND);
		VarNumberSerializer.writeVarInt(entitysound, id);
		MiscSerializer.writeVarIntEnum(entitysound, category);
		VarNumberSerializer.writeVarInt(entitysound, entityId);
		entitysound.writeFloat(volume);
		entitysound.writeFloat(pitch);
		codec.writeClientbound(entitysound);
	}

}
