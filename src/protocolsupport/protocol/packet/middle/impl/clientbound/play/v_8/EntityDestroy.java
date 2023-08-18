package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8;

import java.util.Collections;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__8.AbstractPassengerStackEntityDestroy;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__8.AbstractPassengerStackEntityPassengers;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__8.AbstractPassengerStackEntityPassengers.NetworkEntityVehicleData;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_6__8.EntityPassengers;
import protocolsupport.protocol.pipeline.IPacketDataIO;

public class EntityDestroy extends AbstractPassengerStackEntityDestroy implements IClientboundMiddlePacketV8 {

	public EntityDestroy(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeRemovePassengers(NetworkEntityVehicleData entity) {
		AbstractPassengerStackEntityPassengers.writeVehiclePassengers(io, EntityPassengers::create, entity, Collections.emptyList());
	}

	@Override
	protected void writeLeaveVehicle(NetworkEntityVehicleData entity) {
		AbstractPassengerStackEntityPassengers.writeLeaveVehicleConnectStack(io, EntityPassengers::create, entity);
	}

	@Override
	protected void writeDestroyEntity(int entityId) {
		writeDestroyEntity(io, entityId);
	}


	public static void writeDestroyEntity(IPacketDataIO io, int entityId) {
		ClientBoundPacketData entitydestroyPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_DESTROY);
		VarNumberCodec.writeVarInt(entitydestroyPacket, 1); //entity array length
		VarNumberCodec.writeVarInt(entitydestroyPacket, entityId);
		io.writeClientbound(entitydestroyPacket);
	}

}
