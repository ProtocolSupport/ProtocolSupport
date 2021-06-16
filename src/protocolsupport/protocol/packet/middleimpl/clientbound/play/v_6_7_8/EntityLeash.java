package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_6_7_8;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityLeash;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class EntityLeash extends MiddleEntityLeash {

	public EntityLeash(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData entityleash = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_LEASH);
		entityleash.writeInt(entityId);
		entityleash.writeInt(vehicleId);
		entityleash.writeBoolean(true);
		codec.writeClientbound(entityleash);
	}

}
