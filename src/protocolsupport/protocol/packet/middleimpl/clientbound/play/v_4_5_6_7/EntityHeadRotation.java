package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityHeadRotation;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class EntityHeadRotation extends MiddleEntityHeadRotation {

	public EntityHeadRotation(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData entityheadrotation = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_HEAD_ROTATION);
		entityheadrotation.writeInt(entityId);
		entityheadrotation.writeByte(headRot);
		codec.writeClientbound(entityheadrotation);
	}

}
