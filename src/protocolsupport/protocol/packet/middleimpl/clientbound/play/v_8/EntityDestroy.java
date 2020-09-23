package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8;

import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

import protocolsupport.protocol.packet.PacketDataCodec;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8.AbstractPassengerStackEntityDestroy;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8.AbstractPassengerStackEntityPassengers;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8.AbstractPassengerStackEntityPassengers.NetworkEntityVehicleData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_6_7_8.EntityPassengers;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.networkentity.NetworkEntity;

public class EntityDestroy extends AbstractPassengerStackEntityDestroy {

	public EntityDestroy(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeRemovePassengers(NetworkEntityVehicleData entity) {
		AbstractPassengerStackEntityPassengers.writeVehiclePassengers(codec, EntityPassengers::create, entity, Collections.emptyList());
	}

	@Override
	protected void writeLeaveVehicle(NetworkEntityVehicleData entity) {
		AbstractPassengerStackEntityPassengers.writeLeaveVehicleConnectStack(codec, EntityPassengers::create, entity);
	}

	@Override
	protected <A extends List<NetworkEntity> & RandomAccess> void writeDestroyEntities(A entities) {
		writeDestroyEntities(codec, entities);
	}


	public static <A extends List<NetworkEntity> & RandomAccess> void writeDestroyEntities(PacketDataCodec codec, A entities) {
		ClientBoundPacketData entitydestory = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_DESTROY);
		ArraySerializer.writeVarIntTArray(entitydestory, entities, (to, entity) -> VarNumberSerializer.writeVarInt(to, entity.getId()));
		codec.write(entitydestory);
	}

}
