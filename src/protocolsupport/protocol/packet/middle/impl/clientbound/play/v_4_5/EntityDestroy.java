package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5;

import java.util.Collections;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8.AbstractPassengerStackEntityDestroy;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8.AbstractPassengerStackEntityPassengers;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8.AbstractPassengerStackEntityPassengers.NetworkEntityVehicleData;
import protocolsupport.protocol.pipeline.IPacketDataIO;

public class EntityDestroy extends AbstractPassengerStackEntityDestroy implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5
{

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
	protected void writeDestroyEntity(int entityid) {
		writeDestroyEntity(io, entityid);
	}


	public static void writeDestroyEntity(IPacketDataIO io, int entityId) {
		ClientBoundPacketData entitydestroyPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_DESTROY);
		entitydestroyPacket.writeByte(1); //entity array length
		entitydestroyPacket.writeInt(entityId);
		io.writeClientbound(entitydestroyPacket);
	}

}
